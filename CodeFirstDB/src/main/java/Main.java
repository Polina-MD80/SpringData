import entity.Medicament;
import entity.Patient;
import entity.Product;
import entity.Sale;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence
                .createEntityManagerFactory("test_code_first")
                .createEntityManager();
        //-----------------------------------------------------------
        //Test for task 2:
         entityManager.getTransaction().begin();

        Product pr2 = new Product();
        pr2.setName("Test2Product");
        pr2.setPrice(BigDecimal.TEN);
        pr2.setQuantity(3.0);

        Sale sale = new Sale();
        sale.setProduct(pr2);

        pr2.getSales().add(sale);
        entityManager.persist(pr2);
        entityManager.getTransaction().commit();

        pr2.getSales().forEach(s-> System.out.println(s.getId()));
        // --------------------------------------------------
        //Test for task 5:
        Medicament medicament = new Medicament();
        medicament.setName("alabala");

        Patient patient = new Patient();


        patient.setAddress("somewhere");
        patient.setFirstName("Ivan");
        patient.setLastName("Ivanov");
        patient.setHasMedicalInsurance(true);

        patient.getMedications().add(medicament);


        entityManager.getTransaction().begin();
        entityManager.persist(patient);
        medicament.setPatient(patient);
        entityManager.persist(medicament);
        entityManager.getTransaction().commit();


        patient.getMedications().forEach(m-> System.out.println(m.getName()));


    }
}
