import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public  class Menus {

    public static Ator menuLogin(AtorDB db, ProgramController aux)
    {
        Scanner ler = new Scanner(System.in);
        Ator a1 = new Ator();
        int op;
        do {
            System.out.println("1-Login\n2-Registar\n0-Sair");
            op=ler.nextInt();
            switch (op) {
                case 1: {       //LOGIN!!!!!
                    System.out.print("Nome:");String user = ler.next().toLowerCase();
                    System.out.print("Password:");String pass = ler.next();
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
                        a1 = new Cliente();
                        a1 = ((Cliente)a1).addCliente();
                        db.Add(a1.getNome(), a1);
                    }
                    else if(op==2) {
                        a1 = new Transportes();
                        System.out.println("Escolha o seu tipo de serviço: Pessoas, Bus, Big, Urgentes ou Refeições");
                        String escolhido = ler.next();
                        Servico a = Transportes.escolherServicoT(escolhido);
                        a1 = ((Transportes)a1).addTransporte(a);
                        db.Add(a1.getNome(), a1);
                    }
                    else
                        System.out.println("Invalido");

                }
                break;
                case 0: {
                    System.out.println("Sair");
                    db.gravaFicheiro();
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
        long tempo;
        double custo;
        double x,y;
        do {
            db.atualizaPedidos();
            System.out.println("1-Efetuar Pedido\n2-Mostrar histórico de pedidos\n3-Alterar dados\n4-Repetir serviço\n5-Lista de transportadoras com mais serviços efetuados\n0-Sair");
            op=ler.nextInt();
            switch (op)
            {
                case 1:
                {
                    
                    System.out.println("Escolha o tipo de serviço que deseja: Pessoas, Bus, Big, Urgentes ou Refeições");
                    String escolhido = ler.next();

                    if(escolhido.equalsIgnoreCase("Pessoas") || escolhido.equalsIgnoreCase("Bus"))
                        System.out.print("Escreva as coordenadas de onde se encontra:");
                    else
                        System.out.print("Escreva as coordenadas do vendedor:");
                    System.out.print("Coordenada X:");x=ler.nextDouble();
                    System.out.print("Coordenada Y:");y=ler.nextDouble();
                    ((Cliente)a1).atualizarCoordenadas(x,y,db);
                    Servico servico = Cliente.escolherServicoC(escolhido);
                    System.out.println("Escolha:\n1- Mostrar todos disponiveis\n2- Escolher o mais rapido\n3- Escolher o mais barato\n4- Com tempo e custo máximo");
                    int op2 = ler.nextInt();
                    switch (op2){
                        case 1:{
                            ((Cliente) a1).transportesDisponiveis(servico, db);
                            String escolherTransportadora = ler.next();
                            ((Cliente) a1).AddPedido(db.getTransportes(escolherTransportadora),servico);
                            System.out.println("Tempo estimado de espera:"+db.getTransportes(escolherTransportadora).trajetoTempoTeorico(db.getTransportes(escolherTransportadora),(Cliente)a1));
                            break;
                        }
                        case 2:{
                            Transportes t1 = (Transportes) ((Cliente) a1).transporteMaisRapido(servico, db);
                            ((Cliente) a1).AddPedido(t1,servico);
                            System.out.println("Tempo estimado de espera:"+t1.trajetoTempoTeorico(t1,(Cliente)a1));
                            break;
                        }
                        case 3:{
                            Transportes t1 = (Transportes) ((Cliente) a1).transporteMaisBarato(servico, db);
                            ((Cliente) a1).AddPedido(t1,servico);
                            System.out.println("Tempo estimado de espera:"+t1.trajetoTempoTeorico(t1,(Cliente)a1));
                            break;
                        }
                        case 4:{
                            System.out.println("Tempo Maximo:");
                            tempo=ler.nextLong();
                            System.out.println("Custo Maximo:");
                            custo=ler.nextDouble();
                            ((Cliente)a1).maximoTempoCusto(db,tempo,custo,servico);
                            String escolherTransportadora = ler.next();
                            ((Cliente) a1).AddPedido(db.getTransportes(escolherTransportadora),servico);
                            System.out.println("Tempo estimado de espera:"+db.getTransportes(escolherTransportadora).trajetoTempoTeorico(db.getTransportes(escolherTransportadora),(Cliente)a1));
                            break;
                        }

                    }
                    break;
                }
                case 2: {
                    System.out.println(a1.getHistorico().getPedidosConcluidos());
                    break;
                }
                case 3: {
                    System.out.println(a1.toString());
                    System.out.println("Sair");
                    System.out.println("\nO que pretende mudar?");
                    String opcao = ler.next();
                    a1.alteraDados(opcao, ler);
                    break;
                }
                case 4:{

                    Pedido antigo = a1.getHistorico().getPedido(a1.getHistorico().getPedidosConcluidos());
                    Transportes requisitado= ((Transportes)db.pedidoData(antigo));
                    if(antigo!=null ) {
                        if(requisitado.isDisponivel())
                            ((Cliente) a1).AddPedido(db.pedidoData(antigo), ((Transportes) db.pedidoData(antigo)).getServico());
                        else
                            System.out.println("Transportadora estará disponivel dentro de" + requisitado.tempoRestante() + "minutos");
                    }
                    break;
                }
                case 5: {
                    ((Cliente) a1).maisServicosEfetuados(db);
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
                db.atualizaPedidos();
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
