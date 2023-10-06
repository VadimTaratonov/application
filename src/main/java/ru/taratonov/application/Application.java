package ru.taratonov.application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Application REST Api",
                description = "3 MVP level application",
                version = "1.0.0",
                contact = @Contact(
                        name = "Taratonov Vadim",
                        email = "taratonovv8@bk.ru",
                        url = "https://github.com/VadimTaratonov/application"
                )
        )
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
