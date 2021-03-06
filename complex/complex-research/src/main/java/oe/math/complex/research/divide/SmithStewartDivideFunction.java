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

/** Combines the Smith's algorithm with the Stewart's method of calculating {@code a*b/c} expressions. */
public final class SmithStewartDivideFunction extends BinaryFunction {

    @Override
    public double[] calculate( double a, double b, double c, double d ) {
        double e;
        double f;
        double denominator;
        if ( Math.abs( c ) >= Math.abs( d ) ) {
            denominator = c + d * ( d / c );
            e = a + fusedMultiplyDivide( b, d, c );
            f = b - fusedMultiplyDivide( a, d, c );
        } else {
            denominator = d + c * ( c / d );
            e = fusedMultiplyDivide( a, c, d ) + b;
            f = fusedMultiplyDivide( b, c, d ) - a;
        }
        e = e / denominator;
        f = f / denominator;
        return new double[] { e, f };
    }

    /* Calculates a * b / c. */

    private double fusedMultiplyDivide( double a, double b, double c ) {
        double absA = Math.abs( a );
        double absB = Math.abs( b );
        double absRC = Math.abs( 1.0 / c );
        if ( absA >= absB ) {
            if ( absB >= absRC ) {
                return ( a / c ) * b;
            } else {
                if ( absA >= absRC ) {
                    return ( a * b ) / c;
                } else {
                    return ( b / c ) * a;
                }
            }
        } else {
            if ( absB >= absRC ) {
                if ( absA >= absRC ) {
                    return ( b / c ) * a;
                } else {
                    return ( a * b ) / c;
                }
            } else {
                return ( a / c ) * b;
            }
        }
    }

    @Override
    public String toString() {
        return "Smith+Stewart";
    }

}
