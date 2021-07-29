package hiberspring.repository;

import hiberspring.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e from Employee e " +
            "join Branch b on e.branch.id = b.id " +
            "where size(b.products) > 0 " +
            "GROUP BY e.id, b.id " +
            "order by e.firstName, e.lastName, LENGTH(e.position)DESC ")
    List<Employee> findAllByBranchWithProducts();
}
