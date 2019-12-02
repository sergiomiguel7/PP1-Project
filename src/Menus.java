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
                        System.out.print("Nome:");
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
                                        "Fornecedor"});
                        if (op == 1) {
                            a1 = new Cliente();
                            a1 = addCliente(db);
                            if (a1 != null)
                                db.Add(a1.getNome(), a1);
                        } else if (op == 2) {
                            a1 = new Transportes();
                            System.out.println("Escolha o seu tipo de serviço: Pessoas, Bus, Big, Urgentes ou Refeições");
                            String escolhido = ler.next();
                            Servico a = escolherServicoT(escolhido);
                            if (a != null)
                                a1 = addTransporte(a, db);
                            if (a1 != null)
                                db.Add(a1.getNome(), a1);
                        }
                    }break;
                    case 0: {
                        System.out.println("Sair");
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
                db.classificar(a1);
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
                                if (it != null) {
                                    while (it.hasNext()) {
                                        Transportes aux=it.next();
                                        System.out.print("Nome: " + aux.getNome() + " Preço por km: " + aux.getPrecoKM() + " Tempo por Km: " + aux.getTempoKM());  //depois meter mais info
                                        if(aux.getClassificacao()>0 && aux.getDescontos()>0)
                                            System.out.println(" Classificação: " + fmt.format(aux.getClassificacao()) + " Desconto atual de: " + aux.getDescontos() + "%");
                                        else if (aux.getDescontos() > 0 )
                                            System.out.println("Desconto atual de: " + aux.getDescontos() + "%");
                                        else if(aux.getClassificacao()>0 )
                                            System.out.println(" Classificação: " + fmt.format(aux.getClassificacao()));

                                    }
                                    System.out.println("Escolha transportadora pelo nome");
                                    String escolherTransportadora = ler.next();
                                    if(!db.getUtilizadores().containsKey(escolherTransportadora.toLowerCase()))
                                        throw new NoAtorException("Transportador inválido");
                                    else if(!db.getTransportes(escolherTransportadora).isDisponivel())
                                        throw new NoAtorException("Indisponivel");
                                    ((Cliente) a1).AddPedido(db.getTransportes(escolherTransportadora), servico);
                                    System.out.println("Tempo estimado de espera:" + db.getTransportes(escolherTransportadora).trajetoTempoTeorico(db.getTransportes(escolherTransportadora), (Cliente) a1));

                                }
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
                                ((Cliente) a1).maximoTempoCusto(db, tempo, custo, servico);
                                String escolherTransportadora = ler.next();
                                ((Cliente) a1).AddPedido(db.getTransportes(escolherTransportadora), servico);
                                System.out.println("Tempo estimado de espera:" + db.getTransportes(escolherTransportadora).trajetoTempoTeorico(db.getTransportes(escolherTransportadora), (Cliente) a1));

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
                        alteraDados(a1,ler.next());
                        break;
                    }
                    case 4: {
                        int i=1;
                        Pedido antigo=null;
                        if(a1.getHistorico().getPedidosConcluidos().size()==0)
                            throw new NoStoredDataException("Utilizador sem nenhum pedido concluido até ao momento");

                        TreeSet<Transportes> semReps = ((Cliente)a1).semRepetidos(db,a1.getHistorico());
                        for(Transportes t: semReps) {
                            System.out.println(i + " " + t.getNome());
                            i++;
                        }
                        System.out.println("Escolha o número do serviço que pretende repetir: (0-Sair)");
                        int repetido=ler.nextInt();
                        if(repetido>a1.getHistorico().getPedidosConcluidos().size())
                            throw new InputMismatchException("Operação invalida");
                        if(repetido>0) {
                            t1 = (Transportes) semReps.toArray()[repetido-1];
                        }


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
                        List<Transportes> nova = t1.maisServicosEfetuados(clone);
                        if(nova.size()==0)
                            throw new NoStoredDataException("Nenhum transportador com pedidos efetuados");
                        for (Transportes a : nova) {
                            System.out.println("Nome da Transportadora: " + a.getNome() + " Serviços Efetuados:" + a.getHistorico().getPedidosConcluidos().size());
                        }
                        break;
                    }
                    case 0:

                        break;
                }
            } while (op != 0);
        }catch (InputMismatchException | NoExistentServiceException | NoStoredDataException| DateTimeException | NoAtorException |NoSuportedException e){
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
                       a1.getHistorico().getPedidos().stream().limit(5).forEach(pedido -> System.out.println(pedido.getServico().getClass().getSimpleName()+" " + pedido.getDataInicio().format(formatter)+" "+pedido.getDataFim().format(formatter)+" "+pedido.isConcluido()));
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
                       alteraDados(a1,ler.next());
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
        }catch (InputMismatchException | NoExistentServiceException | DateTimeException e){
            if(e instanceof  InputMismatchException)
                System.out.println("Invalido");
            else
                System.out.println(e.getMessage());
                ler= new Scanner(System.in);
                menuTransportes(db,a1);
        }
    }

    //Operaçao comum aos atores
    public void alteraDados( Ator b,String opcao) throws NoExistentServiceException {
        opcao= opcao.toLowerCase();
        opcao=opcao.replace(" ", "");
        if(opcao.contains("limite"))
            opcao="limite";

            switch (opcao) {
                case "e-mail": {
                    String novo = ler.next();
                    b.setEmail(novo);
                    break;
                }
                case "nome": {
                    System.out.println("Introduza novo:");
                    String novo = ler.next();
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
            if(db.getUtilizadores().containsKey(nome.toLowerCase()))
                throw new ExistingAtorException("Já existe utilizador com esse nome");
            System.out.print("Password:");
            String pass = ler.next();
            System.out.print("E-mail:");
            String email = ler.next();
            if(!pattern.matcher(email).matches())
                throw new InputMismatchException("Email não é válido");
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
            System.out.println(e.getMessage());
            return null;
        }
        catch(ExistingAtorException e){
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
            System.out.print("Nome:");
            String nome = ler.next();
            if(db.getUtilizadores().containsKey(nome))
                throw new ExistingAtorException("Utilizador já existente");
            System.out.print("Password:");
            String pass = ler.next();
            System.out.print("E-mail:");
            String email = ler.next();
            if(!pattern.matcher(email).matches())
                throw new InputMismatchException("Email não é válido");
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
                    ((SRefeicoes)novo).temperaturaConstante(true);
                else
                    ((SRefeicoes)novo).temperaturaConstante(false);
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
    }





