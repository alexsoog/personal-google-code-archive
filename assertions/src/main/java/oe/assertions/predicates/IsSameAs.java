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

package oe.assertions.predicates;

import oe.assertions.Message;
import oe.assertions.Predicate;

/** Evaluates to {@code true} if the input value is the same as the target value. */
public class IsSameAs implements Predicate<Object> {

    private final Object target;

    private final boolean direct;


    /**
     * Creates a new predicate.
     *
     * @param target the target value
     */
    public IsSameAs( Object target ) {
        this( target, true );
    }

    /**
     * Creates a new predicate.
     *
     * @param target the target value
     * @param direct if {@code false} then this predicate checks the inverted condition
     */
    public IsSameAs( Object target, boolean direct ) {
        this.target = target;
        this.direct = direct;
    }


    @Override
    public boolean check( Object input ) {
        boolean result;
        if ( input == target ) {
            result = true;
        } else if ( input == null || target == null ) {
            result = false;
        } else if ( input instanceof Number && target instanceof Number ) {
            result = input.equals( target );
        } else {
            result = false;
        }
        return direct ? result : !result;
    }

    @Override
    public void describe( Object input, Message message ) {
        message.append( direct ? "is same as" : "is not same as" ).append( ' ' ).formatValue( target );
    }

}
