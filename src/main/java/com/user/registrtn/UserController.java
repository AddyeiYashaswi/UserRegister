package com.user.registrtn;

import com.user.registrtn.exception.InvalidAgeOrCountryException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.function.UnaryOperator;

@RestController
@RequestMapping("/api/users")
@Slf4j
@Tag(name = "User Registration", description = "Operations related to user registration and retrieval")
public class UserController {

    String functionName = "";

    UnaryOperator<String> errorMessage = (input) -> "Exception in class :: UserController at method :: "+ input ;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a user if they are residing in france and age is greater than 18")
    public ResponseEntity<String> register( @Valid @RequestBody UserRequestDto userDto) {
        functionName = "register";
        long start = System.currentTimeMillis();
        log.info("register user details " + userDto.toString());
        if(userDto.getAge() > 18 && userDto.getCountry().equalsIgnoreCase("France")) {
           UserResponseDto savedUser =  userService.register(userDto);

            long end = System.currentTimeMillis();

            log.info("saved user details " + savedUser.toString());

            return new ResponseEntity<String>(savedUser.toString(), HttpStatus.CREATED);

        }
         log.info(errorMessage.apply(functionName));

         throw new InvalidAgeOrCountryException("Invalid Age or Country : Age> 18 and  should belongs to France Country");

    }

    @GetMapping("/getRegisteredUser/{id}")
    @Operation(summary = "Retrieves registered user ", description = "Retrieves registered user by id")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        functionName = "getRegisteredUser";
        long start = System.currentTimeMillis();

        log.info("Fetching user with ID: {}", id);


           Optional<UserResponseDto> user = userService.getUser(id);
           long end = System.currentTimeMillis();

         if (user.isPresent()) {
              log.info("User found: {} in {} ms", user.get(), (end - start));
                return ResponseEntity.ok(user.get());
             } else {
                log.info(errorMessage.apply(functionName) + "User not found with ID: {}", id);

                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }

    }
}
