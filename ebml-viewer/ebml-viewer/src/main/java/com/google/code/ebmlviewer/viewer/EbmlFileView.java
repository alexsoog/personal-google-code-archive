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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;

public class EbmlFileView extends FileView {

    private final FileSystemView fileSystemView;

    private final Map<String, String> descriptions;

    private final Map<String, Icon> icons;


    public EbmlFileView( FileSystemView fileSystemView ) {
        if ( fileSystemView == null ) {
            throw new IllegalArgumentException( "fileSystemView is null" );
        }
        this.fileSystemView = fileSystemView;

        descriptions = new HashMap<String, String>();
        icons = new HashMap<String, Icon>();
    }


    public void override( String extension, String description, Icon icon ) {
        if ( extension == null ) {
            throw new IllegalArgumentException( "extension is null" );
        }
        if ( description == null ) {
            descriptions.remove( extension );
        } else {
            descriptions.put( extension, description );
        }
        if ( icon == null ) {
            icons.remove( extension );
        } else {
            icons.put( extension, icon );
        }
    }

    private String getExtension( File file ) {
        String name = file.getName();
        int index = name.lastIndexOf( '.' );
        return index < 0 ? "" : name.substring( index + 1 );
    }


    @Override
    public String getName( File file ) {
        return fileSystemView.getSystemDisplayName( file );
    }

    @Override
    public String getDescription( File file ) {
        return getTypeDescription( file );
    }

    @Override
    public String getTypeDescription( File file ) {
        String description = descriptions.get( getExtension( file ) );
        return description == null ? fileSystemView.getSystemTypeDescription( file ) : description;
    }

    @Override
    public Icon getIcon( File file ) {
        Icon icon = icons.get( getExtension( file ) );
        return icon == null ? fileSystemView.getSystemIcon( file ) : icon;
    }

    @Override
    public Boolean isTraversable( File file ) {
        return fileSystemView.isTraversable( file );
    }

}
