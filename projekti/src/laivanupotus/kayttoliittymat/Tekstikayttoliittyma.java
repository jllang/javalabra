/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kayttoliittymat;

import java.util.Scanner;
import laivanupotus.kontrolli.Ihmispelaaja;
import laivanupotus.kontrolli.Komentotulkki;
import laivanupotus.kontrolli.Pelaaja;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Komentotyyppi;
import laivanupotus.tietorakenteet.Ruutu;

/**
 *
 * @author John Lång
 */
public class Tekstikayttoliittyma implements Kayttoliittyma {
    
    private static final int    KUVAN_LEVEYS = 80, KUVAN_KORKEUS = 25;  // VGA
    private static final char[] AAKKOSET = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T'
    };
    private static final char[] NUMEROT = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };
    private final Scanner       LUKIJA;
    private final Komentotulkki KOMENTOTULKKI;
    
    private char[][]            kuvapuskuri;
    private static Pelikierros  pelikierros;
    private static Ruutu[][]    ruudukko1, ruudukko2;
    private static int          ruudukonLeveys, ruudukonKorkeus;
    private Pelaaja             katsoja;
    
    public Tekstikayttoliittyma() {
        this.LUKIJA         = new Scanner(System.in);
        this.KOMENTOTULKKI  = new Komentotulkki();
        this.kuvapuskuri    = new char[KUVAN_KORKEUS][KUVAN_LEVEYS];
        alustaKuva();
        alustaKoordinaatit();
    }
    
    @Override
    public void asetaPelikierros(Pelikierros pelikierros) {
        Tekstikayttoliittyma.pelikierros = pelikierros;
    }
    
    @Override
    public void asetaKatsoja(Pelaaja katsoja) {
        this.katsoja = katsoja;
    }

    @Override
    public void alusta() {
        ruudukko1 = katsoja.annaPelialue().haeRuudukko(katsoja);
        ruudukko2 = pelikierros.annaVastapelaaja(katsoja).annaPelialue().haeRuudukko(katsoja);
        ruudukonLeveys = pelikierros.annaSaannot().leveys();
        ruudukonKorkeus = pelikierros.annaSaannot().korkeus();
        alustaKuva();
        alustaKoordinaatit();
    }
    
    @Override
    public void paivita(Pelialue pelialue, int x, int y) {
        Ruutu ruutu = pelialue.haeRuutu(katsoja, x, y);
        if (pelialue == pelikierros.annaPelialue1()) {
            ruudukko1[y][x] = ruutu;
        } else {
            ruudukko2[y][x] = ruutu;
        }
    }
    
    @Override
    public void tulostaPelitilanne() {
        tulkitseRuudukko(ruudukko1, 0);
        tulkitseRuudukko(ruudukko2, ruudukonLeveys + 2);
        String mj = rakennaKuva();
        tulostaMerkkijono(mj);
    }
    
    @Override
    public void tulostaViesti(String viesti) {
        System.out.println(viesti);
    }
    
    @Override
    public Komento pyydaKomento(Pelaaja pelaaja) throws Exception {
        Komento luettu = null;
        System.out.print("> ");
        String syote = LUKIJA.nextLine();
        luettu = KOMENTOTULKKI.tulkitse(syote.toUpperCase());
        
        return luettu;
    }
    
    private void alustaKoordinaatit() {
        //Vähän epäkaunis ratkaisu, mutta ajaapahan asiansa:
        for (int i = 0; i < ruudukonLeveys; i++) {
            kuvapuskuri[0][i * 2 + 3] = AAKKOSET[i];
            kuvapuskuri[1][i * 2 + 3] = '-';
            kuvapuskuri[1][i * 2 + 4] = '-';
            kuvapuskuri[0][i * 2 + 7 + ruudukonLeveys * 2] = AAKKOSET[i];
            kuvapuskuri[1][i * 2 + 7 + ruudukonLeveys * 2] = '-';
            kuvapuskuri[1][i * 2 + 7 + ruudukonLeveys * 2 + 1] = '-';
            kuvapuskuri[ruudukonKorkeus + 2][i * 2 + 3] = '-';
            kuvapuskuri[ruudukonKorkeus + 2][i * 2 + 4] = '-';
            kuvapuskuri[ruudukonKorkeus + 2][i * 2 + 7 + ruudukonLeveys * 2] = '-';
            kuvapuskuri[ruudukonKorkeus + 2][i * 2 + 7 + ruudukonLeveys * 2 + 1] = '-';
        }
        
        for (int i = 0; i < ruudukonKorkeus; i++) {
            if (i >= 9) {
                kuvapuskuri[i + 2][0] = NUMEROT[1];
                kuvapuskuri[i + 2][ruudukonLeveys * 2 + 4] = NUMEROT[1];
            }
            kuvapuskuri[i + 2][1] = NUMEROT[(i + 1) % 10];
            kuvapuskuri[i + 2][2] = '|';
            kuvapuskuri[i + 2][ruudukonLeveys * 2 + 2] = '|';
            kuvapuskuri[i + 2][ruudukonLeveys * 2 + 5] = NUMEROT[(i + 1) % 10];
            kuvapuskuri[i + 2][ruudukonLeveys * 2 + 6] = '|';
            kuvapuskuri[i + 2][ruudukonLeveys * 4 + 6] = '|';
        }
    }
    
    private void alustaKuva() {
        for (int i = 0; i < KUVAN_KORKEUS; i++) {
            for (int j = 0; j < KUVAN_LEVEYS; j++) {
                kuvapuskuri[i][j] = ' ';
            }
        }
    }
    
    private void tulkitseRuudukko(Ruutu[][] ruudukko, int siirto) {
        for (int i = 0; i < ruudukonKorkeus; i++) {
            for (int j = 0; j < ruudukonLeveys; j++) {
                kuvapuskuri[i + 2][j * 2 + siirto * 2 + 3] = tulkitseRuutu(ruudukko[i][j]);
            }
        }
    }
    
    private char tulkitseRuutu(Ruutu ruutu) {
        switch (ruutu) {
            default:
                return '~';
            case LAIVA_EI_OSUMAA:
                return 'L';
            case TYHJA_OSUMA:
                return 'O';
            case LAIVA_OSUMA:
                return 'X';
                //debuggausta varten:
            case TARKASTETTU:
                return 'T';
        }
    }
    
    private void tulostaMerkkijono(String merkkijono) {
        System.out.println(merkkijono);
    }

    private String rakennaKuva() {
        StringBuilder mjr = new StringBuilder();
        for (int i = 0; i < KUVAN_KORKEUS; i++) {
            for (int j = 0; j < KUVAN_LEVEYS; j++) {
                mjr.append(kuvapuskuri[i][j]);
            }
            mjr.append('\n');
        }
        return mjr.toString();
    }
    
}
