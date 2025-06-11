package com.user.registrtn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDoa userDoa;


    public UserResponseDto register(UserRequestDto userDto) {
        UserEntity userentity  = userDoa.register(userDto);
        return UserResponseDto.builder().id(userentity.getId()).name(userentity.getName()).age(userentity.getAge()).email(userentity.getEmail()).
                country(userentity.getCountry()).build();

    }


    public Optional<UserResponseDto> getUser(Long id) {

        UserEntity userentity =  userDoa.findById(id).get();
        return Optional.of(UserResponseDto.builder().id(userentity.getId()).name(userentity.getName()).age(userentity.getAge()).email(userentity.getEmail()).
                country(userentity.getCountry()).build());
 }

}
