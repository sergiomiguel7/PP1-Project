import java.util.ArrayList;


public class TransportesDB {
    private ArrayList<Transportes> listtrans;

    public TransportesDB() {
        listtrans = new ArrayList<Transportes>();
    }

    public void addT(Transportes transportes) {
        listtrans.add(transportes);
    }

    public int getQuantidade() {
        return listtrans.size();
    }

    public boolean estaVazio() {
        return listtrans.isEmpty();
    }

    public void removerT(Transportes transportes) {
        listtrans.remove(transportes);
    }

}

