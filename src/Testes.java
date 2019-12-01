import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Testes
{
        AtorDB nova = new AtorDB();
    /**
     * Default constructor for test class teste
     */
    public Testes()
    {
        String escolhido = "Pessoas";
        Servico a= new SPessoas(5, true);

       Ator a1 = new Cliente("sergio@gmail.com", "Sergio", "vitoria", "Rua da", LocalDate.now(), 1, 2);
       Ator a2 = new Transportes("sergio@gmail.com", "Uber", "vitoriag", "Rua da", LocalDate.now(),a, 7, 2, 7,1,0);
       nova.Add(a1.getNome(), a1);
       nova.Add(a2.getNome(), a2);
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {

    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    @Test
        public void testeRegisto()
    {
        Ator teste = new Cliente("a","Teste", "passteste", "a", LocalDate.now(), 1 , 3);
        nova.Add(teste.getNome(), teste);
        assertTrue(nova.getUtilizadores().containsValue(teste));
    }
    @Test
    public void testeLogin()
    {
        boolean a = nova.verificaLogin("Sergio", "vitoria");
        assertTrue(a);
        boolean b = nova.verificaLogin("Uber", "vitoria");
        assertTrue(!b);
    }
    @Test
    public void testeEfetuarPedido()
    {
        Ator teste = nova.getAtor("Sergio");
        Servico testeS = new SPessoas();
        ((Cliente)teste).AddPedido(nova.getTransportes("Uber"), testeS);
        assertEquals(1, teste.getHistorico().getPedidos().size());
        assertEquals(1, nova.getTransportes("Uber").getHistorico().getPedidos().size());
    }
    @Test
    public void testeTotalFaturado()
    {
        Ator teste = nova.getAtor("Sergio");
        Servico testeS = new SPessoas();
        ((Cliente)teste).AddPedido(nova.getTransportes("Uber"), testeS);
        Transportes teste2 = nova.getTransportes("Uber");
        teste2.getHistorico().getPedidos().get(0).setConcluido(true);
        double total = ((Cliente) teste).faturadoIntervaloTempo(teste2,LocalDateTime.now(), LocalDateTime.now().plusMinutes(60));
        assertEquals(4 , total, 0);
    }

}
