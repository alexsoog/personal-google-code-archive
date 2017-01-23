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

import java.text.ParsePosition;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static oe.assertions.Assertions.assertThat;
import static oe.assertions.Predicates.isEqualTo;
import static oe.assertions.Statement.value;

public class ComplexFormatTest {

    @Test( dataProvider = "roundtripSamples" )
    public void roundtrip( Complex complex ) {
        String string = complex.toString();
        Complex parsedComplex = Complex.fromString( string );

        assertThat( parsedComplex, isEqualTo( complex ) );
    }

    @Test( dataProvider = "partialSamples" )
    public void partial( String text, Complex complex, int index, int errorIndex ) {
        ComplexFormat format = new ComplexFormat();
        format.setParsePartial( true );
        ParsePosition pos = new ParsePosition( 0 );
        Complex parsed = format.parse( text, pos );

        assertThat(
                value( parsed ).describedAs( "the parsed value" ).satisfies( isEqualTo( complex ) ),
                value( pos.getIndex() ).describedAs( "the parsing position" ).satisfies( isEqualTo( index ) ),
                value( pos.getErrorIndex() ).describedAs( "the error position" ).satisfies( isEqualTo( errorIndex ) ) );
    }


    @DataProvider( name = "roundtripSamples" )
    public Object[][] roundtripSamples() {
        return new Object[ ][ ] {
                /*
                {complex}
                 */

                // real
                //// {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}r
                { Complex.real( 0.0 ) },
                { Complex.real( -0.0 ) },
                { Complex.real( 1.0 ) },
                { Complex.real( -1.0 ) },
                { Complex.real( Math.PI ) },
                { Complex.real( Math.PI ) },
                { Complex.real( Double.POSITIVE_INFINITY ) },
                { Complex.real( Double.NEGATIVE_INFINITY ) },
                { Complex.real( Double.NaN ) },

                // imaginary
                //// {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.imaginary( 0.0 ) },
                { Complex.imaginary( -0.0 ) },
                { Complex.imaginary( 1.0 ) },
                { Complex.imaginary( -1.0 ) },
                { Complex.imaginary( Math.PI ) },
                { Complex.imaginary( -Math.PI ) },
                { Complex.imaginary( Double.POSITIVE_INFINITY ) },
                { Complex.imaginary( Double.NEGATIVE_INFINITY ) },
                { Complex.imaginary( Double.NaN ) },

                // complex
                //// {+0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( 0.0, 0.0 ) },
                { Complex.cartesian( 0.0, -0.0 ) },
                { Complex.cartesian( 0.0, 1.0 ) },
                { Complex.cartesian( 0.0, -1.0 ) },
                { Complex.cartesian( 0.0, Math.PI ) },
                { Complex.cartesian( 0.0, -Math.PI ) },
                { Complex.cartesian( 0.0, Double.POSITIVE_INFINITY ) },
                { Complex.cartesian( 0.0, Double.NEGATIVE_INFINITY ) },
                { Complex.cartesian( 0.0, Double.NaN ) },

                //// {-0.0}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( -0.0, 0.0 ) },
                { Complex.cartesian( -0.0, -0.0 ) },
                { Complex.cartesian( -0.0, 1.0 ) },
                { Complex.cartesian( -0.0, -1.0 ) },
                { Complex.cartesian( -0.0, Math.PI ) },
                { Complex.cartesian( -0.0, -Math.PI ) },
                { Complex.cartesian( -0.0, Double.POSITIVE_INFINITY ) },
                { Complex.cartesian( -0.0, Double.NEGATIVE_INFINITY ) },
                { Complex.cartesian( -0.0, Double.NaN ) },

                //// {+fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Math.PI, 0.0 ) },
                { Complex.cartesian( Math.PI, -0.0 ) },
                { Complex.cartesian( Math.PI, 1.0 ) },
                { Complex.cartesian( Math.PI, -1.0 ) },
                { Complex.cartesian( Math.PI, Math.PI ) },
                { Complex.cartesian( Math.PI, -Math.PI ) },
                { Complex.cartesian( Math.PI, Double.POSITIVE_INFINITY ) },
                { Complex.cartesian( Math.PI, Double.NEGATIVE_INFINITY ) },
                { Complex.cartesian( Math.PI, Double.NaN ) },

                //// {-fin}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( -Math.PI, 0.0 ) },
                { Complex.cartesian( -Math.PI, -0.0 ) },
                { Complex.cartesian( -Math.PI, 1.0 ) },
                { Complex.cartesian( -Math.PI, -1.0 ) },
                { Complex.cartesian( -Math.PI, Math.PI ) },
                { Complex.cartesian( -Math.PI, -Math.PI ) },
                { Complex.cartesian( -Math.PI, Double.POSITIVE_INFINITY ) },
                { Complex.cartesian( -Math.PI, Double.NEGATIVE_INFINITY ) },
                { Complex.cartesian( -Math.PI, Double.NaN ) },

                //// {+inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.POSITIVE_INFINITY, 0.0 ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, -0.0 ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, 1.0 ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, -1.0 ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Math.PI ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, -Math.PI ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY ) },
                { Complex.cartesian( Double.POSITIVE_INFINITY, Double.NaN ) },

                //// {-inf}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.NEGATIVE_INFINITY, 0.0 ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, -0.0 ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, 1.0 ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, -1.0 ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Math.PI ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, -Math.PI ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY ) },
                { Complex.cartesian( Double.NEGATIVE_INFINITY, Double.NaN ) },

                //// {nan}r + {+0.0, -0.0, +fin, -fin, +inf, -inf, nan}i
                { Complex.cartesian( Double.NaN, 0.0 ) },
                { Complex.cartesian( Double.NaN, -0.0 ) },
                { Complex.cartesian( Double.NaN, 1.0 ) },
                { Complex.cartesian( Double.NaN, -1.0 ) },
                { Complex.cartesian( Double.NaN, Math.PI ) },
                { Complex.cartesian( Double.NaN, -Math.PI ) },
                { Complex.cartesian( Double.NaN, Double.POSITIVE_INFINITY ) },
                { Complex.cartesian( Double.NaN, Double.NEGATIVE_INFINITY ) },
                { Complex.cartesian( Double.NaN, Double.NaN ) },
        };
    }

    @DataProvider( name = "partialSamples" )
    public Object[][] partialSamples() {
        return new Object[ ][ ] {
                { "", null, 0, 0 },
                { "i", null, 0, 0 },

                { "1.0", Complex.real( 1.0 ), 3, -1 },
                { " 1.0", Complex.real( 1.0 ), 4, -1 },
                { "1.0 ", Complex.real( 1.0 ), 3, -1 },
                { " 1.0 ", Complex.real( 1.0 ), 4, -1 },

                { "1.0+2.0i", Complex.real( 1.0 ), 3, -1 },
                { " 1.0+2.0i", Complex.real( 1.0 ), 4, -1 },
                { "1.0 + 2.0i", Complex.real( 1.0 ), 3, -1 },
                { " 1.0 + 2.0i", Complex.real( 1.0 ), 4, -1 },

                { "1.0i", Complex.imaginary( 1.0 ), 4, -1 },
                { " 1.0i", Complex.imaginary( 1.0 ), 5, -1 },
                { "1.0 i", Complex.real( 1.0 ), 3, -1 },
                { " 1.0 i", Complex.real( 1.0 ), 4, -1 },

                { "1.0i+2.0", Complex.imaginary( 1.0 ), 4, -1 },
                { " 1.0i+2.0", Complex.imaginary( 1.0 ), 5, -1 },
                { "1.0i + 2.0", Complex.imaginary( 1.0 ), 4, -1 },
                { " 1.0i + 2.0", Complex.imaginary( 1.0 ), 5, -1 },

                { "1.0 i+2.0", Complex.real( 1.0 ), 3, -1 },
                { "1.0 i + 2.0", Complex.real( 1.0 ), 3, -1 },
                { " 1.0 i + 2.0", Complex.real( 1.0 ), 4, -1 },

                { "1.0int", Complex.real( 1.0 ), 3, -1 },
                { "1.0 int", Complex.real( 1.0 ), 3, -1 },
        };
    }

}
