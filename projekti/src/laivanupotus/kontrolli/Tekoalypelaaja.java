/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import java.util.Random;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Komentotyyppi;
import laivanupotus.tietorakenteet.Saannot;

/**
 *
 * @author John Lång
 */
public class Tekoalypelaaja extends Pelaaja {
    
    private final Random    ARPOJA;
    private Saannot         saannot;
    
    public Tekoalypelaaja(Random arpoja, Saannot saannot) {
        super();
        this.ARPOJA     = arpoja;
        this.saannot    = saannot;
    }
    
    public Komento ammuSatunnaiseenRuutuun() {
        Komento komento;
        
        int x = ARPOJA.nextInt(saannot.leveys());
        int y = ARPOJA.nextInt(saannot.korkeus());
        komento = new Komento(Komentotyyppi.AMMU, x, y);
        
        return komento;
    }
    
}
