import java.util.ArrayList;

public class Cliente extends Ator{
    private Historico historico;

    //construtores
    public Cliente()
    {
        super(" "," "," "," "," ", 0, 0);
        historico=new Historico();
    }
    public Cliente(Historico historico)
    {
        this.historico=new Historico();
    }
    public Cliente(String email, String nome , String password, String morada, String dataN, int x , int y)
    {
        super(email, nome, password, morada, dataN, x, y);
        this.historico=new Historico();

    }
    public Cliente(Cliente cliente)
    {
        super(cliente.getEmail(), cliente.getNome(),cliente.getPassword(),cliente.getPassword(),cliente.getDataN(),cliente.getX(),cliente.getY());
        historico=getHistorico();
    }

    //getters e setters
    public Historico getHistorico() {
        return historico;
    }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\nDados:" + super.toString());
        return s.toString();
    }
}
