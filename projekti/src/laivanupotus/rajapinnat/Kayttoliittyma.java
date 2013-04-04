/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.rajapinnat;

import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.tietorakenteet.Komento;

/**
 *
 * @author John Lång
 */
public interface Kayttoliittyma extends Runnable {
    
    void asetaPelikierros(Pelikierros pelikierros);
    void asetaKatsoja(Pelaaja katsoja);
    void alusta();
    void paivita(Pelialue pelialue, int x, int y);
    void tulostaPelitilanne();
    void tulostaViesti(String viesti);
    void tulostaDebuggausViesti(String viesti);
    Komento pyydaKomento(Pelaaja pelaaja) throws Exception;
    
}
