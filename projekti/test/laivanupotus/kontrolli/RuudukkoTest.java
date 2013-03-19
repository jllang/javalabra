/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.poikkeukset.*;
import laivanupotus.tyypit.Saannot;
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
public class RuudukkoTest {
    
    private static Random   arpoja;
    private static Pelaaja  leikkipelaaja1, leikkipelaaja2;
    private int             leveys, korkeus, x, y;
    private Ruudukko        ruudukko1, ruudukko2;
    
    
    public RuudukkoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        arpoja          = new Random();
        leikkipelaaja1  = new Ihmispelaaja();
        leikkipelaaja2  = new Ihmispelaaja();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        leveys          = arpoja.nextInt(15) + 5;
        korkeus         = arpoja.nextInt(15) + 5;
        Saannot saannot = new Saannot(leveys, korkeus, 0);
        leikkipelaaja1.luoRuudukko(saannot);
        leikkipelaaja2.luoRuudukko(saannot);
        ruudukko1       = leikkipelaaja1.annaRuudukko();
        ruudukko2       = leikkipelaaja2.annaRuudukko();
        x               = arpoja.nextInt(leveys);
        y               = arpoja.nextInt(korkeus);
    }
    
    @After
    public void tearDown() {
        System.out.println("--------------------------------------------------------------------------------\n");
    }

//    @Test
//    public void testRuudukonKonstruktori() {
//        Ruudukko ruudukko3 = new Ruudukko(new Saannot(leveys, korkeus, 0));
//    }
    
    @Test
    public void testAmmu() {
        System.out.println("Testataan ampumista...");
        try {
            ruudukko1.ammu(leikkipelaaja2, x, y);
        } catch (Exception poikkeus) {
            System.out.println("Saatiin kiinni poikkeus. (" + poikkeus + ")");
            fail("Ruutuun ei voitu ampua odotetusti.");
        }
    }
    
    @Test
    public void testAmmuKaksiKertaaSamaanRuutuun() {
        System.out.println("Testataan ampumista samaan ruutuun kahdesti peräkkäin...");
        try {
            ruudukko1.ammu(leikkipelaaja2, x, y);
            ruudukko1.ammu(leikkipelaaja2, x, y);
        } catch (Exception poikkeus) {
            kasittelePoikkeus(RuutuunOnJoAmmuttu.class, poikkeus);
            return;
        }
        fail("Samaan ruutuun pystyttiin ampumaan kaksi kertaa.");
    }
    
    @Test
    public void testAmmuOmaanRuutuun() {
        System.out.println("Testataan omaan ruutuun ampumista...");
        try {
            ruudukko1.ammu(leikkipelaaja1, x, y);
        } catch (Exception poikkeus) {
            kasittelePoikkeus(VaaranPelaajanRuutu.class, poikkeus);
            return;
        }
        fail("Pystyttiin ampumaan omaan ruutuun.");
    }
    
    @Test
    public void testAmmuRuudukonUlkopuolelle() {
        System.out.println("Testataan ampua ruudukon ulkopuolelle");
        x = x * -1;
        try {
            ruudukko1.ammu(leikkipelaaja2, x, y);
        } catch (Exception poikkeus) {
            kasittelePoikkeus(IndexOutOfBoundsException.class, poikkeus);
            return;
        }
        fail("Pystyttiin ampumaan ruudukon ulkopuolelle.");
    }

    private void kasittelePoikkeus(Class odotettuPoikkeus, Exception poikkeus) {
        System.out.println("Napattiin poikkeus: " + poikkeus);
        assertEquals("Tarkastetaan virheen luokka...", odotettuPoikkeus, poikkeus.getClass());
    }
    
}
