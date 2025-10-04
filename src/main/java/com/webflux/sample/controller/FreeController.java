package com.webflux.sample.controller;

import com.webflux.sample.service.FreeService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@AllArgsConstructor
public class FreeController implements BaseController {

    FreeService freeService;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/test-value-path/{testValue}"
    )
    public String returnValuePath(
            @Parameter(name = "testValue", description = "", required = true, in = ParameterIn.PATH)
            @PathVariable("testValue") String testValue
    ) {
        return "The value for tests is: " + testValue;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/generate/password/{value}"
    )
    public String generatePassword(
            @Parameter(name = "value", description = "", required = true, in = ParameterIn.PATH)
            @PathVariable("value") String value
    ) {
        //admin = $2a$10$G294fUFha2LCoNxUjV1APem4pHD75l26dxAHqzf1nlxDlqy27BoAW
        //huntercodexs = $2a$10$uBf3yVM6xG2fiOA8bxlHf.30zE6964RFktl7x0HhK6u.0lzvZ.Teq
        return "The password generated is: " + passwordEncoder.encode(value);
    }

}
