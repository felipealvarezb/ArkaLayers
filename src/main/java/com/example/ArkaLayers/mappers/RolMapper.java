package com.example.ArkaLayers.mappers;

import com.example.ArkaLayers.dtos.RolDTO;
import com.example.ArkaLayers.dtos.response.RolResponseDTO;
import com.example.ArkaLayers.entities.Rol;

public interface RolMapper {

  Rol rolDtoToRol(RolDTO rolDTO);

  RolResponseDTO rolToRolResponseDTO(Rol rol);
}
