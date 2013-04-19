/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package laivanupotus.kayttoliittymat;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kayttoliittymat.komponentit.graafinenKayttoliittyma.Ruutupaneeli;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.tietorakenteet.enumit.Ruutu;

/**
 *
 * @author John Lång
 */
public class GraafinenKayttoliittyma implements Runnable, Kayttoliittyma {
    
    private JFrame              frame;    
    private Ruutu[][]           r1, r2;
    private Ruutupaneeli        rp1, rp2;
    private Pelaaja             katsoja;
    private Pelikierros         pelikierros;
    
    public GraafinenKayttoliittyma() {
        
    }

    @Override
    public void run() {
        frame = new JFrame("Laivanupotus");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        rakennaKomponentit(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void asetaPelikierros(Pelikierros pelikierros) {
        this.pelikierros = pelikierros;
    }

    @Override
    public void asetaKatsoja(Pelaaja katsoja) {
        this.katsoja = katsoja;
    }

    @Override
    public Pelaaja annaKatsoja() {
        return katsoja;
    }

    @Override
    public void alusta() {
        
    }
    
    private void rakennaKomponentit(Container container) {
        r1 = katsoja.annaPelialue().annaRuudukko(katsoja);
        r2 = pelikierros.annaVastapelaaja(katsoja).annaPelialue().annaRuudukko(katsoja);
        rp1 = new Ruutupaneeli(r1, 8, 8);
        rp2 = new Ruutupaneeli(r2, 108, 8);
        container.add(rp1);
        container.add(rp2);
    }

    @Override
    public void paivita(Pelialue pelialue, int x, int y) {
        Ruutu ruutu = pelialue.haeRuutu(katsoja, x, y);
        if (pelialue == pelikierros.annaPelialue1()) {
            r1[y][x] = ruutu;
        } else {
            r2[y][x] = ruutu;
        }
    }

    @Override
    public void tulostaPelitilanne() {
        rp1.repaint();
        rp2.repaint();
    }

    @Override
    public void tulostaOhje() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tulostaViesti(String viesti) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tulostaDebuggausViesti(String viesti) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Komento pyydaKomento() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
