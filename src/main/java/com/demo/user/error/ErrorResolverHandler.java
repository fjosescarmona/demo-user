package com.demo.user.error;

import com.demo.user.api.models.UserError;
import com.demo.user.error.resolvers.GenericErrorResolver;
import com.demo.user.error.resolvers.UserAlreadyExistErrorResolver;
import com.demo.user.error.resolvers.UserNotFoundErrorResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: ExceptionController <br/>*
 *
 * @author Flavio Suarez <br/>
 * <u>Developed by</u>: <br/>
 * <ul>
 * <li>Flavio Suarez</li>
 * </ul>
 * <ul>
 * <li>2/12/2023 Creación de Clase.</li>
 * </ul>
 * @version 1.0
 */
@RestControllerAdvice
public class ErrorResolverHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(GenericErrorResolver.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<UserError> handleGenericErrorResolver(GenericErrorResolver ex) {
    return Mono.just(new UserError().mensaje(ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyExistErrorResolver.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<UserError> handleUserAlreadyExistErrorResolver(UserAlreadyExistErrorResolver ex) {
        return Mono.just(new UserError().mensaje(ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundErrorResolver.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<UserError> handleUserNotFoundErrorResolver(UserNotFoundErrorResolver ex) {
        return Mono.just(new UserError().mensaje(ex.getMessage()));
    }
}
