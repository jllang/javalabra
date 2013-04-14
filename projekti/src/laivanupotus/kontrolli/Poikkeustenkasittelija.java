
package laivanupotus.kontrolli;

import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.rajapinnat.Kayttoliittyma;

/**
 * Luokan vastuulla on poikkeusten käsittely ja mahdollisten virheilmoitusten 
 * välittäminen käyttöliittymän tulostettavaksi tai kirjaaminen lokitiedostoon. 
 * Huom. metodia kasitteleAikaleimalla ei ole vielä toteutettu.
 *
 * @author John Lång
 */
public final class Poikkeustenkasittelija {
    
    private final static long       ALOITUSHETKI = System.currentTimeMillis();
    private final Kayttoliittyma    KAYTTOLIITTYMA;
    private final boolean           VIESTIT_TULOSTETAAN, AIKALEIMAT_ON_KAYTOSSA;
    //private final boolean           LOKI_ON_KAYTOSSA; // Mahdollinen tulevaisuuden ominaisuus...
    
    public Poikkeustenkasittelija(Kayttoliittyma kayttoliittyma,
            boolean viestitTulostetaan, boolean aikaleimatOnKaytossa) {
        this.KAYTTOLIITTYMA         = kayttoliittyma;
        this.VIESTIT_TULOSTETAAN    = viestitTulostetaan;
        this.AIKALEIMAT_ON_KAYTOSSA = aikaleimatOnKaytossa;
    }
    
    /**
     * Käsittelee annetun poikkeuksen konstruktorissa määritellyllä tavalla.
     * 
     * @param poikkeus Käsiteltävä poikkeus.
     */
    public void kasittele(Exception poikkeus) {
        if (VIESTIT_TULOSTETAAN) {
            if (AIKALEIMAT_ON_KAYTOSSA) {
                kasitteleAikaleimalla(poikkeus.getMessage());
            } else {
                kasitteleIlmanAikaleimaa(poikkeus.getMessage());
            }
        }
    }
    
    private void kasitteleAikaleimalla(String viesti) {
        String aikaleimallinenViesti;
        throw new UnsupportedOperationException("Keskeneräinen ominaisuus...");
    }
    
    private void kasitteleIlmanAikaleimaa(String viesti) {
        KAYTTOLIITTYMA.tulostaDebuggausViesti(viesti);
    }

}
