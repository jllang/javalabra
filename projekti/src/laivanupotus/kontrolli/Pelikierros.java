/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import laivanupotus.kayttajat.Tekoalypelaaja;
import laivanupotus.kayttajat.Ihmispelaaja;
import laivanupotus.kayttajat.Pelaaja;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.kielet.Kieli;
import laivanupotus.poikkeukset.RuutuunOnJoAmmuttuException;
import laivanupotus.poikkeukset.TuntematonKomentoException;
import laivanupotus.poikkeukset.TyhjaKomentoException;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.enumit.Komentotyyppi;
import laivanupotus.tietorakenteet.Saannot;

/**
 *
 * @author John Lång
 */
public class Pelikierros {

    private final Kayttoliittyma            KAYTTOLIITTYMA;
    private final Poikkeustenkasittelija    POIKKEUSTENKASITTELIJA;
    private final Random                    ARPOJA;
    private final Saannot                   SAANNOT;
    private final Pelaaja                   PELAAJA1;
    private final Pelaaja                   PELAAJA2;
    private final Pelialue                  PELIALUE1;
    private final Pelialue                  PELIALUE2;
    
    private boolean                         peliJatkuu, vuorojenMääräOnRajoitettu;
    private Pelaaja                         voittaja, vuorossaolija;
    private int                             vuoro;
    
    public Pelikierros(Kayttoliittyma kayttoliittyma,
            Poikkeustenkasittelija poikkeustenkasittelija, Random arpoja,
            Saannot saannot, Pelaaja pelaaja1, Pelaaja pelaaja2) {
        this.KAYTTOLIITTYMA         = kayttoliittyma;
        this.POIKKEUSTENKASITTELIJA = poikkeustenkasittelija;
        this.ARPOJA                 = arpoja;
        this.SAANNOT                = saannot;
        this.PELAAJA1               = pelaaja1;
        this.PELAAJA2               = pelaaja2;
        this.PELIALUE1              = new Pelialue(this, pelaaja1);
        this.PELIALUE2              = new Pelialue(this, pelaaja2);
        this.peliJatkuu    = true;
        this.vuorossaolija          = pelaaja1;
        this.PELAAJA1.asetaPelialue(PELIALUE1);
        this.PELAAJA2.asetaPelialue(PELIALUE2);
    }
    
    public Kayttoliittyma annaKayttoliittyma() {
        return KAYTTOLIITTYMA;
    }
    
    public Saannot annaSaannot() {
        return SAANNOT;
    }
    
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
    
    public void aloita() throws Exception {
        LaivojenArpoja arpoja = new LaivojenArpoja(SAANNOT, ARPOJA);
        KAYTTOLIITTYMA.asetaKatsoja(PELAAJA1);
        KAYTTOLIITTYMA.asetaPelikierros(this);
        KAYTTOLIITTYMA.alusta();
        
        arpoja.sijoitaLaivasto(PELAAJA1);
        arpoja.sijoitaLaivasto(PELAAJA2);
        
        while (peliJatkuu) {
            KAYTTOLIITTYMA.tulostaPelitilanne();
            try {
                kasitteleVuoro(vuorossaolija);
            } catch (Exception poikkeus) {
                POIKKEUSTENKASITTELIJA.kasittele(poikkeus);
            }
        }
    }

    private void kasitteleVuoro(Pelaaja pelaaja) throws Exception {
        if (!pelaaja.annaPelialue().laivojaOnJaljella()) {
            voittaja = annaVastapelaaja(pelaaja);
            peliJatkuu = false;
            KAYTTOLIITTYMA.tulostaViesti("Peli päättyi. Voittaja oli "
                    + ((voittaja == PELAAJA1) ? "pelaaja 1." : "pelaaja 2."));
            return;
        }
        Pelialue pelialue = annaVastapelaaja(pelaaja).annaPelialue();

        Komento komento;
        if (pelaaja.getClass() == Ihmispelaaja.class) {
            komento = KAYTTOLIITTYMA.pyydaKomento(pelaaja);
        } else {
            Tekoalypelaaja tekoalypelaaja = (Tekoalypelaaja) pelaaja;
            komento = tekoalypelaaja.ammuSatunnaiseenRuutuun();
        }
        kasitteleKomento(komento, pelialue);
    }
    
    private void kasitteleKomento(Komento komento, 
            Pelialue pelialue) throws Exception {
        switch (komento.KOMENTOTYYPPI) {
            case TYHJA:
                throw new TyhjaKomentoException();
            case LUOVUTA:  //Asetetaan voittaja ja jatketaan eteenpäin.
                voittaja = annaVastapelaaja(vuorossaolija);
            case LOPETA:
                peliJatkuu = false;
                return;
            case PAIVITA_KAYTTOLIITTYMA:
                KAYTTOLIITTYMA.alusta();
                KAYTTOLIITTYMA.tulostaPelitilanne();
                return;
            case TILAKYSELY:
                kasitteleTilakysely(komento.PARAMETRIT[0]);
                return;
            case AMMU:
                try {
                    pelialue.ammu(vuorossaolija,
                            komento.PARAMETRIT[0],
                            komento.PARAMETRIT[1]);
                    vuorossaolija = annaVastapelaaja(vuorossaolija);
                } catch (RuutuunOnJoAmmuttuException rojae) {
                    // Tulostetaan virhesanoma mutta jatketaan normaalisti
                    KAYTTOLIITTYMA.tulostaDebuggausViesti(rojae.getMessage());
                }
                return;
        default:
            throw new TuntematonKomentoException();
        }
    }
    
    private void kasitteleTilakysely(int kyselynumero) {
        switch (kyselynumero) {
            case Komento.TILAKYSELY_MONESKO_VUORO:
                KAYTTOLIITTYMA.tulostaViesti("On " + vuoro + ". vuoro.");
                break;
            case Komento.TILAKYSELY_VUOROJA_JALJELLA:
                if (vuorojenMääräOnRajoitettu) {
                    KAYTTOLIITTYMA.tulostaViesti("Vuoroja on jäljellä "
                            + (SAANNOT.vuoroja() - vuoro));
                } else {
                    KAYTTOLIITTYMA.tulostaViesti("Vuorojen määrää ei "
                            + "ole rajoitettu");
                }
                break;
            case Komento.TILAKYSELY_OMIA_LAIVOJA_JALJELLA:
                KAYTTOLIITTYMA.tulostaViesti("Omia laivaruutuja on jäljellä "
                        + vuorossaolija.annaPelialue().laivapintaAlaaJaljella()
                        + " kpl.");
                break;
            case Komento.TILAKYSELY_VASTUSTAJAN_LAIVOJA_JALJELLA:
                KAYTTOLIITTYMA.tulostaViesti("Vastustajan laivaruutuja on "
                        + "jäljellä " + annaVastapelaaja(vuorossaolija)
                        .annaPelialue().laivapintaAlaaJaljella()
                        + " kpl.");
                break;
        }
    }
    
    private void lopeta() {
        KAYTTOLIITTYMA.tulostaViesti("Voittaja oli ");
        if (voittaja == PELAAJA1) {
            KAYTTOLIITTYMA.tulostaViesti("pelaaja 1.");
        } else {
            KAYTTOLIITTYMA.tulostaViesti("pelaaja 2.");
        }
    }

}
