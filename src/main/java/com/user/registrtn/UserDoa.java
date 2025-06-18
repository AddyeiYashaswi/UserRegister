package com.user.registrtn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDoa {
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private UserRepository userRepository;

    public UserEntity register(UserRequestDto userDto) {
        UserEntity userEntity = Optional.ofNullable(userDto).map(userdto -> modelMapper.map(userdto, UserEntity.class)).orElse(null);
        return userRepository.save(userEntity);
    }

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }


    public List<UserEntity> findByAll() {
        return userRepository.findAll();
    }
}
