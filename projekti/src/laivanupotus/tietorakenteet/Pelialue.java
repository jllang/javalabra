
package laivanupotus.tietorakenteet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.poikkeukset.RuutuunOnJoAmmuttuException;
import laivanupotus.poikkeukset.SaantojenvastainenSijoitusException;
import laivanupotus.poikkeukset.VaaraPelialueException;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.enumit.Ruutu;

/**
 * Tämän luokan tarkoituksena on hallita pelaajan ruudukkoa sääntöjen mukaisesti.
 *
 * @author John Lång
 * @see Saannot
 */
public final class Pelialue {
    
    private final Kayttoliittyma    KAYTTOLIITTYMA;
    private final Pelaaja           OMISTAJA;
    private final Piste[][]         KOORDINAATISTO;
    private final Map<Piste, Laiva> PISTEET_JA_LAIVAT;  // Jokin mättää.
    private final int               LEVEYS, KORKEUS;
    
    private List<Piste>             pistelista;
    private List<Integer>           koordinaattilista;
    private int                     ehjaPintaAla, laivoja;
    
    /**
     * Luo uuden pelialueen liittäen sen käynnissä olevaan pelikierrokseen ja 
     * asettaen sen omistajaksi parametrina annetun pelaajan. Tietoa pelialueen 
     * omistajasta tarvitaan monessa luokan tarjoamassa palvelussa.
     * 
     * @param pelikierros   Nykyinen pelikierros.
     * @param omistaja      Pelialueen omistaja, ts. pelaaja joka näkee pelin 
     * alusta alkaen kaikki pelialueen laivat.
     */
    public Pelialue(Pelikierros pelikierros, Pelaaja omistaja) {
        this.KAYTTOLIITTYMA     = pelikierros.kayttoliittyma();
        this.OMISTAJA           = omistaja;
        this.LEVEYS             = pelikierros.saannot().leveys();
        this.KORKEUS            = pelikierros.saannot().korkeus();
        this.KOORDINAATISTO     = new Piste[KORKEUS][LEVEYS]; 
        this.PISTEET_JA_LAIVAT  = new HashMap<>();
        this.pistelista         = new ArrayList<>();
        this.koordinaattilista  = new ArrayList<>();
        this.ehjaPintaAla       = 0;
        this.laivoja            = 0;
        for (int i = 0; i < KORKEUS; i++) {
            for (int j = 0; j < LEVEYS; j++) {
                KOORDINAATISTO[i][j] = new Piste();
            }
        }
    }
    
    /**
     * Peittää annetussa sijainnissa olevan pisteen laivalla mikäli mahdollista.
     * 
     * @param lisaaja   Pelialueen omistaja. Parametria käytetään 
     * varmistamaan että pelaaja omistaa pelialueen johon hän yrittää lisätä 
     * laivan. Jos annettu pelaaja ei ole pelialueen omistaja, heitetään 
     * poikkeus <tt>VaaranPelaajanRuutuException</tt>.
     * @param x             Lisättävän laivan x-koordinaatti (kokonaislukuna).
     * @param y             Lisättävän laivan y-koordinaatti.
     * @throws Exception    Mahdollinen sääntöjen vastaisen laivan 
     * lisäysyrityksen aiheuttama poikkeus.
     * @see VaaranPelaajanRuutuException
     * @see RuudussaOnJoLaivaException
     * @see IndexOutOfBoundsException
     */
    public void peitaSijaintiLaivalla(Pelaaja lisaaja, int x, int y)
            throws Exception {
        tarkastaOmistajuus(lisaaja, true);
        Piste piste = haePiste(x, y);

        if (!pisteessaOnLaiva(piste)) {
            koordinaattilista.add(x);
            koordinaattilista.add(y);
            pistelista.add(piste);
        } else {
            // Tätä poikkeusta ei saisi tapahtua.
            throw new SaantojenvastainenSijoitusException("Sääntörikkomus: "
                    + "Ruutuun yritettiin asettaa uudelleen laiva.");
        }
    }
    
    /**
     * Tämä metodi tulee suorittaa sen jälkeen kun lisättävän laiva on kokonaan 
     * lisätty pelialueelle. Metodi muunmuassa lisää uuden laivan
     * hajautuskarttaan ja antaa päivityttää käyttöliittymän välimuistin.
     */
    public void viimeisteleSijoitus() {
        Laiva laiva = new Laiva(pistelista, koordinaattilista);
        for (Piste piste : pistelista) {
            piste.onOsaLaivaa = true;
            ehjaPintaAla++;
            PISTEET_JA_LAIVAT.put(piste, laiva);
        }
        laivoja++;
        paivityta(koordinaattilista);
        pistelista          = new ArrayList<>();
        koordinaattilista   = new ArrayList<>();
    }
    
    /**
     * Ampuu annettuun ruutuun ja kutsuu käyttöliittymän metodia 
     * <tt>paivita</tt> mikäli mahdollista.
     * 
     * @param ampuja        Pelialueen omistajan vastapelaaja. Parametria 
     * käytetään varmistamaan ettei pelaaja ammu omaan ruutuunsa.
     * @param x             Lisättävän laivan x-koordinaatti (kokonaislukuna).
     * @param y             Lisättävän laivan y-koordinaatti.
     * @throws Exception    Mahdollinen sääntöjen vastaisen laivan 
     * lisäysyrityksen aiheuttama poikkeus.
     * @throws Exception 
     * @see VaaranPelaajanRuutuException
     * @see RuutuunOnJoAmmuttuException
     * @see IndexOutOfBoundsException
     */
    public void ammu(Pelaaja ampuja, int x, int y) throws Exception {
        tarkastaOmistajuus(ampuja, false);
        Piste piste = haePiste(x, y);
        
        if (!pisteessaOnOsuma(piste)) {
            piste.onAmmuttu = true;
        } else {
            throw new RuutuunOnJoAmmuttuException();
        }
        
        if (pisteessaOnLaiva(piste)) {
            ehjaPintaAla--;
            tarkastaUpposiko(ampuja, piste);
        } else if (ampuja == KAYTTOLIITTYMA.annaKatsoja()) {
            KAYTTOLIITTYMA.tulostaViesti("Ei osumaa.\n");
        }
        
        KAYTTOLIITTYMA.paivita(this, x, y);
    }
    
    public Ruutu[][] nakyma(Pelaaja asiakas) {
        
        Ruutu[][] ruudukko = new Ruutu[KORKEUS][LEVEYS];
        
        for (int i = 0; i < KORKEUS; i++) {
            for (int j = 0; j < LEVEYS; j++) {
                ruudukko[i][j] = haeRuutu(asiakas, j, i);
            }
        }
        
        return ruudukko;
    }
        
    public Ruutu haeRuutu(Pelaaja asiakas, int x, int y)
            throws IndexOutOfBoundsException {
        Piste piste = haePiste(x, y);
        Ruutu ruutu = Ruutu.TUNTEMATON;
        if (piste.onAmmuttu) {
            if (piste.onOsaLaivaa) {
                if (piste.onOsaUponnuttaLaivaa) {
                    ruutu = Ruutu.LAIVA_UPONNUT;
                } else {
                    ruutu = Ruutu.LAIVA_OSUMA;
                }
            } else  {
                ruutu = Ruutu.TYHJA_OSUMA;
            }
        }
        else if (pelaajaOnOmistaja(asiakas)) {
            if (piste.onOsaLaivaa) {
                ruutu = Ruutu.LAIVA_EI_OSUMAA;
            } else {
                ruutu = Ruutu.TYHJA_EI_OSUMAA;
            }
        }
        return ruutu;
    }
    
    public int laivojaJaljella() {
        return laivoja;
    }
    
    public boolean laivojaOnJaljella() {
        return ehjaPintaAla > 0;
    }
    
    public int laivapintaAlaaJaljella() {
        //Väliaikainen ratkaisu LaivojenArpojan tuloksen tarkistamiseksi.
        return ehjaPintaAla;
    }
    
    private Piste haePiste(int x, int y) throws IndexOutOfBoundsException {
        tarkastaKoordinaatit(x, y);
        return KOORDINAATISTO[y][x];
    }
    
    private void tarkastaUpposiko(Pelaaja ampuja, Piste piste) {
        Laiva laiva = PISTEET_JA_LAIVAT.get(piste);
        if (laiva.upposi()) {
            if (ampuja == KAYTTOLIITTYMA.annaKatsoja()) {
                // Parempi tekoäly tarvitsisi tietoa tästä tapahtumasta esim.
                // jonkin boolean-kentän muodossa.
                // Upotetulle laivalle voisi ehkä varata oman värin käyttöliit-
                // tymässä.
                KAYTTOLIITTYMA.tulostaViesti("Osui ja upposi!\n");
            }
            poistaHylky(laiva);
        } else if (ampuja == KAYTTOLIITTYMA.annaKatsoja()) {
            KAYTTOLIITTYMA.tulostaViesti("Osuma!\n");
        }
    }
     
    private void poistaHylky(Laiva laiva) {
        // Tieto kaikista uponneista ruuduista pitäisi välittää jotenkin käyttö-
        // liittymälle jotta näkymä voitaisiin päivittää.
        for (Piste piste : laiva.annaPisteet()) {
            piste.onOsaUponnuttaLaivaa = true;
            PISTEET_JA_LAIVAT.remove(piste);
        }
        List<Integer> koordinaatit = laiva.annaKoordinaatit();
        paivityta(koordinaatit);
        laivoja--;
    }
    
    private void paivityta(List<Integer> koordinaatit) {
        int x, y;
        for (int i = 0, j = 1; j < koordinaatit.size(); i += 2, j += 2) {
            x = koordinaatit.get(i);
            y = koordinaatit.get(j);
            KAYTTOLIITTYMA.paivita(this, x, y);
        }
    }
    
    private void tarkastaKoordinaatit(int x, int y)
            throws IndexOutOfBoundsException {
        if (x < 0
                || x >= LEVEYS
                || y < 0
                || y >= KORKEUS) {
            throw new IndexOutOfBoundsException("Annetut koordinaatit (" + x + "," + y + ") eivät ole koordinaatistossa.");
        }
    }
    
    private void tarkastaOmistajuus(Pelaaja pelaaja, boolean odotettuPaluuarvo)
            throws VaaraPelialueException {
        if (pelaajaOnOmistaja(pelaaja) != odotettuPaluuarvo) {
            if (odotettuPaluuarvo) {
                throw new VaaraPelialueException("Sääntörikkomus: Yritettiin suorittaa ruudukon omistajalle kuuluvaa toimintoa.");
            } else {
                throw new VaaraPelialueException("Sääntörikkomus: Yritettiin suorittaa vastapelaajalle kuuluvaa toimintoa.");
            }
        }
    }
    
    private boolean pelaajaOnOmistaja(Pelaaja pelaaja) {
        return pelaaja == OMISTAJA;
    }
    
    private boolean pisteessaOnLaiva(Piste piste) {
        return piste.onOsaLaivaa;
    }
    
    private boolean pisteessaOnOsuma(Piste piste) {
        return piste.onAmmuttu;
    }
    
}