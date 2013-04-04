/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package laivanupotus.kayttoliittymat;

import javax.swing.JPanel;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Pelialue;

/**
 *
 * @author John Lång
 */
public class GraafinenKayttoliittyma implements Kayttoliittyma, Runnable {
    
    public GraafinenKayttoliittyma() {
        
    }
    
    @Override
    public void asetaPelikierros(Pelikierros pelikierros) {};
    
    @Override
    public void asetaKatsoja(Pelaaja katsoja) {};
    
    @Override
    public void alusta() {};
    
    @Override
    public void paivita(Pelialue pelialue, int x, int y) {};
    
    @Override
    public void tulostaPelitilanne() {};
    
    @Override
    public void tulostaViesti(String viesti) {};
    
    @Override
    public Komento pyydaKomento(Pelaaja pelaaja) throws Exception {
        return null;
    };

    @Override
    public void tulostaDebuggausViesti(String viesti) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
