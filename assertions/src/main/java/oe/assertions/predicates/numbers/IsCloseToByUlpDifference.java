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

/**
 * Evaluates to {@code true} if the input value is close to the target value by the ulp distance between their
 * floating-point representations.
 */
final class IsCloseToByUlpDifference<T extends Number> extends BinaryFunction<T, Boolean> implements Predicate<T> {

    private final T target;

    private final long tolerance;


    /**
     * Creates a new predicate.
     *
     * @param target the target value
     * @param tolerance the tolerance
     *
     * @throws IllegalArgumentException if {@code target} is {@code null}
     * @throws IllegalArgumentException if {@code tolerance} is negative
     */
    IsCloseToByUlpDifference( T target, long tolerance ) {
        if ( target == null ) {
            throw new IllegalArgumentException( "target is null" );
        }
        if ( tolerance < 0L ) {
            throw new IllegalArgumentException( "tolerance is negative" );
        }
        this.target = target;
        this.tolerance = tolerance;
    }


    @Override
    public boolean check( T input ) {
        return calculate( input, target );
    }

    @Override
    public void describe( T input, Message message ) {
        message.append( "is close to" ).append( ' ' ).formatValue( target )
                .append( ' ' ).append( "by the ulp difference of" )
                .append( ' ' ).format( "%s", tolerance );
    }


    @Override
    protected Boolean calculateBigDecimal( BigDecimal input, BigDecimal target ) {
        BigDecimal ulp = input.ulp().min( target.ulp() );
        BigDecimal difference = input.subtract( target ).abs();
        return difference.compareTo( ulp.multiply( BigDecimal.valueOf( tolerance ) ) ) <= 0;
    }

    @Override
    protected Boolean calculateBigInteger( BigInteger input, BigInteger target ) {
        return input.subtract( target ).abs().compareTo( BigInteger.valueOf( tolerance ) ) <= 0;
    }

    @Override
    protected Boolean calculateDouble( double input, double target ) {
        return Numbers.isFinite( input ) && Numbers.isFinite( target )
                && calculateLong( Double.doubleToLongBits( input ), Double.doubleToLongBits( target ) );
    }

    @Override
    protected Boolean calculateFloat( float input, float target ) {
        return Numbers.isFinite( input ) && Numbers.isFinite( target )
                && calculateLong( Float.floatToIntBits( input ), Float.floatToIntBits( target ) );
    }

    @Override
    protected Boolean calculateLong( long input, long target ) {
        long difference;
        if ( target <= 0L ) {
            difference = Long.MAX_VALUE + target >= input ? input - target : Long.MIN_VALUE;
        } else {
            difference = Long.MIN_VALUE + target <= input ? input - target : Long.MIN_VALUE;
        }
        return -Math.abs( difference ) >= -tolerance;
    }

    @Override
    protected Boolean calculateNumberUnknown( Number input, Number target ) {
        return false;
    }

    @Override
    protected Boolean calculateNumberUnsafe( Number input, Number target ) {
        return false;
    }

}
