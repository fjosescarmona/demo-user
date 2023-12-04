package com.demo.user.model.mapper;

import com.demo.user.api.models.Phone;
import com.demo.user.api.models.UserRequest;
import com.demo.user.api.models.UserResponse;
import com.demo.user.model.PhoneDto;
import com.demo.user.model.UserRequestDto;
import com.demo.user.model.UserResponseDto;
import com.demo.user.model.entity.PhoneEntity;
import com.demo.user.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <b>Class</b>: EntityMapper <br/>*
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
@RequiredArgsConstructor
public class EntityMapper {

    public UserEntity userRequestDtoToUserEntity(UserRequestDto userRequestDto){
        String localDateTime = LocalDateTime.now().toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnnnnn");
        LocalDateTime formattedLocalDateTime = LocalDateTime.parse(localDateTime, formatter);

        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .created(formattedLocalDateTime)
                .modified(formattedLocalDateTime)
                .lastLogin(formattedLocalDateTime)
                .token(UUID.randomUUID().toString())
                .isActive(true)
                .build();
    }

    public PhoneEntity userRequestDtoToPhoneEntity(PhoneDto phoneDto, String userId){
        return PhoneEntity.builder()
                .number(phoneDto.getNumber())
                .countryCode(phoneDto.getCountryCode())
                .cityCode(phoneDto.getCityCode())
                .user_id(userId)
                .build();
    }

    public UserResponseDto userEntityToUserResponseDto(UserEntity userEntity, List<PhoneEntity> phoneEntity){
        return UserResponseDto.builder()
                .id(userEntity.getUserId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .phones(phoneEntity.stream()
                        .map(this::phoneEntityToPhoneDto).collect(Collectors.toList()))
                .created(userEntity.getCreated().toString())
                .modified(userEntity.getModified().toString())
                .lastLogin(userEntity.getLastLogin().toString())
                .token(userEntity.getToken())
                .isActive(userEntity.getIsActive())
                .build();
    }

    private PhoneDto phoneEntityToPhoneDto(PhoneEntity phoneEntity){
        return PhoneDto.builder()
                .cityCode(phoneEntity.getCityCode())
                .countryCode(phoneEntity.getCountryCode())
                .number(phoneEntity.getNumber())
                .build();
    }

    public UserRequestDto userRequestToUserRequestDto(UserRequest userRequest){
        return UserRequestDto.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .phones(userRequest.getPhones().stream()
                        .map(this::phoneToPhoneDto).collect(Collectors.toList()))
                .build();
    }

    private PhoneDto phoneToPhoneDto(Phone phone){
        return PhoneDto.builder()
                .cityCode(phone.getCityCode())
                .countryCode(phone.getCountryCode())
                .number(phone.getNumber())
                .build();
    }

    public UserResponse userResponseDtoToUserResponse(UserResponseDto userResponseDto){
        UserResponse userResponse = new UserResponse();
        userResponse.id(userResponseDto.getId());
        userResponse.name(userResponseDto.getName());
        userResponse.email(userResponseDto.getEmail());
        userResponse.password(userResponseDto.getPassword());
        userResponse.phones(userResponseDto.getPhones().stream()
                .map(this::phoneDtoToPhone).collect(Collectors.toList()));
        userResponse.created(userResponseDto.getCreated());
        userResponse.modified(userResponseDto.getModified());
        userResponse.lastLogin(userResponseDto.getLastLogin());
        userResponse.isActive(userResponseDto.getIsActive());
        userResponse.token(userResponseDto.getToken());
        return userResponse;
    }
    private Phone phoneDtoToPhone(PhoneDto phoneDto){
        Phone phone = new Phone();
        phone.cityCode(phoneDto.getCityCode());
        phone.countryCode(phoneDto.getCountryCode());
        phone.number(phoneDto.getNumber());
        return phone;
    }
}
