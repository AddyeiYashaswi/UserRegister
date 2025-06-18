package com.user.registrtn;

import com.user.registrtn.exception.InvalidAgeOrCountryException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

@RestController
@RequestMapping("/api/users")
@Slf4j
@Tag(name = "User Registration", description = "Operations related to user registration and retrieval")
public class UserController {

    String functionName = "";

    UnaryOperator<String> errorMessage = (input) -> "Exception in class :: UserController at method :: " + input;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a user if they are residing in france and age is greater than 18")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<String> register(@Valid @RequestBody UserRequestDto userDto) {
        functionName = "register";
        long start = System.currentTimeMillis();
        log.info("register user details " + userDto.toString());
        if (userDto.getAge() > 18 && userDto.getCountry().equalsIgnoreCase("France")) {
            UserResponseDto savedUser = userService.register(userDto);

            long end = System.currentTimeMillis();

            log.info("saved user details " + savedUser.toString());

            return new ResponseEntity<String>(savedUser.toString(), HttpStatus.CREATED);

        }
        log.info(errorMessage.apply(functionName));

        throw new InvalidAgeOrCountryException("Invalid Age or Country : Age> 18 and  should belongs to France Country");

    }

    @GetMapping("/getRegisteredUser/{id}")
    @Operation(summary = "Retrieves registered user ", description = "Retrieves registered user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - the user was not found ")
    })
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        functionName = "getRegisteredUser";

        long start = System.currentTimeMillis();

        log.info("Fetching user with ID: {}", id);

        UserResponseDto user = userService.getUser(id);

        long end = System.currentTimeMillis();

            log.info("User found: {} in {} ms", user, (end - start));

            return ResponseEntity.ok(user);

        }

    @GetMapping("/getAllRegisteredUsers")
    @Operation(summary = "Retrieves All Registered Users ", description = "Retrieves All Registered Users ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved All Users"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<UserResponseDto>> getAllUser() {

        long start = System.currentTimeMillis();

        log.info("Fetching users details ");

        List<UserResponseDto> user = userService.getAllUser();

        long end = System.currentTimeMillis();

        log.info("Users found: {} in {} ms", user, (end - start));

        return ResponseEntity.ok(user);

    }

}
