
package laivanupotus.kayttajat;

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
        return Tekoalypelaaja.annaSijoituskomento(odotettu.PARAMETRIT[0]);
    }
    
}
