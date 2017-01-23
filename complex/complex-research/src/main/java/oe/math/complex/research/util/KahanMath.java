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

/** Utility functions from the "Branch cuts for complex elementary functions" by Prof. Kahan. */
public final class KahanMath {

    /** Ω := Overflow threshold = Nextafter(+∞, 0). */
    public static final double OVERFLOW_THRESHOLD = Double.MAX_VALUE;

    /** ε := Roundoff threshold = 1.0 - Nextafter(1.0, 0). */
    public static final double ROUNDOFF_THRESHOLD = 1.0 - Math.nextAfter( 1.0, 0.0 );

    /** λ := Underflow threshold = 4(1-ε)/Ω. */
    public static final double UNDERFLOW_THRESHOLD = Double.MIN_NORMAL;


    public static double[] corner( double x, double y ) {
        if ( Double.isInfinite( x ) && Double.isNaN( y ) ) {
            return new double[] { x, Math.copySign( Double.POSITIVE_INFINITY, y ) };
        } else if ( Double.isNaN( x ) && Double.isInfinite( y ) ) {
            return new double[] { Math.copySign( Double.POSITIVE_INFINITY, x ), y };
        } else {
            return new double[] { x, y };
        }
    }

    public static double multiplyWithPresubstitute( double a, double b ) {
        if ( a == 0.0 && Double.isInfinite( b ) || Double.isInfinite( a ) && b == 0.0 ) {
            return 0.0;
        } else {
            return a * b;
        }
    }

    public static double addWithPresubstitute( double a, double b ) {
        if ( Double.isInfinite( a ) && a == -b ) {
            return 0.0;
        } else {
            return a + b;
        }
    }

    public static double subtractWithPresubstitute( double a, double b ) {
        if ( Double.isInfinite( a ) && a == b ) {
            return 0.0;
        } else {
            return a - b;
        }
    }

    public static double logb( double x ) {
        if ( x != x ) {
            return x;
        } else if ( Double.isInfinite( x ) ) {
            return Double.POSITIVE_INFINITY;
        } else if ( x == 0.0 ) {
            return Double.NEGATIVE_INFINITY;
        } else {
            return Math.getExponent( x );
        }
    }

    public static double[] cmult( double u, double v, double x, double y ) {
        double e = u * x - v * y;
        double f = u * y + v * x;
        if ( e != e || f != f ) {
            // check for invalid operations: 0*∞ or ∞-∞
            boolean invalid = ( u == 0.0 || v == 0.0 ) && ( Double.isInfinite( x ) || Double.isInfinite( y ) )
                    || Double.isInfinite( u * x ) && u * x == v * y
                    || Double.isInfinite( u * y ) && u * y == -v * x;
            if ( invalid ) {
                if ( u == 0.0 && v == 0.0 || x == 0.0 && y == 0.0 ) {
                    // (0+0i)*inf
                    return new double[] { e, f };
                } else {
                    double[] uv = KahanMath.corner( u, v );
                    u = uv[ 0 ];
                    v = uv[ 1 ];
                    double[] xy = KahanMath.corner( x, y );
                    x = xy[ 0 ];
                    y = xy[ 1 ];
                }
            }
            e = subtractWithPresubstitute( multiplyWithPresubstitute( u, x ), multiplyWithPresubstitute( v, y ) );
            f = addWithPresubstitute( multiplyWithPresubstitute( u, y ), multiplyWithPresubstitute( v, x ) );
        }
        return new double[] { e, f };
    }

    public static double[] cdiv( double u, double v, double x, double y ) {
        double[] uv = corner( u, v );
        u = uv[ 0 ];
        v = uv[ 1 ];
        double[] xy = corner( x, y );
        x = xy[ 0 ];
        y = xy[ 1 ];
        if ( u != u || v != v || x != x || y != y ) {
            return new double[] { 0.0 * u * x * x * y, 0.0 * u * x * x * y };
        }
        int L = ( int ) Math.round( logb( Double.MAX_VALUE ) / 2.0 - 1.0 );
        double h = logb( Math.max( Math.abs( x ), Math.abs( y ) ) );
        if ( Double.isInfinite( h ) ) {
            h = Math.copySign( 8 * L, h );
        }
        int K = L - ( int ) Math.round( h );
        x = Math.scalb( x, K );
        y = Math.scalb( y, K );

        h = logb( Math.max( Math.abs( u ), Math.abs( v ) ) );
        if ( Double.isInfinite( h ) ) {
            h = Math.copySign( 8 * L, h );
        }
        int J = L - ( int ) Math.round( h );
        u = Math.scalb( u, J );
        v = Math.scalb( v, J );
        double d = x * x + y * y;
        if ( d == Double.POSITIVE_INFINITY || d == 0.0 ) {
            if ( Math.abs( x ) == d ) {
                x = Math.copySign( 1.0, x );
            }
            if ( y != 0.0 ) {
                y = Math.copySign( 1.0, y );
            }
        }
        double[] ef = cmult( u, v, x, -y );
        double e = ef[ 0 ];
        double f = ef[ 1 ];
        e = Math.scalb( e / d, K - J );
        f = Math.scalb( f / d, K - J );
        return new double[] { e, f };
    }

    public static double[] abs( double x, double y, KahanConstants constants ) {
        x = Math.abs( x );
        y = Math.abs( y );
        if ( x < y ) {
            double temp = x;
            x = y;
            y = temp;
        }
        if ( y == Double.POSITIVE_INFINITY ) {
            x = y;
        }
        double t = x - y;
        double s = 0.0;
        if ( x != Double.POSITIVE_INFINITY && t != x ) {
            // x != +inf, y != +inf, x - y != x
            if ( t > y ) {
                s = x / y;
                s = s + Math.sqrt( 1.0 + s * s );
            } else {
                s = t / y;
                t = ( 2.0 + s ) * s;
                s = constants.getT2P1() + t / ( constants.getR2() + Math.sqrt( 2.0 + t ) ) + s + constants.getR2P1();
            }
            s = y / s;
        }
        double e = x + s;
        double f = 0.0;
        return new double[] { e, f };
    }

    public static double[] arg( double x, double y ) {
        if ( x == 0.0 && y == 0.0 ) {
            x = Math.copySign( 1.0, x );
        }
//        if (Double.isInfinite(x) || Double.isInfinite(y)) {
//            double[] xy = KahanMath.cbox(x, y);
//        }
        if ( Double.isInfinite( x ) ) {
            x = Math.copySign( Double.MAX_VALUE, x );
        }
        if ( Double.isInfinite( y ) ) {
            y = Math.copySign( Double.MAX_VALUE, y );
        }
        double theta;
        if ( Math.abs( y ) > Math.abs( x ) ) {
            theta = Math.copySign( Math.PI / 2.0, y ) - Math.atan( x / y );
        } else if ( x < 0.0 ) {
            theta = Math.copySign( Math.PI, y ) + Math.atan( y / x );
        } else {
            theta = Math.atan( y / x );
        }
        double e = theta;
        double f = 0.0;
        return new double[] { e, f };
    }

    public static double[] cssqs( double x, double y ) {
        double k = 0.0;
        double r = x * x + y * y;
        if ( r != r && ( Double.isInfinite( x ) || Double.isInfinite( y ) ) ) {
            r = Double.POSITIVE_INFINITY;
        } else {
            boolean overflow = !Double.isInfinite( x ) && Double.isInfinite( x * x )
                    || !Double.isInfinite( y ) && Double.isInfinite( y * y )
                    || !Double.isInfinite( x * x ) && !Double.isInfinite( y * y ) && Double.isInfinite( r );
            boolean underflow = x != 0.0 && x * x == 0.0
                    || y != 0.0 && y * y == 0.0;
            if ( overflow || underflow && r < UNDERFLOW_THRESHOLD / ROUNDOFF_THRESHOLD ) {
                k = logb( Math.max( Math.abs( x ), Math.abs( y ) ) );
                int i = ( int ) Math.round( k );
                double sx = Math.scalb( x, -i );
                double sy = Math.scalb( y, -i );
                r = sx * sx + sy * sy;
            }
        }
        return new double[] { r, k };
    }

    public static double[] csqrt( double x, double y ) {
        double[] rk = cssqs( x, y );
        double r = rk[ 0 ];
        double k = rk[ 1 ];
        int ki = ( int ) Math.round( k );
        if ( x == x ) {
            r = Math.scalb( Math.abs( x ), -ki ) + Math.sqrt( r );
        }
        if ( ki % 2 != 0 ) {
            ki = ( ki - 1 ) / 2;
        } else {
            ki = ki / 2 - 1;
            r = r + r;
        }
        r = Math.scalb( Math.sqrt( r ), ki );
        double e = r;
        double f = y;
        if ( r != 0.0 ) {
            if ( !Double.isInfinite( f ) ) {
                f = ( f / r ) / 2.0;
            }
            if ( x < 0.0 ) {
                e = Math.abs( f );
                f = Math.copySign( r, y );
            }
        }
        return new double[] { e, f };
    }

    public static double[] clogs( double x, double y, int j ) {
        double T0 = 1.0 / Math.sqrt( 2.0 );
        double T1 = 5.0 / 4.0;
        double T2 = 3.0;
        double LN2 = Math.log( 2.0 );
        double[] rk = cssqs( x, y );
        double r = rk[ 0 ];
        double k = rk[ 1 ];
        double beta = Math.max( Math.abs( x ), Math.abs( y ) );
        double theta = Math.min( Math.abs( x ), Math.abs( y ) );
        if ( k == 0.0 && T0 < beta && ( beta <= T1 || r < T2 ) ) {
            r = Math.log1p( ( beta - 1.0 ) * ( beta + 1.0 ) + theta * theta ) / 2.0;
        } else {
            r = Math.log( r ) / 2.0 + ( k + j ) * LN2;
        }
        double e = r;
        double f = arg( x, y )[ 0 ];
        return new double[] { e, f };
    }


    private KahanMath() {
    }

}
