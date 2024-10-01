package com.guillaumcn.secretsanta.model;

import com.guillaumcn.secretsanta.domain.response.CreateUserResponse;
import com.guillaumcn.secretsanta.domain.response.SearchUserResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "group_user",
            joinColumns = {@JoinColumn(name = "user_uuid")},
            inverseJoinColumns = {@JoinColumn(name = "group_uuid")}
    )
    @Builder.Default
    private List<GroupEntity> groups = new ArrayList<>();

    public CreateUserResponse toCreateUserResponse() {
        return CreateUserResponse.builder()
                                 .uuid(uuid)
                                 .build();
    }

    public SearchUserResponse toSearchUserResponse() {
        return SearchUserResponse.builder()
                                 .uuid(uuid)
                                 .email(email)
                                 .lastName(lastName)
                                 .firstName(firstName)
                                 .createdAt(createdAt)
                                 .updatedAt(updatedAt)
                                 .build();
    }
}
