/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tyypit.Saannot;

/**
 *
 * @author John Lång
 */
public class Pelikierros {
    
    private final Kayttoliittyma KAYTTOLIITTYMA;
    private final Saannot SAANNOT;
    private final Pelaaja PELAAJA1;
    private final Pelaaja PELAAJA2;
    private final Ruudukko RUUDUKKO1;
    private final Ruudukko RUUDUKKO2;
    
    public Pelikierros(Kayttoliittyma kayttoliittyma, Saannot saannot) {
        this.KAYTTOLIITTYMA = kayttoliittyma;
        this.SAANNOT = saannot;
        this.PELAAJA1 = new Ihmispelaaja();
        this.PELAAJA2 = new Ihmispelaaja();
        PELAAJA1.luoRuudukko(saannot);
        PELAAJA2.luoRuudukko(saannot);
        RUUDUKKO1 = PELAAJA1.annaRuudukko();
        RUUDUKKO2 = PELAAJA2.annaRuudukko();
    }
    
    public Saannot annaSaannot() {
        return SAANNOT;
    }
}
