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

package oe.math.complex.research.pow;

import oe.math.complex.Complex;

public final class ZeroPower {

    private static final Object[][] DATA = {
            { "+a ^ +0.0", Complex.real( 8.0 ), Complex.real( 0.0 ) },
            { "+a ^ -0.0", Complex.real( 8.0 ), Complex.real( -0.0 ) },
            { "+a ^ +0.0i", Complex.real( 8.0 ), Complex.imaginary( 0.0 ) },
            { "+a ^ -0.0i", Complex.real( 8.0 ), Complex.imaginary( -0.0 ) },
            { "+a ^ +0.0+0.0i", Complex.real( 8.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "+a ^ +0.0-0.0i", Complex.real( 8.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "+a ^ -0.0+0.0i", Complex.real( 8.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "+a ^ -0.0-0.0i", Complex.real( 8.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "-a ^ +0.0", Complex.real( -8.0 ), Complex.real( 0.0 ) },
            { "-a ^ -0.0", Complex.real( -8.0 ), Complex.real( -0.0 ) },
            { "-a ^ +0.0i", Complex.real( -8.0 ), Complex.imaginary( 0.0 ) },
            { "-a ^ -0.0i", Complex.real( -8.0 ), Complex.imaginary( -0.0 ) },
            { "-a ^ +0.0+0.0i", Complex.real( -8.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "-a ^ +0.0-0.0i", Complex.real( -8.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "-a ^ -0.0+0.0i", Complex.real( -8.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "-a ^ -0.0-0.0i", Complex.real( -8.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "+bi ^ +0.0", Complex.imaginary( 8.0 ), Complex.real( 0.0 ) },
            { "+bi ^ -0.0", Complex.imaginary( 8.0 ), Complex.real( -0.0 ) },
            { "+bi ^ +0.0i", Complex.imaginary( 8.0 ), Complex.imaginary( 0.0 ) },
            { "+bi ^ -0.0i", Complex.imaginary( 8.0 ), Complex.imaginary( -0.0 ) },
            { "+bi ^ +0.0+0.0i", Complex.imaginary( 8.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "+bi ^ +0.0-0.0i", Complex.imaginary( 8.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "+bi ^ -0.0+0.0i", Complex.imaginary( 8.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "+bi ^ -0.0-0.0i", Complex.imaginary( 8.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "-bi ^ +0.0", Complex.imaginary( -8.0 ), Complex.real( 0.0 ) },
            { "-bi ^ -0.0", Complex.imaginary( -8.0 ), Complex.real( -0.0 ) },
            { "-bi ^ +0.0i", Complex.imaginary( -8.0 ), Complex.imaginary( 0.0 ) },
            { "-bi ^ -0.0i", Complex.imaginary( -8.0 ), Complex.imaginary( -0.0 ) },
            { "-bi ^ +0.0+0.0i", Complex.imaginary( -8.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "-bi ^ +0.0-0.0i", Complex.imaginary( -8.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "-bi ^ -0.0+0.0i", Complex.imaginary( -8.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "-bi ^ -0.0-0.0i", Complex.imaginary( -8.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "+a+0.0i ^ +0.0", Complex.cartesian( 8.0, 0.0 ), Complex.real( 0.0 ) },
            { "+a+0.0i ^ -0.0", Complex.cartesian( 8.0, 0.0 ), Complex.real( -0.0 ) },
            { "+a+0.0i ^ +0.0i", Complex.cartesian( 8.0, 0.0 ), Complex.imaginary( 0.0 ) },
            { "+a+0.0i ^ -0.0i", Complex.cartesian( 8.0, 0.0 ), Complex.imaginary( -0.0 ) },
            { "+a+0.0i ^ +0.0+0.0i", Complex.cartesian( 8.0, 0.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "+a+0.0i ^ +0.0-0.0i", Complex.cartesian( 8.0, 0.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "+a+0.0i ^ -0.0+0.0i", Complex.cartesian( 8.0, 0.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "+a+0.0i ^ -0.0-0.0i", Complex.cartesian( 8.0, 0.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "+a-0.0i ^ +0.0", Complex.cartesian( 8.0, -0.0 ), Complex.real( 0.0 ) },
            { "+a-0.0i ^ -0.0", Complex.cartesian( 8.0, -0.0 ), Complex.real( -0.0 ) },
            { "+a-0.0i ^ +0.0i", Complex.cartesian( 8.0, -0.0 ), Complex.imaginary( 0.0 ) },
            { "+a-0.0i ^ -0.0i", Complex.cartesian( 8.0, -0.0 ), Complex.imaginary( -0.0 ) },
            { "+a-0.0i ^ +0.0+0.0i", Complex.cartesian( 8.0, -0.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "+a-0.0i ^ +0.0-0.0i", Complex.cartesian( 8.0, -0.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "+a-0.0i ^ -0.0+0.0i", Complex.cartesian( 8.0, -0.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "+a-0.0i ^ -0.0-0.0i", Complex.cartesian( 8.0, -0.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "-a+0.0i ^ +0.0", Complex.cartesian( -8.0, 0.0 ), Complex.real( 0.0 ) },
            { "-a+0.0i ^ -0.0", Complex.cartesian( -8.0, 0.0 ), Complex.real( -0.0 ) },
            { "-a+0.0i ^ +0.0i", Complex.cartesian( -8.0, 0.0 ), Complex.imaginary( 0.0 ) },
            { "-a+0.0i ^ -0.0i", Complex.cartesian( -8.0, 0.0 ), Complex.imaginary( -0.0 ) },
            { "-a+0.0i ^ +0.0+0.0i", Complex.cartesian( -8.0, 0.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "-a+0.0i ^ +0.0-0.0i", Complex.cartesian( -8.0, 0.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "-a+0.0i ^ -0.0+0.0i", Complex.cartesian( -8.0, 0.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "-a+0.0i ^ -0.0-0.0i", Complex.cartesian( -8.0, 0.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "-a-0.0i ^ +0.0", Complex.cartesian( -8.0, -0.0 ), Complex.real( 0.0 ) },
            { "-a-0.0i ^ -0.0", Complex.cartesian( -8.0, -0.0 ), Complex.real( -0.0 ) },
            { "-a-0.0i ^ +0.0i", Complex.cartesian( -8.0, -0.0 ), Complex.imaginary( 0.0 ) },
            { "-a-0.0i ^ -0.0i", Complex.cartesian( -8.0, -0.0 ), Complex.imaginary( -0.0 ) },
            { "-a-0.0i ^ +0.0+0.0i", Complex.cartesian( -8.0, -0.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "-a-0.0i ^ +0.0-0.0i", Complex.cartesian( -8.0, -0.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "-a-0.0i ^ -0.0+0.0i", Complex.cartesian( -8.0, -0.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "-a-0.0i ^ -0.0-0.0i", Complex.cartesian( -8.0, -0.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "+0.0+bi ^ +0.0", Complex.cartesian( 0.0, 8.0 ), Complex.real( 0.0 ) },
            { "+0.0+bi ^ -0.0", Complex.cartesian( 0.0, 8.0 ), Complex.real( -0.0 ) },
            { "+0.0+bi ^ +0.0i", Complex.cartesian( 0.0, 8.0 ), Complex.imaginary( 0.0 ) },
            { "+0.0+bi ^ -0.0i", Complex.cartesian( 0.0, 8.0 ), Complex.imaginary( -0.0 ) },
            { "+0.0+bi ^ +0.0+0.0i", Complex.cartesian( 0.0, 8.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "+0.0+bi ^ +0.0-0.0i", Complex.cartesian( 0.0, 8.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "+0.0+bi ^ -0.0+0.0i", Complex.cartesian( 0.0, 8.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "+0.0+bi ^ -0.0-0.0i", Complex.cartesian( 0.0, 8.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "+0.0-bi ^ +0.0", Complex.cartesian( 0.0, -8.0 ), Complex.real( 0.0 ) },
            { "+0.0-bi ^ -0.0", Complex.cartesian( 0.0, -8.0 ), Complex.real( -0.0 ) },
            { "+0.0-bi ^ +0.0i", Complex.cartesian( 0.0, -8.0 ), Complex.imaginary( 0.0 ) },
            { "+0.0-bi ^ -0.0i", Complex.cartesian( 0.0, -8.0 ), Complex.imaginary( -0.0 ) },
            { "+0.0-bi ^ +0.0+0.0i", Complex.cartesian( 0.0, -8.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "+0.0-bi ^ +0.0-0.0i", Complex.cartesian( 0.0, -8.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "+0.0-bi ^ -0.0+0.0i", Complex.cartesian( 0.0, -8.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "+0.0-bi ^ -0.0-0.0i", Complex.cartesian( 0.0, -8.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "-0.0+bi ^ +0.0", Complex.cartesian( -0.0, 8.0 ), Complex.real( 0.0 ) },
            { "-0.0+bi ^ -0.0", Complex.cartesian( -0.0, 8.0 ), Complex.real( -0.0 ) },
            { "-0.0+bi ^ +0.0i", Complex.cartesian( -0.0, 8.0 ), Complex.imaginary( 0.0 ) },
            { "-0.0+bi ^ -0.0i", Complex.cartesian( -0.0, 8.0 ), Complex.imaginary( -0.0 ) },
            { "-0.0+bi ^ +0.0+0.0i", Complex.cartesian( -0.0, 8.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "-0.0+bi ^ +0.0-0.0i", Complex.cartesian( -0.0, 8.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "-0.0+bi ^ -0.0+0.0i", Complex.cartesian( -0.0, 8.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "-0.0+bi ^ -0.0-0.0i", Complex.cartesian( -0.0, 8.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "-0.0-bi ^ +0.0", Complex.cartesian( -0.0, -8.0 ), Complex.real( 0.0 ) },
            { "-0.0-bi ^ -0.0", Complex.cartesian( -0.0, -8.0 ), Complex.real( -0.0 ) },
            { "-0.0-bi ^ +0.0i", Complex.cartesian( -0.0, -8.0 ), Complex.imaginary( 0.0 ) },
            { "-0.0-bi ^ -0.0i", Complex.cartesian( -0.0, -8.0 ), Complex.imaginary( -0.0 ) },
            { "-0.0-bi ^ +0.0+0.0i", Complex.cartesian( -0.0, -8.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "-0.0-bi ^ +0.0-0.0i", Complex.cartesian( -0.0, -8.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "-0.0-bi ^ -0.0+0.0i", Complex.cartesian( -0.0, -8.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "-0.0-bi ^ -0.0-0.0i", Complex.cartesian( -0.0, -8.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "+a+bi ^ +0.0", Complex.cartesian( 8.0, 8.0 ), Complex.real( 0.0 ) },
            { "+a+bi ^ -0.0", Complex.cartesian( 8.0, 8.0 ), Complex.real( -0.0 ) },
            { "+a+bi ^ +0.0i", Complex.cartesian( 8.0, 8.0 ), Complex.imaginary( 0.0 ) },
            { "+a+bi ^ -0.0i", Complex.cartesian( 8.0, 8.0 ), Complex.imaginary( -0.0 ) },
            { "+a+bi ^ +0.0+0.0i", Complex.cartesian( 8.0, 8.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "+a+bi ^ +0.0-0.0i", Complex.cartesian( 8.0, 8.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "+a+bi ^ -0.0+0.0i", Complex.cartesian( 8.0, 8.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "+a+bi ^ -0.0-0.0i", Complex.cartesian( 8.0, 8.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "+a-bi ^ +0.0", Complex.cartesian( 8.0, -8.0 ), Complex.real( 0.0 ) },
            { "+a-bi ^ -0.0", Complex.cartesian( 8.0, -8.0 ), Complex.real( -0.0 ) },
            { "+a-bi ^ +0.0i", Complex.cartesian( 8.0, -8.0 ), Complex.imaginary( 0.0 ) },
            { "+a-bi ^ -0.0i", Complex.cartesian( 8.0, -8.0 ), Complex.imaginary( -0.0 ) },
            { "+a-bi ^ +0.0+0.0i", Complex.cartesian( 8.0, -8.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "+a-bi ^ +0.0-0.0i", Complex.cartesian( 8.0, -8.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "+a-bi ^ -0.0+0.0i", Complex.cartesian( 8.0, -8.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "+a-bi ^ -0.0-0.0i", Complex.cartesian( 8.0, -8.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "-a+bi ^ +0.0", Complex.cartesian( -8.0, 8.0 ), Complex.real( 0.0 ) },
            { "-a+bi ^ -0.0", Complex.cartesian( -8.0, 8.0 ), Complex.real( -0.0 ) },
            { "-a+bi ^ +0.0i", Complex.cartesian( -8.0, 8.0 ), Complex.imaginary( 0.0 ) },
            { "-a+bi ^ -0.0i", Complex.cartesian( -8.0, 8.0 ), Complex.imaginary( -0.0 ) },
            { "-a+bi ^ +0.0+0.0i", Complex.cartesian( -8.0, 8.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "-a+bi ^ +0.0-0.0i", Complex.cartesian( -8.0, 8.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "-a+bi ^ -0.0+0.0i", Complex.cartesian( -8.0, 8.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "-a+bi ^ -0.0-0.0i", Complex.cartesian( -8.0, 8.0 ), Complex.cartesian( -0.0, -0.0 ) },

            { "-a-bi ^ +0.0", Complex.cartesian( -8.0, -8.0 ), Complex.real( 0.0 ) },
            { "-a-bi ^ -0.0", Complex.cartesian( -8.0, -8.0 ), Complex.real( -0.0 ) },
            { "-a-bi ^ +0.0i", Complex.cartesian( -8.0, -8.0 ), Complex.imaginary( 0.0 ) },
            { "-a-bi ^ -0.0i", Complex.cartesian( -8.0, -8.0 ), Complex.imaginary( -0.0 ) },
            { "-a-bi ^ +0.0+0.0i", Complex.cartesian( -8.0, -8.0 ), Complex.cartesian( 0.0, 0.0 ) },
            { "-a-bi ^ +0.0-0.0i", Complex.cartesian( -8.0, -8.0 ), Complex.cartesian( 0.0, -0.0 ) },
            { "-a-bi ^ -0.0+0.0i", Complex.cartesian( -8.0, -8.0 ), Complex.cartesian( -0.0, 0.0 ) },
            { "-a-bi ^ -0.0-0.0i", Complex.cartesian( -8.0, -8.0 ), Complex.cartesian( -0.0, -0.0 ) },
    };


    public static void main( String[] args ) {
        for ( Object[] data : DATA ) {
            System.out.println( data[ 0 ] );
            test( ( Complex ) data[ 1 ], ( Complex ) data[ 2 ] );
        }
    }

    private static void test( Complex base, Complex exponent ) {
        String format = "    %-9s ^ %-9s = %s%n";
        Complex value = convert( base, Double.POSITIVE_INFINITY );
        System.out.printf( format, value, exponent, value.pow( exponent ) );
        value = base;
        Complex previousValue = base;
        Complex previousResult = null;
        do {
            value = previousValue.divide( Complex.real( 2.0 ) );
            Complex result = value.pow( exponent );
            if ( !result.equals( previousResult ) ) {
                if ( previousResult != null ) {
                    System.out.printf( format, previousValue, exponent, previousResult );
                }
                System.out.printf( format, value, exponent, result );
            }
            previousValue = value;
            previousResult = result;
        } while ( value.getRe() == value.getRe() && value.getIm() == value.getIm() &&
                ( value.getRe() != 0.0 || value.getIm() != 0.0 ) );
        System.out.printf( format, value, exponent, value.pow( exponent ) );
        value = convert( base, Double.NaN );
        System.out.printf( format, value, exponent, value.pow( exponent ) );
    }

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


    private ZeroPower() {
    }

}
