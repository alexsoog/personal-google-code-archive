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

package com.google.code.ebmlviewer.viewer.compontents.label;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicLabelUI;

/**
 * Supports styled label text.
 * <p/>
 * If a label has {@code "attributedText"} client property set to an instance of the {@code AttributedString} then that
 * attributed string will be displayed instead of the regular label text.
 *
 * @see AttributedString
 */
public class AttributedLabelUI extends BasicLabelUI {

    public static AttributedString getAttributedText( JComponent component ) {
        return ( AttributedString ) component.getClientProperty( "attributedText" );
    }

    public static void setAttributedText( JComponent component, AttributedString attributedText ) {
        component.putClientProperty( "attributedText", attributedText );
    }


    public static boolean isResetAttributedTextForeground( JComponent component ) {
        return Boolean.TRUE.equals( component.getClientProperty( "resetAttributedTextForeground" ) );
    }

    public static void setResetAttributedTextForeground( JComponent component, boolean resetAttributedTextForeground ) {
        component.putClientProperty( "resetAttributedTextForeground", resetAttributedTextForeground ? true : null );
    }


    @Override
    protected void paintEnabledText( JLabel label, Graphics graphics, String text, int textX, int textY ) {
        AttributedString attributedText = ( AttributedString ) label.getClientProperty( "attributedText" );
        if ( attributedText == null ) {
            super.paintEnabledText( label, graphics, text, textX, textY );
        } else {
            if ( Boolean.TRUE.equals( label.getClientProperty( "resetAttributedTextForeground" ) ) ) {
                AttributedCharacterIterator iterator = attributedText.getIterator();
                Set<AttributedCharacterIterator.Attribute> iteratorKeys = iterator.getAllAttributeKeys();
                if ( iteratorKeys.remove( TextAttribute.FOREGROUND ) ) {
                    attributedText = new AttributedString( iterator, iterator.getBeginIndex(), iterator.getEndIndex(), iteratorKeys.toArray( new AttributedCharacterIterator.Attribute[ iteratorKeys.size() ] ) );
                }
            }
            Graphics2D graphics2d = ( Graphics2D ) graphics.create();

            AttributedCharacterIterator iterator = attributedText.getIterator();

            TextLayout textLayout = new TextLayout( iterator, label.getFontMetrics( label.getFont() ).getFontRenderContext() );
            //graphics2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
            //graphics2d.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
            //graphics2d.setRenderingHint( RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON );
            graphics2d.setFont( label.getFont() );
            graphics2d.setColor( label.getForeground() );
            textLayout.draw( graphics2d, textX, textY );

            Insets insets = label.getInsets();
            Rectangle viewR = new Rectangle( insets.left, insets.top, label.getWidth() - ( insets.left + insets.right ), label.getHeight() - ( insets.top + insets.bottom ) );
            Rectangle iconR = new Rectangle();
            Rectangle textR = new Rectangle();
            layoutCL( label, graphics2d.getFontMetrics(), text, label.getIcon(), viewR, iconR, textR );

            graphics2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF );
            for ( char c = iterator.first(); c != CharacterIterator.DONE; c = iterator.next() ) {
                Object underwave = iterator.getAttribute( AttributedLabelAttribute.UNDERWAVE );
                if ( underwave instanceof Paint ) {
                    int index = iterator.getIndex();
                    int runLimit = iterator.getRunLimit( AttributedLabelAttribute.UNDERWAVE );
                    iterator.setIndex( runLimit );
                    Rectangle bounds = textLayout.getBlackBoxBounds( index, runLimit ).getBounds();
                    graphics2d.setPaint( ( Paint ) underwave );
                    final int wave = 2; // todo configure wave size, or depend on font size
                    for ( int x = textR.x + bounds.x; x < textR.x + bounds.x + bounds.width; x += wave + wave ) {
                        int y = textR.y + textR.height - wave;
                        graphics2d.drawLine( x, y, x + wave, y + wave );
                        graphics2d.drawLine( x + wave, y + wave, x + wave + wave, y );
                    }
                }
            }

            graphics2d.dispose();
        }
    }

}
