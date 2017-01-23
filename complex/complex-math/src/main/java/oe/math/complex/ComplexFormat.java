/*-
 * Copyright (c) 2009-2010, Oleg Estekhin
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  * Neither the names of the copyright holders nor the names of their
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package oe.math.complex;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

/** Provides parsing and formatting of complex numbers. */
public final class ComplexFormat extends Format {

    private static final String PLUS = "+";

    private static final String MINUS = "-";

    private static final String INFINITY = "\u221E"; // ∞

    private static final String NaN = "\uFFFD"; // �

    private static final String IMAGINARY = "i";


    /**
     * Formats a double value and appends the resulting text to a given string buffer.
     * <p/>
     * This method converts a double value to a string using the {@link Double#toString(double)} except that infinite
     * values are represented by the characters {@code \u221E} (∞) and NaN is represented by the characters {@code
     * \uFFFD} (�).
     *
     * @param value the double value to format
     * @param toAppendTo the buffer to append the formatted text to
     *
     * @return the string buffer passed in as {@code toAppendTo}, with formatted text appended
     *
     * @see Double#toString(double)
     */
    static StringBuffer formatDouble( double value, StringBuffer toAppendTo ) {
        if ( value == Double.POSITIVE_INFINITY ) {
            toAppendTo.append( INFINITY );
        } else if ( value == Double.NEGATIVE_INFINITY ) {
            toAppendTo.append( MINUS ).append( INFINITY );
        } else if ( value != value ) {
            toAppendTo.append( NaN );
        } else {
            toAppendTo.append( value );
        }
        return toAppendTo;
    }

    /**
     * Formats a complex value and appends the resulting text to a given string buffer.
     *
     * @param complex the complex value to format
     * @param toAppendTo the buffer to append the formatted text to
     *
     * @return the string buffer passed in as {@code toAppendTo}, with formatted text appended
     *
     * @see #formatDouble(double, StringBuffer)
     */
    static StringBuffer formatComplex( Complex complex, StringBuffer toAppendTo ) {
        if ( complex.hasRealPart() ) {
            double re = complex.getRe();
            formatDouble( re, toAppendTo );
        }
        if ( complex.hasImaginaryPart() ) {
            double im = complex.getIm();
            if ( complex.hasRealPart() && Double.compare( im, 0.0 ) >= 0 ) {
                toAppendTo.append( PLUS );
            }
            formatDouble( im, toAppendTo );
            toAppendTo.append( IMAGINARY );
        }
        return toAppendTo;
    }

    static String complexToString( Complex complex ) {
        StringBuffer buffer = new StringBuffer();
        formatComplex( complex, buffer );
        return buffer.toString();
    }

    static Complex stringToComplex( String source ) {
        source = source.trim();
        ComplexFormat complexFormat = new ComplexFormat();
        ParsePosition pos = new ParsePosition( 0 );
        Complex complex = complexFormat.parse( source, pos );
        if ( complex == null || pos.getIndex() < source.length() ) {
            throw new NumberFormatException( "the source string does not contain a parsable complex" );
        }
        return complex;
    }


    /** The number format used to parse double values from strings. */
    private DecimalFormat numberFormat;

    /** Indicates whether {@code parse} methods stop parsing after obtaining either the real or imaginary part. */
    private boolean parsePartial;


    /** Creates a new {@code ComplexFormat} object. */
    public ComplexFormat() {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols( Locale.ENGLISH );
        decimalFormatSymbols.setDecimalSeparator( '.' );
        decimalFormatSymbols.setInfinity( INFINITY );
        decimalFormatSymbols.setNaN( NaN );
        decimalFormatSymbols.setMinusSign( MINUS.charAt( 0 ) );
        numberFormat = new DecimalFormat( "#0.#", decimalFormatSymbols );
        numberFormat.setGroupingUsed( false );
    }


    /**
     * Returns whether {@code parse} methods will stop parsing after obtaining either the real or imaginary part.
     * <p/>
     * The default value is {@code false}.
     *
     * @return {@code true} if parsing will stop after obtaining either the real or imaginary part; {@code false} if
     *         {@code parse} methods will attempt to parse the imaginary part after obtaining the real part
     *
     * @see #setParsePartial(boolean)
     */
    public boolean isParsePartial() {
        return parsePartial;
    }

    /**
     * Sets whether {@code parse} methods should stop parsing after obtaining either the real or imaginary part.
     *
     * @param parsePartial if {@code true} then parsing will stop after obtaining either the real or imaginary part; if
     * {@code false} then {@code parse} methods will attempt to parse the imaginary part after obtaining the real part
     *
     * @see #isParsePartial()
     */
    public void setParsePartial( boolean parsePartial ) {
        this.parsePartial = parsePartial;
    }


    @Override
    public StringBuffer format( Object object, StringBuffer toAppendTo, FieldPosition pos ) {
        if ( object instanceof Complex ) {
            return format( ( Complex ) object, toAppendTo, pos );
        } else {
            throw new IllegalArgumentException( "object is not an instance of Complex" );
        }
    }

    /**
     * Formats a complex value and appends the resulting text to a given string buffer.
     *
     * @param complex the complex value to format
     * @param toAppendTo the buffer to append the formatted text to
     * @param pos ignored
     *
     * @return the string buffer passed in as {@code toAppendTo}, with formatted text appended
     *
     * @throws IllegalArgumentException if {@code complex} is {@code null}
     * @throws NullPointerException if {@code toAppendTo} or {@code pos} is {@code null}
     */
    public StringBuffer format( Complex complex, StringBuffer toAppendTo, FieldPosition pos ) {
        if ( complex == null ) {
            throw new IllegalArgumentException( "complex is null" );
        }
        if ( toAppendTo == null ) {
            throw new NullPointerException( "toAppendTo is null" );
        }
        if ( pos == null ) {
            throw new NullPointerException( "pos is null" );
        }
        formatComplex( complex, toAppendTo );
        return toAppendTo;
    }

    /**
     * Formats a complex value and returns the resulting text.
     *
     * @param complex the complex value to format
     *
     * @return the text representation of the {@code complex} value
     *
     * @throws IllegalArgumentException if {@code complex} is {@code null}
     */
    public String format( Complex complex ) {
        if ( complex == null ) {
            throw new IllegalArgumentException( "complex is null" );
        }
        return complexToString( complex );
    }


    @Override
    public Object parseObject( String source ) throws ParseException {
        return parse( source );
    }

    @Override
    public Object parseObject( String source, ParsePosition pos ) {
        return parse( source, pos );
    }

    /**
     * Parses the text from the beginning of the given string to produce a complex number. This method may not use the
     * entire text of the given string.
     *
     * @param source the string to be parsed
     *
     * @return a {@code Complex} value parsed from the string
     *
     * @throws IllegalArgumentException if {@code source} is {@code null}
     * @throws ParseException if the beginning of the specified string cannot be parsed
     */
    public Complex parse( String source ) throws ParseException {
        ParsePosition pos = new ParsePosition( 0 );
        Complex result = parse( source, pos );
        if ( result == null ) {
            throw new ParseException( source, pos.getErrorIndex() );
        }
        return result;
    }

    /**
     * Parses the text from a string to produce a complex number.
     * <p/>
     * The method attempts to parse the text starting at the index given by {@code pos}. If parsing succeeds, then the
     * index of {@code pos} is updated to the index after the last character used (parsing does not necessarily use all
     * characters up to the end of the string), and the parsed object is returned. If an error occurs, then the index of
     * {@code pos} is not changed, the error index of {@code pos} is set to the index of the character where the error
     * occurred, and {@code null} is returned.
     *
     * @param source the string to be parsed
     * @param pos a {@code ParsePosition} object with index and error index information
     *
     * @return a {@code Complex} value parsed from the string, or {@code null} if the parse fails
     *
     * @throws IllegalArgumentException if {@code source} is {@code null}
     * @throws NullPointerException if {@code pos} is {@code null}
     */
    public Complex parse( String source, ParsePosition pos ) {
        if ( source == null ) {
            throw new IllegalArgumentException( "source is null" );
        }
        if ( pos == null ) {
            throw new NullPointerException( "pos is null" );
        }
        int index = pos.getIndex();
        int errorIndex = -1;
        try {
            skipWhitespace( source, pos );
            int parsingIndex = pos.getIndex();
            String firstToken = tryParseToken( source, pos );
            if ( IMAGINARY.equals( firstToken ) ) {
                errorIndex = parsingIndex;
                return null;
            }

            skipWhitespace( source, pos );
            parsingIndex = pos.getIndex();
            Number firstNumber = tryParseNumber( source, pos );
            if ( firstNumber == null ) {
                errorIndex = parsingIndex;
                return null;
            }
            double firstValue = firstNumber.doubleValue();
            if ( MINUS.equals( firstToken ) ) {
                firstValue = -firstValue;
            }
            index = pos.getIndex();

            boolean whitespace = skipWhitespace( source, pos );
            String secondToken = tryParseToken( source, pos );
            if ( IMAGINARY.equals( secondToken ) ) {
                if ( whitespace ) {
                    return Complex.real( firstValue );
                } else {
                    index = pos.getIndex();
                    return Complex.imaginary( firstValue );
                }
            }
            if ( secondToken == null || parsePartial ) {
                return Complex.real( firstValue );
            }

            skipWhitespace( source, pos );
            Number secondNumber = tryParseNumber( source, pos );
            if ( secondNumber == null ) {
                return Complex.real( firstValue );
            }
            double secondValue = secondNumber.doubleValue();
            if ( MINUS.equals( secondToken ) ) {
                secondValue = -secondValue;
            }

            whitespace = skipWhitespace( source, pos );
            String thirdToken = tryParseToken( source, pos );
            if ( IMAGINARY.equals( thirdToken ) && !whitespace ) {
                index = pos.getIndex();
                return Complex.cartesian( firstValue, secondValue );
            } else {
                return Complex.real( firstValue );
            }
        } finally {
            pos.setIndex( index );
            pos.setErrorIndex( errorIndex );
        }
    }

    private boolean skipWhitespace( String source, ParsePosition pos ) {
        boolean whitespace = false;
        int index = pos.getIndex();
        while ( index < source.length() && Character.isWhitespace( source.charAt( index ) ) ) {
            index++;
            whitespace = true;
        }
        pos.setIndex( index );
        return whitespace;
    }

    private String tryParseToken( String source, ParsePosition pos ) {
        int index = pos.getIndex();
        if ( index >= source.length() ) {
            return null;
        }
        if ( source.regionMatches( index, PLUS, 0, PLUS.length() ) ) {
            pos.setIndex( index + PLUS.length() );
            return PLUS;
        }
        if ( source.regionMatches( index, MINUS, 0, MINUS.length() ) ) {
            pos.setIndex( index + MINUS.length() );
            return MINUS;
        }
        if ( Character.isUnicodeIdentifierStart( source.charAt( index ) ) ) {
            // token extraction does not handle supplementary characters
            int endIndex = index + 1;
            while ( endIndex < source.length() && Character.isUnicodeIdentifierPart( source.charAt( endIndex ) ) ) {
                endIndex++;
            }
            String token = source.substring( index, endIndex );
            if ( IMAGINARY.equals( token ) ) {
                pos.setIndex( index + IMAGINARY.length() );
                return IMAGINARY;
            }
        }
        return null;
    }

    private Number tryParseNumber( String source, ParsePosition pos ) {
        int index = pos.getIndex();
        if ( index >= source.length() ) {
            return null;
        }
        String positivePrefix = numberFormat.getPositivePrefix();
        if ( !positivePrefix.isEmpty() && source.regionMatches( index, positivePrefix, 0, positivePrefix.length() ) ) {
            return null;
        }
        String negativePrefix = numberFormat.getNegativePrefix();
        if ( !negativePrefix.isEmpty() && source.regionMatches( index, negativePrefix, 0, negativePrefix.length() ) ) {
            return null;
        }
        return numberFormat.parse( source, pos );
    }

}
