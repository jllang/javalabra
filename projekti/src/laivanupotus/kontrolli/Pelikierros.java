
package laivanupotus.kontrolli;

import laivanupotus.kayttajat.Ihmispelaaja;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kayttajat.Tekoalypelaaja;
import laivanupotus.poikkeukset.TuntematonKomentoException;
import laivanupotus.poikkeukset.TyhjaKomentoException;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.*;
import laivanupotus.tietorakenteet.enumit.*;

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
    private long                            katsojanPeliaika;
    private Kello                           kello;
    private Thread                          kelloSaie;
    
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
        this.vuoro                  = 1;
        kello         = new Kello();
        kello.tyhjenna();
        this.kelloSaie                  = new Thread(kello);
        kelloSaie.start();
        this.PELAAJA1.asetaPelialue(PELIALUE1);
        this.PELAAJA2.asetaPelialue(PELIALUE2);
    }
    
    public Kayttoliittyma kayttoliittyma() {
        return KAYTTOLIITTYMA;
    }
    
    public Saannot saannot() {
        return SAANNOT;
    }
    
    public Pelaaja vastapelaaja(Pelaaja pelaaja) {
        if(pelaaja == PELAAJA1) {
            return PELAAJA2;
        }
        return PELAAJA1;
    }
    
    public Pelialue pelialue1() {
        return PELIALUE1;
    }
    
    public Pelialue pelialue2() {
        return PELIALUE2;
    }
    
    public long aika() {
        return kello.aika() + katsojanPeliaika;
    }
    
    public void pysaytaAjanotto() {
        katsojanPeliaika += kello.aika();
    }
    
    public void jatkaAjanottoa() {
        kello.tyhjenna();
    }
    
    /**
     * Aloittaa pelikierroksen kulun.
     */
    public void aloita() {
        KAYTTOLIITTYMA.tulostaViesti("Pelikierros alkoi.");
        KAYTTOLIITTYMA.tulostaPelitilanne();
         
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
        if (tarkastaLoppuikoPeli(pelaaja)) return;
        Pelialue pelialue = vastapelaaja(pelaaja).annaPelialue();

        Komento komento;
        
        if (pelaaja.getClass() == Ihmispelaaja.class) {
            jatkaAjanottoa();
            komento = KAYTTOLIITTYMA.pyydaKomento();
            pysaytaAjanotto();
        } else {
            komento = pelaaja.annaKomento(new Komento(Komentotyyppi.AMMU));
        }
        
        kasitteleKomento(komento, pelialue);
    }
    
    private boolean tarkastaLoppuikoPeli(Pelaaja pelaaja) {
        if (!pelaaja.annaPelialue().laivojaOnJaljella()) {
            voittaja = vastapelaaja(pelaaja);
            peliJatkuu = false;
            KAYTTOLIITTYMA.tulostaViesti("Peli päättyi. Voittaja oli "
                    + ((voittaja == PELAAJA1) ? "pelaaja 1." : "pelaaja 2.\n"));
            KAYTTOLIITTYMA.tulostaLopputilanne();
            return true;
        }
        return false;
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
                kasitteleTyhjaKomento();
                return;
            case LUOVUTA:
                kasitteleLuovutus();
            case LOPETA:
                kasitteleLopetus();
                return;
            case OHJEET:
                kasitteleOhjeet();
                return;
//            case PAIVITA_KAYTTOLIITTYMA: // Varattu debuggausta varten
//                KAYTTOLIITTYMA.alusta();
//                KAYTTOLIITTYMA.tulostaPelitilanne();
//                return;
            case TILAKYSELY:
                kasitteleTilakysely(komento.PARAMETRIT[0]);
                return;
            case AMMU:
                kasitteleAmpuminen(pelialue, komento);
                return;
            case GOD_MODE:
                kasitteleHuijaus();
                return;
            default:
                throw new TuntematonKomentoException();
        }
    }
    
    private void kasitteleTyhjaKomento() throws TyhjaKomentoException {
        throw new TyhjaKomentoException();
    }

    private void kasitteleLuovutus() {
        // Asetetaan voittaja ja jatketaan eteenpäin.
        KAYTTOLIITTYMA.tulostaViesti("Pelaaja "
        + vuorossaolija.kerroNimi() + " luovutti pelin.\n");
        voittaja = vastapelaaja(vuorossaolija);
        KAYTTOLIITTYMA.tulostaLopputilanne();
    }

    private void kasitteleLopetus() throws InterruptedException {
        peliJatkuu = false;
        kelloSaie.join();
    }

    private void kasitteleOhjeet() {
        KAYTTOLIITTYMA.tulostaOhje();
    }
    
    private void kasitteleTilakysely(int kyselynumero) {
        switch (kyselynumero) {
            case Komento.TILAKYSELY_VUOROT:
                KAYTTOLIITTYMA.tulostaViesti("On " + vuoro + ". vuoro.\n");
                if (vuorotOnRajoitettu) {
                    KAYTTOLIITTYMA.tulostaViesti("Vuoroja on jäljellä "
                            + (SAANNOT.vuoroja() - vuoro) + ".\n");
                } else {
                    KAYTTOLIITTYMA.tulostaViesti("Vuorojen määrää ei "
                            + "ole rajoitettu.\n");
                }
                break;
            case Komento.TILAKYSELY_LAIVAT:
                KAYTTOLIITTYMA.tulostaViesti("Omia laivoja on jäljellä "
                        + vuorossaolija.annaPelialue().laivojaJaljella()
                        + " kpl.\n");
                KAYTTOLIITTYMA.tulostaViesti("Vastustajan laivoja on "
                        + "jäljellä " + vastapelaaja(vuorossaolija)
                        .annaPelialue().laivojaJaljella()
                        + " kpl.\n");
                break;
        }
    }

    private void kasitteleAmpuminen(Pelialue pelialue, Komento komento) throws Exception {
        try {
            pelialue.ammu(vuorossaolija,
                    komento.PARAMETRIT[0],
                    komento.PARAMETRIT[1]);
            if (vuorossaolija.getClass() == Tekoalypelaaja.class) {
                Thread.sleep(250);
            }
            if (osuiTaiUpposi(pelialue, komento)) return;
            vuorossaolija = vastapelaaja(vuorossaolija);
        } catch (Exception poikkeus) {
            POIKKEUSTENKASITTELIJA.kasittele(poikkeus);
        }
    }
    
    private boolean osuiTaiUpposi(Pelialue pelialue, Komento komento) throws IndexOutOfBoundsException {
        Ruutu r = pelialue.haeRuutu(vuorossaolija,
                komento.PARAMETRIT[0],
                komento.PARAMETRIT[1]);
        if (r == Ruutu.LAIVA_OSUMA || r == Ruutu.LAIVA_UPONNUT) {
            if (SAANNOT.osumastaSaaLisavuoron()) {
                return true;
            }
        }
        return false;
    }

    private void kasitteleHuijaus() {
        KAYTTOLIITTYMA.tulostaViesti("Jumaltila aktivoitu.\n");
        KAYTTOLIITTYMA.tulostaLopputilanne();
    }
}
