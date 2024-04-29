import java.sql.*;
import java.util.Scanner;

public class Funcionario {
    private String funcao;
    private float salario;
    //Construtor de funcionario
    public Funcionario(String funcao, float salario) {
        this.funcao = funcao;
        this.salario = salario;
    }
    //Chamando funcao de polimorfismo para adicionar funcionario

    public static void insertData(String cpf, String nome, String funcao, float salario) {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            //Verifica se já existe um usuário com o CPF fornecido
            String sqlVerifica = "SELECT COUNT(*) AS count FROM funcionario WHERE cpf = ?";
            try (PreparedStatement verificaStatement = conn.prepareStatement(sqlVerifica)) {
                verificaStatement.setString(1, cpf);
                ResultSet resultSet = verificaStatement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt("count");

                // Se count for zero, significa que não existe nenhum usuário com o CPF fornecido, então podemos inserir
                if (count == 0) {
                    String sql = "INSERT INTO Funcionario (cpf, nome, funcao, salario) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement statement = conn.prepareStatement(sql)) {
                        statement.setString(1, cpf);
                        statement.setString(2, nome);
                        statement.setString(3, funcao);
                        statement.setFloat(4, salario);

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("\nFuncionario adicionado");
                        }
                    }
                } else {
                    System.out.println("\nFuncionario com este CPF já está cadastrado!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //edicao de funcionario

    public static void editData(String cpf) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {
            //Pega os dados de autores
            String sql = "SELECT * FROM Funcionario";
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


                        System.out.println("Digite sua funcao:");
                        String funcaoNovo = scanner.nextLine();


                        System.out.println("Digite seu salario:");
                        float salarioNovo = scanner.nextFloat();

                        sql = "UPDATE Usuario SET nome = ?, funcao = ?, salario = ? WHERE cpf = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {
                            edita.setString(1, nomeNovo);
                            edita.setString(2, funcaoNovo);
                            edita.setFloat(3, salarioNovo);
                            edita.setString(4, codeVerificacao);

                            int rowsUpdated = edita.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("\nFuncionario alterado com sucesso !");
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
    //metodo de remocao de funcionario

    public static void removeData(String cpf) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {
            //Pega os dados de autores
            String sql = "SELECT * FROM Funcionario";
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

                        sql = "Delete FROM funcionario WHERE cpf = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {
                            edita.setString(1, codeVerificacao);

                            int rowsUpdated = edita.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("\nFuncionario deletado !");
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
    //metodo para mostrar todos funcionarios

    public static void mostraTodos() {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM funcionario";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                int count = 0;
                while (resultSet.next()) {
                    String cpf = resultSet.getString("cpf");;
                    String nome = resultSet.getString("nome");
                    String funcao = resultSet.getString("funcao");

                    System.out.println("Funcionario: " + nome  + "\nCPF: " + cpf + "Funcao: " + funcao);
                    System.out.println("=====================================================");
                    count++;
                }
                if (count == 0){
                    System.out.println("Nao ha nenhum funcionario registrada !");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //buscando dado especifico

    public static void buscaAvancada(String verify) {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM funcionario";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                int count = 0;
                while (resultSet.next()) {
                    String cpf = resultSet.getString("cpf");
                    String nome = resultSet.getString("nome");
                    String funcao = resultSet.getString("funcao");
                    float salario = resultSet.getFloat("salario");

                    if (cpf.equals(verify)) {
                        System.out.println("Funcionario encontrado: \n Informacoes\n\n");
                        System.out.println("Funcionario: " + nome + "\nCPF: " + cpf + "Funcao: " + funcao +"Salario: " + salario);
                        System.out.println("=====================================================");
                    }
                    count++;
                }
                if (count == 0){
                    System.out.println("Nao ha nenhum funcionario registrada !");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodos gets e sets de funcionario, salario e funcao
    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }
}
