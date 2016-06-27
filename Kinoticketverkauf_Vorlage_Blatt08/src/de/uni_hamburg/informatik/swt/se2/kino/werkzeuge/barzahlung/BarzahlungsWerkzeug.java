package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.barzahlung;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
 * Das Barzahlungswerkzeug. Mit diesem Werkzeug kann der Benutzer eine Barzahlung durchführen oder abbrechen.
 * 
 * @author Lina Heinzke, SE2 Übungsgruppe "No Pascha"
 * @version 25.06.2016
 *
 */
public class BarzahlungsWerkzeug
{
    // Die UI dieses Werkzeugs. 
    private BarzahlungsWerkzeugUI _ui;

    // Der zu zahlende Betrag
    private int _preis;

    // Wahrheitswert, ob mind. der geforderte Betrag gezahlt (ins JTextField eingegeben) wurde.
    private boolean _wurdeBezahlt;

    /**
     * Führt die Barzahlung durch.
     * 
     * @param betrag Der zu zahlende Betrag.
     * @return  true, wenn auf "OK" geklickt wurde
     *          false, wenn auf "Abbrechen geklickt wurde"
     * 
     * @require betrag >= 0
     */
    public boolean zahlen(int betrag)
    {
        assert betrag >= 0 : "Vorbedingung verletzt: betrag >= 0";

        _preis = betrag;

        _ui = new BarzahlungsWerkzeugUI(betrag);

        // OK soll am Anfang nicht angeklickt werden können -> false 
        _ui.getOKButton()
            .setEnabled(false);

        _ui.setRestbetrag(-betrag);

        registriereUIAktionen();

        _ui.zeigeFenster();

        return _wurdeBezahlt;
    }

    /**
     * Fügt der UI die Funktionalität hinzu mit entsprechenden Listenern.
     */
    private void registriereUIAktionen()
    {
        _ui.getCancelButton()
            .addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    reagiereAufBeendenButton();
                }
            });

        _ui.getOKButton()
            .addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    reagiereAufOKButton();
                }
            });

        //      _ui.getEingabefeld()
        //      .addActionListener(new ActionListener()
        //      {
        //          @Override
        //          public void actionPerformed(ActionEvent e)
        //          {
        //              reagiereAufEingabe();
        //          }
        //      });

        _ui.getEingabefeld()
            .getDocument()
            .addDocumentListener(new DocumentListener()
            {
                @Override
                public void removeUpdate(DocumentEvent e)
                {
                    reagiereAufEingabe();
                }

                @Override
                public void insertUpdate(DocumentEvent e)
                {
                    reagiereAufEingabe();
                }

                @Override
                public void changedUpdate(DocumentEvent e)
                {
                    reagiereAufEingabe();
                }
            });
    }

    /**
     * Schließt das Fenster.
     */
    private void reagiereAufBeendenButton()
    {
        _wurdeBezahlt = false;
        _ui.schliesseFenster();
    }

    /**
     * Vermerkt, dass bezahlt wurde und schließt das Fenster.
     */
    private void reagiereAufOKButton()
    {
        _wurdeBezahlt = true;
        _ui.schliesseFenster();
    }

    /**
     * Aktiviert den OKButton, wenn genug gezahlt wurde und setzt den Restbetrag.
     */
    private void reagiereAufEingabe()
    {
        int gezahlt = _ui.getGezahlterBetrag();
        _ui.getOKButton()
            .setEnabled(gezahlt >= _preis);
        _ui.setRestbetrag(gezahlt - _preis);
    }
}