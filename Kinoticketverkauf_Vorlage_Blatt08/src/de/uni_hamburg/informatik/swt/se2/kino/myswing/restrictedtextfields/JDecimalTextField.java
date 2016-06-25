package de.uni_hamburg.informatik.swt.se2.kino.myswing.restrictedtextfields;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * Extends JTextField, to accept decimal input only
 * 
 * @author Oliver Pola, 5pola@informatik.uni-hamburg.de 
 * @version 23.06.2016
 */
public class JDecimalTextField extends JNumberTextField
{
    private static final long serialVersionUID = 1L;

    DecimalFormat _format;

    /**
     * Create TextField and calculate preferred width
     * 
     * @param columns Number of columns to calculate preferred width
     */
    public JDecimalTextField(int columns)
    {
        super(columns);
        CreateFormat();
    }

    /**
     * Create TextField with preset value and calculate preferred width
     * 
     * @param value A double value to preset
     * @param columns Number of columns to calculate preferred width
     */
    public JDecimalTextField(double value, int columns)
    {
        this(columns);
        setValue(value);
    }

    /**
     * Create TextField with preset value
     * 
     * @param value A double value to preset
     */
    public JDecimalTextField(double value)
    {
        this();
        setValue(value);
    }

    /**
     * Create TextField
     */
    public JDecimalTextField()
    {
        CreateFormat();
    }

    /**
     * Create TextField with preset value and calculate preferred width
     * 
     * @param columns Number of columns to calculate preferred width
     * @param maximumIntegerDigits The MaximumIntegerDigits of DecimalFormat
     * @param decimalSeparator The DecimalSeparator of DecimalFormat
     * @param maximumFractionDigits The MaximumFractionDigits of DecimalFormat
     */
    public JDecimalTextField(int columns, int maximumIntegerDigits,
            char decimalSeparator, int maximumFractionDigits)
    {
        this(columns);
        setMaximumIntegerDigits(maximumIntegerDigits);
        setDecimalSeparator(decimalSeparator);
        setMaximumFractionDigits(maximumFractionDigits);
    }

    /**
     * Create TextField with preset value and calculate preferred width
     * 
     * @param maximumIntegerDigits The MaximumIntegerDigits of DecimalFormat
     * @param decimalSeparator The DecimalSeparator of DecimalFormat
     * @param maximumFractionDigits The MaximumFractionDigits of DecimalFormat
     */
    public JDecimalTextField(int maximumIntegerDigits, char decimalSeparator,
            int maximumFractionDigits)
    {
        this(maximumIntegerDigits + 1 + maximumFractionDigits,
                maximumIntegerDigits, decimalSeparator, maximumFractionDigits);
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
                double value = _format.parse(getText())
                    .doubleValue();
                return value;
            }
            catch (ParseException e)
            {
                // Should not happen thanks to validate()
                return 0;
            }
        }
    }

    /**
     * Set long value to TextField text
     *  
     * @param value The long value to set
     */
    public void setValue(double value)
    {
        setText(_format.format(value));
    }

    /**
     * Get MaximumFractionDigits of DecimalFormat
     * 
     * @return MaximumFractionDigits of DecimalFormat
     */
    public int getMaximumFractionDigits()
    {
        return _format.getMaximumFractionDigits();
    }

    /**
     * Get MaximumIntegerDigits of DecimalFormat
     * 
     * @return MaximumIntegerDigits of DecimalFormat
     */
    public int getMaximumIntegerDigits()
    {
        return _format.getMaximumIntegerDigits();
    }

    /**
     * Get DecimalSeparator of DecimalFormat
     * 
     * @return DecimalSeparator of DecimalFormat
     */
    public char getDecimalSeparator()
    {
        return _format.getDecimalFormatSymbols()
            .getDecimalSeparator();
    }

    /**
     * Set MaximumFractionDigits of DecimalFormat
     */
    public void setMaximumFractionDigits(int digits)
    {
        _format.setMaximumFractionDigits(digits);
        refreshValue();
    }

    /**
     * Set MaximumIntegerDigits of DecimalFormat
     */
    public void setMaximumIntegerDigits(int digits)
    {
        _format.setMaximumIntegerDigits(digits);
        refreshValue();
    }

    /**
     * Set DecimalSeparator of DecimalFormat
     */
    public void setDecimalSeparator(char separator)
    {
        // refresh() could not parse, after separator changed, get value first
        double value = getValue();
        DecimalFormatSymbols sym = _format.getDecimalFormatSymbols();
        sym.setDecimalSeparator(separator);
        _format.setDecimalFormatSymbols(sym);
        if (getText().length() > 0)
        {
            setValue(value);
        }
    }

    private void CreateFormat()
    {
        _format = new DecimalFormat();

        // Thousands separators don't work on input
        _format.setGroupingUsed(false);
    }

    private void refreshValue()
    {
        if (getText().length() > 0)
        {
            setValue(getValue());
        }
    }

    private int countFractionDigits(String input)
    {
        int sepPos = input.indexOf(getDecimalSeparator());
        if (sepPos < 0)
        {
            return 0;
        }
        return input.length() - sepPos - 1;
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
            String in = input;

            // Temporarily manipulate format to accept trailing separator or zeroes
            _format.setDecimalSeparatorAlwaysShown(
                    in.contains(String.valueOf(getDecimalSeparator())));
            _format.setMinimumFractionDigits(Math
                .min(countFractionDigits(in), getMaximumFractionDigits()));

            double value = _format.parse(in)
                .doubleValue();
            String out = _format.format(value);

            //System.out.println(input + " ?= " + s);

            // Reset format
            _format.setDecimalSeparatorAlwaysShown(false);
            _format.setMinimumFractionDigits(0);

            return in.equals(out);
        }
        catch (ParseException e)
        {
            return false;
        }
    }
}
