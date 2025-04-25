package com.webflux.sample.controller;

import com.webflux.sample.exception.BadRequestExceptionReactor;
import com.webflux.sample.exception.InternalErrorExceptionReactor;
import com.webflux.sample.exception.NotFoundExceptionReactor;
import com.webflux.sample.handler.noreactor.exception.BadRequestException;
import com.webflux.sample.handler.noreactor.exception.CustomException;
import com.webflux.sample.service.ExceptionService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
public class ExceptionController implements BaseController {

    ExceptionService exceptionService;

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/custom")
    @ResponseBody
    public Mono<ResponseEntity<Object>> testExceptionCustom(ServerWebExchange exchange) {
        throw new CustomException("Message", 123, "8540958349058439085490", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/bad-request")
    @ResponseBody
    public Mono<ResponseEntity<Object>> testExceptionBadRequest(ServerWebExchange exchange) {
        throw new BadRequestException("Sample Bad Request", 123, "8540958349058439085490");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/bad-request-reactor")
    @ResponseBody
    public Mono<ResponseEntity<Object>> testExceptionBadRequestReactor(ServerWebExchange exchange) {
        throw new BadRequestExceptionReactor("Bad Request for Reactor", 123, "8540958349058439085490");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/not-found-reactor1")
    @ResponseBody
    public Mono<ResponseEntity<Object>> testExceptionNotFoundReactor(ServerWebExchange exchange) {
        throw new NotFoundExceptionReactor("Not Found for Reactor", 123, "8540958349058439085490");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/internal-exception-reactor")
    @ResponseBody
    public Mono<ResponseEntity<Object>> testExceptionInternalErrorReactor(ServerWebExchange exchange) {
        throw new InternalErrorExceptionReactor("Internal Error for Reactor", 123, "8540958349058439085490");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/runtime-exception-reactor")
    @ResponseBody
    public Mono<ResponseEntity<Object>> testExceptionRuntimeReactor(ServerWebExchange exchange) {
        throw new RuntimeException("Runtime Exception Java Lang");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/illegal-argument")
    @ResponseBody
    public Mono<ResponseEntity<Object>> illegalArgumentException(ServerWebExchange exchange) {
        return exceptionService.illegalArgumentException()
                .doFirst(() -> log.info(">>> illegalArgumentException started"))
                .doOnTerminate(() -> log.info(">>> illegalArgumentException finished"))
                .doOnSuccess(success -> log.info(">>> The illegalArgumentException result is {}", success))
                .doOnError(error -> log.error(">>> The illegalArgumentException error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/null-pointer")
    @ResponseBody
    public Mono<ResponseEntity<Object>> nullPointerException(ServerWebExchange exchange) {
        return exceptionService.nullPointerException()
                .doFirst(() -> log.info(">>> nullPointerException started"))
                .doOnTerminate(() -> log.info(">>> nullPointerException finished"))
                .doOnSuccess(success -> log.info(">>> The nullPointerException result is {}", success))
                .doOnError(error -> log.error(">>> The nullPointerException error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/illegal-access")
    @ResponseBody
    public Mono<ResponseEntity<Object>> illegalAccessException(ServerWebExchange exchange) {
        return exceptionService.illegalAccessException()
                .doFirst(() -> log.info(">>> illegalAccessException started"))
                .doOnTerminate(() -> log.info(">>> illegalAccessException finished"))
                .doOnSuccess(success -> log.info(">>> The illegalAccessException result is {}", success))
                .doOnError(error -> log.error(">>> The illegalAccessException error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/http-exception")
    @ResponseBody
    public Mono<ResponseEntity<Object>> httpException(ServerWebExchange exchange) {
        return exceptionService.httpException()
                .doFirst(() -> log.info(">>> httpException started"))
                .doOnTerminate(() -> log.info(">>> httpException finished"))
                .doOnSuccess(success -> log.info(">>> The httpException result is {}", success))
                .doOnError(error -> log.error(">>> The httpException error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/index-out-of-bounds")
    @ResponseBody
    public Mono<ResponseEntity<Object>> indexOutOfBoundsException(ServerWebExchange exchange) {
        return exceptionService.indexOutOfBoundsException()
                .doFirst(() -> log.info(">>> indexOutOfBoundsException started"))
                .doOnTerminate(() -> log.info(">>> indexOutOfBoundsException finished"))
                .doOnSuccess(success -> log.info(">>> The indexOutOfBoundsException result is {}", success))
                .doOnError(error -> log.error(">>> The indexOutOfBoundsException error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/not-found-reactor")
    @ResponseBody
    public Mono<ResponseEntity<Object>> notFoundExceptionReactor(ServerWebExchange exchange) {
        return exceptionService.notFoundExceptionReactor()
                .doFirst(() -> log.info(">>> notFoundExceptionReactor started"))
                .doOnTerminate(() -> log.info(">>> notFoundExceptionReactor finished"))
                .doOnSuccess(success -> log.info(">>> The notFoundExceptionReactor result is {}", success))
                .doOnError(error -> log.error(">>> The notFoundExceptionReactor error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/not-found-swagger")
    @ResponseBody
    public Mono<ResponseEntity<Object>> notFoundException(ServerWebExchange exchange) {
        return exceptionService.notFoundException()
                .doFirst(() -> log.info(">>> notFoundException started"))
                .doOnTerminate(() -> log.info(">>> notFoundException finished"))
                .doOnSuccess(success -> log.info(">>> The notFoundException result is {}", success))
                .doOnError(error -> log.error(">>> The notFoundException error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/error")
    @ResponseBody
    public Mono<ResponseEntity<Object>> error(ServerWebExchange exchange) {
        return exceptionService.error()
                .doFirst(() -> log.info(">>> error started"))
                .doOnTerminate(() -> log.info(">>> error finished"))
                .doOnSuccess(success -> log.info(">>> The error result is {}", success))
                .doOnError(error -> log.error(">>> The error error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/internal-error")
    @ResponseBody
    public Mono<ResponseEntity<Object>> internalError(ServerWebExchange exchange) {
        return exceptionService.internalError()
                .doFirst(() -> log.info(">>> internalError started"))
                .doOnTerminate(() -> log.info(">>> internalError finished"))
                .doOnSuccess(success -> log.info(">>> The internalError result is {}", success))
                .doOnError(error -> log.error(">>> The internalError error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test/exception/internal-error-exception")
    @ResponseBody
    public Mono<ResponseEntity<Object>> internalErrorException(ServerWebExchange exchange) {
        return exceptionService.internalErrorException()
                .doFirst(() -> log.info(">>> internalErrorException started"))
                .doOnTerminate(() -> log.info(">>> internalErrorException finished"))
                .doOnSuccess(success -> log.info(">>> The internalErrorException result is {}", success))
                .doOnError(error -> log.error(">>> The internalErrorException error is {}", String.valueOf(error)))
                .map(body -> ResponseEntity.status(OK).body(body));
    }

}
