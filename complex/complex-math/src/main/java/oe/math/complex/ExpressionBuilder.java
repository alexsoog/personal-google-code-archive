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

import java.util.ArrayList;
import java.util.List;

/** Provides a fluent interface for building complex arithmetic expressions using the postfix notation. */
public final class ExpressionBuilder {

    private final List<ExpressionComponent> components;

    private int dataStackSize;


    ExpressionBuilder() {
        components = new ArrayList<ExpressionComponent>();
    }


    /**
     * Appends the specified component to this postfix expression.
     *
     * @param component the component to append
     *
     * @return this builder
     *
     * @throws IllegalArgumentException if {@code component} is {@code null}
     * @throws IllegalStateException if the component will have insufficient operands during the expression calculation
     */
    ExpressionBuilder append( ExpressionComponent component ) {
        if ( component == null ) {
            throw new IllegalArgumentException( "component is null" );
        }
        ExpressionComponent.Type type = component.getType();
        if ( type != ExpressionComponent.Type.COMPLEX
                && type != ExpressionComponent.Type.VARIABLE
                && type != ExpressionComponent.Type.FUNCTION
                && type != ExpressionComponent.Type.OPERATOR ) {
            throw new AssertionError( component );
        }
        if ( dataStackSize < component.getOperandsCount() ) {
            throw new IllegalStateException( "insufficient operands" );
        }
        dataStackSize = dataStackSize - component.getOperandsCount() + component.getResultsCount();
        components.add( component );
        return this;
    }


    /**
     * Appends a complex number with the specified real part to this postfix expression.
     *
     * @param re the real part
     *
     * @return this builder
     *
     * @see Complex#real(double)
     */
    public ExpressionBuilder real( double re ) {
        return append( new ComplexComponent( Complex.real( re ) ) );
    }

    /**
     * Appends a complex number with the specified imaginary part to this postfix expression.
     *
     * @param im the imaginary part
     *
     * @return this builder
     *
     * @see Complex#imaginary(double)
     */
    public ExpressionBuilder imaginary( double im ) {
        return append( new ComplexComponent( Complex.imaginary( im ) ) );
    }

    /**
     * Appends a complex number created from the specified Cartesian coordinates to this postfix expression.
     *
     * @param re the real part
     * @param im the imaginary part
     *
     * @return this builder
     *
     * @see Complex#cartesian(double, double)
     */
    public ExpressionBuilder cartesian( double re, double im ) {
        return append( new ComplexComponent( Complex.cartesian( re, im ) ) );
    }

    /**
     * Appends a complex number created from the specified polar coordinates to this postfix expression.
     *
     * @param r the absolute value (modulus)
     * @param theta the argument (angle)
     *
     * @return this builder
     *
     * @see Complex#polar(double, double)
     */
    public ExpressionBuilder polar( double r, double theta ) {
        return append( new ComplexComponent( Complex.polar( r, theta ) ) );
    }

    /**
     * Appends the specified complex number to this postfix expression.
     *
     * @param complex the complex number
     *
     * @return this builder
     *
     * @throws IllegalArgumentException if {@code complex} is {@code null}
     */
    public ExpressionBuilder complex( Complex complex ) {
        if ( complex == null ) {
            throw new IllegalArgumentException( "complex is null" );
        }
        return append( new ComplexComponent( complex ) );
    }


    /**
     * Appends the variable with the specified name to this postfix expression.
     *
     * @param name the variable name
     *
     * @return this builder
     *
     * @throws IllegalArgumentException if {@code name} is {@code null}, empty or contains illegal characters
     */
    public ExpressionBuilder variable( String name ) {
        return append( new VariableComponent( name ) );
    }


    /**
     * Appends the addition operation to this postfix expression.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#add(Complex)
     */
    public ExpressionBuilder add() {
        return append( FunctionComponents.ADD );
    }

    /**
     * Appends the subtraction operation to this postfix expression.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#subtract(Complex)
     */
    public ExpressionBuilder subtract() {
        return append( FunctionComponents.SUBTRACT );
    }

    /**
     * Appends the multiplication operation to this postfix expression.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#multiply(Complex)
     */
    public ExpressionBuilder multiply() {
        return append( FunctionComponents.MULTIPLY );
    }

    /**
     * Appends the division operation to this postfix expression.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#divide(Complex)
     */
    public ExpressionBuilder divide() {
        return append( FunctionComponents.DIVIDE );
    }


    /**
     * Appends the negation operation to this postfix expression.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#negate()
     */
    public ExpressionBuilder negate() {
        return append( FunctionComponents.NEGATE );
    }

    /**
     * Appends the conjugation operation to this postfix expression.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#conjugate()
     */
    public ExpressionBuilder conjugate() {
        return append( FunctionComponents.CONJUGATE );
    }


    /**
     * Appends the operation that replaces the complex number on top of the calculation stack with its real part.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#re()
     */
    public ExpressionBuilder re() {
        return append( FunctionComponents.RE );
    }

    /**
     * Appends the operation that replaces the complex number on top of the calculation stack with its imaginary part.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#im()
     */
    public ExpressionBuilder im() {
        return append( FunctionComponents.IM );
    }

    /**
     * Appends the operation that replaces the complex number on top of the calculation stack with its absolute value.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#abs()
     */
    public ExpressionBuilder abs() {
        return append( FunctionComponents.ABS );
    }

    /**
     * Appends the operation that replaces the complex number on top of the calculation stack with its argument.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#arg()
     */
    public ExpressionBuilder arg() {
        return append( FunctionComponents.ARG );
    }


    /**
     * Appends the square root operation to this postfix expression.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#sqrt()
     */
    public ExpressionBuilder sqrt() {
        return append( FunctionComponents.SQRT );
    }

    /**
     * Appends the logarithm operation to this postfix expression.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#log()
     */
    public ExpressionBuilder log() {
        return append( FunctionComponents.LOG );
    }

    /**
     * Appends the exponentiation operation to this postfix expression.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#exp()
     */
    public ExpressionBuilder exp() {
        return append( FunctionComponents.EXP );
    }

    /**
     * Appends the power operation to this postfix expression.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     * @see Complex#pow(Complex)
     */
    public ExpressionBuilder pow() {
        return append( FunctionComponents.POW );
    }


    /**
     * Appends the postfix form of the specified expression to this postfix expression.
     *
     * @param expression the expression
     *
     * @return this builder
     *
     * @throws IllegalArgumentException if {@code expression} is {@code null}
     */
    public ExpressionBuilder expression( Expression expression ) {
        if ( expression == null ) {
            throw new IllegalArgumentException( "expression is null" );
        }
        for ( ExpressionComponent component : expression.getComponents() ) {
            append( component );
        }
        return this;
    }


    /**
     * Appends the operation that duplicates the complex number on top of the calculation stack.
     *
     * @return this builder
     *
     * @throws IllegalStateException if the operation will have insufficient operands during the expression calculation
     */
    public ExpressionBuilder duplicate() {
        return append( FunctionComponents.DUPLICATE );
    }


    /**
     * Creates a new expression representing the sequence of operations in this builder.
     * <p/>
     * A new {@code Expression} object is created and initialized to contain the sequence of operations currently
     * represented by this builder. Subsequent changes to this builder do not affect the contents of the returned {@code
     * Expression}.
     *
     * @return a new expression
     *
     * @throws IllegalStateException if expression is incomplete
     */
    public Expression create() {
        if ( components.isEmpty() ) {
            throw new IllegalStateException( "expression is empty" );
        }
        if ( dataStackSize != 1 ) {
            throw new IllegalStateException( "expression is incomplete" );
        }
        return new Expression( components.toArray( new ExpressionComponent[components.size()] ) );
    }

}
