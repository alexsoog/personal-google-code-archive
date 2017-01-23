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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.google.code.ebmlviewer.core.VariableLengthInteger;

/** Contains static methods to load element descriptors from different sources. */
public final class ElementDescriptors {

    /**
     * Returns the default elements specification.
     * <p/>
     * The default element specification is based on the version of the "source" Matroska specification bundled with
     * this class. The latest version of the "source" Matroska specification can be loaded directly from the <a
     * href="https://matroska.svn.sourceforge.net/svnroot/matroska/trunk/foundation_src/spectool/specdata.xml">https://matroska.svn.sourceforge.net/svnroot/matroska/trunk/foundation_src/spectool/specdata.xml</a>
     * using the {@link #readDescriptors(URL)} method.
     *
     * @return the element descriptors
     */
    public static Map<VariableLengthInteger, ElementDescriptor> getDefaultDescriptors() {
        try {
            return readDescriptors( ElementDescriptors.class.getResource( "specdata.xml" ) );
        } catch ( IOException e ) {
            throw new AssertionError( e );
        } catch ( XMLStreamException e ) {
            throw new AssertionError( e );
        }
    }


    /**
     * Loads the elements specification from the specified url.
     *
     * @param url the location of the elements specification data
     *
     * @return the element descriptors
     *
     * @throws IllegalArgumentException if {@code url} is {@code null}
     * @throws IOException if an I/O error occurred
     * @throws XMLStreamException if an XML error occurred
     */
    public static Map<VariableLengthInteger, ElementDescriptor> readDescriptors( URL url ) throws IOException, XMLStreamException {
        if ( url == null ) {
            throw new IllegalArgumentException( "url is null" );
        }
        InputStream stream = new BufferedInputStream( url.openStream() );
        try {
            return readDescriptors( stream );
        } finally {
            try {
                stream.close();
            } catch ( IOException ignored ) {
            }
        }
    }

    /**
     * Loads the elements specification from the specified file.
     *
     * @param file the file to read from
     *
     * @return the element descriptors
     *
     * @throws IllegalArgumentException if {@code file} is {@code null}
     * @throws IOException if an I/O error occurred
     * @throws XMLStreamException if an XML error occurred
     */
    public static Map<VariableLengthInteger, ElementDescriptor> readDescriptors( File file ) throws IOException, XMLStreamException {
        if ( file == null ) {
            throw new IllegalArgumentException( "file is null" );
        }
        InputStream stream = new BufferedInputStream( new FileInputStream( file ) );
        try {
            return readDescriptors( stream );
        } finally {
            try {
                stream.close();
            } catch ( IOException ignored ) {
            }
        }
    }

    /**
     * Loads the elements specification from the specified data stream.
     *
     * @param stream the data stream to read from
     *
     * @return the element descriptors
     *
     * @throws IllegalArgumentException if {@code stream} is {@code null}
     * @throws IOException if an I/O error occurred
     * @throws XMLStreamException if an XML error occurred
     */
    public static Map<VariableLengthInteger, ElementDescriptor> readDescriptors( InputStream stream ) throws IOException, XMLStreamException {
        if ( stream == null ) {
            throw new IllegalArgumentException( "stream is null" );
        }
        XMLEventReader reader = XMLInputFactory.newFactory().createXMLEventReader( stream );
        try {
            return readDescriptors( reader );
        } finally {
            try {
                reader.close();
            } catch ( XMLStreamException ignored ) {
            }
        }
    }

    private static Map<VariableLengthInteger, ElementDescriptor> readDescriptors( XMLEventReader reader ) throws XMLStreamException {
        XMLEvent event = reader.nextTag();
        if ( !event.isStartElement() || !"table".equals( event.asStartElement().getName().getLocalPart() ) ) {
            throw new XMLStreamException( "the 'table' element is expected as a root element", event.getLocation() );
        }
        return readTable( reader, event.asStartElement() );
    }

    private static Map<VariableLengthInteger, ElementDescriptor> readTable( XMLEventReader reader, StartElement current ) throws XMLStreamException {
        // pre-condition: reader.current == <table>
        Map<VariableLengthInteger, ElementDescriptor> descriptors = new LinkedHashMap<VariableLengthInteger, ElementDescriptor>();
        XMLEvent event = reader.nextTag();
        while ( event.isStartElement() && "element".equals( event.asStartElement().getName().getLocalPart() ) ) {
            ElementDescriptor descriptor = readElement( reader, event.asStartElement() );
            descriptors.put( descriptor.getIdentifier(), descriptor );
            event = reader.nextTag();
        }
        return descriptors;
        // post-condition: reader.current == </table>
    }


    private static ElementDescriptor readElement( XMLEventReader reader, StartElement current ) throws XMLStreamException {
        // pre-condition: reader.current == <element>
        Attribute identifier = current.getAttributeByName( QName.valueOf( "id" ) );
        if ( identifier == null ) {
            throw new XMLStreamException( "the 'id' attribute is required", current.getLocation() );
        }
        Attribute name = current.getAttributeByName( QName.valueOf( "name" ) );
        if ( name == null ) {
            throw new XMLStreamException( "the 'name' attribute is required", current.getLocation() );
        }
        Attribute type = current.getAttributeByName( QName.valueOf( "type" ) );
        if ( type == null ) {
            throw new XMLStreamException( "the 'type' attribute is required", current.getLocation() );
        }

        ElementDescriptor descriptor = new ElementDescriptor( VariableLengthInteger.fromString( identifier.getValue() ), name.getValue(), ElementType.fromString( type.getValue() ) );

        descriptor.setMandatory( readAttribute( current, "mandatory", false ) );

        descriptor.setLevel( readAttribute( current, "level", -1 ) );
        descriptor.setRecursive( readAttribute( current, "recursive", false ) );
        descriptor.setMultiple( readAttribute( current, "multiple", false ) );

        descriptor.setValuesRange( readAttribute( current, "range", null ) );
        descriptor.setDefaultValue( readAttribute( current, "default", null ) );

        descriptor.setMinimumVersion( readAttribute( current, "minver", 1 ) );
        descriptor.setMaximumVersion( readAttribute( current, "maxver", 1 ) );

        descriptor.setBytesize( readAttribute( current, "bytesize", 1 ) );

        descriptor.setWebM( readAttribute( current, "webm", false ) );
        descriptor.setDivX( readAttribute( current, "divx", false ) );

        descriptor.setDescription( readMixedContent( reader, null ) );

        return descriptor;
        // post-condition: reader.current == </element>
    }

    private static boolean readAttribute( StartElement element, String attributeName, boolean defaultValue ) throws XMLStreamException {
        Attribute attribute = element.getAttributeByName( QName.valueOf( attributeName ) );
        if ( attribute != null ) {
            try {
                return Integer.parseInt( attribute.getValue() ) != 0;
            } catch ( NumberFormatException e ) {
                throw new XMLStreamException( String.format( "the value of the '%s' attribute is expected to be an integer", attributeName ), element.getLocation(), e );
            }
        }
        return defaultValue;
    }

    private static int readAttribute( StartElement element, String attributeName, int defaultValue ) throws XMLStreamException {
        Attribute attribute = element.getAttributeByName( QName.valueOf( attributeName ) );
        if ( attribute != null ) {
            try {
                return Integer.parseInt( attribute.getValue() );
            } catch ( NumberFormatException e ) {
                throw new XMLStreamException( String.format( "the value of the '%s' attribute is expected to be an integer", attributeName ), element.getLocation(), e );
            }
        }
        return defaultValue;
    }

    private static String readAttribute( StartElement element, String attributeName, String defaultValue ) {
        Attribute attribute = element.getAttributeByName( QName.valueOf( attributeName ) );
        if ( attribute != null ) {
            return attribute.getValue();
        }
        return defaultValue;
    }

    private static String readMixedContent( XMLEventReader reader, XMLEvent current ) throws XMLStreamException {
        // pre-condition: reader.current == <xxx>
        StringBuilder buffer = new StringBuilder();
        if ( current != null ) {
            buffer.append( current ); // assuming correct XMLEvent.toString()
        }
        XMLEvent event = reader.nextEvent();
        while ( !event.isEndElement() ) {
            if ( event.isStartElement() ) {
                buffer.append( readMixedContent( reader, event ) );
            } else {
                buffer.append( event ); // assuming correct XMLEvent.toString()
            }
            event = reader.nextEvent();
        }
        if ( current != null ) {
            buffer.append( event ); // assuming correct XMLEvent.toString()
        }
        return buffer.toString();
        // post-condition: reader.current == </xxx>
    }


    private ElementDescriptors() {
    }

}
