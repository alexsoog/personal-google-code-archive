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

/** A complex number with only the real part. */
final class RealNumber extends Complex {

    /** The real part. */
    private final double re;


    /**
     * Creates a new {@code RealNumber} object.
     *
     * @param re the real part
     */
    RealNumber( double re ) {
        this.re = re;
    }


    @Override
    public double getRe() {
        return re;
    }

    @Override
    public double getIm() {
        return 0.0;
    }

    @Override
    public double getAbs() {
        return ComplexMath.absReal( re );
    }

    @Override
    public double getArg() {
        return ComplexMath.argReal( re );
    }


    @Override
    public Complex re() {
        return this;
    }

    @Override
    public Complex im() {
        return ZERO;
    }


    @Override
    boolean hasRealPart() {
        return true;
    }

    @Override
    boolean hasImaginaryPart() {
        return false;
    }


    @Override
    public Complex conjugate() {
        return this;
    }

    @Override
    public Complex negate() {
        return new RealNumber( -re );
    }


    @Override
    public Complex add( Complex value ) {
        return value.addRealTransposed( re );
    }

    @Override
    Complex addRealTransposed( double a ) {
        return ComplexMath.addRealReal( a, re );
    }

    @Override
    Complex addImaginaryTransposed( double b ) {
        return ComplexMath.addImaginaryReal( b, re );
    }

    @Override
    Complex addComplexTransposed( double a, double b ) {
        return ComplexMath.addComplexReal( a, b, re );
    }


    @Override
    public Complex subtract( Complex value ) {
        return value.subtractRealTransposed( re );
    }

    @Override
    Complex subtractRealTransposed( double a ) {
        return ComplexMath.subtractRealReal( a, re );
    }

    @Override
    Complex subtractImaginaryTransposed( double b ) {
        return ComplexMath.subtractImaginaryReal( b, re );
    }

    @Override
    Complex subtractComplexTransposed( double a, double b ) {
        return ComplexMath.subtractComplexReal( a, b, re );
    }


    @Override
    public Complex multiply( Complex value ) {
        return value.multiplyRealTransposed( re );
    }

    @Override
    Complex multiplyRealTransposed( double a ) {
        return ComplexMath.multiplyRealReal( a, re );
    }

    @Override
    Complex multiplyImaginaryTransposed( double b ) {
        return ComplexMath.multiplyImaginaryReal( b, re );
    }

    @Override
    Complex multiplyComplexTransposed( double a, double b ) {
        return ComplexMath.multiplyComplexReal( a, b, re );
    }


    @Override
    public Complex divide( Complex value ) {
        return value.divideRealTransposed( re );
    }

    @Override
    Complex divideRealTransposed( double a ) {
        return ComplexMath.divideRealReal( a, re );
    }

    @Override
    Complex divideImaginaryTransposed( double b ) {
        return ComplexMath.divideImaginaryReal( b, re );
    }

    @Override
    Complex divideComplexTransposed( double a, double b ) {
        return ComplexMath.divideComplexReal( a, b, re );
    }


    @Override
    public Complex sqrt() {
        return ComplexMath.sqrtReal( re );
    }

    @Override
    public Complex log() {
        return ComplexMath.logReal( re );
    }

    @Override
    public Complex exp() {
        return ComplexMath.expReal( re );
    }


    @Override
    public Complex pow( Complex exponent ) {
        return exponent.powRealTransposed( re );
    }

    @Override
    Complex powRealTransposed( double a ) {
        return ComplexMath.powRealReal( a, re );
    }

    @Override
    Complex powImaginaryTransposed( double b ) {
        return ComplexMath.powImaginaryReal( b, re );
    }

    @Override
    Complex powComplexTransposed( double a, double b ) {
        return ComplexMath.powComplexReal( a, b, re );
    }


    @Override
    public int hashCode() {
        long longRe = Double.doubleToLongBits( re );
        return ( int ) ( longRe ^ longRe >>> 32 );
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        } else if ( obj instanceof RealNumber ) {
            RealNumber realNumber = ( RealNumber ) obj;
            return Double.doubleToLongBits( re ) == Double.doubleToLongBits( realNumber.re );
        } else {
            return false;
        }
    }

}
