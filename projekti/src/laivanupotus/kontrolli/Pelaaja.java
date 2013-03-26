/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import laivanupotus.tietorakenteet.Pelialue;
import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.poikkeukset.OmistajaOnJoAsetettuException;
import laivanupotus.tietorakenteet.Saannot;

/**
 *
 * @author John Lång
 */
public abstract class Pelaaja {
    
    private Pelialue pelialue;
    
    public Pelaaja() {
    }
    
    public void asetaPelialue(Pelialue pelialue) {
        this.pelialue = pelialue;
    }

    public Pelialue annaPelialue() {
        return pelialue;
    }
    
}
