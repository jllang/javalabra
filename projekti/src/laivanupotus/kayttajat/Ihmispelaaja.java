
package laivanupotus.kayttajat;

import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Komento;

/**
 *
 * @author John Lång
 */
public final class Ihmispelaaja extends Pelaaja {
    
    public Ihmispelaaja(String nimi) {
        super(nimi);
    }

    @Override
    public Komento annaKomento(Komento odotettu) throws Exception {
        // Menee vähän ihmeelliseksi pyörittelyksi mutta saa kelvata toistaiseksi.
        return Tekoalypelaaja.annaSatunnainenSijoituskomento(odotettu.PARAMETRIT[0]);
    }
    
}
