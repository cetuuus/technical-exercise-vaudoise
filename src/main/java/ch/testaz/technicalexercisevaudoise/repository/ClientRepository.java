package ch.testaz.technicalexercisevaudoise.repository;

import ch.testaz.technicalexercisevaudoise.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
