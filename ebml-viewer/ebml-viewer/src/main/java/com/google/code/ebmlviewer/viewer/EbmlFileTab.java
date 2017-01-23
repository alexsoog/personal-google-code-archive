/*-
 * Copyright (c) 2011-2012, Oleg Estekhin
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

package com.google.code.ebmlviewer.viewer;

import java.awt.Color;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.event.TreeExpansionEvent;

import com.google.code.ebmlviewer.core.EbmlDecoder;
import com.google.code.ebmlviewer.core.VariableLengthInteger;
import com.google.code.ebmlviewer.elements.ElementDescriptor;
import com.google.code.ebmlviewer.elements.ElementType;
import com.google.code.ebmlviewer.io.EbmlFile;
import com.google.code.ebmlviewer.io.EbmlFileEntry;
import com.google.code.ebmlviewer.viewer.compontents.tabs.Tab;
import com.google.code.ebmlviewer.viewer.compontents.tree.DynamicTree;
import com.google.code.ebmlviewer.viewer.compontents.tree.DynamicTreeModel;
import com.google.code.ebmlviewer.viewer.compontents.tree.DynamicTreeNode;
import com.google.code.ebmlviewer.viewer.compontents.tree.DynamicTreeNodeRenderer;
import com.google.code.ebmlviewer.viewer.util.AttributedStringBuilder;
import com.google.code.ebmlviewer.viewer.util.PlainTextResourceBundleControl;

import static com.google.code.ebmlviewer.elements.ElementDescriptors.getDefaultDescriptors;

public class EbmlFileTab extends Tab {

    private static final ResourceBundle resources = ResourceBundle.getBundle( EbmlFileTab.class.getPackage().getName() + ".messages", new PlainTextResourceBundleControl( "txt", "UTF-8" ) );


    private static final Map<ElementType, Icon> typeIcons;

    static {
        typeIcons = new HashMap<ElementType, Icon>();
        typeIcons.put( ElementType.SIGNED_INTEGER, new ImageIcon( EbmlFileTab.class.getResource( "signedInteger.png" ) ) );
        typeIcons.put( ElementType.UNSIGNED_INTEGER, new ImageIcon( EbmlFileTab.class.getResource( "unsignedInteger.png" ) ) );
        typeIcons.put( ElementType.FLOATING_POINT, new ImageIcon( EbmlFileTab.class.getResource( "floatingPoint.png" ) ) );
        typeIcons.put( ElementType.ASCII_STRING, new ImageIcon( EbmlFileTab.class.getResource( "string.png" ) ) );
        typeIcons.put( ElementType.UNICODE_STRING, new ImageIcon( EbmlFileTab.class.getResource( "string.png" ) ) );
        typeIcons.put( ElementType.DATE, new ImageIcon( EbmlFileTab.class.getResource( "date.png" ) ) );
        typeIcons.put( ElementType.BINARY, new ImageIcon( EbmlFileTab.class.getResource( "binary.png" ) ) );
        typeIcons.put( ElementType.MASTER, new ImageIcon( EbmlFileTab.class.getResource( "master.png" ) ) );
        typeIcons.put( null, new ImageIcon( EbmlFileTab.class.getResource( "unknown.png" ) ) );
    }

    private static final Icon exceptionIcon = new ImageIcon( EbmlFileTab.class.getResource( "exception.png" ) );


    // attribute maps for node text are shared between all tabs and all nodes
    private static final Map<AttributedCharacterIterator.Attribute, Object> utilAttributes = new HashMap<AttributedCharacterIterator.Attribute, Object>();

    private static final Map<AttributedCharacterIterator.Attribute, Object> nameAttributes = new HashMap<AttributedCharacterIterator.Attribute, Object>();

    private static final Map<AttributedCharacterIterator.Attribute, Object> identifierAttributes = new HashMap<AttributedCharacterIterator.Attribute, Object>();

    private static final Map<AttributedCharacterIterator.Attribute, Object> typeAttributes = new HashMap<AttributedCharacterIterator.Attribute, Object>();

    private static final Map<AttributedCharacterIterator.Attribute, Object> positionAttributes = new HashMap<AttributedCharacterIterator.Attribute, Object>();

    private static final Map<AttributedCharacterIterator.Attribute, Object> sizeAttributes = new HashMap<AttributedCharacterIterator.Attribute, Object>();

    private static final Map<AttributedCharacterIterator.Attribute, Object> valueAttributes = new HashMap<AttributedCharacterIterator.Attribute, Object>();

    private static final Map<AttributedCharacterIterator.Attribute, Object> errorAttributes = new HashMap<AttributedCharacterIterator.Attribute, Object>();

    static {

        utilAttributes.put( TextAttribute.FOREGROUND, Color.GRAY );

        nameAttributes.put( TextAttribute.FOREGROUND, new Color( 0x800000 ) );

        //identifierAttributes.put( TextAttribute.FOREGROUND, Color.BLACK );

        typeAttributes.put( TextAttribute.FOREGROUND, new Color( 0x000080 ) );

        //positionAttributes.put( TextAttribute.FOREGROUND, Color.BLACK );
        //sizeAttributes.put( TextAttribute.FOREGROUND, Color.BLACK );

        valueAttributes.put( TextAttribute.FOREGROUND, new Color( 0x006000 ) );

        errorAttributes.put( TextAttribute.FOREGROUND, new Color( 0xa00000 ) );
    }


    private final EbmlFile ebmlFile;

    private final File file;

    private final Map<VariableLengthInteger, ElementDescriptor> descriptors;


    public EbmlFileTab( EbmlFile ebmlFile, File file, Icon icon ) {
        if ( ebmlFile == null ) {
            throw new IllegalArgumentException( "ebmlFile is null" );
        }
        if ( file == null ) {
            throw new IllegalArgumentException( "file is null" );
        }
        setDefaultCloseOperation( DefaultCloseOperation.DISPOSE_ON_CLOSE );
        setTitle( file.getName() );
        setIcon( icon );

        this.ebmlFile = ebmlFile;
        this.file = file;

        descriptors = getDefaultDescriptors();

        FileTreeNode root = new FileTreeNode( ebmlFile );
        root.setText( file.getAbsolutePath() );
        root.setIcon( icon );
        root.willExpand( null );
        DynamicTree tree = new DynamicTree( new DynamicTreeModel( root ) );
        tree.setCellRenderer( new DynamicTreeNodeRenderer() );

        setContentPane( new JScrollPane( tree ) );
    }


    public File getFile() {
        return file;
    }


    @Override
    public void dispose() {
        try {
            ebmlFile.close();
        } catch ( IOException e ) {
            Logger.getLogger( getClass().getName() ).log( Level.WARNING, "exception thrown while closing an I/O resource", e );
        }
        super.dispose();
    }


    private abstract class MasterTreeNode extends DynamicTreeNode {

        protected abstract boolean getEntriesWillBlock();

        protected abstract List<EbmlFileEntry> getEntries() throws IOException;

        @Override
        public void willExpand( TreeExpansionEvent event ) {
            if ( getChildCount() != 0 ) {
                return;
            }
            if ( event != null && getEntriesWillBlock() ) {
                DynamicTreeNode node = new DynamicTreeNode( null, typeIcons.get( null ) );
                AttributedStringBuilder builder = new AttributedStringBuilder();
                builder.append( resources.getString( "masterTreeNode.loading" ), utilAttributes );
                node.setAttributedText( builder.build() );
                appendChild( node );
                new BackgroundEntryLoader().execute();
            } else {
                try {
                    List<EbmlFileEntry> entries = getEntries();
                    for ( EbmlFileEntry child : entries ) {
                        appendChildSilently( new EntryTreeNode( child, descriptors.get( child.getIdentifier() ) ) );
                    }
                } catch ( Exception e ) {
                    appendChildSilently( new DynamicTreeNode( e.getLocalizedMessage(), exceptionIcon ) );
                }
            }
        }

        private class BackgroundEntryLoader extends SwingWorker<List<EbmlFileEntry>, Object> {

            @Override
            protected List<EbmlFileEntry> doInBackground() throws Exception {
                return getEntries();
            }

            @Override
            protected void done() {
                removeChild( 0 );
                if ( isCancelled() ) {
                    return;
                }
                try {
                    List<EbmlFileEntry> entries = get();
                    for ( EbmlFileEntry child : entries ) {
                        appendChild( new EntryTreeNode( child, descriptors.get( child.getIdentifier() ) ) );
                    }
                } catch ( InterruptedException e ) {
                    throw new AssertionError( e );
                } catch ( ExecutionException e ) {
                    appendChild( new DynamicTreeNode( e.getCause().getLocalizedMessage(), exceptionIcon ) );
                }
            }

        }

    }

    private class FileTreeNode extends MasterTreeNode {

        private final EbmlFile file;


        private FileTreeNode( EbmlFile file ) {
            this.file = file;
        }


        @Override
        protected boolean getEntriesWillBlock() {
            return file.getEntriesWillBlock();
        }

        @Override
        protected List<EbmlFileEntry> getEntries() throws IOException {
            return file.getEntries();
        }

    }

    private class EntryTreeNode extends MasterTreeNode {

        private final EbmlFileEntry entry;

        private final ElementDescriptor descriptor;


        EntryTreeNode( EbmlFileEntry entry, ElementDescriptor descriptor ) {
            if ( entry == null ) {
                throw new IllegalArgumentException( "entry is null" );
            }
            this.entry = entry;
            this.descriptor = descriptor;

            AttributedStringBuilder builder = new AttributedStringBuilder();

            // name
            String name = descriptor == null ? "?" : descriptor.getName();
            builder.append( name, nameAttributes );

            // type
            String type = descriptor == null ? "?" : descriptor.getType().toString();
            builder.append( " (", utilAttributes )
                    .append( type, typeAttributes )
                    .append( ')', utilAttributes );

            // position, id, size
            builder.append( " [", utilAttributes )
                    .append( "position ", utilAttributes )
                    .append( String.format( "#%d", entry.getEntryPosition() ), positionAttributes )
                    .append( ", ", utilAttributes )
                    .append( "identifier ", utilAttributes )
                    .append( entry.getIdentifier().toString(), identifierAttributes )
                    .append( " (", utilAttributes ).append( String.valueOf( entry.getIdentifier().getEncodedLength() ), utilAttributes ).append( ')', utilAttributes )
                    .append( ", ", utilAttributes )
                    .append( "size ", utilAttributes )
                    .append( String.format( "%,d", entry.getSize().getPlainValue() ), sizeAttributes )
                    .append( " (", utilAttributes ).append( String.valueOf( entry.getSize().getEncodedLength() ), utilAttributes ).append( ')', utilAttributes )
                    .append( ']', utilAttributes );

            // data
            if ( descriptor != null && descriptor.getType() != ElementType.BINARY && descriptor.getType() != ElementType.MASTER ) {
                try {
                    EbmlDecoder decoder = new EbmlDecoder();
                    ByteBuffer data = ByteBuffer.allocate( Math.min( 8 * 1024, ( int ) entry.getSize().getPlainValue() ) );
                    entry.read( data );
                    data.flip();
                    String valueText;
                    switch ( descriptor.getType() ) {
                        case SIGNED_INTEGER:
                            long signedInteger = decoder.decodeSignedInteger( data, ( int ) entry.getSize().getPlainValue() );
                            valueText = String.format( "%,d (%<#x)", signedInteger );
                            break;
                        case UNSIGNED_INTEGER:
                            long unsignedInteger = decoder.decodeUnsignedInteger( data, ( int ) entry.getSize().getPlainValue() );
                            valueText = String.format( "%,d (%<#x)", unsignedInteger );
                            break;
                        case FLOATING_POINT:
                            double floatingPoint = decoder.decodeFloatingPoint( data, ( int ) entry.getSize().getPlainValue() );
                            valueText = String.format( "%,.10g (%<s)", floatingPoint );
                            break;
                        case ASCII_STRING:
                            String asciiString = decoder.decodeAsciiString( data, ( int ) entry.getSize().getPlainValue() );
                            valueText = String.format( "%s", asciiString );
                            break;
                        case UNICODE_STRING:
                            String unicodeString = decoder.decodeUnicodeString( data, ( int ) entry.getSize().getPlainValue() );
                            valueText = String.format( "%s", unicodeString );
                            break;
                        case DATE:
                            long date = decoder.decodeDate( data, ( int ) entry.getSize().getPlainValue() );
                            valueText = String.format( "%tF %<tT.%<tL", date );
                            break;
//                    case BINARY:
//                        break;
//                    case MASTER:
//                        break;
                        default:
                            valueText = null;
                    }
                    if ( valueText != null ) {
                        builder.append( " = ", utilAttributes )
                                .append( valueText, valueAttributes );
                    }
                } catch ( Exception e ) {
                    // IllegalEncodedLengthException
                    // BufferUnderflowException
                    // CharacterCodingException
                    builder.append( " = ", utilAttributes ).append( e.getLocalizedMessage(), errorAttributes );
                }
            }

            setAttributedText( builder.build() );
            setIcon( descriptor == null ? typeIcons.get( null ) : typeIcons.get( descriptor.getType() ) );
        }


        @Override
        protected boolean getEntriesWillBlock() {
            return entry.getEntriesWillBlock();
        }

        @Override
        protected List<EbmlFileEntry> getEntries() throws IOException {
            return entry.getEntries();
        }

        @Override
        public void willExpand( TreeExpansionEvent event ) {
            if ( descriptor == null || descriptor.getType() != ElementType.MASTER ) {
                return;
            }
            super.willExpand( event );
        }

        @Override
        public boolean isLeaf() {
            return descriptor == null || descriptor.getType() != ElementType.MASTER;
        }

    }

}
