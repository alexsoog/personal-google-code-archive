/*-
 * Copyright (c) 2008-2012, Oleg Estekhin
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

package com.google.code.ebmlviewer.elements;

import com.google.code.ebmlviewer.core.VariableLengthInteger;

/** Defines the association between the identifier of an element and its name and type. */
public final class ElementDescriptor {

    /**
     * The element identifier.
     * <p/>
     * Corresponds to the 'element/@id' in the "source" Matroska specification.
     */
    private final VariableLengthInteger identifier;

    /**
     * The element name.
     * <p/>
     * Corresponds to the 'element/@name' in the "source" Matroska specification.
     */
    private final String name;

    /**
     * The element type.
     * <p/>
     * Corresponds to the 'element/@type' in the "source" Matroska specification.
     */
    private final ElementType type;


    /**
     * Provides a short description of the element.
     * <p/>
     * Corresponds to the 'element/*' in the "source" Matroska specification.
     */
    private String description;


    /**
     * Indicates whether the presence of element in the file is required.
     * <p/>
     * Corresponds to the 'element/@mandatory' in the "source" Matroska specification.
     */
    private boolean mandatory;


    /**
     * The level within an EBML tree that the element may occur at. The value of {@code -1} means a global element which
     * can be found at any level.
     * <p/>
     * Corresponds to the 'element/@level' in the "source" Matroska specification.
     */
    private int level;

    /**
     * Indicates whether the element can be nested recursively.
     * <p/>
     * Corresponds to the 'element/@recursive' in the "source" Matroska specification.
     */
    private boolean recursive;

    /**
     * Indicates whether the element may appear multiple times within its parent element.
     * <p/>
     * Corresponds to the 'element/@multiple' in the "source" Matroska specification.
     */
    private boolean multiple;


    /**
     * Defines valid range of values of the element.
     * <p/>
     * Corresponds to the 'element/@range' in the "source" Matroska specification.
     */
    private String valuesRange;

    /**
     * Defines the default value of the element.
     * <p/>
     * Corresponds to the 'element/@default' in the "source" Matroska specification.
     */
    private String defaultValue;


    /** Corresponds to the 'element/@minver' in the "source" Matroska specification. */
    private int minimumVersion;

    /** Corresponds to the 'element/@maxver' in the "source" Matroska specification. */
    private int maximumVersion;


    /** Corresponds to the 'element/@bytesize' in the "source" Matroska specification. */
    private int bytesize;


    /** Corresponds to the 'element/@webm' in the "source" Matroska specification. */
    private boolean webm;

    /** Corresponds to the 'element/@divx' in the "source" Matroska specification. */
    private boolean divx;


    /**
     * Creates a new {@code ElementDescriptor} object.
     *
     * @param identifier the element identifier
     * @param name the element name
     * @param type the element type
     *
     * @throws IllegalArgumentException if {@code identifier}, {@code name} or {@code type} is {@code null}
     * @throws IllegalArgumentException if {@code identifier} does not represent a valid identifier
     */
    public ElementDescriptor( VariableLengthInteger identifier, String name, ElementType type ) {
        if ( identifier == null ) {
            throw new IllegalArgumentException( "identifier is null" );
        }
        if ( !identifier.isIdentifier() ) {
            throw new IllegalArgumentException( "identifier is invalid: " + identifier );
        }
        if ( name == null ) {
            throw new IllegalArgumentException( "name is null" );
        }
        if ( type == null ) {
            throw new IllegalArgumentException( "type is null" );
        }
        this.identifier = identifier;
        this.name = name;
        this.type = type;
    }


    /**
     * Returns the identifier of the element defined by this descriptor.
     *
     * @return the element identifier
     */
    public VariableLengthInteger getIdentifier() {
        return identifier;
    }

    /**
     * Returns the name of the element defined by this descriptor.
     *
     * @return the element name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of the element defined by this descriptor.
     *
     * @return the element type
     */
    public ElementType getType() {
        return type;
    }


    /**
     * Returns the short description of the element defined by this descriptor.
     *
     * @return the element description
     */
    public String getDescription() {
        return description;
    }

    protected void setDescription( String description ) {
        this.description = description;
    }


    public boolean isMandatory() {
        return mandatory;
    }

    protected void setMandatory( boolean mandatory ) {
        this.mandatory = mandatory;
    }


    public int getLevel() {
        return level;
    }

    protected void setLevel( int level ) {
        this.level = level;
    }

    public boolean isRecursive() {
        return recursive;
    }

    protected void setRecursive( boolean recursive ) {
        this.recursive = recursive;
    }

    public boolean isMultiple() {
        return multiple;
    }

    protected void setMultiple( boolean multiple ) {
        this.multiple = multiple;
    }


    public String getValuesRange() {
        return valuesRange;
    }

    protected void setValuesRange( String valuesRange ) {
        this.valuesRange = valuesRange;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    protected void setDefaultValue( String defaultValue ) {
        this.defaultValue = defaultValue;
    }


    public int getMinimumVersion() {
        return minimumVersion;
    }

    protected void setMinimumVersion( int minimumVersion ) {
        this.minimumVersion = minimumVersion;
    }

    public int getMaximumVersion() {
        return maximumVersion;
    }

    protected void setMaximumVersion( int maximumVersion ) {
        this.maximumVersion = maximumVersion;
    }


    public int getBytesize() {
        return bytesize;
    }

    protected void setBytesize( int bytesize ) {
        this.bytesize = bytesize;
    }


    public boolean isWebM() {
        return webm;
    }

    protected void setWebM( boolean webm ) {
        this.webm = webm;
    }

    public boolean isDivX() {
        return divx;
    }

    protected void setDivX( boolean divx ) {
        this.divx = divx;
    }


    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + identifier.hashCode();
        result = 37 * result + name.hashCode();
        result = 37 * result + type.hashCode();
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        } else if ( obj instanceof ElementDescriptor ) {
            ElementDescriptor descriptor = ( ElementDescriptor ) obj;
            return identifier.equals( descriptor.identifier )
                    && name.equals( descriptor.name )
                    && type.equals( descriptor.type );
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format( "ElementDescriptor(%s, %s, %s)", identifier, name, type );
    }

}
