package ch.testaz.technicalexercisevaudoise.repository;

import ch.testaz.technicalexercisevaudoise.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("SELECT c FROM Contract c WHERE c.client.id = :clientId AND c.startDate <= :now AND (c.endDate IS NULL OR :now <= c.endDate)")
    List<Contract> findActiveByClientId(@Param("clientId") Long clientId, @Param("now") LocalDateTime now);

    @Query("SELECT c FROM Contract c WHERE c.client.id = :clientId AND c.startDate <= :now AND (c.endDate IS NULL OR :now <= c.endDate) AND c.updatedDate BETWEEN :from AND :to")
    List<Contract> findActiveByClientIdAndUpdatedBetween(@Param("clientId") Long clientId,
                                                         @Param("now") LocalDateTime now,
                                                         @Param("from") LocalDateTime from,
                                                         @Param("to") LocalDateTime to);

    @Query("SELECT COALESCE(SUM(c.amount), 0) FROM Contract c WHERE c.client.id = :clientId AND c.startDate <= :now AND (c.endDate IS NULL OR :now <= c.endDate)")
    BigDecimal sumAmountOfActiveByClientId(@Param("clientId") Long clientId, @Param("now") LocalDateTime now);

    List<Contract> findByClient_Id(Long clientId);
}
