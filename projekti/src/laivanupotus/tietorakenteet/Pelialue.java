
package laivanupotus.tietorakenteet;

import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.poikkeukset.RuudussaOnJoLaivaException;
import laivanupotus.poikkeukset.RuutuunOnJoAmmuttuException;
import laivanupotus.poikkeukset.VaaranPelaajanRuutuException;
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
    private final int               LEVEYS, KORKEUS;
    
    private int                     ehjaPintaAla;
    
    public Pelialue(Pelikierros pelikierros, Pelaaja pelaaja) {
        this.KAYTTOLIITTYMA = pelikierros.annaKayttoliittyma();
        this.OMISTAJA = pelaaja;
        this.LEVEYS = pelikierros.annaSaannot().leveys();
        this.KORKEUS = pelikierros.annaSaannot().korkeus();
        this.KOORDINAATISTO = new Piste[KORKEUS][LEVEYS];        
        for (int i = 0; i < KORKEUS; i++) {
            for (int j = 0; j < LEVEYS; j++) {
                KOORDINAATISTO[i][j] = new Piste();
            }
        }
        this.ehjaPintaAla = 0;
    }
    
    /**
     * Lisää annettuun ruutuun laivan mikäli mahdollista.
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
    public void lisaaLaiva(Pelaaja lisaaja, int x, int y) throws Exception {
        tarkastaOmistajuus(lisaaja, true);
        Piste piste = haePiste(x, y);

        if (!pisteessaOnLaiva(piste)) {
            piste.onOsaLaivaa = true;
            ehjaPintaAla++;
        } else {
            throw new RuudussaOnJoLaivaException();
        }
        
        KAYTTOLIITTYMA.paivita(this, x, y);
    }
    
    /**
     * Ampuu annettuun ruutuun ja kutsuu käyttöliittymän metodia 
     * <tt>paivita</tt> mikäli mahdollista.
     * 
     * @see Pelialue#ammu(laivanupotus.kayttajat.Pelaaja, int, int) 
     * @see Kayttoliittyma#paivita(laivanupotus.tietorakenteet.Pelialue, int, int) 
     */
    public void ammuJaPaivityta(Pelaaja ampuja, int x, int y) throws Exception {
        ammu(ampuja, x, y);
        
        KAYTTOLIITTYMA.paivita(this, x, y);
    }
    
    /**
     * Ampuu annettuun ruutuun mikäli mahdollista.
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
    private void ammu(Pelaaja ampuja, int x, int y) throws Exception {
        tarkastaOmistajuus(ampuja, false);
        Piste piste = haePiste(x, y);
        
        if (!pisteessaOnOsuma(piste)) {
            piste.onAmmuttu = true;
        } else {
            throw new RuutuunOnJoAmmuttuException();
        }
        
        if (pisteessaOnLaiva(piste)) {
            ehjaPintaAla--;
        }
    }
    
    public Ruutu[][] haeRuudukko(Pelaaja pelaaja) {
        
        Ruutu[][] ruudukko = new Ruutu[KORKEUS][LEVEYS];
        
        for (int i = 0; i < KORKEUS; i++) {
            for (int j = 0; j < LEVEYS; j++) {
                ruudukko[i][j] = haeRuutu(pelaaja, j, i);
            }
        }
        
        return ruudukko;
    }
        
    public Ruutu haeRuutu(Pelaaja pelaaja, int x, int y)
            throws IndexOutOfBoundsException {
        Piste piste = haePiste(x, y);
        Ruutu ruutu = Ruutu.TUNTEMATON;
        if (piste.onAmmuttu) {
            if (piste.onOsaLaivaa) {
                ruutu = Ruutu.LAIVA_OSUMA;
            } else  {
                ruutu = Ruutu.TYHJA_OSUMA;
            }
        }
        else if (pelaajaOnOmistaja(pelaaja)) {
            if (piste.onOsaLaivaa) {
                ruutu = Ruutu.LAIVA_EI_OSUMAA;
            } else {
                ruutu = Ruutu.TYHJA_EI_OSUMAA;
            }
        }
        return ruutu;
    }
    
    public int laivojaJaljella() {
        return ehjaPintaAla;
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
    
    private void tarkastaKoordinaatit(int x, int y)
            throws IndexOutOfBoundsException {
        if (x < 0
                || x >= LEVEYS
                || y < 0
                || y >= KORKEUS) {
            throw new IndexOutOfBoundsException("Annetut koordinaatit eivät ole koordinaatistossa.");
        }
    }
    
    private void tarkastaOmistajuus(Pelaaja pelaaja, boolean odotettuPaluuarvo)
            throws VaaranPelaajanRuutuException {
        if (pelaajaOnOmistaja(pelaaja) != odotettuPaluuarvo) {
            if (odotettuPaluuarvo) {
                throw new VaaranPelaajanRuutuException("Sääntörikkomus: Yritettiin suorittaa ruudukon omistajalle kuuluvaa toimintoa.");
            } else {
                throw new VaaranPelaajanRuutuException("Sääntörikkomus: Yritettiin suorittaa vastapelaajalle kuuluvaa toimintoa.");
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