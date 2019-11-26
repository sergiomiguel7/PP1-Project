import java.io.IOException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public  class Menus {


    public static Ator menuLogin(AtorDB db, ProgramController aux)
    {
        Menus menus = new Menus();
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
                case 2: {       //REGISTAR
                    System.out.println("1-Cliente\n2-Fornecedor");
                    op=ler.nextInt();
                    if(op==1) {
                        a1 = new Cliente();
                        a1 = menus.addCliente();
                        if(a1!=null)
                            db.Add(a1.getNome(), a1);
                    }
                    else if(op==2) {
                        a1 = new Transportes();
                        System.out.println("Escolha o seu tipo de serviço: Pessoas, Bus, Big, Urgentes ou Refeições");
                        String escolhido = ler.next();
                        Servico a = Menus.escolherServicoT(escolhido);
                        if(a!=null)
                            a1 = menus.addTransporte(a);
                        if(a1!=null)
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
        Menus menus = new Menus();
        AtorDB clone = db.clone();
        Transportes t1 = new Transportes();
        Pedido p1 = new Pedido();
        Scanner ler = new Scanner(System.in);
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
                    Servico servico = Menus.escolherServicoC(escolhido);
                    System.out.println("Escolha:\n1- Mostrar todos disponiveis\n2- Escolher o mais rapido\n3- Escolher o mais barato\n4- Com tempo e custo máximo");
                    int op2 = ler.nextInt();
                    switch (op2){
                        case 1:{
                            Iterator<Transportes> it = t1.transportesDisponiveis(db,servico,(Cliente) a1);
                            while (it.hasNext())System.out.println(it.next().getNome());  //depois meter mais info
                            String escolherTransportadora = ler.next();
                            ((Cliente) a1).AddPedido(db.getTransportes(escolherTransportadora),servico);
                            System.out.println("Tempo estimado de espera:"+db.getTransportes(escolherTransportadora).trajetoTempoTeorico(db.getTransportes(escolherTransportadora),(Cliente)a1));
                            break;
                        }
                        case 2:{
                            t1 = (Transportes) t1.transporteMaisRapido(db,servico,(Cliente) a1);
                            ((Cliente) a1).AddPedido(t1,servico);
                            System.out.println("Tempo estimado de espera:"+t1.trajetoTempoTeorico(t1,(Cliente)a1));
                            break;
                        }
                        case 3:{
                            t1 = (Transportes) t1.transporteMaisBarato(db,servico,(Cliente) a1);
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
                    menus.alteraDados(ler,a1);
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
                    List<Transportes> nova=t1.maisServicosEfetuados(clone);
                    for (Transportes a : nova){
                        System.out.println("Nome da Transportadora: "+a.getNome()+" Serviços Efetuados:" + a.getHistorico().getPedidosConcluidos().size());
                    }
                    break;
                }
                case 0:

                    break;
            }
        } while (op !=0);
    }

    public static void menuTransportes(AtorDB db,Ator a1){
        Menus menus = new Menus();
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
                       menus.alteraDados( ler,a1);
                       break;
                   }
                   case 0:  break;

               }
            }while (op!=0);
    }

    //Operaçao comum aos atores
    public void alteraDados( Scanner ler,Ator b)
    {
        System.out.println("\nO que pretende mudar?");
        String opcao = ler.next();
        opcao= opcao.toLowerCase();
        opcao=opcao.replace(" ", "");
        boolean continuar=true;
        try{
        while(continuar) {
            switch (opcao) {
                case "e-mail": {
                    String novo = ler.next();
                    b.setEmail(novo);
                    continuar=false;
                    break;
                }
                case "nome": {
                    System.out.println("Introduza novo:");
                    String novo = ler.next();
                    b.setNome(novo);
                    continuar=false;
                    break;
                }
                case "password": {
                    String novo = ler.next();
                    b.setPassword(novo);
                    continuar=false;
                    break;
                }
                case "morada": {
                    String novo = ler.next();
                    b.setMorada(novo);
                    continuar=false;
                    break;
                }
                case "data": {
                    System.out.println("Dia:");
                    int dia = ler.nextInt();
                    System.out.println("Mês:");
                    int mes= ler.nextInt();
                    System.out.println("Ano:");
                    int ano= ler.nextInt();
                    b.setDataN((LocalDate.of(ano,mes,dia)));
                    continuar=false;
                    break;
                }
                case "x": {
                    int novo = ler.nextInt();
                    b.setX(novo);
                    continuar=false;
                    break;
                }
                case "y": {
                    int novo = ler.nextInt();
                    b.setY(novo);
                    continuar=false;
                    break;
                }
                case "serviço":{
                    if(b instanceof Transportes)
                    {
                        System.out.println("Escolha o seu novo tipo de serviço: Pessoas, Bus, Big, Urgentes ou Refeições");
                        String escolhido = ler.next();
                        ((Transportes) b).setServico(Menus.escolherServicoT(escolhido));
                    }
                    break;
                }
                case "preçoporkm": {
                    if (b instanceof Transportes) {
                        double preconovo = ler.nextDouble();
                        ((Transportes) b).setPrecoKM(preconovo);
                    }
                    break;
                }
                case "tempoporkm": {
                    if (b instanceof Transportes) {
                        double temponovo = ler.nextDouble();
                        ((Transportes) b).setTempoKM(temponovo);
                    }
                    break;
                }
                case "autonomia": {
                    if (b instanceof Transportes) {
                        double autonomianova = ler.nextDouble();
                        ((Transportes) b).setAutonomia(autonomianova);
                    }
                    break;
                }
                case "preçoextra": {
                    if (b instanceof Transportes) {
                        double preconovo = ler.nextDouble();
                        ((Transportes) b).setExtra(preconovo);
                    }
                    break;
                }
                case "sair":{ continuar=false; break;}
                default: {
                    System.out.println("Input invalido");
                    opcao=ler.nextLine();
                    break;
                }

            }
        }} catch (InputMismatchException e){
            System.out.println("Dados introduzidos inválidos");
        }


    }


    //Operações Clientes

    public Cliente addCliente(){
        Scanner ler = new Scanner(System.in);
        Scanner ler2= new Scanner(System.in).useDelimiter("\n");
        try {
            System.out.print("Nome:");
            String nome = ler.next();
            System.out.print("Password:");
            String pass = ler.next();
            System.out.print("E-mail:");
            String email = ler.next();
            System.out.print("Morada:");
            String morada = ler2.next();
            System.out.print("Data de Nascimento(dia mes ano):");
            int dia = ler.nextInt();
            int mes = ler.nextInt();
            int ano = ler.nextInt();
            LocalDate datan = LocalDate.of(ano, mes, dia);
            System.out.print("Coordenadas de Entregas (x y):");
            int x = ler.nextInt();
            int y = ler.nextInt();

            return new Cliente(email,nome,pass,morada,datan,x,y);
        } catch (InputMismatchException e){
            System.out.println("Dados introduzidos inválidos");
            return null;
        }
    }

    public static Servico escolherServicoC (String a)
    {
        Scanner ler = new Scanner(System.in);
        Servico novo;
        try {
            while (true) {
                if (a.equalsIgnoreCase("Pessoas")) {
                    System.out.println("Quantas pessoas são para transportar?");
                    int limit = ler.nextInt();
                    System.out.println("Pretende levar consigo crianças?");
                    boolean criancas = false;
                    if (ler.next().equalsIgnoreCase("Sim"))
                        criancas = true;
                    novo = new SPessoas(limit, criancas);
                    break;
                } else if (a.equalsIgnoreCase("Bus")) {

                    System.out.println("Quantas pessoas são para transportar?");
                    int limit = ler.nextInt();
                    System.out.println("Pretende levar consigo crianças?");
                    boolean criancas = false;
                    if (ler.next().equalsIgnoreCase("Sim"))
                        criancas = true;
                    novo = new SBus(limit, criancas);
                    break;
                } else if (a.equalsIgnoreCase("Big")) {
                    System.out.println("Total de carga que pretende transportar?");
                    int limit = (int) ler.nextInt();
                    novo = new SBig(limit);
                    break;
                } else if (a.equalsIgnoreCase("Urgentes")) {
                    System.out.println("Total de produtos que pretende transportar?");
                    int limit = (int) ler.nextInt();
                    novo = new SUrgentes(limit);
                    break;
                } else if (a.equalsIgnoreCase("Refeições")) {
                    System.out.println("Quantas refeições pretende transportar?");
                    int limit = (int) ler.nextInt();
                    novo = new SRefeicoes(limit);
                    break;
                }
            }
        } catch (InputMismatchException e){
        System.out.println("Dados introduzidos inválidos");
        return null;
    }
        return novo;
    }

    //Operaçoes Transportes

    public Transportes addTransporte(Servico a){

        Scanner ler = new Scanner(System.in);
        Scanner ler2 = new Scanner(System.in).useDelimiter("\n");
        try {
            System.out.print("Nome:");
            String nome = ler.next();
            System.out.print("Password:");
            String pass = ler.next();
            System.out.print("E-mail:");
            String email = ler.next();
            System.out.print("Morada:");
            String morada = ler2.next();
            System.out.print("Data de Nascimento(dia mes ano):");
            int dia = ler.nextInt();
            int mes = ler.nextInt();
            int ano = ler.nextInt();
            LocalDate datan = LocalDate.of(ano, mes, dia);
            System.out.print("Preço por Km:");
            double precoKM = ler.nextDouble();
            System.out.print("Tempo por Km:");
            double tempoKM = ler.nextDouble();
            System.out.print("Autonomia:");
            double autonomia = ler.nextDouble();
            System.out.print("Preco extra (noturno):");
            double extra = ler.nextDouble();
            return new Transportes(email, nome, pass, morada, datan, a, tempoKM, precoKM, autonomia, extra);
        }catch (InputMismatchException e){
        System.out.println("Dados introduzidos inválidos");
        return null;
    }
    }

    public static Servico escolherServicoT(String a)
    {
        Scanner ler = new Scanner(System.in);
        Servico novo;
        try{
        while(true) {
            if (a.equalsIgnoreCase("Pessoas"))
            {
                int limit;
                boolean criancas = false;
                System.out.println("Maximo de pessoas que vai transportar [1,7]:");
                limit = ler.nextInt();
                System.out.println("Permite o transporte de crianças?(Sim ou Não)");
                if (ler.next().equalsIgnoreCase("sim"))
                    criancas = true;
                else
                    criancas = false;
                novo = new SPessoas(limit, criancas);
                break;
            } else if (a.equalsIgnoreCase("Bus"))
            {
                int limit;
                boolean criancas = false;
                System.out.println("Maximo de pessoas que vai transportar [1,68]:");
                limit = ler.nextInt();
                System.out.println("Permite o transporte de crianças?(Sim ou Não)");
                if (ler.next().equalsIgnoreCase("sim"))
                    criancas = true;
                else
                    criancas = false;
                novo = new SBus(limit, criancas);
                break;
            } else if (a.equalsIgnoreCase("Big")) {
                int limit;
                boolean criancas = false;
                System.out.println("Maximo de carga que vai transportar (até 6 toneladas):");
                limit = ler.nextInt();
                novo = new SBig(limit);
                break;
            } else if (a.equalsIgnoreCase("Urgentes")) {
                int limit;
                System.out.println("Maximo de produtos por utilizador que pode transportar:");
                limit=ler.nextInt();
                novo= new SUrgentes(limit);
                break;
            } else if (a.equalsIgnoreCase("Refeições")) {
                int limit;
                System.out.println("Maximo de refeições por utilizador que pode transportar[1,15]:");
                limit=ler.nextInt();
                novo= new SRefeicoes(limit);
                break;
            }
        }
        }catch (InputMismatchException e){
                System.out.println("Dados introduzidos inválidos");
                return null;
            }

        return novo;
    }
}



