package com.guillaumcn.secretsanta.service.user;

import com.guillaumcn.secretsanta.domain.exception.UserNotFoundException;
import com.guillaumcn.secretsanta.domain.model.UserEntity;
import com.guillaumcn.secretsanta.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.guillaumcn.secretsanta.creator.UserCreator.createUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRetrievalServiceTest {

    public static final String USER_UUID = "USER_UUID";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRetrievalService userRetrievalService;

    @Test
    public void userExists_findUser_shouldReturnUser() throws UserNotFoundException {
        when(userRepository.findById(USER_UUID)).thenReturn(Optional.of(createUser(USER_UUID)));

        UserEntity user = userRetrievalService.findUser(USER_UUID);

        assertEquals(USER_UUID, user.getUuid());
    }

    @Test
    public void userNotExists_findUser_throwsUserEntityNotFound() {
        when(userRepository.findById(USER_UUID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userRetrievalService.findUser(USER_UUID));
    }
}
