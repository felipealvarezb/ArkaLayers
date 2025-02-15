package com.example.ArkaLayers.mappers.implementation;

import com.example.ArkaLayers.dtos.RolDTO;
import com.example.ArkaLayers.dtos.response.RolResponseDTO;
import com.example.ArkaLayers.entities.Rol;
import com.example.ArkaLayers.mappers.RolMapper;
import org.springframework.stereotype.Component;

@Component
public class RolMapperImpl implements RolMapper {
  @Override
  public Rol rolDtoToRol(RolDTO rolDTO) {
    Rol rol = new Rol();
    rol.setName(rolDTO.getName());
    rol.setDescription(rolDTO.getDescription());
    return rol;
  }

  @Override
  public RolResponseDTO rolToRolResponseDTO(Rol rol) {
    RolResponseDTO rolResponseDTO = new RolResponseDTO();
    rolResponseDTO.setName(rol.getName());
    rolResponseDTO.setDescription(rol.getDescription());
    rolResponseDTO.setCreatedAt(rol.getCreatedAt());
    rolResponseDTO.setUpdatedAt(rol.getUpdatedAt());
    return rolResponseDTO;
  }
}
