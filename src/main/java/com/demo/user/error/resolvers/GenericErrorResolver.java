package com.demo.user.error.resolvers;

/**
 * <b>Class</b>: ExceptionHandler <br/>*
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
public class GenericErrorResolver extends RuntimeException {
    public GenericErrorResolver(String message) {
        super(message);
    }
}
