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

package oe.assertions.predicates.numbers;

import java.util.EnumSet;
import java.util.Set;

import oe.assertions.Message;
import oe.assertions.Predicate;
import oe.assertions.util.Relation;
import oe.assertions.util.RelationComparator;

/** The base class for relation predicates that operate on {@code Number}s. */
public abstract class RelationPredicate<T extends Number> implements Predicate<T> {

    private final RelationComparator<T> comparator;

    private final T target;

    private final Set<Relation> relations;

    private final String description;


    /**
     * Creates a new predicate.
     *
     * @param target the target value
     * @param comparator the number comparator
     * @param relations the set of relation values acceptable by this predicate
     * @param description the description of the relation checked by this predicate
     *
     * @throws IllegalArgumentException if {@code target}, {@code comparator}, {@code relations} or {@code description}
     * is {@code null}
     */
    protected RelationPredicate( T target, RelationComparator<T> comparator, Set<Relation> relations, String description ) {
        if ( target == null ) {
            throw new IllegalArgumentException( "target is null" );
        }
        if ( comparator == null ) {
            throw new IllegalArgumentException( "comparator is null" );
        }
        if ( relations == null ) {
            throw new IllegalArgumentException( "relations is null" );
        }
        if ( description == null ) {
            throw new IllegalArgumentException( "description is null" );
        }
        this.target = target;
        this.comparator = comparator;
        this.relations = EnumSet.copyOf( relations );
        this.description = description;
    }


    @Override
    public boolean check( T input ) {
        return input != null && target != null && relations.contains( comparator.compare( input, target ) );
    }

    @Override
    public void describe( T input, Message message ) {
        message.append( description ).append( ' ' ).formatValue( target );
    }

}
