/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.tietorakenteet;

import java.util.List;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.poikkeukset.OmistajaOnJoAsetettuException;
import laivanupotus.poikkeukset.RuudussaOnJoLaivaException;
import laivanupotus.poikkeukset.RuutuunOnJoAmmuttuException;
import laivanupotus.poikkeukset.VaaranPelaajanRuutuException;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.rajapinnat.Tallennettava;
import laivanupotus.tietorakenteet.Piste;
import laivanupotus.tietorakenteet.enumit.Ruutu;
import laivanupotus.tietorakenteet.Saannot;

/**
 *
 * @author John Lång
 */
public class Pelialue {
    
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
    
    public void lisaaLaiva(Pelaaja omistaja, int x, int y) throws Exception {
        tarkastaOmistajuus(omistaja, true);
        Piste piste = haePiste(x, y);

        if (!pisteessaOnLaiva(piste)) {
            piste.osaLaivaa = true;
            ehjaPintaAla++;
        } else {
            throw new RuudussaOnJoLaivaException();
        }
        
        KAYTTOLIITTYMA.paivita(this, x, y);
    }
    
    public void ammu(Pelaaja ampuja, int x, int y) throws Exception {
        tarkastaOmistajuus(ampuja, false);
        Piste piste = haePiste(x, y);
        
        if (!pisteessaOnOsuma(piste)) {
            piste.osuma = true;
        } else {
            throw new RuutuunOnJoAmmuttuException();
        }
        
        if (pisteessaOnLaiva(piste)) {
            ehjaPintaAla--;
        }
        
        KAYTTOLIITTYMA.paivita(this, x, y);
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
        
    public Ruutu haeRuutu(Pelaaja pelaaja, int x, int y) throws IndexOutOfBoundsException {
        Piste piste = haePiste(x, y);
        Ruutu ruutu = Ruutu.TUNTEMATON;
        if (piste.osuma) {
            if (piste.osaLaivaa) {
                ruutu = Ruutu.LAIVA_OSUMA;
            } else  {
                ruutu = Ruutu.TYHJA_OSUMA;
            }
        }
        else if (pelaajaOnOmistaja(pelaaja)) {
            if (piste.osaLaivaa) {
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
    
    private void tarkastaKoordinaatit(int x, int y) throws IndexOutOfBoundsException {
        if (x < 0
                || x >= LEVEYS
                || y < 0
                || y >= KORKEUS) {
            throw new IndexOutOfBoundsException("Annetut koordinaatit eivät ole koordinaatistossa.");
        }
    }
    
    private void tarkastaOmistajuus(Pelaaja pelaaja, boolean odotettuPaluuarvo) throws VaaranPelaajanRuutuException {
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
        return piste.osaLaivaa;
    }
    
    private boolean pisteessaOnOsuma(Piste piste) {
        return piste.osuma;
    }
    
}