import java.io.Serializable;

public abstract class Servico implements Serializable {

    private int limiteT;            //limite de carga(pessoas ou kg)

    public Servico()
    {
        this.limiteT = 0;

    }
    public Servico(int limiteT) {
        this.limiteT = limiteT;
    }

    public int getLimiteT() {
        return limiteT;
    }

    public void setLimiteT(int limiteT) {
        this.limiteT = limiteT;
    }

    public abstract boolean equals(Servico servico);

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Limite de Carga:").append(this.limiteT);
        return sb.toString();
    }
}
