
package laivanupotus.kayttoliittymat.komponentit.graafinenKayttoliittyma;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import laivanupotus.kayttoliittymat.GraafinenKayttoliittyma;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.enumit.Komentotyyppi;

/**
 *
 * @author John Lång
 */
public class Valikonkuuntelija implements ActionListener, ItemListener {
    
    private final GraafinenKayttoliittyma   KAYTTOLIITTYMA;
    private final JMenuBar                  VALIKKOPALKKI;
    private Pelikierros                     pelikierros;
    
    public Valikonkuuntelija(GraafinenKayttoliittyma kayttoliittyma, JMenuBar valikkopalkki) {
        this.KAYTTOLIITTYMA = kayttoliittyma;
        this.VALIKKOPALKKI = valikkopalkki;
        for (int i = 0; i < valikkopalkki.getMenuCount(); i++) {
            JMenu valikko = valikkopalkki.getMenu(i);
            for (int j = 0; j < valikko.getItemCount(); j++) {
                JMenuItem valinta = valikko.getItem(j);
                valinta.addActionListener(this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Lopeta":
                KAYTTOLIITTYMA.asetaKomento(new Komento(Komentotyyppi.LOPETA));
                break;
            case "Ohje":
                break;
            default:
                throw new AssertionError();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
