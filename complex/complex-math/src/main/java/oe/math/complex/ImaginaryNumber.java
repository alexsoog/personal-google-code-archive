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

/** A complex number with only the imaginary part. */
final class ImaginaryNumber extends Complex {

    /** The imaginary part. */
    private final double im;


    /**
     * Creates a new {@code ImaginaryNumber} object.
     *
     * @param im the imaginary part
     */
    ImaginaryNumber( double im ) {
        this.im = im;
    }


    @Override
    public double getRe() {
        return 0.0;
    }

    @Override
    public double getIm() {
        return im;
    }

    @Override
    public double getAbs() {
        return ComplexMath.absImaginary( im );
    }

    @Override
    public double getArg() {
        return ComplexMath.argImaginary( im );
    }


    @Override
    public Complex re() {
        return ZERO;
    }


    @Override
    boolean hasRealPart() {
        return false;
    }

    @Override
    boolean hasImaginaryPart() {
        return true;
    }


    @Override
    public Complex conjugate() {
        return new ImaginaryNumber( -im );
    }

    @Override
    public Complex negate() {
        return new ImaginaryNumber( -im );
    }


    @Override
    public Complex add( Complex value ) {
        return value.addImaginaryTransposed( im );
    }

    @Override
    Complex addRealTransposed( double a ) {
        return ComplexMath.addRealImaginary( a, im );
    }

    @Override
    Complex addImaginaryTransposed( double b ) {
        return ComplexMath.addImaginaryImaginary( b, im );
    }

    @Override
    Complex addComplexTransposed( double a, double b ) {
        return ComplexMath.addComplexImaginary( a, b, im );
    }


    @Override
    public Complex subtract( Complex value ) {
        return value.subtractImaginaryTransposed( im );
    }

    @Override
    Complex subtractRealTransposed( double a ) {
        return ComplexMath.subtractRealImaginary( a, im );
    }

    @Override
    Complex subtractImaginaryTransposed( double b ) {
        return ComplexMath.subtractImaginaryImaginary( b, im );
    }

    @Override
    Complex subtractComplexTransposed( double a, double b ) {
        return ComplexMath.subtractComplexImaginary( a, b, im );
    }


    @Override
    public Complex multiply( Complex value ) {
        return value.multiplyImaginaryTransposed( im );
    }

    @Override
    Complex multiplyRealTransposed( double a ) {
        return ComplexMath.multiplyRealImaginary( a, im );
    }

    @Override
    Complex multiplyImaginaryTransposed( double b ) {
        return ComplexMath.multiplyImaginaryImaginary( b, im );
    }

    @Override
    Complex multiplyComplexTransposed( double a, double b ) {
        return ComplexMath.multiplyComplexImaginary( a, b, im );
    }


    @Override
    public Complex divide( Complex value ) {
        return value.divideImaginaryTransposed( im );
    }

    @Override
    Complex divideRealTransposed( double a ) {
        return ComplexMath.divideRealImaginary( a, im );
    }

    @Override
    Complex divideImaginaryTransposed( double b ) {
        return ComplexMath.divideImaginaryImaginary( b, im );
    }

    @Override
    Complex divideComplexTransposed( double a, double b ) {
        return ComplexMath.divideComplexImaginary( a, b, im );
    }


    @Override
    public Complex sqrt() {
        return ComplexMath.sqrtImaginary( im );
    }

    @Override
    public Complex log() {
        return ComplexMath.logImaginary( im );
    }

    @Override
    public Complex exp() {
        return ComplexMath.expImaginary( im );
    }


    @Override
    public Complex pow( Complex exponent ) {
        return exponent.powImaginaryTransposed( im );
    }

    @Override
    Complex powRealTransposed( double a ) {
        return ComplexMath.powRealImaginary( a, im );
    }

    @Override
    Complex powImaginaryTransposed( double b ) {
        return ComplexMath.powImaginaryImaginary( b, im );
    }

    @Override
    Complex powComplexTransposed( double a, double b ) {
        return ComplexMath.powComplexImaginary( a, b, im );
    }


    @Override
    public int hashCode() {
        long longIm = Double.doubleToLongBits( im );
        return ( int ) ( longIm ^ longIm >>> 32 );
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        } else if ( obj instanceof ImaginaryNumber ) {
            ImaginaryNumber imaginaryNumber = ( ImaginaryNumber ) obj;
            return Double.doubleToLongBits( im ) == Double.doubleToLongBits( imaginaryNumber.im );
        } else {
            return false;
        }
    }

}
