package com.demo.user.utils;

import com.demo.user.model.PhoneDto;
import com.demo.user.model.UserResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <b>Class</b>: TestUtils <br/>*
 *
 * @author Flavio Suarez <br/>
 * <u>Developed by</u>: <br/>
 * <ul>
 * <li>Flavio Suarez</li>
 * </ul>
 * <ul>
 * <li>3/12/2023 Creación de Clase.</li>
 * </ul>
 * @version 1.0
 */
public class TestUtils {

    public UserResponseDto userResponseDtoBuilder(){
    return UserResponseDto.builder()
        .id("664684+694+94")
        .name("flavio")
        .email("fjosescarmona@gmail.com")
        .password("pass")
        .phones(List.of(PhoneDto.builder()
                .number("934913223")
                .cityCode("1")
                .countryCode("51")
                .build()))
        .created(LocalDateTime.now().toString())
        .modified(LocalDateTime.now().toString())
        .lastLogin(LocalDateTime.now().toString())
        .token("")
        .isActive(true)
        .build();
    }
}
