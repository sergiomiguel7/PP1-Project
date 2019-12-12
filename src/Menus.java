import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public  class Menus {

    private Scanner ler;

    public Menus(){
        ler = new Scanner(System.in);
    }


    public Ator menuLogin(AtorDB db, ProgramController aux)
    {

        Ator a1 = new Ator();
        int op;
        Scanner ler2 = new Scanner(System.in).useDelimiter("\n");
        try {
            do {
                op = mostraOpcoes("Menu Principal",
                        new String[] {"Login",
                                "Registar"});
                switch (op) {
                    case 1: {       //LOGIN!!!!!
                        System.out.print("E-mail:");
                        String user = ler2.next().toLowerCase();
                        System.out.print("Password:");
                        String pass = ler.next();
                        if (db.verificaLogin(user, pass)) {
                            System.out.println("Login valido");
                            aux.setExistente(true);
                            a1 = db.getAtor(user);
                            return a1;
                        } else {
                            System.out.println("Login invalido");
                            aux.setExistente(false);
                        }

                    }
                    break;
                    case 2: {       //REGISTAR
                        op = mostraOpcoes("Menu Registar",
                                new String[] {"Cliente",
                                        "Transportadora"});
                        if (op == 1) {
                            a1 = new Cliente();
                            a1 = addCliente(db);
                            if (a1 != null)
                                db.Add(a1.getEmail(), a1);
                        } else if (op == 2) {
                            a1 = new Transportes();
                            System.out.println("Escolha o seu tipo de serviço: Pessoas, Bus, Big, Urgentes ou Refeições");
                            String escolhido = ler.next();
                            Servico a = escolherServicoT(escolhido);
                            if (a != null)
                                a1 = addTransporte(a, db);
                            if (a1 != null)
                                db.Add(a1.getEmail(), a1);
                        }
                    }break;
                    case 0: {
                        System.out.println("Obrigado pela preferência.");
                        db.gravaFicheiro();
                        System.exit(0);
                    }

                }

            } while (op != 0);
        }catch (InputMismatchException | DateTimeException | NoExistentServiceException e){
            System.out.println(e.getMessage());
            ler= new Scanner(System.in);
        }
        return a1;
    }


    public void menuCliente(AtorDB db, Ator a1) {
        AtorDB clone = db.clone();
        Transportes t1;
        int op;
        long tempo;
        double custo;
        double x,y;
        DecimalFormat fmt = new DecimalFormat("0.00");
        try{
            do {
                t1 = new Transportes();
                db.atualizaPedidos();
                classificar(a1,db);
                op = mostraOpcoes("Menu Cliente",
                        new String[] {"Efetuar pedido",
                                "Mostrar histórico de Pedidos",
                                "Alterar dados",
                                "Repetir serviço",
                                "Lista de transportadoras com mais serviços efetuados"});
                switch (op) {
                    case 1: {
                            System.out.println("Escolha o tipo de serviço que deseja: Pessoas, Bus, Big, Urgentes ou Refeições");
                            String escolhido = ler.next();
                            Servico servico = escolherServicoC(escolhido);
                            if(servico==null)
                                throw new NoExistentServiceException("Serviço inexistente");

                            if(servico instanceof SPessoas || servico instanceof SBus )
                                System.out.println("Escreva as coordenadas de onde se encontra:");
                            else
                                System.out.println("Escreva as coordenadas do vendedor:");
                            System.out.print("Coordenada X:");
                            x = ler.nextDouble();
                            System.out.print("Coordenada Y:");
                            y = ler.nextDouble();
                            ((Cliente) a1).atualizarCoordenadas(x, y, db);
                            int op2 = mostraOpcoes("Escolhas de Transportadora",
                                new String[] {"Mostrar todos disponiveis",
                                        "Escolher o mais rapido",
                                        "Escolher o mais barato",
                                        "Melhor classificado",
                                        "Com tempo e custo máximo"});
                        switch (op2) {

                            case 1: {
                                Iterator<Transportes> it = t1.transportesDisponiveis(db, servico, (Cliente) a1);
                                iteratorToString(it,db,(Cliente)a1,servico);
                                break;
                            }
                            case 2: {
                                t1 = (Transportes) t1.transporteMaisRapido(db, servico, (Cliente) a1);
                                if (t1 != null) {
                                    ((Cliente) a1).AddPedido(t1, servico);
                                    System.out.println("Tempo estimado de espera:" + t1.trajetoTempoTeorico(t1, (Cliente) a1));
                                }
                                break;
                            }
                            case 3: {
                                t1 = (Transportes) t1.transporteMaisBarato(db, servico, (Cliente) a1);
                                if (t1 != null) {
                                    ((Cliente) a1).AddPedido(t1, servico);
                                    System.out.println("Tempo estimado de espera:" + t1.trajetoTempoTeorico(t1, (Cliente) a1));
                                }
                                break;
                            }
                            case 4: {
                                t1=(Transportes)t1.transporteMelhorClassificado(db,servico,(Cliente) a1);
                                if(t1!=null){
                                    ((Cliente) a1).AddPedido(t1, servico);
                                    System.out.println("Tempo estimado de espera:" + t1.trajetoTempoTeorico(t1, (Cliente) a1));
                                }
                                break;
                            }
                            case 5: {
                                System.out.println("Tempo Maximo:");
                                tempo = ler.nextLong();
                                System.out.println("Custo Maximo:");
                                custo = ler.nextDouble();
                                Iterator<Transportes> it = ((Cliente) a1).maximoTempoCusto(db,tempo,custo,servico);
                                if(it.hasNext()){
                                    iteratorToString(it,db,(Cliente)a1,servico);
                                }
                                else{
                                    throw new NoAtorException("Nenhum Transporte disponivel ou capaz de oferecer esses serviços!");
                                }

                                break;
                            }
                        }

                        break;
                    }
                    case 2: {
                        List<Pedido> nova=a1.getHistorico().getPedidosConcluidos();
                        if(nova.size()==0)
                            throw new NoStoredDataException("Utilizador sem nenhum pedido concluido");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm");
                        nova.stream().forEach(pedido -> System.out.println(db.pedidoData(pedido).getNome()+" "+pedido.getServico().getClass().getSimpleName()+ " " +pedido.getDataInicio().format(formatter) +" "+ pedido.getDataFim().format(formatter) + " " + fmt.format(pedido.getPreco())));
                        break;
                    }
                    case 3: {
                        System.out.println(a1.toString()+ "\nSair\n"+"O que pretende mudar?");
                        ler= ler.useDelimiter("\n");
                        alteraDados(a1,ler.next(),db);
                        break;
                    }
                    case 4: {
                        int i=1;
                        Pedido antigo=null;
                        if(a1.getHistorico().getPedidosConcluidos().size()==0)
                            throw new NoStoredDataException("Utilizador sem nenhum pedido concluido até ao momento");

                        TreeSet<Transportes> semReps = ((Cliente)a1).semRepetidos(db,a1.getHistorico());
                        for(Transportes t: semReps) {
                            System.out.println(i + " Nome: " + t.getNome() + " Classificação: " + fmt.format( t.getClassificacao()));
                            i++;
                        }
                        System.out.println("Escolha o número do serviço que pretende repetir: (0-Sair)");
                        int repetido=ler.nextInt();
                        if(repetido>semReps.size())
                            throw new InputMismatchException("Operação invalida");
                        if(repetido>0) {
                            t1 = (Transportes) semReps.toArray()[repetido-1];
                        }
                        else
                            break;


                        if (t1 != null) {
                            int carga;
                            System.out.println("Serviço da transportadora é :" + t1.getServico().getClass().getSimpleName());
                            System.out.println("Prentende continuar? ");
                            String continuar= ler.next();
                            if(!continuar.toLowerCase().contains("sim"))
                                throw new NoSuportedException("Pede-se desculpa pelo transtorno");
                            if (t1.isDisponivel()) {
                                if(t1.getServico().getClass().getSimpleName().equals("SPessoas") || t1.getServico().getClass().getSimpleName().equals("SBus")){
                                    System.out.println("Pessoas que pretende transportar?");
                                    carga = ler.nextInt();
                                }
                                else{
                                    System.out.println("Carga que pretende transportar?");
                                    carga = ler.nextInt();
                                }
                                if(t1.getServico().getLimiteT() < carga)
                                    throw new NoSuportedException("Transportador não permite esta carga");
                                System.out.print("Coordenada X:");
                                x = ler.nextDouble();
                                System.out.print("Coordenada Y:");
                                y = ler.nextDouble();
                                ((Cliente) a1).atualizarCoordenadas(x, y, db);
                                ((Cliente) a1).AddPedido(t1, t1.getServico());
                                System.out.println("Tempo estimado de espera:" + t1.trajetoTempoTeorico(t1, (Cliente) a1));
                            }
                            else
                                System.out.println("Transportadora estará disponivel dentro de" + t1.tempoRestante() + "minutos");
                        }

                        break;
                    }
                    case 5: {
                        List<Ator> nova = t1.maisServicosEfetuados(clone);
                        if(nova.size()==0)
                            throw new NoStoredDataException("Nenhum transportador com pedidos efetuados");
                        for (Ator a : nova) {
                            if(a instanceof Transportes)
                                System.out.println("Nome da Transportadora: " + a.getNome() + " Serviços Efetuados:" + a.getHistorico().getPedidosConcluidos().size());
                        }
                        break;
                    }
                    case 0:

                        break;
                }
            } while (op != 0);
        }catch (InputMismatchException | NoExistentServiceException | NoStoredDataException | DateTimeException | NoAtorException | NoSuportedException | ExistingAtorException e){
            if(e instanceof InputMismatchException)
                System.out.println("Input inválido");
            else
                System.out.println(e.getMessage());
                ler= new Scanner(System.in);
                menuCliente(db,a1);
        }
    }

    public void menuTransportes(AtorDB db,Ator a1){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm");
        DecimalFormat fmt = new DecimalFormat("0.00");
        int op;
        try{
            do{
                db.atualizaPedidos();
                op = mostraOpcoes("Menu Transportes",
                        new String[] {"Mostrar Pedidos Recentes",
                                "Mostrar Pedidos Concluidos",
                                "Total Faturado",
                                "Alterar dados",
                                "Descontos",
                                "Mostrar Classificação"
                        });
               switch (op){
                   case 1:{
                       if(a1.getHistorico().getPedidos().size()==0)
                           throw new NoExistentServiceException("Sem nenhum pedido registado");
                       a1.getHistorico().getPedidos().stream().sorted((p1, p2) -> {if(p1.getDataInicio().isBefore(p2.getDataInicio())) return 1;
                           if(p1.getDataInicio().isAfter(p2.getDataInicio())) return -1;
                           else return 0;
                       }).limit(5).forEach(pedido -> System.out.println(pedido.getServico().getClass().getSimpleName()+" " + pedido.getDataInicio().format(formatter)+" "+pedido.getDataFim().format(formatter)+" "+pedido.isConcluido()));
                       break;
                   }
                   case 2:{
                       if(a1.getHistorico().getPedidosConcluidos().size()==0)
                           throw new NoExistentServiceException("Sem nenhum pedido registado");
                       a1.getHistorico().getPedidosConcluidos().stream().forEach(pedido -> System.out.println(pedido.getServico().getClass().getSimpleName()+" " + pedido.getDataInicio().format(formatter)+" "+pedido.getDataFim().format(formatter)+" "+pedido.isConcluido()));
                       break;
                   }
                   case 3:{
                       System.out.println("Total faturado no intervalo de tempo: "+ ((Transportes)a1).totalFaturado()+" euros" );
                       break;
                   }
                   case 4:{
                       System.out.println(a1.toString()+ "\nSair\n"+"O que pretende mudar?");
                       ler= ler.useDelimiter("\n");
                       alteraDados(a1,ler.next(),db);
                       break;
                   }
                   case 5:{
                       System.out.println("A sua transportadora está com um desconto de "+((Transportes)a1).getDescontos()+"%");
                       System.out.println("Pretende mudar?");
                       ler = ler.useDelimiter("\n");
                       String str = ler.next();
                       if(str.toLowerCase().equals("sim") ){
                           System.out.println("Qual o novo desconto?");
                               double desc = ler.nextDouble();
                               ((Transportes)a1).setDescontos(desc);
                       }
                       else {
                           if (!str.toLowerCase().equals("não")) {
                               throw new InputMismatchException();
                           }
                       }
                       break;
                   }
                   case 6:{
                       System.out.println("Classificação atual: "+  fmt.format(((Transportes)a1).getClassificacao()));
                   }
                   case 0:  break;

               }
            }while (op!=0);
        }catch (InputMismatchException | NoExistentServiceException | DateTimeException | ExistingAtorException e){
            if(e instanceof  InputMismatchException)
                System.out.println("Invalido");
            else
                System.out.println(e.getMessage());
                ler= new Scanner(System.in);
                menuTransportes(db,a1);
        }
    }

    //Operaçao comum aos atores
    public void alteraDados( Ator b,String opcao, AtorDB db) throws NoExistentServiceException, ExistingAtorException {
        opcao= opcao.toLowerCase();
        opcao=opcao.replace(" ", "");
        if(opcao.contains("limite"))
            opcao="limite";

            switch (opcao) {
                case "e-mail": {
                    throw new ExistingAtorException("E-mail não é possivel alterar.");
                }
                case "nome": {
                    System.out.println("Introduza novo:");
                    String novo = ler.next();
                    if(db.verificaNome(novo))
                        throw new ExistingAtorException("Outra transportadora já possui esse nome");
                    b.setNome(novo);
                    break;
                }
                case "password": {
                    String novo = ler.next();
                    b.setPassword(novo);
                    break;
                }
                case "morada": {
                    String novo = ler.next();
                    b.setMorada(novo);
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
                    break;
                }
                case "x": {
                    int novo = ler.nextInt();
                    b.setX(novo);
                    break;
                }
                case "y": {
                    int novo = ler.nextInt();
                    b.setY(novo);
                    break;
                }
                case "serviço": {
                    if (b instanceof Transportes) {
                        System.out.println("Escolha o seu novo tipo de serviço: Pessoas, Bus, Big, Urgentes ou Refeições");
                        String escolhido = ler.next();
                        Servico servico = escolherServicoT(escolhido);
                        if(servico==null)
                            throw new NoExistentServiceException("Serviço inexistente");
                        ((Transportes) b).setServico(servico);
                        break;
                    }
                }
                case "limite":{
                    if(b instanceof Transportes) {
                        int novolimite=ler.nextInt();
                        ((Transportes) b).getServico().setLimiteT(novolimite);
                        break;
                    }
                }
                case "preçoporkm": {
                    if (b instanceof Transportes) {
                        double preconovo = ler.nextDouble();
                        ((Transportes) b).setPrecoKM(preconovo);
                        break;
                    }

                }
                case "tempoporkm": {
                    if (b instanceof Transportes) {
                        double temponovo = ler.nextDouble();
                        ((Transportes) b).setTempoKM(temponovo);
                        break;
                    }

                }
                case "autonomia": {
                    if (b instanceof Transportes) {
                        double autonomianova = ler.nextDouble();
                        ((Transportes) b).setAutonomia(autonomianova);
                        break;
                    }

                }
                case "preçoextra": {
                    if (b instanceof Transportes) {
                        double preconovo = ler.nextDouble();
                        ((Transportes) b).setExtra(preconovo);
                        break;
                    }

                }
                case "sair":{break;}
                default:throw new InputMismatchException();


            }

    }


    //Operações Clientes

    public Cliente addCliente(AtorDB db){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Scanner ler2= new Scanner(System.in).useDelimiter("\n");
        try {
            System.out.print("Nome:");
            String nome = ler2.next();
            System.out.print("Password:");
            String pass = ler.next();
            System.out.print("E-mail:");
            String email = ler.next();
            if(!pattern.matcher(email).matches())
                throw new InputMismatchException("Email não é válido");
            if(db.getUtilizadores().containsKey(email.toLowerCase()))
                throw new ExistingAtorException("Já existe utilizador com esse e-mail");
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
        } catch (InputMismatchException | ExistingAtorException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Servico escolherServicoC (String a) throws InputMismatchException
    {
        Servico novo;
            while (true) {
                if (a.equalsIgnoreCase("Pessoas")) {
                    System.out.println("Quantas pessoas são para transportar?");
                    int limit = ler.nextInt();
                    System.out.println("Pretende levar consigo crianças?");
                    boolean criancas;
                    String permite = ler.next();
                    if (permite.equalsIgnoreCase("Sim"))
                        criancas = true;
                    else if(permite.equalsIgnoreCase("Nao")||permite.equalsIgnoreCase("Não"))
                        criancas=false;
                    else
                        throw new InputMismatchException();
                    novo = new SPessoas(limit, criancas);
                    break;
                } else if (a.equalsIgnoreCase("Bus")) {

                    System.out.println("Quantas pessoas são para transportar?");
                    int limit = ler.nextInt();
                    System.out.println("Pretende levar consigo crianças?");
                    boolean criancas = false;
                    String permite = ler.next();
                    if (permite.equalsIgnoreCase("Sim"))
                        criancas = true;
                    else if(permite.equalsIgnoreCase("Nao")||permite.equalsIgnoreCase("Não"))
                        criancas=false;
                    else
                        throw new InputMismatchException();
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
                    System.out.println("Pretede que a transportadora mantenha a temperatura constante?(esta opção pode diminuir a probabilidade de disponibilidade do transportador)");
                    String quer= ler.next();
                    novo = new SUrgentes(limit);
                    if(quer.toLowerCase().contains("sim"))
                        ((SUrgentes)novo).temperaturaConstante(true);
                    else
                        ((SUrgentes)novo).temperaturaConstante(false);
                    break;
                } else if (a.equalsIgnoreCase("Refeições")) {
                    System.out.println("Quantas refeições pretende transportar?");
                    int limit = (int) ler.nextInt();
                    System.out.println("Pretede que a transportadora mantenha a temperatura constante?(esta opção pode diminuir a probabilidade de disponibilidade do transportador)");
                    String quer= ler.next();
                    novo = new SRefeicoes(limit);
                    if(quer.toLowerCase().contains("sim"))
                        ((SRefeicoes)novo).temperaturaConstante(true);
                    else
                        ((SRefeicoes)novo).temperaturaConstante(false);
                    break;

                }
                else
                    return null;
            }

        return novo;
    }

    //Operaçoes Transportes

    public Transportes addTransporte(Servico a, AtorDB db){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Scanner ler2 = new Scanner(System.in).useDelimiter("\n");
        try {
            System.out.print("E-mail:");
            String email = ler.next();
            if(!pattern.matcher(email).matches())
                throw new InputMismatchException("Email não é válido");
            if(db.getUtilizadores().containsKey(email.toLowerCase()))
                throw new ExistingAtorException("Utilizador já existente");
            System.out.print("Nome:");
            String nome = ler.next();
            if(db.verificaNome(nome))
                throw new ExistingAtorException("Outra transportadora já possui esse nome");
            System.out.print("Password:");
            String pass = ler.next();
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
            return new Transportes(email, nome, pass, morada, datan, a, tempoKM, precoKM, autonomia, extra,0);
        }catch (InputMismatchException e){
        System.out.println(e.getMessage());
        return null;
        }catch(ExistingAtorException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Servico escolherServicoT(String a) throws NoExistentServiceException
    {
        Servico novo;
        while(true) {
            if (a.equalsIgnoreCase("Pessoas"))
            {
                int limit;
                boolean criancas = false;
                System.out.println("Maximo de pessoas que vai transportar [1,7]:");
                limit = ler.nextInt();
                System.out.println("Permite o transporte de crianças?(Sim ou Não)");
                String permite = ler.next();
                if (permite.equalsIgnoreCase("Sim"))
                    criancas = true;
                else if(permite.equalsIgnoreCase("Nao")||permite.equalsIgnoreCase("Não"))
                    criancas=false;
                else
                    throw new InputMismatchException();
                novo=new SPessoas(limit,criancas);
                break;
            } else if (a.equalsIgnoreCase("Bus"))
            {
                int limit;
                boolean criancas;
                System.out.println("Maximo de pessoas que vai transportar [1,68]:");
                limit = ler.nextInt();
                System.out.println("Permite o transporte de crianças?(Sim ou Não)");
                String permite = ler.next();
                if (permite.equalsIgnoreCase("Sim"))
                    criancas = true;
                else if(permite.equalsIgnoreCase("Nao")||permite.equalsIgnoreCase("Não"))
                    criancas=false;
                else
                    throw new InputMismatchException();
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
                System.out.println("Garante temperatura constante?");
                String quer= ler.next();
                novo= new SUrgentes(limit);
                if(quer.toLowerCase().contains("sim"))
                    ((SUrgentes)novo).temperaturaConstante(true);
                else
                    ((SUrgentes)novo).temperaturaConstante(false);
                break;
            } else if (a.equalsIgnoreCase("Refeições")) {
                int limit;
                System.out.println("Maximo de refeições por utilizador que pode transportar[1,15]:");
                limit=ler.nextInt();
                System.out.println("Garante temperatura constante?");
                String quer= ler.next();

                novo= new SRefeicoes(limit);
                if(quer.toLowerCase().contains("sim"))
                    ((SRefeicoes)novo).temperaturaConstante(true);
                else
                    ((SRefeicoes)novo).temperaturaConstante(false);
                break;
            }
            else throw new NoExistentServiceException("Serviço inexistente");

        }


        return novo;
    }

    public void classificar(Ator a1, AtorDB db) throws NoSuportedException {
        Scanner ler = new Scanner(System.in);
        List<Pedido> porAvaliar= a1.getHistorico().classificarPedidos();
        if(porAvaliar.size()>0){
            for(Pedido pedido : porAvaliar){
                System.out.println("Deseja avaliar o pedido da transportadora "+db.pedidoData(pedido).getNome() + "?");
                String aux= ler.next();
                if(aux.toLowerCase().equals("sim")) {
                    System.out.println("Classifique de 1-5:");
                    int classificacao = ler.nextInt();
                    if(classificacao<=5 && classificacao>0)
                        pedido.setClassificacao(classificacao);
                    else
                        throw new NoSuportedException("Classificação tem de ter valores entre 1 e 5");
                }
                else if(aux.toLowerCase().equals("não"))
                    pedido.setClassificacao(0);
                else
                    throw new NoSuportedException("Inválido! Sim ou Não");
            }
        }
        db.atualizaClassificacao();
    }


    public int mostraOpcoes(String titulo, String[] opcoes) throws InputMismatchException{
        Scanner ler = new Scanner(System.in);
        System.out.println("<=====>" + titulo + "<=====>");
        for (int i = 0; i < opcoes.length; i++) {
            System.out.println((1 + i) + "- " + opcoes[i]);
        }
        System.out.println("0 - Sair");
        int op = ler.nextInt();
        return op;
    }

    /**
     * Método iteratorToString
     *Transforma um iterator de Transportes em uma lista de Strings com informação necessária para os clientes
     * */
    public void iteratorToString(Iterator<Transportes> it,AtorDB db,Cliente a1,Servico servico)throws NoAtorException{
        DecimalFormat fmt = new DecimalFormat("0.00");
        if (it != null) {
            while (it.hasNext()) {
                Transportes aux= it.next();
                System.out.print("Nome: " + aux.getNome() + " Preço por km: " + aux.getPrecoKM() + " Tempo por Km: " + aux.getTempoKM());  //depois meter mais info
                if(aux.getClassificacao()>0 && aux.getDescontos()>0)
                    System.out.println(" Classificação: " + fmt.format(aux.getClassificacao()) + " Desconto atual de: " + aux.getDescontos() + "%");
                else if (aux.getDescontos() > 0 )
                    System.out.println("Desconto atual de: " + aux.getDescontos() + "%");
                else if(aux.getClassificacao()>0 )
                    System.out.println(" Classificação: " + fmt.format(aux.getClassificacao()));
                else System.out.println();
            }
            System.out.println("\nEscolha transportadora pelo nome");
            String escolherTransportadora = ler.next();
            int num = (int)db.getUtilizadores().values().stream().filter(e -> e.getNome().equals(escolherTransportadora)).count();
            if(num == 0){
                throw new InputMismatchException();
            }
            Transportes transportes = db.getUtilizadores().values().stream().filter(e -> e instanceof Transportes)
                    .filter(e -> e.getNome().equals(escolherTransportadora)).map(e -> (Transportes)e ).findFirst().get();
            if(!transportes.isDisponivel())
                throw new NoAtorException("Indisponivel");
            a1.AddPedido(transportes,servico);
            System.out.println("Tempo estimado de espera:" + transportes.trajetoTempoTeorico(transportes, a1));

        }
    }
}



