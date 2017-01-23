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

/** Used in the formatting process to represent values on the stack. */
final class FormatToken {

    private final String value;

    private final Associativity associativity;

    private final int precedence;


    /**
     * Creates a new {@code FormatToken} object.
     *
     * @param value the string representation
     * @param associativity the associativity of the operation that created that token
     * @param precedence the precedence of the operation that created that token
     *
     * @throws IllegalArgumentException if {@code value} or {@code associativity} is {@code null}
     */
    FormatToken( String value, Associativity associativity, int precedence ) {
        if ( value == null ) {
            throw new IllegalArgumentException( "value is null" );
        }
        if ( associativity == null ) {
            throw new IllegalArgumentException( "associativity is null" );
        }
        this.value = value;
        this.associativity = associativity;
        this.precedence = precedence;
    }


    public String getValue() {
        return value;
    }

    public Associativity getAssociativity() {
        return associativity;
    }

    public int getPrecedence() {
        return precedence;
    }

}
