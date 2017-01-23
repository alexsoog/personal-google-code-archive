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

/** The difference between two double values. */
final class Difference {

    /**
     * Calculates the difference between the actual and expected values.
     *
     * @param actual the actual value
     * @param expected the expected value
     *
     * @return the difference
     */
    public static Difference compare( double actual, double expected ) {
        if ( expected != expected ) {
            if ( actual != actual ) {
                return new Difference( Type.ULP, 0, false );
            } else {
                return new Difference( Type.INVALID, getUlpDifference( actual, expected ), false );
            }
        } else if ( Double.isInfinite( expected ) ) {
            if ( actual != actual ) {
                return new Difference( Type.INVALID, getUlpDifference( actual, expected ), false );
            } else if ( Double.isInfinite( actual ) ) {
                return new Difference( Type.ULP, getUlpDifference( actual, expected ), getSignDifference( actual, expected ) );
            } else if ( actual == 0.0 ) {
                return new Difference( Type.UNDERFLOW, getUlpDifference( actual, expected ), getSignDifference( actual, expected ) );
            } else {
                return new Difference( Type.ULP, getUlpDifference( actual, expected ), getSignDifference( actual, expected ) );
            }
        } else if ( expected == 0.0 ) {
            if ( actual != actual ) {
                return new Difference( Type.INVALID, getUlpDifference( actual, expected ), false );
            } else if ( Double.isInfinite( actual ) ) {
                return new Difference( Type.OVERFLOW, getUlpDifference( actual, expected ), getSignDifference( actual, expected ) );
            } else if ( actual == 0.0 ) {
                return new Difference( Type.ULP, getUlpDifference( actual, expected ), getSignDifference( actual, expected ) );
            } else {
                return new Difference( Type.ULP, getUlpDifference( actual, expected ), getSignDifference( actual, expected ) );
            }
        } else {
            if ( actual != actual ) {
                return new Difference( Type.INVALID, getUlpDifference( actual, expected ), false );
            } else if ( Double.isInfinite( actual ) ) {
                return new Difference( Type.OVERFLOW, getUlpDifference( actual, expected ), getSignDifference( actual, expected ) );
            } else if ( actual == 0.0 ) {
                return new Difference( Type.UNDERFLOW, getUlpDifference( actual, expected ), getSignDifference( actual, expected ) );
            } else {
                return new Difference( Type.ULP, getUlpDifference( actual, expected ), getSignDifference( actual, expected ) );
            }
        }
    }

    private static long getUlpDifference( double actual, double expected ) {
        long actualLongBits = Double.doubleToLongBits( Math.abs( actual ) );
        long expectedLongBits = Double.doubleToLongBits( Math.abs( expected ) );
        return Math.abs( actualLongBits - expectedLongBits );
    }

    private static boolean getSignDifference( double actual, double expected ) {
        return Double.compare( actual, 0.0 ) >= 0 ^ Double.compare( expected, 0.0 ) >= 0;
    }


    public final Type type;

    public final long ulp;

    public final boolean sign;


    private Difference( Type type, long ulp, boolean sign ) {
        this.type = type;
        this.ulp = ulp;
        this.sign = sign;
    }


    public boolean isDifferent() {
        return type != Type.ULP || ulp != 0L || sign;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        if ( sign ) {
            buffer.append( "sign " );
        }
        switch ( type ) {
            case ULP:
                buffer.append( ulp );
                break;
            case UNDERFLOW:
                buffer.append( "underflow " ).append( ulp );
                break;
            case OVERFLOW:
                buffer.append( "overflow " ).append( ulp );
                break;
            case INVALID:
                buffer.append( "invalid" );
                break;
            default:
                throw new AssertionError( type );
        }
        return buffer.toString();
    }


    public enum Type {

        ULP,
        UNDERFLOW,
        OVERFLOW,
        INVALID

    }

}
