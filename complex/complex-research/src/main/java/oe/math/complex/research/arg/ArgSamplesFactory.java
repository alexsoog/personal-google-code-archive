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

package oe.math.complex.research.arg;

import java.util.Iterator;

import oe.math.complex.research.util.AbstractRandomSamplesIterator;
import oe.math.complex.research.util.RandomDouble;

public final class ArgSamplesFactory {

    /**
     * Creates a new random sample {a, b, e, f} where arg(a, b) = (e, f). The argument modulus will be in the specified
     * bounds.
     * <p/>
     * This method generates exact argument from the random result.
     *
     * @param absMin the lower bound
     * @param absMax the upper bound
     *
     * @return the new sample
     */
    public static double[] createExactRandomSample( double absMin, double absMax ) {
        double e = RandomDouble.randomFiniteDoubleWithAbsBounds( 0.0, Math.PI );
        double r = Math.abs( RandomDouble.randomFiniteDoubleWithAbsBounds( absMin, absMax ) );
        double a = r * Math.cos( e );
        double b = r * Math.sin( e );
        double f = 0.0;
        return new double[] { a, b, e, f };
    }

    /**
     * Creates a new exact random samples iterator. The argument modulus will be in the specified bounds.
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
        return new AbstractRandomSamplesIterator( String.format( "exact random samples with the argument modulus in the [%s, %s]", absMin, absMax ), count ) {
            @Override
            protected double[] createSample() {
                return createExactRandomSample( absMin, absMax );
            }
        };
    }


    private ArgSamplesFactory() {
    }

}
