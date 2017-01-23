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

package oe.math.complex.research.multiply;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Iterator;

import oe.math.complex.research.util.AbstractRandomSamplesIterator;
import oe.math.complex.research.util.RandomDouble;

public final class MultiplySamplesFactory {

    /**
     * Creates a new random sample {a, b, c, d, e, f} where (a, b) * (c, d) = (e, f). The absolute values of argument's
     * components will be in the specified bounds.
     * <p/>
     * This method calculates result from the random arguments using {@link BigDecimal} arithmetic.
     *
     * @param absMin the lower bound
     * @param absMax the upper bound
     *
     * @return the new sample
     */
    public static double[] createRandomSample( double absMin, double absMax ) {
        double a;
        double b;
        double c;
        double d;
        double e;
        double f;
        do {
            a = RandomDouble.randomFiniteDoubleWithAbsBounds( absMin, absMax );
            b = RandomDouble.randomFiniteDoubleWithAbsBounds( absMin, absMax );
            c = RandomDouble.randomFiniteDoubleWithAbsBounds( absMin, absMax );
            d = RandomDouble.randomFiniteDoubleWithAbsBounds( absMin, absMax );
            double[] result = multiply( a, b, c, d );
            e = result[ 0 ];
            f = result[ 1 ];
        } while ( e != e || f != f );
        return new double[] { a, b, c, d, e, f };
    }

    private static double[] multiply( double a, double b, double c, double d ) {
        BigDecimal[] result = multiply( BigDecimal.valueOf( a ), BigDecimal.valueOf( b ), BigDecimal.valueOf( c ), BigDecimal.valueOf( d ), MathContext.DECIMAL128 );
        double e = result[ 0 ].doubleValue();
        double f = result[ 1 ].doubleValue();
        return new double[] { e, f };
    }

    private static BigDecimal[] multiply( BigDecimal a, BigDecimal b, BigDecimal c, BigDecimal d, MathContext context ) {
        BigDecimal ac = a.multiply( c, context );
        BigDecimal bd = b.multiply( d, context );
        BigDecimal e = ac.subtract( bd, context );

        BigDecimal bc = b.multiply( c, context );
        BigDecimal ad = a.multiply( d, context );
        BigDecimal f = bc.add( ad, context );

        return new BigDecimal[] { e, f };
    }


    /**
     * Creates a new random samples iterator. The argument's components of all samples will be in the specified bounds.
     *
     * @param absMin the lower bound
     * @param absMax the upper bound
     * @param count the number of samples
     *
     * @return the new iterator
     *
     * @see #createRandomSample(double, double)
     */
    public static Iterator<double[]> createRandomSamplesIterator( final double absMin, final double absMax, int count ) {
        return new AbstractRandomSamplesIterator( String.format( "random samples with the absolute values of argument's components in the [%s, %s]", absMin, absMax ), count ) {
            @Override
            protected double[] createSample() {
                return createRandomSample( absMin, absMax );
            }
        };
    }


    private MultiplySamplesFactory() {
    }

}
