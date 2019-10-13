public class Testes {
    public static void main(String[] args)
    {
        TransportesDB listtrans = new TransportesDB();
        Login login = new Login();
        Transportes p1,p2;
        p1 = new Transportes("sergio@", "Sergio", "a", "Guim", "1 3 2000", 1 , 2, 3, 2,3);
        p2 = new Transportes("jose@","Jose","b","Braga","1 2 2000", 3 ,2, 6 ,7 , 9);
        login.Add(p1.nome, p1.password);
        login.Add(p2.nome, p2.password);
        //System.out.println(p1.toString());
       // System.out.println(p2.toString());0
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
        System.out.println(listtrans.estaVazio());*/

    }


}
