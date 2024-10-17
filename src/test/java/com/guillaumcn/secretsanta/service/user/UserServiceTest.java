package com.guillaumcn.secretsanta.service.user;

import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.user.CreateUserRequest;
import com.guillaumcn.secretsanta.domain.response.user.CreateUserResponse;
import com.guillaumcn.secretsanta.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    public static final String USER_EMAIL = "user.email@email.email";
    public static final String USER_LAST_NAME = "LAST_NAME";
    public static final String USER_FIRST_NAME = "FIRST_NAME";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String ENCODED_PASSWORD = "ENCODED_PASSWORD";
    public static final String USER_UUID = "USER_UUID";

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRetrievalService userRetrievalService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser() {
        // GIVEN
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                                                               .email(USER_EMAIL)
                                                               .lastName(USER_LAST_NAME)
                                                               .firstName(USER_FIRST_NAME)
                                                               .password(USER_PASSWORD)
                                                               .build();
        when(passwordEncoder.encode(USER_PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(UserEntity.class))).thenAnswer((invocation) -> {
            UserEntity argument = invocation.getArgument(0);
            return UserEntity.builder()
                             .lastName(argument.getLastName())
                             .firstName(argument.getFirstName())
                             .email(argument.getEmail())
                             .password(argument.getPassword())
                             .uuid(USER_UUID)
                             .build();
        });

        // WHEN
        CreateUserResponse response = userService.createUser(createUserRequest);

        // THEN
        ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(argumentCaptor.capture());
        UserEntity userEntityArgument = argumentCaptor.getValue();
        assertNull(userEntityArgument.getUuid());
        assertEquals(USER_EMAIL, userEntityArgument.getEmail());
        assertEquals(USER_FIRST_NAME, userEntityArgument.getFirstName());
        assertEquals(USER_LAST_NAME, userEntityArgument.getLastName());
        assertEquals(ENCODED_PASSWORD, userEntityArgument.getPassword());
        assertEquals(USER_UUID, response.getUuid());
    }
}
