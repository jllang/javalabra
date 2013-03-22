/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kayttoliittymat;

import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.kontrolli.Pelialue;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tyypit.Ruutu;

/**
 *
 * @author John Lång
 */
public class Tekstikayttoliittyma implements Kayttoliittyma {
    
    private static Pelikierros  pelikierros;
    private static Ruutu[][]    ruudukko1;
    
    public Tekstikayttoliittyma() {}
    
    @Override
    public void asetaPelikierros(Pelikierros pelikierros) {
        Tekstikayttoliittyma.pelikierros = pelikierros;
    }

    @Override
    public void alusta() {
        Pelialue pelialue1 = pelikierros.annaPelialue1();
        Pelialue pelialue2 = pelikierros.annaPelialue2();
    }
    
    @Override
    public void paivita() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
