package com.fanap.structure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fanap.structure.dto.request.PersonRequestDto;
import com.fanap.structure.dto.response.PersonResponseDto;
import com.fanap.structure.service.IPersonService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private IPersonService iPersonService;
    private  ErrorResponseBuilder errorResponseBuilder;

    @PostMapping("/define/person")
    public ResponseEntity<?> definePerson(@RequestBody PersonRequestDto requestDto) throws Exception {
        try {
            if (requestDto.getFirstName() != null && requestDto.getLastName() != null && requestDto.getAge() != null
                    && requestDto.getGender() != null
                    && requestDto.getNationalCode() != null && requestDto.getRoleTitle() !=null) {
                PersonResponseDto personResponseDto = iPersonService.definePerson(requestDto);
                // check if there person recird is not iserted before
                if (StringUtils.hasText(personResponseDto.getMessage())) {
                    return ResponseEntity
                            .status(HttpStatus.CONFLICT)
                            .body(personResponseDto.getMessage());

                } else {
                    // when new  person record is inserted
                    return ResponseEntity.status(HttpStatus.CREATED).body(personResponseDto.getMessage());
                }
            } else {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("تمامی فیلد ها را وارد نمایید");
            }
        } catch (Exception e) {
            return errorResponseBuilder.buildErrorResponse(e);
        }
    }

    @GetMapping("/get/person/info")
    public ResponseEntity<?> getPersonInfo(@RequestBody(required=false) PersonRequestDto dto){
        try {
            List<String> personResponseDtos = iPersonService.searchPerson(dto);
        
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(personResponseDtos);

    
            
        } catch (Exception e) {
            return errorResponseBuilder.buildErrorResponse(e);
        }
    }
    @GetMapping("/get/manager")
    public ResponseEntity<?> getPersonManagers(@RequestBody PersonRequestDto requestDto){
        try {
            List<String> personResponseDtos = iPersonService.getPersonDetails(requestDto.getId());
        
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(personResponseDtos);
  
        } catch (Exception e) {
            return errorResponseBuilder.buildErrorResponse(e);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updatePerson(@RequestBody PersonRequestDto requestDto){
        try {
            PersonResponseDto personResponseDto = iPersonService.updatePerson(requestDto);
        
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(personResponseDto.getMessage());
  
        } catch (Exception e) {
            return errorResponseBuilder.buildErrorResponse(e);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePerson(@RequestBody PersonRequestDto requestDto){
        try {
            String message= iPersonService.deletePerson(requestDto.getId());
        
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(message);
  
        } catch (Exception e) {
            return errorResponseBuilder.buildErrorResponse(e);
        }
    }

}
class ErrorResponseBuilder {
    public ResponseEntity<?> buildErrorResponse(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("خطای داخلی: " + e.getMessage());
    }
}
