package com.fanap.structure.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fanap.structure.dto.response.RoleResponseDto;
import com.fanap.structure.persistance.dao.IRoleDao;
import com.fanap.structure.persistance.entity.Role;
import com.fanap.structure.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired 
    private IRoleDao iRoleDao;

    


    @Override
    @Cacheable(value="roles")
    public List<Role> findAllRoles() {
        return iRoleDao.findAll();
    }

    @Override
    public RoleResponseDto roleId(String title) {
        List<Role> roles = findAllRoles();
        RoleResponseDto roleResponseDto=new RoleResponseDto();
        for(Role role:roles){
            if(role.getTitle().equals(title)){
                BeanUtils.copyProperties(role, roleResponseDto);
            }
        }
        if(roleResponseDto.getId() !=null){
            return roleResponseDto;
        }
        else{
            roleResponseDto.setMessage("سطح مورد نظر یافت نشد");
            return roleResponseDto;
        }
        
    }
    @Override
    public RoleResponseDto roleTitle(Long id) {
        List<Role> roles = findAllRoles();
        RoleResponseDto roleResponseDto=new RoleResponseDto();
        for(Role role:roles){
            if(role.getId()==(id)){
                BeanUtils.copyProperties(role, roleResponseDto);
               
            }
        }
        return roleResponseDto;
    }
     
}
