
import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class AtorDB implements Serializable {
    private Map<String,Ator> utilizadores;
    private boolean logInfo;


    public AtorDB() {
        this.utilizadores = new HashMap<>();
        logInfo=false;
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

    public Ator pedidoData(Pedido pedido){

        for(Ator a : this.getUtilizadores().values()){
            if(a instanceof Transportes){
                for(Pedido p : a.getHistorico().getPedidos()){
                    if(p.equals(pedido)){
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
            File fileOne=new File("Dados.csv");
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
            File toRead=new File("Dados.csv");
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
