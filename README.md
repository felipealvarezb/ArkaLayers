# Modificaciones en el código para cumplir con los principios SOLID

## Open/Closed Principle (OCP) & Single Responsability

### 🔹 Antes de aplicar OCP y SR

Inicialmente, teníamos una clase GlobalExceptionHandler que contenía todas las excepciones en un solo lugar. 
Cada vez que queríamos manejar una nueva excepción, debíamos modificar directamente esta clase, 
lo que violaba el principio de Abierto/Cerrado (OCP), ya que la clase debía cambiar cada vez que se agregaba una nueva excepción.

Código original:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("error", "Not Found");
    response.put("message", ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, Object> errors = new HashMap<>();
    errors.put("error", "Validation Failed");

    List<String> messages = ex.getBindingResult().getFieldErrors()
            .stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.toList());

    errors.put("messages", messages);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }
}

```

🔹 Aplicando Open/Closed Principle & Single Responsability 

Para hacer que la solución sea abierta para extensión pero cerrada para modificación, implementamos un enfoque basado en 
estrategias utilizando interfaces y clases específicas para cada tipo de excepción.

Ahora, en lugar de modificar GlobalExceptionHandler cada vez que agregamos un nuevo tipo de excepción, 
simplemente creamos una nueva clase que implemente la interfaz ExceptionHandlerStrategy<T>, respetando el principio OCP.

```java
public interface ExceptionHandlerStrategy<T extends Exception> {
    ResponseEntity<?> handleException(T ex);
    Class<T> getExceptionType();
}
```

Esta interfaz permite que cada estrategia defina su propio manejo de excepciones. Lo que hace que se use también el principio Single Responsability 
ya que cada clases solo se encarga de manejar una Excepcion. Cuando se tengan ya muchas excepciones se debe manejar otra estrategia.


```java
@Component
public class EntityNotFoundExceptionHandler implements ExceptionHandlerStrategy<EntityNotFoundException> {
    @Override
    public ResponseEntity<?> handleException(EntityNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Not Found");
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @Override
    public Class<EntityNotFoundException> getExceptionType() {
        return EntityNotFoundException.class;
    }
}

@Component
public class ValidationExceptionHandler implements ExceptionHandlerStrategy<MethodArgumentNotValidException> {
    @Override
    public ResponseEntity<?> handleException(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("error", "Validation Failed");

        List<Map<String, String>> errorDetails = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> {
                    Map<String, String> fieldError = new HashMap<>();
                    fieldError.put("field", error.getField());
                    fieldError.put("message", error.getDefaultMessage());
                    return fieldError;
                })
                .collect(Collectors.toList());

        errors.put("details", errorDetails);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @Override
    public Class<MethodArgumentNotValidException> getExceptionType() {
        return MethodArgumentNotValidException.class;
    }
}

```

Cada excepción ahora tiene su propia clase de manejo sin necesidad de modificar GlobalExceptionHandler.

Se modificó el GlobalExceptionHandler para delegar el manejo de excepciones

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Map<Class<? extends Exception>, ExceptionHandlerStrategy<?>> handlers;

    @Autowired
    public GlobalExceptionHandler(List<ExceptionHandlerStrategy<?>> handlerStrategies) {
        handlers = handlerStrategies.stream()
                .collect(Collectors.toMap(ExceptionHandlerStrategy::getExceptionType, strategy -> strategy));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        ExceptionHandlerStrategy handler = handlers.get(ex.getClass());

        if (handler != null) {
            return handler.handleException(ex);
        }

        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
```






## Single Responsability CreateOrder

### 🔹 Antes de aplicar SR

Iniciálmente el método createOrder en el OrderService realizaba demasiadas tareas, hacia consultas, calculaba el total, limpiaba el carrito de compras entre otras cosas
Así se veía:

```
@Override
  public Order createOrder(Long userId) {
    User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

    Cart userCart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException("User cart not found"));

    List<CartDetail> cartDetails = userCart.getCartDetails();

    if (cartDetails.isEmpty()) {
      throw new IllegalStateException("Cart is empty");
    }

    Order order = new Order();
    order.setUser(existingUser);
    order.setStatus("Pending");
    order.setOrderDetails(new ArrayList<>());

    double totalOrder = 0.0;
    for (CartDetail cartDetail : cartDetails) {
      OrderDetail orderDetail = new OrderDetail();
      orderDetail.setProduct(cartDetail.getProduct());
      orderDetail.setOrder(order);
      orderDetail.setQuantity(cartDetail.getQuantity());
      orderDetail.setSubtotal(cartDetail.getProduct().getProductPrice() * cartDetail.getQuantity());
      order.getOrderDetails().add(orderDetail);
      totalOrder += orderDetail.getSubtotal();
    }

    order.setTotal(totalOrder);

    order = orderRepository.save(order);

    cartDetailRepository.deleteByCartId(userCart.getId());
    userCart.setCartDetails(new ArrayList<>());
    cartRepository.save(userCart);

    return order;
  }
```

Después de separar la lógica, dividir el código en funciónes que solo realizan una única responsabilidad, ya solo tendremos que modificar una función y no el método completo de 
crear una orden. 

🔹 Aplicando Single Responsability 

```java
@Override
  public Order createOrder(Long userId) {
    User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

    Cart userCart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException("User cart not found"));

    validateCart(userCart);

    Order order = new Order();
    order.setUser(existingUser);
    order.setStatus("Pending");
    order.setOrderDetails(new ArrayList<>());

    double totalOrder = calculateTotalOrder(userCart, order);

    order.setTotal(totalOrder);
    order = orderRepository.save(order);

    clearUserCart(userCart);

    return order;
  }

  private double calculateTotalOrder(Cart cart, Order order) {
    double totalOrder = 0.0;
    for (CartDetail cartDetail : cart.getCartDetails()) {
      OrderDetail orderDetail = new OrderDetail();
      orderDetail.setProduct(cartDetail.getProduct());
      orderDetail.setOrder(order);
      orderDetail.setQuantity(cartDetail.getQuantity());
      orderDetail.setSubtotal(cartDetail.getProduct().getProductPrice() * cartDetail.getQuantity());
      order.getOrderDetails().add(orderDetail);
      totalOrder += orderDetail.getSubtotal();
    }
    return totalOrder;
  }

  private void validateCart(Cart cart) {
    if (cart.getCartDetails().isEmpty()) {
      throw new IllegalStateException("Cart is empty");
    }
  }

  private void clearUserCart(Cart cart) {
    cartDetailRepository.deleteByCartId(cart.getId());
    cart.setCartDetails(new ArrayList<>());
    cartRepository.save(cart);
  }

```

Si en algún momento se tiene que cambiar la manera en la que se calcula el total, no se modifica el metodo createOrder sino que se modifica el calculateTotalOrder
cada función asume una única responsabilidad. 
















