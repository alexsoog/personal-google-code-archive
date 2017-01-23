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

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Defines constants required for Kahan's abs algorithm.
 * <pre>
 * r2 = sqrt(2)
 * r2p1 = 1 + sqrt(2)
 * t2p1 = 1 + sqrt(2) - r2p1
 * </pre>
 */
public enum KahanConstants {

    DOUBLE( 0x1.6a09e667f3bcdp0, 0x1.3504f333f9de6p1, 0.0 ),
    DECIMAL( 0x1.6a09e667f3bcdp0, 0x1.3504f333f9de6p1, 0x1.21165f626cdd5p-53 ),
    DECIMAL_ALT( 0x1.6a09e667f3bcdp0, 0x1.3504f333f9de6p1, 0x1.c21dbac50e1d2p-55 );


    public static void main( String[] args ) {
        printDouble();
        System.out.println();
        printDecimal( MathContext.DECIMAL64 );
        System.out.println();
        printDecimal( MathContext.DECIMAL128 );
        System.out.println();
        printDecimal( new MathContext( 100, RoundingMode.HALF_EVEN ) );
    }

    private static void printDouble() {
        double r2 = Math.sqrt( 2.0 );
        double r2p1 = 1.0 + Math.sqrt( 2.0 );
        double t2p1 = 1.0 + Math.sqrt( 2.0 ) - r2p1;

        System.out.println( "double" );
        System.out.printf( "r2     = %a (%a)%n", r2, r2 * r2 );
        System.out.printf( "r2p1   = %a%n", r2p1 );
        System.out.printf( "t2p1   = %a%n", t2p1 );
    }

    private static void printDecimal( MathContext context ) {
        BigDecimal r2 = BigDecimalMath.sqrt( BigDecimalMath.TWO, context );
        double r2d = r2.doubleValue();

        BigDecimal r2p1 = BigDecimal.ONE.add( r2, context );
        double r2p1d = r2p1.doubleValue();

        BigDecimal t2p1 = BigDecimal.ONE.add( r2, context ).subtract( new BigDecimal( r2p1d, context ), context );
        double t2p1d = t2p1.doubleValue();

        BigDecimal t2p1alt = BigDecimal.ONE.add( r2, context ).subtract( BigDecimal.valueOf( r2p1d ), context );
        double t2p1dalt = t2p1alt.doubleValue();

        System.out.printf( "context is %s%n", context );
        System.out.printf( "r2     = %a (%a)%n", r2d, r2d * r2d );
        System.out.printf( "r2p1   = %a%n", r2p1d );
        System.out.printf( "t2p1   = %a (%s)%n", t2p1d, t2p1 );
        System.out.printf( "t2p1da = %a (%s)%n", t2p1dalt, t2p1dalt );
    }


    private final double r2;

    private final double r2p1;

    private final double t2p1;


    KahanConstants( double r2, double r2p1, double t2p1 ) {
        this.r2 = r2;
        this.r2p1 = r2p1;
        this.t2p1 = t2p1;
    }


    public double getR2() {
        return r2;
    }

    public double getR2P1() {
        return r2p1;
    }

    public double getT2P1() {
        return t2p1;
    }

}
