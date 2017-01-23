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

import java.text.ParsePosition;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a complex arithmetic expression.
 * <p/>
 * Instances of this class are immutable.
 */
public final class Expression {

    /**
     * Returns a new postfix expression builder.
     *
     * @return a new builder
     */
    public static ExpressionBuilder postfix() {
        return new ExpressionBuilder();
    }

    /**
     * Returns a complex arithmetic expression represented by the argument string.
     * <p/>
     * Leading and trailing whitespace characters in {@code source} are ignored. Whitespace is removed as if by the
     * {@link String#trim()} method. The rest of {@code source} should constitute an infix expression as produced by the
     * {@link #toString()} method.
     *
     * @param source the string to be parsed
     *
     * @return an expression represented by the argument string
     *
     * @throws IllegalArgumentException if {@code source} is {@code null}
     * @throws IllegalArgumentException if {@code source} does not contain a parsable infix expression
     * @see #toString()
     */
    public static Expression fromString( String source ) {
        if ( source == null ) {
            throw new IllegalArgumentException( "source is null" );
        }
        return ExpressionFormat.stringToExpression( source );
    }

    /**
     * Returns a complex arithmetic expression represented by the argument string.
     * <p/>
     * Leading and trailing whitespace characters in {@code source} are ignored. Whitespace is removed as if by the
     * {@link String#trim()} method. The rest of {@code source} should constitute a postfix expression as produced by
     * the {@link #toPostfixString()} method.
     *
     * @param source the string to be parsed
     *
     * @return an expression represented by the argument string
     *
     * @throws IllegalArgumentException if {@code source} is {@code null}
     * @throws IllegalArgumentException if {@code source} does not contain a parsable postfix expression
     * @see #toPostfixString()
     */
    public static Expression fromPostfixString( String source ) {
        if ( source == null ) {
            throw new IllegalArgumentException( "source is null" );
        }
        source = source.trim();
        if ( source.isEmpty() ) {
            throw new IllegalArgumentException( "source is empty" );
        }
        String[] tokens = source.split( "[ ]+" );
        List<ExpressionComponent> components = new ArrayList<ExpressionComponent>( tokens.length );
        ComplexFormat complexFormat = new ComplexFormat();
        ParsePosition pos = new ParsePosition( 0 );
        for ( String token : tokens ) {
            ExpressionComponent component = null;
            for ( FunctionComponent functionComponent : FunctionComponents.values() ) {
                if ( functionComponent.getPostfixSymbol().equals( token ) ) {
                    component = functionComponent;
                    break;
                }
            }
            if ( component == null ) {
                if ( VariableComponent.isValidName( token ) ) {
                    component = new VariableComponent( token );
                } else {
                    pos.setIndex( 0 );
                    pos.setErrorIndex( -1 );
                    Complex complex = complexFormat.parse( token, pos );
                    if ( complex != null && pos.getIndex() == token.length() ) {
                        component = new ComplexComponent( complex );
                    }
                }
            }
            if ( component == null ) {
                throw new IllegalArgumentException( "source contains invalid token: " + token );
            } else {
                components.add( component );
            }
        }
        return new Expression( components.toArray( new ExpressionComponent[components.size()] ) );
    }


    private final ExpressionComponent[] components;

    private final int maxDataStackSize;


    /**
     * Creates a new {@code Expression} object.
     *
     * @param components the expression components
     *
     * @throws IllegalArgumentException if {@code components} is {@code null}, empty or contains {@code null} element
     * @throws IllegalArgumentException if some operation will have insufficient operands during the expression
     * calculation
     */
    Expression( ExpressionComponent[] components ) {
        if ( components == null ) {
            throw new IllegalArgumentException( "components is null" );
        }
        if ( components.length == 0 ) {
            throw new IllegalArgumentException( "components is empty" );
        }
        int dataStackSize = 0;
        int maxDataStackSize = 0;
        for ( ExpressionComponent component : components ) {
            if ( component == null ) {
                throw new IllegalArgumentException( "components contains null" );
            }
            if ( dataStackSize < component.getOperandsCount() ) {
                throw new IllegalArgumentException( "insufficient operands" );
            }
            dataStackSize = dataStackSize - component.getOperandsCount() + component.getResultsCount();
            if ( maxDataStackSize < dataStackSize ) {
                maxDataStackSize = dataStackSize;
            }
        }
        this.components = components.clone();
        this.maxDataStackSize = maxDataStackSize;
    }


    ExpressionComponent[] getComponents() {
        return components.clone();
    }

    int getMaxDataStackSize() {
        return maxDataStackSize;
    }


    /**
     * Simplifies this expression by replacing some component sequences with their compact counterparts.
     * <p/>
     * This method calls {@link #simplify(Set)} with all possible simplifications requested.
     *
     * @return the simplified expression
     */
    public Expression simplify() {
        return simplify( EnumSet.allOf( Simplifications.class ) );
    }

    /**
     * Simplifies this expression by replacing some component sequences with their compact counterparts.
     *
     * @param simplifications the simplifications to perform
     *
     * @return the simplified expression
     *
     * @throws IllegalArgumentException if {@code simplifications} is {@code null}
     */
    public Expression simplify( Set<Simplifications> simplifications ) {
        if ( simplifications == null ) {
            throw new IllegalArgumentException( "simplifications is null" );
        }
        if ( simplifications.isEmpty() ) {
            return this;
        }
        List<ExpressionComponent> output = new ArrayList<ExpressionComponent>( components.length );
        int state = 0;
        Complex re = null;
        Complex im = null;
        Complex complex = null;
        for ( ExpressionComponent component : components ) {
            switch ( state ) {
                case 0:
                    // initial state
                    if ( component instanceof ComplexComponent ) {
                        ComplexComponent complexComponent = ( ComplexComponent ) component;
                        Complex value = complexComponent.getValue();
                        if ( value.hasRealPart() ) {
                            if ( value.hasImaginaryPart() ) {
                                complex = value;
                                state = 3;
                            } else {
                                re = value;
                                state = 1;
                            }
                        } else {
                            // assert complex.hasImaginaryPart();
                            im = value;
                            state = 2;
                        }
                    } else {
                        output.add( component );
                        state = 0;
                    }
                    break;
                case 1:
                    // re
                    if ( component instanceof ComplexComponent ) {
                        ComplexComponent complexComponent = ( ComplexComponent ) component;
                        Complex value = complexComponent.getValue();
                        if ( value.hasRealPart() ) {
                            output.add( new ComplexComponent( re ) );
                            re = null;
                            if ( value.hasImaginaryPart() ) {
                                complex = value;
                                state = 3;
                            } else {
                                re = value;
                                state = 1;
                            }
                        } else {
                            // assert complex.hasImaginaryPart();
                            im = value;
                            state = 4;
                        }
                    } else if ( FunctionComponents.NEGATE.equals( component ) && simplifications.contains( Simplifications.REAL_NEGATE ) ) {
                        re = re.negate();
                        state = 1;
                    } else if ( FunctionComponents.CONJUGATE.equals( component ) && simplifications.contains( Simplifications.REAL_CONJUGATE ) ) {
                        re = re.conjugate();
                        state = 1;
                    } else {
                        output.add( new ComplexComponent( re ) );
                        re = null;
                        output.add( component );
                        state = 0;
                    }
                    break;
                case 2:
                    // im
                    if ( component instanceof ComplexComponent ) {
                        output.add( new ComplexComponent( im ) );
                        im = null;
                        ComplexComponent complexComponent = ( ComplexComponent ) component;
                        Complex value = complexComponent.getValue();
                        if ( value.hasRealPart() ) {
                            if ( value.hasImaginaryPart() ) {
                                complex = value;
                                state = 3;
                            } else {
                                re = value;
                                state = 1;
                            }
                        } else {
                            // assert complex.hasImaginaryPart();
                            im = value;
                            state = 2;
                        }
                    } else if ( FunctionComponents.NEGATE.equals( component ) && simplifications.contains( Simplifications.IMAGINARY_NEGATE ) ) {
                        im = im.negate();
                        state = 2;
                    } else if ( FunctionComponents.NEGATE.equals( component ) && simplifications.contains( Simplifications.IMAGINARY_CONJUGATE ) ) {
                        im = im.conjugate();
                        state = 2;
                    } else {
                        output.add( new ComplexComponent( im ) );
                        im = null;
                        output.add( component );
                        state = 0;
                    }
                    break;
                case 3:
                    // complex
                    if ( component instanceof ComplexComponent ) {
                        output.add( new ComplexComponent( complex ) );
                        complex = null;
                        ComplexComponent complexComponent = ( ComplexComponent ) component;
                        Complex value = complexComponent.getValue();
                        if ( value.hasRealPart() ) {
                            if ( value.hasImaginaryPart() ) {
                                complex = value;
                                state = 3;
                            } else {
                                re = value;
                                state = 1;
                            }
                        } else {
                            // assert complex.hasImaginaryPart();
                            im = value;
                            state = 2;
                        }
                    } else if ( FunctionComponents.NEGATE.equals( component ) && simplifications.contains( Simplifications.COMPLEX_NEGATE ) ) {
                        complex = complex.negate();
                        state = 3;
                    } else if ( FunctionComponents.CONJUGATE.equals( component ) && simplifications.contains( Simplifications.COMPLEX_CONJUGATE ) ) {
                        complex = complex.negate();
                        state = 3;
                    } else {
                        output.add( new ComplexComponent( complex ) );
                        complex = null;
                        output.add( component );
                        state = 0;
                    }
                    break;
                case 4:
                    // re im
                    if ( component instanceof ComplexComponent ) {
                        output.add( new ComplexComponent( re ) );
                        re = null;
                        output.add( new ComplexComponent( im ) );
                        im = null;
                        ComplexComponent complexComponent = ( ComplexComponent ) component;
                        Complex value = complexComponent.getValue();
                        if ( value.hasRealPart() ) {
                            if ( value.hasImaginaryPart() ) {
                                complex = value;
                                state = 3;
                            } else {
                                re = value;
                                state = 1;
                            }
                        } else {
                            // assert complex.hasImaginaryPart();
                            im = value;
                            state = 2;
                        }
                    } else if ( FunctionComponents.ADD.equals( component ) && simplifications.contains( Simplifications.REAL_IMAGINARY_ADD ) ) {
                        complex = re.add( im );
                        re = null;
                        im = null;
                        state = 3;
                    } else if ( FunctionComponents.SUBTRACT.equals( component ) && simplifications.contains( Simplifications.REAL_IMAGINARY_SUBTRACT ) ) {
                        complex = re.subtract( im );
                        re = null;
                        im = null;
                        state = 3;
                    } else if ( FunctionComponents.NEGATE.equals( component ) && simplifications.contains( Simplifications.IMAGINARY_NEGATE ) ) {
                        im = im.negate();
                        state = 4;
                    } else {
                        output.add( new ComplexComponent( re ) );
                        re = null;
                        output.add( new ComplexComponent( im ) );
                        im = null;
                        output.add( component );
                        state = 0;
                    }
                    break;
                default:
                    throw new AssertionError( state );
            }
        }
        switch ( state ) {
            case 0:
                break;
            case 1:
                output.add( new ComplexComponent( re ) );
                break;
            case 2:
                output.add( new ComplexComponent( im ) );
                break;
            case 3:
                output.add( new ComplexComponent( complex ) );
                break;
            default:
                throw new AssertionError( state );
        }
        return new Expression( output.toArray( new ExpressionComponent[output.size()] ) );
    }


    /**
     * Calculates this expression and returns the result.
     *
     * @param context the calculation context that provides variable values
     *
     * @return the result of the calculation
     *
     * @throws IllegalArgumentException if {@code context} is {@code null}
     * @throws IllegalArgumentException if expression contains a variable whose value is not defined in the {@code
     * context}
     */
    public Complex calculate( CalculationContext context ) {
        if ( context == null ) {
            throw new IllegalArgumentException( "context is null" );
        }
        Deque<Complex> stack = new ArrayDeque<Complex>( maxDataStackSize );
        for ( ExpressionComponent component : components ) {
            component.calculate( stack, context );
        }
        if ( stack.size() != 1 ) {
            throw new AssertionError( stack );
        }
        return stack.pop();
    }

    /**
     * Formats this expression and returns the formatted text.
     *
     * @param context the formatting context
     *
     * @return the formatted text
     *
     * @throws IllegalArgumentException if {@code context} is {@code null}
     */
    String format( FormatContext context ) {
        if ( context == null ) {
            throw new IllegalArgumentException( "context is null" );
        }
        Deque<FormatToken> stack = new ArrayDeque<FormatToken>( maxDataStackSize );
        for ( ExpressionComponent component : components ) {
            component.format( stack, context );
        }
        if ( stack.size() != 1 ) {
            throw new AssertionError( stack );
        }
        return stack.pop().getValue();
    }


    @Override
    public int hashCode() {
        return Arrays.hashCode( components );
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        } else if ( obj instanceof Expression ) {
            Expression expression = ( Expression ) obj;
            return Arrays.equals( components, expression.components );
        } else {
            return false;
        }
    }

    /**
     * Returns a string representation of this expression in the infix form.
     *
     * @return the string representation of this expression in the infix form
     *
     * @see #fromString(String)
     */
    @Override
    public String toString() {
        return ExpressionFormat.expressionToString( this );
    }

    /**
     * Returns a string representation of this expression in the postfix form. The expression postfix form is a sequence
     * of symbols that represent expression components separated by the single space character.
     *
     * @return the string representation of this expression in the postfix form
     *
     * @see #fromPostfixString(String)
     */
    public String toPostfixString() {
        StringBuilder buffer = new StringBuilder();
        boolean separator = false;
        for ( ExpressionComponent component : components ) {
            if ( separator ) {
                buffer.append( ' ' );
            }
            buffer.append( component.getPostfixSymbol() );
            separator = true;
        }
        return buffer.toString();
    }


    /** Defines possible expression simplifications. */
    public enum Simplifications {

        /** Replace [real, negate()] with [-real]. */
        REAL_NEGATE,
        /** Replace [imaginary, negate()] with [-imaginary]. */
        IMAGINARY_NEGATE,
        /** Replace [complex, negate()] with [-complex]. */
        COMPLEX_NEGATE,

        /** Replace [real, conjugate()] with [~real]. */
        REAL_CONJUGATE,
        /** Replace [imaginary, conjugate()] with [~imaginary]. */
        IMAGINARY_CONJUGATE,
        /** Replace [complex, conjugate()] with [~complex]. */
        COMPLEX_CONJUGATE,

        /** Replace [real, imaginary, add()] with [complex], where complex = real + imaginary. */
        REAL_IMAGINARY_ADD,
        /** Replace [real, imaginary, subtract()] with [complex], where complex = real - imaginary. */
        REAL_IMAGINARY_SUBTRACT,

    }

}
