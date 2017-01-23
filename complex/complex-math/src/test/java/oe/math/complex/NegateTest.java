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
import static oe.assertions.Predicates.isEqualTo;

public class NegateTest {

    @Test( dataProvider = "negateSamples" )
    public void negate( Complex complex, Complex expectedNegate ) {
        Complex actualNegate = complex.negate();
        assertThat( actualNegate, isEqualTo( expectedNegate ) );
    }

    @Test( dataProvider = "negateSamples" )
    public void negateIsInvolutary( Complex complex, Complex expectedNegate ) {
        Complex actualNegate = complex.negate().negate();
        assertThat( actualNegate, isEqualTo( complex ) );
    }


    @DataProvider( name = "negateSamples" )
    public Object[][] negateSamples() {
        return new Object[][] {
                /*
                {complex, expectedNegate}
                 */

                // real
                //// {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( 0.0 ), Complex.real( -0.0 ) },
                { Complex.real( -0.0 ), Complex.real( 0.0 ) },
                { Complex.real( 1.0 ), Complex.real( -1.0 ) },
                { Complex.real( -1.0 ), Complex.real( 1.0 ) },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.NEGATIVE_INFINITY ) },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( Double.NaN ), Complex.real( Double.NaN ) },


                // imaginary
                //// {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.imaginary( -0.0 ) },
                { Complex.imaginary( -0.0 ), Complex.imaginary( 0.0 ) },
                { Complex.imaginary( 1.0 ), Complex.imaginary( -1.0 ) },
                { Complex.imaginary( -1.0 ), Complex.imaginary( 1.0 ) },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ) },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ) },
                { Complex.imaginary( Double.NaN ), Complex.imaginary( Double.NaN ) },


                // complex
                //// {0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 1.0 ), Complex.cartesian( -0.0, -1.0 ) },
                { Complex.cartesian( 0.0, -1.0 ), Complex.cartesian( -0.0, 1.0 ) },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, -0.0 ) },
                { Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( -0.0, 0.0 ) },
                { Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ) },
                { Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ) },
                { Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( -0.0, Double.NaN ) },

                //// {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r + {+0.0}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, -0.0 ) },
                { Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 0.0, -0.0 ) },
                { Complex.cartesian( 1.0, 0.0 ), Complex.cartesian( -1.0, -0.0 ) },
                { Complex.cartesian( -1.0, 0.0 ), Complex.cartesian( 1.0, -0.0 ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ) },
                { Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, -0.0 ) },

                //// {+fin, -fin}r + {+fin, -fin}i
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -1.0, -2.0 ) },
                { Complex.cartesian( 1.0, -2.0 ), Complex.cartesian( -1.0, 2.0 ) },
                { Complex.cartesian( -1.0, 2.0 ), Complex.cartesian( 1.0, -2.0 ) },
                { Complex.cartesian( -1.0, -2.0 ), Complex.cartesian( 1.0, 2.0 ) },
        };
    }

}
