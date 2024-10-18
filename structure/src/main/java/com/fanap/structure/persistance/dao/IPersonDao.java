package com.fanap.structure.persistance.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fanap.structure.persistance.entity.Person;

@Repository
public interface IPersonDao extends JpaRepository<Person, Long> {
    Person findByRoleId(Long id);
    Person findByNationalCode(String nationalCode);
    
}
