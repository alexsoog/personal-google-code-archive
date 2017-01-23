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

public class LogarithmTest {

    @Test( dataProvider = "logarithmSamples" )
    public void logarithm( Complex complex, Complex expectedLogarithm, boolean conjugateIsDistributive ) {
        Complex actualLogarithm = complex.log();
        // assertThat(actualLogarithm).isEqualTo(expectedLogarithm);
        assertThat(
                value( actualLogarithm.getRe() ).describedAs( "the real part" ).satisfiesAnyOf(
                        isSameAs( expectedLogarithm.getRe() ),
                        isCloseTo( expectedLogarithm.getRe() ).byUlpDifference( 1L ) ),
                value( actualLogarithm.getIm() ).describedAs( "the imaginary part" ).satisfiesAnyOf(
                        isSameAs( expectedLogarithm.getIm() ),
                        isCloseTo( expectedLogarithm.getIm() ).byUlpDifference( 1L ) ) );
    }

    @Test( dataProvider = "logarithmSamples" )
    public void conjugateIsDistributiveOverLogarithm( Complex complex, Complex expectedLogarithm, boolean conjugateIsDistributive ) {
        complex = complex.conjugate();
        if ( conjugateIsDistributive ) {
            expectedLogarithm = expectedLogarithm.conjugate();
        } else {
            /*
            if input is negative real then Im(result) is always positive
             */
        }
        Complex actualLogarithm = complex.log();
        assertThat(
                value( actualLogarithm.getRe() ).describedAs( "the real part" ).satisfiesAnyOf(
                        isSameAs( expectedLogarithm.getRe() ),
                        isCloseTo( expectedLogarithm.getRe() ).byUlpDifference( 1L ) ),
                value( actualLogarithm.getIm() ).describedAs( "the imaginary part" ).satisfiesAnyOf(
                        isSameAs( expectedLogarithm.getIm() ),
                        isCloseTo( expectedLogarithm.getIm() ).byUlpDifference( 1L ) ) );
    }


    @DataProvider( name = "logarithmSamples" )
    public Object[][] logarithmSamples() {
        return new Object[ ][ ] {
                /*
                {complex, expectedLogarithm}
                 */

                //  @log( real )
                ////  @log  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( 0.0 ), Complex.real( Double.NEGATIVE_INFINITY ), true },
                { Complex.real( -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Math.PI ), false },
                { Complex.real( 1.0 ), Complex.real( 0.0 ), true },
                { Complex.real( -1.0 ), Complex.cartesian( 0.0, Math.PI ), false },
                { Complex.real( 4.0 ), Complex.real( Math.log( 4.0 ) ), true },
                { Complex.real( -4.0 ), Complex.cartesian( Math.log( 4.0 ), Math.PI ), false },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Math.PI ), false },
                { Complex.real( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },


                //  @log( imaginary )
                ////  @log  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Math.PI / 2.0 ), true },
                { Complex.imaginary( -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -Math.PI / 2.0 ), true },
                { Complex.imaginary( 1.0 ), Complex.cartesian( 0.0, Math.PI / 2.0 ), true },
                { Complex.imaginary( -1.0 ), Complex.cartesian( 0.0, -Math.PI / 2.0 ), true },
                { Complex.imaginary( 4.0 ), Complex.cartesian( Math.log( 4.0 ), Math.PI / 2.0 ), true },
                { Complex.imaginary( -4.0 ), Complex.cartesian( Math.log( 4.0 ), -Math.PI / 2.0 ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Math.PI / 2.0 ), true },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -Math.PI / 2.0 ), true },
                { Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },


                //  @log( complex )
                ////  @log  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), true },
                { Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), true },
                { Complex.cartesian( 0.0, 1.0 ), Complex.cartesian( 0.0, Math.PI / 2.0 ), true },
                { Complex.cartesian( 0.0, -1.0 ), Complex.cartesian( 0.0, -Math.PI / 2.0 ), true },
                { Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Math.PI / 2.0 ), true },
                { Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -Math.PI / 2.0 ), true },
                { Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                ////  @log  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Math.PI ), true },
                { Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -Math.PI ), true },
                { Complex.cartesian( -0.0, 1.0 ), Complex.cartesian( 0.0, Math.PI / 2.0 ), true },
                { Complex.cartesian( -0.0, -1.0 ), Complex.cartesian( 0.0, -Math.PI / 2.0 ), true },
                { Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Math.PI / 2.0 ), true },
                { Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -Math.PI / 2.0 ), true },
                { Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                ////  @log  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 0.0 ), Complex.cartesian( 0.0, 0.0 ), true },
                { Complex.cartesian( 1.0, -0.0 ), Complex.cartesian( 0.0, -0.0 ), true },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Math.log( 2.0 ) / 2.0, Math.PI / 4.0 ), true },
                { Complex.cartesian( 1.0, -1.0 ), Complex.cartesian( Math.log( 2.0 ) / 2.0, -Math.PI / 4.0 ), true },
                { Complex.cartesian( 1.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Math.PI / 2.0 ), true },
                { Complex.cartesian( 1.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -Math.PI / 2.0 ), true },
                { Complex.cartesian( 1.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                ////  @log  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( -1.0, 0.0 ), Complex.cartesian( 0.0, Math.PI ), true },
                { Complex.cartesian( -1.0, -0.0 ), Complex.cartesian( 0.0, -Math.PI ), true },
                { Complex.cartesian( -1.0, 1.0 ), Complex.cartesian( Math.log( 2.0 ) / 2.0, Math.PI * 3.0 / 4.0 ), true },
                { Complex.cartesian( -1.0, -1.0 ), Complex.cartesian( Math.log( 2.0 ) / 2.0, -Math.PI * 3.0 / 4.0 ), true },
                { Complex.cartesian( -1.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Math.PI / 2.0 ), true },
                { Complex.cartesian( -1.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -Math.PI / 2.0 ), true },
                { Complex.cartesian( -1.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                ////  @log  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, -1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Math.PI / 4.0 ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -Math.PI / 4.0 ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                ////  @log  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Math.PI ), true },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -Math.PI ), true },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Math.PI ), true },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, -1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -Math.PI ), true },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Math.PI * 3.0 / 4.0 ), true },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -Math.PI * 3.0 / 4.0 ), true },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                ////  @log  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ), true },
                { Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ), true },
                { Complex.cartesian( Double.NaN, 1.0 ), Complex.cartesian( Double.NaN, Double.NaN ), true },
                { Complex.cartesian( Double.NaN, -1.0 ), Complex.cartesian( Double.NaN, Double.NaN ), true },
                { Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },
                { Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },
                { Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                ////  @log( complex ), from polar
                { Complex.polar( 4.0, Math.PI / 3.0 ), Complex.cartesian( Math.log( 4.0 ), Math.PI / 3.0 ), true },
                { Complex.polar( 4.0, -Math.PI / 3.0 ), Complex.cartesian( Math.log( 4.0 ), -Math.PI / 3.0 ), true },
        };
    }

}
