/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.rajapinnat;

import laivanupotus.kontrolli.Pelaaja;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.kontrolli.Pelikierros;

/**
 *
 * @author John Lång
 */
public interface Kayttoliittyma {
    
    void asetaPelikierros(Pelikierros pelikierros);
    void asetaKatsoja(Pelaaja katsoja);
    void alusta();
    void paivita(Pelialue pelialue, int x, int y);
    void tulosta();
    
}
