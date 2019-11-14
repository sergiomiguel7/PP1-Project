import java.util.Scanner;

public abstract class Servico {

    private boolean transporteTC;   //temperatura constante
    private int limiteT;            //limite de carga(pessoas ou kg)

    public Servico()
    {
        this.transporteTC = false;
        this.limiteT = 0;

    }
    public Servico(boolean transporteTC, int limiteT) {
        this.transporteTC = transporteTC;
        this.limiteT = limiteT;
    }

    public boolean isTransporteTC() {
        return transporteTC;
    }

    public void setTransporteTC(boolean transporteTC) {
        this.transporteTC = transporteTC;
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
        sb.append("Temperatura Constante:").append(this.transporteTC)
                .append("Limite de Carga:").append(this.limiteT);
        return sb.toString();
    }
}
