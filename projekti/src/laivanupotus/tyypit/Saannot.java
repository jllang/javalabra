/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.tyypit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import laivanupotus.rajapinnat.Tallennettava;

/**
 *
 * @author John Lång
 */
public class Saannot implements Tallennettava {
    
    private final List<Object> VARASTO;
    
    public Saannot(int leveys, int korkeus, int vuoroja) {
        tarkastaArvot(leveys, korkeus, vuoroja);
        this.VARASTO = new ArrayList<>();
        VARASTO.add(leveys);
        VARASTO.add(korkeus);
        VARASTO.add(vuoroja);
    }
    
    public Saannot() {
        //Oletussäännöt
        this.VARASTO = new ArrayList<>();
        VARASTO.add(10);
        VARASTO.add(10);
        VARASTO.add(0);
    }
    
    private void tarkastaArvot(int leveys, int korkeus, int vuoroja) {
        if (leveys < 5
                || leveys > 20
                || korkeus < 5
                || korkeus > 20
                || vuoroja < 0) {
            throw new IllegalArgumentException("Ruudukon korkeuden tulee olla väliltä [5 ... 20] ja vuorojen määrä ei saa olla negatiivinen.");
        }
    }
    
    public int leveys() {
        return (int) VARASTO.get(0);
    }
    
    public int korkeus() {
        return (int) VARASTO.get(1);
    }
        
    public int vuoroja() {
        return (int) VARASTO.get(2);
    }

    @Override
    public List<Object> annaSisalto() {
        return VARASTO;
    }
    
}
