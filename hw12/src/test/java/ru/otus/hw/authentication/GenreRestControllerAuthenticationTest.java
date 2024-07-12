package ru.otus.hw.authentication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.hw.rest.GenreRestController;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.GenreService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreRestController.class)
@Import(SecurityConfiguration.class)
public class GenreRestControllerAuthenticationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @WithMockUser(username = "administrator")
    @DisplayName("Проверка доступа для пользователя administrator")
    @Test
    public void authenticateAsAdministrator() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/genres")).andExpect(status().isOk());
    }

    @DisplayName("Проверка доступа без аутентификации")
    @Test
    public void unauthenticatedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/genres")).andExpect(status().isFound());
    }
}
