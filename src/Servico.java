import java.util.Scanner;

public class Servico {

    private boolean transporteTC;   //temperatura constante
    private int limiteT;            //limite de carga(pessoas ou kg)
    private int limiteD;            //limite de distancia

    public Servico()
    {
        this.transporteTC = false;
        this.limiteT = 0;
        this.limiteD = 0;

    }
    public Servico(boolean transporteTC, int limiteT, int limiteD) {
        this.transporteTC = transporteTC;
        this.limiteT = limiteT;
        this.limiteD = limiteD;
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

    public int getLimiteD() {
        return limiteD;
    }

    public void setLimiteD(int limiteD) {
        this.limiteD = limiteD;
    }


}
