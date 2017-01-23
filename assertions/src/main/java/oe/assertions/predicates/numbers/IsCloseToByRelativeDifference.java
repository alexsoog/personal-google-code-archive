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
 * Evaluates to {@code true} if the input value is close to the target value by the relative difference between them,
 * that is if either {@code |(input - target) / input| <= tolerance} or {@code |(input - target) / target| <=
 * tolerance}.
 */
final class IsCloseToByRelativeDifference<T extends Number> extends BinaryFunction<T, Boolean> implements Predicate<T> {

    private final T target;

    private final double tolerance;

    private final boolean strict;


    /**
     * Creates a new predicate.
     *
     * @param target the target value
     * @param tolerance the tolerance
     * @param strict if {@code true} then this predicates checks for strict relative difference
     *
     * @throws IllegalArgumentException if {@code target} is {@code null}
     * @throws IllegalArgumentException if {@code tolerance} is negative or not a finite number
     */
    IsCloseToByRelativeDifference( T target, double tolerance, boolean strict ) {
        if ( target == null ) {
            throw new IllegalArgumentException( "target is null" );
        }
        if ( Double.isNaN( tolerance ) || Double.isInfinite( tolerance ) ) {
            throw new IllegalArgumentException( "tolerance is not a finite number" );
        }
        if ( Double.compare( tolerance, 0.0 ) < 0 ) {
            throw new IllegalArgumentException( "tolerance is negative" );
        }
        this.target = target;
        this.tolerance = tolerance;
        this.strict = strict;
    }


    @Override
    public boolean check( T input ) {
        return calculate( input, target );
    }

    @Override
    public void describe( T input, Message message ) {
        message.append( "is close to" ).append( ' ' ).formatValue( target )
                .append( ' ' ).append( strict ? "by the strict relative difference of" : "by the relative difference of" )
                .append( ' ' ).format( "%s", tolerance );
    }


    @Override
    protected Boolean calculateBigDecimal( BigDecimal input, BigDecimal target ) {
        BigDecimal difference = input.subtract( target ).abs();
        BigDecimal toleranceAsBigDecimal = BigDecimal.valueOf( tolerance );
        boolean relativeToInput = difference.compareTo( toleranceAsBigDecimal.multiply( input ) ) <= 0;
        boolean relativeToTarget = difference.compareTo( toleranceAsBigDecimal.multiply( target ) ) <= 0;
        return strict ? relativeToInput && relativeToTarget : relativeToInput || relativeToTarget;
    }

    @Override
    protected Boolean calculateBigInteger( BigInteger input, BigInteger target ) {
        return calculateBigDecimal( new BigDecimal( input ), new BigDecimal( target ) );
    }

    @Override
    protected Boolean calculateDouble( double input, double target ) {
        boolean relativeToInput = Math.abs( input - target ) <= Math.abs( tolerance * input );
        boolean relativeToTarget = Math.abs( input - target ) <= Math.abs( tolerance * target );
        return strict ? relativeToInput && relativeToTarget : relativeToInput || relativeToTarget;
    }

    @Override
    protected Boolean calculateFloat( float input, float target ) {
        return calculateDouble( input, target );
    }

    @Override
    protected Boolean calculateLong( long input, long target ) {
        return calculateDouble( input, target );
    }

    @Override
    protected Boolean calculateNumberUnknown( Number input, Number target ) {
        return calculateLong( input.longValue(), target.longValue() );
    }

    @Override
    protected Boolean calculateNumberUnsafe( Number input, Number target ) {
        return false;
    }

}
