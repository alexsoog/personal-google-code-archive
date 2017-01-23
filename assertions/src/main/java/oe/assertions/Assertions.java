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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static oe.assertions.Statement.value;

/** Contains static methods that evaluate assertion statements. */
public final class Assertions {

    /**
     * Asserts that the input value satisfies the specified predicate.
     *
     * @param value an input value
     * @param predicate a predicate to be checked
     *
     * @throws IllegalArgumentException if {@code predicate} is {@code null}
     * @throws AssertionError if assertion fails
     */
    public static <T> void assertThat( T value, Predicate<? super T> predicate ) {
        assertThat( value( value ).satisfies( predicate ) );
    }

    /**
     * Asserts that the input value satisfies all of the specified predicates.
     *
     * @param value an input value
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     *
     * @throws IllegalArgumentException if {@code first} or {@code second} is {@code null}
     * @throws AssertionError if assertion fails
     */
    public static <T> void assertThat( T value, Predicate<? super T> first, Predicate<? super T> second ) {
        assertThat( value( value ).satisfiesAllOf( first, second ) );
    }

    /**
     * Asserts that the input value satisfies all of the specified predicates.
     *
     * @param value an input value
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     * @param third another predicate to be checked
     *
     * @throws IllegalArgumentException if {@code first}, {@code second} or {@code third} is {@code null}
     * @throws AssertionError if assertion fails
     */
    public static <T> void assertThat( T value, Predicate<? super T> first, Predicate<? super T> second, Predicate<? super T> third ) {
        assertThat( value( value ).satisfiesAllOf( first, second, third ) );
    }

    /**
     * Asserts that the input value satisfies all of the specified predicates.
     *
     * @param value an input value
     * @param first a predicate to be checked
     * @param second another predicate to be checked
     * @param rest the remaining predicates to be checked
     *
     * @throws IllegalArgumentException if {@code first} or {@code second} is {@code null}
     * @throws IllegalArgumentException if {@code rest} is {@code null} or contains {@code null} element
     * @throws AssertionError if assertion fails
     */
    public static <T> void assertThat( T value, Predicate<? super T> first, Predicate<? super T> second, Predicate<? super T>... rest ) {
        assertThat( value( value ).satisfiesAllOf( first, second, rest ) );
    }


    /**
     * Asserts that the specified statement is true.
     *
     * @param statement a statement to check
     *
     * @throws IllegalArgumentException if {@code statement} is {@code null}
     * @throws AssertionError if assertion fails
     */
    public static void assertThat( Statement<?> statement ) {
        if ( statement == null ) {
            throw new IllegalArgumentException( "statement is null" );
        }
        Assertion assertion = new Assertion( Collections.<Statement<?>>singletonList( statement ) );
        assertion.check();
    }

    /**
     * Asserts that all of the specified statements are true.
     *
     * @param first a statement to check
     * @param rest the remaining statements to check
     *
     * @throws IllegalArgumentException if {@code first} or {@code rest} is {@code null}, or if {@code rest} contains
     * {@code null} elements
     * @throws AssertionError if assertion fails
     */
    public static void assertThat( Statement<?> first, Statement<?>... rest ) {
        List<Statement<?>> list = new ArrayList<Statement<?>>( 1 + rest.length );
        list.add( first );
        list.addAll( Arrays.asList( rest ) );
        Assertion assertion = new Assertion( list );
        assertion.check();
    }


    private Assertions() {
    }

}
