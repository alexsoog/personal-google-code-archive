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

package oe.math.complex.research.divide;

import oe.math.complex.research.util.BinaryFunction;

/**
 * Implements the algorithm from the "Algorithm 116: Complex division" by Robert L. Smith [<a
 * href="http://dx.doi.org/10.1145/368637.368661">doi:10.1145/368637.368661</a>].
 */
public final class SmithDivideFunction extends BinaryFunction {

    @Override
    public double[] calculate( double a, double b, double c, double d ) {
        double e;
        double f;
        double denominator;
        if ( Math.abs( c ) >= Math.abs( d ) ) {
            double r = d / c;
            denominator = c + r * d;
            e = a + b * r;
            f = b - a * r;
        } else {
            double r = c / d;
            denominator = r * c + d;
            e = a * r + b;
            f = b * r - a;
        }
        e = e / denominator;
        f = f / denominator;
        return new double[] { e, f };
    }

    @Override
    public String toString() {
        return "Smith";
    }

}
