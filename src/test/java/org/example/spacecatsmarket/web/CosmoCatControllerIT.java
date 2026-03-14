package org.example.spacecatsmarket.web;

import org.example.spacecatsmarket.featuretoggle.FeatureToggleExtension;
import org.example.spacecatsmarket.featuretoggle.annotation.DisabledFeatureToggle;
import org.example.spacecatsmarket.featuretoggle.annotation.EnabledFeatureToggle;
import org.example.spacecatsmarket.featuretoggle.exception.FeatureNotAvailableException;
import org.example.spacecatsmarket.service.CosmoCatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.example.spacecatsmarket.featuretoggle.FeatureToggles.COSMO_CAT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(FeatureToggleExtension.class)
class CosmoCatControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CosmoCatService cosmoCatService;

    @Test
    @EnabledFeatureToggle(COSMO_CAT)
    void shouldGet200() throws Exception {
        mockMvc.perform(get("/api/v1/cosmo-cats"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser
    @DisabledFeatureToggle(COSMO_CAT)
    void shouldGet404FeatureDisabled() throws Exception {
        when(cosmoCatService.getCosmoCats())
                .thenThrow(new FeatureNotAvailableException());

        mockMvc.perform(get("/api/v1/cosmo-cats"))
                .andExpect(status().isNotFound());

    }

}
