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

/** A complex number with both real and imaginary parts. */
final class ComplexNumber extends Complex {

    /** The real part. */
    private final double re;

    /** The imaginary part. */
    private final double im;


    /**
     * Creates a new {@code ComplexNumber} object.
     *
     * @param re the real part
     * @param im the imaginary part
     */
    ComplexNumber( double re, double im ) {
        this.re = re;
        this.im = im;
    }


    @Override
    public double getRe() {
        return re;
    }

    @Override
    public double getIm() {
        return im;
    }

    @Override
    public double getAbs() {
        return ComplexMath.absComplex( re, im );
    }

    @Override
    public double getArg() {
        return ComplexMath.argComplex( re, im );
    }


    @Override
    boolean hasRealPart() {
        return true;
    }

    @Override
    boolean hasImaginaryPart() {
        return true;
    }


    @Override
    public Complex conjugate() {
        return new ComplexNumber( re, -im );
    }

    @Override
    public Complex negate() {
        return new ComplexNumber( -re, -im );
    }


    @Override
    public Complex add( Complex value ) {
        return value.addComplexTransposed( re, im );
    }

    @Override
    Complex addRealTransposed( double a ) {
        return ComplexMath.addRealComplex( a, re, im );
    }

    @Override
    Complex addImaginaryTransposed( double b ) {
        return ComplexMath.addImaginaryComplex( b, re, im );
    }

    @Override
    Complex addComplexTransposed( double a, double b ) {
        return ComplexMath.addComplexComplex( a, b, re, im );
    }


    @Override
    public Complex subtract( Complex value ) {
        return value.subtractComplexTransposed( re, im );
    }

    @Override
    Complex subtractRealTransposed( double a ) {
        return ComplexMath.subtractRealComplex( a, re, im );
    }

    @Override
    Complex subtractImaginaryTransposed( double b ) {
        return ComplexMath.subtractImaginaryComplex( b, re, im );
    }

    @Override
    Complex subtractComplexTransposed( double a, double b ) {
        return ComplexMath.subtractComplexComplex( a, b, re, im );
    }


    @Override
    public Complex multiply( Complex value ) {
        return value.multiplyComplexTransposed( re, im );
    }

    @Override
    Complex multiplyRealTransposed( double a ) {
        return ComplexMath.multiplyRealComplex( a, re, im );
    }

    @Override
    Complex multiplyImaginaryTransposed( double b ) {
        return ComplexMath.multiplyImaginaryComplex( b, re, im );
    }

    @Override
    Complex multiplyComplexTransposed( double a, double b ) {
        return ComplexMath.multiplyComplexComplex( a, b, re, im );
    }


    @Override
    public Complex divide( Complex value ) {
        return value.divideComplexTransposed( re, im );
    }

    @Override
    Complex divideRealTransposed( double a ) {
        return ComplexMath.divideRealComplex( a, re, im );
    }

    @Override
    Complex divideImaginaryTransposed( double b ) {
        return ComplexMath.divideImaginaryComplex( b, re, im );
    }

    @Override
    Complex divideComplexTransposed( double a, double b ) {
        return ComplexMath.divideComplexComplex( a, b, re, im );
    }


    @Override
    public Complex sqrt() {
        return ComplexMath.sqrtComplex( re, im );
    }

    @Override
    public Complex log() {
        return ComplexMath.logComplex( re, im );
    }

    @Override
    public Complex exp() {
        return ComplexMath.expComplex( re, im );
    }


    @Override
    public Complex pow( Complex exponent ) {
        return exponent.powComplexTransposed( re, im );
    }

    @Override
    Complex powRealTransposed( double a ) {
        return ComplexMath.powRealComplex( a, re, im );
    }

    @Override
    Complex powImaginaryTransposed( double b ) {
        return ComplexMath.powImaginaryComplex( b, re, im );
    }

    @Override
    Complex powComplexTransposed( double a, double b ) {
        return ComplexMath.powComplexComplex( a, b, re, im );
    }


    @Override
    public int hashCode() {
        int result = 17;
        long longRe = Double.doubleToLongBits( re );
        result = 37 * result + ( int ) ( longRe ^ longRe >>> 32 );
        long longIm = Double.doubleToLongBits( im );
        result = 37 * result + ( int ) ( longIm ^ longIm >>> 32 );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        } else if ( obj instanceof ComplexNumber ) {
            ComplexNumber complexNumber = ( ComplexNumber ) obj;
            return Double.doubleToLongBits( re ) == Double.doubleToLongBits( complexNumber.re )
                    && Double.doubleToLongBits( im ) == Double.doubleToLongBits( complexNumber.im );
        } else {
            return false;
        }
    }

}
