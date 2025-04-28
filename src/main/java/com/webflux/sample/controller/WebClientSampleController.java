package com.webflux.sample.controller;

import com.webflux.sample.model.ZipcodeModel;
import com.webflux.sample.service.WebClientSampleService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.OK;

@Log4j2
@RestController
@AllArgsConstructor
public class WebClientSampleController implements BaseController {

    WebClientSampleService webClientSampleService;

    @RequestMapping(method = RequestMethod.GET, value = "/test/webclient/get")
    @ResponseBody
    public Mono<ResponseEntity<ZipcodeModel>> get(ServerWebExchange exchange) {
        return webClientSampleService.get()
                .doFirst(() -> log.info(">>> get started"))
                .doOnTerminate(() -> log.info(">>> get finished"))
                .doOnSuccess(success -> log.info(">>> The get result is {}", success))
                .doOnError(error -> log.error(">>> The get error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/webclient/post")
    @ResponseBody
    public Mono<ResponseEntity<Void>> post(ServerWebExchange exchange) {
        return webClientSampleService.post()
                .doFirst(() -> log.info(">>> post started"))
                .doOnTerminate(() -> log.info(">>> post finished"))
                .doOnSuccess(success -> log.info(">>> The post result is {}", success))
                .doOnError(error -> log.error(">>> The post error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/webclient/put")
    @ResponseBody
    public Mono<ResponseEntity<Void>> put(ServerWebExchange exchange) {
        return webClientSampleService.put()
                .doFirst(() -> log.info(">>> put started"))
                .doOnTerminate(() -> log.info(">>> put finished"))
                .doOnSuccess(success -> log.info(">>> The put result is {}", success))
                .doOnError(error -> log.error(">>> The put error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/webclient/delete")
    @ResponseBody
    public Mono<ResponseEntity<Void>> delete(ServerWebExchange exchange) {
        return webClientSampleService.delete()
                .doFirst(() -> log.info(">>> delete started"))
                .doOnTerminate(() -> log.info(">>> delete finished"))
                .doOnSuccess(success -> log.info(">>> The delete result is {}", success))
                .doOnError(error -> log.error(">>> The delete error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

}
