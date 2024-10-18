package com.fanap.structure.service;

import java.util.List;

import com.fanap.structure.dto.response.RoleResponseDto;
import com.fanap.structure.persistance.entity.Role;


public interface IRoleService {
    List<Role> findAllRoles();
    RoleResponseDto roleId(String title);
    RoleResponseDto roleTitle(Long id);
}
