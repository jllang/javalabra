
package laivanupotus.kayttoliittymat.komponentit.graafinenKayttoliittyma;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import laivanupotus.kontrolli.Pelikierros;

/**
 *
 * @author John Lång
 */
public class Tapahtumakuuntelija extends AbstractAction {
    
    private Pelikierros pelikierros;
    
    public Tapahtumakuuntelija() {
    }
    
    public void asetaPelikierros(Pelikierros pelikierros) {
        this.pelikierros = pelikierros;
    }
    
    public Pelikierros annaPelikierros() {
        return this.pelikierros;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
    }

}
