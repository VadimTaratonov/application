package ru.taratonov.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.taratonov.application.dto.LoanApplicationRequestDTO;
import ru.taratonov.application.dto.LoanOfferDTO;
import ru.taratonov.application.util.ApplicationResponseErrorHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class RestTemplateRequestsService {
    private final RestTemplate restTemplate;

    @Value("${path.to.deal.get.offers}")
    private String PATH_TO_DEAL_GET_OFFERS;

    @Value("${path.to.deal.choose.offer}")
    private String PATH_TO_DEAL_CHOOSE_OFFER;

    @Autowired
    public RestTemplateRequestsService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .errorHandler(new ApplicationResponseErrorHandler())
                .build();
    }

    public List<LoanOfferDTO> requestToGetOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        log.debug("Request to get offer to deal with {}", loanApplicationRequestDTO);
        ResponseEntity<LoanOfferDTO[]> responseEntity =
                restTemplate.postForEntity(PATH_TO_DEAL_GET_OFFERS, loanApplicationRequestDTO, LoanOfferDTO[].class);
        return Arrays.stream(Objects.requireNonNull(responseEntity.getBody())).toList();
    }

    public void chooseOffer(LoanOfferDTO loanOfferDTO) {
        log.debug("Request to select offer to deal with {}", loanOfferDTO);
        restTemplate.put(PATH_TO_DEAL_CHOOSE_OFFER, loanOfferDTO);
    }
}



