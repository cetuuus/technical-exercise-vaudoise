package ch.testaz.technicalexercisevaudoise.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class ContractDTO {
    private LocalDateTime startDate; // optional, default now if null
    private LocalDateTime endDate;   // optional, may be null

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

}
