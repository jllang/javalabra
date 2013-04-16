
package laivanupotus.poikkeukset;

/**
 * Tämä poikkeus heitetään jos laiva yritetään sijoittaa pelialueelle 
 * laivanupotuksen sääntöjen vastaisesti. Näin tapahtuu jos yritetään sijoittaa 
 * laivaa ruutuun, johon on jo sijoitettu laiva, jos laivoja yritetään sijoittaa 
 * vierekkäisiin ruutuihin (laivat saavat tosin koskea kulmittain) tai jos 
 * laiva ei mahdu kokonaisuudessaan pelialueelle.
 *
 * @author John Lång
 */
public final class SaantojenvastainenSijoitusException extends Exception {

    public SaantojenvastainenSijoitusException(String viesti) {
        super(viesti);
    }
    
}
