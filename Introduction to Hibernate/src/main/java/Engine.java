import entities.*;

import javax.persistence.*;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class Engine implements Runnable {
    private final EntityManager entityManager;
    private final BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.println("Enter task number:");
        int taskNumber = 0;
        try {
            taskNumber = Integer.parseInt(reader.readLine());
            switch (taskNumber) {
                case 2 -> taskTwo();
                case 3 -> taskThree();
                case 4 -> taskFour();
                case 5 -> taskFive();
                case 6 -> taskSix();
                case 7 -> taskSeven();
                case 8 -> taskEight();
                case 9 -> taskNine();
                case 10 -> taskTen();
                case 11 -> taskEleven();
                case 12 -> taskTwelve();
                case 13 -> taskThirteen();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

    }

    private void taskThirteen() throws IOException {
        //Задачата не работи за градове,
        // като Seattle защото не мога за сега да задам адекватни каскади между
        //Self референциите.
        System.out.println("Enter town name:");
        String townName = reader.readLine();
        Town town = entityManager.createQuery("select t from Town t where t.name = :param", Town.class)
                .setParameter("param", townName)
                .getSingleResult();
        List<Address> addressesToDelete = entityManager.createQuery("select a from  Address  a " +
                "where a.town = :id", Address.class)
                .setParameter("id", town)
                .getResultList();
        entityManager.getTransaction().begin();
        addressesToDelete.forEach(entityManager::remove);
        entityManager.remove(town);
        entityManager.getTransaction().commit();
        System.out.printf("%d address in %s deleted", addressesToDelete.size(), townName);
    }

    private void taskEleven() throws IOException {
        System.out.println("Enter first name abbreviation:");
        String abr = reader.readLine();

        List<Employee> employee = entityManager.createQuery(
                "select e from Employee e where e.firstName  LIKE CONCAT(:par ,'%') ", Employee.class)
                .setParameter("par", abr)
                .getResultList();
        employee.forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                e.getFirstName(),
                e.getLastName(),
                e.getJobTitle(),
                e.getSalary()));

    }

    private void taskNine() {
        List<Project> resultList = entityManager.createQuery("select p from Project p " +
                "order by p.startDate DESC", Project.class).setMaxResults(10).getResultList();
        resultList.stream().sorted(Comparator.comparing(Project::getName))
                .forEach(r -> System.out.printf("Project name: %s%n" +
                                " Project Description: %s%n" +
                                " Project Start Date:%s%n" +
                                " Project End Date: %s%n", r.getName(),
                        r.getDescription().substring(0, 35) + "...",
                        r.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")),
                        r.getEndDate()==null? null : r.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"))));
    }

    private void taskTwelve() {
        List<Object[]> resultList = entityManager.createNativeQuery("select d.name, Max(e.salary) as 'max_salary' from departments d\n" +
                "join employees e on d.department_id = e.department_id\n" +
                "group by d.department_id\n" +
                "having max_salary not between 30000 and 70000").getResultList();
        resultList.forEach(o -> System.out.println(o[0].toString() + " " + o[1].toString()));

    }

    private void taskTen() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Employee e " +
                "set e.salary = e.salary * 1.12 where " +
                "e.department.id in :ids")
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .executeUpdate();
        entityManager.getTransaction().commit();
        List<Employee> employees = entityManager.createQuery("select e from Employee e where e.department.id in :ids", Employee.class)
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .getResultList();
        employees.forEach(e -> System.out.printf("%s %s ($%.2f)%n", e.getFirstName(), e.getLastName(), e.getSalary()));
    }

    private void taskEight() throws IOException {
        System.out.println("Enter employee id:");
        int id = Integer.parseInt(reader.readLine());
        Employee employee = entityManager.find(Employee.class, id);
        System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getJobTitle());
        employee.getProjects().forEach(p -> System.out.println("      " + p.getName()));


    }

    private void taskSeven() {
        List<Address> addresses = entityManager.createQuery("select a from  Address a " +
                "order by a.employees.size desc", Address.class)
                .setMaxResults(10)
                .getResultList();

        addresses
                .forEach(a -> System.out.printf("%s, %s - %d employees%n",
                        a.getText(), a.getTown() == null ? "unknown" : a.getTown(), a.getEmployees().size()));

    }

    private void taskSix() throws IOException {
        System.out.println("Enter employee last name:");
        String lastname = reader.readLine();
        Employee employee = entityManager.createQuery("select e from Employee e " +
                "where e.lastName = :last_name", Employee.class)
                .setParameter("last_name", lastname)
                .getSingleResult();

        Address address = createAddress("Vitoshka 15");
        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();


    }

    private Address createAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);
        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();
        return address;
    }

    private void taskFive() {
        List<Employee> employees = entityManager.createQuery("SELECT e from  Employee e " +
                "where e.department.name = :d_name " +
                "order by e.salary, e.id", Employee.class)
                .setParameter("d_name", "Research and Development")
                .getResultList();
        employees.forEach(e -> System.out.printf("%s %s from %s - $%.2f%n", e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary()));
    }

    private void taskFour() {
        List<Employee> employees = entityManager.createQuery("select e from Employee e " +
                "where e.salary > :min_salary", Employee.class)
                .setParameter("min_salary", BigDecimal.valueOf(50000L))
                .getResultList();
        employees.forEach(e -> System.out.println(e.getFirstName()));
    }

    private void taskThree() throws IOException {
        System.out.println("Enter employee full name:");
        String[] fullName = reader.readLine().split("\\s+");
        String firstName = fullName[0];
        String lastName = fullName[1];
        Long count = entityManager.createQuery("select count(e) from Employee e where " +
                "e.firstName = : f_name AND e.lastName = :l_name", Long.class)
                .setParameter("f_name", firstName)
                .setParameter("l_name", lastName)
                .getSingleResult();
        if (count > 0) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }

    }

    private void taskTwo() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("update Town t " +
                "set t.name = upper(t.name) " +
                "where length(t.name) <= 5");

        int affectedRows = query.executeUpdate();
        System.out.println(affectedRows);
        entityManager.getTransaction().commit();
    }
}
