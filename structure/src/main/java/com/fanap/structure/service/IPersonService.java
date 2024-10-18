package com.fanap.structure.service;

import java.util.List;

import com.fanap.structure.dto.request.PersonRequestDto;
import com.fanap.structure.dto.response.PersonResponseDto;


public interface IPersonService {
    
    PersonResponseDto definePerson(PersonRequestDto requestDto) throws Exception;
    List<String> searchPerson(PersonRequestDto requestDto);
    List<String> getPersonDetails(Long personId);
    PersonResponseDto updatePerson(PersonRequestDto personRequestDto);
    String deletePerson(Long personId);
    

}
