package com.user.registrtn;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testRegisterUser_Success() throws Exception {
    String userJson = "{\"name\":\"Alice\",\"age\":25,\"country\":\"France\"}";

    mockMvc.perform(post("/api/users/register")
                 .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
            .andExpect(status().isCreated());
}


    @Test
    public void testRegisterUser_InvalidAge() throws Exception {
        String userJson = "{\"name\":\"Bob\",\"age\":17,\"country\":\"France\"}";

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest());
    }
}
