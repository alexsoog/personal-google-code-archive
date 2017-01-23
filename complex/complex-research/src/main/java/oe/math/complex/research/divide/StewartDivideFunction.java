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
 * Implements the algorithm from the "A Note on Complex Division" by G. W. Stewart [<a
 * href="http://dx.doi.org/10.1145/214408.214414">doi:10.1145/214408.214414</a>].
 * <p/>
 * All flips and swaps are explicitly expanded into if-else code blocks.
 */
public final class StewartDivideFunction extends BinaryFunction {

    @Override
    public double[] calculate( double a, double b, double c, double d ) {
        double e;
        double f;
        double denominator;
        double absA = Math.abs( a );
        double absB = Math.abs( b );
        double absC = Math.abs( c );
        double absD = Math.abs( d );
        if ( absD >= absC ) {
            denominator = d + c * ( c / d );
            double absRD = 1.0 / absD;
            if ( absC >= absRD ) {
                if ( absA >= absC ) {
                    e = b + c * ( a / d );
                } else if ( absA >= absRD ) {
                    e = b + a * ( c / d );
                } else {
                    e = b + a * c / d;
                }
                if ( absB >= absC ) {
                    f = c * ( b / d ) - a;
                } else if ( absB >= absRD ) {
                    f = b * ( c / d ) - a;
                } else {
                    f = b * c / d - a;
                }
            } else {
                if ( absA >= absRD ) {
                    e = b + a * c / d;
                } else if ( absA >= absC ) {
                    e = b + a * ( c / d );
                } else {
                    e = b + c * ( a / d );
                }
                if ( absB >= absRD ) {
                    f = b * c / d - a;
                } else if ( absB >= absC ) {
                    f = b * ( c / d ) - a;
                } else {
                    f = c * ( b / d ) - a;
                }
            }
        } else {
            denominator = c + d * ( d / c );
            double absRC = 1.0 / absC;
            if ( absD >= absRC ) {
                if ( absB >= absD ) {
                    e = a + d * ( b / c );
                } else if ( absB >= absRC ) {
                    e = a + b * ( d / c );
                } else {
                    e = a + b * d / c;
                }
                if ( absA >= absD ) {
                    f = b - d * ( a / c );
                } else if ( absA >= absRC ) {
                    f = b - a * ( d / c );
                } else {
                    f = b - a * d / c;
                }
            } else {
                if ( absB >= absRC ) {
                    e = a + b * d / c;
                } else if ( absB >= absD ) {
                    e = a + b * ( d / c );
                } else {
                    e = a + d * ( b / c );
                }
                if ( absA >= absRC ) {
                    f = b - a * d / c;
                } else if ( absA >= absD ) {
                    f = b - a * ( d / c );
                } else {
                    f = b - d * ( a / c );
                }
            }
        }
        e = e / denominator;
        f = f / denominator;
        return new double[] { e, f };
    }

    @Override
    public String toString() {
        return "Stewart";
    }

}
