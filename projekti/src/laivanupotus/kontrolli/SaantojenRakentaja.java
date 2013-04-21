
package laivanupotus.kontrolli;

import laivanupotus.rajapinnat.Kayttoliittyma;

/**
 * Tämän luokan tarkoituksena on rakentaa uudet säännöt peliin käyttäjän 
 * komentojen perusteella.
 *
 * @author John Lång
 */
public final class SaantojenRakentaja {
    
    private final Kayttoliittyma KAYTTOLIITTYMA; 

    public SaantojenRakentaja(Kayttoliittyma kayttoliittyma) {
        this.KAYTTOLIITTYMA = kayttoliittyma;
    }
    
//    public Saannot rakennaSaannot() throws Exception {
//        // Tämänkin voisi toteuttaa fiksummin jos aikaa olisi...
//        Saannot saannot;
//        
//        KAYTTOLIITTYMA.tulostaViesti("Paljonko pelikentän leveydeksi tulee"
//                + "(väliltä [5, 20])?");
//        Komento k = KAYTTOLIITTYMA.pyydaKomento();
//        
//        KAYTTOLIITTYMA.tulostaViesti("Paljonko pelikentän korkeudeksi tulee"
//                + "(väliltä [5, 20])?");
//        Komento l = KAYTTOLIITTYMA.pyydaKomento();
//        
//        
//        
//        saannot = new Saannot();
//        return saannot;
//    }
}
