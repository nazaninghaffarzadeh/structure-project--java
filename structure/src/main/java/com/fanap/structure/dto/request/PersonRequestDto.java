package com.fanap.structure.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRequestDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private Integer age;
    private String nationalCode;
    private String personalCode;
    private Long managerId;
    private String roleTitle;
    private String rolePosition;

}
