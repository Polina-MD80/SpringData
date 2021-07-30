package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import softuni.exam.domain.entities.Player;

import java.math.BigDecimal;
import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("select p FROM Player p " +
            "where p.team.name = :name " +
            "order by p.id")
    List<Player> findByTeam(@Param(value = "name") String name);


    List<Player> findAllBySalaryGreaterThanOrderBySalaryDesc(BigDecimal value);
}
