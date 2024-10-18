package com.fanap.structure.persistance.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.fanap.structure.dto.request.PersonRequestDto;
import com.fanap.structure.persistance.dao.IPersonGlobalDao;
import com.fanap.structure.persistance.entity.Person;

@Repository
public class PersonDaoImpl implements IPersonGlobalDao {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Person> searchPersonInfo(PersonRequestDto dto) {
        try {
            StringBuilder query = new StringBuilder("SELECT p.* FROM person p");
            if (dto.getId() != null) {
                query.append(" WHERE p.id = :id");
            }

            if (dto.getFirstName() != null) {
                if (query.toString().contains("WHERE")) {
                    query.append(" AND p.first_name =:fistName");
                } else {
                    query.append(" WHERE p.first_name =:fistName");
                }
            }

            if (dto.getLastName() != null) {
                if (query.toString().contains("WHERE")) {
                    query.append(" AND p.last_name =:lastName");
                } else {
                    query.append(" WHERE p.last_name =:lastName");
                }
            }
            if (dto.getAge() != null) {
                if (query.toString().contains("WHERE")) {
                    query.append(" AND p.age =:age");
                } else {
                    query.append(" WHERE p.age =:age");
                }
            }
            if (dto.getGender() != null) {
                if (query.toString().contains("WHERE")) {
                    query.append(" AND p.gender =:gender");
                } else {
                    query.append(" WHERE p.gender =:gender");
                }
            }
            if (dto.getNationalCode() != null) {
                if (query.toString().contains("WHERE")) {
                    query.append(" AND p.national_code =:nationalCode");
                } else {
                    query.append(" WHERE p.national_code =:nationalCode");
                }
            }
            if (dto.getPersonalCode() != null) {
                if (query.toString().contains("WHERE")) {
                    query.append(" AND p.personal_code =:personalCode");
                } else {
                    query.append(" WHERE p.personal_code =:personalCode");
                }
            }
            if (dto.getManagerId() != null) {
                if (query.toString().contains("WHERE")) {
                    query.append(" AND p.manager_id =:managerId");
                } else {
                    query.append(" WHERE p.manager_id =:managerId");
                }
            }
            if (dto.getRoleTitle() != null) {
                if (query.toString().contains("WHERE")) {
                    query.append(" AND p.role_id IN (SELECT r.id FROM role r WHERE r.title=:role)");
                } else {
                    query.append(" WHERE p.role_id IN (SELECT r.id FROM role r WHERE r.title=:role)");
                }
            }
            Query q = entityManager.createNativeQuery(query.toString(), Person.class);
            if (dto.getId() != null) {
                q.setParameter("id", dto.getId());
            }
            if (dto.getFirstName() != null) {
                q.setParameter("fistName", dto.getFirstName());
            }
            if (dto.getLastName() != null) {
                q.setParameter("lastName", dto.getLastName());
            }
            if (dto.getAge() != null) {
                q.setParameter("age", dto.getAge());
            }
            if (dto.getGender() != null) {
                q.setParameter("gender", dto.getGender());
            }
            if (dto.getNationalCode() != null) {
                q.setParameter("nationalCode", dto.getNationalCode());
            }
            if (dto.getPersonalCode() != null) {
                q.setParameter("personalCode", dto.getPersonalCode());
            }
            if (dto.getManagerId() != null) {
                q.setParameter("managerId", dto.getManagerId());
            }
            if (dto.getRoleTitle() != null) {
                q.setParameter("role", dto.getRoleTitle());
            }

            return (ArrayList<Person>) q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

   
}
