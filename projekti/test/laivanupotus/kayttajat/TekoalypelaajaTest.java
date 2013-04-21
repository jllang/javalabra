
package laivanupotus.kayttajat;

import java.util.Random;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Saannot;
import laivanupotus.tietorakenteet.enumit.Komentotyyppi;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TekoalypelaajaTest {
    
    private static final Random     ARPOJA = new Random();
    private static final Saannot    SAANNOT = new Saannot();
    
    private Tekoalypelaaja          pelaaja;
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Testataan luokkaa Tekoalypelaaja...");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("==================================================="
                + "=============================\n");
    }
    
    @Before
    public void setUp() {
        this.pelaaja = new Tekoalypelaaja(ARPOJA, SAANNOT);
    }
    
    @After
    public void tearDown() {
        System.out.println("---------------------------------------------------"
                + "-----------------------------\n");
    }

    @Test
    public void testAnnaKomento1() {
        System.out.println("Testataan metodia annaKomento...");
        Komento pyynto = new Komento(Komentotyyppi.TILAKYSELY, 4);
        Komento vastaus = pelaaja.annaKomento(pyynto);
//        Komento odotettu = new Komento(Komentotyyppi.TYHJA, null);
        Komento odotettu = new Komento();
        assertEquals(odotettu.KOMENTOTYYPPI, vastaus.KOMENTOTYYPPI);
        assertEquals(odotettu.PARAMETRIT, vastaus.PARAMETRIT);
    }

//    @Test
//    public void testAnnaKomento2() {
//        System.out.println("Testataan");
//        int pituus = ARPOJA.nextInt(5) + 1;
//        Komento sk = pelaaja.annaKomento(new Komento(Komentotyyppi.SIJOITA_LAIVA, pituus));
//    }
}
