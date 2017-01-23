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

package com.google.code.ebmlviewer.viewer.compontents;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

/**
 * A layout manager that arranges components to have the same size and position and to fill the parent component.
 * <p/>
 * Components alignments and minimum and maximum sizes are not respected.
 */
public class ContentPaneLayout implements LayoutManager2 {

    /**
     * Returns the union of the specified dimensions.
     *
     * @param dimension1 the first dimension
     * @param dimension2 the second dimension
     *
     * @return the smallest dimension containing both {@code dimension1} and {@code dimension2}
     */
    private static Dimension union( Dimension dimension1, Dimension dimension2 ) {
        return new Dimension( Math.max( dimension1.width, dimension2.width ), Math.max( dimension1.height, dimension2.height ) );
    }

    /**
     * Resizes the dimension both horizontally and vertically.
     *
     * @param dimension the initial dimension
     * @param insets the expansion
     *
     * @return the new dimension
     */
    private static Dimension grow( Dimension dimension, Insets insets ) {
        int width = dimension.width < Integer.MAX_VALUE - insets.left - insets.right ? dimension.width + insets.left + insets.right : Integer.MAX_VALUE;
        int height = dimension.height < Integer.MAX_VALUE - insets.top + insets.bottom ? dimension.height + insets.top + insets.bottom : Integer.MAX_VALUE;
        return new Dimension( width, height );
    }


    @Override
    public void addLayoutComponent( String name, Component component ) {
        invalidateLayout( component.getParent() );
    }

    @Override
    public void addLayoutComponent( Component component, Object constraints ) {
        invalidateLayout( component.getParent() );
    }

    @Override
    public void removeLayoutComponent( Component component ) {
        invalidateLayout( component.getParent() );
    }


    @Override
    public Dimension preferredLayoutSize( Container container ) {
        Dimension dimension = new Dimension( 0, 0 );
        for ( int i = 0; i < container.getComponentCount(); i++ ) {
            dimension = union( dimension, container.getComponent( i ).getPreferredSize() );
        }
        return grow( dimension, container.getInsets() );
    }

    @Override
    public Dimension minimumLayoutSize( Container container ) {
        Dimension dimension = new Dimension( 0, 0 );
        for ( int i = 0; i < container.getComponentCount(); i++ ) {
            dimension = union( dimension, container.getComponent( i ).getMinimumSize() );
        }
        return grow( dimension, container.getInsets() );
    }

    @Override
    public Dimension maximumLayoutSize( Container container ) {
        Dimension dimension = new Dimension( 0, 0 );
        for ( int i = 0; i < container.getComponentCount(); i++ ) {
            dimension = union( dimension, container.getComponent( i ).getMaximumSize() );
        }
        return grow( dimension, container.getInsets() );
    }


    @Override
    public float getLayoutAlignmentX( Container container ) {
        return Component.CENTER_ALIGNMENT;
    }

    @Override
    public float getLayoutAlignmentY( Container container ) {
        return Component.CENTER_ALIGNMENT;
    }


    @Override
    public void invalidateLayout( Container container ) {
    }


    @Override
    public void layoutContainer( Container container ) {
        Insets insets = container.getInsets();
        int x = insets.left;
        int y = insets.top;
        int width = container.getWidth() - insets.left - insets.right;
        int height = container.getHeight() - insets.top - insets.bottom;
        for ( int i = 0; i < container.getComponentCount(); i++ ) {
            container.getComponent( i ).setBounds( x, y, width, height );
        }
    }

}
