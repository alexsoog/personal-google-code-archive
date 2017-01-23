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

package oe.math.complex;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isCloseTo;
import static oe.assertions.Predicates.isSameAs;
import static oe.assertions.Statement.value;

public class SquareRootTest {

    @Test( dataProvider = "squareRootSamples" )
    public void squareRoot( Complex complex, Complex expectedSquareRoot, boolean conjugateIsDistributive ) {
        Complex actualSquareRoot = complex.sqrt();
        assertThat(
                value( actualSquareRoot.getRe() ).describedAs( "the real part" ).satisfiesAnyOf(
                        isSameAs( expectedSquareRoot.getRe() ),
                        isCloseTo( expectedSquareRoot.getRe() ).byUlpDifference( 1L ) ),
                value( actualSquareRoot.getIm() ).describedAs( "the imaginary part" ).satisfiesAnyOf(
                        isSameAs( expectedSquareRoot.getIm() ),
                        isCloseTo( expectedSquareRoot.getIm() ).byUlpDifference( 1L ) ) );
    }

    @Test( dataProvider = "squareRootSamples" )
    public void conjugateIsDistributiveOverSquareRoot( Complex complex, Complex expectedSquareRoot, boolean conjugateIsDistributive ) {
        complex = complex.conjugate();
        if ( conjugateIsDistributive ) {
            expectedSquareRoot = expectedSquareRoot.conjugate();
        } else {
            /*
            if input is negative real then Im(result) is always positive
             */
        }
        Complex actualSquareRoot = complex.sqrt();
        assertThat(
                value( actualSquareRoot.getRe() ).describedAs( "the real part" ).satisfiesAnyOf(
                        isSameAs( expectedSquareRoot.getRe() ),
                        isCloseTo( expectedSquareRoot.getRe() ).byUlpDifference( 1L ) ),
                value( actualSquareRoot.getIm() ).describedAs( "the imaginary part" ).satisfiesAnyOf(
                        isSameAs( expectedSquareRoot.getIm() ),
                        isCloseTo( expectedSquareRoot.getIm() ).byUlpDifference( 1L ) ) );
    }


    @DataProvider( name = "squareRootSamples" )
    public Object[][] squareRootSamples() {
        return new Object[ ][ ] {
                /*
                {complex, expectedSquareRoot}
                 */

                //  @sqrt( real )
                ////  @sqrt  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( 0.0 ), Complex.real( 0.0 ), true },
                { Complex.real( -0.0 ), Complex.imaginary( 0.0 ), false },
                { Complex.real( 1.0 ), Complex.real( 1.0 ), true },
                { Complex.real( -1.0 ), Complex.imaginary( 1.0 ), false },
                { Complex.real( 4.0 ), Complex.real( 2.0 ), true },
                { Complex.real( -4.0 ), Complex.imaginary( 2.0 ), false },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), false },
                { Complex.real( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                { Complex.real( Double.MIN_VALUE ), Complex.real( Math.sqrt( Double.MIN_VALUE ) ), true },
                { Complex.real( -Double.MIN_VALUE ), Complex.imaginary( Math.sqrt( Double.MIN_VALUE ) ), false },
                { Complex.real( Double.MAX_VALUE ), Complex.real( Math.sqrt( Double.MAX_VALUE ) ), true },
                { Complex.real( -Double.MAX_VALUE ), Complex.imaginary( Math.sqrt( Double.MAX_VALUE ) ), false },


                //  @sqrt( imaginary )
                ////  @sqrt  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, 0.0 ), true },
                { Complex.imaginary( -0.0 ), Complex.cartesian( 0.0, -0.0 ), true },
                { Complex.imaginary( 1.0 ), Complex.cartesian( 1.0 / Math.sqrt( 2.0 ), 1.0 / Math.sqrt( 2.0 ) ), true },
                { Complex.imaginary( -1.0 ), Complex.cartesian( 1.0 / Math.sqrt( 2.0 ), -1.0 / Math.sqrt( 2.0 ) ), true },
                { Complex.imaginary( 4.0 ), Complex.cartesian( 2.0 / Math.sqrt( 2.0 ), 2.0 / Math.sqrt( 2.0 ) ), true },
                { Complex.imaginary( -4.0 ), Complex.cartesian( 2.0 / Math.sqrt( 2.0 ), -2.0 / Math.sqrt( 2.0 ) ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                { Complex.imaginary( Double.MIN_VALUE ), Complex.cartesian( 0.0, 0.0 ), true },
                { Complex.imaginary( -Double.MIN_VALUE ), Complex.cartesian( 0.0, -0.0 ), true },
                { Complex.imaginary( Double.MAX_VALUE ), Complex.cartesian( Math.sqrt( Double.MAX_VALUE / 2.0 ), Math.sqrt( Double.MAX_VALUE / 2.0 ) ), true },
                { Complex.imaginary( -Double.MAX_VALUE ), Complex.cartesian( Math.sqrt( Double.MAX_VALUE / 2.0 ), -Math.sqrt( Double.MAX_VALUE / 2.0 ) ), true },


                //  @sqrt( complex )
                ////  @sqrt  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, 0.0 ), true },
                { Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 0.0, -0.0 ), true },
                { Complex.cartesian( 0.0, 1.0 ), Complex.cartesian( 1.0 / Math.sqrt( 2.0 ), 1.0 / Math.sqrt( 2.0 ) ), true },
                { Complex.cartesian( 0.0, -1.0 ), Complex.cartesian( 1.0 / Math.sqrt( 2.0 ), -1.0 / Math.sqrt( 2.0 ) ), true },
                { Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( 2.0 / Math.sqrt( 2.0 ), 2.0 / Math.sqrt( 2.0 ) ), true },
                { Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( 2.0 / Math.sqrt( 2.0 ), -2.0 / Math.sqrt( 2.0 ) ), true },
                { Complex.cartesian( 0.0, Double.MIN_VALUE ), Complex.cartesian( 0.0, 0.0 ), true },
                { Complex.cartesian( 0.0, -Double.MIN_VALUE ), Complex.cartesian( 0.0, -0.0 ), true },
                { Complex.cartesian( 0.0, Double.MAX_VALUE ), Complex.cartesian( Math.sqrt( Double.MAX_VALUE / 2.0 ), Math.sqrt( Double.MAX_VALUE / 2.0 ) ), true },
                { Complex.cartesian( 0.0, -Double.MAX_VALUE ), Complex.cartesian( Math.sqrt( Double.MAX_VALUE / 2.0 ), -Math.sqrt( Double.MAX_VALUE / 2.0 ) ), true },
                { Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                ////  @sqrt  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 0.0, 0.0 ), true },
                { Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 0.0, -0.0 ), true },
                { Complex.cartesian( -0.0, 1.0 ), Complex.cartesian( 1.0 / Math.sqrt( 2.0 ), 1.0 / Math.sqrt( 2.0 ) ), true },
                { Complex.cartesian( -0.0, -1.0 ), Complex.cartesian( 1.0 / Math.sqrt( 2.0 ), -1.0 / Math.sqrt( 2.0 ) ), true },
                { Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( 2.0 / Math.sqrt( 2.0 ), 2.0 / Math.sqrt( 2.0 ) ), true },
                { Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( 2.0 / Math.sqrt( 2.0 ), -2.0 / Math.sqrt( 2.0 ) ), true },
                { Complex.cartesian( -0.0, Double.MIN_VALUE ), Complex.cartesian( 0.0, 0.0 ), true },
                { Complex.cartesian( -0.0, -Double.MIN_VALUE ), Complex.cartesian( 0.0, -0.0 ), true },
                { Complex.cartesian( -0.0, Double.MAX_VALUE ), Complex.cartesian( Math.sqrt( Double.MAX_VALUE / 2.0 ), Math.sqrt( Double.MAX_VALUE / 2.0 ) ), true },
                { Complex.cartesian( -0.0, -Double.MAX_VALUE ), Complex.cartesian( Math.sqrt( Double.MAX_VALUE / 2.0 ), -Math.sqrt( Double.MAX_VALUE / 2.0 ) ), true },
                { Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                ////  @sqrt  {+fin}r + {+0.0, -0.0, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ), true },
                { Complex.cartesian( 1.0, -0.0 ), Complex.cartesian( 1.0, -0.0 ), true },
                { Complex.cartesian( 1.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                { Complex.cartesian( 4.0, 0.0 ), Complex.cartesian( 2.0, 0.0 ), true },
                { Complex.cartesian( 4.0, -0.0 ), Complex.cartesian( 2.0, -0.0 ), true },

                { Complex.cartesian( Double.MIN_VALUE, 0.0 ), Complex.cartesian( Math.sqrt( Double.MIN_VALUE ), 0.0 ), true },
                { Complex.cartesian( Double.MIN_VALUE, -0.0 ), Complex.cartesian( Math.sqrt( Double.MIN_VALUE ), -0.0 ), true },
                { Complex.cartesian( Double.MAX_VALUE, 0.0 ), Complex.cartesian( Math.sqrt( Double.MAX_VALUE ), 0.0 ), true },
                { Complex.cartesian( Double.MAX_VALUE, -0.0 ), Complex.cartesian( Math.sqrt( Double.MAX_VALUE ), -0.0 ), true },

                ////  @sqrt  {-fin}r + {+0.0, -0.0, +inf, -inf, nan}i
                { Complex.cartesian( -1.0, 0.0 ), Complex.cartesian( 0.0, 1.0 ), true },
                { Complex.cartesian( -1.0, -0.0 ), Complex.cartesian( 0.0, -1.0 ), true },
                { Complex.cartesian( -1.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( -1.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( -1.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                { Complex.cartesian( -4.0, 0.0 ), Complex.cartesian( 0.0, 2.0 ), true },
                { Complex.cartesian( -4.0, -0.0 ), Complex.cartesian( 0.0, -2.0 ), true },

                { Complex.cartesian( -Double.MIN_VALUE, 0.0 ), Complex.cartesian( 0.0, Math.sqrt( Double.MIN_VALUE ) ), true },
                { Complex.cartesian( -Double.MIN_VALUE, -0.0 ), Complex.cartesian( 0.0, -Math.sqrt( Double.MIN_VALUE ) ), true },
                { Complex.cartesian( -Double.MAX_VALUE, 0.0 ), Complex.cartesian( 0.0, Math.sqrt( Double.MAX_VALUE ) ), true },
                { Complex.cartesian( -Double.MAX_VALUE, -0.0 ), Complex.cartesian( 0.0, -Math.sqrt( Double.MAX_VALUE ) ), true },

                ////  @sqrt  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, -1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                ////  @sqrt  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, 1.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, -1.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },

                ////  @sqrt  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ), true },
                { Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ), true },
                { Complex.cartesian( Double.NaN, 1.0 ), Complex.cartesian( Double.NaN, Double.NaN ), true },
                { Complex.cartesian( Double.NaN, -1.0 ), Complex.cartesian( Double.NaN, Double.NaN ), true },
                { Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                ////  @sqrt( complex ), from polar
                { Complex.polar( 1.0, Math.PI / 3.0 ), Complex.polar( 1.0, Math.PI / 6.0 ), true },
                { Complex.polar( 1.0, -Math.PI / 3.0 ), Complex.polar( 1.0, -Math.PI / 6.0 ), true },

                { Complex.polar( 4.0, Math.PI / 3.0 ), Complex.polar( 2.0, Math.PI / 6.0 ), true },
                { Complex.polar( 4.0, -Math.PI / 3.0 ), Complex.polar( 2.0, -Math.PI / 6.0 ), true },
        };
    }

}
