package ch.testaz.technicalexercisevaudoise.dto;

import ch.testaz.technicalexercisevaudoise.model.Client;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String companyIdentifier;
    private Client.ClientType type;

}
