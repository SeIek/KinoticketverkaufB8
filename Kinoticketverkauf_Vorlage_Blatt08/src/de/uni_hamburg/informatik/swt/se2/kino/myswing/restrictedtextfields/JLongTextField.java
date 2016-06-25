package de.uni_hamburg.informatik.swt.se2.kino.myswing.restrictedtextfields;

/**
 * Extends JTextField, to accept long input only
 * 
 * @author Oliver Pola, 5pola@informatik.uni-hamburg.de 
 * @version 23.06.2016
 */
public class JLongTextField extends JNumberTextField
{
    private static final long serialVersionUID = 1L;

    /**
     * Create TextField and calculate preferred width
     * 
     * @param columns Number of columns to calculate preferred width
     */
    public JLongTextField(int columns)
    {
        super(columns);
    }

    /**
     * Create TextField with preset value and calculate preferred width
     * 
     * @param value A long value to preset
     * @param columns Number of columns to calculate preferred width
     */
    public JLongTextField(long value, int columns)
    {
        super(String.valueOf(value), columns);
    }

    /**
     * Create TextField with preset value
     * 
     * @param value A long value to preset
     */
    public JLongTextField(long value)
    {
        super (String.valueOf(value));
    }

    /**
     * Create TextField
     */
    public JLongTextField()
    {
    }

    /**
     * Set long value to TextField text
     *  
     * @param value The long value to set
     */
    public void setValue(long value)
    {
        setText(String.valueOf(value));
    }

    /**
     * Get TextField text as long value
     * 
     * @return The read long value
     */
    public long getValue()
    {
        if (getText().length() == 0)
        {
            return 0;
        }
        else
        {
            try
            {
                long value = Long.parseLong(getText());
                return value;
            }
            catch (NumberFormatException e)
            {
                // Should not happen thanks to validate()
                return 0;
            }
        }
    }

    @Override
    protected void invertValueSign()
    {
        setValue(-getValue());
    }

    @Override
    protected boolean validateNumber(String input)
    {
        try
        {
            Long.parseLong(input);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
}
