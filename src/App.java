import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class App {
    private static final String DATABASE_URL = "jdbc:mysql://localhost/func";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "12345678";


    public static void main(String[] args) {
        insertData(1, "Teste");
        insertData(10,"Marcos");
        readData();
    }


    public static void insertData(int id, String name) {
        try (Connection conn = DriverManager.getConnection(
                DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {

            String sql = "INSERT INTO funcionario (idfuncionario, nome) VALUES (?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.setString(2, name);
                statement.setInt(1,id);
                statement.setString(2,name);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("A new user was inserted successfully!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void readData() {
        try (Connection conn = DriverManager.getConnection(
                DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {

            String sql = "SELECT * FROM funcionario";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {


                while (resultSet.next()) {
                    int id = resultSet.getInt("idfuncionario");
                    String name = resultSet.getString("nome");;


                    System.out.println("ID: " + id + ", Name: " + name );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
