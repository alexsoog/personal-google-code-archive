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

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isCloseTo;
import static oe.assertions.Predicates.isEqualTo;
import static oe.assertions.Predicates.isSameAs;
import static oe.assertions.Statement.value;

public class PowerTest {

    @Test( dataProvider = "powerSamples" )
    public void power( Complex base, Complex exponent, Complex expectedPower ) {
        Complex actualPower = base.pow( exponent );
        assertThat(
                value( actualPower.getRe() ).describedAs( "the real part" ).satisfiesAnyOf(
                        isSameAs( expectedPower.getRe() ),
                        isCloseTo( expectedPower.getRe() ).byUlpDifference( 1L ) ),
                value( actualPower.getIm() ).describedAs( "the imaginary part" ).satisfiesAnyOf(
                        isSameAs( expectedPower.getIm() ),
                        isCloseTo( expectedPower.getIm() ).byUlpDifference( 1L ) ) );
    }

    @Test( dataProvider = "zeroPowerSamples" )
    public void zeroPower( Complex base, Complex exponent ) {
        Complex actualPower = base.pow( exponent );
        assertThat(
                value( actualPower.getRe() ).describedAs( "the real part" ).satisfies( isEqualTo( 1.0 ) ),
                value( actualPower.getIm() ).describedAs( "the imaginary part" ).satisfies( isEqualTo( 0.0 ) ) ); // covers both +0.0 and -0.0
    }


    @DataProvider( name = "powerSamples" )
    public Object[][] powerSamples() {
        return new Object[ ][ ] {
                /*
                {base, exponent, expectedPower}
                 */

                // real  @^  real
                //// {+0.0}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( 0.0 ), Complex.real( 0.0 ), Complex.real( 1.0 ) },
                { Complex.real( 0.0 ), Complex.real( -0.0 ), Complex.real( 1.0 ) },
                { Complex.real( 0.0 ), Complex.real( 1.0 ), Complex.real( 0.0 ) },
                { Complex.real( 0.0 ), Complex.real( -1.0 ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( 0.0 ), Complex.real( 2.0 ), Complex.real( 0.0 ) },
                { Complex.real( 0.0 ), Complex.real( -2.0 ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( 0.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( 0.0 ) },
                { Complex.real( 0.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( 0.0 ), Complex.real( Double.NaN ), Complex.real( Double.NaN ) },

                //// {-0.0}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( -0.0 ), Complex.real( 0.0 ), Complex.real( 1.0 ) },
                { Complex.real( -0.0 ), Complex.real( -0.0 ), Complex.real( 1.0 ) },
                { Complex.real( -0.0 ), Complex.real( 1.0 ), Complex.real( -0.0 ) },
                { Complex.real( -0.0 ), Complex.real( -1.0 ), Complex.real( Double.NEGATIVE_INFINITY ) },
                { Complex.real( -0.0 ), Complex.real( 2.0 ), Complex.real( 0.0 ) },
                { Complex.real( -0.0 ), Complex.real( -2.0 ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( -0.0 ), Complex.real( 3.0 ), Complex.real( -0.0 ) },
                { Complex.real( -0.0 ), Complex.real( -3.0 ), Complex.real( Double.NEGATIVE_INFINITY ) },
                { Complex.real( -0.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( 0.0 ) },
                { Complex.real( -0.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( -0.0 ), Complex.real( Double.NaN ), Complex.real( Double.NaN ) },

                //// {+1.0}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( 1.0 ), Complex.real( 0.0 ), Complex.real( 1.0 ) },
                { Complex.real( 1.0 ), Complex.real( -0.0 ), Complex.real( 1.0 ) },
                { Complex.real( 1.0 ), Complex.real( 1.0 ), Complex.real( 1.0 ) },
                { Complex.real( 1.0 ), Complex.real( -1.0 ), Complex.real( 1.0 ) },
                { Complex.real( 1.0 ), Complex.real( 2.0 ), Complex.real( 1.0 ) },
                { Complex.real( 1.0 ), Complex.real( -2.0 ), Complex.real( 1.0 ) },
                { Complex.real( 1.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.NaN ) },
                { Complex.real( 1.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NaN ) },
                { Complex.real( 1.0 ), Complex.real( Double.NaN ), Complex.real( Double.NaN ) },

                //// {-1.0}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( -1.0 ), Complex.real( 0.0 ), Complex.real( 1.0 ) },
                { Complex.real( -1.0 ), Complex.real( -0.0 ), Complex.real( 1.0 ) },
                { Complex.real( -1.0 ), Complex.real( 1.0 ), Complex.real( -1.0 ) },
                { Complex.real( -1.0 ), Complex.real( -1.0 ), Complex.real( -1.0 ) },
                { Complex.real( -1.0 ), Complex.real( 2.0 ), Complex.real( 1.0 ) },
                { Complex.real( -1.0 ), Complex.real( -2.0 ), Complex.real( 1.0 ) },
                { Complex.real( -1.0 ), Complex.real( 3.0 ), Complex.real( -1.0 ) },
                { Complex.real( -1.0 ), Complex.real( -3.0 ), Complex.real( -1.0 ) },
                { Complex.real( -1.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.NaN ) },
                { Complex.real( -1.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NaN ) },
                { Complex.real( -1.0 ), Complex.real( Double.NaN ), Complex.real( Double.NaN ) },

                //// {+fin}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( 2.0 ), Complex.real( 0.0 ), Complex.real( 1.0 ) },
                { Complex.real( 2.0 ), Complex.real( -0.0 ), Complex.real( 1.0 ) },
                { Complex.real( 2.0 ), Complex.real( 1.0 ), Complex.real( 2.0 ) },
                { Complex.real( 2.0 ), Complex.real( -1.0 ), Complex.real( 1.0 / 2.0 ) },
                { Complex.real( 2.0 ), Complex.real( 2.0 ), Complex.real( 4.0 ) },
                { Complex.real( 2.0 ), Complex.real( -2.0 ), Complex.real( 1.0 / 4.0 ) },
                { Complex.real( 2.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( 2.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( 0.0 ) },
                { Complex.real( 2.0 ), Complex.real( Double.NaN ), Complex.real( Double.NaN ) },

                //// {-fin}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( -2.0 ), Complex.real( 0.0 ), Complex.real( 1.0 ) },
                { Complex.real( -2.0 ), Complex.real( -0.0 ), Complex.real( 1.0 ) },
                { Complex.real( -2.0 ), Complex.real( 1.0 ), Complex.real( -2.0 ) },
                { Complex.real( -2.0 ), Complex.real( -1.0 ), Complex.real( -1.0 / 2.0 ) },
                { Complex.real( -2.0 ), Complex.real( 2.0 ), Complex.real( 4.0 ) },
                { Complex.real( -2.0 ), Complex.real( -2.0 ), Complex.real( 1.0 / 4.0 ) },
                { Complex.real( -2.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( -2.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( 0.0 ) },
                { Complex.real( -2.0 ), Complex.real( Double.NaN ), Complex.real( Double.NaN ) },

                //// {+inf}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( 0.0 ), Complex.real( 1.0 ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( -0.0 ), Complex.real( 1.0 ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( 1.0 ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( -1.0 ), Complex.real( 0.0 ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( 2.0 ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( -2.0 ), Complex.real( 0.0 ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( 0.0 ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.NaN ), Complex.real( Double.NaN ) },

                //// {-inf}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( 0.0 ), Complex.real( 1.0 ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( -0.0 ), Complex.real( 1.0 ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( 1.0 ), Complex.real( Double.NEGATIVE_INFINITY ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( -1.0 ), Complex.real( -0.0 ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( 2.0 ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( -2.0 ), Complex.real( 0.0 ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( 0.0 ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NaN ), Complex.real( Double.NaN ) },

                //// {nan}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( Double.NaN ), Complex.real( 0.0 ), Complex.real( 1.0 ) },
                { Complex.real( Double.NaN ), Complex.real( -0.0 ), Complex.real( 1.0 ) },
                { Complex.real( Double.NaN ), Complex.real( 1.0 ), Complex.real( Double.NaN ) },
                { Complex.real( Double.NaN ), Complex.real( -1.0 ), Complex.real( Double.NaN ) },
                { Complex.real( Double.NaN ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.NaN ) },
                { Complex.real( Double.NaN ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NaN ) },
                { Complex.real( Double.NaN ), Complex.real( Double.NaN ), Complex.real( Double.NaN ) },


                // real  @^  imaginary
                //// {+0.0}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 0.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 0.0 ), Complex.imaginary( 1.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.imaginary( -1.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-0.0}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -0.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( -0.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( -0.0 ), Complex.imaginary( 1.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.imaginary( -1.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 1.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 1.0 ), Complex.imaginary( 1.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 1.0 ), Complex.imaginary( -1.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.real( 1.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-fin}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -1.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( -1.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( -1.0 ), Complex.imaginary( 1.0 ), Complex.cartesian( Math.exp( -Math.PI ), 0.0 ) },
                { Complex.real( -1.0 ), Complex.imaginary( -1.0 ), Complex.cartesian( Math.exp( Math.PI ), -0.0 ) },
                { Complex.real( -1.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+inf}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( 1.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( -1.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-inf}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( 1.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( -1.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {nan}r  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( Double.NaN ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( Double.NaN ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( Double.NaN ), Complex.imaginary( 1.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( Double.NaN ), Complex.imaginary( -1.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( Double.NaN ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( Double.NaN ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( Double.NaN ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },


                // real  @^  complex
                //// {+0.0}r  @^  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}r  @^  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}r  @^  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}r  @^  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}r  @^  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}r  @^  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}r  @^  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-0.0}r  @^  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -0.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( -0.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( -0.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-0.0}r  @^  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -0.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.real( -0.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( -0.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-0.0}r  @^  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -0.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-0.0}r  @^  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -0.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-0.0}r  @^  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-0.0}r  @^  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-0.0}r  @^  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -0.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r  @^  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r  @^  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r  @^  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r  @^  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r  @^  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r  @^  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r  @^  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-fin}r  @^  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -1.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( -1.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( -1.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( Math.exp( -Math.PI * 4.0 ), 0.0 ) },
                { Complex.real( -1.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( Math.exp( Math.PI * 4.0 ), 0.0 ) },
                { Complex.real( -1.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-fin}r  @^  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -1.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.real( -1.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.real( -1.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( Math.exp( -Math.PI * 4.0 ), 0.0 ) },
                { Complex.real( -1.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( Math.exp( Math.PI * 4.0 ), -0.0 ) },
                { Complex.real( -1.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-fin}r  @^  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -1.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( -1.0, Math.sin( Math.PI * 3.0 ) ) }, // Math.sin(Math.PI) != 0.0
                { Complex.real( -1.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( -1.0, Math.sin( Math.PI * 3.0 ) ) }, // Math.sin(Math.PI) != 0.0
                { Complex.real( -1.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.polar( Math.exp( -Math.PI * 4.0 ), Math.PI * 3.0 ) },
                { Complex.real( -1.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.polar( Math.exp( Math.PI * 4.0 ), Math.PI * 3.0 ) },
                { Complex.real( -1.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-fin}r  @^  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -1.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( -1.0, Math.sin( -Math.PI * 3.0 ) ) }, // Math.sin(Math.PI) != 0.0
                { Complex.real( -1.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( -1.0, Math.sin( -Math.PI * 3.0 ) ) }, // Math.sin(Math.PI) != 0.0
                { Complex.real( -1.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.polar( Math.exp( -Math.PI * 4.0 ), -Math.PI * 3.0 ) },
                { Complex.real( -1.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.polar( Math.exp( Math.PI * 4.0 ), -Math.PI * 3.0 ) },
                { Complex.real( -1.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-fin}r  @^  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-fin}r  @^  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-fin}r  @^  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -1.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.real( -1.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },


                // imaginary  @^  real
                //// {+0.0}i  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.imaginary( 0.0 ), Complex.real( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( 0.0 ), Complex.real( -0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.imaginary( 0.0 ), Complex.real( 1.0 ), Complex.cartesian( 0.0, 0.0 ) },
                { Complex.imaginary( 0.0 ), Complex.real( -1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ) },
                { Complex.imaginary( 0.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.real( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}i  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.imaginary( 2.0 ), Complex.real( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( 2.0 ), Complex.real( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( 2.0 ), Complex.real( 1.0 ), Complex.polar( 2.0, Math.PI / 2.0 ) },
                { Complex.imaginary( 2.0 ), Complex.real( -1.0 ), Complex.polar( 1.0 / 2.0, -Math.PI / 2.0 ) },
                { Complex.imaginary( 2.0 ), Complex.real( 2.0 ), Complex.cartesian( -4.0, 4.0 * Math.sin( Math.PI ) ) }, // Math.sin(Math.PI) != 0.0
                { Complex.imaginary( 2.0 ), Complex.real( -2.0 ), Complex.cartesian( -1.0 / 4.0, Math.sin( -Math.PI ) / 4.0 ) }, // Math.sin(Math.PI) != 0.0
                { Complex.imaginary( 2.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.real( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },


                // imaginary  @^  imaginary
                //// {+0.0}i  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( 0.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( 0.0 ), Complex.imaginary( 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.imaginary( -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-0.0}i  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( -0.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.imaginary( -0.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( -0.0 ), Complex.imaginary( 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( -0.0 ), Complex.imaginary( -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( -0.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( -0.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( -0.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}i  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( 2.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( 2.0 ), Complex.imaginary( 4.0 ), Complex.polar( Math.exp( -Math.PI * 2.0 ), Math.log( 2.0 ) * 4.0 ) },
                { Complex.imaginary( 2.0 ), Complex.imaginary( -4.0 ), Complex.polar( Math.exp( Math.PI * 2.0 ), -Math.log( 2.0 ) * 4.0 ) },
                { Complex.imaginary( 2.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-fin}i  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( -2.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( -2.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.imaginary( -2.0 ), Complex.imaginary( 4.0 ), Complex.polar( Math.exp( Math.PI * 2.0 ), Math.log( 2.0 ) * 4.0 ) },
                { Complex.imaginary( -2.0 ), Complex.imaginary( -4.0 ), Complex.polar( Math.exp( -Math.PI * 2.0 ), -Math.log( 2.0 ) * 4.0 ) },
                { Complex.imaginary( -2.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( -2.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( -2.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+inf}i  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {-inf}i  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {nan}i  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( Double.NaN ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( Double.NaN ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( Double.NaN ), Complex.imaginary( 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.NaN ), Complex.imaginary( -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.NaN ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.NaN ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( Double.NaN ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },


                // imaginary  @^  complex
                //// {+0.0}i  @^  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}i  @^  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}i  @^  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}i  @^  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}i  @^  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}i  @^  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}i  @^  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}i  @^  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.polar( Math.exp( -Math.PI * 2.0 ), Math.log( 2.0 ) * 4.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.polar( Math.exp( Math.PI * 2.0 ), -Math.log( 2.0 ) * 4.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}i  @^  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.polar( Math.exp( -Math.PI * 2.0 ), Math.log( 2.0 ) * 4.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.polar( Math.exp( Math.PI * 2.0 ), -Math.log( 2.0 ) * 4.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}i  @^  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.polar( Math.exp( Math.log( 2.0 ) * 3.0 ), Math.PI * 3.0 / 2.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.polar( Math.exp( Math.log( 2.0 ) * 3.0 ), Math.PI * 3.0 / 2.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.polar( Math.exp( Math.log( 2.0 ) * 3.0 - Math.PI * 2.0 ), Math.PI * 3.0 / 2.0 + Math.log( 2.0 ) * 4.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.polar( Math.exp( Math.log( 2.0 ) * 3.0 + Math.PI * 2.0 ), Math.PI * 3.0 / 2.0 - Math.log( 2.0 ) * 4.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}i  @^  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.polar( Math.exp( -Math.log( 2.0 ) * 3.0 ), -Math.PI * 3.0 / 2.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.polar( Math.exp( -Math.log( 2.0 ) * 3.0 ), -Math.PI * 3.0 / 2.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.polar( Math.exp( -Math.log( 2.0 ) * 3.0 - Math.PI * 2.0 ), -Math.PI * 3.0 / 2.0 + Math.log( 2.0 ) * 4.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.polar( Math.exp( -Math.log( 2.0 ) * 3.0 + Math.PI * 2.0 ), -Math.PI * 3.0 / 2.0 - Math.log( 2.0 ) * 4.0 ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}i  @^  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}i  @^  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}i  @^  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },


                // complex  @^  real
                //// {+fin}r + {+fin}i  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.cartesian( 1.0, 1.0 ), Complex.real( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.real( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.real( 3.0 ), Complex.polar( Math.exp( Math.log( Math.sqrt( 2.0 ) ) * 3.0 ), Math.PI * 3.0 / 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.real( -3.0 ), Complex.polar( Math.exp( -Math.log( Math.sqrt( 2.0 ) ) * 3.0 ), -Math.PI * 3.0 / 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.real( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },


                // complex  @^  imaginary
                //// {+fin}r + {+fin}i  @^  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 1.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.imaginary( 4.0 ), Complex.polar( Math.exp( -Math.PI ), Math.log( Math.sqrt( 2.0 ) ) * 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.imaginary( -4.0 ), Complex.polar( Math.exp( Math.PI ), -Math.log( Math.sqrt( 2.0 ) ) * 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                // complex  @^  complex
                //// {+0.0}r + {+0.0}i  @^  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}r + {+0.0}i  @^  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}r + {+0.0}i  @^  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}r + {+0.0}i  @^  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}r + {+0.0}i  @^  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}r + {+0.0}i  @^  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+0.0}r + {+0.0}i  @^  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r + {+fin}i  @^  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.polar( Math.exp( -Math.PI ), Math.log( Math.sqrt( 2.0 ) ) * 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.polar( Math.exp( Math.PI ), -Math.log( Math.sqrt( 2.0 ) ) * 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r + {+fin}i  @^  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.polar( Math.exp( -Math.PI ), Math.log( Math.sqrt( 2.0 ) ) * 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.polar( Math.exp( Math.PI ), -Math.log( Math.sqrt( 2.0 ) ) * 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r + {+fin}i  @^  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.polar( Math.exp( Math.log( Math.sqrt( 2.0 ) ) * 3.0 ), Math.PI * 3.0 / 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.polar( Math.exp( Math.log( Math.sqrt( 2.0 ) ) * 3.0 ), Math.PI * 3.0 / 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.polar( Math.exp( Math.log( Math.sqrt( 2.0 ) ) * 3.0 - Math.PI ), Math.PI * 3.0 / 4.0 + Math.log( Math.sqrt( 2.0 ) ) * 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.polar( Math.exp( Math.log( Math.sqrt( 2.0 ) ) * 3.0 + Math.PI ), Math.PI * 3.0 / 4.0 - Math.log( Math.sqrt( 2.0 ) ) * 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r + {+fin}i  @^  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.polar( Math.exp( -Math.log( Math.sqrt( 2.0 ) ) * 3.0 ), -Math.PI * 3.0 / 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.polar( Math.exp( -Math.log( Math.sqrt( 2.0 ) ) * 3.0 ), -Math.PI * 3.0 / 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.polar( Math.exp( -Math.log( Math.sqrt( 2.0 ) ) * 3.0 - Math.PI ), -Math.PI * 3.0 / 4.0 + Math.log( Math.sqrt( 2.0 ) ) * 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.polar( Math.exp( -Math.log( Math.sqrt( 2.0 ) ) * 3.0 + Math.PI ), -Math.PI * 3.0 / 4.0 - Math.log( Math.sqrt( 2.0 ) ) * 4.0 ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r + {+fin}i  @^  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r + {+fin}i  @^  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+fin}r + {+fin}i  @^  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+inf}r + {+inf}i  @^  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+inf}r + {+inf}i  @^  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+inf}r + {+inf}i  @^  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+inf}r + {+inf}i  @^  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+inf}r + {+inf}i  @^  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+inf}r + {+inf}i  @^  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },

                //// {+inf}r + {+inf}i  @^  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ) },
        };
    }

    @DataProvider( name = "zeroPowerSamples" )
    public Iterator<Object[]> zeroPowerSamples() {
        return new ZeroPowerSamplesIterator();
    }


    private static final Complex[] BASES = {
            Complex.real( 1.0 ), Complex.real( -1.0 ),
            Complex.imaginary( 1.0 ), Complex.imaginary( -1.0 ),
            Complex.cartesian( 1.0, 0.0 ), Complex.cartesian( 1.0, -0.0 ), Complex.cartesian( -1.0, 0.0 ), Complex.cartesian( -1.0, -0.0 ),
            Complex.cartesian( 0.0, 1.0 ), Complex.cartesian( 0.0, -1.0 ), Complex.cartesian( -0.0, 1.0 ), Complex.cartesian( -0.0, -1.0 ),
            Complex.cartesian( 1.0, 1.0 ), Complex.cartesian( 1.0, -1.0 ), Complex.cartesian( -1.0, 1.0 ), Complex.cartesian( -1.0, -1.0 ),
    };

    private static final double[] BASE_MULTIPLIERS = {
            Double.POSITIVE_INFINITY, 10.0, 0.1, 0.0, Double.NaN,
    };

    private static final Complex[] ZERO_POWERS = {
            Complex.real( 0.0 ), Complex.real( -0.0 ),
            Complex.imaginary( 0.0 ), Complex.imaginary( -0.0 ),
            Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( -0.0, -0.0 ),
    };


    private static final class ZeroPowerSamplesIterator implements Iterator<Object[]> {

        private static Complex convert( Complex base, double value ) {
            Complex temp = base.multiply( Complex.real( value ) );
            if ( temp.getRe() != temp.getRe() && base.getRe() == 0.0 ) {
                temp = Complex.cartesian( base.getRe(), temp.getIm() );
            }
            if ( temp.getIm() != temp.getIm() && base.getIm() == 0.0 ) {
                temp = Complex.cartesian( temp.getRe(), base.getIm() );
            }
            return temp;
        }


        private int baseIndex;

        private int baseMultiplierIndex;

        private int powerIndex;


        @Override
        public boolean hasNext() {
            return baseIndex < BASES.length && baseMultiplierIndex < BASE_MULTIPLIERS.length && powerIndex < ZERO_POWERS.length;
        }

        @Override
        public Object[] next() {
            if ( baseIndex < BASES.length && baseMultiplierIndex < BASE_MULTIPLIERS.length && powerIndex < ZERO_POWERS.length ) {
                Complex base = convert( BASES[ baseIndex ], BASE_MULTIPLIERS[ baseMultiplierIndex ] );
                Complex power = ZERO_POWERS[ powerIndex ];
                baseMultiplierIndex++;
                if ( baseMultiplierIndex >= BASE_MULTIPLIERS.length ) {
                    baseMultiplierIndex = 0;
                    powerIndex++;
                    if ( powerIndex >= ZERO_POWERS.length ) {
                        powerIndex = 0;
                        baseIndex++;
                    }
                }
                return new Object[ ] { base, power };
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}
