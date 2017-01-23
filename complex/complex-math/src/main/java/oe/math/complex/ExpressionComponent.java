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

/** The base class for expression components. */
abstract class ExpressionComponent {

    protected final Type type;

    protected final Associativity associativity;

    protected final int precedence;

    protected final int operandsCount;

    protected final int resultsCount;


    /**
     * Creates a new {@code ExpressionComponent} object.
     *
     * @param type the type of this component
     * @param associativity the associativity of this component
     * @param precedence the precedence of this component
     * @param operandsCount the number of operands required by this component
     * @param resultsCount the number of return values produced by this component
     *
     * @throws IllegalArgumentException if {@code type} or {@code associativity} is {@code null}
     * @throws IllegalArgumentException if {@code operandsCount} or {@code resultsCount} is negative
     */
    protected ExpressionComponent( Type type, Associativity associativity, int precedence, int operandsCount, int resultsCount ) {
        if ( type == null ) {
            throw new IllegalArgumentException( "type is null" );
        }
        if ( associativity == null ) {
            throw new IllegalArgumentException( "associativity is null" );
        }
        if ( operandsCount < 0 ) {
            throw new IllegalArgumentException( "operandsCount is negative" );
        }
        if ( resultsCount < 0 ) {
            throw new IllegalArgumentException( "resultsCount is negative" );
        }
        this.type = type;
        this.associativity = associativity;
        this.precedence = precedence;
        this.operandsCount = operandsCount;
        this.resultsCount = resultsCount;
    }


    public Type getType() {
        return type;
    }

    public Associativity getAssociativity() {
        return associativity;
    }

    public int getPrecedence() {
        return precedence;
    }

    public int getOperandsCount() {
        return operandsCount;
    }

    public int getResultsCount() {
        return resultsCount;
    }


    public abstract String getInfixSymbol();

    public abstract String getPostfixSymbol();


    /**
     * Applies calculation step represented by this component to the specified stack.
     *
     * @param stack the calculation stack
     * @param context the calculation context
     */
    public abstract void calculate( Deque<Complex> stack, CalculationContext context );

    /**
     * Applies formatting step represented by this component to the specified stack.
     *
     * @param stack the formatting stack
     * @param context the formatting context
     */
    public abstract void format( Deque<FormatToken> stack, FormatContext context );

    /**
     * Applies parsing step represented by this component to the specified stack.
     *
     * @param stack the parsing stack
     * @param output the parsing output
     * @param context the parsing context
     */
    public abstract void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context );


    /** Defines possible types of expression components. */
    public enum Type {

        COMPLEX,
        VARIABLE,
        FUNCTION,
        OPERATOR,
        LEFT_PARENTHESIS,
        RIGHT_PARENTHESIS,

    }

}
