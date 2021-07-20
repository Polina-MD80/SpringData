import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class main {
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "minions_db";
    private static Connection conn;
    private static BufferedReader reader;

    public static void main(String[] args) throws SQLException, IOException {

        reader = new BufferedReader(new InputStreamReader(System.in));
        // get connection
        conn = getConnection(reader);

        System.out.println("Enter task number: ");
        String task = reader.readLine();
        switch (task) {
            case "2":
                taskTwo();
                break;
            case "3":
                taskThree("villains");
                break;
            case "4":
                taskFour();
                break;
            case "5":
                taskFive();
                break;
            case "6":
                taskSix();
                break;
            case "7":
                taskSeven();
                break;
            case "8":
                taskEight();
                break;
            case "9":
                taskNine();
                break;
            default:
                System.out.printf("There is not existing task %s in JDBC exercise set.", task);

        }


    }

    private static void taskSix() throws IOException, SQLException {
        System.out.println("Enter villain ID:");
        int id = Integer.parseInt(reader.readLine());
        String villainName = findEntityNameByID(id, "villains");
        if (villainName == null) {
            System.out.println("No such villain was found");
        } else {
            PreparedStatement psMinions = conn.prepareStatement("DELETE from minions_villains where villain_id = ?");
            psMinions.setInt(1, id);
            int numberOfReleasedMinions = psMinions.executeUpdate();
            PreparedStatement psVillain = conn.prepareStatement("delete from villains where id = ?");
            psVillain.setInt(1, id);
            psVillain.executeUpdate();
            System.out.printf("Villain: %s was deleted%n", villainName);
            System.out.printf("%d minions released", numberOfReleasedMinions);

        }

    }

    private static void taskNine() throws IOException, SQLException {
//        This is the create procedure script
//        create
//                definer = root@localhost procedure usp_get_older(IN minion_id int)
//        begin
//        update minions
//        set age = age + 1
//        where id = minion_id;
//
//        end;

        System.out.println("Enter minion id:");
        int id = Integer.parseInt(reader.readLine());
        CallableStatement cs = conn.prepareCall("CALL usp_get_older(?)");
        cs.setInt(1, id);
        cs.executeQuery();

        PreparedStatement ps = conn.prepareStatement("select name, age from minions where id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        System.out.printf("%s %d %n", rs.getString("name"), rs.getInt("age"));

    }

    private static void taskEight() throws IOException, SQLException {
        System.out.println("Enter minions IDs separated by space:");
        int[] ids = Arrays.stream(reader.readLine().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        PreparedStatement ps1 = conn.prepareStatement("update minions\n" +
                "set name = LOWER(name)\n" +
                "where id = ?;");
        PreparedStatement ps2 = conn.prepareStatement("update minions\n" +
                "set age = age +1\n" +
                "where id = ?;");
        PreparedStatement ps3 = conn.prepareStatement("select name, age from minions where id = ?");
        Set<String> results = new LinkedHashSet<>();

        for (int e : ids) {
            ps1.setInt(1, e);
            ps2.setInt(1, e);
            ps1.executeUpdate();
            ps2.executeUpdate();
            ps3.setInt(1, e);
            ResultSet rs = ps3.executeQuery();
            rs.next();
            results.add(String.format("%s %d", rs.getString("name"), rs.getInt("age")));

        }

        results.forEach(System.out::println);

    }

    private static void taskSeven() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select name from minions");
        ResultSet rs = ps.executeQuery();

        List<String> results = new ArrayList<>();

        while (rs.next()) {
            results.add(rs.getString("name"));
        }
        for (int i = 0; i < results.size() / 2; i++) {
            System.out.println(results.get(i));
            System.out.println(results.get(results.size() - 1 - i));
        }

    }

    private static void taskFive() throws IOException, SQLException {
        System.out.println("Enter country name:");
        String countryName = reader.readLine();

        PreparedStatement ps = conn.prepareStatement("UPDATE  towns " +
                "set name = upper(name) where country = ?");

        ps.setString(1, countryName);

        int affectedRows = ps.executeUpdate();

        if (affectedRows == 0) {
            System.out.println("No town names were affected.");
        } else {
            System.out.printf("%d town names were affected.%n", affectedRows);
            PreparedStatement psTowns = conn.prepareStatement("select name from towns where country = ?");
            psTowns.setString(1, countryName);

            ResultSet rs = psTowns.executeQuery();
            Set<String> results = new LinkedHashSet<>();
            while (rs.next()) {
                results.add(rs.getString("name"));
            }
            System.out.println(Arrays.toString(results.toArray()));
        }
    }

    private static void taskFour() throws IOException, SQLException {
        System.out.println("Enter data for minion and guru:");
        String[] minionData = reader.readLine().split("\\s+");
        String[] villainData = reader.readLine().split("\\s+");
        insertDataForMinion(minionData);
        insertDataForVillain(villainData);
        System.out.printf("Successfully added %s to be minion of %s.", minionData[1], villainData[1]);

    }

    private static void insertDataForVillain(String[] guruData) throws SQLException {
        String villainName = guruData[1];
        int villainId;
       ResultSet rsVill = checkForExistingEntity("villains",villainName);
       if(!rsVill.isBeforeFirst()){
           insertIntoTable("villains", villainName);
           ResultSet rsVillAfterInsert = checkForExistingEntity("villains", villainName);
           rsVillAfterInsert.next();
           villainId = rsVillAfterInsert.getInt("id");
           PreparedStatement psAdEvil = conn.prepareStatement("update villains set evilness_factor = 'evil' where id = ?");
           psAdEvil.setInt(1,villainId);
           psAdEvil.executeUpdate();
           System.out.printf("Villain %s was added to the database.%n", villainName);
       }
       else {
           rsVill.next();
           villainId = rsVill.getInt("id");
       }

        PreparedStatement ps = conn.prepareStatement("update minions_villains\n" +
                "set villain_id = ?\n" +
                "where villain_id is null ;");
        ps.setInt(1, villainId);






    }

    private static void insertDataForMinion(String[] minionData) throws SQLException {
        String minionName = minionData[1];
        int age = Integer.parseInt(minionData[2]);
        String townName = minionData[3];
        int townId;
        int minionId;


        ResultSet rsTownId = checkForExistingEntity("towns", townName);
        if (!rsTownId.isBeforeFirst()) {
            insertIntoTable("towns", townName);
            System.out.printf("Town %s was added to the database.%n", townName);
            ResultSet rsTownAfterInsert = checkForExistingEntity("towns", townName);
            rsTownAfterInsert.next();
            townId = rsTownAfterInsert.getInt("id");

        } else {
            rsTownId.next();
            townId = rsTownId.getInt("id");
        }


        ResultSet rsMinion = checkForExistingEntity("minions", minionName);
        if (!rsMinion.isBeforeFirst()) {
            insertIntoMinionTable(minionName, age, townId);
            ResultSet rsMinionAfterInsert = checkForExistingEntity("minions", minionName);
            rsMinionAfterInsert.next();
            minionId = rsMinionAfterInsert.getInt("id");


        } else
            minionId = rsMinion.getInt("id");
        PreparedStatement ps = conn.prepareStatement("insert into minions_villains (minion_id)\n" +
                "value (?);");
        ps.setInt(1, minionId);
        ps.executeUpdate();

    }

    private static void insertIntoMinionTable(String minionName, int age, int townId) throws SQLException {
        String query = String.format("insert into minions (name, age, town_id)\n" +
                "values ('%s', %d, %d);", minionName, age, townId);
        PreparedStatement ps = conn.prepareStatement(query);
        ps.executeUpdate();
    }

    private static ResultSet checkForExistingEntity(String tableName, String name) throws SQLException {
        String query = String.format("select id from %s where name = '%s'", tableName, name);
        PreparedStatement ps = conn.prepareStatement(query);
        return ps.executeQuery();
    }

    private static void insertIntoTable(String tableName, String name) throws SQLException {
        String query = String.format("INSERT into %s (name) value ('%s');", tableName, name);
        PreparedStatement ps = conn.prepareStatement(query);
        ps.executeUpdate();
    }

    private static void taskThree(String tableName) throws SQLException, IOException {
        System.out.print("Enter villain id:");
        int id = Integer.parseInt(reader.readLine());
        String entityName = findEntityNameByID(id, tableName);
        if (entityName == null) {
            System.out.printf("No villain with ID %d exists in the database.", id);
        } else
            System.out.printf("Villain: %s%n", entityName);
        getMinions(id);
    }

    private static String findEntityNameByID(int id, String tableName) throws SQLException {
        String query = String.format("select name from %s  where id = ?", tableName);
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            rs.next();
            return String.format("%s", rs.getString("name"));
        }
        return null;
    }

    private static void getMinions(int id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("select m.name, m.age from minions as m\n" +
                "right join minions_villains mv on m.id = mv.minion_id\n" +
                "where mv.villain_id = ?;\n");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.printf("%3d.| %10.10s|%3d%n", rs.getRow(), rs.getString("name"), rs.getInt("age"));
        }

    }

    private static void taskTwo() throws SQLException, IOException {
        // Prepare statement
        String quireStr = "select v.name, count(DISTINCT mv.minion_id) as count_ from villains as v\n" +
                "join minions_villains mv on v.id = mv.villain_id\n" +
                "group by v.name\n" +
                "having count_ >  ?\n" +
                "order by count_ DESC;";
        PreparedStatement ps = conn.prepareStatement(quireStr);
        // set parameter
        ps.setInt(1, 15);
        // execute query
        ResultSet rs = ps.executeQuery();
        // get results from result set
        while (rs.next()) {
            System.out.printf("%d.|%10.10s|%3d|%n", rs.getRow(), rs.getString("name"), rs.getInt("count_"));
        }
    }

    private static Connection getConnection(BufferedReader reader) throws IOException, SQLException {

        System.out.print("Enter user:");
        String user = reader.readLine();
        user = user.equals("") ? "root" : user;
        System.out.print("Enter password:");
        String password = reader.readLine();
        password = password.equals("") ? "root" : password;
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        return DriverManager.getConnection(CONN_STRING + DB_NAME, properties);
    }
}




