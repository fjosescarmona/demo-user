package com.demo.user.repository;

import com.demo.user.model.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: UserRepository <br/>*
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
@Repository
public interface UserRepository extends R2dbcRepository<UserEntity, Long> {

    @Query("SELECT * FROM users u WHERE u.user_id = :userId")
    Mono<UserEntity> findByUserId(@Param("user_id") String userId);
}
