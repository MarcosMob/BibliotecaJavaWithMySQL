import java.sql.*;
import java.util.Scanner;

public class Editora {
    private int codigo;
    private String nome;
    private String contato;

    //Construtor da classe editora
    public Editora(int codigo, String nome, String contato) {
        this.codigo = codigo;
        this.nome = nome;
        this.contato = contato;
    }

    //adicionar editora

    public static boolean insertData(int CodigoEditora,String nome, String contato) {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            //Verifica se já existe um usuário com o CPF fornecido
            String sqlVerifica = "SELECT COUNT(*) AS count FROM Editora WHERE codigo = ?";
            try (PreparedStatement verificaStatement = conn.prepareStatement(sqlVerifica)) {
                verificaStatement.setInt(1, CodigoEditora);
                ResultSet resultSet = verificaStatement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt("count");

                // Se count for zero, significa que não existe nenhum usuário com o CPF fornecido, então podemos inserir
                if (count == 0) {
                    String sql = "INSERT INTO Editora (codigo,nome,contato) VALUES (?, ?, ?)";
                    try (PreparedStatement statement = conn.prepareStatement(sql)) {
                        statement.setInt(1,CodigoEditora);
                        statement.setString(2,nome);
                        statement.setString(3, contato);

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("\nEditora adicionada");
                            return true;
                        }
                    }

                } else {
                    System.out.println("\nEditora com este codigo já está cadastrado!");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //metodo para editar dados

    public static void editData(int codigo) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {
            //Pega os dados de autores
            String sql = "SELECT * FROM Editora";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                boolean cpfEncontrado = false;
                //loop para rodar os dados de autores
                while (resultSet.next()) {
                    //variavel para receber o valor da chave primaria atual
                    int codeVerificacao = resultSet.getInt("codigo");
                    //checagem para ver se as chaves sao identicas para realizar as alteracoes
                    if (codeVerificacao == codigo) {
                        System.out.println("Codigo " + codeVerificacao + " encontrado, digite os dados para serem atualizados:");

                        System.out.println("Digite o seu novo nome:");
                        String nomeNovo = scanner.nextLine();


                        System.out.println("Digite seu novo contato:");
                        String contato = scanner.nextLine();



                        sql = "UPDATE Editora SET nome = ?, contato = ? WHERE codigo = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {
                            edita.setString(1, nomeNovo);
                            edita.setString(2, contato);
                            edita.setInt(3, codeVerificacao);

                            int rowsUpdated = edita.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("\nEditora alterado com sucesso !");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cpfEncontrado = true;
                        break;
                    }
                }
                if (!cpfEncontrado) {
                    System.out.println("CPF não encontrado. Por favor, tente novamente.");}
            }
        }catch (Exception e) {
            e.printStackTrace();}
    }
    //metodo para remover editora

    public static void removeData(int codigo) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {
            //Pega os dados de autores
            String sql = "SELECT * FROM Editora";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                boolean cpfEncontrado = false;
                //loop para rodar os dados de autores
                while (resultSet.next()) {
                    //variavel para receber o valor da chave primaria atual
                    int codeVerificacao = resultSet.getInt("codigo");
                    //checagem para ver se as chaves sao identicas para realizar as alteracoes
                    if (codeVerificacao == codigo) {
                        System.out.println("Codigo " + codeVerificacao + " encontrado, digite os dados para serem atualizados:");

                        sql = "UPDATE Livro SET codigoeditora = null WHERE codigoeditora = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {
                            edita.setInt(1,codeVerificacao);

                            int rowsUpdated = edita.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("\nCodigo do Livro removido com sucesso !");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sql = "delete from Editora WHERE codigo = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {
                            edita.setInt(1, codeVerificacao);

                            int rowsUpdated = edita.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("\nEditora removido com sucesso !");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cpfEncontrado = true;
                        break;
                    }
                }
                if (!cpfEncontrado) {
                    System.out.println("Codigo da editora não encontrado. Por favor, tente novamente.");}
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    //mostrando todas as editoras

    public static void mostraTodos() {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM editora";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                int count = 0;
                while (resultSet.next()) {
                    count++;
                    int codigo = resultSet.getInt("codigo");;
                    String nome = resultSet.getString("nome");
                    String contato = resultSet.getString("contato");

                    System.out.println("Editora: " + nome  + "\nCodigo: " + codigo + "\nContato: " + contato );
                    System.out.println("=====================================================");

                }
                if (count == 0){
                    System.out.println("Nao ha nenhuma editora registrada !");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buscaAvancada(int verify) {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM editora";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                int count = 0;
                while (resultSet.next()) {
                    count++;
                    int codigo = resultSet.getInt("codigo");;
                    String nome = resultSet.getString("nome");
                    String contato = resultSet.getString("contato");

                    if (codigo == verify) {
                        System.out.println("Editora encontrada: \n Informacoes\n\n");
                        System.out.println("Editora: " + nome + "\nCodigo: " + codigo + "\nContato: " + contato);
                        System.out.println("=====================================================");
                    }
                }
                if (count == 0){
                    System.out.println("Nao ha nenhuma editora registrada !");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //gets e sets da classe editora, codigo,nome,contato
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

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}
