package ru.taratonov.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.taratonov.application.dto.LoanApplicationRequestDTO;
import ru.taratonov.application.dto.LoanOfferDTO;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationService {

    private final RestTemplateRequestsService restTemplateRequestsService;

    public List<LoanOfferDTO> getOffers(LoanApplicationRequestDTO loanApplicationRequest) {
        log.info("Get loanApplicationRequestDTO and create new client with name - {}, surname - {}",
                loanApplicationRequest.getFirstName(), loanApplicationRequest.getLastName());
        return restTemplateRequestsService.requestToGetOffers(loanApplicationRequest);
    }

    public void chooseOffer(LoanOfferDTO loanOfferDTO) {
        log.info("Selected offer is {}", loanOfferDTO);
        restTemplateRequestsService.chooseOffer(loanOfferDTO);
    }
}
