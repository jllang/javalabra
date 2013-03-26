/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.kayttoliittymat.Tekstikayttoliittyma;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Piste;
import laivanupotus.tietorakenteet.Saannot;

/**
 *
 * @author John Lång
 */
public class Laivanupotus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Kayttoliittyma kayttoliittyma = new Tekstikayttoliittyma();
        Map<Integer, Integer> laivojenMitatjaMaarat = new TreeMap<>();
        laivojenMitatjaMaarat.put(1, 2);
        laivojenMitatjaMaarat.put(2, 0);
        laivojenMitatjaMaarat.put(3, 2);
        Saannot saannot = new Saannot(20, 10, 0, laivojenMitatjaMaarat);
        Pelaaja p1 = new Ihmispelaaja();
        Pelaaja p2 = new Ihmispelaaja();
        Pelikierros pelikierros = new Pelikierros(kayttoliittyma, saannot, p1, p2);
        
        LaivojenArpoja sijoittamisTekoaly = new LaivojenArpoja(saannot);
        try {
            sijoittamisTekoaly.arvoLaivat(p1);
            sijoittamisTekoaly.arvoLaivat(p2);
        } catch (Exception ex) {
            Logger.getLogger(Laivanupotus.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        kayttoliittyma.asetaPelikierros(pelikierros);
        kayttoliittyma.asetaKatsoja(p1);
        kayttoliittyma.alusta();
        kayttoliittyma.tulosta();
        
    }
}
