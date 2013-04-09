
package laivanupotus.rajapinnat;

import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.tietorakenteet.Komento;

/**
 * Tämän rajapinnan on tarkoitus määrittää ohjelman käyttöliittymältä vaaditut 
 * ominaisuudet.
 *
 * @author John Lång
 */
public interface Kayttoliittyma extends Runnable {
    
    /**
     * Metodin tarkoituksena on välittää käyttöliittymälle tieto nykyisestä 
     * pelikierroksesta.
     *
     * @param pelikierros Nykyinen pelikierros.
     * @see Pelikierros
     */
    void asetaPelikierros(Pelikierros pelikierros);
    
    /**
     * Metodin tarkoituksena on välittää käyttöliittymälle tieto siitä 
     * pelaajasta, jonka pelialue halutaan näyttää. Vastapelaajan pelialueen 
     * ruudut ovat oletusarvoisesti tilassa Ruutu.TUNTEMATON.
     *
     * @param katsoja Pelaaja, jonka kaikki laivat näytetään. Ts. pelaaja, jonka
     * näkökulmasta pelitilannetta tarkastellaan.
     * @see Pelaaja
     * @see Pelialue
     * @see Ruutu
     */
    void asetaKatsoja(Pelaaja katsoja);
    
    /**
     * Suorittaa pelitilanteen tulostamista edeltävät valmistelutoimenpiteet. 
     * Metodi on tarkoitettu suoritettavaksi vain kerran jokaisen pelikierroksen 
     * alussa.
     * 
     * @see Kayttoliittyma#paivita(laivanupotus.tietorakenteet.Pelialue, int, int) 
     */
    void alusta();
    
    /**
     * Päivittää annetun pelialueen ruudun tilan käyttöliittymän sisäiseen 
     * välimuistiin myöhempiä tulostusoperaatioita varten. Metodia tulee kutsua 
     * aina ruudun tilan muuttuessa jotta käyttöliittymä pysyisi ajan tasalla.
     *
     * @param pelialue  Pelialue, jonka ruudun tila on muuttunut.
     * @param x         Ruudun x-koordinaatti.
     * @param y         Ruudun y-koordinaatti.
     */
    void paivita(Pelialue pelialue, int x, int y);
    
    /**
     * Tulostaa pelaajien ruudukot. Käyttöliittymän tulee olla alustettu ja 
     * päivitetty ennen tulostamista, jotta ajankohtainen pelitilanne näkyisi 
     * oikein käyttäjälle.
     * @see Kayttoliittyma#alusta() 
     * @see Kayttoliittyma#paivita(laivanupotus.tietorakenteet.Pelialue, int, int) 
     */
    void tulostaPelitilanne();
    
    /**
     * Tulostaa (tai esittää muulla tavoin) annetun viestin käyttäjälle.
     *
     * @param viesti Tulostettava viesti.
     */
    void tulostaViesti(String viesti);
    
    /**
     * Tulostaa (tai esittää muulla tavoin) annetun debuggaukseen tarkoitetun 
     * viestin käytäjälle.
     *
     * @param viesti Tulostettava debuggausviesti.
     */
    void tulostaDebuggausViesti(String viesti);
    
    /**
     * Palauttaa (ihmis-)pelaajan antaman komennon.
     *
     * @return              Uusi luokan <tt>Komento</tt> instanssi.
     * @throws Exception    Mahdollinen käyttäjän antaman komennon 
     * tulkitsemiseen liittyvä poikkeus.
     * @see Komento
     */
    Komento pyydaKomento() throws Exception;
    
}
