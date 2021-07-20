package JDBSdemo;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class main {
    public static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static String DB_URL = "jdbc:mysql://localhost:3306/soft_uni";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter DB username (<Enter> for 'root'): ");
        String username = scanner.nextLine().trim();
        username = username.length() > 0 ? username : "root";

        System.out.println("Enter DB password (<Enter> for 'root'): ");
        String password = scanner.nextLine().trim();
        password = password.length() > 0 ? password : "root";

        // 1.Load DB driver


        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.printf("Database driver: '%s' is not found", DB_DRIVER);
            System.exit(0);
        }
        System.out.println("DB Driver logged successfully.");

        // 2. Connect to DB
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);

        // parameter
        System.out.println("Enter minimal salary(<Enter> for '40000'):");
        String salaryStr = scanner.nextLine().trim();
        salaryStr = salaryStr.length() > 0 ? salaryStr : "40000";
        double salary = 40000;
        try {
            salary = Double.parseDouble(salaryStr);
        } catch (NumberFormatException ex) {
            System.err.printf("Invalid salary: %s", salaryStr);
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, props)) {
            PreparedStatement preparedSt = conn.prepareStatement("SELECT * from employees where salary > ?");

            //4 Execute statement with parameter

            preparedSt.setDouble(1, salary);
            ResultSet rs = preparedSt.executeQuery();

            // 5. Print Result
            while (rs.next()) {
                System.out.printf("| %10d | %-15.15s | %-15.15s | %10.2f%n",
                        rs.getLong("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDouble("salary"));
            }
//        } catch (SQLException throwables) {
//            System.err.printf("Cannot Connect to DB '%s'%n.",DB_URL);
//            System.exit(0);
//
//        }
//        System.out.println("DB connection created successfully.");
//
//
//        }
//        //3 Create prepare statement


//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//     // Close connection
//        try {
//            conn.close();
        } catch (SQLException throwables) {
            System.err.printf("Cannot close DB connection: %s%n", throwables.getMessage());
        }
    }
}

