public class SBig extends Servico {


    public SBig(int limiteT)
    {
        super(false,limiteT);
    }


    public String toString() {
        return "SBig{}";
    }

    public boolean equals(Servico servico) {
        if (servico instanceof SBig)
            return true;
        else
            return false;
    }
}
