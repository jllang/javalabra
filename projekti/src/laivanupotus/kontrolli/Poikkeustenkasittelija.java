/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package laivanupotus.kontrolli;

import laivanupotus.rajapinnat.Kayttoliittyma;

/**
 *
 * @author John Lång
 */
public class Poikkeustenkasittelija {
    
    private final static long       ALOITUSHETKI = System.currentTimeMillis();
    private final Kayttoliittyma    KAYTTOLIITTYMA;
    private final boolean           VIESTIT_TULOSTETAAN, AIKALEIMAT_ON_KAYTOSSA;
    //private final boolean           LOKI_ON_KAYTOSSA;
    
    public Poikkeustenkasittelija(Kayttoliittyma kayttoliittyma,
            boolean viestitTulostetaan, boolean aikaleimatOnKaytossa) {
        this.KAYTTOLIITTYMA         = kayttoliittyma;
        this.VIESTIT_TULOSTETAAN    = viestitTulostetaan;
        this.AIKALEIMAT_ON_KAYTOSSA = aikaleimatOnKaytossa;
    }
    
    public void kasittele(Exception poikkeus) {
        if (AIKALEIMAT_ON_KAYTOSSA) {
            kasitteleAikaleimalla(poikkeus.getMessage());
        } else {
            kasitteleIlmanAikaleimaa(poikkeus.getMessage());
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
