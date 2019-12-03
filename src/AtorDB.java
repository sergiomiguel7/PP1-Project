
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;


public class AtorDB implements Serializable {
    private Map<String,Ator> utilizadores;
    private boolean logInfo;

    public AtorDB() {
        this.utilizadores = new HashMap<>();
        logInfo=false;
    }

    public AtorDB(AtorDB db)
    {
        this.utilizadores=db.getUtilizadores();
    }
    public Map<String,Ator> getUtilizadores()
    {
        return this.utilizadores;

    }

    public Ator getAtor(String user) {user=user.toLowerCase(); return this.utilizadores.get(user); }

    public Transportes getTransportes(String user)
    {
        user=user.toLowerCase();
        return (Transportes) this.utilizadores.get(user.toLowerCase());
    }

    public void setUtilizadores(HashMap<String,Ator> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public boolean isLogInfo() {
        return logInfo;
    }

    public void setLogInfo(boolean logInfo) {
        this.logInfo = logInfo;
    }

    public void Add(String user, Ator a) {

        this.utilizadores.put(user.toLowerCase(), a);
    }

    public int getQuantidade() {
        return utilizadores.size();
    }

    public boolean estaVazio() {
        return utilizadores.isEmpty();
    }

    @Override
    public AtorDB clone() {
        return new AtorDB(this);
    }

    public boolean verificaLogin(String name, String pass) {

        name = name.toLowerCase();
        if(this.utilizadores.containsKey(name) && this.utilizadores.get(name).getPassword().equals(pass)) {
                this.logInfo=true;
        }
        else
            this.logInfo=false;



        return this.logInfo;
    }

    public void atualizaPedidos()
    {
        for(Ator a : this.utilizadores.values())
        {
            a.getHistorico().getPedidos().stream()
                    .filter(pedido -> pedido.getDataFim().isBefore(LocalDateTime.now()))
                    .forEach(pedido -> pedido.setConcluido(true));

            if(a instanceof Transportes){
                if(a.getHistorico().getPedidos().size() == a.getHistorico().getPedidosConcluidos().size()){
                    ((Transportes) a).setDisponivel(true);
                }
            }
        }
    }

    public void classificar(Ator a1) throws NoSuportedException {
        Scanner ler = new Scanner(System.in);
        List<Pedido> porAvaliar= a1.getHistorico().classificarPedidos();
        if(porAvaliar.size()>0){
            for(Pedido pedido : porAvaliar){
                System.out.println("Deseja avaliar o pedido da transportadora "+this.pedidoData(pedido).getNome() + "?");
                String aux= ler.next();
                if(aux.toLowerCase().equals("sim")) {
                    System.out.println("Classifique de 1-5:");
                    int classificacao = ler.nextInt();
                    if(classificacao<=5)
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
        atualizaClassificacao();
    }

    public void atualizaClassificacao(){
        double total;
        double i;
        for(Ator a : this.getUtilizadores().values()){
            if(a instanceof Transportes){
                total=0;
                i=0;
                for(Pedido pedido : a.getHistorico().getPedidosConcluidos()) {
                    double aux = pedido.getClassificacao();
                    if (aux > 0) {
                        total += aux;
                        i++;
                    }
                }
                ((Transportes) a).setClassificacao(total/i);
            }
        }



    }

    public Ator pedidoData(Pedido pedido){

        for(Ator a : this.getUtilizadores().values()){
            if(a instanceof Transportes){
                for(Pedido p : a.getHistorico().getPedidos()){
                    if(p.equals(pedido) && p.getDataFim().isEqual(pedido.getDataFim())){
                        return a;
                    }
                }
            }
        }
        return null;
    }

    public void gravaFicheiro()
    {
        try{
            File fileOne=new File("Dados.ppI");
            FileOutputStream fos=new FileOutputStream(fileOne);
            ObjectOutputStream oos=new ObjectOutputStream(fos);

            oos.writeObject(this.utilizadores);
            oos.flush();
            oos.close();
            fos.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }



    public void leFicheiro()
    {
        try{
            File toRead=new File("Dados.ppI");
            FileInputStream fis=new FileInputStream(toRead);
            ObjectInputStream ois=new ObjectInputStream(fis);
            HashMap<String,Ator> mapInFile=(HashMap<String,Ator>)ois.readObject();
            ois.close();
            fis.close();
            //print All data in MAP
            this.utilizadores=mapInFile;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
