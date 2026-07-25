package com.naike.teamhub.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "teams")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_lead_id")
    private UserEntity teamLead;

    @ManyToMany
    @JoinTable(
            name = "team_membership",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private List<UserEntity> members = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<ProjectEntity> projects = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createAt;

    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TeamEntity that = (TeamEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(createAt, that.createAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createAt, updatedAt);
    }

    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
    }
}
