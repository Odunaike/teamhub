package com.naike.teamhub.data.repositories;

import com.naike.teamhub.domain.entities.TeamEntity;
import com.naike.teamhub.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, UUID> {
    boolean existsByName(String name);
    boolean existsByMembersId(UUID membersId);
}
