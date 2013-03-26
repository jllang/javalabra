/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Saannot;

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
    
    public Pelikierros(Kayttoliittyma kayttoliittyma, Saannot saannot, Pelaaja pelaaja1, Pelaaja pelaaja2) {
        this.KAYTTOLIITTYMA = kayttoliittyma;
        this.SAANNOT = saannot;
        this.PELAAJA1 = pelaaja1;
        this.PELAAJA2 = pelaaja2;
        this.PELIALUE1 = new Pelialue(this, pelaaja1);
        this.PELIALUE2 = new Pelialue(this, pelaaja2);
        this.PELAAJA1.asetaPelialue(PELIALUE1);
        this.PELAAJA2.asetaPelialue(PELIALUE2);
    }
    
    public Kayttoliittyma annaKayttoliittyma() {
        return KAYTTOLIITTYMA;
    }
    
    public Saannot annaSaannot() {
        return SAANNOT;
    }
    
//    public Pelaaja annaPelaaja1() {
//        return PELAAJA1;
//    }
//    
//    public Pelaaja annaPelaaja2() {
//        return PELAAJA2;
//    }
    
    public Pelaaja annaVastapelaaja(Pelaaja pelaaja) {
        if(pelaaja == PELAAJA1) {
            return PELAAJA2;
        }
        return PELAAJA1;
    }
    
    public Pelialue annaPelialue1() {
        return PELIALUE1;
    }
    
    public Pelialue annaPelialue2() {
        return PELIALUE2;
    }
}
