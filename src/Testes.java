import java.util.Scanner;

public class Testes {

    public static int menu()
    {
        int selection;
        Scanner input = new Scanner(System.in);
        System.out.println("Login - 1\n Registar - 2");
        selection=input.nextInt();

        return selection;

    }

    public static void main(String[] args) {

        TransportesDB listtrans = new TransportesDB();

        /*
        Transportes p1,p2;
        p1 = new Transportes("sergio@", "Sergio", "a", "Guim", "1 3 2000", 1 , 2, 3, 2,3);
        p2 = new Transportes("jose@","Jose","b","Braga","1 2 2000", 3 ,2, 6 ,7 , 9);
        System.out.println(listtrans.estaVazio());
        listtrans.addT(p1);
        listtrans.addT(p2);
        System.out.println(listtrans.estaVazio());
        int n = listtrans.getQuantidade();
        System.out.println(n);
        listtrans.removerT(p2);
        System.out.println(listtrans.estaVazio());
        int j = listtrans.getQuantidade();
        System.out.println(j);
        listtrans.removerT(p1);
        System.out.println(listtrans.estaVazio());


*/



        AtorDB novo = new AtorDB();
        novo.Add(args[0], args[1]);

        System.out.println(novo.getUtilizadores());

        System.out.println(novo.verificaLogin(args[0], args[1]));
        System.out.println(novo.verificaLogin("ola", "vitoria"));
        System.out.println(novo.isLogInfo());
    }
}





