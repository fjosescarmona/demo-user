package com.demo.user.repository;

import com.demo.user.model.entity.PhoneEntity;
import com.demo.user.model.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>Class</b>: PhoneRepository <br/>*
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
public interface PhoneRepository extends R2dbcRepository<PhoneEntity, Long> {

    @Query("SELECT * FROM phones p WHERE p.user_id = :userId")
    Flux<PhoneEntity> findByUserId(@Param("user_id") String userId);
}
