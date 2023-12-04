package com.demo.user.controller;

import com.demo.user.api.models.UserRequest;
import com.demo.user.api.models.UserResponse;
import com.demo.user.model.UserRequestDto;
import com.demo.user.model.UserResponseDto;
import com.demo.user.model.mapper.EntityMapper;
import com.demo.user.service.UserServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

/**
 * <b>Class</b>: UserControllerImplTest <br/>*
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
@ExtendWith(MockitoExtension.class)
class UserControllerImplTest {

    @Mock
    private EntityMapper entityMapper;

    @Mock
    private UserServices userServices;

    @InjectMocks
    private UserControllerImpl userController;

    @Test
    @DisplayName("Validate create user endpoint when user does not exist")
    void validateCreateUserEndpointWhenUserDoesNotExist() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        UserRequestDto userRequestDto = userRequestDtoBuilder();
        UserResponseDto userResponseDto = userResponseDtoBuilder();
        when(entityMapper.userRequestToUserRequestDto(userRequest)).thenReturn(userRequestDto);
        when(userServices.createUser(userRequestDto)).thenReturn(Mono.just(userResponseDto));
        when(entityMapper.userResponseDtoToUserResponse(userResponseDto)).thenReturn(new UserResponse());

        // Act
        StepVerifier.create(userController.createUser("security", Mono.just(userRequest), null))
                .expectNextMatches(responseEntity -> responseEntity.getStatusCode().is2xxSuccessful())
                .verifyComplete();

        // Assert
        verify(entityMapper, times(1)).userRequestToUserRequestDto(userRequest);
        verify(userServices, times(1)).createUser(userRequestDto);
        verify(entityMapper, times(1)).userResponseDtoToUserResponse(userResponseDto);
    }

    @Test
    @DisplayName("Validate get user endpoint when user exist")
    void validateGetUserEndpointWhenUserExist() {
        // Arrange
        String userId = "1";
        UserResponseDto userResponseDto = userResponseDtoBuilder();
        when(userServices.getUser(userId)).thenReturn(Mono.just(userResponseDto));
        when(entityMapper.userResponseDtoToUserResponse(userResponseDto)).thenReturn(new UserResponse());

        // Act
        userController.getUser("security", userId, null).block();

        // Assert
        StepVerifier.create(userController.getUser("security", userId, null))
                .expectNextMatches(responseEntity -> responseEntity.getStatusCode().is2xxSuccessful())
                .verifyComplete();

    }

    public UserResponseDto userResponseDtoBuilder(){
        return UserResponseDto.builder()
                .build();
    }
    public UserRequestDto userRequestDtoBuilder(){
        return UserRequestDto.builder()
                .build();
    }

}