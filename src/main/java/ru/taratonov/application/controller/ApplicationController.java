package ru.taratonov.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.taratonov.application.dto.ErrorDTO;
import ru.taratonov.application.dto.LoanApplicationRequestDTO;
import ru.taratonov.application.dto.LoanOfferDTO;
import ru.taratonov.application.service.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@Tag(name = "Application Controller", description = "You can get 4 loan offers and after choose on of them")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    @Operation(summary = "Get loan offers", description = "Allows to get 4 loan offers for person")
    @ApiResponse(
            responseCode = "200",
            description = "List of offers received!",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LoanOfferDTO.class)))
    @ApiResponse(
            responseCode = "400",
            description = "Prescoring failed",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class)))
    public List<LoanOfferDTO> getPossibleLoanOffers(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Loan request",
            content = @Content(schema = @Schema(implementation = LoanApplicationRequestDTO.class)))
                                                    @RequestBody
                                                    @Valid LoanApplicationRequestDTO loanApplicationRequest) {
        return applicationService.getOffers(loanApplicationRequest);
    }

    @PutMapping("/offer")
    @Operation(summary = "Choose one offer", description = "Allows to choose one of four offers")
    @ApiResponse(
            responseCode = "200",
            description = "The offer is selected",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseEntity.class)))
    @ApiResponse(
            responseCode = "400",
            description = "Fail to choose offer",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class)))
    public ResponseEntity<HttpStatus> getOneOfTheOffers(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Selected loan offer",
            content = @Content(schema = @Schema(implementation = LoanOfferDTO.class)))
                                                        @RequestBody
                                                        @Valid LoanOfferDTO loanOfferDTO) {
        applicationService.chooseOffer(loanOfferDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
