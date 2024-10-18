package com.fanap.structure.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequestDto {
    private Long id;
    private String title;
    private String position;
}
