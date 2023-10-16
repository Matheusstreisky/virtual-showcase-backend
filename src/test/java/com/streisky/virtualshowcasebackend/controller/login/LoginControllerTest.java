package com.streisky.virtualshowcasebackend.controller.login;

import com.streisky.virtualshowcasebackend.constants.AccountTestsConstants;
import com.streisky.virtualshowcasebackend.domain.account.dto.AccountDTO;
import com.streisky.virtualshowcasebackend.domain.account.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<AccountDTO> accountDTORequestJson;

    @Autowired
    private AccountRepository accountRepository;

    private static final String LOGIN_URL = "/login";

    @Test
    public void shouldReturnToken_whenCredentialsAreCorrect() throws Exception {
        accountRepository.save(AccountTestsConstants.ACCOUNT);

        MockHttpServletResponse response = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(LOGIN_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(accountDTORequestJson.write(AccountTestsConstants.ACCOUNT_DTO).getJson())
                )
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assertions.assertTrue(response.getContentAsString().contains("\"token\""));
    }

    @Test
    public void shouldReturnUnauthorized_whenBothCredentialsAreIncorrect() throws Exception {
        AccountDTO accountDTO = new AccountDTO(AccountTestsConstants.INVALID_LOGIN, AccountTestsConstants.INVALID_PASSWORD);
        shouldReturnUnauthorized(accountDTO);
    }

    @Test
    public void shouldReturnUnauthorized_whenLoginAreIncorrect() throws Exception {
        AccountDTO accountDTO = new AccountDTO(AccountTestsConstants.INVALID_LOGIN, AccountTestsConstants.PASSWORD);
        shouldReturnUnauthorized(accountDTO);
    }

    @Test
    public void shouldReturnUnauthorized_whenPasswordAreIncorrect() throws Exception {
        AccountDTO accountDTO = new AccountDTO(AccountTestsConstants.LOGIN, AccountTestsConstants.INVALID_PASSWORD);
        shouldReturnUnauthorized(accountDTO);
    }

    private void shouldReturnUnauthorized(AccountDTO accountDTO) throws Exception {
        accountRepository.save(AccountTestsConstants.ACCOUNT);

        MockHttpServletResponse response = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(LOGIN_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(accountDTORequestJson.write(accountDTO).getJson())
                )
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    public void shouldReturnBadRequest_WhenLoginIsNull() throws Exception {
        AccountDTO accountDTO = new AccountDTO(null, AccountTestsConstants.PASSWORD);
        shouldReturnBadRequest(accountDTO, "login");
    }

    @Test
    public void shouldReturnBadRequest_WhenPasswordIsNull() throws Exception {
        AccountDTO accountDTO = new AccountDTO(AccountTestsConstants.LOGIN, null);
        shouldReturnBadRequest(accountDTO, "password");
    }

    @Test
    public void shouldReturnBadRequest_WhenLoginIsEmpty() throws Exception {
        AccountDTO accountDTO = new AccountDTO("", AccountTestsConstants.PASSWORD);
        shouldReturnBadRequest(accountDTO, "login");
    }

    @Test
    public void shouldReturnBadRequest_WhenPasswordIsEmpty() throws Exception {
        AccountDTO accountDTO = new AccountDTO(AccountTestsConstants.LOGIN, "");
        shouldReturnBadRequest(accountDTO, "password");
    }

    private void shouldReturnBadRequest(AccountDTO accountDTO, String field) throws Exception {
        accountRepository.save(AccountTestsConstants.ACCOUNT);

        MockHttpServletResponse response = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post(LOGIN_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(accountDTORequestJson.write(accountDTO).getJson())
                )
                .andReturn()
                .getResponse();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        Assertions.assertTrue(response.getContentAsString().contains("\"field\""));
        Assertions.assertTrue(response.getContentAsString().contains(field));
        Assertions.assertTrue(response.getContentAsString().contains("\"message\""));
        Assertions.assertTrue(response.getContentAsString().contains("not be blank"));
    }

}
