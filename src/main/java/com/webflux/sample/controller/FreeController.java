package com.webflux.sample.controller;

import com.webflux.sample.handler.noreactor.exception.BadRequestException;
import com.webflux.sample.handler.reactor.exception.BadRequestExceptionReactor;
import com.webflux.sample.handler.reactor.exception.NotFoundExceptionReactor;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@AllArgsConstructor
public class FreeController implements BaseController {

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

    @RequestMapping(method = RequestMethod.GET, value = "/test-exception")
    @ResponseBody
    public Mono<ResponseEntity<Object>> testException(ServerWebExchange exchange) {
//        throw new CustomException("Message", 123, "8540958349058439085490", HttpStatus.BAD_REQUEST);
//        throw new BadRequestException("Sample Bad Request");
//        throw new BadRequestException("Sample Bad Request", 123);
//        throw new BadRequestException("Sample Bad Request", "8540958349058439085490");
        throw new BadRequestException("Sample Bad Request", 123, "8540958349058439085490");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test-exception-reactor")
    @ResponseBody
    public Mono<ResponseEntity<Object>> testExceptionReactor(ServerWebExchange exchange) {
//        throw new BadRequestExceptionReactor("Bad Request for Reactor");
//        throw new BadRequestExceptionReactor("Bad Request for Reactor", 123);
//        throw new BadRequestExceptionReactor("Bad Request for Reactor", "8540958349058439085490");
//        throw new BadRequestExceptionReactor("Bad Request for Reactor", 123, "8540958349058439085490");
        throw new NotFoundExceptionReactor("Not Found for Reactor", 123, "8540958349058439085490");
    }

}
