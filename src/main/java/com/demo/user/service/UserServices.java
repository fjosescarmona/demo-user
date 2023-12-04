package com.demo.user.service;

import com.demo.user.model.UserRequestDto;
import com.demo.user.model.UserResponseDto;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: UserServices <br/>*
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
public interface UserServices {

    Mono<UserResponseDto> createUser(UserRequestDto userRequest);
    Mono<UserResponseDto> getUser(String id);
}
