package com.example.loginsystem.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CorsTest {

        @Autowired
        private MockMvc mockMvc;

        @Test
        public void testCorsHeadersForApiEndpoint() throws Exception {
            mockMvc.perform(
                            options("/api/test")
                                    .header("Origin", "http://localhost:3000")
                                    .header("Access-Control-Request-Method", "GET")
                    )
                    .andExpect(status().isOk())
                    .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"))
                    .andExpect(header().string("Access-Control-Allow-Credentials", "true"))
                    .andExpect(header().string("Access-Control-Allow-Headers", "Authorization,Content-Type,Accept"))
                    .andExpect(header().string("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE"));
        }
}