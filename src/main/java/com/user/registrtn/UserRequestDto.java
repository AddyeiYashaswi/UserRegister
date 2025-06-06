package com.user.registrtn;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "First name is required")
    private String name;

    @NotNull(message = "age is required")
    private Integer age;

    private String email;

    @NotBlank(message = "country is required")
    private String country;
}
