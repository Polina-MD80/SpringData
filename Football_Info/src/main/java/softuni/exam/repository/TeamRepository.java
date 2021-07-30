package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.domain.entities.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
 Boolean existsByName(String name);
 Team findByName(String name);
}
