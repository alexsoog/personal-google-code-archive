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

public class ExpressionFormatTest {

    @Test( dataProvider = "roundtripSamples" )
    public void roundtripPostfix( Expression expression ) {
        String string = expression.toPostfixString();
        Expression parsedExpression = Expression.fromPostfixString( string );

        assertThat( parsedExpression, isEqualTo( expression ) );
    }

    @Test( dataProvider = "roundtripSamples" )
    public void roundtripInfix( Expression expression ) {
        String string = expression.toString();
        Expression parsedExpression = Expression.fromString( string );

        assertThat( parsedExpression.simplify(), isEqualTo( expression.simplify() ) );
    }


    @DataProvider( name = "roundtripSamples" )
    public Object[][] roundtripSamples() {
        return new Object[][] {
                { Expression.postfix().real( 1.0 ).create() },
                { Expression.postfix().real( -1.0 ).create() },

                { Expression.postfix().imaginary( 2.0 ).create() },
                { Expression.postfix().imaginary( -2.0 ).create() },

                { Expression.postfix().cartesian( 1.0, 2.0 ).create() },
                { Expression.postfix().cartesian( 1.0, -2.0 ).create() },
                { Expression.postfix().cartesian( -1.0, 2.0 ).create() },
                { Expression.postfix().cartesian( -1.0, -2.0 ).create() },

                { Expression.postfix().polar( 1.0, 0.0 ).create() },

                { Expression.postfix().variable( "variable" ).create() },

                // add
                { Expression.postfix().real( 1.0 ).real( 2.0 ).add().create() },
                { Expression.postfix().real( 1.0 ).real( -2.0 ).add().create() },
                { Expression.postfix().real( -1.0 ).real( 2.0 ).add().create() },
                { Expression.postfix().real( -1.0 ).real( -2.0 ).add().create() },

                { Expression.postfix().imaginary( 1.0 ).imaginary( 2.0 ).add().create() },
                { Expression.postfix().imaginary( 1.0 ).imaginary( -2.0 ).add().create() },
                { Expression.postfix().imaginary( -1.0 ).imaginary( 2.0 ).add().create() },
                { Expression.postfix().imaginary( -1.0 ).imaginary( -2.0 ).add().create() },

                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).add().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, -4.0 ).add().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( -3.0, 4.0 ).add().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( -3.0, -4.0 ).add().create() },
                { Expression.postfix().cartesian( 1.0, -2.0 ).cartesian( 3.0, 4.0 ).add().create() },
                { Expression.postfix().cartesian( -1.0, 2.0 ).cartesian( 3.0, 4.0 ).add().create() },
                { Expression.postfix().cartesian( -1.0, -2.0 ).cartesian( 3.0, 4.0 ).add().create() },

                // subtract
                { Expression.postfix().real( 1.0 ).real( 2.0 ).subtract().create() },
                { Expression.postfix().real( 1.0 ).real( -2.0 ).subtract().create() },
                { Expression.postfix().real( -1.0 ).real( 2.0 ).subtract().create() },
                { Expression.postfix().real( -1.0 ).real( -2.0 ).subtract().create() },

                { Expression.postfix().imaginary( 1.0 ).imaginary( 2.0 ).subtract().create() },
                { Expression.postfix().imaginary( 1.0 ).imaginary( -2.0 ).subtract().create() },
                { Expression.postfix().imaginary( -1.0 ).imaginary( 2.0 ).subtract().create() },
                { Expression.postfix().imaginary( -1.0 ).imaginary( -2.0 ).subtract().create() },

                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).subtract().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, -4.0 ).subtract().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( -3.0, 4.0 ).subtract().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( -3.0, -4.0 ).subtract().create() },
                { Expression.postfix().cartesian( 1.0, -2.0 ).cartesian( 3.0, 4.0 ).subtract().create() },
                { Expression.postfix().cartesian( -1.0, 2.0 ).cartesian( 3.0, 4.0 ).subtract().create() },
                { Expression.postfix().cartesian( -1.0, -2.0 ).cartesian( 3.0, 4.0 ).subtract().create() },

                // multiply
                { Expression.postfix().real( 1.0 ).real( 2.0 ).multiply().create() },
                { Expression.postfix().real( 1.0 ).real( -2.0 ).multiply().create() },
                { Expression.postfix().real( -1.0 ).real( 2.0 ).multiply().create() },
                { Expression.postfix().real( -1.0 ).real( -2.0 ).multiply().create() },

                { Expression.postfix().imaginary( 1.0 ).imaginary( 2.0 ).multiply().create() },
                { Expression.postfix().imaginary( 1.0 ).imaginary( -2.0 ).multiply().create() },
                { Expression.postfix().imaginary( -1.0 ).imaginary( 2.0 ).multiply().create() },
                { Expression.postfix().imaginary( -1.0 ).imaginary( -2.0 ).multiply().create() },

                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).multiply().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, -4.0 ).multiply().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( -3.0, 4.0 ).multiply().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( -3.0, -4.0 ).multiply().create() },
                { Expression.postfix().cartesian( 1.0, -2.0 ).cartesian( 3.0, 4.0 ).multiply().create() },
                { Expression.postfix().cartesian( -1.0, 2.0 ).cartesian( 3.0, 4.0 ).multiply().create() },
                { Expression.postfix().cartesian( -1.0, -2.0 ).cartesian( 3.0, 4.0 ).multiply().create() },

                // divide
                { Expression.postfix().real( 1.0 ).real( 2.0 ).divide().create() },
                { Expression.postfix().real( 1.0 ).real( -2.0 ).divide().create() },
                { Expression.postfix().real( -1.0 ).real( 2.0 ).divide().create() },
                { Expression.postfix().real( -1.0 ).real( -2.0 ).divide().create() },

                { Expression.postfix().imaginary( 1.0 ).imaginary( 2.0 ).divide().create() },
                { Expression.postfix().imaginary( 1.0 ).imaginary( -2.0 ).divide().create() },
                { Expression.postfix().imaginary( -1.0 ).imaginary( 2.0 ).divide().create() },
                { Expression.postfix().imaginary( -1.0 ).imaginary( -2.0 ).divide().create() },

                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).divide().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, -4.0 ).divide().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( -3.0, 4.0 ).divide().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( -3.0, -4.0 ).divide().create() },
                { Expression.postfix().cartesian( 1.0, -2.0 ).cartesian( 3.0, 4.0 ).divide().create() },
                { Expression.postfix().cartesian( -1.0, 2.0 ).cartesian( 3.0, 4.0 ).divide().create() },
                { Expression.postfix().cartesian( -1.0, -2.0 ).cartesian( 3.0, 4.0 ).divide().create() },

                // negate
                { Expression.postfix().real( 1.0 ).negate().create() },
                { Expression.postfix().real( -1.0 ).negate().create() },

                { Expression.postfix().imaginary( 2.0 ).negate().create() },
                { Expression.postfix().imaginary( -2.0 ).negate().create() },

                { Expression.postfix().cartesian( 1.0, 2.0 ).negate().create() },
                { Expression.postfix().cartesian( 1.0, -2.0 ).negate().create() },
                { Expression.postfix().cartesian( -1.0, 2.0 ).negate().create() },
                { Expression.postfix().cartesian( -1.0, -2.0 ).negate().create() },

                // conjugate
                { Expression.postfix().real( 1.0 ).conjugate().create() },
                { Expression.postfix().real( -1.0 ).conjugate().create() },

                { Expression.postfix().imaginary( 2.0 ).conjugate().create() },
                { Expression.postfix().imaginary( -2.0 ).conjugate().create() },

                { Expression.postfix().cartesian( 1.0, 2.0 ).conjugate().create() },
                { Expression.postfix().cartesian( 1.0, -2.0 ).conjugate().create() },
                { Expression.postfix().cartesian( -1.0, 2.0 ).conjugate().create() },
                { Expression.postfix().cartesian( -1.0, -2.0 ).conjugate().create() },

                // component functions
                { Expression.postfix().cartesian( 1.0, 2.0 ).re().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).im().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).abs().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).arg().create() },

                // other functions
                { Expression.postfix().cartesian( 1.0, 2.0 ).sqrt().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).log().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).exp().create() },

                // power
                { Expression.postfix().real( 1.0 ).real( 2.0 ).pow().create() },
                { Expression.postfix().real( 1.0 ).real( -2.0 ).pow().create() },
                { Expression.postfix().real( -1.0 ).real( 2.0 ).pow().create() },
                { Expression.postfix().real( -1.0 ).real( -2.0 ).pow().create() },

                { Expression.postfix().imaginary( 1.0 ).imaginary( 2.0 ).pow().create() },
                { Expression.postfix().imaginary( 1.0 ).imaginary( -2.0 ).pow().create() },
                { Expression.postfix().imaginary( -1.0 ).imaginary( 2.0 ).pow().create() },
                { Expression.postfix().imaginary( -1.0 ).imaginary( -2.0 ).pow().create() },

                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, -4.0 ).pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( -3.0, 4.0 ).pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( -3.0, -4.0 ).pow().create() },
                { Expression.postfix().cartesian( 1.0, -2.0 ).cartesian( 3.0, 4.0 ).pow().create() },
                { Expression.postfix().cartesian( -1.0, 2.0 ).cartesian( 3.0, 4.0 ).pow().create() },
                { Expression.postfix().cartesian( -1.0, -2.0 ).cartesian( 3.0, 4.0 ).pow().create() },


                // multiple operations
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).cartesian( 5.0, 6.0 ).add().multiply().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).cartesian( 5.0, 6.0 ).multiply().add().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).cartesian( 5.0, 6.0 ).divide().multiply().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).cartesian( 5.0, 6.0 ).multiply().divide().create() },

                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).add().cartesian( 5.0, 6.0 ).multiply().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).multiply().cartesian( 5.0, 6.0 ).add().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).divide().cartesian( 5.0, 6.0 ).multiply().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).multiply().cartesian( 5.0, 6.0 ).divide().create() },


                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).add().negate().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).negate().add().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).negate().cartesian( 3.0, 4.0 ).add().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).negate().cartesian( 3.0, 4.0 ).negate().add().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).negate().cartesian( 3.0, 4.0 ).negate().add().negate().create() },

                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).pow().negate().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).negate().pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).negate().cartesian( 3.0, 4.0 ).pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).negate().cartesian( 3.0, 4.0 ).negate().pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).negate().cartesian( 3.0, 4.0 ).negate().pow().negate().create() },


                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).add().conjugate().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).conjugate().add().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).conjugate().cartesian( 3.0, 4.0 ).add().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).conjugate().cartesian( 3.0, 4.0 ).conjugate().add().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).conjugate().cartesian( 3.0, 4.0 ).conjugate().add().conjugate().create() },

                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).pow().conjugate().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).conjugate().pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).conjugate().cartesian( 3.0, 4.0 ).pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).conjugate().cartesian( 3.0, 4.0 ).conjugate().pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).conjugate().cartesian( 3.0, 4.0 ).conjugate().pow().conjugate().create() },


                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).add().cartesian( 5.0, 6.0 ).pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).multiply().cartesian( 5.0, 6.0 ).pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).pow().cartesian( 5.0, 6.0 ).pow().create() },

                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).cartesian( 5.0, 6.0 ).add().pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).cartesian( 5.0, 6.0 ).pow().add().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).cartesian( 5.0, 6.0 ).multiply().pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).cartesian( 5.0, 6.0 ).pow().multiply().create() },

                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).pow().cartesian( 5.0, 6.0 ).pow().create() },
                { Expression.postfix().cartesian( 1.0, 2.0 ).cartesian( 3.0, 4.0 ).cartesian( 5.0, 6.0 ).pow().pow().create() },
        };
    }

}
