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

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import oe.math.complex.Complex;

public final class Plotter {

    public static void main( String[] args ) {
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {
                JFrame frame = createFrame();
                frame.setLocationByPlatform( true );
                frame.setVisible( true );
            }
        } );
    }

    private static JFrame createFrame() {
        final CardLayout containerLayout = new CardLayout();
        final JPanel container = new JPanel( containerLayout );
        container.add( createMouthpiecePanel(), "Borda's Mouthpiece" );
        container.add( createFlowPanel(), "Flow past a Unit Disk" );
        container.add( createSqrtPanel(), "Square Root" );

        JComboBox selector = new JComboBox( new Object[] { "Borda's Mouthpiece", "Flow past a Unit Disk", "Square Root" } );
        selector.addItemListener( new ItemListener() {
            @Override
            public void itemStateChanged( ItemEvent e ) {
                if ( e.getStateChange() == ItemEvent.SELECTED ) {
                    containerLayout.show( container, e.getItem().toString() );
                }
            }
        } );
        JPanel selectorPanel = new JPanel( null );
        GroupLayout selectorLayout = new GroupLayout( selectorPanel );
        selectorPanel.setLayout( selectorLayout );
        selectorLayout.setHorizontalGroup(
                selectorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent( selector )
                        .addContainerGap()
        );
        selectorLayout.setVerticalGroup(
                selectorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent( selector )
        );

        JFrame frame = new JFrame( "Complex Plotter" );
        frame.getContentPane().add( selectorPanel, BorderLayout.PAGE_START );
        frame.getContentPane().add( container, BorderLayout.CENTER );
        frame.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        frame.pack();
        return frame;
    }

    private static JComponent createMouthpiecePanel() {
        Point2D viewport = new Point2D.Double( 8.0, 8.0 );
        double r = 4.0;
        Point2D[][] rays = {
                { new Point2D.Double( 0.0, 0.0 ), new Point2D.Double( r, 0.0 ) },
                { new Point2D.Double( 0.0, 0.0 ), new Point2D.Double( r * Math.cos( Math.PI / 12.0 ), r * Math.sin( Math.PI / 12.0 ) ) },
                { new Point2D.Double( 0.0, 0.0 ), new Point2D.Double( r * Math.cos( Math.PI * 2.0 / 12.0 ), r * Math.sin( Math.PI * 2.0 / 12.0 ) ) },
                { new Point2D.Double( 0.0, 0.0 ), new Point2D.Double( r * Math.cos( Math.PI * 3.0 / 12.0 ), r * Math.sin( Math.PI * 3.0 / 12.0 ) ) },
                { new Point2D.Double( 0.0, 0.0 ), new Point2D.Double( r * Math.cos( Math.PI * 4.0 / 12.0 ), r * Math.sin( Math.PI * 4.0 / 12.0 ) ) },
                { new Point2D.Double( 0.0, 0.0 ), new Point2D.Double( r * Math.cos( Math.PI * 5.0 / 12.0 ), r * Math.sin( Math.PI * 5.0 / 12.0 ) ) },
                { new Point2D.Double( 0.0, 0.0 ), new Point2D.Double( 0.0, r ) },
        };
        Function mirrorFunction = new Function( "Mouthpiece mirror" ) {
            @Override
            public Point2D calculate( Point2D point ) {
                return new Point2D.Double( point.getX(), -point.getY() );
            }
        };
        Function[] plotterFunctions = {
                new Function( "original rays" ) {
                    @Override
                    public Point2D calculate( Point2D point ) {
                        return point;
                    }
                },
                new Function( Complex.class.getName() ) {
                    @Override
                    public Point2D calculate( Point2D point ) {
                        Complex c = Complex.cartesian( point.getX(), point.getY() );
                        Complex c2 = c.multiply( c );
                        Complex term0 = c2.add( Complex.ONE );
                        Complex term1 = c.multiply( term0.sqrt() );
                        Complex g = c2.add( term1 );
                        Complex term3 = g.log();
                        Complex term4 = g.add( term3 );
                        Complex f = term4.add( Complex.ONE );
                        return new Point2D.Double( f.getRe(), f.getIm() );
                    }
                },
                new Function( org.apache.commons.math.complex.Complex.class.getName() ) {
                    @Override
                    public Point2D calculate( Point2D point ) {
                        org.apache.commons.math.complex.Complex c = new org.apache.commons.math.complex.Complex( point.getX(), point.getY() );
                        org.apache.commons.math.complex.Complex c2 = c.multiply( c );
                        org.apache.commons.math.complex.Complex term0 = c2.add( org.apache.commons.math.complex.Complex.ONE );
                        org.apache.commons.math.complex.Complex term1 = c.multiply( term0.sqrt() );
                        org.apache.commons.math.complex.Complex g = c2.add( term1 );
                        org.apache.commons.math.complex.Complex term3 = g.log();
                        org.apache.commons.math.complex.Complex term4 = g.add( term3 );
                        org.apache.commons.math.complex.Complex f = term4.add( org.apache.commons.math.complex.Complex.ONE );
                        return new Point2D.Double( f.getReal(), f.getImaginary() );
                    }
                },
        };
        return createPlotterPanel( viewport, rays, mirrorFunction, plotterFunctions );
    }

    private static JComponent createFlowPanel() {
        Point2D viewport = new Point2D.Double( 4.0, 4.0 );
        double r = 2.0;
        Point2D[][] rays = {
                { new Point2D.Double( -r, 0.0 ), new Point2D.Double( r, 0.0 ) },
                { new Point2D.Double( -r, 0.1 ), new Point2D.Double( r, 0.1 ) },
                { new Point2D.Double( -r, 0.2 ), new Point2D.Double( r, 0.2 ) },
                { new Point2D.Double( -r, 0.3 ), new Point2D.Double( r, 0.3 ) },
                { new Point2D.Double( -r, 0.4 ), new Point2D.Double( r, 0.4 ) },
                { new Point2D.Double( -r, 0.5 ), new Point2D.Double( r, 0.5 ) },
                { new Point2D.Double( -r, 0.6 ), new Point2D.Double( r, 0.6 ) },
                { new Point2D.Double( -r, 0.7 ), new Point2D.Double( r, 0.7 ) },
        };
        Function mirrorFunction = new Function( "Flow mirror" ) {
            @Override
            public Point2D calculate( Point2D point ) {
                return new Point2D.Double( point.getX(), -point.getY() );
            }
        };
        Function[] plotterFunctions = {
                new Function( "original rays" ) {
                    @Override
                    public Point2D calculate( Point2D point ) {
                        return point;
                    }
                },
                new Function( Complex.class.getName() ) {
                    @Override
                    public Point2D calculate( Point2D point ) {
                        Complex c = Complex.cartesian( point.getX(), point.getY() );
                        c = c.conjugate();
                        Complex f1 = c.negate().subtract( Complex.ONE ).sqrt();
                        Complex f2 = c.negate().add( Complex.ONE ).sqrt();
                        Complex f = f1.multiply( f2 ).subtract( c );
                        return new Point2D.Double( f.getRe(), f.getIm() );
                    }
                },
                new Function( org.apache.commons.math.complex.Complex.class.getName() ) {
                    @Override
                    public Point2D calculate( Point2D point ) {
                        org.apache.commons.math.complex.Complex c = new org.apache.commons.math.complex.Complex( point.getX(), point.getY() );
                        c = c.conjugate();
                        org.apache.commons.math.complex.Complex f1 = c.negate().subtract( org.apache.commons.math.complex.Complex.ONE ).sqrt();
                        org.apache.commons.math.complex.Complex f2 = c.negate().add( org.apache.commons.math.complex.Complex.ONE ).sqrt();
                        org.apache.commons.math.complex.Complex f = f1.multiply( f2 ).subtract( c );
                        return new Point2D.Double( f.getReal(), f.getImaginary() );
                    }
                },
        };
        return createPlotterPanel( viewport, rays, mirrorFunction, plotterFunctions );
    }

    private static JComponent createSqrtPanel() {
        Point2D viewport = new Point2D.Double( 8.0, 8.0 );
        double r = 2.0;
        Point2D[][] rays = {
                { new Point2D.Double( 0.0, r ), new Point2D.Double( r, 0.0 ) },
                { new Point2D.Double( -0.0, 1.5 * r ), new Point2D.Double( -1.5 * r, 0.0 ) },
        };
        Function mirrorFunction = new Function( "sqrt mirror" ) {
            @Override
            public Point2D calculate( Point2D point ) {
                return new Point2D.Double( -point.getX(), -point.getY() );
            }
        };
        Function[] plotterFunctions = {
                new Function( "original rays" ) {
                    @Override
                    public Point2D calculate( Point2D point ) {
                        return point;
                    }
                },
                new Function( Complex.class.getName() ) {
                    @Override
                    public Point2D calculate( Point2D point ) {
                        Complex c = Complex.cartesian( point.getX(), point.getY() );
                        Complex f = c.sqrt();
                        return new Point2D.Double( f.getRe(), f.getIm() );
                    }
                },
                new Function( org.apache.commons.math.complex.Complex.class.getName() ) {
                    @Override
                    public Point2D calculate( Point2D point ) {
                        org.apache.commons.math.complex.Complex c = new org.apache.commons.math.complex.Complex( point.getX(), point.getY() );
                        org.apache.commons.math.complex.Complex f = c.sqrt();
                        return new Point2D.Double( f.getReal(), f.getImaginary() );
                    }
                },
        };
        return createPlotterPanel( viewport, rays, mirrorFunction, plotterFunctions );
    }

    private static JComponent createPlotterPanel( Point2D viewport, Point2D[][] rays, Function mirrorFunction, Function[] plotterFuntions ) {
        JLabel[] labels = new JLabel[plotterFuntions.length];
        JComponent[] components = new JComponent[plotterFuntions.length];
        for ( int i = 0; i < plotterFuntions.length; i++ ) {
            labels[ i ] = new JLabel( plotterFuntions[ i ].getName() );
            components[ i ] = new PlotterPane( viewport, rays, mirrorFunction, plotterFuntions[ i ] );
        }

        JPanel panel = new JPanel( null );
        GroupLayout layout = new GroupLayout( panel );
        layout.setAutoCreateContainerGaps( true );
        panel.setLayout( layout );

        GroupLayout.SequentialGroup horizontalGroup = layout.createSequentialGroup();
        for ( int i = 0; i < plotterFuntions.length; i++ ) {
            if ( i > 0 ) {
                horizontalGroup.addPreferredGap( LayoutStyle.ComponentPlacement.UNRELATED );
            }
            horizontalGroup.addGroup( layout.createParallelGroup().addComponent( labels[ i ] ).addComponent( components[ i ] ) );
        }
        layout.setHorizontalGroup( horizontalGroup );

        GroupLayout.Group verticalGroup = layout.createSequentialGroup();
        GroupLayout.Group labelsGroup = layout.createParallelGroup();
        GroupLayout.Group componentsGroup = layout.createParallelGroup();
        for ( int i = 0; i < plotterFuntions.length; i++ ) {
            labelsGroup.addComponent( labels[ i ] );
            componentsGroup.addComponent( components[ i ] );
        }
        verticalGroup.addGroup( labelsGroup ).addGroup( componentsGroup );
        layout.setVerticalGroup( verticalGroup );

        return panel;
    }


    private Plotter() {
    }

}
