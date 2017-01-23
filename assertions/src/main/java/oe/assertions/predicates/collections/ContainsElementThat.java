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

package oe.assertions.predicates.collections;

import oe.assertions.Message;
import oe.assertions.Predicate;

/** Evaluates to {@code true} if the input collection contains an element that satisfies the specified condition. */
public final class ContainsElementThat<E, T extends Iterable<? extends E>> implements Predicate<T> {

    private final Predicate<E> condition;


    /**
     * Creates a new predicate.
     *
     * @param condition the predicate that will be applied to collection elements
     *
     * @throws IllegalArgumentException if {@code condition} is {@code null}
     */
    public ContainsElementThat( Predicate<E> condition ) {
        if ( condition == null ) {
            throw new IllegalArgumentException( "condition is null" );
        }
        this.condition = condition;
    }


    @Override
    public boolean check( T input ) {
        if ( input == null ) {
            return false;
        }
        for ( E element : input ) {
            if ( condition.check( element ) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describe( T input, Message message ) {
        message.append( "contains element that" ).append( ':' ).append( ' ' );
        condition.describe( null, message );
    }

}
