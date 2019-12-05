import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;
import java.util.*;
import java.util.stream.Collectors;

public class Transportes extends Ator {
    private Servico servico;
    private double precoKM;
    private double tempoKM;
    private double autonomia;
    private boolean disponivel;
    private double extra;
    private double descontos;
    private double classificacao;
    private static final long serialVersionUID = 2L;


    //Quanto maior a autonomia, menor o consumo medio;

    public Transportes() {
        super(" ", " ", " ", " ", LocalDate.now(), 0, 0);
        super.setHistorico(new Historico());
        this.precoKM = 0;
        this.tempoKM = 0;
        this.autonomia = 0;
        this.extra = 0;
        this.descontos = 0;
        this.disponivel = true;
        this.classificacao=0;
    }

    public Transportes(double precoKM, double tempoKM, double autonomia, Servico a,double extra,double descontos) {
        this.servico = a;
        this.precoKM = precoKM;
        this.tempoKM = tempoKM;
        this.autonomia = autonomia;
        this.extra = extra;
        this.disponivel = true;
        this.descontos = descontos;
        this.classificacao=0;
    }

    public Transportes(String email, String nome, String password, String morada, LocalDate dataN, Servico a, double tempoKM, double precoKM, double autonomia,double extra,double descontos) {
        super(email, nome, password, morada, dataN, 0, 0);
        super.setHistorico(new Historico());
        this.servico = a;
        this.tempoKM = tempoKM;
        this.precoKM = precoKM;
        this.autonomia = autonomia;
        this.disponivel = true;
        this.extra = extra;
        this.descontos = descontos;
    }

    public Transportes(Transportes transportes) {
        super(transportes.getEmail(), transportes.getNome(), transportes.getPassword(), transportes.getPassword(), transportes.getDataN(), transportes.getX(), transportes.getY());
        super.getHistorico();
        this.servico = transportes.getServico();
        this.tempoKM = transportes.getTempoKM();
        this.precoKM = transportes.getPrecoKM();
        this.autonomia = transportes.getAutonomia();
        this.disponivel = transportes.isDisponivel();
        this.extra = transportes.getExtra();
        this.descontos = transportes.getDescontos();
        this.classificacao=transportes.getClassificacao();
    }

    public double getDescontos() { return descontos; }

    public void setDescontos(double descontos) { this.descontos = descontos; }

    public double getExtra() { return extra; }

    public void setExtra(double extra) { this.extra = extra; }

    public boolean isDisponivel() { return disponivel; }

    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public double getPrecoKM() {
        return precoKM;
    }

    public void setPrecoKM(double precoKM) {
        this.precoKM = precoKM;
    }

    public double getTempoKM() {
        return tempoKM;
    }

    public void setTempoKM(double tempoKM) {
        this.tempoKM = tempoKM;
    }


    public double getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(double autonomia) {
        this.autonomia = autonomia;
    }

    public double getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(double classificacao) {
        this.classificacao = classificacao;
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\nDados:" + super.toString());
        s.append("\nServiço:" + this.getServico());
        s.append("\nPreço por km: " + this.getPrecoKM() );
        s.append("\nTempo por km: "+ this.getTempoKM());
        s.append("\nAutonomia: " + this.getAutonomia());
        s.append("\nPreco Extra: " + this.getExtra());
        if(this.getDescontos() != 0){s.append("\nDesconto: "+this.getDescontos());}
        return s.toString();
    }

    public long tempoRestante()
    {
        Pedido naoC= this.getHistorico().getPedidos().stream()
                .filter(s -> !s.isConcluido())
                .findFirst().get();

        Duration duration = Duration.between(naoC.getDataFim(), LocalDateTime.now());
        return duration.toMinutes();

    }

    //Distância escalar entre os dois pontos\
    public double distanciaXY(Cliente cliente, Transportes transportes) {
        double x1 = (transportes.getX() - cliente.getX());
        double y1 = (transportes.getY() - cliente.getY());
        x1 = x1 * x1;
        y1 = y1 * y1;
        double total = x1 + y1;
        total=Math.sqrt(total);
        return total;
    }

    /*Método Trajeto Tempo
     * Neste método é criada uma simulação do transportador, apresentando o tempo real que este iria demorar, simulando probabilidades
     * de trânsito ou eventuais atrasos.
     * */
    public long trajetoTempo(Transportes transporte,Cliente cliente){
        Random rand = new Random();
        double distancia = distanciaXY(cliente,transporte);
        double tempo = transporte.getTempoKM();
        tempo = tempo  * distancia;
        for(int i = 0;i < distancia; i ++ ){
            int randprob = rand.nextInt(100);
            int randtempo = rand.nextInt(5);
            if(randprob <= 5 ){
                tempo+=randtempo;
            }
        }
        return (long) Math.round(tempo);
    }

    /*Método Trajeto Tempo Teórico
     * Neste método é calculado o tempo esperado, dependendo apenas da distância e da velocidade do transportador
     * */
    public long trajetoTempoTeorico(Transportes transporte,Cliente cliente){
        Random rand = new Random();
        double distancia = distanciaXY(cliente,transporte);
        double tempo = transporte.getTempoKM();
        tempo = tempo  * distancia;
        return (long) tempo;
    }


    /*Método Trajeto Preço
     * É neste método que acontecem todos os cálculos a cerca do preço da transportadora numa certa distância
     * Variáveis presentes:
     * Se o cliente solicitar um serviço de refeições, este apresenta um preço fixo até 5km,
     * Se o pedido for efetuado entre as 21 horas e as 9 horas, o cliente paga uma taxa extra, escolhida pelo transportador,
     * Se a transportadora apresentar um desconto, esta percentagem é descontada no preço final;
     * */
    public double trajetoPreco(Cliente cliente){
        LocalTime inicio = LocalTime.of(9,0,0);
        LocalTime fim = LocalTime.of(21,0,0);
        double preco = 0;
        double dist = 0;
        if(this.servico instanceof SRefeicoes){
            preco = ((SRefeicoes) this.servico).getPrecoFixo();
            dist =  distanciaXY(cliente,this);
            if(dist > 5){
                dist -= 5;
                preco += (dist*getPrecoKM());
            }

        }else {
            preco = getPrecoKM();
            preco *= distanciaXY(cliente, this);
        }

        if (LocalTime.now().isAfter(fim) || LocalTime.now().isBefore(inicio) ) {
            preco += extra;
        }

        if(this.getDescontos()>0) {
            double desconto = preco * (  this.getDescontos() / 100);
            preco=preco-desconto;
        }
        return preco;
    }


    public Iterator<Transportes> transportesDisponiveis(AtorDB db, Servico servico,Cliente cliente){
        Comparator<Transportes >byNome = Comparator.comparing(Transportes::getNome);
        TreeSet<Transportes> res = codicaoTreeSet(byNome,db,servico,cliente);
        if(res!=null)
            return res.iterator();
        else
            return null;
    }


    public Ator transporteMaisRapido(AtorDB db, Servico servico,Cliente cliente){
        Comparator<Transportes> maisRapido = (t1,t2) -> (int) (t1.getTempoKM() - t2.getTempoKM());
        TreeSet<Transportes> res = codicaoTreeSet(maisRapido,db,servico,cliente);
        if(res!=null)
            return res.first();
        else
            return null;
    }

    public Ator transporteMaisBarato(AtorDB db, Servico servico,Cliente cliente){
        Comparator<Transportes> maisBarato = (t1,t2) -> (int) (t1.trajetoPreco(cliente) - t2.trajetoPreco(cliente));
        TreeSet<Transportes> res = codicaoTreeSet(maisBarato,db,servico,cliente);
        if(res!=null)
            return res.first();
        else
            return null;
    }

    public Ator transporteMelhorClassificado(AtorDB db, Servico servico,Cliente cliente){
        Comparator<Transportes> melhorClassificado =(t1, t2) -> { if(t1.getClassificacao() > t2.getClassificacao()) return -1;
            if(t1.getClassificacao()== t2.getClassificacao()) return 0 ;
            else return 1;};
        TreeSet<Transportes> res = codicaoTreeSet(melhorClassificado,db,servico,cliente);
        if(res!=null)
            return res.first();
        else
            return null;
    }


    public List<Transportes> maisServicosEfetuados(AtorDB atordb)
    {
        Comparator<Transportes> byServicosE = (t1,t2)->  (int) (t1.getHistorico().getPedidosConcluidos().size()-t2.getHistorico().getPedidosConcluidos().size());
        List<Integer> valores =   atordb.getUtilizadores().values().stream()
                .filter(ator -> ator instanceof Transportes)
                .map(ator -> ((Transportes) ator).getHistorico().getPedidosConcluidos().size())
                .sorted()
                .limit(5)
                .collect(Collectors.toList());

        List<Transportes> atores = new ArrayList<>();
        for(int i : valores){
            for(Ator a : atordb.getUtilizadores().values()){
                if(a instanceof Transportes && a.getHistorico().getPedidosConcluidos().size() == i && a.getHistorico().getPedidosConcluidos().size()>0){
                    atores.add(((Transportes)a));
                    break;
                }
            }
        }
        atores.sort(byServicosE);
        Collections.reverse(atores);
        return atores;
    }

    public TreeSet<Transportes> codicaoTreeSet(Comparator<Transportes> c, AtorDB db,Servico servico, Cliente cliente){
        try {
            TreeSet<Transportes> res = new TreeSet<>(c);
            List<Transportes> aux = new ArrayList<>();
            for (Ator a : db.getUtilizadores().values()) {
                if (a instanceof Transportes) {
                    if (((Transportes) a).getServico().equals(servico) && ((Transportes) a).isDisponivel() &&
                            ((Transportes) a).getServico().getLimiteT() >= servico.getLimiteT() &&
                            ((Transportes) a).getAutonomia() >= ((Transportes) a).distanciaXY(cliente, db.getTransportes(a.getNome()))) {
                        aux.add((Transportes) a);
                    }
                }
            }
            if(aux.size()==0)
                throw new NoAtorException("Nenhum transportador disponivel");
            res.addAll(aux);
            return res ;
        }catch (NoAtorException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
    *Método trajeto total faturado.
    *Neste método são pedidas duas datas, pega-se em todos os pedidos concluídos neste intervalo e soma-se o valor de todos os pedidos
    */
    public int  totalFaturado()
    {
        Scanner ler = new Scanner(System.in);
        int dia,mes,ano,hora,minutos;
        LocalDateTime data1 = null,data2 = null;
        for(int i=0;i<2; i++){
            System.out.println("Dia Mês Ano Hora Minutos");dia=ler.nextInt();mes=ler.nextInt();ano=ler.nextInt();hora=ler.nextInt();minutos=ler.nextInt();
            if(i==0)
                data1=LocalDateTime.of(ano,mes,dia,hora,minutos);
            else if(i==1)
                data2=LocalDateTime.of(ano,mes,dia,hora,minutos);
        }
        LocalDateTime finalData2 = data2;
        LocalDateTime finalData = data1;
        return this.getHistorico().getPedidosConcluidos().stream()
                .filter(pedido -> pedido.getDataFim().isBefore(finalData2) && pedido.getDataFim().isAfter(finalData))
                .mapToInt(pedido -> (int) Math.round(pedido.getPreco()))
                .sum();

    }

}