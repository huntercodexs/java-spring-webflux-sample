package com.webflux.sample.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExampleResponse {

    private String id;
    private String product;
    private String description;

}
