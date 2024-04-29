import java.sql.*;
import java.util.Scanner;

public class Autores {
    private int codigo;
    private String nome;
    private String nacionalidade;

    //Construtor da classe autores
    public Autores(int codigo, String nome, String nacionalidade) {
        this.codigo = codigo;
        this.nome = nome;
        this.nacionalidade = nacionalidade;
    }

    //adicionando autores

    public static void insertData(int codigo, String nome, String nacionalidade) {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "INSERT INTO Autores (codigo,nome, nacionalidade) VALUES (?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1,codigo);
                statement.setString(2, nome);
                statement.setString(3,nacionalidade);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("\nAutor adicionado ao banco de dados");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editData(int codigo) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {
        //Pega os dados de autores
            String sql = "SELECT * FROM Autores";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                boolean cpfEncontrado = false;
        //loop para rodar os dados de autores
                while (resultSet.next()) {
                    //variavel para receber o valor da chave primaria atual
                    int codeVerificacao = resultSet.getInt("codigo");
                    //checagem para ver se as chaves sao identicas para realizar as alteracoes

                    if (codeVerificacao == codigo){
                        System.out.println("Codigo encontrado, vamos realizar as alteracoes:");

                        System.out.println("Digite o nome do autor");
                        String nomeNovo = scanner.nextLine();


                        System.out.println("Digite a nacionalidade do autor");
                        String nacionalidadeNovo = scanner.nextLine();

                     sql ="UPDATE Autores SET nome = ?, nacionalidade = ? WHERE codigo = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {

                            edita.setString(1,nomeNovo);
                            edita.setString(2,nacionalidadeNovo);
                            edita.setInt(3,codeVerificacao);

                            int rowsInserted = edita.executeUpdate();
                            if (rowsInserted > 0) {
                                System.out.println("\nAutor alterado com sucesso !");
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }cpfEncontrado = true;
                    break;
                }
                if (!cpfEncontrado) {
                    System.out.println("CPF não encontrado. Por favor, tente novamente.");}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //metodo para remover algum autor

    public static void removeData(int codigo) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            //Pega os dados de autores
            String sql = "SELECT * FROM Autores";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                boolean cpfEncontrado = false;
                //loop para rodar os dados de autores
                while (resultSet.next()) {
                    //variavel para receber o valor da chave primaria atual
                    int codeVerificacao = resultSet.getInt("codigo");
                    //checagem para ver se as chaves sao identicas para realizar as alteracoes

                    if (codeVerificacao == codigo){
                        System.out.println("Codigo encontrado, vamos realizar as alteracoes");


                        sql ="DELETE FROM  Autores WHERE codigo = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {

                            edita.setInt(1,codeVerificacao);

                            int rowsInserted = edita.executeUpdate();
                            if (rowsInserted > 0) {
                                System.out.println("\nAutor " + "REMOVIDO");
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        cpfEncontrado = true;
                        break;
                    }
                }
                if (!cpfEncontrado) {
                    System.out.println("Codigo do autor não encontrado. Por favor, tente novamente.");}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //metodo para mostrar todos os autores registrados

    public static void mostraTodos() {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM Autores";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                int count = 0;
                while (resultSet.next()) {
                    count++;
                    int codigo = resultSet.getInt("codigo");;
                    String nome = resultSet.getString("nome");
                    String nacionalidade = resultSet.getString("nacionalidade");

                    System.out.println("Autor(a): " + nome  + "\nCodigo: " + codigo + "\nNacionalidade: " + nacionalidade );
                    System.out.println("=====================================================");

                }
                if (count == 0){
                    System.out.println("Nao ha nenhum autor(a) registrado !");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buscaAvancada(int verify) {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM Autores";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                int count = 0;
                while (resultSet.next()) {
                    count++;
                    int codigo = resultSet.getInt("codigo");;
                    String nome = resultSet.getString("nome");
                    String nacionalidade = resultSet.getString("nacionalidade");
                    if (codigo == verify) {
                        System.out.println("Autor(a) encontrado(a): \n Informacoes\n\n");
                        System.out.println("Autor(a): " + nome + "\nCodigo: " + codigo + "\nNacionalidade: " + nacionalidade);
                        System.out.println("=====================================================");
                    }
                }
                if (count == 0){
                    System.out.println("Nao ha nenhum autor(a) registrado !");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Get e sets da classe Autores, codigo,nome,nacionalidade
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }
}
