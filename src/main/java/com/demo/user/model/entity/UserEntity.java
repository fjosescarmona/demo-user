package com.demo.user.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * <b>Class</b>: UserEntity <br>
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
@Table(name = "users")
@Getter
@Setter
@Builder
public class UserEntity {

  @Id
  @Column("id")
  private String id;

  @Column("user_id")
  private String userId;

  @Column("name")
  private String name;

  @Column("email")
  private String email;

  @Column("password")
  private String password;

  @Column("created")
  private LocalDateTime created;

  @Column("modified")
  private LocalDateTime modified;

  @Column("last_login")
  private LocalDateTime lastLogin;

  @Column("token")
  private String token;

  @Column("is_active")
  private Boolean isActive;
}
