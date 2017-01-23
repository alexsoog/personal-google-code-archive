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

package oe.math.complex.research.log;

import java.util.Iterator;

import oe.math.complex.research.util.AbstractRandomSamplesIterator;
import oe.math.complex.research.util.RandomDouble;

public final class LogSamplesFactory {

    /**
     * Creates a new random sample {a, b, e, f} where log(a, b) = (e, f).
     *
     * @param absMin the lower bound
     * @param absMax the upper bound
     *
     * @return the new sample
     */
    public static double[] createRandomSample( double absMin, double absMax ) {
        double r;
        double theta;
        double a;
        double b;
        do {
            r = Math.abs( RandomDouble.randomFiniteDoubleWithAbsBounds( absMin, absMax ) );
            theta = RandomDouble.randomFiniteDoubleWithAbsBounds( Double.MIN_NORMAL, Math.PI );
            a = r * Math.cos( theta );
            b = r * Math.sin( theta );
        } while ( a == 0.0 && b == 0.0 || theta != 0.0 && b == 0.0 );
        double e = Math.log( Math.abs( r ) );
        double f = theta;
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


    private LogSamplesFactory() {
    }

}
