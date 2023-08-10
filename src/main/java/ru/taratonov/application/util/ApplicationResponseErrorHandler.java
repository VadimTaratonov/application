package ru.taratonov.application.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import ru.taratonov.application.dto.ErrorDTO;
import ru.taratonov.application.exception.IllegalDataFromOtherMsException;

import java.io.IOException;

@Component
public class ApplicationResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorDTO errorDTO = objectMapper.readValue(response.getBody(), ErrorDTO.class);
        throw new IllegalDataFromOtherMsException(errorDTO.getMsg());
    }
}
