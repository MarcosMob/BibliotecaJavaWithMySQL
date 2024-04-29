import javax.naming.ldap.Control;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    int op;
    //Criando scanner para efetuar uma entrada de dados
    Scanner scanner = new Scanner(System.in);
    public void start(){
        System.out.println("========= Seja Bem-Vindo(a) a Biblioteca ==========");
        do {

            System.out.println("\n\nAbaixo estao algumas opcoes do nosso sistema, digite o numero de acordo com a opcao desejada\n");
            System.out.println("1  - Adicionar \n" +
                               "2  - Editar \n" +
                               "3  - Remover \n" +
                               "4  - Efetuar emprestimo de livro \n" +
                               "5  - Efetuar Devolucao de livro \n" +
                               "6  - Mostrar Livros Disponiveis \n" +
                               "7  - Mostrar Livros Emprestados \n" +
                               "8  - Mostrar Informacaoes \n" +
                               "9  - Busca de informacoes avancadas\n" +
                               "10 - Sair do Programa\n");


            op = scanner.nextInt();

            switch (op){
                case 1:
                    Controle.chamaCaso1();
                    break;

                case 2:
                    Controle.chamaCaso2();
                    break;

                case 3:
                    Controle.chamaCaso3();
                    break;

                case 4:
                    Livro livroEmp = new Livro(-1,"varTeste","error");
                    livroEmp.emprestar();
                    break;

                case 5:
                    Livro livroDev = new Livro(-1,"varTeste","error");
                    livroDev.devolver();
                    break;

                case 6:
                    System.out.println("Livros Disponiveis :\n");
                    Livro.mostraDisponivel();
                    break;

                case 7:
                    System.out.println("Livros Emprestados :\n");
                    Livro.mostraIndisponivel();
                    break;

                case 8:
                Controle.chamaCaso8();
                    break;
                case 9:
                Controle.chamaCaso9();
                    break;

                case 10:
                    System.out.println("Saindo...");
                    break;

                default:

                    System.out.println("Opcao invalida, tente novamente outra opcao !");

                    break;
            }
        }while (op != 10);
    }



    public static void main(String[] args) throws SQLException {
        Main starter = new Main();
        starter.start();
    }
}
