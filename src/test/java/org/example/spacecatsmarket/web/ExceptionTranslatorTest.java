package org.example.spacecatsmarket.web;


import org.example.spacecatsmarket.featuretoggle.annotation.DisabledFeatureToggle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.example.spacecatsmarket.featuretoggle.FeatureToggles.COSMO_CAT;

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionTranslatorTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void handleProductNotFoundException_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Product Not Found"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/api/v1/products/999"));
    }


    @Test
    void handleMethodArgumentNotValid_shouldReturnBadRequest() throws Exception {
        String invalidRequest = "{\"name\":\"\"}";

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/api/v1/products"));
    }

    @Test
    @DisabledFeatureToggle(COSMO_CAT)
    void handleFeatureNotAvailableException_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/cosmo-cats"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Feature Not Available"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.path").value("/api/v1/cosmo-cats"));
    }
}
