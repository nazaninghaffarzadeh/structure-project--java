package com.fanap.structure.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fanap.structure.dto.request.PersonRequestDto;
import com.fanap.structure.dto.response.PersonResponseDto;
import com.fanap.structure.dto.response.RoleResponseDto;
import com.fanap.structure.persistance.dao.IPersonDao;
import com.fanap.structure.persistance.dao.IPersonGlobalDao;
import com.fanap.structure.persistance.entity.Person;
import com.fanap.structure.service.IPersonService;
import com.fanap.structure.service.IRoleService;


@Service

public class PersonServiceImpl implements IPersonService {

    private static final String CEO_ROLE = "مدیرعامل";
    private static final String DUPLICATE_NATIONAL_CODE_MESSAGE = "کد ملی تکراری است";
    private static final String ROLE_ALREADY_REGISTERED_MESSAGE = "شخص با این سمت قبلا ثبت شده است و امکان ثبت دوباره آن وجود ندارد";
    private static final String PERSON_CREATED_SUCCESSFULLY = "شخص با موفقیت ثبت شد";
    @Autowired
    private IPersonDao iPersonDao;
    @Autowired
    private IRoleService iRoleService;

     @Autowired
    private IPersonGlobalDao personGlobalDao;

    public PersonResponseDto definePerson(PersonRequestDto requestDto) throws Exception {
        PersonResponseDto responseDto = new PersonResponseDto();
        try {

            Person personEntity = iPersonDao.findByNationalCode(requestDto.getNationalCode());
            if (personEntity != null) {
                responseDto.setMessage(DUPLICATE_NATIONAL_CODE_MESSAGE);

            } else {
                RoleResponseDto roleResponseDto = iRoleService.roleId(requestDto.getRoleTitle());
                if (roleResponseDto.getId() != null) {
                    if (requestDto.getRoleTitle().equals(CEO_ROLE)) {
                        personEntity = iPersonDao.findByRoleId(roleResponseDto.getId());
                        if (personEntity != null) {
                            responseDto.setMessage(ROLE_ALREADY_REGISTERED_MESSAGE);
                        } else {
                            responseDto = createPersonEntity(requestDto, roleResponseDto);
                        }
                    } else {
                        responseDto = createPersonEntity(requestDto, roleResponseDto);
                    }
                } else {
                    responseDto.setMessage(roleResponseDto.getMessage());
                }
            }

            return responseDto;
        } catch (Exception e) {
            responseDto.setMessage("ثبت شخص با خطا مواجه شد");
            return responseDto;
        }
    }

    // method for inserting person into database
    private PersonResponseDto createPersonEntity(PersonRequestDto requestDto, RoleResponseDto roleResponseDto) {
        Person personEntity = new Person();
        PersonResponseDto response = new PersonResponseDto();
        BeanUtils.copyProperties(requestDto, personEntity);
        personEntity.setPersonalCode(generatePersonalCode(requestDto.getNationalCode()));
        personEntity.setRoleId(roleResponseDto.getId());
        iPersonDao.saveAndFlush(personEntity);
        BeanUtils.copyProperties(personEntity, response);
        response.setMessage(PERSON_CREATED_SUCCESSFULLY);
        return response;
    }

    private String generatePersonalCode(String nationalCode) {
        // it adds 1 to the end of national code
        return nationalCode + 1;
    }
    

    public List<String> searchPerson(PersonRequestDto requestDto) {
        List<String> personResponseDtos = new ArrayList<>();
        List<Person> personEntities = personGlobalDao.searchPersonInfo(requestDto);

        for (Person personEntity : personEntities) {
            PersonResponseDto personResponseDto = new PersonResponseDto();
            BeanUtils.copyProperties(personEntity, personResponseDto);

            // Retrieve the role title based on the roleId
            RoleResponseDto roleResponseDto = iRoleService.roleTitle(personEntity.getRoleId());
            personResponseDto.setRoleTitle(roleResponseDto.getTitle()); // assuming RoleResponseDto has a getTitle()
            personResponseDto.setRolePosition(roleResponseDto.getPosition()); // method
            personResponseDto.setMessage(generateResponseFormatForSearch(personResponseDto));
            personResponseDtos.add(personResponseDto.getMessage());
        }

        return personResponseDtos;
    }
    public String generateResponseFormatForSearch( PersonResponseDto personResponseDto){
        String personDetail=personResponseDto.getFirstName()+" "+ personResponseDto.getLastName()+" ( "+personResponseDto.getPersonalCode()+" -"+personResponseDto.getRolePosition()+
        "-"+personResponseDto.getRoleTitle()+" )";
        return personDetail;
    }

    public List<String> getPersonDetails(Long personId) {
        Person personEntity = iPersonDao.findById(personId).orElseThrow();
        List<String> responseDtos = new ArrayList<>();
    
        PersonResponseDto personResponseDto = new PersonResponseDto();
        BeanUtils.copyProperties(personEntity, personResponseDto);
    
        // Retrieve the role title based on the roleId
        RoleResponseDto roleResponseDto = iRoleService.roleTitle(personEntity.getRoleId());
        personResponseDto.setRoleTitle(roleResponseDto.getTitle());
        personResponseDto.setRolePosition(roleResponseDto.getPosition());
        personResponseDto.setMessage(generateResponseFormatForDetail(personResponseDto));
        responseDtos.add(personResponseDto.getMessage());
    
        Long managerId = personEntity.getManagerId();
        while (managerId != null) {
            personEntity = iPersonDao.findById(managerId).orElseThrow();
            personResponseDto = new PersonResponseDto();
            BeanUtils.copyProperties(personEntity, personResponseDto);
    
            // Retrieve the role title based on the roleId
            roleResponseDto = iRoleService.roleTitle(personEntity.getRoleId());
            personResponseDto.setRoleTitle(roleResponseDto.getTitle());
            personResponseDto.setRolePosition(roleResponseDto.getPosition());
            personResponseDto.setMessage(generateResponseFormatForDetail(personResponseDto));
            responseDtos.add(personResponseDto.getMessage());
    
            managerId = personEntity.getManagerId();
        }
    
        return responseDtos;
    }
    public String generateResponseFormatForDetail( PersonResponseDto personResponseDto){
        String personDetail=personResponseDto.getFirstName()+" "+ personResponseDto.getLastName()+" شماره پرسنلی :"+personResponseDto.getPersonalCode()+"  -سمت : "+personResponseDto.getRolePosition()+
        " -نام سمت : "+personResponseDto.getRoleTitle();
        return personDetail;
    }
    public PersonResponseDto updatePerson(PersonRequestDto personRequestDto){
        PersonResponseDto responseDto = new PersonResponseDto();
        try {

            Person personEntity = iPersonDao.findById(personRequestDto.getId()).orElse(null);
            if (personEntity != null) {
                RoleResponseDto roleResponseDto = iRoleService.roleId(personRequestDto.getRoleTitle());
                Person personUpdate=new Person();
                personUpdate.setRoleId(roleResponseDto.getId());
                BeanUtils.copyProperties(personRequestDto, personUpdate);
                iPersonDao.save(personUpdate);
                responseDto.setMessage("شخص با موفقیت به روز رسانی شد");
            } else {
             responseDto.setMessage("شخص وجود ندارد");
            }

            return responseDto;
        } catch (Exception e) {
            responseDto.setMessage("ویرایش شخص با خطا مواجه شد");
            return responseDto;
        }
    }
    public  String deletePerson(Long personId){
        try {
            String message;
            Person personEntity = iPersonDao.findById(personId).orElse(null);
                if (personEntity != null) {
                    
                    iPersonDao.delete(personEntity);
                    message= "شخص با موفقیت به حذف شد";
                } else {
                 message="شخص وجود ندارد";
                }
            return message;
        } catch (Exception e) {
            return "حذف شخص با خطا مواجه شد";
             
        }
       }
        

}
