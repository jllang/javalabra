/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package laivanupotus.kayttoliittymat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kayttoliittymat.komponentit.graafinenKayttoliittyma.Hiirenkuuntelija;
import laivanupotus.kayttoliittymat.komponentit.graafinenKayttoliittyma.Ruutupaneeli;
import laivanupotus.kayttoliittymat.komponentit.graafinenKayttoliittyma.Selitepaneeli;
import laivanupotus.kayttoliittymat.komponentit.graafinenKayttoliittyma.Valikonkuuntelija;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.tietorakenteet.enumit.Ruutu;

/**
 * Laivanupotuspelin graafinen käyttöliittymä.
 * 
 * @see Kayttoliittyma
 * @author John Lång
 */
public final class GraafinenKayttoliittyma implements Runnable, Kayttoliittyma {
 
    private static Ruutu[][]    r1, r2; // Miksi ette päivity???
    private static Pelikierros  pelikierros;
    
    private JFrame              freimi;
    private JLabel              tilaviesti, laivoja1, laivoja2;
    private JOptionPane         popupViesti;
    private Hiirenkuuntelija    hk1, hk2;
    private Ruutupaneeli        rp1, rp2;
    private Pelaaja             katsoja;
    private Komento             viimeisinKomento;
    
    public GraafinenKayttoliittyma() {}

    @Override
    public void run() {
        freimi = new JFrame("Laivanupotus");
        freimi.setPreferredSize(new Dimension(640, 480));
        freimi.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        freimi.setResizable(false);
        rakennaKomponentit(freimi.getContentPane());
        freimi.pack();
        freimi.setVisible(true);
    }
    
    private void rakennaKomponentit(Container container) {
        popupViesti = new JOptionPane("Virhe", JOptionPane.ERROR_MESSAGE);
        
        rakennaValikkopalkki();
        
        r1  = katsoja.annaPelialue().annaRuudukko(katsoja);
        r2  = pelikierros.annaVastapelaaja(katsoja).annaPelialue().annaRuudukko(katsoja);        
        Ruutupaneeli.asetaMitat(r1[0].length, r1.length, 16, 16);
        
        hk1 = new Hiirenkuuntelija(this, rp1, katsoja);
        hk2 = new Hiirenkuuntelija(this, rp2, pelikierros.annaVastapelaaja(katsoja));
        rp1 = new Ruutupaneeli(hk1);
        rp2 = new Ruutupaneeli(hk2);
        
        JPanel keskialue = rakennaKeskialue();

        tilaviesti = new JLabel("Peli alkoi.");
        
        container.add(keskialue, BorderLayout.CENTER);
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
        Valikonkuuntelija vk = new Valikonkuuntelija(this, valikkopalkki);
        freimi.setJMenuBar(valikkopalkki);
    }
    
    private JPanel rakennaKeskialue() {
        JPanel keskialue            = new JPanel();
        GridBagLayout asettelu      = new GridBagLayout();
        GridBagConstraints rajat    = new GridBagConstraints();
        keskialue.setLayout(asettelu);
        
        rajat.gridx     = 0;
        rajat.gridy     = 0;
        rajat.weightx   = 0.5;
        rajat.weighty   = 0.1;
        keskialue.add(new JLabel("Pelaaja 1"), rajat);
        
        rajat.gridx     = 1;
        rajat.gridy     = 0;
        rajat.weightx   = 0.5;
        rajat.weighty   = 0.1;
        keskialue.add(new JLabel("Pelaaja 2"), rajat);
        
        rajat.gridx     = 0;
        rajat.gridy     = 1;
        rajat.weightx   = 0.5;
        rajat.weighty   = 0.8;
        rajat.fill      = rajat.BOTH;
        keskialue.add(rp1, rajat);
        
        rajat.gridx     = 1;
        rajat.gridy     = 1;
        rajat.weighty   = 0.8;
        rajat.fill      = rajat.BOTH;
        keskialue.add(rp2, rajat);
        
        rajat.gridx     = 2;
        rajat.gridy     = 1;
        rajat.weightx   = 0.5;
        rajat.weighty   = 0.8;
        keskialue.add(new Selitepaneeli(), rajat);
        
        rajat.gridx     = 0;
        rajat.gridy     = 2;
        rajat.weightx   = 0.5;
        rajat.weighty   = 0.1;
        laivoja1        = new JLabel("Laivoja: ");
        keskialue.add(laivoja1, rajat);
        
        rajat.gridx     = 1;
        rajat.gridy     = 2;
        rajat.weightx   = 0.5;
        rajat.weighty   = 0.1;
        laivoja2        = new JLabel("Laivoja: ");
        keskialue.add(laivoja2, rajat);
        
        return keskialue;
    }
    
    public void tulostaRuudut() {
        System.out.println(r1 == rp1.annaRuudut());
        rp1.tulostaRuudut();
        // Debuggaukseen
//        for (Ruutu[] ruutus : r1) {
//            for (Ruutu ruutu : ruutus) {
//                switch (ruutu) {
//                    case LAIVA_EI_OSUMAA:
//                        System.out.print("L ");
//                        break;
//                    case LAIVA_OSUMA:
//                        System.out.print("X ");
//                        break;
//                    case TYHJA_EI_OSUMAA:
//                        System.out.print("~ ");
//                        break;
//                    case TYHJA_OSUMA:
//                        System.out.print("O ");
//                        break;
//                    default:
//                        System.out.print("? ");
//                        break;
//                }
//            }
//            System.out.println();
//        }
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
        r1 = katsoja.annaPelialue().annaRuudukko(katsoja);
        r2 = pelikierros.annaVastapelaaja(katsoja).annaPelialue().annaRuudukko(katsoja);
        rp1.asetaRuudukko(r1);
        rp2.asetaRuudukko(r2);
    }

    @Override
    public void paivita(Pelialue pelialue, int x, int y) {
        Ruutu ruutu = pelialue.haeRuutu(katsoja, x, y);
//        System.out.println("Ruudun uusi tila:" + ruutu);
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
        laivoja1.setText("Laivoja: " + pelikierros.annaPelialue1().laivojaJaljella());
        laivoja2.setText("Laivoja: " + pelikierros.annaPelialue2().laivojaJaljella());
    }

    @Override
    public void tulostaOhje() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tulostaViesti(String viesti) {
        tilaviesti.setText(viesti.trim());
    }

    @Override
    public void tulostaDebuggausViesti(String viesti) {
        popupViesti.setMessage(viesti);
        JDialog dialogi = popupViesti.createDialog("Poikkeus");
        dialogi.setVisible(true);
    }

    @Override
    public Komento pyydaKomento() throws Exception {
        while (viimeisinKomento == null) {
            Thread.sleep(100);
        }
        Komento k = viimeisinKomento;
        viimeisinKomento = null;
        return k;
    }
    
    public void asetaKomento(Komento komento) {
        this.viimeisinKomento = komento;
    }

}
