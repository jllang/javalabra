Ohjelmoinnin harjoitustyö: Laivanupotus
=======================================

John Lång 28.4.2013

Yleistä

Laivanupotus on peli jossa tarkoituksena on upottaa vastapelaajan koko laivasto.
Pelin aluksi molemmat pelaajat sijoittavat laivastonsa omalle pelialueelleen joko 
käsin tai automaattisesti.
Peli etenee siten että pelaajat ampuvata vuorotellen toistensa ruudukkoihin kunnes 
peli päättyy.
Peli voi päättyä jomman kumman pelaajan voittoon, luovutukseen tai vuorojen loppumiseen kesken. 
(Yksityiskohdat riippuvat käytettävistä säännöistä.)

--------------------------------------------------------------------------------

Pelin käynnistäminen

Peli voidaan käynnistää komentotulkissa seuraavalla komennolla:
    java -jar projekti.jar [\<argumentti 1\>][ \<argumentti 2\>][... \<argumentti n\>]
Huom. Argumentit pitää erottaa välilyönneillä.

Pelin käynnistysargumentit

    Argumentti          Lyh.    Selitys

    ohje                o       Näyttää tämän ohjeen.
    vaihtoehtosaannot   vs      Peli käynnistetään toisilla säännöillä. *
    automaattisijoitus  a       Pelaajan laivat asetetaan automaattisesti.
    debuggaus           d       (Debuggaus) Tulostetaanko debuggausviestit.
    aikaleimat          da      (Debuggaus) Käytetäänkö aikaleimoja
    cli                 c       Tekstikäyttöliittymä
    varit               v       Värilliset tekstit käyttöön terminaalissa. 
                                Vaikuttaa vain tekstikäyttöliittymässä.

* = Oletuksena käytössä olevissa säännöissä ruudukon mitat ovat 10x10 ruutua,
    vuorojen määrä on rajoittamaton ja osumasta ei saa lisävuoroa. Laivojen mitat 
    ovat seuraavat: 1 kpl 4 ruudun, 2 kpl 3 ruudun, 3 kpl 2 ruudun ja 4 kpl
    1 ruudun laivoja.

    Vaihtoehtoisissa säännöissä ruudukon mitat ovat 10x10 ruutua, vuorojen määrä 
    on rajoittamaton sekä osumasta saa lisävuoron. Laivojen mitat ovat:
    1 kpl 5 ruudun, 1 kpl 4 ruudun, 2 kpl 3 ruudun, 1 kpl 2 ruudun ja 1 kpl
    1 ruudun laivoja.

--------------------------------------------------------------------------------

Tarkemmat käyttöohjeet peliin löytyvät kansiossa "dokumentointi" olevista tiedostoista 
cli.txt (tekstikäyttöliittymän käyttöohjeet) ja gui.txt (graafisen käyttöliittymän 
käyttöohjeet).
