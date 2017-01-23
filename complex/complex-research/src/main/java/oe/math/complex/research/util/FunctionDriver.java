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

package oe.math.complex.research.util;

import java.util.Iterator;

public final class FunctionDriver {

    private final Function[] functions;


    public FunctionDriver( Function[] functions ) {
        if ( functions == null ) {
            throw new NullPointerException( "functions is null" );
        }
        for ( Function function : functions ) {
            if ( function == null ) {
                throw new NullPointerException( "functions contains null" );
            }
        }
        this.functions = functions.clone();
    }


    public void run( Iterator<double[]> samples ) {
        run( samples, false );
    }

    public void run( Iterator<double[]> samples, boolean printIntermediate ) {
        System.out.printf( "samples: %s%n", samples );
        DifferenceAccumulator[] reAccumulators = new DifferenceAccumulator[functions.length];
        DifferenceAccumulator[] imAccumulators = new DifferenceAccumulator[functions.length];
        for ( int i = 0; i < functions.length; i++ ) {
            reAccumulators[ i ] = new DifferenceAccumulator();
            imAccumulators[ i ] = new DifferenceAccumulator();
        }
        int samplesCount = 0;
        while ( samples.hasNext() ) {
            boolean sampleHeaderRequired = true;
            boolean sampleFooterRequired = false;
            samplesCount++;
            double[] sample = samples.next();
            for ( int i = 0; i < functions.length; i++ ) {
                double[] result = functions[ i ].calculate( sample );
                Difference re = Difference.compare( result[ 0 ], sample[ sample.length - 2 ] );
                Difference im = Difference.compare( result[ 1 ], sample[ sample.length - 1 ] );
                reAccumulators[ i ].append( re );
                imAccumulators[ i ].append( im );
                if ( printIntermediate && ( re.isDifferent() || im.isDifferent() ) ) {
                    if ( sampleHeaderRequired ) {
                        System.out.printf( "    sample %d:", samplesCount );
                        for ( int j = 0; j < sample.length - 2; j += 2 ) {
                            System.out.printf( " (%s, %s)", sample[ j ], sample[ j + 1 ] );
                        }
                        System.out.printf( "%n" );
                        System.out.printf( "        %-20s: (%-24s, %-24s)%n", "expected", sample[ sample.length - 2 ], sample[ sample.length - 1 ] );
                        sampleHeaderRequired = false;
                        sampleFooterRequired = true;
                    }
                    System.out.printf( "        %-20s: (%-24s, %-24s) (%s, %s)%n", functions[ i ], result[ 0 ], result[ 1 ], re, im );
                }
            }
            if ( printIntermediate && sampleFooterRequired ) {
                //System.out.println();
            }
        }
        System.out.printf( "samples count = %d%n", samplesCount );
        System.out.printf( "    %-20s  %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s%n",
                "function name",
                "equal count",
                "wrong sign count",
                "ulp count",
                "max ulp diff",
                "total ulp diff",
                "underflow count",
                "overflow count",
                "invalid count" );
        for ( int i = 0; i < functions.length; i++ ) {
            System.out.printf( "    %-20s: %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s%n",
                    functions[ i ],
                    String.format( "(%d, %d)", reAccumulators[ i ].equalCount, imAccumulators[ i ].equalCount ),
                    String.format( "(%d, %d)", reAccumulators[ i ].wrongSignCount, imAccumulators[ i ].wrongSignCount ),
                    String.format( "(%d, %d)", reAccumulators[ i ].ulpCount, imAccumulators[ i ].ulpCount ),
                    String.format( "(%s, %s)", formatUlpDifference( reAccumulators[ i ].maxUlpDifference ), formatUlpDifference( imAccumulators[ i ].maxUlpDifference ) ),
                    String.format( "(%s, %s)", formatUlpDifference( reAccumulators[ i ].totalUlpDifference ), formatUlpDifference( imAccumulators[ i ].totalUlpDifference ) ),
                    String.format( "(%d, %d)", reAccumulators[ i ].underflowCount, imAccumulators[ i ].underflowCount ),
                    String.format( "(%d, %d)", reAccumulators[ i ].overflowCount, imAccumulators[ i ].overflowCount ),
                    String.format( "(%d, %d)", reAccumulators[ i ].invalidCount, imAccumulators[ i ].invalidCount ) );
        }
    }

    private String formatUlpDifference( long difference ) {
        if ( difference > 9999999L || difference < -9999999L ) {
            return ">9999999";
        } else {
            return String.format( "%d", difference );
        }
    }

}
