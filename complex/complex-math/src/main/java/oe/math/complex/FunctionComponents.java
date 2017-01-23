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

import java.util.Deque;

import static oe.math.complex.ExpressionComponent.Type;

/** Contains predefined function and operator components. */
final class FunctionComponents {

    public static final FunctionComponent ADD = new FunctionComponent( Type.OPERATOR, Associativity.LEFT, 10, 2, 1, "+", "+" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex right = stack.pop();
            Complex left = stack.pop();
            stack.push( left.add( right ) );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatBinaryOperator( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            parseBinaryOperator( stack, output, context );
        }
    };

    public static final FunctionComponent SUBTRACT = new FunctionComponent( Type.OPERATOR, Associativity.LEFT, 10, 2, 1, "-", "-" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex right = stack.pop();
            Complex left = stack.pop();
            stack.push( left.subtract( right ) );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatBinaryOperator( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            Type previousType = context.getPreviousType();
            if ( previousType == Type.LEFT_PARENTHESIS || previousType == Type.OPERATOR ) {
                NEGATE.parse( stack, output, context );
            } else {
                parseBinaryOperator( stack, output, context );
            }
        }
    };

    public static final FunctionComponent MULTIPLY = new FunctionComponent( Type.OPERATOR, Associativity.LEFT, 20, 2, 1, "*", "*" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex right = stack.pop();
            Complex left = stack.pop();
            stack.push( left.multiply( right ) );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatBinaryOperator( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            parseBinaryOperator( stack, output, context );
        }
    };

    public static final FunctionComponent DIVIDE = new FunctionComponent( Type.OPERATOR, Associativity.LEFT, 20, 2, 1, "/", "/" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex right = stack.pop();
            Complex left = stack.pop();
            stack.push( left.divide( right ) );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatBinaryOperator( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            parseBinaryOperator( stack, output, context );
        }
    };


    public static final FunctionComponent NEGATE = new FunctionComponent( Type.OPERATOR, Associativity.RIGHT, 30, 1, 1, "-", "negate()" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex top = stack.pop();
            stack.push( top.negate() );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatUnaryOperator( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            Type previousType = context.getPreviousType();
            if ( previousType == Type.RIGHT_PARENTHESIS || previousType == Type.COMPLEX || previousType == Type.VARIABLE ) {
                SUBTRACT.parse( stack, output, context );
            } else {
                parseUnaryOperator( stack, output, context );
            }
        }
    };

    public static final FunctionComponent CONJUGATE = new FunctionComponent( Type.OPERATOR, Associativity.RIGHT, 30, 1, 1, "~", "~" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex top = stack.pop();
            stack.push( top.conjugate() );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatUnaryOperator( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            parseUnaryOperator( stack, output, context );
        }
    };


    public static final FunctionComponent RE = new FunctionComponent( Type.FUNCTION, Associativity.RIGHT, 100, 1, 1, "re", "re()" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex top = stack.pop();
            stack.push( top.re() );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatFunction( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            parseFunction( stack, output, context );
        }
    };

    public static final FunctionComponent IM = new FunctionComponent( Type.FUNCTION, Associativity.RIGHT, 100, 1, 1, "im", "im()" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex top = stack.pop();
            stack.push( top.im() );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatFunction( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            parseFunction( stack, output, context );
        }
    };

    public static final FunctionComponent ABS = new FunctionComponent( Type.FUNCTION, Associativity.RIGHT, 100, 1, 1, "abs", "abs()" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex top = stack.pop();
            stack.push( top.abs() );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatFunction( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            parseFunction( stack, output, context );
        }
    };

    public static final FunctionComponent ARG = new FunctionComponent( Type.FUNCTION, Associativity.RIGHT, 100, 1, 1, "arg", "arg()" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex top = stack.pop();
            stack.push( top.arg() );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatFunction( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            parseFunction( stack, output, context );
        }
    };


    public static final FunctionComponent SQRT = new FunctionComponent( Type.FUNCTION, Associativity.RIGHT, 100, 1, 1, "sqrt", "sqrt()" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex top = stack.pop();
            stack.push( top.sqrt() );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatFunction( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            parseFunction( stack, output, context );
        }
    };

    public static final FunctionComponent LOG = new FunctionComponent( Type.FUNCTION, Associativity.RIGHT, 100, 1, 1, "log", "log()" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex top = stack.pop();
            stack.push( top.log() );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatFunction( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            parseFunction( stack, output, context );
        }
    };

    public static final FunctionComponent EXP = new FunctionComponent( Type.FUNCTION, Associativity.RIGHT, 100, 1, 1, "exp", "exp()" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex top = stack.pop();
            stack.push( top.exp() );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatFunction( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            parseFunction( stack, output, context );
        }
    };

    public static final FunctionComponent POW = new FunctionComponent( Type.OPERATOR, Associativity.RIGHT, 30, 2, 1, "^", "^" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex right = stack.pop();
            Complex left = stack.pop();
            stack.push( left.pow( right ) );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            formatBinaryOperator( stack, context );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            parseBinaryOperator( stack, output, context );
        }
    };


    public static final FunctionComponent DUPLICATE = new FunctionComponent( Type.FUNCTION, Associativity.LEFT, Integer.MAX_VALUE, 1, 2, "#dup", "#dup" ) {
        @Override
        public void calculate( Deque<Complex> stack, CalculationContext context ) {
            Complex top = stack.peek();
            stack.push( top );
        }

        @Override
        public void format( Deque<FormatToken> stack, FormatContext context ) {
            FormatToken top = stack.peek();
            stack.push( top );
        }

        @Override
        public void parse( Deque<ExpressionComponent> stack, ExpressionBuilder output, ParseContext context ) {
            throw new AssertionError();
        }
    };


    private static final FunctionComponent[] VALUES = {
            ADD, SUBTRACT, MULTIPLY, DIVIDE,
            NEGATE, CONJUGATE,
            RE, IM, ABS, ARG,
            SQRT, LOG, EXP, POW,
            DUPLICATE,
    };

    public static FunctionComponent[] values() {
        return VALUES.clone();
    }


    private FunctionComponents() {
    }

}
