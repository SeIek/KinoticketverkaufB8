package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.barzahlung;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import de.uni_hamburg.informatik.swt.se2.kino.myswing.restrictedtextfields.JDecimalTextField;

/**
 * Das UI für das BarzahlungsWerkzeug.
 * 
 * @version 25.06.2016
 */
class BarzahlungsWerkzeugUI
{
    private JDialog _dialogfenster;

    private JPanel _hauptfenster;
    private JPanel _behilfspanel;

    private JPanel _buttonpanel;
    private JButton _button_ok;
    private JButton _button_abbrechen;

    private static final String TITEL = "Barzahlung";

    private static final String ZUZAHLEN = "zu zahlen:";
    private String _zuzahlen_betrag;

    private static final String GEZAHLT = "gezahlt:";
    private JDecimalTextField _eingabebetrag;

    private static final String RESTBETRAG = "Restbetrag:";
    private JLabel _restbetraglabel;

    private static final String BESTAETIGEN = "OK";
    private static final String ABBRECHEN = "Abbrechen";

    /**
     * Der komplette JDialog wird im Konstruktor... konstruiert.
     * 
     * @param fenster_hauptprogramm Der Parent JFrame des JDialog, von dem dieser abhängt.
     * @param zuzahlen Der zu zahlende Geldbetrag, der angezeigt wird.
     */
    public BarzahlungsWerkzeugUI(int zuzahlen)
    {
        assert zuzahlen >= 0 : "Vorbedingung verletzt: zuzahlen >= 0";

        _dialogfenster = new JDialog((JFrame) null, TITEL, true);
        erstelleHauptfenster(zuzahlen);
        _dialogfenster.getContentPane()
            .add(_hauptfenster, BorderLayout.NORTH);

        _dialogfenster.add(new JSeparator());

        erstelleButtonPanel();
        _dialogfenster.getContentPane()
            .add(_buttonpanel, BorderLayout.SOUTH);

        _dialogfenster.pack();
        _dialogfenster.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // oder JDialog.HIDE_ON_CLOSE ?
        _dialogfenster.setLocationRelativeTo(null); // zentriert den Dialog in der Bildschirmmitte
    }

    /**
     *Erstellt das Hauptfenster mit den Indizes, Beträgen und dem Eingabefeld.
     * 
     * @param zuzahlen Der zu zahlende Geldbetrag, der angezeigt wird.
     */
    private void erstelleHauptfenster(int zuzahlen)
    {
        _hauptfenster = new JPanel();
        GridLayout tabelle_layout = new GridLayout(3, 2);
        _hauptfenster.setLayout(tabelle_layout);

        _behilfspanel = new JPanel();// ohne diesen bringt das JTextfield die Reihenfolge im GridLayout durcheinander

        _zuzahlen_betrag = formatiereZahl(zuzahlen);

        _eingabebetrag = new JDecimalTextField(10);
        _eingabebetrag.setAllowNegative(false);
        _eingabebetrag.setMaximumFractionDigits(2);
        _eingabebetrag.setHorizontalAlignment(JTextField.CENTER);

        _restbetraglabel = new JLabel("", SwingConstants.CENTER);
        _behilfspanel.add(_eingabebetrag, SwingConstants.CENTER);

        _hauptfenster.add(new JLabel(ZUZAHLEN, SwingConstants.CENTER));
        _hauptfenster.add(new JLabel(_zuzahlen_betrag, SwingConstants.CENTER));

        _hauptfenster.add(new JLabel(GEZAHLT, SwingConstants.CENTER));
        _hauptfenster.add(_behilfspanel);

        _hauptfenster.add(new JLabel(RESTBETRAG, SwingConstants.CENTER));
        _hauptfenster.add(_restbetraglabel);
    }

    /**
     * Formatiert die Zahl zu einem String, so dass sie als Eurobetrag angezeigt wird.
     * 
     * @param zahl Die zu formatierende Zahl
     * @return formatierter String
     */
    private String formatiereZahl(int zahl)
    {
        // Modulo kann auch negativ werden!
        String rest = "" + Math.abs(zahl % 100);
        if (rest.length() == 1)
        {
            rest = "0" + rest;
        }
        return (zahl / 100) + "," + rest;
    }

    /**
     * Erstellt das Panel mit den beiden Buttons.
     */
    private void erstelleButtonPanel()
    {
        _buttonpanel = new JPanel();
        FlowLayout buttonpanel_layout = new FlowLayout();
        _buttonpanel.setLayout(buttonpanel_layout);

        _button_ok = new JButton(BESTAETIGEN);
        _buttonpanel.add(_button_ok);

        _button_abbrechen = new JButton(ABBRECHEN);
        _buttonpanel.add(_button_abbrechen);
    }

    /**
     * Zeigt das Fenster an.
     */
    public void zeigeFenster()
    {
        _dialogfenster.setVisible(true);
    }

    /**
     * Schließt das Fenster.
     */
    public void schliesseFenster()
    {
        _dialogfenster.dispose(); // oder _dialogfenster.setVisible(false); ?
    }

    public JButton getOKButton()
    {
        return _button_ok;
    }

    public JButton getCancelButton()
    {
        return _button_abbrechen;
    }

    public int getGezahlterBetrag()
    {
        return (int) (_eingabebetrag.getValue() * 100);
    }

    public JTextField getEingabefeld()
    {
        return _eingabebetrag;
    }

    /**
     * Setzt den Text im JLabel _RestbetragLabel auf den Restbetrag
     * @param betrag Der Restbetrag
     */
    public void setRestbetrag(int betrag)
    {
        _restbetraglabel.setText(formatiereZahl(betrag));
    }
}