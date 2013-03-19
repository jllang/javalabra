/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.poikkeukset.OmistajaOnJoAsetettu;
import laivanupotus.tyypit.Saannot;

/**
 *
 * @author John Lång
 */
public abstract class Pelaaja {
    
    private Ruudukko ruudukko;
    
    public Pelaaja() {
    }
    
    public void luoRuudukko(Saannot saannot) {
        this.ruudukko = new Ruudukko(saannot);
        try {
            this.ruudukko.asetaOmistaja(this);
        } catch (OmistajaOnJoAsetettu ex) {
            Logger.getLogger(Pelaaja.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Ruudukko annaRuudukko() {
        return ruudukko;
    }
    
}
