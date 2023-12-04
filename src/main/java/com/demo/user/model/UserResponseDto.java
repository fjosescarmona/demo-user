package com.demo.user.model;

import com.demo.user.api.models.Phone;
import com.demo.user.model.entity.PhoneEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import java.util.List;

/**
 * <b>Class</b>: UserResponseMaper <br/>*
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
@Builder
@Getter
@Setter
public class UserResponseDto {

    private String id;

    private String name;

    private String email;

    private String password;

    private List<PhoneDto> phones;

    private String created;

    private String modified;

    private String lastLogin;

    private String token;

    private Boolean isActive;
}
