/*-
 * Copyright (c) 2008-2010, Oleg Estekhin
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

package oe.assertions;

/** A logic statement that is true if the value satisfies a predicate. */
public final class Statement<T> {

    /**
     * Returns a builder for a statement with the specified input value.
     *
     * @param value the input value
     *
     * @return a statement builder
     */
    public static <T> StatementDescriptionBuilder<T> value( T value ) {
        return new StatementDescriptionBuilder<T>( value, "the actual value" );
    }


    private final T value;

    private final String valueDescription;

    private final Predicate<? super T> predicate;


    /**
     * Creates a new statement.
     *
     * @param value the input value
     * @param valueDescription the description of the input value
     * @param predicate the predicate
     *
     * @throws IllegalArgumentException if {@code valueDescription} or {@code predicate} is {@code null}
     */
    Statement( T value, String valueDescription, Predicate<? super T> predicate ) {
        if ( valueDescription == null ) {
            throw new IllegalArgumentException( "valueDescription is null" );
        }
        if ( predicate == null ) {
            throw new IllegalArgumentException( "predicate is null" );
        }
        this.value = value;
        this.valueDescription = valueDescription;
        this.predicate = predicate;
    }


    /**
     * Checks this statement.
     *
     * @return the result of this statement
     */
    public boolean check() {
        return predicate.check( value );
    }

    /**
     * Appends a description of this statement to the specified message.
     *
     * @param message the object to append the description of the predicate
     *
     * @throws NullPointerException if {@code message} is {@code null}
     */
    public void describe( Message message ) {
        message.indent().append( valueDescription ).append( ": " ).formatValue( value ).eol();
        message.indent().append( "the expected value" ).append( ": " );
        predicate.describe( value, message );
        message.eol();
    }

}
