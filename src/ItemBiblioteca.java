import java.sql.*;

abstract class ItemBiblioteca {
    protected int codigo;
    protected String titulo;
    protected String status;
//construtor de itembiblioteca
    public ItemBiblioteca(int codigo, String titulo, String status) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.status = status;
    }
    //funcao de adicionar para utilizar o polimorfismo em livro
    public static void insertData() {

    }
    //Gets e setters da classe ItemBiblioteca;
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
