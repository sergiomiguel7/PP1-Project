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





}
