import java.util.Scanner;

public class Servico {
    private int servicoX;

    public Servico(){ this.servicoX=this.EscolherServico(); }

    public int getServicoX() {
        return servicoX;
    }

    public void setServicoX(int servicoX) {
        this.servicoX = servicoX;
    }

    public int EscolherServico(){
        Scanner ler = new Scanner(System.in);
        System.out.println("0 - Pessoas\n1-Bus\n2-Big\n3-Urgentes\n4-Refeições");
        int x = ler.nextInt();
        return x;
    }

    public String XtoString(){

        String []Servico = new String[]{"Pessoas","Bus","Big","Urgentes","Refeições"};
        return Servico[this.servicoX];
    }

}
