import java.util.Comparator;

public class ComparadorPreco implements Comparator<Transportes>{
    public int compare(Transportes t1, Transportes t2) {
        return (int) t1.getPrecoKM() - (int) t2.getPrecoKM();
    }
}
