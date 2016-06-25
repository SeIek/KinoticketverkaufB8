package de.uni_hamburg.informatik.swt.se2.kino.myswing.restrictedtextfields;

/**
 * Extends JTextField, to accept filtered input only
 * 
 * This is a base class for number only input, where the
 * column size calculation can is modified to be smaller.
 * 
 * @author Oliver Pola, 5pola@informatik.uni-hamburg.de
 * @version 23.06.2016
 */
public abstract class JNumberTextField extends JRestrictedTextField
{
    private static final long serialVersionUID = 1L;
    private static final double NUMBER_COLUMN_SIZE_FACTOR = 0.7;

    private boolean _allowNegative = true;
    private boolean _leadingMinus = false;

    /**
     * Create TextField and calculate preferred width
     * 
     * @param columns Number of columns to calculate preferred width
     */
    public JNumberTextField(int columns)
    {
        super(calculateColumns(columns));
    }

    /**
     * Create TextField with preset text and calculate preferred width
     * 
     * @param text A text to preset
     */
    public JNumberTextField(String text)
    {
        super(text);
    }

    /**
     * Create TextField with preset text
     * 
     * @param text A text to preset
     * @param columns Number of columns to calculate preferred width
     */
    public JNumberTextField(String text, int columns)
    {
        super(text, calculateColumns(columns));
    }

    /**
     * Create TextField
     */
    public JNumberTextField()
    {
    }

    /**
     * Get AllowNegative property
     * 
     * @return AllowNegative property
     */
    public boolean getAllowNegative()
    {
        return _allowNegative;
    }

    /**
     * Set AllowNegative property
     * 
     * @param allow AllowNegative property
     */
    public void setAllowNegative(boolean allow)
    {
        _allowNegative = allow;
        if (!allow && getText().length() > 0)
        {
            invertValueSign();
        }
    }

    /**
     * Invert the sign of current value
     */
    protected abstract void invertValueSign();

    /**
     * Validate the input, called by the Filter
     * 
     * @param input The input to validate
     * @return true if accepted, false otherwise
     */
    protected abstract boolean validateNumber(String input);

    /**
     * Validate wraps some tests, please override validateNumber()
     */
    @Override
    protected final boolean validate(String input)
    {
        if (input.length() == 0)
        {
            return true;
        }
        if (input.equals("-"))
        {
            if (getText().length() > 1)
            {
                // Deleted last digit after minus sign
                setText("");
            }
            _leadingMinus = _allowNegative;
            return false;
        }
        if (input.equals("+"))
        {
            _leadingMinus = false;
            return false;
        }
        if (!_allowNegative && input.charAt(0) == '-')
        {
            return false;
        }
        boolean result = validateNumber(input);
        if (_allowNegative && _leadingMinus && result && input.length() == 1)
        {
            if (validateNumber("-" + input))
            {
                _leadingMinus = false;
                setText("-" + input);
                // Return false to stop using input twice
                return false;
            }
        }
        return result;
    }

    private static int calculateColumns(int columns)
    {
        return (int) (columns * NUMBER_COLUMN_SIZE_FACTOR);
    }
}
