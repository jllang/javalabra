
package laivanupotus.kayttoliittymat.komponentit.graafinenKayttoliittyma;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kayttoliittymat.GraafinenKayttoliittyma;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.enumit.Komentotyyppi;

/**
 *
 * @author John Lång
 */
public class Hiirenkuuntelija implements MouseListener {
    
    private final GraafinenKayttoliittyma   KAYTTOLIITTYMA;
    private final Pelaaja                   RUUDUKON_PELAAJA;
    private int sarakkeita, riveja, ruudunLeveys, ruudunKorkeus;
    
    public Hiirenkuuntelija(GraafinenKayttoliittyma kayttoliittyma,
            Ruutupaneeli ruutupaneeli, Pelaaja pelaaja) {
        this.KAYTTOLIITTYMA     = kayttoliittyma;
        this.RUUDUKON_PELAAJA   = pelaaja;
        this.sarakkeita         = Ruutupaneeli.sarakkeita;
        this.riveja             = Ruutupaneeli.riveja;
        this.ruudunLeveys       = Ruutupaneeli.ruudunLeveys;
        this.ruudunKorkeus      = Ruutupaneeli.ruudunKorkeus;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x0, y0, x1, y1;
        x0 = e.getX() - ruudunLeveys;   // Ruutupaneeli siirtää ruudukkoa yhdel-
        y0 = e.getY() - ruudunKorkeus;  // lä ruudulla koordinaatteja varten.
        if (x0 <= sarakkeita * ruudunLeveys
                && x0 >= 0
                &&y0 <= riveja * ruudunKorkeus
                && y0 >= 0) {
            x1 = x0 / ruudunLeveys;
            y1 = y0 / ruudunKorkeus;
//            System.err.println("Klikattiin ruutuun (" + x1 + "," + y1 + ").");
            if (KAYTTOLIITTYMA.annaKatsoja() == RUUDUKON_PELAAJA) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    KAYTTOLIITTYMA.asetaKomento(new Komento(Komentotyyppi.SIJOITA_LAIVA, x1, y1, 0));
                } else if (e.getButton() == MouseEvent.BUTTON2 || e.getButton() == MouseEvent.BUTTON3) {
                    KAYTTOLIITTYMA.asetaKomento(new Komento(Komentotyyppi.SIJOITA_LAIVA, x1, y1, 1));
                }
            } else {
                KAYTTOLIITTYMA.asetaKomento(new Komento(Komentotyyppi.AMMU, x1, y1));
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
