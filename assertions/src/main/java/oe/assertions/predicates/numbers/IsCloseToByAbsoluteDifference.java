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
 * Evaluates to {@code true} if the input value is close to the target value by the absolute difference between them,
 * that is if {@code |input - target| <= tolerance}.
 */
final class IsCloseToByAbsoluteDifference<T extends Number> extends BinaryFunction<T, Boolean> implements Predicate<T> {

    private final T target;

    private final T tolerance;


    /**
     * Creates a new predicate.
     *
     * @param target the target value
     * @param tolerance the tolerance
     *
     * @throws IllegalArgumentException if {@code target} is {@code null}
     * @throws IllegalArgumentException if {@code tolerance} is {@code null}
     * @throws IllegalArgumentException if {@code tolerance} is negative or not a finite number
     */
    IsCloseToByAbsoluteDifference( T target, T tolerance ) {
        if ( target == null ) {
            throw new IllegalArgumentException( "target is null" );
        }
        if ( tolerance == null ) {
            throw new IllegalArgumentException( "tolerance is null" );
        }
        if ( tolerance instanceof BigDecimal ) {
            if ( ( ( BigDecimal ) tolerance ).signum() < 0 ) {
                throw new IllegalArgumentException( "tolerance is negative" );
            }
        } else if ( tolerance instanceof BigInteger ) {
            if ( ( ( BigInteger ) tolerance ).signum() < 0 ) {
                throw new IllegalArgumentException( "tolerance is negative" );
            }
        } else if ( tolerance instanceof Double || tolerance instanceof Float ) {
            double d = tolerance.doubleValue();
            if ( Double.isNaN( d ) || Double.isInfinite( d ) ) {
                throw new IllegalArgumentException( "tolerance is not a finite number" );
            }
            if ( Double.compare( d, 0.0 ) < 0 ) {
                throw new IllegalArgumentException( "tolerance is negative" );
            }
        } else {
            if ( tolerance.longValue() < 0L ) {
                throw new IllegalArgumentException( "tolerance is negative" );
            }
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
                .append( ' ' ).append( "by the absolute difference of" )
                .append( ' ' ).format( "%s", tolerance );
    }


    @Override
    protected Boolean calculateBigDecimal( BigDecimal input, BigDecimal target ) {
        return input.subtract( target ).abs().compareTo( ( BigDecimal ) tolerance ) <= 0;
    }

    @Override
    protected Boolean calculateBigInteger( BigInteger input, BigInteger target ) {
        return input.subtract( target ).abs().compareTo( ( BigInteger ) tolerance ) <= 0;
    }

    @Override
    protected Boolean calculateDouble( double input, double target ) {
        return Math.abs( input - target ) <= tolerance.doubleValue();
    }

    @Override
    protected Boolean calculateFloat( float input, float target ) {
        return Math.abs( input - target ) <= tolerance.floatValue();
    }

    @Override
    protected Boolean calculateLong( long input, long target ) {
        long difference;
        if ( target <= 0L ) {
            difference = Long.MAX_VALUE + target >= input ? input - target : Long.MIN_VALUE;
        } else {
            difference = Long.MIN_VALUE + target <= input ? input - target : Long.MIN_VALUE;
        }
        return -Math.abs( difference ) >= -tolerance.longValue();
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
