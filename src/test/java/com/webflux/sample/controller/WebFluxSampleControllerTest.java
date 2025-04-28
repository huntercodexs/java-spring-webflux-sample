package com.webflux.sample.controller;

import com.webflux.sample.model.WebFluxSampleModel;
import com.webflux.sample.service.WebFluxSampleService;
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
public class WebFluxSampleControllerTest implements BaseControllerTest {

    WebFluxSampleService webFluxSampleService;

    @RequestMapping(method = RequestMethod.GET, value = "/test/mono")
    @ResponseBody
    public Mono<ResponseEntity<Void>> mono(ServerWebExchange exchange) {
        return webFluxSampleService.mono()
                .doFirst(() -> log.info(">>> mono started"))
                .doOnTerminate(() -> log.info(">>> mono finished"))
                .doOnSuccess(success -> log.info(">>> The mono result is {}", success))
                .doOnError(error -> log.error(">>> The mono error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/flux")
    @ResponseBody
    public Mono<ResponseEntity<Void>> flux(ServerWebExchange exchange) {
        return webFluxSampleService.flux()
                .doFirst(() -> log.info(">>> flux started"))
                .doOnTerminate(() -> log.info(">>> flux finished"))
                .collectList()
                .doOnSuccess(success -> log.info(">>> The flux result is {}", success))
                .doOnError(error -> log.error(">>> The flux error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(null));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/map")
    @ResponseBody
    public Mono<ResponseEntity<Void>> map(ServerWebExchange exchange) {
        return webFluxSampleService.map()
                .doFirst(() -> log.info(">>> map started"))
                .doOnTerminate(() -> log.info(">>> map finished"))
                .doOnSuccess(success -> log.info(">>> The map result is {}", success))
                .doOnError(error -> log.error(">>> The map error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/flatMap")
    @ResponseBody
    public Mono<ResponseEntity<Void>> flatMap(ServerWebExchange exchange) {
        log.info("[SYNC][MARKER] flatMap START");

        Mono<ResponseEntity<Void>> flatMap = webFluxSampleService.flatMap()
                .doFirst(() -> log.info("[ASYNC] >>> flatMap started"))
                .doOnTerminate(() -> log.info("[ASYNC] >>> flatMap finished"))
                .doOnSuccess(success -> log.info("[ASYNC] >>> The flatMap result is {}", success))
                .doOnError(error -> log.error("[ASYNC] >>> The flatMap error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));

        log.info("[SYNC][MARKER] flatMap STOP");

        return flatMap;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/flatMapAndZip")
    @ResponseBody
    public Mono<ResponseEntity<Void>> flatMapAndZip(ServerWebExchange exchange) {
        log.info("[SYNC][MARKER] flatMapAndZip START");

        Mono<ResponseEntity<Void>> flatMapAndZip = webFluxSampleService.flatMapAndZip()
                .doFirst(() -> log.info("[ASYNC] >>> flatMapAndZip started"))
                .doOnTerminate(() -> log.info("[ASYNC] >>> flatMapAndZip finished"))
                .doOnSuccess(success -> log.info("[ASYNC] >>> The flatMapAndZip result is {}", success))
                .doOnError(error -> log.error("[ASYNC] >>> The flatMapAndZip error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));

        log.info("[SYNC][MARKER] flatMapAndZip STOP");

        return flatMapAndZip;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/then")
    @ResponseBody
    public Mono<ResponseEntity<Void>> then(ServerWebExchange exchange) {
        return webFluxSampleService.then()
                .doFirst(() -> log.info(">>> then started"))
                .doOnTerminate(() -> log.info(">>> then finished"))
                .doOnSuccess(success -> log.info(">>> The then result is {}", success))
                .doOnError(error -> log.error(">>> The then error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/thenReturn")
    @ResponseBody
    public Mono<ResponseEntity<Object>> thenReturn(ServerWebExchange exchange) {
        return webFluxSampleService.thenReturn()
                .doFirst(() -> log.info(">>> thenReturn started"))
                .doOnTerminate(() -> log.info(">>> thenReturn finished"))
                .doOnSuccess(success -> log.info(">>> The thenReturn result is {}", success))
                .doOnError(error -> log.error(">>> The thenReturn error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/switchIfEmpty")
    @ResponseBody
    public Mono<ResponseEntity<WebFluxSampleModel>> switchIfEmpty(ServerWebExchange exchange) {
        return webFluxSampleService.switchIfEmpty()
                .doFirst(() -> log.info(">>> switchIfEmpty started"))
                .doOnTerminate(() -> log.info(">>> switchIfEmpty finished"))
                .doOnSuccess(success -> log.info(">>> The switchIfEmpty result is {}", success))
                .doOnError(error -> log.error(">>> The switchIfEmpty error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/zip")
    @ResponseBody
    public Mono<ResponseEntity<WebFluxSampleModel>> zip(ServerWebExchange exchange) {
        return webFluxSampleService.zip()
                .doFirst(() -> log.info(">>> zip started"))
                .doOnTerminate(() -> log.info(">>> zip finished"))
                .doOnSuccess(success -> log.info(">>> The zip result is {}", success))
                .doOnError(error -> log.error(">>> The zip error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/zipWith")
    @ResponseBody
    public Mono<ResponseEntity<WebFluxSampleModel>> zipWith(ServerWebExchange exchange) {
        return webFluxSampleService.zipWith()
                .doFirst(() -> log.info(">>> zipWith started"))
                .doOnTerminate(() -> log.info(">>> zipWith finished"))
                .doOnSuccess(success -> log.info(">>> The zipWith result is {}", success))
                .doOnError(error -> log.error(">>> The zipWith error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/zipWhen")
    @ResponseBody
    public Mono<ResponseEntity<WebFluxSampleModel>> zipWhen(ServerWebExchange exchange) {
        return webFluxSampleService.zipWhen()
                .doFirst(() -> log.info(">>> zipWhen started"))
                .doOnTerminate(() -> log.info(">>> zipWhen finished"))
                .doOnSuccess(success -> log.info(">>> The zipWhen result is {}", success))
                .doOnError(error -> log.error(">>> The zipWhen error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/subscribe")
    @ResponseBody
    public Mono<ResponseEntity<Void>> subscribe(ServerWebExchange exchange) {
        return webFluxSampleService.subscribe()
                .doFirst(() -> log.info(">>> subscribe started"))
                .doOnTerminate(() -> log.info(">>> subscribe finished"))
                .doOnSuccess(success -> log.info(">>> The subscribe result is {}", success))
                .doOnError(error -> log.error(">>> The subscribe error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

}
