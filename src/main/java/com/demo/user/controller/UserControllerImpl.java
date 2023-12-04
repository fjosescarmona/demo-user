package com.demo.user.controller;

import com.demo.user.api.ApiUtil;
import com.demo.user.api.CreateUserApiDelegate;
import com.demo.user.api.GetUserApiDelegate;
import com.demo.user.api.models.UserRequest;
import com.demo.user.api.models.UserResponse;
import com.demo.user.model.UserResponseDto;
import com.demo.user.model.mapper.EntityMapper;
import com.demo.user.model.UserRequestDto;
import com.demo.user.service.UserServices;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * <b>Class</b>: UserControllerImpl <br/>*
 *
 * @author Flavio Suarez <br/>
 * <u>Developed by</u>: <br/>
 * <ul>
 * <li>Flavio Suarez</li>
 * </ul>
 * <ul>
 * <li>1/12/2023 Creación de Clase.</li>
 * </ul>
 * @version 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class UserControllerImpl implements CreateUserApiDelegate, GetUserApiDelegate {

    @Autowired
    private EntityMapper entityMapper;
    private UserServices userServices;

    @Override
    public Mono<ResponseEntity<UserResponse>> createUser(String security,
                                                         Mono<UserRequest> userRequest,
                                                         ServerWebExchange exchange) {

        return userRequest
                .doOnNext(rq -> log.info("input: {}", rq))
                .map(entityMapper::userRequestToUserRequestDto)
                .flatMap(userServices::createUser)
                .map(entityMapper::userResponseDtoToUserResponse)
                .doOnSuccess(register -> log.info("Register success: {}", register))
                .map(ResponseEntity::ok);

    }

    @Override
    public Mono<ResponseEntity<UserResponse>> getUser(String security,
                                                       String id,
                                                       ServerWebExchange exchange) {
        return userServices.getUser(id)
                .map(entityMapper::userResponseDtoToUserResponse)
                .doOnSuccess(register -> log.info("Register success: {}", register))
                .map(ResponseEntity::ok);

    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return CreateUserApiDelegate.super.getRequest();
    }
}
