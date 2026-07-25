package com.naike.teamhub.domain.entities;

import com.naike.teamhub.domain.enums.UserRole;
import com.naike.teamhub.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

//A reason why we are not using @Data is because of the hash that lommbok generates for us which may cause some problems while working with mapstruct. So we'll manually create our hashcode and equals ourself.
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String jobTitle;
    private String department;

    @ManyToMany(mappedBy = "members")
    private List<TeamEntity> teams = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(jobTitle, that.jobTitle) && Objects.equals(department, that.department) && role == that.role && status == that.status && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, jobTitle, department, role, status, createdAt, updatedAt);
    }

    /**
     * This will set the date just before persisting to the db in case the date being passed is null
     */
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
