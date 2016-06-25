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

import de.uni_hamburg.informatik.swt.se2.kino.myswing.restrictedtextfields.JIntegerTextField;

/**
 * Das UI für das BarzahlungsWerkzeug.
 * 
 * @version 25.06.2016
 */
class BarzahlungsWerkzeugUI
{
    private JDialog _dialogfenster;
    private JFrame _fenster_hauptprogramm;

    private JPanel _hauptfenster;
    private JPanel _behilfspanel;

    private JPanel _buttonpanel;
    private JButton _button_ok;
    private JButton _button_abbrechen;

    private static final String TITEL = "Barzahlung";

    private static final String ZUZAHLEN = "zu zahlen:";
    private String _zuzahlen_betrag;

    private static final String GEZAHLT = "gezahlt:";
    private JTextField _eingabebetrag;

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
    public BarzahlungsWerkzeugUI(JFrame fenster_hauptprogramm, int zuzahlen)
    {
        assert fenster_hauptprogramm != null : "Vorbedingung verletzt: fenster_hauptprogramm != null";
        assert zuzahlen >= 0 : "Vorbedingung verletzt: zuzahlen >= 0";

        _fenster_hauptprogramm = fenster_hauptprogramm;

        _dialogfenster = new JDialog(_fenster_hauptprogramm, TITEL, true);
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
     * Erstellt das Hauptfenster mit den Indizes, Beträgen und dem Eingabefeld.
     * 
     * @param zuzahlen Der zu zahlende Geldbetrag, der angezeigt wird.
     */
    private void erstelleHauptfenster(int zuzahlen)
    {
        _hauptfenster = new JPanel();
        GridLayout tabelle_layout = new GridLayout(0, 2, 0, 0);
        _hauptfenster.setLayout(tabelle_layout);

        _behilfspanel = new JPanel();// ohne diesen bringt das JTextfield die Reihenfolge im GridLayout durcheinander

        _zuzahlen_betrag = "" + zuzahlen;
        
        // TODO hier geerbtes TextField eingebaut
        // _eingabebetrag = new JTextField(10);
        JIntegerTextField eingabe = new JIntegerTextField(10);
        eingabe.setAllowNegative(false);
        _eingabebetrag = eingabe;
        
        _eingabebetrag.setHorizontalAlignment(JTextField.CENTER);

        _restbetraglabel = new JLabel("0", SwingConstants.CENTER); // zeigt anfangs den Wert 0 an, wird später durch das BarzahlungsWerkzeug berechnet.
        _behilfspanel.add(_eingabebetrag, SwingConstants.CENTER);
        _hauptfenster.add(new JLabel(ZUZAHLEN, SwingConstants.CENTER));
        _hauptfenster.add(new JLabel(_zuzahlen_betrag, SwingConstants.CENTER));
        _hauptfenster.add(new JLabel(GEZAHLT, SwingConstants.CENTER));
        _hauptfenster.add(_behilfspanel);
        _hauptfenster.add(new JLabel(RESTBETRAG, SwingConstants.CENTER));
        _hauptfenster.add(_restbetraglabel);
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
        _restbetraglabel.setText("" + betrag);
    }
}