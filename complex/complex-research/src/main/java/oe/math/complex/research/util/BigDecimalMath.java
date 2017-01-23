/*-
 * Copyright (c) 2009-2010, Oleg Estekhin
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

package oe.math.complex.research.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Deque;
import java.util.LinkedList;

public final class BigDecimalMath {

    public static final BigDecimal TWO = BigDecimal.valueOf( 2 );


    public static BigDecimal sqrt( BigDecimal value, MathContext context ) {
        /*
        Implements square root by coupled Newton iteration.
         */
        if ( value.signum() < 0 ) {
            throw new ArithmeticException( "value is negative" );
        }
        if ( context.getPrecision() == 0 ) {
            throw new IllegalArgumentException( "context has infinite precision" );
        }
        // approximation
        BigInteger unscaled = value.unscaledValue();
        int bitScale;
        int bitLength = unscaled.bitLength();
        long longTemp;
        if ( bitLength > 53 ) {
            bitScale = bitLength - 53;
            longTemp = unscaled.shiftRight( bitScale ).longValue();
        } else {
            bitScale = 0;
            longTemp = unscaled.longValue();
        }
        double doubleApproximation = Math.sqrt( ( double ) longTemp );
        double correctionScale = 1.0;
        BigDecimal biScale = new BigDecimal( BigInteger.ONE.shiftLeft( bitScale / 2 ) );
        if ( bitScale % 2 != 0 ) {
            correctionScale *= Math.sqrt( 2.0 );
        }
        if ( value.scale() % 2 != 0 ) {
            correctionScale *= Math.sqrt( 10.0 );
        }
        BigDecimal decimalApproximation = BigDecimal.valueOf( doubleApproximation * correctionScale ).multiply( biScale ).scaleByPowerOfTen( -value.scale() / 2 );
        if ( decimalApproximation.compareTo( BigDecimal.ZERO ) == 0 ) {
            return BigDecimal.ZERO;
        }
        // precision
        Deque<Integer> precision = new LinkedList<Integer>();
        int tempPrecision = context.getPrecision();
        do {
            precision.push( tempPrecision + 2 );
            tempPrecision /= 2;
        } while ( tempPrecision > 16 );
        MathContext currentContext = new MathContext( precision.peek(), RoundingMode.HALF_EVEN );
        // iteration
        BigDecimal previous;
        BigDecimal current = decimalApproximation;
        BigDecimal reciprocal = BigDecimal.ONE.divide( current.multiply( TWO ), currentContext );
        do {
            if ( precision.size() > 0 ) {
                currentContext = new MathContext( precision.pop(), RoundingMode.HALF_EVEN );
            }
            previous = current;
            BigDecimal reciprocalTemp = BigDecimal.ONE.subtract( current.multiply( reciprocal ).multiply( TWO ), currentContext );
            reciprocal = reciprocal.add( reciprocalTemp.multiply( reciprocal ), currentContext );
            BigDecimal currentTemp = value.subtract( current.multiply( current ), currentContext );
            current = current.add( currentTemp.multiply( reciprocal ), currentContext );
        } while ( precision.size() > 0 || current.compareTo( previous ) != 0 );
        return current.round( context );
    }

    public static BigDecimal hypot( BigDecimal x, BigDecimal y, MathContext context ) {
        BigDecimal x2 = x.multiply( x, context );
        BigDecimal y2 = y.multiply( y, context );

        BigDecimal z2 = x2.add( y2, context );
        BigDecimal z = sqrt( z2, context );

        return z;
    }

    private BigDecimalMath() {
    }

}
