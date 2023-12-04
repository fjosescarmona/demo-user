package com.demo.user.error.resolvers;

/**
 * <b>Class</b>: UserAlreadyExistException <br>
 * *
 *
 * @author Flavio Suarez <br>
 *     <u>Developed by</u>: <br>
 *     <ul>
 *       <li>Flavio Suarez
 *     </ul>
 *     <ul>
 *       <li>2/12/2023 Creación de Clase.
 *     </ul>
 *
 * @version 1.0
 */
public class UserAlreadyExistErrorResolver extends RuntimeException {
  public UserAlreadyExistErrorResolver(String message) {
    super(message);
  }
}
