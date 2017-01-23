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

public class ExponentiationTest {

    @Test( dataProvider = "exponentSamples" )
    public void exponent( Complex complex, Complex expectedExponent ) {
        Complex actualExponent = complex.exp();
        assertThat(
                value( actualExponent.getRe() ).describedAs( "the real part" ).satisfiesAnyOf(
                        isSameAs( expectedExponent.getRe() ),
                        isCloseTo( expectedExponent.getRe() ).byUlpDifference( 1L ) ),
                value( actualExponent.getIm() ).describedAs( "the imaginary part" ).satisfiesAnyOf(
                        isSameAs( expectedExponent.getIm() ),
                        isCloseTo( expectedExponent.getIm() ).byUlpDifference( 1L ) ) );
    }

    @Test( dataProvider = "exponentSamples" )
    public void conjugateIsDistributiveOverExponent( Complex complex, Complex expectedExponent ) {
        complex = complex.conjugate();
        expectedExponent = expectedExponent.conjugate();
        Complex actualExponent = complex.exp();
        assertThat(
                value( actualExponent.getRe() ).describedAs( "the real part" ).satisfiesAnyOf(
                        isSameAs( expectedExponent.getRe() ),
                        isCloseTo( expectedExponent.getRe() ).byUlpDifference( 1L ) ),
                value( actualExponent.getIm() ).describedAs( "the imaginary part" ).satisfiesAnyOf(
                        isSameAs( expectedExponent.getIm() ),
                        isCloseTo( expectedExponent.getIm() ).byUlpDifference( 1L ) ) );
    }


    @DataProvider( name = "exponentSamples" )
    public Object[][] exponentSamples() {
        return new Object[ ][ ] {
                /*
                {complex, expectedExponent}
                 */

                //  @exp( real )
                ////  @exp  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( 0.0 ), Complex.real( 1.0 ) },
                { Complex.real( -0.0 ), Complex.real( 1.0 ) },
                { Complex.real( 1.0 ), Complex.real( Math.E ) },
                { Complex.real( -1.0 ), Complex.real( 1.0 / Math.E ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( 0.0 ) },
                { Complex.real( Double.NaN ), Complex.real( Double.NaN ) },


                //  @exp( imaginary )
                ////  @exp  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.imaginary( 1.0 ), Complex.cartesian( Math.cos( 1.0 ), Math.sin( 1.0 ) ) },
                { Complex.imaginary( -1.0 ), Complex.cartesian( Math.cos( -1.0 ), Math.sin( -1.0 ) ) },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },


                //  @exp( complex )
                ////  @exp  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.cartesian( 0.0, 1.0 ), Complex.cartesian( Math.cos( 1.0 ), Math.sin( 1.0 ) ) },
                { Complex.cartesian( 0.0, -1.0 ), Complex.cartesian( Math.cos( -1.0 ), Math.sin( -1.0 ) ) },
                { Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                ////  @exp  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.cartesian( -0.0, 1.0 ), Complex.cartesian( Math.cos( 1.0 ), Math.sin( 1.0 ) ) },
                { Complex.cartesian( -0.0, -1.0 ), Complex.cartesian( Math.cos( -1.0 ), Math.sin( -1.0 ) ) },
                { Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                ////  @exp  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 0.0 ), Complex.cartesian( Math.E, 0.0 ) },
                { Complex.cartesian( 1.0, -0.0 ), Complex.cartesian( Math.E, -0.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Math.E * Math.cos( 1.0 ), Math.E * Math.sin( 1.0 ) ) },
                { Complex.cartesian( 1.0, -1.0 ), Complex.cartesian( Math.E * Math.cos( -1.0 ), Math.E * Math.sin( -1.0 ) ) },
                { Complex.cartesian( 1.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                ////  @exp  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( -1.0, 0.0 ), Complex.cartesian( 1.0 / Math.E, 0.0 ) },
                { Complex.cartesian( -1.0, -0.0 ), Complex.cartesian( 1.0 / Math.E, -0.0 ) },
                { Complex.cartesian( -1.0, 1.0 ), Complex.cartesian( Math.cos( 1.0 ) / Math.E, Math.sin( 1.0 ) / Math.E ) },
                { Complex.cartesian( -1.0, -1.0 ), Complex.cartesian( Math.cos( -1.0 ) / Math.E, Math.sin( -1.0 ) / Math.E ) },
                { Complex.cartesian( -1.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( -1.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( -1.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                ////  @exp  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, -1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                ////  @exp  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( 0.0, 0.0 ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( 0.0, -0.0 ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, 1.0 ), Complex.cartesian( 0.0, 0.0 ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, -1.0 ), Complex.cartesian( 0.0, -0.0 ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                ////  @exp  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.NaN, 1.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.NaN, -1.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },
        };
    }

}
