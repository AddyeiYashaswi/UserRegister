package com.user.registrtn;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Slf4j
@Tag(name = "User Registration", description = "Operations related to user registration and retrieval")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a user if they are residing in france and age is greater than 18")
    public ResponseEntity<String> register( @Valid @RequestBody UserRequestDto userDto) {

        long start = System.currentTimeMillis();
        log.info("register user details " + userDto.toString());
        if(userDto.getAge() > 18 && userDto.getCountry().equalsIgnoreCase("France")) {
           UserResponseDto savedUser =  userService.register(userDto);

            long end = System.currentTimeMillis();

            log.info("saved user details " + savedUser.toString());
            return new ResponseEntity<String>(userDto.toString(), HttpStatus.CREATED);
        }
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/getAllRegisteredUser/{id}")
    @Operation(summary = "Retrieves registered user ", description = "Retrieves registered user by id")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
       if(id == null) {
           id = (long) 1;
       }
        long start = System.currentTimeMillis();
        log.info("Fetching user with ID: {}", id);

           Optional<UserResponseDto> user = userService.getUser(id);
           long end = System.currentTimeMillis();

         if (user.isPresent()) {
              log.info("User found: {} in {} ms", user.get(), (end - start));
                return ResponseEntity.ok(user.get());
             } else {
                log.warn("User not found with ID: {}", id);
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

    }
}
