package ru.taratonov.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import ru.taratonov.application.dto.LoanApplicationRequestDTO;
import ru.taratonov.application.dto.LoanOfferDTO;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@TestPropertySource(locations = "/application-test.yml")
@SpringBootTest
class RestTemplateRequestsServiceTest {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RestTemplateRequestsService restTemplateRequestsService;
    private MockRestServiceServer mockServer;
    private final ObjectMapper mapper = new ObjectMapper();
    @Value("${custom.integration.deal.url.get.offers}")
    private String PATH_TO_DEAL_GET_OFFERS;

    @Value("${custom.integration.deal.url.choose.offer}")
    private String PATH_TO_DEAL_CHOOSE_OFFER;
    private final LoanApplicationRequestDTO loanApplicationRequest = new LoanApplicationRequestDTO()
            .setAmount(BigDecimal.valueOf(10000))
            .setTerm(9)
            .setBirthdate(LocalDate.EPOCH);
    private final LoanOfferDTO loanOffer = new LoanOfferDTO()
            .setTerm(9)
            .setRequestedAmount(BigDecimal.valueOf(10000));

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void requestToGetOffersReturnList() throws URISyntaxException, JsonProcessingException {
        List<LoanOfferDTO> listExpected = List.of(loanOffer, loanOffer);

        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(PATH_TO_DEAL_GET_OFFERS)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(listExpected)));

        List<LoanOfferDTO> list = restTemplateRequestsService.requestToGetOffers(loanApplicationRequest);

        assertEquals(listExpected.size(), list.size());
        assertEquals(loanApplicationRequest.getAmount(), list.get(0).getRequestedAmount());
        assertEquals(loanApplicationRequest.getTerm(), list.get(0).getTerm());
    }

    @Test
    void requestToGetOffersReturnNullException() throws URISyntaxException {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(PATH_TO_DEAL_GET_OFFERS)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(""));

        assertThrows(NullPointerException.class, () -> {
            restTemplateRequestsService.requestToGetOffers(loanApplicationRequest);
        });
    }

    @Test
    void chooseOffer() throws URISyntaxException {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI(PATH_TO_DEAL_CHOOSE_OFFER)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(""));

        restTemplateRequestsService.chooseOffer(loanOffer);
    }
}