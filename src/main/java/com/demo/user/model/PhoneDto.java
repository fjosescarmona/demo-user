package com.demo.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * <b>Class</b>: PhoneDto <br>
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
@Builder
@Getter
@Setter
public class PhoneDto {

  private String number;

  private String cityCode;

  private String countryCode;
}
