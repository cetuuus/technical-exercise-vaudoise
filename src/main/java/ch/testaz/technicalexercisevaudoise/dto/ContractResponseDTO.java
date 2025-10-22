package ch.testaz.technicalexercisevaudoise.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class ContractResponseDTO {
    private Long id;
    private Long clientId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal amount;
    // updatedDate intentionally omitted (internal only)

}
