package de.uni_hamburg.informatik.swt.se2.kino.myswing.restrictedtextfields;

/**
 * Extends JTextField, to accept integer input only
 * 
 * @author Oliver Pola, 5pola@informatik.uni-hamburg.de 
 * @version 23.06.2016
 */
public class JIntegerTextField extends JNumberTextField
{
    private static final long serialVersionUID = 1L;

    /**
     * Create TextField and calculate preferred width
     * 
     * @param columns Number of columns to calculate preferred width
     */
    public JIntegerTextField(int columns)
    {
        super(columns);
    }

    /**
     * Create TextField with preset value and calculate preferred width
     * 
     * @param value A integer value to preset
     * @param columns Number of columns to calculate preferred width
     */
    public JIntegerTextField(int value, int columns)
    {
        super(String.valueOf(value), columns);
    }

    /**
     * Create TextField
     */
    public JIntegerTextField()
    {
    }

    /**
     * Set integer value to TextField text
     *  
     * @param value The integer value to set
     */
    public void setValue(int value)
    {
        setText(String.valueOf(value));
    }

    /**
     * Get TextField text as integer value
     * 
     * @return The read integer value
     */
    public int getValue()
    {
        if (getText().length() == 0)
        {
            return 0;
        }
        else
        {
            try
            {
                int value = Integer.parseInt(getText());
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
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
}
