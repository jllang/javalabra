/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import laivanupotus.poikkeukset.OmistajaOnJoAsetettu;
import laivanupotus.poikkeukset.RuutuunOnJoAmmuttu;
import laivanupotus.poikkeukset.VaaranPelaajanRuutu;
import laivanupotus.tyypit.Piste;
import laivanupotus.tyypit.Ruutu;
import laivanupotus.tyypit.Saannot;

/**
 *
 * @author John Lång
 */
public class Ruudukko {
    
    private Pelaaja         omistaja;
    private boolean         omistajaAsetettu;
    private final Piste[][] KOORDINAATISTO;
    private final int       LEVEYS;
    private final int       KORKEUS;
    
    public Ruudukko(Saannot saannot) {
        this.LEVEYS = saannot.leveys();
        this.KORKEUS = saannot.korkeus();
        this.KOORDINAATISTO = new Piste[KORKEUS][LEVEYS];
        
        for (int i = 0; i < KORKEUS; i++) {
            for (int j = 0; j < LEVEYS; j++) {
                KOORDINAATISTO[i][j] = new Piste();
            }
        }
    }
    
    public void asetaOmistaja(Pelaaja omistaja) throws OmistajaOnJoAsetettu {
        if (!omistajaAsetettu) {            
            this.omistaja = omistaja;
            omistajaAsetettu = true;
        } else {
            throw new OmistajaOnJoAsetettu();
        }
    }
    
    public void lisaaLaiva(Pelaaja omistaja, int x, int y) throws Exception {
        tarkastaOmistajuus(omistaja, true);
        Piste piste = haePiste(x, y);

        if (!onkoLaivaa(piste)) {
            piste.osaLaivaa = true;
        } else {
            piste.osaLaivaa = false;
        }
    }
    
    public void ammu(Pelaaja ampuja, int x, int y) throws Exception {
        tarkastaOmistajuus(ampuja, false);
        Piste piste = haePiste(x, y);
        
        if (!onkoOsumaa(piste)) {
            piste.osuma = true;
        } else {
            throw new RuutuunOnJoAmmuttu();
        }
    }
    
    public Ruutu[][] haeRuudut(Pelaaja pelaaja) {
        
        Ruutu[][] ruudukko = new Ruutu[KORKEUS][LEVEYS];
        
        for (int i = 0; i < KORKEUS; i++) {
            for (int j = 0; j < LEVEYS; j++) {
                ruudukko[i][j] = haeRuutu(pelaaja, j, i);
            }
        }
        
        return ruudukko;
    }
        
    public Ruutu haeRuutu(Pelaaja pelaaja, int x, int y) {
        Piste piste = haePiste(x, y);
        Ruutu ruutu = Ruutu.TUNTEMATON;
        if (piste.osuma) {
            if (piste.osaLaivaa) {
                ruutu = Ruutu.LAIVA_OSUMA;
            } else  {
                ruutu = Ruutu.TYHJA_OSUMA;
            }
        }
        else if (onkoPelaajaOmistaja(pelaaja)) {
            if (piste.osaLaivaa) {
                ruutu = Ruutu.LAIVA_EI_OSUMAA;
            } else {
                ruutu = Ruutu.TYHJA_EI_OSUMAA;
            }
        }
        return ruutu;
    }
    
    private Piste haePiste(int x, int y) throws IndexOutOfBoundsException {
        tarkastaKoordinaatit(x, y);
        return KOORDINAATISTO[y][x];
    }
    
    private void tarkastaKoordinaatit(int x, int y) throws IndexOutOfBoundsException {
        if (x < 0
                || x >= KOORDINAATISTO[0].length
                || y < 0
                || y >= KOORDINAATISTO.length) {
            throw new IndexOutOfBoundsException("Annetut koordinaatit eivät ole koordinaatistossa.");
        }
    }
    
    private void tarkastaOmistajuus(Pelaaja pelaaja, boolean odotettuPaluuarvo) throws VaaranPelaajanRuutu {
        if (onkoPelaajaOmistaja(pelaaja) != odotettuPaluuarvo) {
            if (odotettuPaluuarvo) {
                throw new VaaranPelaajanRuutu("Sääntörikkomus: Yritettiin suorittaa ruudukon omistajalle kuuluvaa toimintoa.");
            } else {
                throw new VaaranPelaajanRuutu("Sääntörikkomus: Yritettiin suorittaa vastapelaajalle kuuluvaa toimintoa.");
            }
        }
    }
    
    private boolean onkoPelaajaOmistaja(Pelaaja pelaaja) {
        return pelaaja == omistaja;
    }
    
    private boolean onkoLaivaa(Piste piste) {
        return piste.osaLaivaa;
    }
    
    private boolean onkoOsumaa(Piste piste) {
        return piste.osuma;
    }

    /* Tätä metodia voisi käyttää pelin tallentamiseen (edellyttäen rajapinnan 
     * "Tallennettava" implementointia):
     */
//    @Override
//    public List<Object> annaSisalto() {
//        List<Object> sisalto = new ArrayList<>();
//        for (int i = 0; i < KORKEUS; i++) {
//            for (int j = 0; j < LEVEYS; j++) {
//                sisalto.add(KOORDINAATISTO[i][j]);
//            }
//        }
//        return sisalto;
//    }
    
}
