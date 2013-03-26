/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kayttoliittymat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import laivanupotus.kontrolli.Ihmispelaaja;
import laivanupotus.kontrolli.Pelaaja;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.tietorakenteet.Saannot;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import testilogiikka.SaantojenArpoja;

/**
 *
 * @author johnny
 */
public class TekstikayttoliittymaTest {
    
    private static ByteArrayOutputStream    standardiulostulo;
    private static ByteArrayOutputStream    virheulostulo;
    private static Random                   arpoja;
    private static SaantojenArpoja          saantokone;
    private static Pelaaja                  pelaaja1, pelaaja2;
    
    private Saannot                 saannot;
    private Pelikierros             pelikierros;
    private Pelialue                pelialue1, pelialue2;
    private int                     leveys, korkeus, x, y;
    private Tekstikayttoliittyma    tekstikayttoliittyma;
    
    public TekstikayttoliittymaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        standardiulostulo   = new ByteArrayOutputStream();
        virheulostulo       = new ByteArrayOutputStream();
        arpoja      = new Random();
        saantokone  = new SaantojenArpoja(arpoja);
        pelaaja1    = new Ihmispelaaja();
        pelaaja2    = new Ihmispelaaja();
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("================================================================================\n");
    }
    
    @Before
    public void setUp() {
        saannot                 = saantokone.arvoSaannot();
        leveys                  = saannot.leveys();
        korkeus                 = saannot.korkeus();
        tekstikayttoliittyma    = new Tekstikayttoliittyma();
        pelikierros             = new Pelikierros(tekstikayttoliittyma, saannot, pelaaja1, pelaaja2);
        pelialue1               = pelikierros.annaPelialue1();
        pelialue2               = pelikierros.annaPelialue2();
        tekstikayttoliittyma.asetaPelikierros(pelikierros);
    }
    
    @After
    public void tearDown() {
        System.out.println("--------------------------------------------------------------------------------\n");
    }

//    /**
//     * Test of tulosta method, of class Tekstikayttoliittyma.
//     */
//    @Test
//    public void testTulostaTyhjatRuudukot() {
//        System.out.println("Testataan tulostamista...");
//        tekstikayttoliittyma.asetaKatsoja(pelaaja1);
//        tekstikayttoliittyma.alusta();
//        aloitaKaappaus();
//        tekstikayttoliittyma.tulosta();
//        lopetaKaappaus();
//        
//    }
    
//    @Test
//    public void testTulostaTyhjatVakiokokoisetRuudukot() {
//        String odotettuTuloste =
//                "~~~~~~~~~~ ~~~~~~~~~~                                                           \n"
//                + "~~~~~~~~~~ ~~~~~~~~~~                                                           \n"
//                + "~~~~~~~~~~ ~~~~~~~~~~                                                           \n"
//                + "~~~~~~~~~~ ~~~~~~~~~~                                                           \n"
//                + "~~~~~~~~~~ ~~~~~~~~~~                                                           \n"
//                + "~~~~~~~~~~ ~~~~~~~~~~                                                           \n"
//                + "~~~~~~~~~~ ~~~~~~~~~~                                                           \n"
//                + "~~~~~~~~~~ ~~~~~~~~~~                                                           \n"
//                + "~~~~~~~~~~ ~~~~~~~~~~                                                           \n"
//                + "~~~~~~~~~~ ~~~~~~~~~~";
//        
//        saannot     = new Saannot(10, 10, 0);
//        pelikierros = new Pelikierros(tekstikayttoliittyma, saannot, pelaaja1, pelaaja2);
//        tekstikayttoliittyma.asetaPelikierros(pelikierros);
//        tekstikayttoliittyma.asetaKatsoja(pelaaja1);
//        tekstikayttoliittyma.alusta();
//        aloitaKaappaus();
//        tekstikayttoliittyma.tulosta();
//        assertEquals(odotettuTuloste, standardiulostulo.toString().trim());
//        lopetaKaappaus();
//    }
    
    @Test
    public void tyhjaTesti() {}

    private void aloitaKaappaus() {
        System.setOut(new PrintStream(standardiulostulo));
        System.setErr(new PrintStream(virheulostulo));
    }

    private void lopetaKaappaus() {
        System.setOut(null);
        System.setErr(null);
    }
}
