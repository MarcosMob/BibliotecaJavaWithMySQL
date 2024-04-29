import java.sql.*;
import java.util.Scanner;

public class Livro extends ItemBiblioteca implements Emprestavel{

    private int edicao;
    private String genero;
    private int AnoDePublicacao;
    private int CodigoEditora;

    private String codigoLivro;

    public Livro(int codigo, String titulo, String status) {
        super(codigo, titulo, status);
    }

    //Construtor da classe livro


    public static boolean verificaItem(){
        return true;
    }

    public static void insertData(int codigoEditora, String nome, String contato, int codigo, int edicao, String genero, int AnoDePublicacao, int CodigoEditora, String titulo, String status) {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {
            boolean verifyRegistro = Editora.insertData(codigoEditora, nome, contato);
            //Verifica se já existe um usuário com o CPF fornecido
            String sqlVerifica = "SELECT COUNT(*) AS count FROM LIVRO WHERE codigo = ?";
            try (PreparedStatement verificaStatement = conn.prepareStatement(sqlVerifica)) {
                verificaStatement.setInt(1, codigo);
                ResultSet resultSet = verificaStatement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt("count");

                // Se count for zero, significa que não existe nenhum usuário com o CPF fornecido, então podemos inserir
                if (count == 0 && verifyRegistro) {
                    // First insert into ItemBiblioteca
                    String sqlItemBiblioteca = "INSERT INTO itembiblioteca (codigo, titulo, status) VALUES (?, ?, ?)";
                    try (PreparedStatement statement1 = conn.prepareStatement(sqlItemBiblioteca)) {
                        statement1.setInt(1, codigo);
                        statement1.setString(2, titulo);
                        statement1.setString(3, status);

                        int rowsInserted = statement1.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("\nItem adicionado");
                        }
                    }

                    // Then insert into Livro
                    String sqlLivro = "INSERT INTO Livro (codigo, edicao, genero, anodepublicacao, codigoeditora, titulo, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement statement = conn.prepareStatement(sqlLivro)) {
                        statement.setInt(1, codigo);
                        statement.setInt(2, edicao);
                        statement.setString(3, genero);
                        statement.setInt(4, AnoDePublicacao);
                        statement.setInt(5, CodigoEditora);
                        statement.setString(6, titulo);
                        statement.setString(7, status);

                        int rowsInserted = statement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("\nLivro adicionado");
                        }
                    }
                } else {
                    System.out.println("\nLivro com este codigo já está cadastrado!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Metodo para edicao de um livro

    public static void editData(int codigo) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {
            //Pega os dados de autores
            String sql = "SELECT * FROM Livro";
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

                        System.out.println("Digite o seu novo titulo:");
                        String tituloNovo = scanner.nextLine();

                        System.out.println("Digite seu novo valor de edicao:");
                        int edicaoNovo = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Digite seu novo genero:");
                        String generoNovo = scanner.nextLine();

                        System.out.println("Digite seu novo ano de publicacao:");
                        int anoPubliNovo = scanner.nextInt();




                        sql = "UPDATE Livro SET titulo = ?, edicao = ?, genero = ?, anodepublicacao = ? WHERE codigo = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {
                            edita.setString(1, tituloNovo);
                            edita.setInt(2, edicaoNovo);
                            edita.setString(3,generoNovo);
                            edita.setInt(4,anoPubliNovo);
                            edita.setInt(5, codeVerificacao);

                            int rowsUpdated = edita.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("\nLivro alterado com sucesso !");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sql = "UPDATE itemBiblioteca SET titulo = ? WHERE codigo = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {
                            edita.setString(1, tituloNovo);
                            edita.setInt(2, codeVerificacao);

                            int rowsUpdated = edita.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("\nItem alterado com sucesso !");
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

    //remove livro

    public static void removeData(int codigo) {
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {
            //Pega os dados de autores
            String sql = "SELECT * FROM Livro";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                boolean cpfEncontrado = false;
                //loop para rodar os dados de autores
                while (resultSet.next()) {
                    //variavel para receber o valor da chave primaria atual
                    int codeVerificacao = resultSet.getInt("codigo");
                    //checagem para ver se as chaves sao identicas para realizar as alteracoes
                    if (codeVerificacao == codigo) {
                        System.out.println("Codigo " + codeVerificacao + " encontrado !");

                        sql = "delete from Livro WHERE codigo = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {
                            edita.setInt(1, codeVerificacao);

                            int rowsUpdated = edita.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("\nLivro removido com sucesso !");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sql = "delete from itemBiblioteca WHERE codigo = ?";
                        try (PreparedStatement edita = conn.prepareStatement(sql)) {
                            edita.setInt(1, codeVerificacao);

                            int rowsUpdated = edita.executeUpdate();
                            if (rowsUpdated > 0) {
                                System.out.println("\nItem removido com sucesso !");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cpfEncontrado = true;
                        break;
                    }
                }
                if (!cpfEncontrado) {
                    System.out.println("Codigo do livro não encontrado. Por favor, tente novamente.");}
            }
        }catch (Exception e) {
            e.printStackTrace();}
    }



     //Implementando os metodos de interface da classe Emprestavel
     @Override
     public void emprestar() {
         Scanner scanner = new Scanner(System.in);
         try (Connection conn = DriverManager.getConnection(
                 Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {
             System.out.println("Me informe o código do livro para verificar seu status:");
             int codigo = scanner.nextInt();
             scanner.nextLine();

             System.out.println("Me informe o CPF da pessoa que está fazendo o empréstimo do livro:");
             String cpfVerify = scanner.nextLine();

             String sql = "SELECT nome FROM pessoa WHERE CPF = ?";
             try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                 preparedStatement.setString(1, cpfVerify);
                 ResultSet rs = preparedStatement.executeQuery();

                 if (rs.next()) {
                     String nomePessoa = rs.getString("nome");
                     String sql2 = "SELECT * FROM livro WHERE codigo = ? AND status = 'disponivel'";
                     try (PreparedStatement statement = conn.prepareStatement(sql2)) {
                         statement.setInt(1, codigo);
                         ResultSet resultSet = statement.executeQuery();

                         if (resultSet.next()) {
                             System.out.println("Livro encontrado:");

                             String updateLivroSql = "UPDATE livro SET status = 'emprestado' WHERE codigo = ? AND status = 'disponivel'";
                             try (PreparedStatement edita = conn.prepareStatement(updateLivroSql)) {
                                 edita.setInt(1, codigo);
                                 int rowsUpdated = edita.executeUpdate();
                                 if (rowsUpdated > 0) {
                                     System.out.println("Livro adicionado à lista de empréstimo!");
                                 }
                             } catch (SQLException e) {
                                 e.printStackTrace();
                             }

                             String updateItemSql = "UPDATE itembiblioteca SET status = 'disponivel' WHERE codigo = ? AND status = 'disponivel'";
                             try (PreparedStatement edita = conn.prepareStatement(updateItemSql)) {
                                 edita.setInt(1, codigo);
                                 int rowsUpdated = edita.executeUpdate();

                             } catch (SQLException e) {
                                 e.printStackTrace();
                             }
                             String updatePessoaSql = "UPDATE Livro SET cpfLivro = ?";
                             try (PreparedStatement edita = conn.prepareStatement(updatePessoaSql)) {
                                 edita.setString(1, cpfVerify);
                                 int rowsUpdated = edita.executeUpdate();

                             } catch (SQLException e) {
                                 e.printStackTrace();
                             }
                         } else {
                             System.out.println("Livro não encontrado ou não disponível para empréstimo!");
                         }
                     }
                 } else {
                     System.out.println("Pessoa não encontrada!");
                 }
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }


    @Override
     public void devolver() {
        Scanner scanner = new Scanner(System.in);
         try (Connection conn = DriverManager.getConnection(
                 Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {
             //Pega os dados de autores

             String sql = "SELECT * FROM Livro";
             try (Statement statement = conn.createStatement();
                  ResultSet resultSet = statement.executeQuery(sql)) {
                 //
                 int codigo;
                 System.out.println("Me informe o codigo do livro para verificar seu status");
                 codigo = scanner.nextInt();
                 //loop para rodar os dados de autores
                 while (resultSet.next()) {
                     //variavel para receber o valor da chave primaria atual
                     int codeVerificacao = resultSet.getInt("codigo");
                     String statusVerificacao = resultSet.getString("status");
                     //checagem para ver se as chaves sao identicas para realizar as alteracoes
                     if (codeVerificacao == codigo && statusVerificacao.equals("emprestado")) {
                         System.out.println("Codigo " + codeVerificacao + " encontrado:");

                         sql = "update livro set status = 'disponivel' WHERE codigo = ? AND status = 'emprestado'";
                         try (PreparedStatement edita = conn.prepareStatement(sql)) {
                             edita.setInt(1, codeVerificacao);

                             int rowsUpdated = edita.executeUpdate();

                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                         sql = "update itembiblioteca set status = 'disponivel' WHERE codigo = ? AND status = 'emprestado'";
                         try (PreparedStatement edita = conn.prepareStatement(sql)) {
                             edita.setInt(1, codeVerificacao);

                             int rowsUpdated = edita.executeUpdate();

                         } catch (Exception e) {
                             e.printStackTrace();
                         }

                         String updatePessoaSql = "UPDATE Livro SET cpfLivro = NULL WHERE codigo = ?";
                         try (PreparedStatement edita = conn.prepareStatement(updatePessoaSql)) {
                             edita.setInt(1, codigo);
                             int rowsUpdated = edita.executeUpdate();
                                 System.out.println("Livro Devolvido !");

                         } catch (SQLException e) {
                             e.printStackTrace();
                         }

                     }else{
                         System.out.println("Codigo de livro nao encontrado ou o livro nao pode ser devolvido no momento!");
                     }
                 }
             }
         }catch (Exception e) {
             e.printStackTrace();}
     }

     //metodo para mostrar os livros disponiveis

    public static void mostraDisponivel() {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM Livro";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {


                while (resultSet.next()) {
                    String situacao = resultSet.getString("status");;
                    if (situacao.equals("disponivel")){
                        String titulo = resultSet.getString("titulo");
                        int codigo = resultSet.getInt("codigo");


                    System.out.println("O livro: " + titulo  + "\nCodigo: " + codigo + "\nStatus: " + situacao  );
                    System.out.println("=================================");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //metodo para mostrar livros emprestados

    public static void mostraIndisponivel() {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM Livro";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {


                while (resultSet.next()) {
                    String situacao = resultSet.getString("status");;
                    if (situacao.equals("emprestado")){
                        String titulo = resultSet.getString("titulo");
                        int codigo = resultSet.getInt("codigo");
                        String cpf = resultSet.getString("cpflivro");

                        System.out.println("O livro: " + titulo  + "\nCodigo: " + codigo + "\nStatus: " + situacao + "\nCPF responsavel: " + cpf );
                        System.out.println("=================================");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //metodo para mostrar todos livros

    public static void mostraTodos() {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM Livro";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                int count = 0;
                while (resultSet.next()) {
                    count++;
                    int codigo = resultSet.getInt("codigo");;
                    String titulo = resultSet.getString("titulo");
                    int edicao = resultSet.getInt("edicao");
                    int editora = resultSet.getInt("codigoEditora");
                    String status = resultSet.getString("status");

                    System.out.println("Codigo: " + codigo  + "\nLivro: " + titulo + "\nEdicao: " + edicao + "\nCodigo da Editora: " + editora + "\nStatus: " + status);
                    System.out.println("=====================================================");

                }
                if (count == 0){
                    System.out.println("Nao ha nenhum livro registrado !");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //busca avancada

    public static void buscaAvancada(int verify) {
        try (Connection conn = DriverManager.getConnection(
                Controle.getDATABASE_URL(), Controle.getDATABASE_USER(), Controle.getDATABASE_PASSWORD())) {

            String sql = "SELECT * FROM Livro";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                int count = 0;
                while (resultSet.next()) {
                    count++;
                    int codigo = resultSet.getInt("codigo");;
                    String titulo = resultSet.getString("titulo");
                    int edicao = resultSet.getInt("edicao");
                    int editora = resultSet.getInt("codigoEditora");
                    String status = resultSet.getString("status");
                    String genero = resultSet.getString("genero");
                    int anoPubli  = resultSet.getInt("anodepublicacao");

                    if (codigo == verify) {
                        System.out.println("Livro encontrado: \n Informacoes\n\n");
                        System.out.println("Codigo: " + codigo + "\nLivro: " + titulo +"\nGenero: " + genero + "\nEdicao: " + edicao + "" +
                                "\nAno de Publicacao: "+anoPubli+"\nCodigo da Editora: " + editora + "\nStatus: " + status);
                        System.out.println("=====================================================");
                    }
                }
                if (count == 0){
                    System.out.println("Nao ha nenhum livro registrado !");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Gets e sets da classe livro, edicao,genero,anodepublicacao,codigoeditora
     public int getEdicao() {
         return edicao;
     }

     public void setEdicao(int edicao) {
         this.edicao = edicao;
     }

     public String getGenero() {
         return genero;
     }

     public void setGenero(String genero) {
         this.genero = genero;
     }

     public int getAnoDePublicacao() {
         return AnoDePublicacao;
     }

     public void setAnoDePublicacao(int anoDePublicacao) {
         AnoDePublicacao = anoDePublicacao;
     }

     public int getCodigoEditora() {
         return CodigoEditora;
     }

     public void setCodigoEditora(int codigoEditora) {
         CodigoEditora = codigoEditora;
     }
 }
