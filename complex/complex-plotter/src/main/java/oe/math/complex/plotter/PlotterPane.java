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

package oe.math.complex.plotter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

final class PlotterPane extends JComponent {

    private final Point2D viewport;

    private final Point2D[][] rays;

    private final Function mirrorFunction;

    private final Function plotterFunction;

    private final int steps;


    PlotterPane( Point2D viewport, Point2D[][] rays, Function mirrorFunction, Function plotterFunction ) {
        this( viewport, rays, mirrorFunction, plotterFunction, 1000 );
    }

    PlotterPane( Point2D viewport, Point2D[][] rays, Function mirrorFunction, Function plotterFunction, int steps ) {
        setOpaque( true );
        setPreferredSize( new Dimension( 256, 256 ) );
        this.viewport = viewport;
        this.rays = rays;
        this.mirrorFunction = mirrorFunction;
        this.plotterFunction = plotterFunction;
        this.steps = steps;
    }


    @Override
    protected void paintComponent( Graphics graphics ) {
        int width = getWidth();
        int height = getHeight();
        if ( width > 0 && height > 0 ) {
            Graphics2D g = ( Graphics2D ) graphics.create();
            g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
            g.setColor( Color.WHITE );
            g.fillRect( 0, 0, width, height );

            double centerX = width / 2.0;
            double centerY = height / 2.0;
            double scaleX = width / viewport.getX();
            double scaleY = height / viewport.getY();

            g.setColor( new Color( 16, 16, 16, 32 ) );
            Path2D grid = new Path2D.Double();
            grid.moveTo( 0.0, centerY );
            grid.lineTo( width, centerY );
            grid.moveTo( centerX, 0.0 );
            grid.lineTo( centerX, height );
            for ( double x = 0.0; x <= viewport.getX() / 2.0; x += 1.0 ) {
                grid.moveTo( centerX + x * scaleX, centerY - 5.0 );
                grid.lineTo( centerX + x * scaleX, centerY + 5.0 );
                grid.moveTo( centerX - x * scaleX, centerY - 5.0 );
                grid.lineTo( centerX - x * scaleX, centerY + 5.0 );
            }
            for ( double y = 0.0; y <= viewport.getY() / 2.0; y += 1.0 ) {
                grid.moveTo( centerX + 5.0, centerY + y * scaleY );
                grid.lineTo( centerX - 5.0, centerY + y * scaleY );
                grid.moveTo( centerX + 5.0, centerY - y * scaleY );
                grid.lineTo( centerX - 5.0, centerY - y * scaleY );
            }
            g.draw( grid );

            AffineTransform transform = new AffineTransform();
            transform.translate( centerX, centerY );
            transform.scale( scaleX, -scaleY );
            Color plain = new Color( 255, 0, 0, 128 );
            Color mirror = new Color( 0, 0, 255, 128 );
            for ( Point2D[] ray : rays ) {
                Point2D from = ray[ 0 ];
                Point2D to = ray[ 1 ];
                g.setColor( plain );
                g.draw( createPath( from, to ).createTransformedShape( transform ) );
                g.setColor( mirror );
                from = mirrorFunction.calculate( from );
                to = mirrorFunction.calculate( to );
                g.draw( createPath( from, to ).createTransformedShape( transform ) );
            }

            g.dispose();
        }
    }

    private Path2D createPath( Point2D from, Point2D to ) {
        double fromRe = from.getX();
        double fromIm = from.getY();
        double toRe = to.getX();
        double toIm = to.getY();
        Path2D.Double path = new Path2D.Double();
        Point2D fromPoint = plotterFunction.calculate( from );
        path.moveTo( fromPoint.getX(),fromPoint.getY() );
        for ( int step = 1; step < steps; step++ ) {
            Point2D point = plotterFunction.calculate( new Point2D.Double(
                    fromRe == 0.0 && toRe == 0.0 ? fromRe + toRe : fromRe + ( toRe - fromRe ) * ( double ) step / ( double ) steps,
                    fromIm == 0.0 && toIm == 0.0 ? fromIm + toIm : fromIm + ( toIm - fromIm ) * ( double ) step / ( double ) steps
            ) );
            path.lineTo( point.getX(), point.getY() );
        }
        Point2D toPoint = plotterFunction.calculate( to ) ;
        path.lineTo( toPoint.getX(), toPoint.getY() );
        return path;
    }

}
