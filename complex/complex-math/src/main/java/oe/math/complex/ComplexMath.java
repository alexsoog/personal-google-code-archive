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

/** Contains implementations of the complex operations. */
final class ComplexMath {
    /*
    The naming of arguments and local variables assumes that
    (e + fi) = (a + bi) op (c + di)

    The opComplexComplex(double a, double b, double c, double d) method contains the base algorithm which is adjusted
    by other methods according to their available arguments.
     */


    static double absReal( double a ) {
        return Math.abs( a );
    }

    static double absImaginary( double b ) {
        return Math.abs( b );
    }

    static double absComplex( double a, double b ) {
        return Math.hypot( a, b );
    }


    static double argReal( double a ) {
        if ( a != a ) {
            return a;
        } else if ( Double.compare( a, 0.0 ) >= 0 ) {
            return 0.0;
        } else {
            return Math.PI;
        }
    }

    static double argImaginary( double b ) {
        if ( b != b ) {
            return b;
        } else {
            return Math.copySign( Math.PI / 2.0, b );
        }
    }

    static double argComplex( double a, double b ) {
        return Math.atan2( b, a );
    }


    static Complex addRealReal( double a, double c ) {
        return new RealNumber( a + c );
    }

    static Complex addRealImaginary( double a, double d ) {
        return new ComplexNumber( a, d );
    }

    static Complex addRealComplex( double a, double c, double d ) {
        return new ComplexNumber( a + c, d );
    }

    static Complex addImaginaryReal( double b, double c ) {
        return new ComplexNumber( c, b );
    }

    static Complex addImaginaryImaginary( double b, double d ) {
        return new ImaginaryNumber( b + d );
    }

    static Complex addImaginaryComplex( double b, double c, double d ) {
        return new ComplexNumber( c, b + d );
    }

    static Complex addComplexReal( double a, double b, double c ) {
        return new ComplexNumber( a + c, b );
    }

    static Complex addComplexImaginary( double a, double b, double d ) {
        return new ComplexNumber( a, b + d );
    }

    static Complex addComplexComplex( double a, double b, double c, double d ) {
        return new ComplexNumber( a + c, b + d );
    }


    static Complex subtractRealReal( double a, double c ) {
        return new RealNumber( a - c );
    }

    static Complex subtractRealImaginary( double a, double d ) {
        return new ComplexNumber( a, -d );
    }

    static Complex subtractRealComplex( double a, double c, double d ) {
        return new ComplexNumber( a - c, -d );
    }

    static Complex subtractImaginaryReal( double b, double c ) {
        return new ComplexNumber( -c, b );
    }

    static Complex subtractImaginaryImaginary( double b, double d ) {
        return new ImaginaryNumber( b - d );
    }

    static Complex subtractImaginaryComplex( double b, double c, double d ) {
        return new ComplexNumber( -c, b - d );
    }

    static Complex subtractComplexReal( double a, double b, double c ) {
        return new ComplexNumber( a - c, b );
    }

    static Complex subtractComplexImaginary( double a, double b, double d ) {
        return new ComplexNumber( a, b - d );
    }

    static Complex subtractComplexComplex( double a, double b, double c, double d ) {
        return new ComplexNumber( a - c, b - d );
    }


    static Complex multiplyRealReal( double a, double c ) {
        return new RealNumber( a * c );
    }

    static Complex multiplyRealImaginary( double a, double d ) {
        return new ImaginaryNumber( a * d );
    }

    static Complex multiplyRealComplex( double a, double c, double d ) {
        return new ComplexNumber( a * c, a * d );
    }

    static Complex multiplyImaginaryReal( double b, double c ) {
        return new ImaginaryNumber( b * c );
    }

    static Complex multiplyImaginaryImaginary( double b, double d ) {
        return new RealNumber( -b * d );
    }

    static Complex multiplyImaginaryComplex( double b, double c, double d ) {
        return new ComplexNumber( -b * d, b * c );
    }

    static Complex multiplyComplexReal( double a, double b, double c ) {
        return new ComplexNumber( a * c, b * c );
    }

    static Complex multiplyComplexImaginary( double a, double b, double d ) {
        return new ComplexNumber( -b * d, a * d );
    }

    static Complex multiplyComplexComplex( double a, double b, double c, double d ) {
        return new ComplexNumber( a * c - b * d, a * d + b * c );
    }


    static Complex divideRealReal( double a, double c ) {
        return new RealNumber( a / c );
    }

    static Complex divideRealImaginary( double a, double d ) {
        return new ImaginaryNumber( -a / d );
    }

    static Complex divideRealComplex( double a, double c, double d ) {
        double e;
        double f;
        double denominator;
        if ( a == 0.0 || c == 0.0 && d == 0.0 || Double.isInfinite( c ) && Double.isInfinite( d ) ) {
            denominator = Math.abs( c ) + Math.abs( d );
            c = Math.copySign( 1.0, c );
            d = Math.copySign( 1.0, d );
            e = a * c;
            f = -a * d;
        } else {
            double absA = Math.abs( a );
            double absC = Math.abs( c );
            double absD = Math.abs( d );
            if ( absD >= absC ) {
                denominator = d + c * ( c / d );
                double absRD = 1.0 / absD;
                if ( absC >= absRD ) {
                    if ( absA >= absC ) {
                        e = c * ( a / d );
                    } else if ( absA >= absRD ) {
                        e = a * ( c / d );
                    } else {
                        e = a * c / d;
                    }
                } else {
                    if ( absA >= absRD ) {
                        e = a * c / d;
                    } else if ( absA >= absC ) {
                        e = a * ( c / d );
                    } else {
                        e = c * ( a / d );
                    }
                }
                f = -a;
            } else {
                denominator = c + d * ( d / c );
                double absRC = 1.0 / absC;
                e = a;
                if ( absD >= absRC ) {
                    if ( absA >= absD ) {
                        f = -d * ( a / c );
                    } else if ( absA >= absRC ) {
                        f = -a * ( d / c );
                    } else {
                        f = -a * d / c;
                    }
                } else {
                    if ( absA >= absRC ) {
                        f = -a * d / c;
                    } else if ( absA >= absD ) {
                        f = -a * ( d / c );
                    } else {
                        f = -d * ( a / c );
                    }
                }
            }
        }
        return new ComplexNumber( e / denominator, f / denominator );
    }

    static Complex divideImaginaryReal( double b, double c ) {
        return new ImaginaryNumber( b / c );
    }

    static Complex divideImaginaryImaginary( double b, double d ) {
        return new RealNumber( b / d );
    }

    static Complex divideImaginaryComplex( double b, double c, double d ) {
        double e;
        double f;
        double denominator;
        if ( b == 0.0 || c == 0.0 && d == 0.0 || Double.isInfinite( c ) && Double.isInfinite( d ) ) {
            denominator = Math.abs( c ) + Math.abs( d );
            c = Math.copySign( 1.0, c );
            d = Math.copySign( 1.0, d );
            e = b * d;
            f = b * c;
        } else {
            double absB = Math.abs( b );
            double absC = Math.abs( c );
            double absD = Math.abs( d );
            if ( absD >= absC ) {
                denominator = d + c * ( c / d );
                double absRD = 1.0 / absD;
                e = b;
                if ( absC >= absRD ) {
                    if ( absB >= absC ) {
                        f = c * ( b / d );
                    } else if ( absB >= absRD ) {
                        f = b * ( c / d );
                    } else {
                        f = b * c / d;
                    }
                } else {
                    if ( absB >= absRD ) {
                        f = b * c / d;
                    } else if ( absB >= absC ) {
                        f = b * ( c / d );
                    } else {
                        f = c * ( b / d );
                    }
                }
            } else {
                denominator = c + d * ( d / c );
                double absRC = 1.0 / absC;
                if ( absD >= absRC ) {
                    if ( absB >= absD ) {
                        e = d * ( b / c );
                    } else if ( absB >= absRC ) {
                        e = b * ( d / c );
                    } else {
                        e = b * d / c;
                    }
                } else {
                    if ( absB >= absRC ) {
                        e = b * d / c;
                    } else if ( absB >= absD ) {
                        e = b * ( d / c );
                    } else {
                        e = d * ( b / c );
                    }
                }
                f = b;
            }
        }
        return new ComplexNumber( e / denominator, f / denominator );
    }

    static Complex divideComplexReal( double a, double b, double c ) {
        return new ComplexNumber( a / c, b / c );
    }

    static Complex divideComplexImaginary( double a, double b, double d ) {
        return new ComplexNumber( b / d, -a / d );
    }

    static Complex divideComplexComplex( double a, double b, double c, double d ) {
        /*
        Stewart algorithm with all flips and swaps expanded into explicit if-else code blocks
         */
        double e;
        double f;
        double denominator;
        if ( a == 0.0 && b == 0.0 || c == 0.0 && d == 0.0 || Double.isInfinite( c ) && Double.isInfinite( d ) ) {
            denominator = Math.abs( c ) + Math.abs( d );
            c = Math.copySign( 1.0, c );
            d = Math.copySign( 1.0, d );
            e = a * c + b * d;
            f = b * c - a * d;
        } else {
            double absA = Math.abs( a );
            double absB = Math.abs( b );
            double absC = Math.abs( c );
            double absD = Math.abs( d );
            if ( absD >= absC ) {
                denominator = d + c * ( c / d );
                double absRD = 1.0 / absD;
                if ( absC >= absRD ) {
                    if ( absA >= absC ) {
                        e = b + c * ( a / d );
                    } else if ( absA >= absRD ) {
                        e = b + a * ( c / d );
                    } else {
                        e = b + a * c / d;
                    }
                    if ( absB >= absC ) {
                        f = c * ( b / d ) - a;
                    } else if ( absB >= absRD ) {
                        f = b * ( c / d ) - a;
                    } else {
                        f = b * c / d - a;
                    }
                } else {
                    if ( absA >= absRD ) {
                        e = b + a * c / d;
                    } else if ( absA >= absC ) {
                        e = b + a * ( c / d );
                    } else {
                        e = b + c * ( a / d );
                    }
                    if ( absB >= absRD ) {
                        f = b * c / d - a;
                    } else if ( absB >= absC ) {
                        f = b * ( c / d ) - a;
                    } else {
                        f = c * ( b / d ) - a;
                    }
                }
            } else {
                denominator = c + d * ( d / c );
                double absRC = 1.0 / absC;
                if ( absD >= absRC ) {
                    if ( absB >= absD ) {
                        e = a + d * ( b / c );
                    } else if ( absB >= absRC ) {
                        e = a + b * ( d / c );
                    } else {
                        e = a + b * d / c;
                    }
                    if ( absA >= absD ) {
                        f = b - d * ( a / c );
                    } else if ( absA >= absRC ) {
                        f = b - a * ( d / c );
                    } else {
                        f = b - a * d / c;
                    }
                } else {
                    if ( absB >= absRC ) {
                        e = a + b * d / c;
                    } else if ( absB >= absD ) {
                        e = a + b * ( d / c );
                    } else {
                        e = a + d * ( b / c );
                    }
                    if ( absA >= absRC ) {
                        f = b - a * d / c;
                    } else if ( absA >= absD ) {
                        f = b - a * ( d / c );
                    } else {
                        f = b - d * ( a / c );
                    }
                }
            }
        }
        return new ComplexNumber( e / denominator, f / denominator );
    }


    static Complex sqrtReal( double a ) {
        if ( a == Double.POSITIVE_INFINITY ) {
            return new RealNumber( Double.POSITIVE_INFINITY );
        } else if ( a == Double.NEGATIVE_INFINITY ) {
            return new ImaginaryNumber( Double.POSITIVE_INFINITY );
        } else if ( a != a ) {
            return new ComplexNumber( a, a );
        } else if ( Double.compare( a, 0.0 ) >= 0 ) {
            return new RealNumber( Math.sqrt( a ) );
        } else {
            return new ImaginaryNumber( Math.sqrt( -a ) );
        }
    }

    static Complex sqrtImaginary( double b ) {
        if ( b == Double.POSITIVE_INFINITY ) {
            return new ComplexNumber( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY );
        } else if ( b == Double.NEGATIVE_INFINITY ) {
            return new ComplexNumber( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY );
        } else if ( b != b ) {
            return new ComplexNumber( b, b );
        } else {
            double w = Math.sqrt( Math.abs( b ) / 2.0 );
            return new ComplexNumber( w, Math.copySign( w, b ) );
        }
    }

    static Complex sqrtComplex( double a, double b ) {
        if ( a == 0.0 ) {
            // ±0, any
            if ( b == Double.POSITIVE_INFINITY ) {
                return new ComplexNumber( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY );
            } else if ( b == Double.NEGATIVE_INFINITY ) {
                return new ComplexNumber( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY );
            } else if ( b != b ) {
                return new ComplexNumber( b, b );
            } else {
                double w = Math.sqrt( Math.abs( b ) / 2.0 );
                return new ComplexNumber( w, Math.copySign( w, b ) );
            }
        } else if ( a == Double.POSITIVE_INFINITY ) {
            // +inf, any
            if ( b == Double.POSITIVE_INFINITY ) {
                return new ComplexNumber( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY );
            } else if ( b == Double.NEGATIVE_INFINITY ) {
                return new ComplexNumber( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY );
            } else if ( b != b ) {
                return new ComplexNumber( Double.POSITIVE_INFINITY, b );
            } else {
                return new ComplexNumber( Double.POSITIVE_INFINITY, Math.copySign( 0.0, b ) );
            }
        } else if ( a == Double.NEGATIVE_INFINITY ) {
            // -inf, any
            if ( b == Double.POSITIVE_INFINITY ) {
                return new ComplexNumber( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY );
            } else if ( b == Double.NEGATIVE_INFINITY ) {
                return new ComplexNumber( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY );
            } else if ( b != b ) {
                return new ComplexNumber( b, Math.copySign( Double.POSITIVE_INFINITY, b ) );
            } else {
                return new ComplexNumber( 0.0, Math.copySign( Double.POSITIVE_INFINITY, b ) );
            }
        } else if ( a != a ) {
            // nan, any
            if ( Double.isInfinite( b ) ) {
                return new ComplexNumber( Double.POSITIVE_INFINITY, b );
            } else {
                return new ComplexNumber( a, a );
            }
        } else if ( b == Double.POSITIVE_INFINITY ) {
            // ±finite, +inf
            return new ComplexNumber( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY );
        } else if ( b == Double.NEGATIVE_INFINITY ) {
            // ±finite, -inf
            return new ComplexNumber( Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY );
        } else if ( b != b ) {
            // ±finite, nan
            return new ComplexNumber( b, b );
        } else {
            // ±finite, ±finite or ±0
            double absA = Math.abs( a );
            double absB = Math.abs( b );
            double w;
            if ( absA >= absB ) {
                double ba = absB / absA;
                w = Math.sqrt( absA ) * Math.sqrt( ( 1.0 + Math.sqrt( 1.0 + ba * ba ) ) / 2.0 );
            } else {
                double ab = absA / absB;
                w = Math.sqrt( absB ) * Math.sqrt( ( ab + Math.sqrt( 1.0 + ab * ab ) ) / 2.0 );
            }
            if ( Double.compare( a, 0.0 ) >= 0 ) {
                return new ComplexNumber( w, b / ( 2.0 * w ) );
            } else {
                return new ComplexNumber( Math.abs( b ) / ( 2.0 * w ), Math.copySign( w, b ) );
            }
        }
    }


    static Complex logReal( double a ) {
        if ( a == Double.POSITIVE_INFINITY ) {
            return new RealNumber( Double.POSITIVE_INFINITY );
        } else if ( a == Double.NEGATIVE_INFINITY ) {
            return new ComplexNumber( Double.POSITIVE_INFINITY, Math.PI );
        } else if ( a != a ) {
            return new ComplexNumber( a, a );
        } else if ( Double.compare( a, 0.0 ) >= 0 ) {
            return new RealNumber( Math.log( a ) );
        } else {
            return new ComplexNumber( Math.log( -a ), Math.PI );
        }
    }

    static Complex logImaginary( double b ) {
        if ( b == Double.POSITIVE_INFINITY ) {
            return new ComplexNumber( Double.POSITIVE_INFINITY, Math.PI / 2.0 );
        } else if ( b == Double.NEGATIVE_INFINITY ) {
            return new ComplexNumber( Double.POSITIVE_INFINITY, -Math.PI / 2.0 );
        } else if ( b != b ) {
            return new ComplexNumber( b, b );
        } else {
            return new ComplexNumber( Math.log( Math.abs( b ) ), Math.copySign( Math.PI / 2.0, b ) );
        }
    }

    static Complex logComplex( double a, double b ) {
        return new ComplexNumber( Math.log( Math.hypot( a, b ) ), Math.atan2( b, a ) );
    }


    static Complex expReal( double a ) {
        return new RealNumber( Math.exp( a ) );
    }

    static Complex expImaginary( double b ) {
        return new ComplexNumber( Math.cos( b ), Math.sin( b ) );
    }

    static Complex expComplex( double a, double b ) {
        double ere = Math.exp( a );
        double cos = Math.cos( b );
        double sin = Math.sin( b );
        double rc;
        double rs;
        if ( ere == Double.POSITIVE_INFINITY || ere == Double.NEGATIVE_INFINITY ) {
            rc = cos == 0.0 ? Math.copySign( 1.0, ere ) : ere;
            rs = sin == 0.0 ? Math.copySign( 1.0, ere ) : ere;
        } else {
            rc = ere;
            rs = ere;
        }
        return new ComplexNumber( rc * cos, rs * sin );
    }


    static Complex powRealReal( double a, double c ) {
        return new RealNumber( Math.pow( a, c ) );
    }

    static Complex powRealImaginary( double a, double d ) {
        // z^w = e^(w log z)
        // log z = log(a+bi) = p+qi
        double p = Math.log( absReal( a ) );
        double q = argReal( a );
        if ( d == 0.0 ) {
            double f = d * Math.copySign( 0.0, p ) + Math.copySign( 0.0, q );
            return new ComplexNumber( 1.0, f );
        } else {
            // w log z = (c+di)*(p+qi) = u+vi
            double u = -d * q;
            double v = d * p;
            // e^(w log z) = e^(u+vi)
            return expComplex( u, v );
        }
    }

    static Complex powRealComplex( double a, double c, double d ) {
        // z^w = e^(w log z)
        // log z = log(a+bi) = p+qi
        double p = Math.log( absReal( a ) );
        double q = argReal( a );
        if ( c == 0.0 && d == 0.0 ) {
            double f = d * Math.copySign( 0.0, p ) + c * Math.copySign( 0.0, q );
            return new ComplexNumber( 1.0, f );
        } else {
            // w log z = (c+di)*(p+qi) = u+vi
            double u = q == 0.0 ? c * p : c * p - d * q;
            double v = q == 0.0 ? d * p : d * p + c * q;
            // e^(w log z) = e^(u+vi)
            return expComplex( u, v );
        }
    }

    static Complex powImaginaryReal( double b, double c ) {
        // z^w = e^(w log z)
        // log z = log(a+bi) = p+qi
        double p = Math.log( absImaginary( b ) );
        double q = argImaginary( b );
        if ( c == 0.0 ) {
            double f = Math.copySign( 0.0, p ) + c * Math.copySign( 0.0, q );
            return new ComplexNumber( 1.0, f );
        } else {
            // w log z = (c+di)*(p+qi) = u+vi
            double u = c * p;
            double v = c * q;
            // e^(w log z) = e^(u+vi)
            return expComplex( u, v );
        }
    }

    static Complex powImaginaryImaginary( double b, double d ) {
        // z^w = e^(w log z)
        // log z = log(a+bi) = p+qi
        double p = Math.log( absImaginary( b ) );
        double q = argImaginary( b );
        if ( d == 0.0 ) {
            double f = d * Math.copySign( 0.0, p ) + Math.copySign( 0.0, q );
            return new ComplexNumber( 1.0, f );
        } else {
            // w log z = (c+di)*(p+qi) = u+vi
            double u = -d * q;
            double v = d * p;
            // e^(w log z) = e^(u+vi)
            return expComplex( u, v );
        }
    }

    static Complex powImaginaryComplex( double b, double c, double d ) {
        // z^w = e^(w log z)
        // log z = log(a+bi) = p+qi
        double p = Math.log( absImaginary( b ) );
        double q = argImaginary( b );
        if ( c == 0.0 && d == 0.0 ) {
            double f = d * Math.copySign( 0.0, p ) + c * Math.copySign( 0.0, q );
            return new ComplexNumber( 1.0, f );
        } else {
            // w log z = (c+di)*(p+qi) = u+vi
            double u = c * p - d * q;
            double v = d * p + c * q;
            // e^(w log z) = e^(u+vi)
            return expComplex( u, v );
        }
    }

    static Complex powComplexReal( double a, double b, double c ) {
        // z^w = e^(w log z)
        // log z = log(a+bi) = p+qi
        double p = Math.log( absComplex( a, b ) );
        double q = argComplex( a, b );
        if ( c == 0.0 ) {
            double f = Math.copySign( 0.0, p ) + c * Math.copySign( 0.0, q );
            return new ComplexNumber( 1.0, f );
        } else {
            // w log z = (c+di)*(p+qi) = u+vi
            double u = c * p;
            double v = c * q;
            // e^(w log z) = e^(u+vi)
            return expComplex( u, v );
        }
    }

    static Complex powComplexImaginary( double a, double b, double d ) {
        // z^w = e^(w log z)
        // log z = log(a+bi) = p+qi
        double p = Math.log( absComplex( a, b ) );
        double q = argComplex( a, b );
        if ( d == 0.0 ) {
            double f = d * Math.copySign( 0.0, p ) + Math.copySign( 0.0, q );
            return new ComplexNumber( 1.0, f );
        } else {
            // w log z = (c+di)*(p+qi) = u+vi
            double u = -d * q;
            double v = d * p;
            // e^(w log z) = e^(u+vi)
            return expComplex( u, v );
        }
    }

    static Complex powComplexComplex( double a, double b, double c, double d ) {
        // z^w = e^(w log z)
        // log z = log(a+bi) = p+qi
        double p = Math.log( absComplex( a, b ) );
        double q = argComplex( a, b );
        if ( c == 0.0 && d == 0.0 ) {
            double f = d * Math.copySign( 0.0, p ) + c * Math.copySign( 0.0, q );
            return new ComplexNumber( 1.0, f );
        } else {
            // w log z = (c+di)*(p+qi) = u+vi
            double u = c * p - d * q;
            double v = d * p + c * q;
            // e^(w log z) = e^(u+vi)
            return expComplex( u, v );
        }
    }


    private ComplexMath() {
    }

}
