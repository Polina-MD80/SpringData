package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import softuni.exam.models.entity.Plane;

public interface PlaneRepository extends JpaRepository<Plane, Long> {
 boolean existsByRegisterNumber(String registerNumber);
 Plane findByRegisterNumber(String registerNumber);
}
