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
    private final Pelialue PELIALUE1;
    private final Pelialue PELIALUE2;
    
    public Pelikierros(Kayttoliittyma kayttoliittyma, Saannot saannot) {
        this.KAYTTOLIITTYMA = kayttoliittyma;
        this.SAANNOT = saannot;
        this.PELAAJA1 = new Ihmispelaaja();
        this.PELAAJA2 = new Ihmispelaaja();
        PELAAJA1.luoPelialue(saannot);
        PELAAJA2.luoPelialue(saannot);
        PELIALUE1 = PELAAJA1.annaPelialue();
        PELIALUE2 = PELAAJA2.annaPelialue();
    }
    
    public Saannot annaSaannot() {
        return SAANNOT;
    }
    
    public Pelialue annaPelialue1() {
        return PELIALUE1;
    }
    
    public Pelialue annaPelialue2() {
        return PELIALUE2;
    }
}
