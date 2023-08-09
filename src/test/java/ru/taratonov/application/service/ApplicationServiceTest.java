package ru.taratonov.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.taratonov.application.dto.LoanApplicationRequestDTO;
import ru.taratonov.application.dto.LoanOfferDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private RestTemplateRequestsService restTemplateRequestsService;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void getOffers() {
        LoanOfferDTO expectedOffer = new LoanOfferDTO();
        List<LoanOfferDTO> expectedOffers = List.of(expectedOffer, expectedOffer);
        LoanApplicationRequestDTO loanApplicationRequest = new LoanApplicationRequestDTO();
        when(restTemplateRequestsService.requestToGetOffers(loanApplicationRequest))
                .thenReturn(expectedOffers);

        List<LoanOfferDTO> actualOffers = applicationService.getOffers(loanApplicationRequest);

        assertEquals(expectedOffers.size(), actualOffers.size());
        assertEquals(expectedOffer, actualOffers.get(0));
        verify(restTemplateRequestsService, times(1)).requestToGetOffers(loanApplicationRequest);
    }

    @Test
    void chooseOffer() {
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();

        applicationService.chooseOffer(loanOfferDTO);

        verify(restTemplateRequestsService, times(1)).chooseOffer(loanOfferDTO);
    }
}