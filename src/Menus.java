import java.time.LocalDateTime;
import java.util.Scanner;

public  class Menus {

    public static Ator menuLogin(AtorDB db, ProgramController aux)
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
                        System.out.println("Escolha o seu tipo de serviço: Pessoas, Bus, Big, Urgentes ou Refeições");
                        String escolhido = ler.next();
                        Servico a= Transportes.escolherServicoT(escolhido);
                        a2 = new Transportes("sergio@gmail.com", "Pedroso", "vitoria", "Rua da", LocalDateTime.now(),a, 3, 5, 7, 2, 7);
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

                }

            }
        } while (op !=0);
    }
*/
}
