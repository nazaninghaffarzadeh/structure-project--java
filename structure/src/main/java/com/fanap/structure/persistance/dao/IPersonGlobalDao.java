package com.fanap.structure.persistance.dao;

import java.util.List;
import com.fanap.structure.dto.request.PersonRequestDto;
import com.fanap.structure.persistance.entity.Person;


public interface IPersonGlobalDao{

    List<Person> searchPersonInfo(PersonRequestDto dto);
 
    
}
