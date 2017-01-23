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

package oe.math.complex.research.abs;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Iterator;
import java.util.NoSuchElementException;

import oe.math.complex.research.util.AbstractRandomSamplesIterator;
import oe.math.complex.research.util.AbstractSamplesIterator;
import oe.math.complex.research.util.ArraySamplesIterator;
import oe.math.complex.research.util.BigDecimalMath;
import oe.math.complex.research.util.RandomDouble;

public final class AbsSamplesFactory {

    /**
     * Creates a new special samples iterator.
     *
     * @return the new iterator
     */
    public static Iterator<double[]> createSpecialSamplesIterator() {
        return new ArraySamplesIterator( "samples with special values (infinity and NaN)", new double[][] {
                // (inifinity, finite)
                { Double.POSITIVE_INFINITY, 0.0, Double.POSITIVE_INFINITY, 0.0 },
                { Double.POSITIVE_INFINITY, -0.0, Double.POSITIVE_INFINITY, 0.0 },
                { Double.POSITIVE_INFINITY, 1.0, Double.POSITIVE_INFINITY, 0.0 },
                { Double.POSITIVE_INFINITY, -1.0, Double.POSITIVE_INFINITY, 0.0 },
                { Double.POSITIVE_INFINITY, Double.MAX_VALUE, Double.POSITIVE_INFINITY, 0.0 },
                { Double.POSITIVE_INFINITY, -Double.MAX_VALUE, Double.POSITIVE_INFINITY, 0.0 },

                { Double.NEGATIVE_INFINITY, 0.0, Double.POSITIVE_INFINITY, 0.0 },
                { Double.NEGATIVE_INFINITY, -0.0, Double.POSITIVE_INFINITY, 0.0 },
                { Double.NEGATIVE_INFINITY, 1.0, Double.POSITIVE_INFINITY, 0.0 },
                { Double.NEGATIVE_INFINITY, -1.0, Double.POSITIVE_INFINITY, 0.0 },
                { Double.NEGATIVE_INFINITY, Double.MAX_VALUE, Double.POSITIVE_INFINITY, 0.0 },
                { Double.NEGATIVE_INFINITY, -Double.MAX_VALUE, Double.POSITIVE_INFINITY, 0.0 },

                // (finite, infinity)
                { 0.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },
                { -0.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },
                { 1.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },
                { -1.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },
                { Double.MAX_VALUE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },
                { -Double.MAX_VALUE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },

                { 0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },
                { -0.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },
                { 1.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },
                { -1.0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },
                { Double.MAX_VALUE, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },
                { -Double.MAX_VALUE, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },

                // (infinity, nan)
                { Double.POSITIVE_INFINITY, Double.NaN, Double.POSITIVE_INFINITY, 0.0 },
                { Double.NEGATIVE_INFINITY, Double.NaN, Double.POSITIVE_INFINITY, 0.0 },

                // (nan, infinity)
                { Double.NaN, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },
                { Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.0 },

                // (nan, finite)
                { Double.NaN, 0.0, Double.NaN, 0.0 },
                { Double.NaN, -0.0, Double.NaN, 0.0 },
                { Double.NaN, 1.0, Double.NaN, 0.0 },
                { Double.NaN, -1.0, Double.NaN, 0.0 },
                { Double.NaN, Double.MAX_VALUE, Double.NaN, 0.0 },
                { Double.NaN, -Double.MAX_VALUE, Double.NaN, 0.0 },

                // (finite, nan)
                { 0.0, Double.NaN, Double.NaN, 0.0 },
                { -0.0, Double.NaN, Double.NaN, 0.0 },
                { 1.0, Double.NaN, Double.NaN, 0.0 },
                { -1.0, Double.NaN, Double.NaN, 0.0 },
                { Double.MAX_VALUE, Double.NaN, Double.NaN, 0.0 },
                { -Double.MAX_VALUE, Double.NaN, Double.NaN, 0.0 },

                // (nan, nan)
                { Double.NaN, Double.NaN, Double.NaN, 0.0 },
        } );
    }


    /**
     * Creates a new random sample {a, b, e, f} where abs(a, b) = (e, f). The absolute values of argument's components
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
        BigDecimal ef = BigDecimalMath.hypot( BigDecimal.valueOf( a ), BigDecimal.valueOf( b ), MathContext.DECIMAL128 );
        double e = ef.doubleValue();
        double f = 0.0;
        return new double[] { a, b, e, f };
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
     * Creates a new pythagorean triple sample {a, b, e, f} where abs(a, b) = (e, f).
     *
     * @param value the base value
     *
     * @return the new sample
     */
    public static double[] createPythagoreanTripleSample( double value ) {
        // removing 3 trailing bits
        value = Double.longBitsToDouble( Double.doubleToLongBits( value ) & 0xfffffffffffffff8L );
        // 3*value, 4*value and 5*value are exact
        double a = value * 3.0;
        double b = value * 4.0;
        double e = value * 5.0;
        double f = 0.0;
        return new double[] { a, b, e, f };
    }

    /**
     * Creates a new pythagorean triple samples iterator. The triples are in the form of (3x, 4x, 5x) where x takes
     * values from {@code min} to {@code max} with the rate of {@code rate}.
     *
     * @param min the lower bound
     * @param max the upper bound
     * @param rate the change rate
     *
     * @return the new iterator
     *
     * @see #createPythagoreanTripleSample(double)
     */
    public static Iterator<double[]> createPythagoreanTripleSamplesIterator( double min, double max, double rate ) {
        return new PythagoreanTripleSamplesIterator( min, max, rate );
    }


    private AbsSamplesFactory() {
    }


    private static final class PythagoreanTripleSamplesIterator extends AbstractSamplesIterator {

        private final double min;

        private final double max;

        private final double rate;

        private double value;

        private PythagoreanTripleSamplesIterator( double min, double max, double rate ) {
            super( String.format( "pythagorean triple (3x, 4x, 5x) samples with x from %s to %s and rate %s", min, max, rate ) );
            this.min = min;
            this.max = max;
            if ( min == 0.0 ) {
                this.rate = 1 / rate;
                value = max;
            } else {
                this.rate = rate;
                value = min;
            }
        }

        @Override
        public boolean hasNext() {
            return rate >= 1.0 ? value < max : value > min;
        }

        @Override
        public double[] next() {
            if ( hasNext() ) {
                double[] sample = createPythagoreanTripleSample( value );
                value *= rate;
                return sample;
            } else {
                throw new NoSuchElementException();
            }
        }

    }

}
