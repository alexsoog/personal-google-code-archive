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

import java.util.Deque;

/** An expression component that represents a complex constant. */
final class ComplexComponent extends ExpressionComponent {

    private final Complex value;


    /**
     * Creates a new {@code ComplexComponent} object.
     *
     * @param value the complex value
     *
     * @throws IllegalArgumentException if {@code value} is {@code null}
     */
    ComplexComponent( Complex value ) {
        super( Type.COMPLEX, Associativity.LEFT, Integer.MAX_VALUE, 0, 1 );
        if ( value == null ) {
            throw new IllegalArgumentException( "value is null" );
        }
        this.value = value;
    }


    public Complex getValue() {
        return value;
    }


    @Override
    public String getInfixSymbol() {
        return value.toString();
    }

    @Override
    public String getPostfixSymbol() {
        return value.toString();
    }


    @Override
    public void calculate( Deque<Complex> stack, CalculationContext context ) {
        stack.push( value );
    }

    @Override
    public void format( Deque<FormatToken> stack, FormatContext context ) {
        Associativity a;
        int p;
        if ( value.hasRealPart() ) {
            if ( value.hasImaginaryPart() ) {
                a = FunctionComponents.ADD.getAssociativity();
                p = Integer.MIN_VALUE; // forcing parentheses around complex value
            } else {
                if ( Double.compare( value.getRe(), 0.0 ) < 0 ) {
                    a = FunctionComponents.NEGATE.getAssociativity();
                    p = FunctionComponents.NEGATE.getPrecedence();
                } else {
                    a = associativity;
                    p = precedence;
                }
            }
        } else {
            // assert value.hasImaginaryPart();
            if ( Double.compare( value.getIm(), 0.0 ) < 0 ) {
                a = FunctionComponents.NEGATE.getAssociativity();
                p = FunctionComponents.NEGATE.getPrecedence();
            } else {
                a = associativity;
                p = precedence;
            }
        }
        stack.push( new FormatToken( context.getComplexFormat().format( value ), a, p ) );
    }

    @Override
    public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
        Type previousType = context.getPreviousType();
        if ( previousType == Type.LEFT_PARENTHESIS || previousType == Type.OPERATOR ) {
            output.append( this );
        } else {
            throw new IllegalStateException( type + " follows " + previousType );
        }
    }


    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        } else if ( obj instanceof ComplexComponent ) {
            ComplexComponent component = ( ComplexComponent ) obj;
            return value.equals( component.value );
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
