/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import java.util.Map;
import java.util.Random;
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
        Random arpoja = new Random();
//        Map<Integer, Integer> laivojenMitatjaMaarat = new TreeMap<>();
////        laivojenMitatjaMaarat.put(1, 4);
////        laivojenMitatjaMaarat.put(2, 3);
////        laivojenMitatjaMaarat.put(3, 2);
////        laivojenMitatjaMaarat.put(4, 1);
//        laivojenMitatjaMaarat.put(1, 1);
//        laivojenMitatjaMaarat.put(2, 1);
//        laivojenMitatjaMaarat.put(3, 1);
//        Saannot saannot = new Saannot(5, 5, 0, laivojenMitatjaMaarat);
//        Saannot saannot = new Saannot(20, 10, 0, laivojenMitatjaMaarat); //Kaatuu
        Saannot saannot = new Saannot();
        Pelaaja p1 = new Ihmispelaaja();
        Pelaaja p2 = new Tekoalypelaaja(arpoja, saannot);
        Pelikierros pelikierros = new Pelikierros(kayttoliittyma, arpoja, saannot, p1, p2);
        pelikierros.aloita();
        
    }
}
