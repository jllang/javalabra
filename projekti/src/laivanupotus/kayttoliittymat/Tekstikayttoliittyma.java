
package laivanupotus.kayttoliittymat;

import java.util.Scanner;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kayttoliittymat.komponentit.tekstikayttoliittyma.Komentotulkki;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.tietorakenteet.enumit.Ruutu;

/**
 * Tämän luokan tarkoituksena on toteuttaa käyttöjärjestelmän komentotulkista 
 * käsin operoitava käyttöliittymä.
 *
 * @author John Lång 
 */
public final class Tekstikayttoliittyma implements Kayttoliittyma {
    
    private static final int    KUVAN_LEVEYS = 80, KUVAN_KORKEUS = 21;
    private static final char[] AAKKOSET = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T'
    };
    private static final char[] NUMEROT = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };
    
    // Värit ei toimi Windowsissa.
    private static final String VARI_TEKSTI     = "\u001B[0m";
    private static final String VARI_LAIVA      = "\u001B[37m";
    private static final String VARI_MERI       = "\u001B[34m";
    private static final String VARI_TUNTEMATON = "\u001B[37m";
    private static final String VARI_OSUMA      = "\u001B[31m"; // Osuma laivaan
    
    private final Scanner       LUKIJA;
    private final Komentotulkki KOMENTOTULKKI;
    private final boolean       VARIT_ON_KAYTOSSA;
    
    private char[][]            valimuisti;
    private static Pelikierros  pelikierros;
    private static Ruutu[][]    ruudukko1, ruudukko2;
    private static int          ruudukonLeveys, ruudukonKorkeus;
    private Pelaaja             katsoja;
    
    /**
     * Luokan Tekstikayttoliittyma konstruktori.
     * 
     * @param varitOnKaytossa Käytetäänkö viestien tulostamisessa System.out 
     * -virtaan "ANSI escape code"-merkkijonoja. Kyseinen ominaisuus ei toimi 
     * Windows-käyttöjärjestelmissä ja oletuksena luokan Laivanupotus metodi 
     * main syöttää Tekstikayttoliittyman konstruktorille arvon <tt>false</tt>.
     */
    public Tekstikayttoliittyma(boolean varitOnKaytossa) {
        this.LUKIJA             = new Scanner(System.in);
        this.KOMENTOTULKKI      = new Komentotulkki();
        this.VARIT_ON_KAYTOSSA  = varitOnKaytossa;
        this.valimuisti        = new char[KUVAN_KORKEUS][KUVAN_LEVEYS];
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
    public Pelaaja annaKatsoja() {
        return this.katsoja;
    }

    @Override
    public void alusta() {
        ruudukko1 = katsoja.annaPelialue().annaRuudukko(katsoja);
        ruudukko2 = pelikierros.annaVastapelaaja(katsoja).annaPelialue().annaRuudukko(katsoja);
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
        System.out.println();
        tulkitseRuudukko(ruudukko1, 0);
        tulkitseRuudukko(ruudukko2, ruudukonLeveys + 2);
        String mj = rakennaKuva();
        tulostaMerkkijono(mj);
    }
    
    @Override
    public void tulostaOhje() {
        // Teen tämän myöhemmin siististi jos ehdin:
        tulostaMerkkijono("\nYleistä\n\n"
                + "Laivanupotus on peli jossa tarkoituksena on upottaa "
                + "vastapelaajan koko laivasto.\n"
                + "Pelin aluksi molemmat pelaajat sijoittavat laivastonsa "
                + "omalle pelialueelleen jo-\n"
                + "ko käsin tai automaattisesti.\n"
                + "Peli etenee siten että pelaajat ampuvata vuorotellen "
                + "toistensa ruudukkoihin kun-\n"
                + "nes peli päättyy.\n"
                + "Peli voi päättyä jomman kumman pelaajan voittoon, "
                + "luovutukseen tai vuorojen lop-\n"
                + "pumiseen kesken. (Yksityiskohdat riippuvat käytettävistä "
                + "säännöistä.)\n\n"
                + "----------------------------------------"
                + "----------------------------------------\n\n"
                + "Legenda\n\n"
                + "    Symboli  Merkitys\n\n"
                + "    ?        Vastustajan ruutu (ei ammuttu vielä)\n"
                + "    ~        Merta (ei ammuttu vielä)\n"
                + "    O        Merta (ammuttu, ei osumaa)\n"
                + "    L        Laiva (oma ruudukko, ei osumaa)\n"
                + "    X        Osuma\n\n"
                + "----------------------------------------"
                + "----------------------------------------\n\n"
                + "Laivojen sijoittaminen ruudukkoon\n\n"
                + "Laivat sijoitetaan ennen pelikierroksen alkua "
                + "pelialueille alkaen lyhyimmistä\n"
                + "laivoista pisimpiin. Laivojen sijoittaminen käsin "
                + "ruudukkoon tapahtuu komennolla\n"
                + "    aseta <x> <y> 0|1\n"
                + "missä <x> on sijoitettavan laivan ensimmäisen ruudun "
                + "sijainti vaakasuunnassa vä-\n"
                + "liltä [A, " + AAKKOSET[pelikierros.annaSaannot().leveys() -1]
                + "] ja <y> on sijoitettavan laivan ensimmäisen ruudun sijainti"
                + " pysty-\n"
                + "suunnassa väliltä [1, " + pelikierros.annaSaannot().korkeus()
                + "]. "
                + "Komennon viimeisenä parametrina annetaaan sijoitetta-\n"
                + "van laivan orientaatio. Orientaatio kuvaa sijoitettavan "
                + "laivan suuntaa siten,\n"
                + "että arvo 0 merkitsee laivan sijoittamista horisontaalisesti"
                + " (ts. annetusta ruu-\n"
                + "dusta oikealle päin) ja arvo 1 vertikaalisesti (ts. "
                + "annetusta ruudusta alaspäin)\n. Esimerkki:\n"
                + "    aseta a 3 0\n\n"
                + "----------------------------------------"
                + "----------------------------------------\n\n"
                + "Pelikierroksen aikana käytettävissä olevat komennot\n\n"
                + "    Komento      Parametrit      Selitys\n\n"
                + "    ohje         (ei ole)        Tulostaa tämän ohjeen.\n"
                + "    lopeta       (ei ole)        Lopettaa pelin.\n"
                + "    luovuta      (ei ole)        Lopettaa pelin (vastapuolen"
                + " voittoon.)\n"
                + "    ammu         <x> <y>         Ampuu vastustajan "
                + "pelialueelle annettuihin\n"
                + "                                 koordinaatteihin. <x> ja "
                + "<y> kuten laivojen si-\n"
                + "                                 joituskomennossa.\n"
                + "    laivoja      (ei ole)        Tulostaa tiedon pelaajilla "
                + "olevevien upottamat-\n"
                + "                                 tomien laivojen määrästä.\n"
                + "    vuoro        (ei ole)        Tulostaa tiedon käynnissä "
                + "olevan pelivuoron\n"
                + "                                 järjestysnumerosta. "
                + "Tulostaa lisäksi tiedon\n"
                + "                                 jäljellä olevien vuorojen "
                + "määrästä mikäli se on\n"
                + "                                 rajoitettu.\n\n"
                + "----------------------------------------"
                + "----------------------------------------\n\n");
    };
    
    @Override
    public void tulostaViesti(String viesti) {
        tulostaMerkkijono(viesti);
    }
    
    @Override
    public Komento pyydaKomento() throws Exception {
        Komento luettu;
        tulostaMerkkijono("> ");
        String syote = LUKIJA.nextLine();
        luettu = KOMENTOTULKKI.tulkitse(syote.toUpperCase());
        
        return luettu;
    }
    
    private void alustaKoordinaatit() {
        //Vähän epäkaunis ratkaisu, mutta ajaapahan asiansa:
        for (int i = 0; i < ruudukonLeveys; i++) {
            valimuisti[0][i * 2 + 3] = AAKKOSET[i];
            valimuisti[1][i * 2 + 3] = '-';
            valimuisti[1][i * 2 + 4] = '-';
            valimuisti[0][i * 2 + 7 + ruudukonLeveys * 2] = AAKKOSET[i];
            valimuisti[1][i * 2 + 7 + ruudukonLeveys * 2] = '-';
            valimuisti[1][i * 2 + 7 + ruudukonLeveys * 2 + 1] = '-';
            valimuisti[ruudukonKorkeus + 2][i * 2 + 3] = '-';
            valimuisti[ruudukonKorkeus + 2][i * 2 + 4] = '-';
            valimuisti[ruudukonKorkeus + 2][i * 2 + 7 + ruudukonLeveys * 2] = '-';
            valimuisti[ruudukonKorkeus + 2][i * 2 + 7 + ruudukonLeveys * 2 + 1] = '-';
        }
        
        for (int i = 0; i < ruudukonKorkeus; i++) {
            if (i >= 9) {
                valimuisti[i + 2][0] = NUMEROT[1];
                valimuisti[i + 2][ruudukonLeveys * 2 + 4] = NUMEROT[1];
            }
            valimuisti[i + 2][1] = NUMEROT[(i + 1) % 10];
            valimuisti[i + 2][2] = '|';
            valimuisti[i + 2][ruudukonLeveys * 2 + 2] = '|';
            valimuisti[i + 2][ruudukonLeveys * 2 + 5] = NUMEROT[(i + 1) % 10];
            valimuisti[i + 2][ruudukonLeveys * 2 + 6] = '|';
            valimuisti[i + 2][ruudukonLeveys * 4 + 6] = '|';
        }
    }
    
    private void alustaKuva() {
        for (int i = 0; i < KUVAN_KORKEUS; i++) {
            for (int j = 0; j < KUVAN_LEVEYS; j++) {
                valimuisti[i][j] = ' ';
            }
        }
    }
    
    private void tulkitseRuudukko(Ruutu[][] ruudukko, int siirto) {
        for (int i = 0; i < ruudukonKorkeus; i++) {
            for (int j = 0; j < ruudukonLeveys; j++) {
                valimuisti[i + 2][j * 2 + siirto * 2 + 3] = tulkitseRuutu(ruudukko[i][j]);
            }
        }
    }
    
    private char tulkitseRuutu(Ruutu ruutu) {
        switch (ruutu) {
            default:
                return '?';
            case TYHJA_EI_OSUMAA:
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
        System.out.print(merkkijono);
    }

    private String rakennaKuva() {
        StringBuilder mjr = new StringBuilder();
        char nykyinenMerkki;
        for (int i = 0; i < KUVAN_KORKEUS; i++) {
            for (int j = 0; j < KUVAN_LEVEYS; j++) {
                nykyinenMerkki = valimuisti[i][j];
                if (VARIT_ON_KAYTOSSA) {
                    mjr.append(varita(nykyinenMerkki));
                }
                mjr.append(nykyinenMerkki);
            }
            mjr.append('\n');
        }
        return mjr.toString();
    }
    
    private String varita(char nykyinenMerkki) {
        String varikoodi;
        switch (nykyinenMerkki) {
            default:
                varikoodi = VARI_TEKSTI;
                break;
            case '?':
                varikoodi = VARI_TUNTEMATON;
                break;
            case 'L':
                varikoodi = VARI_LAIVA;
                break;
            case 'O':
            case '~':
                varikoodi = VARI_MERI;
                break;
            case 'X':
                varikoodi = VARI_OSUMA;
        }
        return varikoodi;
    }

    @Override
    public void tulostaDebuggausViesti(String viesti) {
        System.err.println(viesti);
    }
    
    @Override
    public void run() {
    }
    
}
