/*-
 * Copyright (c) 2008-2010, Oleg Estekhin
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

package oe.assertions.predicates;

import java.util.List;

/**
 * An operator used to combine the results of individual predicates in order to evaluate the result of a composite
 * predicate.
 */
public enum CompositeOperator {

    /** Logical AND operator. */
    ALL_OF() {
        @Override
        public boolean combine( List<Boolean> values ) {
            boolean value = true;
            for ( Boolean b : values ) {
                value = value && b;
            }
            return value;
        }

        @Override
        public String toString() {
            return "satisfies all of";
        }
    },
    /** Logical OR operator. */
    ANY_OF() {
        @Override
        public boolean combine( List<Boolean> values ) {
            boolean value = false;
            for ( Boolean b : values ) {
                value = value || b;
            }
            return value;
        }

        @Override
        public String toString() {
            return "satisfies any of";
        }
    };

    /**
     * Applies this operator to the specified values.
     *
     * @param values a list of individual values
     *
     * @return the combined value
     */
    public abstract boolean combine( List<Boolean> values );

}
