/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import java.util.Random;
import laivanupotus.kayttajat.Ihmispelaaja;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kayttoliittymat.Tekstikayttoliittyma;
import laivanupotus.poikkeukset.RuudussaOnJoLaivaException;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Saannot;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author John Lång
 */
public class LaivojenArpojaTest {

    private static Kayttoliittyma           kayttoliittyma;
    private static Poikkeustenkasittelija   poikkeustenkasittelija;
    private static Random                   arpoja;
    private static Pelaaja                  pelaaja1, pelaaja2;
    
    
    private Saannot         saannot;
    private Pelikierros     pelikierros;
    private LaivojenArpoja  laivojenArpoja;
    
    public LaivojenArpojaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        kayttoliittyma          = new Tekstikayttoliittyma(false);
        poikkeustenkasittelija  = new Poikkeustenkasittelija(kayttoliittyma, true, false);
        arpoja                  = new Random();
        pelaaja1                = new Ihmispelaaja();
        pelaaja2                = new Ihmispelaaja();
        kayttoliittyma.asetaKatsoja(pelaaja1);
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("==================================================="
                + "=============================\n");
    }
    
    @Before
    public void setUp() {
        this.saannot        = new Saannot();
        this.pelikierros    = new Pelikierros(kayttoliittyma,
                poikkeustenkasittelija, arpoja, saannot, pelaaja1, pelaaja2);
        kayttoliittyma.asetaPelikierros(pelikierros);
        kayttoliittyma.alusta();
        this.laivojenArpoja = new LaivojenArpoja(saannot, arpoja);
    }
    
    @After
    public void tearDown() {
        System.out.println("---------------------------------------------------"
                + "-----------------------------\n");
    }

    @Test
    public void testSijoitaLaivasto() {
        System.out.println("Testataan laivvaston sijoittamista.");
        try {
            laivojenArpoja.sijoitaLaivasto(pelaaja1);
        } catch (Exception poikkeus) {
            System.out.println(poikkeus);
            fail("Laivastoa ei kyetty sijoittamaan.");
        }
    }
}
