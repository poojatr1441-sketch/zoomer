package com.pooja.zoomer.controller;

import com.pooja.zoomer.security.JwtFilter;
import com.pooja.zoomer.security.JwtUtil;
import com.pooja.zoomer.security.SecurityConfig;
import com.pooja.zoomer.service.OrderService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;

import org.springframework.http.HttpStatus;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.doThrow;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
@Import({SecurityConfig.class, JwtFilter.class, JwtUtil.class})
class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;
    
    @MockBean
    private OrderService orderService;

    @Test
    void shouldReturn403WhenOwnerAccessesAnotherOwnersOrder() throws Exception {

        // fake token (can be anything for now)
    	String token = jwtUtil.generateToken(
    	        "owner1@gmail.com",
    	        "OWNER"
    	);

        // service throws forbidden
        doThrow(new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You cannot access another owner's order"
        		)).when(orderService).acceptOrder(1L, "owner1@gmail.com");

        mockMvc.perform(
                put("/owner/order/1/accept")
                        .header("Authorization", "Bearer " + token)
        )
        .andExpect(status().isForbidden());
    }
}