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

package oe.math.complex.research.abs;

import oe.math.complex.research.util.Function;
import oe.math.complex.research.util.FunctionDriver;
import oe.math.complex.research.util.KahanConstants;

public final class AbsTest {

    private static final double[] BOUNDS = { 0.0, Math.sqrt( Double.MIN_NORMAL ), 1.0, Math.sqrt( Double.MAX_VALUE ), Double.POSITIVE_INFINITY };


    public static void main( String[] args ) {
        FunctionDriver driver = new FunctionDriver( new Function[] {
                new DefaultAbsFunction(),
                new ScaledAbsFunction(),
                new HypotAbsFunction(),
                new KahanAbsFunction( KahanConstants.DOUBLE ),
                new KahanAbsFunction( KahanConstants.DECIMAL ),
                new KahanAbsFunction( KahanConstants.DECIMAL_ALT ),
                new ComplexAbsFunction(),
        } );

        driver.run( AbsSamplesFactory.createSpecialSamplesIterator() );

        for ( int i = 0; i < BOUNDS.length - 1; i++ ) {
            driver.run( AbsSamplesFactory.createRandomSamplesIterator( BOUNDS[ i ], BOUNDS[ i + 1 ], 10000 ) );
        }
        driver.run( AbsSamplesFactory.createRandomSamplesIterator( 0.0, Double.POSITIVE_INFINITY, 10000 ) );

        for ( int i = 0; i < BOUNDS.length - 1; i++ ) {
            driver.run( AbsSamplesFactory.createPythagoreanTripleSamplesIterator( BOUNDS[ i ], BOUNDS[ i + 1 ], Math.E ) );
        }
    }


    private AbsTest() {
    }

}
