
package laivanupotus.kontrolli.sijoitus;

import java.util.Map;
import laivanupotus.kayttajat.Ihmispelaaja;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kontrolli.Poikkeustenkasittelija;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Komento;
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
public final class LaivastonSijoituttaja {
    
    private static final Laivansijoitin     LAIVANSIJOITIN = new Laivansijoitin();
    
    private final Poikkeustenkasittelija    POIKKEUSTENKASITTELIJA;
    private final Kayttoliittyma            KAYTTOLIITTYMA;
    
    private Saannot                         saannot;    
    private Pelaaja                         pelaaja;
    private Map<Integer, Integer>           laivojenMitatJaMaarat;
    private boolean                         sijoitetaanKasin;
    
    public LaivastonSijoituttaja(Poikkeustenkasittelija poikkeustenkasittelija,
            Kayttoliittyma kayttoliittyma) {
        this.POIKKEUSTENKASITTELIJA = poikkeustenkasittelija;
        this.KAYTTOLIITTYMA         = kayttoliittyma;
        
    }
        
    public void asetaSaannot(Saannot saannot) {
        this.saannot    = saannot;
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
            sijoitutaLaiva(luokka);
        }
    }
    
    private void sijoitutaLaiva(int pituus) throws Exception {
         
        boolean sijoitusOnnistui = false;
        Komento komento;
        int i = 0;
        
        while (!sijoitusOnnistui) {
            i++;
            if (!sijoitetaanKasin) {
                komento = pelaaja.annaKomento(new Komento(Komentotyyppi.SIJOITA_LAIVA, pituus));
            } else {
                komento = hankiSijoituskomentoKayttoliittymalta();
            }
            
            int x = komento.PARAMETRIT[0], y = komento.PARAMETRIT[1];
            int orientaatio = komento.PARAMETRIT[2];
            
            try {
                LAIVANSIJOITIN.sijoitaLaiva(x, y, orientaatio, pituus);
            } catch (Exception poikkeus) {
                if (pelaaja == KAYTTOLIITTYMA.annaKatsoja()) {
                    POIKKEUSTENKASITTELIJA.kasittele(poikkeus);
                }
                continue;
            }
            sijoitusOnnistui = true;
            if (sijoitetaanKasin) {
                KAYTTOLIITTYMA.tulostaPelitilanne();
            }
        }
        
    }
    
    private void valmisteleLaivasto(Pelaaja pelaaja, boolean sijoitetaanKasin) {
        this.pelaaja            = pelaaja;
        this.sijoitetaanKasin   = pelaaja.getClass() == Ihmispelaaja.class
                && sijoitetaanKasin;
        laivojenMitatJaMaarat   = saannot.annaLaivojenMitatJaMaarat();
        LAIVANSIJOITIN.asetaPelaaja(pelaaja);
    }
    
    private Komento hankiSijoituskomentoKayttoliittymalta() throws Exception {
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
                    + "laivansijoituskomennon.\n");
        }
    }
    
}
