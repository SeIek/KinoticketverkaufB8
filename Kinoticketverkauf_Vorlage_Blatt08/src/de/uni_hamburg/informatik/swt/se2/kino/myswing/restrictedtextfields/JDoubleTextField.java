package de.uni_hamburg.informatik.swt.se2.kino.myswing.restrictedtextfields;

/**
 * Extends JTextField, to accept double input only
 * 
 * @author Oliver Pola, 5pola@informatik.uni-hamburg.de 
 * @version 23.06.2016
 */
public class JDoubleTextField extends JNumberTextField
{
    private static final long serialVersionUID = 1L;

    /**
     * Create TextField and calculate preferred width
     * 
     * @param columns Number of columns to calculate preferred width
     */
    public JDoubleTextField(int columns)
    {
        super(columns);
    }

    /**
     * Create TextField with preset value and calculate preferred width
     * 
     * @param value A double value to preset
     * @param columns Number of columns to calculate preferred width
     */
    public JDoubleTextField(double value, int columns)
    {
        super(String.valueOf(value), columns);
    }

    /**
     * Create TextField with preset value
     * 
     * @param value A double value to preset
     */
    public JDoubleTextField(double value)
    {
        super (String.valueOf(value));
    }

    /**
     * Create TextField
     */
    public JDoubleTextField()
    {
    }

    /**
     * Set long value to TextField text
     *  
     * @param value The long value to set
     */
    public void setValue(double value)
    {
        setText(String.valueOf(value));
    }

    /**
     * Get TextField text as double value
     * 
     * @return The read double value
     */
    public double getValue()
    {
        if (getText().length() == 0)
        {
            return 0;
        }
        else
        {
            try
            {
                double value = Double.parseDouble(getText());
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
            Double.parseDouble(input);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
}
