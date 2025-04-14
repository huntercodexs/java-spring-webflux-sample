package com.webflux.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PersonDetailsDto {

    private String id;
    private String address;
    private String phone;

}
