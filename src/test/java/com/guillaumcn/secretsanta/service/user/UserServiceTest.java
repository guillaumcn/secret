package com.guillaumcn.secretsanta.service.user;

import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.model.GroupEntity;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.user.CreateUserRequest;
import com.guillaumcn.secretsanta.domain.request.user.SearchUserRequest;
import com.guillaumcn.secretsanta.domain.request.user.UpdateUserRequest;
import com.guillaumcn.secretsanta.domain.response.user.CreateUserResponse;
import com.guillaumcn.secretsanta.domain.response.user.GetUserResponse;
import com.guillaumcn.secretsanta.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.guillaumcn.secretsanta.creator.GroupCreator.createGroup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    public static final String GROUP_UUID = "GROUP_UUID";

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

    @Test
    void testGetUser() throws UserNotFoundException {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        UserEntity user = createUser(now);

        when(userRetrievalService.findUser(USER_UUID)).thenReturn(user);

        // WHEN
        GetUserResponse response = userService.getUser(USER_UUID);

        // THEN
        assertEquals(USER_UUID, response.getUuid());
        assertEquals(USER_FIRST_NAME, response.getFirstName());
        assertEquals(USER_LAST_NAME, response.getLastName());
        assertEquals(USER_EMAIL, response.getEmail());
        assertEquals(now, response.getCreatedAt());
        assertEquals(now, response.getUpdatedAt());
        assertEquals(1, response.getGroups().size());
        assertEquals(GROUP_UUID, response.getGroups().getFirst().getUuid());
    }

    @Test
    void testSearchUser() {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        UserEntity user = createUser(now);
        SearchUserRequest searchUserRequest = SearchUserRequest
                .builder()
                .uuid(USER_UUID)
                .build();

        when(userRetrievalService.searchUsers(any(SearchUserRequest.class))).thenReturn(Collections.singletonList(user));

        // WHEN
        List<GetUserResponse> response = userService.searchUsers(searchUserRequest);

        // THEN
        assertEquals(1, response.size());
        GetUserResponse firstResponse = response.getFirst();
        assertEquals(USER_UUID, firstResponse.getUuid());
        assertEquals(USER_FIRST_NAME, firstResponse.getFirstName());
        assertEquals(USER_LAST_NAME, firstResponse.getLastName());
        assertEquals(USER_EMAIL, firstResponse.getEmail());
        assertEquals(now, firstResponse.getCreatedAt());
        assertEquals(now, firstResponse.getUpdatedAt());
        assertEquals(1, firstResponse.getGroups().size());
        assertEquals(GROUP_UUID, firstResponse.getGroups().getFirst().getUuid());
    }

    @Test
    void testDeleteUser() throws UserNotFoundException {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        UserEntity user = createUser(now);

        when(userRetrievalService.findUser(USER_UUID)).thenReturn(user);

        // WHEN
        userService.deleteUser(USER_UUID);

        // THEN
        verify(userRepository).delete(user);
    }

    @Test
    void updateAll_updateUser_shouldUpdateEachFields() throws UserNotFoundException {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        UserEntity user = createUser(now);

        when(userRetrievalService.findUser(USER_UUID)).thenReturn(user);

        final String newFirstName = "NEW_FIRST_NAME";
        final String newLastName = "NEW_LAST_NAME";
        final String newPassword = "NEW_PASSWORD";
        final String encodedNewPassword = "NEW_ENCODED_PASSWORD";
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                                                               .firstName(newFirstName)
                                                               .lastName(newLastName)
                                                               .password(newPassword)
                                                               .build();
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);

        // WHEN
        userService.updateUser(USER_UUID, updateUserRequest);

        // THEN
        assertEquals(encodedNewPassword, user.getPassword());
        assertEquals(newFirstName, user.getFirstName());
        assertEquals(newLastName, user.getLastName());
        assertNotEquals(now, user.getUpdatedAt());
    }

    @Test
    void updateNone_updateUser_shouldUpdateOnlyUpdatedAt() throws UserNotFoundException {
        // GIVEN
        LocalDateTime now = LocalDateTime.now();
        UserEntity user = createUser(now);

        when(userRetrievalService.findUser(USER_UUID)).thenReturn(user);

        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                                                               .build();

        // WHEN
        userService.updateUser(USER_UUID, updateUserRequest);

        // THEN
        assertEquals(ENCODED_PASSWORD, user.getPassword());
        assertEquals(USER_FIRST_NAME, user.getFirstName());
        assertEquals(USER_LAST_NAME, user.getLastName());
        assertNotEquals(now, user.getUpdatedAt());
    }

    private static UserEntity createUser(LocalDateTime now) {
        GroupEntity group = createGroup(GROUP_UUID);

        return UserEntity.builder()
                         .firstName(USER_FIRST_NAME)
                         .lastName(USER_LAST_NAME)
                         .password(ENCODED_PASSWORD)
                         .email(USER_EMAIL)
                         .uuid(USER_UUID)
                         .createdAt(now)
                         .updatedAt(now)
                         .groups(Collections.singletonList(group))
                         .build();
    }
}
