
package laivanupotus.tietorakenteet;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import laivanupotus.rajapinnat.Tallennettava;

/**
 * Tämän luokan instanssin tehtävänä on sisältää tieto pelikierroksella 
 * käytettävistä pelin säännöistä sekä varmistaa mahdollisten käyttäjän antamien 
 * sääntöjen tarkoituksenmukaisuus. Sääntöjen mahdollista tiedostojärjestelmään 
 * tallentamista varten kaikki tieto tallennetaan kokonaislukulistaan. Laivojen 
 * mittojen ja määrien käsittelyn helpottamiseksi näistä tiedoista pidetään myös 
 * redundantit kopiot <tt>TreeMap</tt> tietorakenteessa.
 *
 * @author John Lång
 */
public final class Saannot implements Tallennettava {
    
    // Näiden kahden tietorakenteet päällekkäisten arvojen yhtäläisyys pitäisi
    // varmistaa testillä:
    private final List<Object>              VARASTO;
    private final TreeMap<Integer, Integer> LAIVOJEN_MITAT_JA_MAARAT;
    
    private int                             laivojenMaara; // lisää redundanssia
    
    /**
     * Luo annettujen parametrien mukaiset säännöt mikäli se on 
     * tarkoituksenmukaista. Sääntöolion sisällä tieto säilötään listaan, 
     * jolloin tieto on helppo tarvittaessa tallentaa tiedostojärjestelmään 
     * Tallentaja-rajapinnan toteuttavan olion avulla.
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
            TreeMap<Integer, Integer> laivojenMitatJaMaarat,
            boolean osumastaSaaLisavuoron) {
        tarkastaArvot(leveys, korkeus, vuoroja, laivojenMitatJaMaarat);
        this.VARASTO = new ArrayList<>();
        this.LAIVOJEN_MITAT_JA_MAARAT = laivojenMitatJaMaarat;
        VARASTO.add(leveys);
        VARASTO.add(korkeus);
        VARASTO.add(vuoroja);
        // Laivojen pituuksien puuttumista ei tulla tarkistamaan koska käyttäjä 
        // ei pääse suoraan lisäämään avain-arvopareja TreeMappiin sitten 
        // tulevaisuudessa kun kerkeän toteuttamaan pelaajan omien sääntöjen 
        // luomisen peliin.
        sarjallistaLaivat(laivojenMitatJaMaarat);
        if (osumastaSaaLisavuoron) {
            VARASTO.add(1);
        } else {
            VARASTO.add(0);
        }
    }
    
    /**
     *  Luo oletusarvoiset säännöt.
     */
    public Saannot() {
        this.VARASTO                    = new ArrayList<>();
        this.LAIVOJEN_MITAT_JA_MAARAT   = new TreeMap<>();
        VARASTO.add(10);    // Pelialueen leveys
        VARASTO.add(10);    // Pelialueen korkeus
        VARASTO.add(0);     // Vuorojen määrä (0 = ei rajoitusta)
        VARASTO.add(4);     // Pisimmän laivan pituus. Taitaa olla redundantti.
        VARASTO.add(4);     // 4 * 1 ruutu
        VARASTO.add(6);     // 3 * 2 ruutua
        VARASTO.add(6);     // 2 * 3 ruutua
        VARASTO.add(4);     // 1 * 4 ruutua
        VARASTO.add(0);     // Osumasta ei saa lisävuoroa.
        laskeLaivojenMitatJaMaarat();
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
    
    private void sarjallistaLaivat(TreeMap<Integer, Integer> laivojenMitatJaMaarat) {
        VARASTO.add(laivojenMitatJaMaarat.size());  // Lisättävien arvojen määrä ja pisimpien laivojen pituus
        int maara;
        for (Integer pituus : laivojenMitatJaMaarat.keySet()) {
            maara = laivojenMitatJaMaarat.get(pituus);
            VARASTO.add(pituus * maara);
            laivojenMaara += maara;
        }
    }
    
    private void laskeLaivojenMitatJaMaarat() {
        int pituus  = (int) VARASTO.get(3);     // Pisimmän laivan pituus;
        int i       = (int) VARASTO.size() - 2; // Pisimpien laivojen pinta-ala.
        int maara;
        
        for (; i > 3; i--, pituus--) {
            maara = (int) VARASTO.get(i) / pituus;
            LAIVOJEN_MITAT_JA_MAARAT.put(pituus, maara);
            laivojenMaara += maara;
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
        return LAIVOJEN_MITAT_JA_MAARAT;
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
    
    public boolean osumastaSaaLisavuoron() {
        return VARASTO.get(VARASTO.size() - 1) == 1;
    }
    
    public int laivapintaAla() {
        int laivapintaAla = 0;
        for (int i = 4; i < VARASTO.size() - 1; i++) {
            laivapintaAla += (int) VARASTO.get(i);
        }
        return laivapintaAla;
    }
    
    public int laivojenMaara() {
        return laivojenMaara;
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
