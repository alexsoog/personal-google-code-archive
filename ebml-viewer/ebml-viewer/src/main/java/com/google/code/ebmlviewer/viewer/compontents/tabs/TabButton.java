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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

final class TabButton extends JButton {

    private int previousHeight;


    TabButton() {
        setFocusable( false );
        setRolloverEnabled( true );

        previousHeight = -1;
    }


    @Override
    public Dimension getPreferredSize() {
        int size = getFontMetrics( getFont() ).getHeight();
        return new Dimension( size, size );
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }


    @Override
    protected void paintComponent( Graphics graphics ) {
        int height = getHeight();
        if ( previousHeight != height ) {
            setIcon( createIcon( height, false, false ) );
            setRolloverIcon( createIcon( height, true, false ) );
            setPressedIcon( createIcon( height, true, true ) );
            previousHeight = height;
        }
        super.paintComponent( graphics );
    }


    @Override
    public void updateUI() {
        previousHeight = -1;
        setUI( new BasicButtonUI() );
        setContentAreaFilled( false );
        setBorder( null );
    }


    private Icon createIcon( int dimension, boolean rollover, boolean pressed ) {
        BufferedImage image = new BufferedImage( dimension, dimension, BufferedImage.TYPE_INT_ARGB );

        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        Color background = getBackground();
        Color foreground = getForeground();

        if ( rollover ) {
            Ellipse2D.Float shape = new Ellipse2D.Float( 0.15f * dimension, 0.15f * dimension, 0.7f * dimension, 0.7f * dimension );

            graphics.setColor( background.darker() );
            graphics.fill( shape );

            GradientPaint spot = new GradientPaint( 0, 0, new Color( 255, 255, 255, 200 ), dimension, dimension, new Color( 255, 255, 255, 0 ) );
            Graphics2D tempGraphics = ( Graphics2D ) graphics.create();
            tempGraphics.setPaint( spot );
            tempGraphics.setClip( shape );
            tempGraphics.fillRect( 0, 0, dimension, dimension );
            tempGraphics.dispose();

            graphics.setColor( new Color( foreground.getRed(), foreground.getGreen(), foreground.getBlue(), 64 ) );
            graphics.draw( shape );
        }

        float widthOuter = ( float ) ( 0.3f * Math.pow( dimension, 0.75 ) );
        float widthInner = ( float ) ( 0.2f * Math.pow( dimension, 0.75 ) );

        GeneralPath path = new GeneralPath();
        path.moveTo( 0.35f * dimension, 0.35f * dimension );
        path.lineTo( 0.65f * dimension, 0.65f * dimension );
        path.moveTo( 0.65f * dimension, 0.35f * dimension );
        path.lineTo( 0.35f * dimension, 0.65f * dimension );

        graphics.setStroke( new BasicStroke( widthOuter, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
        graphics.setColor( new Color( foreground.getRed(), foreground.getGreen(), foreground.getBlue(), 196 ) );
        graphics.draw( path );

        graphics.setStroke( new BasicStroke( widthInner, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND ) );
        graphics.setColor( pressed ? foreground : Color.WHITE );
        graphics.draw( path );

        graphics.dispose();

        return new ImageIcon( image );
    }

}
