import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class Controle {
    private static final String DATABASE_URL = "jdbc:mysql://localhost/bibliotecajava";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "12345678";

    public static String getDATABASE_URL() {
        return DATABASE_URL;
    }

    // Getter para DATABASE_USER
    public static String getDATABASE_USER() {
        return DATABASE_USER;
    }

    // Getter para DATABASE_PASSWORD
    public static String getDATABASE_PASSWORD() {
        return DATABASE_PASSWORD;
    }



    //metodo para mostrar opcoes de adicionar

    public static void chamaCaso1(){
        int op;
        Scanner scanner = new Scanner(System.in);
        do {

            System.out.println("=====================================================");
            System.out.println ("1 - Adicionar novo Usuario \n" +
                    "2 - Adicionar novo Funcionario \n" +
                    "3 - Adicionar novo Livro e Editora\n" +
                    "4 - Adicionar novo Autor \n" +
                    "5 - <-- Voltar ao menu inicial\n");


            op = scanner.nextInt();

            switch (op){
                case 1:
                    String cpf,endereco,nome,telefone,funcao;
                    float salario;

                    scanner.nextLine();
                    System.out.println("Digite seu CPF:");
                    cpf = scanner.nextLine();


                    System.out.println("Digite seu Nome:");
                    nome = scanner.nextLine();


                    System.out.println("Digite seu endereco:");
                    endereco = scanner.nextLine();

                    System.out.println("Digite seu telefone:");
                    telefone = scanner.nextLine();
                    Pessoa.insertData(cpf,nome);
                    Usuario.insertData(cpf,nome,endereco,telefone);
                    break;

                case 2:

                    scanner.nextLine();
                    System.out.println("Digite seu CPF:");
                    cpf = scanner.nextLine();


                    System.out.println("Digite seu Nome:");
                    nome = scanner.nextLine();


                    System.out.println("Digite sua funcao:");
                    funcao = scanner.nextLine();

                    System.out.println("Digite seu salario:");
                    salario = scanner.nextFloat();
                    Pessoa.insertData(cpf,nome);
                    Funcionario.insertData(cpf,nome,funcao,salario);

                    break;
                case 3:
                    String titulo,status,contato,genero;
                    int codigo,codigoEditora,anoDePublicacao,edicao;

                    scanner.nextLine();
                    System.out.println("Digite o codigo do livro:");
                    codigo = scanner.nextInt();

                    scanner.nextLine();
                    System.out.println("Digite o titulo do livro:");
                    titulo = scanner.nextLine();


                    System.out.println("Digite o codigo da editora:");
                    codigoEditora = scanner.nextInt();

                    scanner.nextLine();
                    System.out.println("Digite o nome da editora:");
                    nome = scanner.nextLine();

                    System.out.println("Digite o email de contato da editora:");
                    contato = scanner.nextLine();

                    System.out.println("Informe a edicao do livro:");
                    edicao = scanner.nextInt();

                    scanner.nextLine();
                    System.out.println("Digite o genero do livro:");
                    genero = scanner.nextLine();

                    System.out.println("Digite o ano de publicacao do livro:");
                    anoDePublicacao = scanner.nextInt();

                    System.out.println("Status definido como disponivel");
                    status = "disponivel";


                    Livro.insertData(codigoEditora,nome,contato,codigo,edicao,genero,anoDePublicacao,codigoEditora,titulo,status);
                    break;
                case 4:
                    String nacionalidade;

                    System.out.println("Informe o codigo do autor:");
                    codigo = scanner.nextInt();

                    scanner.nextLine();
                    System.out.println("Informe o nome do autor:");
                    nome = scanner.nextLine();

                    System.out.println("Informe a nacionalidade do autor");
                    nacionalidade = scanner.nextLine();

                    Autores.insertData(codigo,nome,nacionalidade);
                    break;
                case 5:
                    System.out.println("<--");
                    break;

                default:
                    System.out.println("Opcao invalida tente novamente.");
                    break;

            }
        }while (op != 5);

    }


    //metodo para chamar o caso 2 com opcoes de edicao
    public static void chamaCaso2(){
        int op;
        Scanner scanner = new Scanner(System.in);
        do {

            System.out.println("=====================================================");
            System.out.println ("1 - Editar  Usuario \n" +
                                "2 - Editar  Funcionario\n" +
                                "3 - Editar  Livro  \n" +
                                "4 - Editar  Editora\n"+
                                "5 - Editar  Autor \n" +
                                "6 - <-- Voltar ao menu inicial\n");


            op = scanner.nextInt();

            switch (op){
                case 1:
                    scanner.nextLine();
                    System.out.println("Digite o CPF do usuario que deseja alterar:");
                    String cpf = scanner.nextLine();
                    Usuario.editData(cpf);
                    break;

                case 2:
                    System.out.println("Digite o CPF do funcionario que deseja alterar");
                    cpf = scanner.nextLine();
                    Funcionario.editData(cpf);
                    break;
                case 3:
                    System.out.println("Digite o Codigo do livro que deseja alterar");
                    int codigo = scanner.nextInt();
                    Livro.editData(codigo);

                    break;
                case 4:
                    System.out.println("Digite o Codigo da Editora que deseja alterar");
                    codigo = scanner.nextInt();
                    Editora.editData(codigo);
                    break;
                case 5:
                    System.out.println("Digite o codigo do autor que deseja alterar:");
                    codigo = scanner.nextInt();
                    Editora.editData(codigo);
                    break;
                case 6:
                    System.out.println("<--");
                    break;

                default:
                    System.out.println("Opcao invalida tente novamente.");
                    break;

            }
        }while (op != 6);

    }


    //metodo para mostrar opcoes de remocao
    public static void chamaCaso3(){
        int op;
        Scanner scanner = new Scanner(System.in);
        do {

            System.out.println("=====================================================");
            System.out.println ("1 - Remove Usuario \n" +
                    "2 - Remove Funcionario \n" +
                    "3 - Remove Livro \n" +
                    "4 - Remove Editora \n" +
                    "5 - Remove Autor \n" +
                    "6 - <-- Voltar ao menu inicial\n");


            op = scanner.nextInt();

            switch (op){
                case 1:
                    scanner.nextLine();
                    System.out.println("Digite o CPF do usuario que deseja remover:");
                    String cpf = scanner.nextLine();
                    Usuario.removeData(cpf);
                    break;

                case 2:
                    scanner.nextLine();
                    System.out.println("Digite o CPF do funcionario que deseja remover:");
                    cpf = scanner.nextLine();
                    Funcionario.removeData(cpf);
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.println("Digite o codigo do livro que deseja remover:");
                    int codigo = scanner.nextInt();
                    Livro.removeData(codigo);
                    break;
                case 4:
                    scanner.nextLine();
                    System.out.println("Digite o codigo da editora que deseja remover:");
                    codigo = scanner.nextInt();
                    Editora.removeData(codigo);
                    break;
                case 5:
                    System.out.println("Digite o codigo do autor que deseja remover:");
                    codigo = scanner.nextInt();
                    Autores.removeData(codigo);
                    break;
                case 6:
                    System.out.println("<--");
                    break;

                default:
                    System.out.println("Opcao invalida tente novamente.");
                    break;

            }
        }while (op != 6);

    }

    //metodo para chamar as funcoes de mostrar

    public static void chamaCaso8(){
        int op;
        Scanner scanner = new Scanner(System.in);
        do {

            System.out.println("=====================================================");
            System.out.println ("1 - Mostrar todas pessoas registradas \n" +
                                "2 - Mostrar todos os usuarios  registrados \n" +
                                "3 - Mostrar todos os funcionarios registrados \n" +
                                "4 - Mostrar todos os livros registrados \n" +
                                "5 - Mostrar todas as editoras registradas \n" +
                                "6 - Mostrar todos os autores registrados \n" +
                                "7 - <-- Voltar ao menu inicial\n");


            op = scanner.nextInt();

            switch (op){
                case 1:
                    System.out.println("Mostrando todas as pessoas registradas :\n");
                    Pessoa.mostraTodos();
                    break;

                case 2:
                    System.out.println("Mostrando todos os usuarios registradas :\n");
                    Usuario.mostraTodos();

                    break;
                case 3:
                    System.out.println("Mostrando todos os funcionarios registradas :\n");
                    Funcionario.mostraTodos();

                    break;
                case 4:
                    System.out.println("Mostrando todos os livros registradas :\n");
                    Livro.mostraTodos();
                    break;
                case 5:
                    System.out.println("Mostrando todos as editoras registradas :\n");
                    Editora.mostraTodos();
                    break;
                case 6:
                    System.out.println("Mostrando todos os autores registradas :\n");
                    Autores.mostraTodos();
                    break;
                case 7:
                    System.out.println("<--");
                    break;

                default:
                    System.out.println("Opcao invalida tente novamente.");
                    break;

            }
        }while (op != 7);

    }
    public static void chamaCaso9() {
        int op;
        Scanner scanner = new Scanner(System.in);
        do {

            System.out.println("=====================================================");
            System.out.println("1 - Buscar pessoa especifica \n" +
                    "2 - Buscar usuario  especifico \n" +
                    "3 - Buscar funcionario especifico \n" +
                    "4 - Buscar livro especifico \n" +
                    "5 - Buscar editora especifica \n" +
                    "6 - Buscar autor especifico \n" +
                    "7 - <-- Voltar ao menu inicial\n");


            op = scanner.nextInt();

            switch (op) {
                case 1:
                    scanner.nextLine();
                    System.out.println("Digite o CPF para busca");
                    String cpf = scanner.nextLine();
                    Pessoa.buscaAvancada(cpf);
                    break;

                case 2:
                    scanner.nextLine();
                    System.out.println("Digite o CPF para busca");
                    cpf = scanner.nextLine();
                    Usuario.buscaAvancada(cpf);

                    break;
                case 3:
                    scanner.nextLine();
                    System.out.println("Digite o CPF para busca");
                    cpf = scanner.nextLine();
                    Funcionario.buscaAvancada(cpf);

                    break;
                case 4:
                    System.out.println("Digite o codigo para busca");
                    int codigo = scanner.nextInt();
                    Livro.buscaAvancada(codigo);
                    break;
                case 5:
                    System.out.println("Digite o codigo para busca");
                    codigo = scanner.nextInt();
                    Editora.buscaAvancada(codigo);
                    break;
                case 6:
                    System.out.println("Digite o codigo para busca");
                    codigo = scanner.nextInt();
                    Autores.buscaAvancada(codigo);
                    break;
                case 7:
                    System.out.println("<--");
                    break;

                default:
                    System.out.println("Opcao invalida tente novamente.");
                    break;

            }
        } while (op != 7);
    }
}

