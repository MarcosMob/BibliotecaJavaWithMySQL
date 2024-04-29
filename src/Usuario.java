import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class Usuario extends Pessoa{
        private String endereco;
        private String telefone;

    public Usuario(String endereco, String telefone) {
        this.endereco = endereco;
        this.telefone = telefone;
    }
    public static void insertData(String cpf, String nome, String endereco, String telefone) {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            //Verifica se já existe um usuário com o CPF fornecido
            String sqlVerifica = "SELECT COUNT(*) AS count FROM Usuario WHERE cpf = ?";
            try (PreparedStatement verificaStatement = conn.prepareStatement(sqlVerifica)) {
                verificaStatement.setString(1, cpf);
                ResultSet resultSet = verificaStatement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt("count");

                // Se count for zero, significa que não existe nenhum usuário com o CPF fornecido, então podemos inserir
                if (count == 0) {

                    String sql = "INSERT INTO Usuario (cpf, nome, endereco, telefone) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement statement = conn.prepareStatement(sql)) {
                        statement.setString(1, cpf);
                        statement.setString(2, nome);
                        statement.setString(3, endereco);
                        statement.setString(4, telefone);

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("\nUsuário adicionado");
                        }
                    }
                } else {
                    System.out.println("\nUsuário com este CPF já está cadastrado!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //metodo de edicao de usuario
    public static void editData(String cpf) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {
            //Pega os dados de autores
            String sql = "SELECT * FROM Usuario";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                boolean cpfEncontrado = false;
                //loop para rodar os dados de autores
                while (resultSet.next()) {
                    //variavel para receber o valor da chave primaria atual
                    String codeVerificacao = resultSet.getString("cpf");
                    //checagem para ver se as chaves sao identicas para realizar as alteracoes
                    if (codeVerificacao.equals(cpf)) {
                        System.out.println("CPF encontrado, digite os dados para serem atualizados:");

                        System.out.println("Digite o seu nome:");
                        String nomeNovo = scanner.nextLine();


                        System.out.println("Digite seu endereco:");
                        String enderecoNovo = scanner.nextLine();


                        System.out.println("Digite seu telefone:");
                        String telefoneNovo = scanner.nextLine();

                        sql = "UPDATE Usuario SET nome = ?, endereco = ?, telefone = ? WHERE cpf = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {
                            edita.setString(1, nomeNovo);
                            edita.setString(2, enderecoNovo);
                            edita.setString(3, telefoneNovo);
                            edita.setString(4, codeVerificacao);

                            int rowsUpdated = edita.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("\nUsuario alterado com sucesso !");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sql = "UPDATE Pessoa SET nome = ? WHERE cpf = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {
                            edita.setString(1, nomeNovo);
                            edita.setString(2, codeVerificacao);

                            int rowsUpdated = edita.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("\nPessoa alterado com sucesso !");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }cpfEncontrado = true;
                    break;
                }
                if (!cpfEncontrado) {
                    System.out.println("CPF não encontrado. Por favor, tente novamente.");}
            }
        }catch (Exception e) {
            e.printStackTrace();}
    }

    //removendo usuario

    public static void removeData(String cpf) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {
            //Pega os dados de autores
            String sql = "SELECT * FROM Usuario";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                boolean cpfEncontrado = false;
                //loop para rodar os dados de autores
                while (resultSet.next()) {
                    //variavel para receber o valor da chave primaria atual
                    String codeVerificacao = resultSet.getString("cpf");
                    //checagem para ver se as chaves sao identicas para realizar as alteracoes
                    if (codeVerificacao.equals(cpf)) {
                        System.out.println("CPF encontrado!");

                        sql = "Delete FROM usuario WHERE cpf = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {
                            edita.setString(1, codeVerificacao);

                            int rowsUpdated = edita.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("\nUsuario deletado !");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sql = "Delete from Pessoa WHERE cpf = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {
                            edita.setString(1, codeVerificacao);

                            int rowsUpdated = edita.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("\nPessoa Deletada!");
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

    //mostrando todos usuarios registrados

    public static void mostraTodos() {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM usuario";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                int count = 0;
                while (resultSet.next()) {
                    count++;
                    String cpf = resultSet.getString("cpf");;
                    String nome = resultSet.getString("nome");

                    System.out.println("Usuario: " + nome  + "\nCPF: " + cpf );
                    System.out.println("=====================================================");

                }
                if (count == 0){
                    System.out.println("Nao ha nenhum usuario registrado !");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buscaAvancada(String verify) {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM usuario";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                int count = 0;
                while (resultSet.next()) {
                    count++;
                    String cpf = resultSet.getString("cpf");;
                    String nome = resultSet.getString("nome");
                    String endereco = resultSet.getString("endereco");
                    String telefone  = resultSet.getString("telefone");

                    if (cpf.equals(verify)) {
                        System.out.println("Usuario encontrado: \n Informacoes\n\n");
                        System.out.println("Usuario: " + nome + "\nCPF: " + cpf + "\nEndereco: " + endereco +"\nTelefone: " + telefone);
                        System.out.println("=====================================================");
                    }
                }
                if (count == 0){
                    System.out.println("Nao ha nenhum usuario registrado !");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Metodos gets e setter de endereco e telefone
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
