package com.demo.user.service.impl;

import com.demo.user.error.resolvers.UserAlreadyExistErrorResolver;
import com.demo.user.error.resolvers.GenericErrorResolver;
import com.demo.user.error.resolvers.UserNotFoundErrorResolver;
import com.demo.user.model.UserRequestDto;
import com.demo.user.model.UserResponseDto;
import com.demo.user.model.entity.PhoneEntity;
import com.demo.user.model.entity.UserEntity;
import com.demo.user.model.mapper.EntityMapper;
import com.demo.user.repository.PhoneRepository;
import com.demo.user.repository.UserRepository;
import com.demo.user.service.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <b>Class</b>: UserServicesImpl <br/>*
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
@Component
@AllArgsConstructor
public class UserServicesImpl implements UserServices {

    private UserRepository userRepository;
    private PhoneRepository phoneRepository;
    @Autowired
    private EntityMapper entityMapper;

    @Override
    public Mono<UserResponseDto> createUser(UserRequestDto userRequestDto) {

        return userRepository.save(entityMapper.userRequestDtoToUserEntity(userRequestDto))
                .flatMap(user -> {
                    List<PhoneEntity> phoneEntities = userRequestDto.getPhones().stream()
                            .map(phone -> entityMapper.userRequestDtoToPhoneEntity(phone, user.getUserId()))
                            .collect(Collectors.toList());

                    return phoneRepository.saveAll(phoneEntities)
                            .collectList()
                            .map(savedPhones -> entityMapper.userEntityToUserResponseDto(user, savedPhones))
                            .onErrorResume(error -> Mono.error(new GenericErrorResolver("Error Creating user")));
                }).onErrorResume(error -> Mono.error(
                        (error instanceof DuplicateKeyException)
                                ? new UserAlreadyExistErrorResolver("This email already exist")
                                : new GenericErrorResolver("Error creating user: " +error.getMessage())
                ));
    }

    @Override
    public Mono<UserResponseDto> getUser(String id) {
        return Mono.zip(findUser(id), findPhoneList(id), (userEntity, phoneEntityList) ->
                entityMapper.userEntityToUserResponseDto(userEntity, phoneEntityList));

    }

    private Mono<UserEntity> findUser(String id){
        return userRepository.findByUserId(id)
                .switchIfEmpty(Mono.error(new UserNotFoundErrorResolver("User not found")));
    }
    private Mono<List<PhoneEntity>> findPhoneList(String id){
        return phoneRepository.findByUserId(id)
                .collect(Collectors.toList())
                .onErrorResume(error -> Mono.error(new GenericErrorResolver("Error getting phone")));
    }
}
