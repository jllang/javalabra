/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Komentotyyppi;
import laivanupotus.tietorakenteet.Saannot;

/**
 *
 * @author John Lång
 */
public class Pelikierros {
        
    private final Kayttoliittyma    KAYTTOLIITTYMA;
    private final Random            ARPOJA;
    private final Saannot           SAANNOT;
    private final Pelaaja           PELAAJA1;
    private final Pelaaja           PELAAJA2;
    private final Pelialue          PELIALUE1;
    private final Pelialue          PELIALUE2;
    
    private boolean                 jatkuukoPelikierros;
    private Pelaaja                 voittaja, vuorossaOlija;
    
    public Pelikierros(Kayttoliittyma kayttoliittyma, Random arpoja,
            Saannot saannot, Pelaaja pelaaja1, Pelaaja pelaaja2) {
        this.KAYTTOLIITTYMA         = kayttoliittyma;
        this.ARPOJA                 = arpoja;
        this.SAANNOT                = saannot;
        this.PELAAJA1               = pelaaja1;
        this.PELAAJA2               = pelaaja2;
        this.PELIALUE1              = new Pelialue(this, pelaaja1);
        this.PELIALUE2              = new Pelialue(this, pelaaja2);
        this.jatkuukoPelikierros    = true;
        this.vuorossaOlija          = pelaaja1;
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
    
    public void aloita() {
        LaivojenArpoja arpoja = new LaivojenArpoja(SAANNOT, ARPOJA);
        KAYTTOLIITTYMA.asetaKatsoja(PELAAJA1);
        KAYTTOLIITTYMA.asetaPelikierros(this);
        KAYTTOLIITTYMA.alusta();
        
        try {
            arpoja.arvoLaivat(PELAAJA1);
            arpoja.arvoLaivat(PELAAJA2);
        } catch (Exception poikkeus) {
            poikkeustenKasittelija(poikkeus);
        }
        
        KAYTTOLIITTYMA.tulostaPelitilanne();
        
        while (jatkuukoPelikierros) {            
            kasitteleVuoro(vuorossaOlija);
            KAYTTOLIITTYMA.tulostaPelitilanne();
            vuorossaOlija = annaVastapelaaja(vuorossaOlija);
        }
    }

    private void kasitteleVuoro(Pelaaja pelaaja) {
        if (!pelaaja.annaPelialue().laivojaOnJaljella()) {
            voittaja = annaVastapelaaja(pelaaja);
            jatkuukoPelikierros = false;
            KAYTTOLIITTYMA.tulostaViesti("Peli päättyi.");
            return;
        }
        Pelialue pelialue = annaVastapelaaja(pelaaja).annaPelialue();

        try {
            Komento komento;
            if (pelaaja.getClass() == Ihmispelaaja.class) {
                komento = KAYTTOLIITTYMA.pyydaKomento(pelaaja);
            } else {
                Tekoalypelaaja tekoalypelaaja = (Tekoalypelaaja) pelaaja;
                komento = tekoalypelaaja.ammuSatunnaiseenRuutuun();
            }
            switch (komento.KOMENTOTYYPPI) {
            case TYHJA:
                System.out.println("Mitään komentoa ei annettu");
                return;
            case LUOVUTA:  //Asetetaan voittaja ja jatketaan eteenpäin.
                voittaja = annaVastapelaaja(pelaaja);
            case LOPETA:
                jatkuukoPelikierros = false;
                return;
            case PAIVITA_KAYTTOLIITTYMA:
                KAYTTOLIITTYMA.alusta();
                KAYTTOLIITTYMA.tulostaPelitilanne();
                return;
            case AMMU:
                pelialue.ammu(pelaaja,
                        komento.PARAMETRIT[0],
                        komento.PARAMETRIT[1]);
                return;
            default:
                System.out.println("Tuntematon komento.");
            }
        } catch (Exception poikkeus) {
            vuorossaOlija = annaVastapelaaja(pelaaja);
            poikkeustenKasittelija(poikkeus);
       }
    }
    
    private void lopeta() {
        System.out.print("Voittaja oli ");
        if (voittaja == PELAAJA1) {
            System.out.println("pelaaja 1.");
        } else {
            System.out.println("pelaaja 2.");
        }
    }

    private void poikkeustenKasittelija(Exception poikkeus) {
        System.out.println(poikkeus.getMessage());
    }
}
