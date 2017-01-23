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

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Deque;
import java.util.LinkedList;

/** Provides parsing and formatting of complex arithmetic expressions in the infix form. */
public final class ExpressionFormat extends Format {

    static String expressionToString( Expression expression ) {
        ExpressionFormat format = new ExpressionFormat();
        return format.format( expression );
    }

    static Expression stringToExpression( String source ) {
        ExpressionFormat format = new ExpressionFormat();
        ParsePosition pos = new ParsePosition( 0 );
        Expression result = format.parse( source, pos );
        if ( result == null || pos.getIndex() < source.length() ) {
            throw new IllegalArgumentException( "the source string does not contain a parsable expression" );
        }
        return result;
    }


    /** The complex format used to parse complex values from strings. */
    private final ComplexFormat complexFormat;

    /** Indicates whether {@code format} methods add spaces around binary operators. */
    private boolean spaceAroundBinaryOperators;

    /** Indicates whether {@code format} methods add spaces within parentheses. */
    private boolean spaceWithinParentheses;


    /** Creates a new {@code ExpressionFormat} object. */
    public ExpressionFormat() {
        complexFormat = new ComplexFormat();
        complexFormat.setParsePartial( true );
        spaceAroundBinaryOperators = true;
        spaceWithinParentheses = true;
    }


    /**
     * Returns whether {@code format} methods will add spaces around binary operators.
     * <p/>
     * The default value is {@code true}.
     *
     * @return {@code true} if formatting will add spaces around binary operators; {@code false} otherwise
     *
     * @see #setSpaceAroundBinaryOperators(boolean)
     */
    public boolean isSpaceAroundBinaryOperators() {
        return spaceAroundBinaryOperators;
    }

    /**
     * Sets whether {@code format} methods should add spaces around binary operators.
     *
     * @param spaceAroundBinaryOperators if {@code true} then formatting will add spaces around binary operators
     *
     * @see #isSpaceAroundBinaryOperators()
     */
    public void setSpaceAroundBinaryOperators( boolean spaceAroundBinaryOperators ) {
        this.spaceAroundBinaryOperators = spaceAroundBinaryOperators;
    }

    /**
     * Returns whether {@code format} methods will add spaces within parentheses
     * <p/>
     * The default value is {@code true}.
     *
     * @return {@code true} if formatting will add spaces within parentheses; {@code false} otherwise
     *
     * @see #setSpaceWithinParentheses(boolean)
     */
    public boolean isSpaceWithinParentheses() {
        return spaceWithinParentheses;
    }

    /**
     * Sets whether {@code format} methods should add spaces within parentheses.
     *
     * @param spaceWithinParentheses if {@code true} then formatting will add spaces within parentheses
     *
     * @see #isSpaceWithinParentheses()
     */
    public void setSpaceWithinParentheses( boolean spaceWithinParentheses ) {
        this.spaceWithinParentheses = spaceWithinParentheses;
    }


    @Override
    public StringBuffer format( Object obj, StringBuffer toAppendTo, FieldPosition pos ) {
        if ( obj instanceof Expression ) {
            return format( ( Expression ) obj, toAppendTo, pos );
        } else {
            throw new IllegalArgumentException( "object is not an instance of Expression" );
        }
    }

    /**
     * Formats a complex arithmetic expression and appends the resulting text to a given string buffer.
     *
     * @param expression the arithmetic expression to format
     * @param toAppendTo the buffer to append the formatted text to
     * @param pos ignored
     *
     * @return the string buffer passed in as {@code toAppendTo}, with formatted text appended
     *
     * @throws IllegalArgumentException if {@code expression} is {@code null}
     * @throws NullPointerException if {@code toAppendTo} or {@code pos} is {@code null}
     */
    public StringBuffer format( Expression expression, StringBuffer toAppendTo, FieldPosition pos ) {
        if ( expression == null ) {
            throw new IllegalArgumentException( "expression is null" );
        }
        if ( toAppendTo == null ) {
            throw new NullPointerException( "toAppendTo is null" );
        }
        if ( pos == null ) {
            throw new NullPointerException( "pos is null" );
        }
        FormatContext context = new FormatContext( complexFormat, spaceAroundBinaryOperators, spaceWithinParentheses );
        toAppendTo.append( expression.format( context ) );
        return toAppendTo;
    }

    /**
     * Formats a complex arithmetic expression and returns the resulting text.
     *
     * @param expression the arithmetic expression to format
     *
     * @return the text representation of the {@code expression}
     *
     * @throws IllegalArgumentException if {@code expression} is {@code null}
     */
    public String format( Expression expression ) {
        if ( expression == null ) {
            throw new IllegalArgumentException( "expression is null" );
        }
        FormatContext context = new FormatContext( complexFormat, spaceAroundBinaryOperators, spaceWithinParentheses );
        return expression.format( context );
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
     * Parses the text from the beginning of the given string to produce a complex arithmetic expression. This method
     * may not use the entire text of the given string.
     *
     * @param source the string to be parsed
     *
     * @return an {@code Expression} value parsed from the string
     *
     * @throws IllegalArgumentException if {@code source} is {@code null}
     * @throws ParseException if the beginning of the specified string cannot be parsed
     */
    public Expression parse( String source ) throws ParseException {
        ParsePosition pos = new ParsePosition( 0 );
        Expression result = parse( source, pos );
        if ( result == null ) {
            throw new ParseException( source, pos.getErrorIndex() );
        }
        return result;
    }

    /**
     * Parses the text from a string to produce a complex arithmetic expression.
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
     * @return an {@code Expression} value parsed from the string, or {@code null} if the parse fails
     *
     * @throws IllegalArgumentException if {@code source} is {@code null}
     * @throws NullPointerException if {@code pos} is {@code null}
     */
    public Expression parse( String source, ParsePosition pos ) {
        if ( source == null ) {
            throw new IllegalArgumentException( "source is null" );
        }
        if ( pos == null ) {
            throw new NullPointerException( "pos is null" );
        }
        int index = pos.getIndex();
        int errorIndex = -1;
        int parsingIndex = index;
        try {
            Deque<ExpressionComponent> stack = new LinkedList<ExpressionComponent>();
            ExpressionBuilder output = new ExpressionBuilder();
            ParseContext context = new ParseContext();
            skipWhitespace( source, pos );
            parsingIndex = pos.getIndex();
            ExpressionComponent component = parseNextComponent( source, pos );
            while ( component != null ) {
                component.parse( stack, output, context );
                context.setPreviousType( component.getType() );
                skipWhitespace( source, pos );
                parsingIndex = pos.getIndex();
                component = parseNextComponent( source, pos );
            }
            parsingIndex = pos.getIndex();
            component = stack.peek();
            while ( component != null && component.getType() == ExpressionComponent.Type.OPERATOR ) {
                output.append( stack.pop() );
                component = stack.peek();
            }
            if ( stack.isEmpty() ) {
                Expression expression = output.create();
                index = parsingIndex;
                return expression;
            } else {
                errorIndex = parsingIndex;
                return null;
            }
        } catch ( IllegalStateException ignored ) {
            errorIndex = parsingIndex;
            return null;
        } finally {
            pos.setIndex( index );
            pos.setErrorIndex( errorIndex );
        }
    }


    private void skipWhitespace( String source, ParsePosition pos ) {
        int index = pos.getIndex();
        while ( index < source.length() && Character.isWhitespace( source.charAt( index ) ) ) {
            index++;
        }
        pos.setIndex( index );
    }

    private ExpressionComponent parseNextComponent( String source, ParsePosition pos ) {
        int index = pos.getIndex();
        if ( index >= source.length() ) {
            return null;
        }
        char c = source.charAt( index );
        if ( Character.isDigit( c ) ) {
            Complex complex = complexFormat.parse( source, pos );
            return complex == null ? null : new ComplexComponent( complex );
        } else if ( Character.isUnicodeIdentifierStart( c ) ) {
            // token extraction does not handle supplementary characters
            int startIndex = index;
            int endIndex = index + 1;
            while ( endIndex < source.length() && Character.isUnicodeIdentifierPart( source.charAt( endIndex ) ) ) {
                endIndex++;
            }
            String name = source.substring( startIndex, endIndex );
            pos.setIndex( endIndex );
            for ( FunctionComponent component : FunctionComponents.values() ) {
                if ( component.getType() == ExpressionComponent.Type.FUNCTION && component.getInfixSymbol().equals( name ) ) {
                    return component;
                }
            }
            return new VariableComponent( name );
        } else {
            for ( FunctionComponent component : FunctionComponents.values() ) {
                if ( component.getType() == ExpressionComponent.Type.OPERATOR ) {
                    String symbol = component.getInfixSymbol();
                    if ( source.regionMatches( index, symbol, 0, symbol.length() ) ) {
                        pos.setIndex( index + symbol.length() );
                        return component;
                    }
                }
            }
            for ( SeparatorComponent component : SeparatorComponents.values() ) {
                String symbol = component.getInfixSymbol();
                if ( source.regionMatches( index, symbol, 0, symbol.length() ) ) {
                    pos.setIndex( index + symbol.length() );
                    return component;
                }
            }
        }
        return null;
    }

}
