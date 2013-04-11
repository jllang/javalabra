
package laivanupotus.kontrolli;

import laivanupotus.kayttajat.Ihmispelaaja;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.poikkeukset.RuutuunOnJoAmmuttuException;
import laivanupotus.poikkeukset.TuntematonKomentoException;
import laivanupotus.poikkeukset.TyhjaKomentoException;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Saannot;
import laivanupotus.tietorakenteet.enumit.Komentotyyppi;

/**
 * Tämän luokka ohjaa varsinaista pelin kulkua ja välittää tietoa eri 
 * komponenttien välillä.
 *
 * @author John Lång
 */
public final class Pelikierros {

    private final Kayttoliittyma            KAYTTOLIITTYMA;
    private final Poikkeustenkasittelija    POIKKEUSTENKASITTELIJA;
    private final Saannot                   SAANNOT;
    private final Pelaaja                   PELAAJA1;
    private final Pelaaja                   PELAAJA2;
    private final Pelialue                  PELIALUE1;
    private final Pelialue                  PELIALUE2;
    
    private boolean                         peliJatkuu, vuorotOnRajoitettu;
    private Pelaaja                         voittaja, vuorossaolija;
    private int                             vuoro;
    
    public Pelikierros(
            Kayttoliittyma kayttoliittyma,
            Poikkeustenkasittelija poikkeustenkasittelija,
            Saannot saannot,
            Pelaaja pelaaja1,
            Pelaaja pelaaja2) {
        
        this.KAYTTOLIITTYMA         = kayttoliittyma;
        this.POIKKEUSTENKASITTELIJA = poikkeustenkasittelija;
        this.SAANNOT                = saannot;
        this.PELAAJA1               = pelaaja1;
        this.PELAAJA2               = pelaaja2;
        this.PELIALUE1              = new Pelialue(this, pelaaja1);
        this.PELIALUE2              = new Pelialue(this, pelaaja2);
        this.peliJatkuu             = true;
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
    
    /**
     * Aloittaa pelikierroksen kulun.
     */
    public void aloita() {
        KAYTTOLIITTYMA.asetaKatsoja(PELAAJA1);
        KAYTTOLIITTYMA.asetaPelikierros(this);
        KAYTTOLIITTYMA.alusta();
        
        while (peliJatkuu) {
            if (vuorossaolija.getClass() == Ihmispelaaja.class) {
                KAYTTOLIITTYMA.tulostaPelitilanne();
            }
            try {
                kasitteleVuoro(vuorossaolija);
            } catch (Exception poikkeus) {
                POIKKEUSTENKASITTELIJA.kasittele(poikkeus);
            }
        }
    }

    /**
     * Tämän metodin tarkoitus on kuljettaa peliä eteenpäin yksi vuoro, joka 
     * sisältää molempien pelaajien ampumiskomennot tai joka päättyy 
     * mahdollisesti jomman kumman pelaajan voittoon tai luovutukseen
     * @param pelaaja Vuorossa oleva pelaaja.
     * @throws Exception Mahdollinen pelaajan sääntöjen vastaisen toiminnan tai 
     * muun ei-toivotun tapahtuman aiheuttama poikkeus.
     * @see Saannot
     */
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
            komento = KAYTTOLIITTYMA.pyydaKomento();
        } else {
            komento = pelaaja.annaKomento(new Komento(Komentotyyppi.AMMU));
        }
        
        kasitteleKomento(komento, pelialue);
    }
    
    /**
     * Käsittelee pelaajalta saatuja pelin kulkuun liittyviä komentoja.
     * 
     * @param komento luokan <tt>Komento</tt> instanssi, joka sisältää tiedon 
     * suoritettavasta toiminnosta ja sen mahdollisista parametreista.
     * @param pelialue Sen pelaajan pelialue, johon mahdollisesti ollaan 
     * ampumassa.
     * @throws Exception Mahdollinen pelaajan sääntöjen vastaisen toiminnan tai 
     * muun ei-toivotun tapahtuman aiheuttama poikkeus.
     */
    private void kasitteleKomento(Komento komento, 
            Pelialue pelialue) throws Exception {
        switch (komento.KOMENTOTYYPPI) {
            case TYHJA:
                throw new TyhjaKomentoException();
            case LUOVUTA:  // Asetetaan voittaja ja jatketaan eteenpäin.
                voittaja = annaVastapelaaja(vuorossaolija);
            case LOPETA:
                peliJatkuu = false;
                return;
            case PAIVITA_KAYTTOLIITTYMA: // Varattu mahdollista myöhempää käyttöä varten
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
//                    KAYTTOLIITTYMA.tulostaDebuggausViesti(rojae.getMessage());
                    POIKKEUSTENKASITTELIJA.kasittele(rojae);
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
                if (vuorotOnRajoitettu) {
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
