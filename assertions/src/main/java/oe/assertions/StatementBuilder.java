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

/**
 * Builds the statement from an input value and a set of predicates.
 *
 * @param <T> the type of the input value
 */
public class StatementBuilder<T> {

    protected final T value;

    protected String valueDescription;


    /**
     * Creates a new builder with the specified input value.
     *
     * @param value the input value
     * @param valueDescription the description of the input value
     *
     * @throws IllegalArgumentException if {@code valueDescription} is {@code null}
     */
    StatementBuilder( T value, String valueDescription ) {
        if ( valueDescription == null ) {
            throw new IllegalArgumentException( "valueDescription is null" );
        }
        this.value = value;
        this.valueDescription = valueDescription;
    }


    /**
     * Creates a new statement from the current builder state and the specified predicate.
     *
     * @param predicate a predicate to be checked
     *
     * @return a new statement
     *
     * @throws IllegalArgumentException if {@code predicate} is {@code null}
     */
    public Statement<T> satisfies( Predicate<? super T> predicate ) {
        return new Statement<T>( value, valueDescription, predicate );
    }


    /**
     * Creates a new statement from the current builder state and the specified predicates. The statement is true if the
     * input value satisfies all of the specified predicates.
     *
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     *
     * @return a new statement
     *
     * @throws IllegalArgumentException if {@code first} or {@code second} is {@code null}
     */
    public Statement<T> satisfiesAllOf( Predicate<? super T> first, Predicate<? super T> second ) {
        return satisfies( Predicates.<T>satisfiesAllOf( first, second ) );
    }

    /**
     * Creates a new statement from the current builder state and the specified predicates. The statement is true if the
     * input value satisfies all of the specified predicates.
     *
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     * @param third another predicate to be checked
     *
     * @return a new statement
     *
     * @throws IllegalArgumentException if {@code first}, {@code second} or {@code third} is {@code null}
     */
    public Statement<T> satisfiesAllOf( Predicate<? super T> first, Predicate<? super T> second, Predicate<? super T> third ) {
        return satisfies( Predicates.<T>satisfiesAllOf( first, second, third ) );
    }

    /**
     * Creates a new statement from the current builder state and the specified predicates. The statement is true if the
     * input value satisfies all of the specified predicates.
     *
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     * @param rest the remaining predicates to be checked
     *
     * @return a new statement
     *
     * @throws IllegalArgumentException if {@code first}, {@code second} or {@code rest} is {@code null}, or if {@code
     * rest} contains {@code null} elements
     */
    public Statement<T> satisfiesAllOf( Predicate<? super T> first, Predicate<? super T> second, Predicate<? super T>... rest ) {
        return satisfies( Predicates.<T>satisfiesAllOf( first, second, rest ) );
    }


    /**
     * Creates a new statement from the current builder state and the specified predicates. The statement is true if the
     * input value satisfies any of the specified predicates.
     *
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     *
     * @return a new statement
     *
     * @throws IllegalArgumentException if {@code first} or {@code second} is {@code null}
     */
    public Statement<T> satisfiesAnyOf( Predicate<? super T> first, Predicate<? super T> second ) {
        return satisfies( Predicates.<T>satisfiesAnyOf( first, second ) );
    }

    /**
     * Creates a new statement from the current builder state and the specified predicates. The statement is true if the
     * input value satisfies any of the specified predicates.
     *
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     * @param third another predicate to be checked
     *
     * @return a new statement
     *
     * @throws IllegalArgumentException if {@code first}, {@code second} or {@code third} is {@code null}
     */
    public Statement<T> satisfiesAnyOf( Predicate<? super T> first, Predicate<? super T> second, Predicate<? super T> third ) {
        return satisfies( Predicates.<T>satisfiesAnyOf( first, second, third ) );
    }

    /**
     * Creates a new statement from the current builder state and the specified predicates. The statement is true if the
     * input value satisfies any of the specified predicates.
     *
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     * @param rest the remaining predicates to be checked
     *
     * @return a new statement
     *
     * @throws IllegalArgumentException if {@code first}, {@code second} or {@code rest} is {@code null}, or if {@code
     * rest} contains {@code null} elements
     */
    public Statement<T> satisfiesAnyOf( Predicate<? super T> first, Predicate<? super T> second, Predicate<? super T>... rest ) {
        return satisfies( Predicates.<T>satisfiesAnyOf( first, second, rest ) );
    }

}
