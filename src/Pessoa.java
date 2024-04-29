import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Pessoa {

    private String cpf;
    private String nome;


    //metodo para inserir pessoa no banco de dados
    public static void insertData(String cpf, String nome) {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "INSERT INTO Pessoa (cpf, nome) VALUES (?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1,cpf);
                statement.setString(2,nome);


                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("\nPessoa adicionada");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //metodo para mostrar todas pessoas registradas
    public static void mostraTodos() {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM Pessoa";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                int count = 0;
                while (resultSet.next()) {
                        count++;
                        String cpf = resultSet.getString("cpf");;
                        String nome = resultSet.getString("nome");

                        System.out.println("Pessoa: " + nome  + "\nCPF: " + cpf );
                        System.out.println("=====================================================");

                }
                if (count == 0){
                    System.out.println("Nao ha nenhuma pessoa registrada !");
                }
                //contador para ver se ha alguem registrado
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buscaAvancada(String verify) {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM Pessoa";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                int count = 0;
                while (resultSet.next()) {
                    count++;
                    String cpf = resultSet.getString("cpf");;
                    String nome = resultSet.getString("nome");
                    if (cpf.equals(verify)) {
                        System.out.println("Pessoa encontrada: \n Informacoes\n\n");
                        System.out.println("Pessoa: " + nome + "\nCPF: " + cpf);
                        System.out.println("=====================================================");
                    }
                }
                if (count == 0){
                    System.out.println("Nao ha nenhuma pessoa registrada !");
                }
                //contador para ver se ha alguem registrado
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Metodos gets e sets de Pessoa, CPF e Nome
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
