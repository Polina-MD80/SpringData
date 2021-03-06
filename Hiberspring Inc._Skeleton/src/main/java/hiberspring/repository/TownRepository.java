package hiberspring.repository;

import hiberspring.domain.entity.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
    boolean existsByName(String town);
    Town findByName(String name);


}
