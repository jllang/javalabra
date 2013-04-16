
package laivanupotus.kontrolli;

import java.text.DecimalFormat;
import laivanupotus.rajapinnat.Kayttoliittyma;

/**
 * Luokan vastuulla on poikkeusten käsittely ja mahdollisten virheilmoitusten 
 * välittäminen käyttöliittymän tulostettavaksi tai kirjaaminen lokitiedostoon. 
 *
 * @author John Lång
 */
public final class Poikkeustenkasittelija {
    
    private final Kayttoliittyma    KAYTTOLIITTYMA;
    private final DecimalFormat     DESIMAALIFORMAATTI;
    private final boolean           VIESTIT_TULOSTETAAN, AIKALEIMAT_ON_KAYTOSSA;
    //private final boolean           LOKI_ON_KAYTOSSA; // Mahdollinen tulevaisuuden ominaisuus...
    
    public Poikkeustenkasittelija(Kayttoliittyma kayttoliittyma,
            boolean viestitTulostetaan, boolean aikaleimatOnKaytossa) {
        this.KAYTTOLIITTYMA         = kayttoliittyma;
        this.DESIMAALIFORMAATTI     = new DecimalFormat("0.000");
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
        long nykyhetki = System.currentTimeMillis() - Laivanupotus.KAYNNISTYSAIKA;
        String aikaleimallinenViesti = "[";
        aikaleimallinenViesti += String.format("%10s",
                DESIMAALIFORMAATTI.format((double) nykyhetki / 1000));
        aikaleimallinenViesti += "] " + viesti;
        kasitteleIlmanAikaleimaa(aikaleimallinenViesti);
    }
    
    private void kasitteleIlmanAikaleimaa(String viesti) {
        KAYTTOLIITTYMA.tulostaDebuggausViesti(viesti);
    }

}
