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
import static oe.assertions.Predicates.isInstanceOf;

public class SquareRootTypeTest {

    @Test( dataProvider = "squareRootTypeSamples" )
    public void squareRootResultType( Complex ab, Class<? extends Complex> expectedResultType ) {
        Complex ef = ab.sqrt();
        assertThat( ef, isInstanceOf( expectedResultType ) );
    }


    @DataProvider( name = "squareRootTypeSamples" )
    public Object[][] squareRootTypeSamples() {
        return new Object[][] {
                /*
                {ab, result.class}
                 */

                { Complex.real( 1.0 ), RealNumber.class },
                { Complex.real( -1.0 ), ImaginaryNumber.class },

                { Complex.imaginary( 2.0 ), ComplexNumber.class },

                { Complex.cartesian( 1.0, 2.0 ), ComplexNumber.class },
        };
    }

}
