
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
    private final Pelaaja                   PELAAJA;
    private int siirtoX, siirtoY, sarakkeita, riveja, ruudunLeveys, ruudunKorkeus;
    
    public Hiirenkuuntelija(GraafinenKayttoliittyma kayttoliittyma,
            Ruutupaneeli ruutupaneeli, Pelaaja pelaaja) {
        this.KAYTTOLIITTYMA     = kayttoliittyma;
        this.PELAAJA            = pelaaja;
        this.sarakkeita         = Ruutupaneeli.sarakkeita;
        this.riveja             = Ruutupaneeli.riveja;
        this.ruudunLeveys       = Ruutupaneeli.ruudunLeveys;
        this.ruudunKorkeus      = Ruutupaneeli.ruudunKorkeus;
        this.siirtoX            = ruutupaneeli.siirtoX;
        this.siirtoY            = ruutupaneeli.siirtoY;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x0, y0, x1, y1;
        x0 = e.getX() - siirtoX;
        y0 = e.getY() - siirtoY;
        if (x0 <= sarakkeita * ruudunLeveys
                && x0 >= 0
                &&y0 <= riveja * ruudunKorkeus
                && y0 >= 0) {
            x1 = x0 / ruudunLeveys;
            y1 = y0 / ruudunKorkeus;
            if (KAYTTOLIITTYMA.annaKatsoja() == PELAAJA) {
                KAYTTOLIITTYMA.asetaKomento(new Komento(Komentotyyppi.SIJOITA_LAIVA, x1, y1));
            } else {
                KAYTTOLIITTYMA.asetaKomento(new Komento(Komentotyyppi.AMMU_JA_PAIVITYTA, x1, y1));
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
