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

/** Evaluates to {@code true} if the input value is close to the target value. */
public final class IsCloseTo<T extends Number> extends BinaryFunction<T, Boolean> implements Predicate<T> {

    private final T target;


    /**
     * Creates a new predicate.
     *
     * @param target the target value
     *
     * @throws IllegalArgumentException if {@code target} is {@code null}
     */
    public IsCloseTo( T target ) {
        if ( target == null ) {
            throw new IllegalArgumentException( "target is null" );
        }
        this.target = target;
    }


    /**
     * Returns a predicate that evaluates to {@code true} if the input value is close to the target value using the ulp
     * distance between their floating-point representations.
     *
     * @param tolerance the tolerance
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code tolerance} is negative
     */
    public Predicate<T> byUlpDifference( long tolerance ) {
        return new IsCloseToByUlpDifference<T>( target, tolerance );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is close to the target value using the
     * absolute difference between them, that is if {@code |input - target| <= tolerance}.
     *
     * @param tolerance the tolerance
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code tolerance} is {@code null}
     * @throws IllegalArgumentException if {@code tolerance} is negative or not a finite number
     */
    public Predicate<T> byAbsoluteDifference( T tolerance ) {
        return new IsCloseToByAbsoluteDifference<T>( target, tolerance );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is close to the target value using the
     * relative difference between them, that is if either {@code |(input - target) / input| <= tolerance} or {@code
     * |(input - target) / target| <= tolerance}.
     *
     * @param tolerance the tolerance
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code tolerance} is negative or not a finite number
     */
    public Predicate<T> byRelativeDifference( double tolerance ) {
        return new IsCloseToByRelativeDifference<T>( target, tolerance, false );
    }

    /**
     * Returns a predicate that evaluates to {@code true} if the input value is close to the target value using the
     * strict relative difference between them, that is if both {@code |(input - target) / input| <= tolerance} and
     * {@code |(input - target) / target| <= tolerance}.
     *
     * @param tolerance the tolerance
     *
     * @return a new predicate
     *
     * @throws IllegalArgumentException if {@code tolerance} is negative or not a finite number
     */
    public Predicate<T> byStrictRelativeDifference( double tolerance ) {
        return new IsCloseToByRelativeDifference<T>( target, tolerance, true );
    }


    @Override
    public boolean check( T input ) {
        return calculate( input, target );
    }

    @Override
    public void describe( T input, Message message ) {
        message.append( "is close to" ).append( ' ' ).formatValue( target );
    }

    @Override
    protected Boolean calculateBigDecimal( BigDecimal input, BigDecimal target ) {
        return input.compareTo( target ) == 0;
    }

    @Override
    protected Boolean calculateBigInteger( BigInteger input, BigInteger target ) {
        return input.compareTo( target ) == 0;
    }

    @Override
    protected Boolean calculateDouble( double input, double target ) {
        return Numbers.isFinite( input ) && Numbers.isFinite( target )
                && Double.compare( input, target ) == 0;
    }

    @Override
    protected Boolean calculateFloat( float input, float target ) {
        return Numbers.isFinite( input ) && Numbers.isFinite( target )
                && Float.compare( input, target ) == 0;
    }

    @Override
    protected Boolean calculateLong( long input, long target ) {
        return input == target;
    }

    @Override
    protected Boolean calculateNumberUnknown( Number input, Number target ) {
        return input.equals( target );
    }

    @Override
    protected Boolean calculateNumberUnsafe( Number input, Number target ) {
        return false;
    }

}
