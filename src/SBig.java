public class SBig extends Servico {

    public SBig()
    {
        super();
    }

    public SBig(int limiteT)
    {
        setlimiteTBig(limiteT);
    }

    public void setlimiteTBig(int limiteT){
        if(limiteT <= 6000 && limiteT > 0){
            super.setLimiteT(limiteT);
        }else {
            super.setLimiteT(0);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Serviço de BUS")
                .append("\nLimite de Carga:").append(super.getLimiteT());
        return sb.toString();
    }

    public boolean equals(Servico servico) {
        if (servico instanceof SBig)
            return true;
        else
            return false;
    }


}
