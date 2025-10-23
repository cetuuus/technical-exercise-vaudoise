package ch.testaz.technicalexercisevaudoise.dto;

import ch.testaz.technicalexercisevaudoise.model.Client;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ClientDTO {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9 .()+-]{7,20}$", message = "Invalid phone number format")
    private String phone;

    private LocalDate birthDate;

    private String companyIdentifier;

    @NotNull
    private Client.ClientType type;

}
