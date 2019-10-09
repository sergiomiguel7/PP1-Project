import java.util.Scanner;

public class Servico {
    private String []servico;
    private int servicoX;

    public String[] getServico() {
        return servico;
    }

    public void setServico(String[] servico) {
        this.servico = servico;
    }

    public int getServicoX() {
        return servicoX;
    }

    public void setServicoX(int servicoX) {
        this.servicoX = servicoX;
    }



    public Servico(){
        this.servico = new String[]{"Pessoas","Bus","Big","Urgentes","Refeições"};

    }

    //Incompleta!!!
    public void EscolherServico(){
        Scanner ler = new Scanner(System.in);
        System.out.println("0 - Pessoas");
        System.out.println("1 - Bus");
        System.out.println("2 - Big");
        System.out.println("3 - Urgentes");
        System.out.println("4 - Refeições");
        int x = ler.nextInt();
        this.servicoX = x;

    }
}
