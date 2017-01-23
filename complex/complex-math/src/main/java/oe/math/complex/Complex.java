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

/**
 * Represents a complex number with double-precision components.
 * <p/>
 * Instances of this class are immutable. To create a new instance, use the appropriate static factory method ({@link
 * #real(double) real}, {@link #imaginary(double) imaginary}, {@link #cartesian(double, double) cartesian} or {@link
 * #polar(double, double) polar}).
 */
public abstract class Complex {

    /** A complex number with the value of zero. */
    public static final Complex ZERO = new RealNumber( 0.0 );

    /** A complex number with the value of one. */
    public static final Complex ONE = new RealNumber( 1.0 );

    /** A complex number with the value of imaginary unit, or <i>i</i>. */
    public static final Complex I = new ImaginaryNumber( 1.0 );


    /**
     * Creates a complex number with the specified real part.
     *
     * @param re the real part
     *
     * @return a new {@code Complex} object
     */
    public static Complex real( double re ) {
        return new RealNumber( re );
    }

    /**
     * Creates a complex number with the specified imaginary part.
     *
     * @param im the imaginary part
     *
     * @return a new {@code Complex} object
     */
    public static Complex imaginary( double im ) {
        return new ImaginaryNumber( im );
    }

    /**
     * Creates a complex number from the specified Cartesian coordinates.
     *
     * @param re the real part
     * @param im the imaginary part
     *
     * @return a new {@code Complex} object
     */
    public static Complex cartesian( double re, double im ) {
        return new ComplexNumber( re, im );
    }

    /**
     * Creates a complex number from the specified polar coordinates.
     *
     * @param r the absolute value (modulus)
     * @param theta the argument (angle)
     *
     * @return a new {@code Complex} object
     */
    public static Complex polar( double r, double theta ) {
        double e = Math.cos( theta );
        double f = Math.sin( theta );
        if ( Double.isInfinite( r ) ) {
            /*
            The polar complex number with infinite modulus and finite argument is coerced into one of the infinite
            Cartesian complex numbers in such a way that the difference between the requested argument and the argument
            of the result is less than pi/8.
             */
            double er = e == 0.0 ? Math.copySign( 1.0, r ) : r;
            double fr = f == 0.0 ? Math.copySign( 1.0, r ) : r;
            return new ComplexNumber( er * e, fr * f );
        } else {
            return new ComplexNumber( r * e, r * f );
        }
    }

    /**
     * Returns a complex number with the value represented by the argument string.
     * <p/>
     * Leading and trailing whitespace characters in {@code source} are ignored. Whitespace is removed as if by the
     * {@link String#trim()} method. The rest of {@code source} should constitute a complex value as produced by the
     * {@link #toString()} method.
     *
     * @param source the string to be parsed
     *
     * @return a complex number with the value represented by the argument string
     *
     * @throws IllegalArgumentException if {@code source} is {@code null}
     * @throws NumberFormatException if {@code source} does not contain a parsable complex
     */
    public static Complex fromString( String source ) {
        if ( source == null ) {
            throw new IllegalArgumentException( "source is null" );
        }
        return ComplexFormat.stringToComplex( source );
    }


    /** Creates a new {@code Complex} object. */
    Complex() {
    }


    /**
     * Returns the real part of this complex number.
     *
     * @return the real part
     */
    public abstract double getRe();

    /**
     * Returns the imaginary part of this complex number.
     *
     * @return the imaginary part
     */
    public abstract double getIm();

    /**
     * Returns the absolute value (or modulus) of this complex number.
     *
     * @return the absolute value
     */
    public abstract double getAbs();

    /**
     * Returns the principal value of the argument (or angle) of this complex number.
     * <p/>
     * The principal value of a complex argument is defined as a value in the range [-pi, pi].
     *
     * @return the principal value of the argument
     */
    public abstract double getArg();


    /**
     * Returns a {@code Complex} whose value is the real part of this complex number.
     *
     * @return the real part as a {@code Complex} object
     *
     * @see #getRe()
     */
    public Complex re() {
        return new RealNumber( getRe() );
    }

    /**
     * Returns a {@code Complex} whose value is the imaginary part of this complex number.
     *
     * @return the imaginary part as a {@code Complex} object
     *
     * @see #getIm()
     */
    public Complex im() {
        return new RealNumber( getIm() );
    }

    /**
     * Returns a {@code Complex} whose value is the absolute value (or modulus) of this complex number.
     *
     * @return the absolute value as a {@code Complex} object
     *
     * @see #getAbs()
     */
    public Complex abs() {
        return new RealNumber( getAbs() );
    }

    /**
     * Returns a {@code Complex} whose value is the principal value of the argument (or angle) of this complex number.
     *
     * @return the principal value of the argument as a {@code Complex} object
     *
     * @see #getArg()
     */
    public Complex arg() {
        return new RealNumber( getArg() );
    }


    /**
     * Returns whether this complex number has a real part.
     *
     * @return {@code true} if this complex number has a real part; {@code false} otherwise
     */
    abstract boolean hasRealPart();

    /**
     * Returns whether this complex number has an imaginary part.
     *
     * @return {@code true} if this complex number has an imaginary part; {@code false} otherwise
     */
    abstract boolean hasImaginaryPart();


    /**
     * Returns a {@code Complex} whose value is the conjugate of this complex number.
     *
     * @return the conjugate of this complex number
     */
    public abstract Complex conjugate();

    /**
     * Returns a {@code Complex} whose value is the additive inverse of this complex number.
     *
     * @return {@code -this}
     */
    public abstract Complex negate();


    /**
     * Returns a {@code Complex} whose value is {@code this + value}.
     *
     * @param value the value to be added to this {@code Complex}
     *
     * @return {@code this + value}
     */
    public abstract Complex add( Complex value );

    abstract Complex addRealTransposed( double a );

    abstract Complex addImaginaryTransposed( double b );

    abstract Complex addComplexTransposed( double a, double b );


    /**
     * Returns a {@code Complex} whose value is {@code this - value}.
     *
     * @param value the value to be subtracted from this {@code Complex}
     *
     * @return {@code this - value}
     */
    public abstract Complex subtract( Complex value );

    abstract Complex subtractRealTransposed( double a );

    abstract Complex subtractImaginaryTransposed( double b );

    abstract Complex subtractComplexTransposed( double a, double b );


    /**
     * Returns a {@code Complex} whose value is {@code this · value}.
     *
     * @param value the value by which this {@code Complex} is to be multiplied
     *
     * @return {@code this · value}
     */
    public abstract Complex multiply( Complex value );

    abstract Complex multiplyRealTransposed( double a );

    abstract Complex multiplyImaginaryTransposed( double b );

    abstract Complex multiplyComplexTransposed( double a, double b );


    /**
     * Returns a {@code Complex} whose value is {@code this / value}.
     *
     * @param value the value by which this {@code Complex} is to be divided
     *
     * @return {@code this / value}
     */
    public abstract Complex divide( Complex value );

    abstract Complex divideRealTransposed( double a );

    abstract Complex divideImaginaryTransposed( double b );

    abstract Complex divideComplexTransposed( double a, double b );


    /**
     * Returns a {@code Complex} whose value is the principal value of the square root of this complex number.
     *
     * @return the principal value of the square root of this complex number
     */
    public abstract Complex sqrt();

    /**
     * Returns a {@code Complex} whose value is the principal value of the logarithm (base <i>e</i>) of this complex
     * number.
     * <p/>
     * The principal value of a complex logarithm is defined as <code>Log(z) = ln(|z|)+<i>i</i>Arg(z)</code>, where
     * {@code Arg(z)} is the principal value of the complex argument.
     *
     * @return the principal value of the logarithm of this complex number
     *
     * @see #getAbs()
     * @see #getArg()
     */
    public abstract Complex log();

    /**
     * Returns a {@code Complex} whose value is the number <i>e</i> raised to the power of this complex number.
     *
     * @return the value <i>e</i><sup>{@code this}</sup>, where <i>e</i> is the base of the natural logarithm
     */
    public abstract Complex exp();

    /**
     * Returns a {@code Complex} whose value is the principal value of this complex number raised to the power of the
     * {@code exponent}.
     * <p/>
     * The principal value of a complex exponential function is defined as <code>z<sup>w</sup> = <i>e</i><sup>w
     * Log(z)</sup></code> where {@code Log(z)} is the principal value of the complex logarithm.
     *
     * @param exponent the exponent
     *
     * @return the principal value of {@code this}<sup>{@code exponent}</sup>
     *
     * @see #log()
     */
    public abstract Complex pow( Complex exponent );

    abstract Complex powRealTransposed( double a );

    abstract Complex powImaginaryTransposed( double b );

    abstract Complex powComplexTransposed( double a, double b );


    /**
     * Returns a string representation of this complex number.
     * <p/>
     * The {@code Complex} string representation is based on the {@code double} string representation produced by the
     * {@link Double#toString(double)} method except that infinite values are represented by the characters {@code
     * u221E} (∞) and NaN is represented by the characters {@code uFFFD} (�).
     *
     * @return a string representation of this complex number
     *
     * @see Double#toString(double)
     */
    @Override
    public String toString() {
        return ComplexFormat.complexToString( this );
    }

}
