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

import oe.math.complex.research.util.UnaryFunction;

/** Implements the algorithm from the "Numerical Recipies in C: The art of scientific computing". */
public final class ScaledSqrtFunction extends UnaryFunction {

    @Override
    public double[] calculate( double a, double b ) {
        double w;
        if ( a == 0.0 && b == 0.0 ) {
            w = 0.0;
        } else if ( Math.abs( a ) >= Math.abs( b ) ) {
            double ba = b / a;
            w = Math.sqrt( Math.abs( a ) ) * Math.sqrt( ( 1.0 + Math.sqrt( 1.0 + ba * ba ) ) / 2.0 );
        } else {
            double ab = a / b;
            w = Math.sqrt( Math.abs( b ) ) * Math.sqrt( ( Math.abs( ab ) + Math.sqrt( 1.0 + ab * ab ) ) / 2.0 );
        }
        double e;
        double f;
        if ( w == 0.0 ) {
            e = 0.0;
            f = 0.0;
        } else if ( a >= 0.0 ) {
            e = w;
            f = ( b / w ) / 2.0;
        } else if ( b >= 0.0 ) {
            e = ( Math.abs( b ) / w ) / 2.0;
            f = w;
        } else {
            e = ( Math.abs( b ) / w ) / 2.0;
            f = -w;
        }
        return new double[] { e, f };
    }

    @Override
    public String toString() {
        return "scaled";
    }

}
