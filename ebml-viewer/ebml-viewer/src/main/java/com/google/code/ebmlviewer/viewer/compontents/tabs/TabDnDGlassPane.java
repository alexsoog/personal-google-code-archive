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

package com.google.code.ebmlviewer.viewer.compontents.tabs;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

final class TabDnDGlassPane extends JComponent {

    private final AlphaComposite composite;

    private Image dragImage;

    private Point dragImageLocation;

    private Image dropLocationImage;

    private Point dropLocation;


    TabDnDGlassPane() {
        composite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.5f );
    }


    public Image getDragImage() {
        return dragImage;
    }

    public void setDragImage( Image dragImage ) {
        this.dragImage = dragImage;
    }

    public void showDragImage( Point location ) {
        dragImageLocation = location;
    }

    public void hideDragImage() {
        dragImageLocation = null;
    }


    private void prepareDropLocationImage() {
        if ( dropLocationImage == null ) {
            int dimension = getFontMetrics( getFont() ).getHeight();
            dropLocationImage = createDropLocationImage( dimension );
        }
    }

    private Image createDropLocationImage( int dimension ) {
        BufferedImage image = new BufferedImage( dimension, dimension, BufferedImage.TYPE_INT_ARGB );
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        graphics.translate( -0.5d, 0.0 );

        Color foreground = getForeground();
        Color background = getBackground();

        GeneralPath path = new GeneralPath();
        path.moveTo( 0.35f * dimension, 0.1f * dimension );
        path.lineTo( 0.65f * dimension, 0.1f * dimension );
        path.lineTo( 0.65f * dimension, 0.55f * dimension );
        path.lineTo( 0.85f * dimension, 0.55f * dimension );
        path.lineTo( 0.5f * dimension, 0.9f * dimension );
        path.lineTo( 0.15f * dimension, 0.55f * dimension );
        path.lineTo( 0.35f * dimension, 0.55f * dimension );
        path.closePath();

        graphics.setColor( background.brighter() );
        graphics.fill( path );
        graphics.setColor( new Color( foreground.getRed(), foreground.getGreen(), foreground.getBlue(), 196 ) );
        graphics.draw( path );

        graphics.dispose();
        return image;
    }

    public void showDropLocation( Point location ) {
        dropLocation = location;
    }

    public void hideDropLocation() {
        dropLocation = null;
    }


    @Override
    protected void paintComponent( Graphics g ) {
        Graphics2D graphics = ( Graphics2D ) g.create();
        graphics.setComposite( composite );
        if ( dragImage != null && dragImageLocation != null ) {
            int x = dragImageLocation.x - dragImage.getWidth( null ) / 2;
            int y = dragImageLocation.y - dragImage.getHeight( null ) / 2;
            graphics.drawImage( dragImage, x, y, null );
        }
        if ( dropLocation != null ) {
            prepareDropLocationImage();
            int x = dropLocation.x - dropLocationImage.getWidth( null ) / 2;
            int y = dropLocation.y - dropLocationImage.getHeight( null ) * 2 / 3;
            graphics.drawImage( dropLocationImage, x, y, null );
        }
        graphics.dispose();
    }

}
