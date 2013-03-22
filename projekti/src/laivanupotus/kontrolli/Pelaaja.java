/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.poikkeukset.OmistajaOnJoAsetettuException;
import laivanupotus.tyypit.Saannot;

/**
 *
 * @author John Lång
 */
public abstract class Pelaaja {
    
    private Pelialue pelialue;
    
    public Pelaaja() {
    }
    
    public void luoPelialue(Saannot saannot) {
        this.pelialue = new Pelialue(saannot);
        try {
            this.pelialue.asetaOmistaja(this);
        } catch (OmistajaOnJoAsetettuException ex) {
            Logger.getLogger(Pelaaja.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Pelialue annaPelialue() {
        return pelialue;
    }
    
}
