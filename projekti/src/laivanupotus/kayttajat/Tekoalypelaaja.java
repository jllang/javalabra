/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kayttajat;

import laivanupotus.kayttajat.Pelaaja;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.enumit.Komentotyyppi;
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
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException keskeytys) {
            Logger.getLogger(Tekoalypelaaja.class.getName()).log(Level.SEVERE, null, keskeytys);
        }
        
        return komento;
    }
    
}
