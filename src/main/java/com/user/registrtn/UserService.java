package com.user.registrtn;

import com.user.registrtn.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserDoa userDoa;


    public UserResponseDto register(UserRequestDto userDto) {
        UserEntity userentity  = userDoa.register(userDto);
        return UserResponseDto.builder().id(userentity.getId()).name(userentity.getName()).age(userentity.getAge()).email(userentity.getEmail()).
                country(userentity.getCountry()).build();

    }


    public UserResponseDto getUser(Long id) {

        Optional<UserEntity> opUserEntity =  userDoa.findById(id);
        UserEntity userEntity = opUserEntity.orElseThrow(() -> { throw new UserNotFoundException("User not found with id : " +id); });

        return UserResponseDto.builder().id(userEntity.getId()).name(userEntity.getName()).age(userEntity.getAge()).email(userEntity.getEmail()).
                country(userEntity.getCountry()).build();
 }

    public List <UserResponseDto>  getAllUser() {

      List<UserEntity>  userEntityList = userDoa.findByAll();

        return userEntityList.stream().map(userEntity ->  UserResponseDto.builder().id(userEntity.getId()).name(userEntity.getName()).age(userEntity.getAge()).email(userEntity.getEmail()).
                country(userEntity.getCountry()).build()).collect(Collectors.toList());

    }

}
