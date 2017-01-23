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
 * Implements the algorithm from the "Efficient scaling for complex division" by Douglas M. Priest [<a
 * href="http://dx.doi.org/10.1145/1039813.1039814">doi:10.1145/1039813.1039814</a>].
 */
public final class PriestDivideFunction extends BinaryFunction {

    @Override
    public double[] calculate( double a, double b, double c, double d ) {
        int ha = ( int ) ( Double.doubleToLongBits( a ) >>> 32 ) & 0x7fffffff;
        int hb = ( int ) ( Double.doubleToLongBits( b ) >>> 32 ) & 0x7fffffff;
        int hz = ( ha > hb ) ? ha : hb;

        int hc = ( int ) ( Double.doubleToLongBits( c ) >>> 32 ) & 0x7fffffff;
        int hd = ( int ) ( Double.doubleToLongBits( d ) >>> 32 ) & 0x7fffffff;
        int hw = ( hc > hd ) ? hc : hd;

        int hs;
        if ( hz < 0x07200000 && hw >= 0x32800000 && hw < 0x47100000 ) {
            hs = ( ( ( 0x47100000 - hw ) >>> 1 ) & 0xfff00000 ) + 0x3ff00000;
        } else {
            hs = ( ( ( hw >>> 2 ) - hw ) + 0x6fd7ffff ) & 0xfff00000;
        }

        long ss = ( ( long ) hs ) << 32;
        double s = Double.longBitsToDouble( ss );

        c = c * s;
        d = d * s;
        double t = 1.0 / ( c * c + d * d );

        c = c * s;
        d = d * s;

        double e = ( a * c + b * d ) * t;
        double f = ( b * c - a * d ) * t;

        return new double[] { e, f };
    }

    @Override
    public String toString() {
        return "Priest";
    }

}
