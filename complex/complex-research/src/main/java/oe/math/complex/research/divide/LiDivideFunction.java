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
 * Implements the algorithm from the "Design, implementation and testing of extended and mixed precision BLAS" by X. S.
 * Li, J. W. Demmel, D. H. Bailey, G. Henry, Y. Hida, J. Iskandar, W. Kahan, A. Kapur, M. C. Martin, T. Tung, D. J. Yoo
 * [<a href="http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.16.3289">http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.16.3289</a>].
 */
public final class LiDivideFunction extends BinaryFunction {

    @Override
    public double[] calculate( double a, double b, double c, double d ) {
        double e;
        double f;

        double ab = Math.max( Math.abs( a ), Math.abs( b ) );
        double cd = Math.max( Math.abs( c ), Math.abs( d ) );

        double overflow = Double.MAX_VALUE;
        double underflow = Double.MIN_NORMAL;
        double epsilon = 1.0 - Math.nextAfter( 1.0, 0.0 );
        double small = 2.0;

        double scale = 1.0;

        if ( ab > overflow / 16.0 ) {
            double scaleFactor = 16.0;
            a = a / scaleFactor;
            b = b / scaleFactor;
            scale = scale * scaleFactor;
        }
        if ( cd > overflow / 16.0 ) {
            double scaleFactor = 16.0;
            c = c / scaleFactor;
            d = d / scaleFactor;
            scale = scale / scaleFactor;
        }
        if ( ab < underflow * small / epsilon ) {
            double scaleFactor = small / ( epsilon * epsilon );
            a = a * scaleFactor;
            b = b * scaleFactor;
            scale = scale / scaleFactor;
        }
        if ( cd < underflow * small / epsilon ) {
            double scaleFactor = small / ( epsilon * epsilon );
            c = c * scaleFactor;
            d = d * scaleFactor;
            scale = scale * scaleFactor;
        }
        if ( Math.abs( c ) > Math.abs( d ) ) {
            double r = d / c;
            double denominator = c + d * r;
            e = ( a + b * r ) / denominator;
            f = ( b - a * r ) / denominator;
        } else {
            double r = c / d;
            double denominator = c * r + d;
            e = ( a * r + b ) / denominator;
            f = ( b * r - a ) / denominator;
        }

        e = e * scale;
        f = f * scale;

        return new double[] { e, f };
    }

    @Override
    public String toString() {
        return "Li et al";
    }

}
