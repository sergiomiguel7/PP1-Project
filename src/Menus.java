import java.time.LocalDate;
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
                    String user = ler.next().toLowerCase();
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
                        a1 = new Cliente("sergio@gmail.com", "Sergio", "vitoria", "Rua da", LocalDate.now(), 1, 2);
                        db.Add(a1.getNome(), a1);
                    }
                    else if(op==2) {
                        System.out.println("Escolha o seu tipo de serviço: Pessoas, Bus, Big, Urgentes ou Refeições");
                        String escolhido = ler.next();
                        Servico a= Transportes.escolherServicoT(escolhido);
                        a2 = new Transportes("sergio@gmail.com", "Uber", "vitoria", "Rua da", LocalDate.now(),a, 3, 5, 7, 2, 7);
                        db.Add(a2.getNome(), a2);
                    }
                    else
                        System.out.println("Invalido");

                }
                break;
                case 0: {
                    System.out.println("Sair");
                    System.exit(0);
                }

            }

        } while (op!=0);

        return a1;
    }


    public static void menuCliente(AtorDB db, Ator a1)
    {
        Pedido p1 = new Pedido();
        Scanner ler = new Scanner(System.in);
        int escolha;
        int op;
        do {
            System.out.println("1-Efetuar Pedido\n2-Mostrar histórico de pedidos\n3-Alterar dados\n4-Lista de transportadoras com mais serviços efetuados\n0-Sair");
            op=ler.nextInt();
            switch (op)
            {
                case 1:
                {
                    
                    System.out.println("Escolha o tipo de serviço que deseja:");
                    String escolhido = ler.next();
                    Servico servico = Cliente.escolherServicoC(escolhido);
                    System.out.println("Escolha:\n1 - Mostrar todos disponiveis\n2 - Escolher o mais rapido\n 3 - Escolher o mais barato");
                    int op2 = ler.nextInt();
                    switch (op2){
                        case 1:{
                            ((Cliente) a1).transportesDisponiveis(servico, db);
                            String escolherTransportadora = ler.next();
                            ((Cliente) a1).AddPedido(db.getTransportes(escolherTransportadora),servico);

                            break;
                        }
                        case 2:{
                            ((Cliente) a1).AddPedido(((Cliente) a1).transporteMaisRapido(servico, db),servico);
                            break;
                        }
                        case 3:{
                            ((Cliente) a1).AddPedido(((Cliente) a1).transporteMaisBarato(servico, db),servico);
                            break;
                        }

                    }

                }
                case 2: {
                    System.out.println(a1.getHistorico().getPedidos());
                    break;
                }
                case 3: {
                    System.out.println(a1.toString());
                    System.out.println("Sair");
                    System.out.println("\nO que pretende mudar?");
                    String opcao = ler.next();
                    a1.alteraDados(opcao, ler);
                }
                case 4: {
                    System.out.println(((Cliente) a1).maisServicosEfetuados(db));
                    break;
                }
                case 0:

                    break;
            }
        } while (op !=0);
    }

    public static void menuTransportes(AtorDB db,Ator a1){
        Scanner ler = new Scanner(System.in);
        int op;
            do{
               System.out.println("1 - Mostrar Pedidos Recentes\n2 - Mostrar Pedidos Concluidos\n3 - Alterar dados\n0 - Sair");
               op = ler.nextInt();
               switch (op){
                   case 1:{
                       System.out.println(a1.getHistorico().getPedidos());
                       break;
                   }
                   case 2:{
                       System.out.println(a1.getHistorico().getPedidosConcluidos());
                       break;
                   }
                   case 3:{
                       System.out.println(a1.toString());
                       System.out.println("Sair");
                       System.out.println("\nO que pretende mudar?");
                       String opcao = ler.next();
                       a1.alteraDados(opcao, ler);
                       break;
                   }
                   case 0:  break;

               }
            }while (op!=0);
    }
}
