/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.tietorakenteet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import laivanupotus.rajapinnat.Tallennettava;

/**
 *
 * @author John Lång
 */
public class Saannot implements Tallennettava {
    
    private final List<Object> VARASTO;
    
    public Saannot(int leveys, int korkeus, int vuoroja, Map<Integer, Integer> laivojenMitatJaMaarat) {
        tarkastaArvot(leveys, korkeus, vuoroja, laivojenMitatJaMaarat);
        this.VARASTO = new ArrayList<>();
        VARASTO.add(leveys);
        VARASTO.add(korkeus);
        VARASTO.add(vuoroja);
        lisaaLaivojenMitatJaMaarat(laivojenMitatJaMaarat);
    }
    
    public Saannot() {
        //Oletussäännöt
        this.VARASTO = new ArrayList<>();
        VARASTO.add(10);
        VARASTO.add(10);
        VARASTO.add(0);
        VARASTO.add(4);     // Pisimmän laivan pituus
        VARASTO.add(4);     // 4 * 1 ruutu
        VARASTO.add(6);     // 3 * 2 ruutua
        VARASTO.add(6);     // 2 * 3 ruutua
        VARASTO.add(4);     // 1 * 4 ruutua
    }
    
    private void tarkastaArvot(int leveys, int korkeus, int vuoroja, Map<Integer, Integer> laivojenMitatJaMaarat) {
        if (leveys < 5
                || leveys > 20
                || korkeus < 5
                || korkeus > 20
                || vuoroja < 0) {
            throw new IllegalArgumentException("Virheelliset säännöt: Ruudukon korkeuden tulee olla väliltä [5 ... 20] ja vuorojen määrä ei saa olla negatiivinen.");
        }
        
        if(laivojenMitatJaMaarat == null || laivojenMitatJaMaarat.isEmpty()) {
            throw new IllegalArgumentException("Virheelliset säännöt: Laivojen määriä ja mittoja ei annettu.");
        }
        
        int laivojenKokonaisPintaAla = 0, alusluokanPintaAla = 0;
        
        for (Integer pituus : laivojenMitatJaMaarat.keySet()) {
            if (pituus > leveys || pituus > korkeus) {
                throw new IllegalArgumentException("Virheelliset säännöt: Laivojen pituudet eivät saa ylittää pelialueen mittoja.");
            }
            laivojenKokonaisPintaAla += pituus * laivojenMitatJaMaarat.get(pituus);
        }
        
        //Tämä tarkastus on hieman karkea, mutta estänee aivan älyttömät laivojen määrät:
        if (laivojenKokonaisPintaAla > (leveys * korkeus) / 3) {
            throw new IllegalArgumentException("Virheelliset säännöt: Liikaa laivoja.");
        }       
        
    }
    
    private void lisaaLaivojenMitatJaMaarat(Map<Integer, Integer> laivojenMitatJaMaarat) {
        VARASTO.add(laivojenMitatJaMaarat.size());  // Lisättävien arvojen määrä ja pisimpien laivojen pituus
        for (Integer pituus : laivojenMitatJaMaarat.keySet()) {
            VARASTO.add(pituus * laivojenMitatJaMaarat.get(pituus));
        }
    }
    
    public Map<Integer, Integer> annaLaivojenMitatJaMaarat() {
        Map<Integer, Integer> laivojenMitatJaMaarat = new TreeMap<>();
        int pituus  = (int) VARASTO.get(3); //Pisimmän laivan pituus;
        int i       = (int) VARASTO.size() - 1; //Pisimpien laivojen pinta-ala.
        
        for (; i > 3; i--, pituus--) {
            laivojenMitatJaMaarat.put(pituus, (int) VARASTO.get(i) / pituus);
        }
        
        return laivojenMitatJaMaarat;
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
    
    public int laivapintaAla() {
        // Tämä tarvitsee testin.
        int laivapintaAla = 0;
        for (int i = 3; i < VARASTO.size(); i++) {
            laivapintaAla += (int) VARASTO.get(i);
        }
        return laivapintaAla;
    }

    @Override
    public List<Object> annaSisalto() {
        return VARASTO;
    }
    
    @Override
    public void rakennaSisalto(List<Object> ladattuSisalto) {
        for (int i = 0; i < ladattuSisalto.size(); i++) {
            VARASTO.set(i, ladattuSisalto.get(i));
        }
    }
    
}
