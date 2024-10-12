package com.guillaumcn.secretsanta.service.user;

import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.domain.request.user.SearchUserRequest;
import com.guillaumcn.secretsanta.repository.UserRepository;
import com.guillaumcn.secretsanta.repository.specification.SearchUserSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.guillaumcn.secretsanta.creator.UserCreator.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRetrievalServiceTest {

    public static final String USER_UUID = "USER_UUID";
    public static final String EMAIL = "EMAIL";
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRetrievalService userRetrievalService;

    @Test
    void userExists_findUser_shouldReturnUser() throws UserNotFoundException {
        when(userRepository.findById(USER_UUID)).thenReturn(Optional.of(createUser(USER_UUID)));

        UserEntity user = userRetrievalService.findUser(USER_UUID);

        assertEquals(USER_UUID, user.getUuid());
    }

    @Test
    void userNotExists_findUser_throwsUserEntityNotFound() {
        when(userRepository.findById(USER_UUID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userRetrievalService.findUser(USER_UUID));
    }

    @Test
    void searchGroup_callFindAllWithSpecification() {
        // GIVEN
        List<UserEntity> users = Collections.emptyList();
        when(userRepository.findAll(any(SearchUserSpecification.class))).thenReturn(users);
        SearchUserRequest searchUserRequest = SearchUserRequest.builder()
                                                               .email(EMAIL)
                                                               .firstName(FIRST_NAME)
                                                               .lastName(LAST_NAME)
                                                               .build();

        // WHEN
        List<UserEntity> results = userRetrievalService.searchUsers(searchUserRequest);

        // THEN
        assertEquals(users, results);

        ArgumentCaptor<SearchUserSpecification> argumentCaptor = ArgumentCaptor.forClass(SearchUserSpecification.class);
        verify(userRepository).findAll(argumentCaptor.capture());
        SearchUserSpecification searchUserSpecification = argumentCaptor.getValue();
        assertEquals(EMAIL, searchUserSpecification.getEmail());
        assertEquals(FIRST_NAME, searchUserSpecification.getFirstName());
        assertEquals(LAST_NAME, searchUserSpecification.getLastName());
    }
}
