
package laivanupotus.kontrolli.sijoitus;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.kayttajat.Ihmispelaaja;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kayttajat.Tekoalypelaaja;
import laivanupotus.kontrolli.Poikkeustenkasittelija;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.tietorakenteet.Saannot;
import laivanupotus.tietorakenteet.enumit.Komentotyyppi;

/**
 * Vastaa pelaajan kaikkien laivojen sijoittamisesta pelialueelle sääntöjen 
 * mukaisesti. Laivojen sijoitus pelialueille tapahtuu ennen pelikierroksen 
 * aloittamista.
 *
 * @author John Lång
 * @see Saannot
 * @see LaivanSijoitin
 */
public final class LaivastonSijoitus {
    
    private static final Laivansijoitin     LAIVAN_SIJOITIN = new Laivansijoitin();
    
    private final Poikkeustenkasittelija    POIKKEUSTENKASITTELIJA;
    private final Kayttoliittyma            KAYTTOLIITTYMA;
    
    private Saannot                         saannot;
    private int                             leveys, korkeus;
    
    private Pelaaja                         pelaaja;
    private Pelialue                        pelialue;
    private Map<Integer, Integer>           laivojenMitatJaMaarat;
    private boolean                         sijoitetaanKasin;
    
    public LaivastonSijoitus(Poikkeustenkasittelija poikkeustenkasittelija,
            Kayttoliittyma kayttoliittyma) {
        this.POIKKEUSTENKASITTELIJA = poikkeustenkasittelija;
        this.KAYTTOLIITTYMA         = kayttoliittyma;
        
    }
        
    public void asetaSaannot(Saannot saannot) {
        this.saannot    = saannot;
        this.leveys     = saannot.leveys();
        this.korkeus    = saannot.korkeus();
    }
    
    /**
     * Sijoittaa kaikki pelaajan laivat.
     * 
     * @param pelaaja           Pelaaja jonka laivat sijoitetaan.
     * @param sijoitetaanKasin  Merkityksellinen ainoastaan jos parametrina 
     * pelaaja annetun olion luokka on Ihmispelaaja. Tekoalypelaaja-olioiden 
     * laivat sijoitetaan aina automaattisesti.
     * @throws Exception        Heitetään jos laivoja ei pystytty sijoittamaan 
     * pelialueelle sääntöjen mukaisesti.
     */
    public void sijoitaLaivasto(Pelaaja pelaaja,
            boolean sijoitetaanKasin) throws Exception {
        valmisteleLaivasto(pelaaja, sijoitetaanKasin);
        
        for (Integer laivanPituus : laivojenMitatJaMaarat.keySet()) {
            sijoitutaAlusluokanLaivat((int) laivanPituus,
                    (int) laivojenMitatJaMaarat.get(laivanPituus));
        }
    }
    
    private void sijoitutaAlusluokanLaivat(int luokka, int maara)
            throws Exception {
        for (int i = 0; i < maara; i++) {
            sijoitaLaiva(luokka);
        }
    }
    
    private void sijoitaLaiva(int pituus) throws Exception {
         
        boolean sijoitusOnnistui = false;
        Komento komento;
        
        while (!sijoitusOnnistui) {            
            if (!sijoitetaanKasin) {
                komento = pelaaja.annaKomento(new Komento(Komentotyyppi.SIJOITA_LAIVA, pituus));
            } else {
                // Pitää korjata tämä Ihmispelaajalle.
                komento = hankiSijoituskomento();
            }
            
            int x = komento.PARAMETRIT[0], y = komento.PARAMETRIT[1];
            int orientaatio = komento.PARAMETRIT[2];
            
            try {
                LAIVAN_SIJOITIN.sijoitaLaiva(x, y, orientaatio, pituus);
            } catch (Exception exception) {
                continue;
            }
            sijoitusOnnistui = true;
        }
        
       KAYTTOLIITTYMA.tulostaPelitilanne(); 
        
    }
    
    private void valmisteleLaivasto(Pelaaja pelaaja, boolean sijoitetaanKasin) {
        this.pelaaja            = pelaaja;
        this.pelialue           = pelaaja.annaPelialue();
        this.sijoitetaanKasin   = pelaaja.getClass() == Ihmispelaaja.class
                && sijoitetaanKasin;
        laivojenMitatJaMaarat   = saannot.annaLaivojenMitatJaMaarat();
        LAIVAN_SIJOITIN.asetaPelaaja(pelaaja);
    }
    
    private Komento hankiSijoituskomento() throws Exception {
        boolean saatiinKelvollinenKomento = false;
        Komento komento = new Komento();
        while (!saatiinKelvollinenKomento) {
            komento = KAYTTOLIITTYMA.pyydaKomento();
            try {
                tarkastaKomento(komento);
            } catch (IllegalArgumentException illegalArgumentException) {
                POIKKEUSTENKASITTELIJA.kasittele(illegalArgumentException);
                continue;
            }
            saatiinKelvollinenKomento = true;
        }
        return komento;
    }
    
    private void tarkastaKomento(Komento tarkastettava)
            throws IllegalArgumentException {
        if (tarkastettava.KOMENTOTYYPPI != Komentotyyppi.SIJOITA_LAIVA) {
            throw new IllegalArgumentException("Pelaaja antoi epäkelvon "
                    + "laivansijoituskomennon. (" + tarkastettava.KOMENTOTYYPPI.name() + ")");
        }
    }
    
}
