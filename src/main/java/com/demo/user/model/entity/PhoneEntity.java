package com.demo.user.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * <b>Class</b>: PhoneEntity <br>
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
@Table(name = "phones")
@Setter
@Getter
@Builder
public class PhoneEntity {
  @Id
  @Column("phone_id")
  private Long phoneId;

  @Column("number")
  private String number;

  @Column("city_code")
  private String cityCode;

  @Column("country_code")
  private String countryCode;

  private String user_id;
}
