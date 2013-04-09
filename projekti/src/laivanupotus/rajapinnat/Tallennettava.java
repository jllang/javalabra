
package laivanupotus.rajapinnat;

import java.util.List;

/**
 * Tämän rajapinnan toteuttavien olioiden tila pystytään kirjoittamaan ja 
 * lukemaan rajapinnan Tallentaja implementaation avulla.
 *
 * @author John Lång
 * @see Tallentaja
 */
public interface Tallennettava {
    
    /**
     * Palauttaa olion tilan sarjallistettavassa muodossa tallentamista varten.
     *
     * @return Olion tila listamuodossa.
     */
    List<Object> annaSisalto();
    
    /**
     * Rakentaa olion kentät parametrina annetun listan tietojen perusteella.
     *
     * @param luettuSisalto Olion tila listamuodossa.
     */
    void rakennaSisalto(List<Object> luettuSisalto);
    
}
