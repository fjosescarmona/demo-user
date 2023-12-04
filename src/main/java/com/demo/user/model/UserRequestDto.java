package com.demo.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <b>Class</b>: UserRequestMapper <br>
 * *
 *
 * @author Flavio Suarez <br>
 *     <u>Developed by</u>: <br>
 *     <ul>
 *       <li>Flavio Suarez
 *     </ul>
 *     <ul>
 *       <li>1/12/2023 Creación de Clase.
 *     </ul>
 *
 * @version 1.0
 */
@Getter
@Setter
@Builder
public class UserRequestDto {

  private String name;

  private String email;

  private String password;

  private List<PhoneDto> phones;
}
