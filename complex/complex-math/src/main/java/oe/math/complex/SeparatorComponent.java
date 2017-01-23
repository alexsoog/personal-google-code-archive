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

/** An expression component that represents separators in the infix notation. */
abstract class SeparatorComponent extends ExpressionComponent {

    protected final String infixSymbol;


    /**
     * Creates a new {@code SeparatorComponent} object.
     *
     * @param type the type of this component
     * @param infixSymbol the symbol that represents this component in the infix notation
     *
     * @throws IllegalArgumentException if {@code infixSymbol} is {@code null}
     */
    protected SeparatorComponent( Type type, String infixSymbol ) {
        super( type, Associativity.LEFT, Integer.MAX_VALUE, Integer.MAX_VALUE, 0 );
        if ( infixSymbol == null ) {
            throw new IllegalArgumentException( "infixSymbol is null" );
        }
        this.infixSymbol = infixSymbol;
    }


    @Override
    public String getInfixSymbol() {
        return infixSymbol;
    }

    @Override
    public String getPostfixSymbol() {
        throw new AssertionError();
    }


    @Override
    public void calculate( Deque<Complex> stack, CalculationContext context ) {
        throw new AssertionError();
    }

    @Override
    public void format( Deque<FormatToken> stack, FormatContext context ) {
        throw new AssertionError();
    }

}
