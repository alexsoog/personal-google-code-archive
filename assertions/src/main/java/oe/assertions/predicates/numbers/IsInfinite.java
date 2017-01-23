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

package oe.assertions.predicates.numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

import oe.assertions.Message;
import oe.assertions.Predicate;

/** Evaluates to {@code true} if the input value is an infinite number. */
public final class IsInfinite<T extends Number> extends UnaryFunction<T, Boolean> implements Predicate<T> {

    /** Creates a new predicate. */
    public IsInfinite() {
    }


    @Override
    public boolean check( T input ) {
        return calculate( input );
    }

    @Override
    public void describe( T input, Message message ) {
        message.append( "is infinite" );
    }


    @Override
    protected Boolean calculateBigDecimal( BigDecimal input ) {
        return false;
    }

    @Override
    protected Boolean calculateBigInteger( BigInteger input ) {
        return false;
    }

    @Override
    protected Boolean calculateDouble( double input ) {
        return Double.isInfinite( input );
    }

    @Override
    protected Boolean calculateFloat( float input ) {
        return Float.isInfinite( input );
    }

    @Override
    protected Boolean calculateLong( long input ) {
        return false;
    }

    @Override
    protected Boolean calculateNumberUnknown( Number input ) {
        return false;
    }

    @Override
    protected Boolean calculateNumberUnsafe( Number input ) {
        return false;
    }

}
