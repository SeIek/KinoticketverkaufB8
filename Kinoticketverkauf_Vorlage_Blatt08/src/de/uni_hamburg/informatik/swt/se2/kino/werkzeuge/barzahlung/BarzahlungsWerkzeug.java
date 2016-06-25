package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.barzahlung;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

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

        _ui = new BarzahlungsWerkzeugUI(new JFrame(), betrag);

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
        
        _ui.getEingabefeld()
        .addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
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
        int gezahlt = getBetrag(_ui.getEingabefeld());
        _ui.getOKButton()
            .setEnabled(gezahlt >= _preis);
        _ui.setRestbetrag(gezahlt - _preis);
    }

    //TODO sinnvoller in der UI Klasse? 
    /**
     * Gibt dir Zahl zurück, der in einem JTextField steht (wenn keine Zahl 0).
     * 
     * @param textField Das JTextField, aus dem die Zahl berechnet werden soll.
     * @return Zahl im TextField; wenn keine Zahl 0
     */
    private int getBetrag(JTextField textField)
    {
        String text = textField.getText();
        if (istInt(text))
        {
            return Integer.parseInt(text);
        }
        else
        {
            return 0;
        }
    }

    /**
     * Gibt zurück, ob ein String nur aus Ziffern besteht.
     * @param text Der zu prüfende String
     * @return Gibt zurück, ob der String eine Zahl ist.
     */
    public static boolean istInt(String text)
    {
        for (int i = 0; i < text.length(); i++)
        {
            if (!Character.isDigit(text.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
}