
package laivanupotus.tietorakenteet;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import laivanupotus.rajapinnat.Tallennettava;

/**
 * Tämän luokan instanssin tehtävänä on sisältää tieto pelikierroksella 
 * käytettävistä pelin säännöistä sekä varmistaa mahdollisten käyttäjän antamien 
 * sääntöjen tarkoituksenmukaisuus.
 *
 * @author John Lång
 */
public class Saannot implements Tallennettava {
    
    private final List<Object> VARASTO;
    
    /**
     * Luo annettujen parametrien mukaiset säännöt mikäli se on 
     * tarkoituksenmukaista.
     * 
     * @param leveys                Pelialueen leveys.
     * @param korkeus               Pelialueen korkeus.
     * @param vuoroja               Suurin sallittu pelivuorojen määrä.
     * @param laivojenMitatJaMaarat <tt>TreeMap</tt>-tyypin puukartta, jonka 
     * avaimina tulee olla pelissä käytettävien laivojen pituudet ja arvoina 
     * määrät. On syytä huomata, että kaikille laivojen pituuksille 1..n tulee 
     * olla avain kartassa vaikka kyseisen mittaisten laivojen määrä olisikin 0.
     * (Käytettävä tietorakenne on nimenomaan <tt>TreeMap</tt> sillä avainten 
     * oletetaan olevan suuruusjärjestyksessä.)
     */
    public Saannot(int leveys, int korkeus, int vuoroja,
            TreeMap<Integer, Integer> laivojenMitatJaMaarat) {
        tarkastaArvot(leveys, korkeus, vuoroja, laivojenMitatJaMaarat);
        this.VARASTO = new ArrayList<>();
        VARASTO.add(leveys);
        VARASTO.add(korkeus);
        VARASTO.add(vuoroja);
        // Laivojen pituuksien puuttumista ei tulla tarkistamaan koska käyttäjä 
        // ei pääse suoraan lisäämään avain-arvopareja TreeMappiin sitten 
        // tulevaisuudessa kun kerkeän toteuttamaan pelaajan omien sääntöjen 
        // luomisen peliin.
        lisaaLaivojenMitatJaMaarat(laivojenMitatJaMaarat);
    }
    
    /**
     *  Luo oletusarvoiset säännöt.
     */
    public Saannot() {
        this.VARASTO = new ArrayList<>();
        VARASTO.add(10);
        VARASTO.add(10);
        VARASTO.add(0);
        VARASTO.add(4);     // Pisimmän laivan pituus
        VARASTO.add(4);     // 4 * 1 ruutu
        VARASTO.add(6);     // 3 * 2 ruutua
        VARASTO.add(6);     // 2 * 3 ruutua
        VARASTO.add(4);     // 1 * 4 ruutua
    }
    
    private void tarkastaArvot(int leveys, int korkeus, int vuoroja,
            TreeMap<Integer, Integer> laivojenMitatJaMaarat) {
        tarkastaPelialueenMitat(leveys, korkeus, vuoroja);
        tarkastaLaivojenMitatJaPintaAlat(laivojenMitatJaMaarat, leveys, korkeus);
        
    }
    
    private void tarkastaPelialueenMitat(int leveys, int korkeus, int vuoroja)
            throws IllegalArgumentException {
        if (leveys < 5
                || leveys > 20
                || korkeus < 5
                || korkeus > 20
                || vuoroja < 0) {
            throw new IllegalArgumentException("Virheelliset säännöt: Ruudukon korkeuden tulee olla väliltä [5 ... 20] ja vuorojen määrä ei saa olla negatiivinen.");
        }
    }

    private void tarkastaLaivojenMitatJaPintaAlat(
            TreeMap<Integer, Integer> laivojenMitatJaMaarat,
            int leveys,
            int korkeus)
            throws IllegalArgumentException {
        if(laivojenMitatJaMaarat == null || laivojenMitatJaMaarat.isEmpty()) {
            throw new IllegalArgumentException("Virheelliset säännöt: "
                    + "Laivojen määriä ja mittoja ei annettu.");
        }
        
        int laivojenKokonaisPintaAla = 0, laivojenLukumaara = 0;
        
        for (Integer pituus : laivojenMitatJaMaarat.keySet()) {
            if (pituus > leveys || pituus > korkeus) {
                throw new IllegalArgumentException("Virheelliset säännöt: "
                        + "Laivojen pituudet eivät saa ylittää pelialueen "
                        + "mittoja.");
            }
            laivojenKokonaisPintaAla += pituus * laivojenMitatJaMaarat.get(pituus);
            laivojenLukumaara++;
        }
        
        if ((laivojenKokonaisPintaAla * 3 + laivojenLukumaara * 2) > (leveys - 2) * (korkeus - 2)) {
            throw new IllegalArgumentException("Virheelliset säännöt: Liikaa laivoja.");
        }
    }
    
    private void lisaaLaivojenMitatJaMaarat(TreeMap<Integer, Integer> laivojenMitatJaMaarat) {
        VARASTO.add(laivojenMitatJaMaarat.size());  // Lisättävien arvojen määrä ja pisimpien laivojen pituus
        for (Integer pituus : laivojenMitatJaMaarat.keySet()) {
            VARASTO.add(pituus * laivojenMitatJaMaarat.get(pituus));
        }
    }
    
    /**
     * Palauttaa <tt>Map</tt>-rajapinnan toteuttavan tietorakenteen, jonka 
     * avaimina ovat laivojen mitat ja arvoina kunkin mittaisten laivojen 
     * lukumäärät. Luokka LaivastonSijoitus on riippuvainen tästä metodista.
     *
     * @return <tt>Map</tt> sääntöjen mukaisista laivojen pituuksista ja 
     * määristä.
     * @see LaivastonSijoitus#sijoitaLaiva(int) 
     */
    public TreeMap<Integer, Integer> annaLaivojenMitatJaMaarat() {
        TreeMap<Integer, Integer> laivojenMitatJaMaarat = new TreeMap<>();
        int pituus  = (int) VARASTO.get(3);     // Pisimmän laivan pituus;
        int i       = (int) VARASTO.size() - 1; // Pisimpien laivojen pinta-ala.
        
        for (; i > 3; i--, pituus--) {
            laivojenMitatJaMaarat.put(pituus, (int) VARASTO.get(i) / pituus);
        }
        
        return laivojenMitatJaMaarat;
    }
    
    public int leveys() {
        return (int) VARASTO.get(0);
    }
    
    public int korkeus() {
        return (int) VARASTO.get(1);
    }
        
    public int vuoroja() {
        return (int) VARASTO.get(2);
    }
    
    public int laivapintaAla() {
        int laivapintaAla = 0;
        for (int i = 4; i < VARASTO.size(); i++) {
            laivapintaAla += (int) VARASTO.get(i);
        }
        return laivapintaAla;
    }

    @Override
    public List<Object> annaSisalto() {
        return VARASTO;
    }
    
    @Override
    public void rakennaSisalto(List<Object> ladattuSisalto) {
        for (int i = 0; i < ladattuSisalto.size(); i++) {
            VARASTO.set(i, ladattuSisalto.get(i));
        }
    }
    
}
