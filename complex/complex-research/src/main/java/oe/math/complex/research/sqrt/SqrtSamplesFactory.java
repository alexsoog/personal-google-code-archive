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

package oe.math.complex.research.sqrt;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Iterator;

import oe.math.complex.research.util.AbstractRandomSamplesIterator;
import oe.math.complex.research.util.ArraySamplesIterator;
import oe.math.complex.research.util.BigDecimalMath;
import oe.math.complex.research.util.RandomDouble;

public final class SqrtSamplesFactory {

    /**
     * Creates a new special samples iterator.
     *
     * @return the new iterator
     */
    public static Iterator<double[]> createSpecialSamplesIterator() {
        return new ArraySamplesIterator( "samples with special values (infinity and NaN)", new double[][] {
                // (any, infinite)
                { 0.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY },
                { -0.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY },
                { 1.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY },
                { -1.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY },
                { Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY },
                { Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY },
                { Double.NaN, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY },

                { 0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY },
                { -0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY },
                { 1.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY },
                { -1.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY },
                { Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY },
                { Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY },
                { Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY },

                // (nan, finite)
                { Double.NaN, 0.0, Double.NaN, Double.NaN },
                { Double.NaN, -0.0, Double.NaN, Double.NaN },
                { Double.NaN, 1.0, Double.NaN, Double.NaN },
                { Double.NaN, -1.0, Double.NaN, Double.NaN },

                // (finite, nan)
                { 0.0, Double.NaN, Double.NaN, Double.NaN },
                { -0.0, Double.NaN, Double.NaN, Double.NaN },
                { 1.0, Double.NaN, Double.NaN, Double.NaN },
                { -1.0, Double.NaN, Double.NaN, Double.NaN },

                // (nan, nan)
                { Double.NaN, Double.NaN, Double.NaN, Double.NaN },

                // (+infinite, finite)
                { Double.POSITIVE_INFINITY, 0.0, Double.POSITIVE_INFINITY, 0.0 },
                { Double.POSITIVE_INFINITY, -0.0, Double.POSITIVE_INFINITY, -0.0 },
                { Double.POSITIVE_INFINITY, 1.0, Double.POSITIVE_INFINITY, 0.0 },
                { Double.POSITIVE_INFINITY, -1.0, Double.POSITIVE_INFINITY, -0.0 },

                // (+infinite, nan)
                { Double.POSITIVE_INFINITY, Double.NaN, Double.POSITIVE_INFINITY, Double.NaN },
                { Double.POSITIVE_INFINITY, Double.longBitsToDouble( 0xfff8000000000000L ), Double.POSITIVE_INFINITY, Double.NaN },

                // (-infinite, finite)
                { Double.NEGATIVE_INFINITY, 0.0, 0.0, Double.POSITIVE_INFINITY },
                { Double.NEGATIVE_INFINITY, -0.0, 0.0, Double.NEGATIVE_INFINITY },
                { Double.NEGATIVE_INFINITY, 1.0, 0.0, Double.POSITIVE_INFINITY },
                { Double.NEGATIVE_INFINITY, -1.0, 0.0, Double.NEGATIVE_INFINITY },

                // (-infinite, nan)
                { Double.NEGATIVE_INFINITY, Double.NaN, Double.NaN, Double.POSITIVE_INFINITY },
                { Double.NEGATIVE_INFINITY, Double.longBitsToDouble( 0xfff8000000000000L ), Double.NaN, Double.NEGATIVE_INFINITY },
        } );
    }


    /**
     * Creates a new random sample {a, b, e, f} where sqrt(a, b) = (e, f). The absolute values of argument's components
     * will be in the specified bounds.
     * <p/>
     * This method calculates result from the random argument using {@link BigDecimal} arithmetic.
     *
     * @param absMin the lower bound
     * @param absMax the upper bound
     *
     * @return the new sample
     */
    public static double[] createRandomSample( double absMin, double absMax ) {
        double a = RandomDouble.randomFiniteDoubleWithAbsBounds( absMin, absMax );
        double b = RandomDouble.randomFiniteDoubleWithAbsBounds( absMin, absMax );
        double[] result = sqrt( a, b );
        double e = result[ 0 ];
        double f = result[ 1 ];
        return new double[] { a, b, e, f };
    }

    private static double[] sqrt( double a, double b ) {
        BigDecimal[] result = sqrt( BigDecimal.valueOf( a ), BigDecimal.valueOf( b ), new MathContext( 1024, RoundingMode.HALF_EVEN ) );
        double e = result[ 0 ].doubleValue();
        double f = result[ 1 ].doubleValue();
        return new double[] { e, f };
    }

    private static BigDecimal[] sqrt( BigDecimal a, BigDecimal b, MathContext context ) {
        BigDecimal a2 = a.multiply( a, context );
        BigDecimal b2 = b.multiply( b, context );
        BigDecimal r2 = a2.add( b2, context );
        BigDecimal r = BigDecimalMath.sqrt( r2, context );

        BigDecimal e2 = r.add( a, context ).divide( BigDecimalMath.TWO, context );
        BigDecimal e = BigDecimalMath.sqrt( e2, context );

        BigDecimal f2 = r.subtract( a, context ).divide( BigDecimalMath.TWO, context );
        BigDecimal f = BigDecimalMath.sqrt( f2, context );
        if ( f.signum() != 0 && f.signum() != b.signum() ) {
            f = f.negate( context );
        }

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


    /**
     * Creates a new random sample {a, b, e, f} where sqrt(a, b) = (e, f). The absolute values of argument's components
     * will be in the specified bounds.
     * <p/>
     * This method generates exact argument from the random result.
     *
     * @param absMin the lower bound
     * @param absMax the upper bound
     *
     * @return the new sample
     */
    public static double[] createExactRandomSample( double absMin, double absMax ) {
        double a;
        double b;
        double e;
        double f;
        do {
            e = RandomDouble.randomFiniteDouble();
            f = RandomDouble.randomFiniteDouble();
            // removing 28 of the trailing bits and the sign bit of e
            e = Double.longBitsToDouble( Double.doubleToLongBits( e ) & 0x7ffffffff0000000L );
            f = Double.longBitsToDouble( Double.doubleToLongBits( f ) & 0xfffffffff0000000L );
            // e^2, f^2 and 2ef are exact
            a = e * e - f * f;
            b = 2.0 * e * f;
        } while ( a != a || Double.isInfinite( a ) || Math.abs( a ) < absMin || Math.abs( a ) > absMax
                || b != b || Double.isInfinite( b ) || Math.abs( b ) < absMin || Math.abs( b ) > absMax );
        return new double[] { a, b, e, f };
    }

    /**
     * Creates a new exact random samples iterator. The argument's components of all samples will be in the specified
     * bounds.
     *
     * @param absMin the lower bound
     * @param absMax the upper bound
     * @param count the number of samples
     *
     * @return the new iterator
     *
     * @see #createExactRandomSample(double, double)
     */
    public static Iterator<double[]> createExactRandomSamplesIterator( final double absMin, final double absMax, int count ) {
        return new AbstractRandomSamplesIterator( String.format( "exact random samples with the absolute values of argument's components in the [%s, %s]", absMin, absMax ), count ) {
            @Override
            protected double[] createSample() {
                return createExactRandomSample( absMin, absMax );
            }
        };
    }

    private SqrtSamplesFactory() {
    }

}
