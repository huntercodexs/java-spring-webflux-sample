package com.webflux.sample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebFluxSampleModel {

    private List<SampleProduct> sampleProduct = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SampleProduct {
        private String name;
        private double price;
        private int stock;
    }
}
