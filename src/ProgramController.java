import java.time.LocalDateTime;
import java.util.Scanner;

public class ProgramController {
    private boolean isExistente;

    public ProgramController() {
        this.isExistente = false;
    }

    public boolean isExistente() {
        return isExistente;
    }

    public void setExistente(boolean existente) {
        isExistente = existente;
    }

    private static Ator menuLogin(AtorDB db, ProgramController aux)
    {
        Scanner ler = new Scanner(System.in);
        Ator a1 = new Ator();
        Ator a2 = new Ator();

        int op;
        do {
            System.out.println("1-Login\n2-Registar\n0-Sair");
            op=ler.nextInt();
            switch (op) {
                case 1: {       //LOGIN!!!!!
                    String user = ler.next();
                    String pass = ler.next();
                    if (db.verificaLogin(user,pass)) {
                        System.out.println("Login valido");
                        aux.setExistente(true);
                        a1=db.getAtor(user);
                        return a1;
                    } else {
                        System.out.println("Login invalido");
                        aux.setExistente(false);
                    }
                }
                break;
                case 2: {       //REGISTAR!!!!
                    System.out.println("1-Cliente\n2-Fornecedor");
                    op=ler.nextInt();
                    if(op==1) {
                        a1 = new Cliente("sergio@gmail.com", "Sergio", "vitoria", "Rua da", LocalDateTime.now(), 1, 2);
                        db.Add(a1.getNome(), a1);
                    }
                    else if(op==2) {
                        a1 = new Transportes("sergio@gmail.com", "Sergio", "vitoria", "Rua da", LocalDateTime.now(), 1, 2, 3, 5, 6);
                        a2 = new Transportes("sergio@gmail.com", "Pedroso", "vitoria", "Rua da", LocalDateTime.now(), 3, 5, 7, 2, 7);
                        db.Add(a1.getNome(), a1);
                        db.Add(a2.getNome(), a2);
                    }
                    else
                        System.out.println("Invalido");

                }
                break;
                case 0: {
                    System.out.println("Sair");
                }

            }

        } while (op!=0);

return a1;
    }
/*
    private static void menuCliente(AtorDB db, Cliente a1)
    {
        Pedido p1 = new Pedido();
        Scanner ler = new Scanner(System.in);
        int escolha;
        int op;
        do {
            System.out.println("1-Efetuar Pedido\n2-Mostrar histórico de pedidos\n3-Alterar dados\n0-Sair");
            op=ler.nextInt();
            switch (op) {
                case 1:
                    {
                        System.out.println("Tipo de serviço:\n 1-Pessoas\n2-Bus\n3-Cargas\n4-Urgentes\n5-Refeições");
                        escolha=ler.nextInt();
                        if(escolha==1) { int carga= ler.nextInt();p1.setCarga(carga); }
                        break;
                    }

            }
        } while (op !=0);
    }

*/


    public static void main(String[] args) {
        ProgramController aux = new ProgramController();
        AtorDB db = new AtorDB();
        Scanner ler = new Scanner(System.in);
        Ator ator = new Ator();

        ator = menuLogin(db, aux);
        if(aux.isExistente)
        {
            if (ator instanceof Cliente){       //compara se ator e de tipo Cliente ou Transportes
               // menuCliente();

            }
            else if(ator instanceof Transportes) {
                //menuFornecedor
               // System.out.println(((Transportes) ator).toString());
                ((Transportes) ator).getHistorico();
            }
        }


    }


}








