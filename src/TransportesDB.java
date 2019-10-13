import java.util.ArrayList;
import java.util.List;

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

    /*
    Devolve uma Lista de inteiros com as posiçoes do Array de Transportes que partilham o mesmo serviço;
     */
    public List<Integer> msmServico(String servico){
        List<Integer> array = new ArrayList<>();
        int i = 0;
        for(Transportes transportes: listtrans){
            if(servico.equals(transportes.getServico())){
                array.add(i);
            }
            i++;
        }
        return array;
    }
}

