package com.demo.user.service.impl;

import com.demo.user.error.resolvers.UserNotFoundErrorResolver;
import com.demo.user.model.PhoneDto;
import com.demo.user.model.UserRequestDto;
import com.demo.user.model.UserResponseDto;
import com.demo.user.model.entity.PhoneEntity;
import com.demo.user.model.entity.UserEntity;
import com.demo.user.model.mapper.EntityMapper;
import com.demo.user.repository.PhoneRepository;
import com.demo.user.repository.UserRepository;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * <b>Class</b>: UserServicesImplTest <br>
 * *
 *
 * @author Flavio Suarez <br>
 *     <u>Developed by</u>: <br>
 *     <ul>
 *       <li>Flavio Suarez
 *     </ul>
 *     <ul>
 *       <li>3/12/2023 Creación de Clase.
 *     </ul>
 *
 * @version 1.0
 */
@DataR2dbcTest
@ContextConfiguration(classes = {UserServicesImplTest.R2dbcDataLoaderConfiguration.class})
@ComponentScan(basePackages = {"com.demo.user.service", "com.demo.user.model.mapper"})
@EnableR2dbcRepositories(basePackages = "com.demo.user.repository")
class UserServicesImplTest {

  @Mock private UserRepository userRepository;

  @Mock private PhoneRepository phoneRepository;

  @Mock private EntityMapper entityMapper;

  @InjectMocks private UserServicesImpl userServices;

  // @Test
  @DisplayName("Validate create user endpoint when user does not exist")
  void validateCreateUserEndpointWhenUserDoesNotExist() {

    UserRequestDto userRequestDto = userRequestDtoBuilder();
    UserEntity userEntity = userEntityBuilder();
    List<PhoneEntity> phoneEntityList = List.of(phoneEntityBuilder());

    when(entityMapper.userRequestDtoToUserEntity(userRequestDto)).thenReturn(userEntity);
    when(userRepository.save(userEntity)).thenReturn(Mono.just(userEntity));

    PhoneEntity phoneEntity = phoneEntityBuilder();
    when(entityMapper.userRequestDtoToPhoneEntity(phoneDtoBuilder(), userEntity.getUserId()))
        .thenReturn(phoneEntity);
    when(phoneRepository.saveAll(phoneEntityList)).thenReturn(Flux.just(phoneEntity));

    UserResponseDto userResponseDto = userResponseDtoBuilder();
    when(entityMapper.userEntityToUserResponseDto(any(), any())).thenReturn(userResponseDto);

    StepVerifier.create(userServices.createUser(userRequestDto))
        .expectNext(userResponseDto)
        .verifyComplete();
  }

  // @Test
  @DisplayName("Validate get user endpoint when user exist")
  void validateGetUserEndpointWhenUserExist() {

    String userId = "1";
    when(userRepository.findByUserId(userId)).thenReturn(Mono.empty());

    StepVerifier.create(userServices.getUser(userId))
        .expectError(UserNotFoundErrorResolver.class)
        .verify();

    verify(userRepository, times(1)).findByUserId(userId);
    verifyNoMoreInteractions(phoneRepository, entityMapper);
  }

  public UserResponseDto userResponseDtoBuilder() {
    return UserResponseDto.builder()
        .id("664684+694+94")
        .name("flavio")
        .email("fjosescarmona@gmail.com")
        .password("pass")
        .phones(
            List.of(PhoneDto.builder().number("934913223").cityCode("1").countryCode("51").build()))
        .created(LocalDateTime.now().toString())
        .modified(LocalDateTime.now().toString())
        .lastLogin(LocalDateTime.now().toString())
        .token("")
        .isActive(true)
        .build();
  }

  public UserRequestDto userRequestDtoBuilder() {
    return UserRequestDto.builder()
        .name("flavio")
        .email("fjosescarmona@gmail.com")
        .password("pass")
        .phones(
            List.of(PhoneDto.builder().countryCode("51").cityCode("1").number("934913223").build()))
        .build();
  }

  public UserEntity userEntityBuilder() {
    return UserEntity.builder()
        .userId("664684+694+94")
        .name("flavio")
        .email("fjosescarmona@gmail.com")
        .password("pass")
        .created(LocalDateTime.now())
        .modified(LocalDateTime.now())
        .lastLogin(LocalDateTime.now())
        .token("")
        .isActive(true)
        .build();
  }

  public PhoneEntity phoneEntityBuilder() {
    return PhoneEntity.builder()
        .number("934913223")
        .cityCode("1")
        .countryCode("51")
        .user_id("664684+694+94")
        .build();
  }

  private PhoneDto phoneDtoBuilder() {
    return PhoneDto.builder().countryCode("51").cityCode("1").number("934913223").build();
  }

  @Configuration
  static class R2dbcDataLoaderConfiguration {

    @Bean
    @Lazy(value = false)
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
      ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
      initializer.setConnectionFactory(connectionFactory);
      initializer.setDatabasePopulator(
          new CompositeDatabasePopulator(
              new ResourceDatabasePopulator(new ClassPathResource("schema.sql"))));
      return initializer;
    }
  }
}
