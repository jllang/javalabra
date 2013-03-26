/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import laivanupotus.tietorakenteet.Pelialue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.kayttoliittymat.Tekstikayttoliittyma;
import laivanupotus.poikkeukset.*;
import laivanupotus.rajapinnat.Kayttoliittyma;
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
 * @author John Lång
 */
public class PelialueTest {
    
    private static Random           arpoja;
    private static SaantojenArpoja  saantokone;
    private static Kayttoliittyma   kayttoliittyma;
    private static Pelaaja          leikkipelaaja1, leikkipelaaja2;
    private int                     leveys, korkeus, x, y;
    private Pelikierros             pelikierros;
    private Pelialue                pelialue1, pelialue2;
    
    
    public PelialueTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        arpoja          = new Random();
        saantokone      = new SaantojenArpoja(arpoja);
        kayttoliittyma  = new Tekstikayttoliittyma();
        leikkipelaaja1  = new Ihmispelaaja();
        leikkipelaaja2  = new Ihmispelaaja();
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("================================================================================\n");
    }
    
    @Before
    public void setUp() {
        Saannot saannot = saantokone.arvoSaannot();
        leveys          = saannot.leveys();
        korkeus         = saannot.korkeus();
        pelikierros     = new Pelikierros(kayttoliittyma, saannot, leikkipelaaja1, leikkipelaaja2);
        pelialue1       = new Pelialue(pelikierros, leikkipelaaja1);
        pelialue2       = new Pelialue(pelikierros, leikkipelaaja2);
        x               = arpoja.nextInt(leveys);
        y               = arpoja.nextInt(korkeus);
    }
    
    @After
    public void tearDown() {
        System.out.println("--------------------------------------------------------------------------------\n");
    }
    
    @Test
    public void testAmmu() {
        System.out.println("Testataan ampumista...");
        try {
            pelialue1.ammu(leikkipelaaja2, x, y);
        } catch (Exception poikkeus) {
            System.out.println("Saatiin kiinni poikkeus. (" + poikkeus + ")");
            fail("Ruutuun ei voitu ampua odotetusti.");
        }
    }
    
    @Test
    public void testAmmuKaksiKertaaSamaanRuutuun() {
        System.out.println("Testataan ampumista samaan ruutuun kahdesti peräkkäin...");
        try {
            pelialue1.ammu(leikkipelaaja2, x, y);
            pelialue1.ammu(leikkipelaaja2, x, y);
        } catch (Exception poikkeus) {
            kasittelePoikkeus(RuutuunOnJoAmmuttuException.class, poikkeus);
            return;
        }
        fail("Samaan ruutuun pystyttiin ampumaan kaksi kertaa.");
    }
    
    @Test
    public void testAmmuOmaanRuutuun() {
        System.out.println("Testataan omaan ruutuun ampumista...");
        try {
            pelialue1.ammu(leikkipelaaja1, x, y);
        } catch (Exception poikkeus) {
            kasittelePoikkeus(VaaranPelaajanRuutuException.class, poikkeus);
            return;
        }
        fail("Pystyttiin ampumaan omaan ruutuun.");
    }
    
    @Test
    public void testAmmuRuudukonUlkopuolelle() {
        System.out.println("Testataan ampua ruudukon ulkopuolelle");
        x = x * -1;
        try {
            pelialue1.ammu(leikkipelaaja2, x, y);
        } catch (Exception poikkeus) {
            kasittelePoikkeus(IndexOutOfBoundsException.class, poikkeus);
            return;
        }
        fail("Pystyttiin ampumaan ruudukon ulkopuolelle koordinaatteihin (" + x + "," + y + ").");
    }

    private void kasittelePoikkeus(Class odotettuPoikkeus, Exception poikkeus) {
        System.out.println("Napattiin poikkeus: " + poikkeus);
        assertEquals("Tarkastetaan virheen luokka...", odotettuPoikkeus, poikkeus.getClass());
    }
    
}
