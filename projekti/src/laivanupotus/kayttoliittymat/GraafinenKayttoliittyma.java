/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package laivanupotus.kayttoliittymat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kayttoliittymat.komponentit.graafinenKayttoliittyma.Ruutupaneeli;
import laivanupotus.kayttoliittymat.komponentit.graafinenKayttoliittyma.Valikonkuuntelija;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.tietorakenteet.enumit.Ruutu;

/**
 *
 * @author John Lång
 */
public final class GraafinenKayttoliittyma implements Runnable, Kayttoliittyma {
    
    private JFrame              freimi;
    private Valikonkuuntelija   valikonkuuntelija;
    private Ruutu[][]           r1, r2;
    private Ruutupaneeli        rp1, rp2;
    private Pelaaja             katsoja;
    private Pelikierros         pelikierros;
    private Komento             viimeisinKomento;
    
    public GraafinenKayttoliittyma() {
        
    }

    @Override
    public void run() {
        freimi = new JFrame("Laivanupotus");
        freimi.setPreferredSize(new Dimension(640, 480));
        freimi.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        rakennaKomponentit(freimi.getContentPane());
        freimi.pack();
        freimi.setVisible(true);
    }
    
    private void rakennaKomponentit(Container container) {
        rakennaValikkopalkki();
        
//        JPanel tausta = new JPanel();
//        tausta.setBackground(Color.GRAY);
        r1  = katsoja.annaPelialue().annaRuudukko(katsoja);
        r2  = pelikierros.annaVastapelaaja(katsoja).annaPelialue().annaRuudukko(katsoja);
        rp1 = new Ruutupaneeli(r1, 8, 8);
        rp2 = new Ruutupaneeli(r2, 188, 8);
        JLabel tilaviesti = new JLabel("Peli alkoi.");
        
//        container.add(tausta, BorderLayout.CENTER);
        container.add(rp1, BorderLayout.CENTER);
        container.add(rp2, BorderLayout.CENTER);
        container.add(tilaviesti, BorderLayout.SOUTH);
    }
    
    private void rakennaValikkopalkki() {
        JMenuBar valikkopalkki  = new JMenuBar();
        JMenu valikko           = new JMenu("Valikko");
        JMenuItem valinta       = new JMenuItem("Ohje");
        valikko.add(valinta);
        valinta                 = new JMenuItem("Lopeta");
        valikko.add(valinta);
        valikkopalkki.add(valikko);
        valikonkuuntelija       = new Valikonkuuntelija(this, valikkopalkki);
        freimi.setJMenuBar(valikkopalkki);
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
//        r1 = pelikierros.annaPelialue1().annaRuudukko(katsoja);
//        r2 = pelikierros.annaPelialue2().annaRuudukko(katsoja);
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
        rp1.tulosta();
        rp2.tulosta();
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
        while (viimeisinKomento == null) {
            Thread.sleep(10);
        }
        Komento k = viimeisinKomento;
        viimeisinKomento = null;
        return k;
    }
    
    public void asetaKomento(Komento komento) {
        this.viimeisinKomento = komento;
    }

}
