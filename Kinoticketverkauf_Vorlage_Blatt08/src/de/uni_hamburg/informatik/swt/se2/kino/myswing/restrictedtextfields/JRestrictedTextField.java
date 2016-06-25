package de.uni_hamburg.informatik.swt.se2.kino.myswing.restrictedtextfields;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 * Extends JTextField, to accept filtered input only
 * 
 * To apply filters derive from JRestrictedTextField
 * and implement validate(String)
 * 
 * @author Oliver Pola, 5pola@informatik.uni-hamburg.de
 * @version 22.06.2016
 */
public abstract class JRestrictedTextField extends JTextField
{
    private static final long serialVersionUID = 1L;
    
    /**
     * Create TextField and calculate preferred width
     * 
     * @param columns Number of columns to calculate preferred width
     */
    public JRestrictedTextField(int columns)
    {
        super(columns);
        init();
    }

    /**
     * Create TextField with preset text and calculate preferred width
     * 
     * @param text A text to preset
     */
    public JRestrictedTextField(String text)
    {
        super(text);
        init();
    }
    
    /**
     * Create TextField with preset text
     * 
     * @param text A text to preset
     * @param columns Number of columns to calculate preferred width
     */
    public JRestrictedTextField(String text, int columns)
    {
        super(text, columns);
        init();
    }
    
    /**
     * Create TextField
     */
    public JRestrictedTextField()
    {
        init();
    }

    /**
     * Validate the input, called by the Filter
     * 
     * @param input The input to validate
     * @return true if accepted, false otherwise
     */
    abstract protected boolean validate(String input);

    /**
     * React to restricted input, called by Filter if not accepted
     * @param input The rejected input
     */
    protected void reactToRejectedInput(String input)
    {
    }

    private void init()
    {
        Document d = getDocument();
        if (d instanceof PlainDocument)
        {
            PlainDocument doc = (PlainDocument) getDocument();
            doc.setDocumentFilter(new ValidationFilter());
        }
    }

    /**
     * A DocumentFilter Class linked to JRestrictedTextField
     * Calls JRestrictedTextField.validate(String) to check the input
     * Calls JRestrictedTextField.reactToRejectedInput(String) on rejection
     * 
     * @author Hovercraft Full Of Eels, embedded in JRestrictedTextField by Oliver Pola
     * http://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
     */
    private class ValidationFilter extends DocumentFilter
    {
        @Override
        public void insertString(FilterBypass fb, int offset, String string,
                AttributeSet attr) throws BadLocationException
        {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);

            if (validate(sb.toString()))
            {
                super.insertString(fb, offset, string, attr);
            }
            else
            {
                reactToRejectedInput(sb.toString());
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length,
                String text, AttributeSet attrs) throws BadLocationException
        {

            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);

            if (validate(sb.toString()))
            {
                super.replace(fb, offset, length, text, attrs);
            }
            else
            {
                reactToRejectedInput(sb.toString());
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length)
                throws BadLocationException
        {
            Document doc = fb.getDocument();
            StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);

            if (validate(sb.toString()))
            {
                super.remove(fb, offset, length);
            }
            else
            {
                reactToRejectedInput(sb.toString());
            }
        }
    }
}
