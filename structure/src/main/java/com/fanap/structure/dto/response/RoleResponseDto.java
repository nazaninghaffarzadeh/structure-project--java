package com.fanap.structure.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponseDto {
    private Long id;
    private String title;
    private String position;
    private String message;
}
