/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kayttoliittymat;

import laivanupotus.kontrolli.Pelaaja;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Ruutu;

/**
 *
 * @author John Lång
 */
public class Tekstikayttoliittyma implements Kayttoliittyma {
    
    private static final int    KUVAN_LEVEYS = 80, KUVAN_KORKEUS = 25;  // VGA
    private char[][]            kuvapuskuri;
    private static Pelikierros  pelikierros;
    private static Ruutu[][]    ruudukko1, ruudukko2;
    private static int          ruudukonLeveys, ruudukonKorkeus;
    private Pelaaja             katsoja;
    
    public Tekstikayttoliittyma() {
        this.kuvapuskuri = new char[KUVAN_KORKEUS][KUVAN_LEVEYS];
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
    }
    
    @Override
    public void paivita(Pelialue pelialue, int x, int y) {
        pelialue.haeRuutu(katsoja, x, y);
    }
    
    @Override
    public void tulosta() {
        tulkitseRuudukko(ruudukko1, 0);
        tulkitseRuudukko(ruudukko2, ruudukonLeveys + 1);
        String mj = rakennaKuva();
        tulostaMerkkijono(mj);
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
                kuvapuskuri[i][j + siirto] = tulkitseRuutu(ruudukko[i][j]);
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
