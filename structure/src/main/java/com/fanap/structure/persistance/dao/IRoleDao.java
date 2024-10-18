package com.fanap.structure.persistance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fanap.structure.persistance.entity.Role;

@Repository
public interface IRoleDao extends JpaRepository<Role, Long>{

    Role findByTitle(String title);
    
} 
