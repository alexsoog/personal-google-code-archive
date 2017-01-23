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

public class AddTest {

    @Test( dataProvider = "addSamples" )
    public void add( Complex ab, Complex cd, Complex expectedSum, boolean conjugateIsDistributive ) {
        Complex actualSum = ab.add( cd );
        assertThat( actualSum, isEqualTo( expectedSum ) );
    }

    @Test( dataProvider = "addSamples" )
    public void addIsCommutative( Complex ab, Complex cd, Complex expectedSum, boolean conjugateIsDistributive ) {
        Complex actualSum = cd.add( ab );
        assertThat( actualSum, isEqualTo( expectedSum ) );
    }

    @Test( dataProvider = "addSamples" )
    public void conjugateIsDistributiveOverAdd( Complex ab, Complex cd, Complex expectedSum, boolean conjugateIsDistributive ) {
        ab = ab.conjugate();
        cd = cd.conjugate();
        if ( conjugateIsDistributive ) {
            expectedSum = expectedSum.conjugate();
        } else {
            /*
            if b and d are zeros of the different sign then Im(result) is always positive zero
             */
        }
        Complex actualSum = ab.add( cd );
        assertThat( actualSum, isEqualTo( expectedSum ) );
    }


    @DataProvider( name = "addSamples" )
    public Object[][] addSamples() {
        return new Object[][] {
                /*
                {ab, cd, ab + cd}
                 */

                // real  @+  real
                //// {+0.0}r  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( 0.0 ), Complex.real( 0.0 ), Complex.real( 0.0 ), true },
                { Complex.real( 0.0 ), Complex.real( -0.0 ), Complex.real( 0.0 ), true },
                { Complex.real( 0.0 ), Complex.real( 3.0 ), Complex.real( 3.0 ), true },
                { Complex.real( 0.0 ), Complex.real( -3.0 ), Complex.real( -3.0 ), true },
                { Complex.real( 0.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.real( Double.NaN ), Complex.real( Double.NaN ), true },

                //// {-0.0}r  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( -0.0 ), Complex.real( 0.0 ), Complex.real( 0.0 ), true },
                { Complex.real( -0.0 ), Complex.real( -0.0 ), Complex.real( -0.0 ), true },
                { Complex.real( -0.0 ), Complex.real( 3.0 ), Complex.real( 3.0 ), true },
                { Complex.real( -0.0 ), Complex.real( -3.0 ), Complex.real( -3.0 ), true },
                { Complex.real( -0.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ), true },
                { Complex.real( -0.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NEGATIVE_INFINITY ), true },
                { Complex.real( -0.0 ), Complex.real( Double.NaN ), Complex.real( Double.NaN ), true },

                //// {+fin}r  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( 1.0 ), Complex.real( 0.0 ), Complex.real( 1.0 ), true },
                { Complex.real( 1.0 ), Complex.real( -0.0 ), Complex.real( 1.0 ), true },
                { Complex.real( 1.0 ), Complex.real( 3.0 ), Complex.real( 4.0 ), true },
                { Complex.real( 1.0 ), Complex.real( -3.0 ), Complex.real( -2.0 ), true },
                { Complex.real( 1.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.real( Double.NaN ), Complex.real( Double.NaN ), true },

                //// {-fin}r  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( -1.0 ), Complex.real( 0.0 ), Complex.real( -1.0 ), true },
                { Complex.real( -1.0 ), Complex.real( -0.0 ), Complex.real( -1.0 ), true },
                { Complex.real( -1.0 ), Complex.real( 3.0 ), Complex.real( 2.0 ), true },
                { Complex.real( -1.0 ), Complex.real( -3.0 ), Complex.real( -4.0 ), true },
                { Complex.real( -1.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ), true },
                { Complex.real( -1.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NEGATIVE_INFINITY ), true },
                { Complex.real( -1.0 ), Complex.real( Double.NaN ), Complex.real( Double.NaN ), true },

                //// {+inf}r  @+  {0.0, -0.0, finite, -fin, +inf, -inf, nan}r
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( 0.0 ), Complex.real( Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( -0.0 ), Complex.real( Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( 3.0 ), Complex.real( Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( -3.0 ), Complex.real( Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NaN ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.NaN ), Complex.real( Double.NaN ), true },

                //// {-inf}r  @+  {0.0, -0.0, finite, -fin, +inf, -inf, nan}r
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( 0.0 ), Complex.real( Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( -0.0 ), Complex.real( Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( 3.0 ), Complex.real( Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( -3.0 ), Complex.real( Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.NaN ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NaN ), Complex.real( Double.NaN ), true },

                //// {nan}r  @+  {0.0, -0.0, finite, -fin, +inf, -inf, nan}r
                { Complex.real( Double.NaN ), Complex.real( 0.0 ), Complex.real( Double.NaN ), true },
                { Complex.real( Double.NaN ), Complex.real( -0.0 ), Complex.real( Double.NaN ), true },
                { Complex.real( Double.NaN ), Complex.real( 3.0 ), Complex.real( Double.NaN ), true },
                { Complex.real( Double.NaN ), Complex.real( -3.0 ), Complex.real( Double.NaN ), true },
                { Complex.real( Double.NaN ), Complex.real( Double.POSITIVE_INFINITY ), Complex.real( Double.NaN ), true },
                { Complex.real( Double.NaN ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.real( Double.NaN ), true },
                { Complex.real( Double.NaN ), Complex.real( Double.NaN ), Complex.real( Double.NaN ), true },


                // real  @+  imaginary
                //// {+0.0}r  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, 0.0 ), true },
                { Complex.real( 0.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( 0.0, -0.0 ), true },
                { Complex.real( 0.0 ), Complex.imaginary( 4.0 ), Complex.cartesian( 0.0, 4.0 ), true },
                { Complex.real( 0.0 ), Complex.imaginary( -4.0 ), Complex.cartesian( 0.0, -4.0 ), true },
                { Complex.real( 0.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( 0.0, Double.NaN ), true },

                //// {-0.0}r  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -0.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, 0.0 ), true },
                { Complex.real( -0.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( -0.0, -0.0 ), true },
                { Complex.real( -0.0 ), Complex.imaginary( 4.0 ), Complex.cartesian( -0.0, 4.0 ), true },
                { Complex.real( -0.0 ), Complex.imaginary( -4.0 ), Complex.cartesian( -0.0, -4.0 ), true },
                { Complex.real( -0.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.real( -0.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( -0.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( -0.0, Double.NaN ), true },

                //// {+fin}r  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 0.0 ), true },
                { Complex.real( 1.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, -0.0 ), true },
                { Complex.real( 1.0 ), Complex.imaginary( 4.0 ), Complex.cartesian( 1.0, 4.0 ), true },
                { Complex.real( 1.0 ), Complex.imaginary( -4.0 ), Complex.cartesian( 1.0, -4.0 ), true },
                { Complex.real( 1.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 1.0, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( 1.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( 1.0, Double.NaN ), true },

                //// {-fin}r  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( -1.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( -1.0, 0.0 ), true },
                { Complex.real( -1.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( -1.0, -0.0 ), true },
                { Complex.real( -1.0 ), Complex.imaginary( 4.0 ), Complex.cartesian( -1.0, 4.0 ), true },
                { Complex.real( -1.0 ), Complex.imaginary( -4.0 ), Complex.cartesian( -1.0, -4.0 ), true },
                { Complex.real( -1.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -1.0, Double.POSITIVE_INFINITY ), true },
                { Complex.real( -1.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( -1.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( -1.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( -1.0, Double.NaN ), true },

                //// {+inf}r  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {-inf}r  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( 4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( -4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), true },

                //// {nan}r  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( Double.NaN ), Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, 0.0 ), true },
                { Complex.real( Double.NaN ), Complex.imaginary( -0.0 ), Complex.cartesian( Double.NaN, -0.0 ), true },
                { Complex.real( Double.NaN ), Complex.imaginary( 4.0 ), Complex.cartesian( Double.NaN, 4.0 ), true },
                { Complex.real( Double.NaN ), Complex.imaginary( -4.0 ), Complex.cartesian( Double.NaN, -4.0 ), true },
                { Complex.real( Double.NaN ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.NaN ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.NaN ), Complex.imaginary( Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },


                // real  @+  complex
                //// {+0.0}r  @+  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, 0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 0.0, -0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( 0.0, 4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( 0.0, -4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( 0.0, Double.NaN ), true },

                //// {+0.0}r  @+  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 0.0, 0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 0.0, -0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( 0.0, 4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( 0.0, -4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( 0.0, Double.NaN ), true },

                //// {+0.0}r  @+  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( 3.0, 0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( 3.0, -0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( 3.0, 4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( 3.0, -4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( 3.0, Double.NaN ), true },

                //// {+0.0}r  @+  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( -3.0, 0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( -3.0, -0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( -3.0, 4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( -3.0, -4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( -3.0, Double.NaN ), true },

                //// {+0.0}r  @+  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+0.0}r  @+  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), true },

                //// {+0.0}r  @+  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, 0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, -0.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, 4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, -4.0 ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                //// {+fin}r  @+  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 1.0, -0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( 1.0, 4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( 1.0, -4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 1.0, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 1.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( 1.0, Double.NaN ), true },

                //// {+fin}r  @+  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 1.0, 0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 1.0, -0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( 1.0, 4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( 1.0, -4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 1.0, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 1.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( 1.0, Double.NaN ), true },

                //// {+fin}r  @+  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( 4.0, 0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( 4.0, -0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( 4.0, 4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( 4.0, -4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 4.0, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 4.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( 4.0, Double.NaN ), true },

                //// {+fin}r  @+  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( -2.0, 0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( -2.0, -0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( -2.0, 4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( -2.0, -4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( -2.0, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( -2.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( -2.0, Double.NaN ), true },

                //// {+fin}r  @+  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+fin}r  @+  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), true },

                //// {+fin}r  @+  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, 0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, -0.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, 4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, -4.0 ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( 1.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                //// {+inf}r  @+  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+inf}r  @+  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+inf}r  @+  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+inf}r  @+  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+inf}r  @+  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+inf}r  @+  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, 0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, -0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, 4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, -4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                //// {+inf}r  @+  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, 0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, -0.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, 4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, -4.0 ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), true },
                { Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },


                // imaginary  @+  real
                //// {+fin}i  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.imaginary( 2.0 ), Complex.real( 0.0 ), Complex.cartesian( 0.0, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.real( -0.0 ), Complex.cartesian( -0.0, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.real( 1.0 ), Complex.cartesian( 1.0, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.real( -1.0 ), Complex.cartesian( -1.0, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.real( Double.NaN ), Complex.cartesian( Double.NaN, 2.0 ), true },


                // imaginary  @+  imaginary
                //// {+0.0}i  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.imaginary( 0.0 ), Complex.imaginary( 0.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.imaginary( -0.0 ), Complex.imaginary( 0.0 ), false },
                { Complex.imaginary( 0.0 ), Complex.imaginary( 4.0 ), Complex.imaginary( 4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.imaginary( -4.0 ), Complex.imaginary( -4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.imaginary( Double.NaN ), Complex.imaginary( Double.NaN ), true },

                //// {-0.0}i  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( -0.0 ), Complex.imaginary( 0.0 ), Complex.imaginary( 0.0 ), false },
                { Complex.imaginary( -0.0 ), Complex.imaginary( -0.0 ), Complex.imaginary( -0.0 ), true },
                { Complex.imaginary( -0.0 ), Complex.imaginary( 4.0 ), Complex.imaginary( 4.0 ), true },
                { Complex.imaginary( -0.0 ), Complex.imaginary( -4.0 ), Complex.imaginary( -4.0 ), true },
                { Complex.imaginary( -0.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( -0.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( -0.0 ), Complex.imaginary( Double.NaN ), Complex.imaginary( Double.NaN ), true },

                //// {+fin}i  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.imaginary( 0.0 ), Complex.imaginary( 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.imaginary( -0.0 ), Complex.imaginary( 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.imaginary( 4.0 ), Complex.imaginary( 6.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.imaginary( -4.0 ), Complex.imaginary( -2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.imaginary( Double.NaN ), Complex.imaginary( Double.NaN ), true },

                //// {-fin}i  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( -2.0 ), Complex.imaginary( 0.0 ), Complex.imaginary( -2.0 ), true },
                { Complex.imaginary( -2.0 ), Complex.imaginary( -0.0 ), Complex.imaginary( -2.0 ), true },
                { Complex.imaginary( -2.0 ), Complex.imaginary( 4.0 ), Complex.imaginary( 2.0 ), true },
                { Complex.imaginary( -2.0 ), Complex.imaginary( -4.0 ), Complex.imaginary( -6.0 ), true },
                { Complex.imaginary( -2.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( -2.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( -2.0 ), Complex.imaginary( Double.NaN ), Complex.imaginary( Double.NaN ), true },

                //// {+inf}i  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( 0.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( -0.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( 4.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( -4.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NaN ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.NaN ), Complex.imaginary( Double.NaN ), true },

                //// {-inf}i  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( 0.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( -0.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( 4.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( -4.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.NaN ), true },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NaN ), Complex.imaginary( Double.NaN ), true },

                //// {nan}i  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( Double.NaN ), Complex.imaginary( 0.0 ), Complex.imaginary( Double.NaN ), true },
                { Complex.imaginary( Double.NaN ), Complex.imaginary( -0.0 ), Complex.imaginary( Double.NaN ), true },
                { Complex.imaginary( Double.NaN ), Complex.imaginary( 4.0 ), Complex.imaginary( Double.NaN ), true },
                { Complex.imaginary( Double.NaN ), Complex.imaginary( -4.0 ), Complex.imaginary( Double.NaN ), true },
                { Complex.imaginary( Double.NaN ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.imaginary( Double.NaN ), true },
                { Complex.imaginary( Double.NaN ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.imaginary( Double.NaN ), true },
                { Complex.imaginary( Double.NaN ), Complex.imaginary( Double.NaN ), Complex.imaginary( Double.NaN ), true },


                // imaginary  @+  complex
                //// {+0.0}i  @+  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, 0.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 0.0, 0.0 ), false },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( 0.0, 4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( 0.0, -4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( 0.0, Double.NaN ), true },

                //// {+0.0}i  @+  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( -0.0, 0.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( -0.0, 0.0 ), false },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( -0.0, 4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( -0.0, -4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( -0.0, Double.NaN ), true },

                //// {+0.0}i  @+  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( 3.0, 0.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( 3.0, 0.0 ), false },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( 3.0, 4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( 3.0, -4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( 3.0, Double.NaN ), true },

                //// {+0.0}i  @+  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( -3.0, 0.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( -3.0, 0.0 ), false },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( -3.0, 4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( -3.0, -4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( -3.0, Double.NaN ), true },

                //// {+0.0}i  @+  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), false },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+0.0}i  @+  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), false },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), true },

                //// {+0.0}i  @+  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, 0.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, 0.0 ), false },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, 4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, -4.0 ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                //// {+fin}i  @+  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 0.0, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( 0.0, 6.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( 0.0, -2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( 0.0, Double.NaN ), true },

                //// {+fin}i  @+  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( -0.0, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( -0.0, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( -0.0, 6.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( -0.0, -2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( -0.0, Double.NaN ), true },

                //// {+fin}i  @+  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( 3.0, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( 3.0, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( 3.0, 6.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( 3.0, -2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( 3.0, Double.NaN ), true },

                //// {+fin}i  @+  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( -3.0, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( -3.0, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( -3.0, 6.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( -3.0, -2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( -3.0, Double.NaN ), true },

                //// {+fin}i  @+  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 6.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+fin}i  @+  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 6.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), true },

                //// {+fin}i  @+  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, 2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, 6.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, -2.0 ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), true },
                { Complex.imaginary( 2.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                //// {+inf}i  @+  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 0.0, Double.NaN ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( 0.0, Double.NaN ), true },

                //// {+inf}i  @+  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( -0.0, Double.NaN ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( -0.0, Double.NaN ), true },

                //// {+inf}i  @+  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 3.0, Double.NaN ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( 3.0, Double.NaN ), true },

                //// {+inf}i  @+  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( -3.0, Double.NaN ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( -3.0, Double.NaN ), true },

                //// {+inf}i  @+  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+inf}i  @+  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), true },

                //// {+inf}i  @+  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ), true },
                { Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },


                // complex  @+  real
                //// {+fin}r + {+fin}i  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.cartesian( 1.0, 2.0 ), Complex.real( 0.0 ), Complex.cartesian( 1.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.real( -0.0 ), Complex.cartesian( 1.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.real( 3.0 ), Complex.cartesian( 4.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.real( -3.0 ), Complex.cartesian( -2.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.real( Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.real( Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.real( Double.NaN ), Complex.cartesian( Double.NaN, 2.0 ), true },


                // complex  @+  imaginary
                //// {+fin}r + {+fin}i  @+  {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 2.0 ), Complex.imaginary( 0.0 ), Complex.cartesian( 1.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.imaginary( -0.0 ), Complex.cartesian( 1.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.imaginary( 4.0 ), Complex.cartesian( 1.0, 6.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.imaginary( -4.0 ), Complex.cartesian( 1.0, -2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.imaginary( Double.POSITIVE_INFINITY ), Complex.cartesian( 1.0, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.imaginary( Double.NEGATIVE_INFINITY ), Complex.cartesian( 1.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.imaginary( Double.NaN ), Complex.cartesian( 1.0, Double.NaN ), true },


                // complex  @+  complex
                //// {+0.0}r + {+0.0}i  @+  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, 0.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 0.0, 0.0 ), false },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( 0.0, 4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( 0.0, -4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( 0.0, Double.NaN ), true },

                //// {+0.0}r + {+0.0}i  @+  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 0.0, 0.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 0.0, 0.0 ), false },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( 0.0, 4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( 0.0, -4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( 0.0, Double.NaN ), true },

                //// {+0.0}r + {+0.0}i  @+  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( 3.0, 0.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( 3.0, 0.0 ), false },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( 3.0, 4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( 3.0, -4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( 3.0, Double.NaN ), true },

                //// {+0.0}r + {+0.0}i  @+  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( -3.0, 0.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( -3.0, 0.0 ), false },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( -3.0, 4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( -3.0, -4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( -3.0, Double.NaN ), true },

                //// {+0.0}r + {+0.0}i  @+  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), false },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+0.0}r + {+0.0}i  @+  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), false },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), true },

                //// {+0.0}r + {+0.0}i  @+  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, 0.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, 0.0 ), false },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, 4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, -4.0 ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                //// {+fin}r + {+fin}i  @+  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( 1.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( 1.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( 1.0, 6.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( 1.0, -2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 1.0, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 1.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( 1.0, Double.NaN ), true },

                //// {+fin}r + {+fin}i  @+  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( 1.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( 1.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( 1.0, 6.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( 1.0, -2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 1.0, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 1.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( 1.0, Double.NaN ), true },

                //// {+fin}r + {+fin}i  @+  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( 4.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( 4.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( 4.0, 6.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( 4.0, -2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( 4.0, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( 4.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( 4.0, Double.NaN ), true },

                //// {+fin}r + {+fin}i  @+  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( -2.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( -2.0, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( -2.0, 6.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( -2.0, -2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( -2.0, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( -2.0, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( -2.0, Double.NaN ), true },

                //// {+fin}r + {+fin}i  @+  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, 6.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, -2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+fin}r + {+fin}i  @+  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, 6.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, -2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), true },

                //// {+fin}r + {+fin}i  @+  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, 2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, 6.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, -2.0 ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), true },
                { Complex.cartesian( 1.0, 2.0 ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                //// {+inf}r + {+inf}i  @+  {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 0.0, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+inf}r + {+inf}i  @+  {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -0.0, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+inf}r + {+inf}i  @+  {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( 3.0, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+inf}r + {+inf}i  @+  {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( -3.0, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+inf}r + {+inf}i  @+  {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, 4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, -4.0 ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ), true },

                //// {+inf}r + {+inf}i  @+  {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, 4.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, -4.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },

                //// {+inf}r + {+inf}i  @+  {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, 0.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, -0.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, 4.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, -4.0 ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ), true },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ), Complex.cartesian( Double.NaN, Double.NaN ), Complex.cartesian( Double.NaN, Double.NaN ), true },
        };
    }

}
