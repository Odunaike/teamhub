package com.naike.teamhub.services;

import com.naike.teamhub.data.repositories.TeamRepository;
import com.naike.teamhub.data.repositories.UserRepository;
import com.naike.teamhub.domain.dtos.user.CreateUserDto;
import com.naike.teamhub.domain.dtos.user.UpdateUserDto;
import com.naike.teamhub.domain.entities.TeamEntity;
import com.naike.teamhub.domain.entities.UserEntity;
import com.naike.teamhub.domain.enums.UserStatus;
import com.naike.teamhub.domain.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserEntity createUser(CreateUserDto userDto) {
        if(userRepository.existsByEmail(userDto.getEmail()))
            throw new DataIntegrityViolationException("Email already in use");

        UserEntity userEntity = userMapper.toEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity savedUserEntity = userRepository.save(userEntity);

        if(!(userDto.getTeamIds() == null) && !userDto.getTeamIds().isEmpty()){
            List<TeamEntity> teams = teamRepository.findAllById(userDto.getTeamIds());

            teams.forEach(team -> {
                team.getMembers().add(savedUserEntity);
            });
            teamRepository.saveAll(teams);
        }

        return savedUserEntity;
    }

    public UserEntity getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new  UsernameNotFoundException(""));
    }
    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserEntity updateUser (UUID id, UpdateUserDto userDto){

        return userRepository.findById(id)
                .map(user -> {
                    Optional.ofNullable(userDto.getFirstName()).ifPresent(user::setFirstName);
                    Optional.ofNullable(userDto.getLastName()).ifPresent(user::setLastName);
                    Optional.ofNullable(userDto.getEmail()).ifPresent(user::setEmail);
                    Optional.ofNullable(userDto.getJobTitle()).ifPresent(user::setJobTitle);
                    Optional.ofNullable(userDto.getDepartment()).ifPresent(user::setDepartment);
                    user.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new  UsernameNotFoundException("No user with this id " + id + " found"));
    }

    public void deactivateUser(UUID id){
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("No user with this id " + id + " found"));
        user.setStatus(UserStatus.INACTIVE);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }


}
