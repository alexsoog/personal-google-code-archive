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
import static oe.assertions.Predicates.isSameAs;
import static oe.assertions.Statement.value;

public class ComponentsTest {

    @Test( dataProvider = "componentsSamples" )
    public void components( Complex complex, double expectedRe, double expectedIm, double expectedAbsolute, double expectedArgument ) {
        assertThat(
                value( complex.getRe() ).describedAs( "the real part" ).satisfies( isSameAs( expectedRe ) ),
                value( complex.re() ).describedAs( "the real part as a Complex" ).satisfies( isEqualTo( Complex.real( expectedRe ) ) ),

                value( complex.getIm() ).describedAs( "the imaginary part" ).satisfies( isSameAs( expectedIm ) ),
                value( complex.im() ).describedAs( "the imaginary part as a Complex" ).satisfies( isEqualTo( Complex.real( expectedIm ) ) ),

                value( complex.getAbs() ).describedAs( "the absolute value" ).satisfies( isSameAs( expectedAbsolute ) ),
                value( complex.abs() ).describedAs( "the absolute value as a Complex" ).satisfies( isEqualTo( Complex.real( expectedAbsolute ) ) ),

                value( complex.getArg() ).describedAs( "the argument" ).satisfies( isSameAs( expectedArgument ) ),
                value( complex.arg() ).describedAs( "the argument as a Complex" ).satisfies( isEqualTo( Complex.real( expectedArgument ) ) )
        );
    }


    @DataProvider( name = "componentsSamples" )
    public Object[][] componentsSamples() {
        return new Object[ ][ ] {
                /*
                {complex,
                        expectedRe, expectedIm,
                        expectedAbsolute, expectedArgument}
                 */

                // real
                { Complex.real( 0.0 ),
                        0.0, 0.0,
                        0.0, 0.0 },
                { Complex.real( -0.0 ),
                        -0.0, 0.0,
                        0.0, Math.PI },

                { Complex.real( 1.0 ),
                        1.0, 0.0,
                        1.0, 0.0 },
                { Complex.real( -1.0 ),
                        -1.0, 0.0,
                        1.0, Math.PI },

                { Complex.real( Double.POSITIVE_INFINITY ),
                        Double.POSITIVE_INFINITY, 0.0,
                        Double.POSITIVE_INFINITY, 0.0 },
                { Complex.real( Double.NEGATIVE_INFINITY ),
                        Double.NEGATIVE_INFINITY, 0.0,
                        Double.POSITIVE_INFINITY, Math.PI },

                { Complex.real( Double.NaN ),
                        Double.NaN, 0.0,
                        Double.NaN, Double.NaN },

                // imaginary
                { Complex.imaginary( 0.0 ),
                        0.0, 0.0,
                        0.0, Math.PI / 2.0 },
                { Complex.imaginary( -0.0 ),
                        0.0, -0.0,
                        0.0, -Math.PI / 2.0 },

                { Complex.imaginary( 1.0 ),
                        0.0, 1.0,
                        1.0, Math.PI / 2.0 },
                { Complex.imaginary( -1.0 ),
                        0.0, -1.0,
                        1.0, -Math.PI / 2.0 },

                { Complex.imaginary( Double.POSITIVE_INFINITY ),
                        0.0, Double.POSITIVE_INFINITY,
                        Double.POSITIVE_INFINITY, Math.PI / 2.0 },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ),
                        0.0, Double.NEGATIVE_INFINITY,
                        Double.POSITIVE_INFINITY, -Math.PI / 2.0 },

                { Complex.imaginary( Double.NaN ),
                        0.0, Double.NaN,
                        Double.NaN, Double.NaN },

                // complex (real)
                { Complex.cartesian( 0.0, 0.0 ),
                        0.0, 0.0,
                        0.0, 0.0 },
                { Complex.cartesian( -0.0, 0.0 ),
                        -0.0, 0.0,
                        0.0, Math.PI },

                { Complex.cartesian( 1.0, 0.0 ),
                        1.0, 0.0,
                        1.0, 0.0 },
                { Complex.cartesian( -1.0, 0.0 ),
                        -1.0, 0.0,
                        1.0, Math.PI },

                { Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ),
                        Double.POSITIVE_INFINITY, 0.0,
                        Double.POSITIVE_INFINITY, 0.0 },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ),
                        Double.NEGATIVE_INFINITY, 0.0,
                        Double.POSITIVE_INFINITY, Math.PI },

                { Complex.cartesian( Double.NaN, 0.0 ),
                        Double.NaN, 0.0,
                        Double.NaN, Double.NaN },

                // complex (imaginary)
                { Complex.cartesian( 0.0, 0.0 ),
                        0.0, 0.0,
                        0.0, 0.0 },
                { Complex.cartesian( 0.0, -0.0 ),
                        0.0, -0.0,
                        0.0, -0.0 },

                { Complex.cartesian( 0.0, 1.0 ),
                        0.0, 1.0,
                        1.0, Math.PI / 2.0 },
                { Complex.cartesian( 0.0, -1.0 ),
                        0.0, -1.0,
                        1.0, -Math.PI / 2.0 },

                { Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ),
                        0.0, Double.POSITIVE_INFINITY,
                        Double.POSITIVE_INFINITY, Math.PI / 2.0 },
                { Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ),
                        0.0, Double.NEGATIVE_INFINITY,
                        Double.POSITIVE_INFINITY, -Math.PI / 2.0 },

                { Complex.cartesian( 0.0, Double.NaN ),
                        0.0, Double.NaN,
                        Double.NaN, Double.NaN },

                // complex (cartesian)
                { Complex.cartesian( 1.0, 1.0 ),
                        1.0, 1.0,
                        Math.sqrt( 2.0 ), Math.PI / 4.0 },
                { Complex.cartesian( 1.0, -1.0 ),
                        1.0, -1.0,
                        Math.sqrt( 2.0 ), -Math.PI / 4.0 },
                { Complex.cartesian( -1.0, 1.0 ),
                        -1.0, 1.0,
                        Math.sqrt( 2.0 ), 3.0 * Math.PI / 4.0 },
                { Complex.cartesian( -1.0, -1.0 ),
                        -1.0, -1.0,
                        Math.sqrt( 2.0 ), -3.0 * Math.PI / 4.0 },

                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ),
                        Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
                        Double.POSITIVE_INFINITY, Math.PI / 4.0 },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ),
                        Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
                        Double.POSITIVE_INFINITY, -Math.PI / 4.0 },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ),
                        Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                        Double.POSITIVE_INFINITY, 3.0 * Math.PI / 4.0 },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ),
                        Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                        Double.POSITIVE_INFINITY, -3.0 * Math.PI / 4.0 },

                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ),
                        Double.POSITIVE_INFINITY, Double.NaN,
                        Double.POSITIVE_INFINITY, Double.NaN },
                { Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ),
                        Double.NaN, Double.POSITIVE_INFINITY,
                        Double.POSITIVE_INFINITY, Double.NaN },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ),
                        Double.NEGATIVE_INFINITY, Double.NaN,
                        Double.POSITIVE_INFINITY, Double.NaN },
                { Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ),
                        Double.NaN, Double.NEGATIVE_INFINITY,
                        Double.POSITIVE_INFINITY, Double.NaN },

                { Complex.cartesian( 1.0, Double.NaN ),
                        1.0, Double.NaN,
                        Double.NaN, Double.NaN },
                { Complex.cartesian( Double.NaN, 1.0 ),
                        Double.NaN, 1.0,
                        Double.NaN, Double.NaN },
                { Complex.cartesian( -1.0, Double.NaN ),
                        -1.0, Double.NaN,
                        Double.NaN, Double.NaN },
                { Complex.cartesian( Double.NaN, -1.0 ),
                        Double.NaN, -1.0,
                        Double.NaN, Double.NaN },

                { Complex.cartesian( Double.NaN, Double.NaN ),
                        Double.NaN, Double.NaN,
                        Double.NaN, Double.NaN },

                // complex (polar)
                { Complex.polar( 0.0, 0.0 ),
                        0.0, 0.0,
                        0.0, 0.0 },

                { Complex.polar( 1.0, 0.0 ),
                        1.0, 0.0,
                        1.0, 0.0 },
                { Complex.polar( -1.0, 0.0 ),
                        -1.0, -0.0,
                        1.0, -Math.PI },

                { Complex.polar( Double.POSITIVE_INFINITY, 0.0 ),
                        Double.POSITIVE_INFINITY, 0.0,
                        Double.POSITIVE_INFINITY, 0.0 },
                { Complex.polar( Double.POSITIVE_INFINITY, -0.0 ),
                        Double.POSITIVE_INFINITY, -0.0,
                        Double.POSITIVE_INFINITY, -0.0 },
                { Complex.polar( Double.POSITIVE_INFINITY, Double.NaN ),
                        Double.NaN, Double.NaN,
                        Double.NaN, Double.NaN },
                { Complex.polar( Double.NEGATIVE_INFINITY, 0.0 ),
                        Double.NEGATIVE_INFINITY, -0.0,
                        Double.POSITIVE_INFINITY, -Math.PI },
                { Complex.polar( Double.NEGATIVE_INFINITY, -0.0 ),
                        Double.NEGATIVE_INFINITY, 0.0,
                        Double.POSITIVE_INFINITY, Math.PI },
                { Complex.polar( Double.NEGATIVE_INFINITY, Double.NaN ),
                        Double.NaN, Double.NaN,
                        Double.NaN, Double.NaN },

                { Complex.polar( 1.0, Double.POSITIVE_INFINITY ),
                        Double.NaN, Double.NaN,
                        Double.NaN, Double.NaN },
                { Complex.polar( 1.0, Double.NEGATIVE_INFINITY ),
                        Double.NaN, Double.NaN,
                        Double.NaN, Double.NaN },
                { Complex.polar( 1.0, Double.NaN ),
                        Double.NaN, Double.NaN,
                        Double.NaN, Double.NaN },
        };
    }

}
