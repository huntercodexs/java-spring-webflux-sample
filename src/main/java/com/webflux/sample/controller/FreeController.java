package com.webflux.sample.controller;

import com.webflux.sample.service.FreeService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@AllArgsConstructor
public class FreeController implements BaseController {

    FreeService freeService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/test-value-path/{testValue}"
    )
    public void testValuePath(
            @Parameter(name = "testValue", description = "", required = true, in = ParameterIn.PATH)
            @PathVariable("testValue") String testValue
    ) {
        System.out.println(testValue);
    }

}
