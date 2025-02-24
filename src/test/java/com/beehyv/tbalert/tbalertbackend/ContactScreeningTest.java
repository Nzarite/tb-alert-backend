package com.beehyv.tbalert.tbalertbackend;

import com.beehyv.tbalert.tbalertbackend.controller.ContactScreeningController;
import com.beehyv.tbalert.tbalertbackend.dto.input.ContactScreeningInputDTO;
import com.beehyv.tbalert.tbalertbackend.entity.ContactScreening;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ContactScreeningController.class)
public class ContactScreeningTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getContactScreening() throws Exception {

        ContactScreeningInputDTO contactScreeningInputDTO =
                ContactScreeningInputDTO.builder()
                    .contactScreeningDone(true)
                    .build();


        mockMvc.perform(
                post("/contactscreening")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(contactScreeningInputDTO))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
