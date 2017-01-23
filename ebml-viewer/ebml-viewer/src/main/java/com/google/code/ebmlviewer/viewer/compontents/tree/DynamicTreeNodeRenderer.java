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

package com.google.code.ebmlviewer.viewer.compontents.tree;

import java.awt.Component;
import java.text.AttributedString;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.google.code.ebmlviewer.viewer.compontents.label.AttributedLabelUI;

/** Provides a base class for tree cell renderers. */
public class DynamicTreeNodeRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent( JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean focused ) {
        setToolTipText( null );
        if ( value instanceof DynamicTreeNode ) {
            DynamicTreeNode dynamicTreeNode = ( DynamicTreeNode ) value;
            super.getTreeCellRendererComponent( tree, dynamicTreeNode.getText(), selected, expanded, leaf, row, focused );
            if ( dynamicTreeNode.getIcon() != null ) {
                setIcon( dynamicTreeNode.getIcon() );
            }
            setAttributedText( dynamicTreeNode.getAttributedText() );
            setResetAttributedTextForeground( selected );
            configureDynamicTreeNodeRenderer( tree, dynamicTreeNode, selected, expanded, leaf, row, focused );
        } else {
            super.getTreeCellRendererComponent( tree, value, selected, expanded, leaf, row, focused );
            setAttributedText( null );
            setResetAttributedTextForeground( selected );
        }
        return this;
    }

    /** Provides a way to perform additional renderer configuration based on the node to be displayed. */
    protected void configureDynamicTreeNodeRenderer( JTree tree, DynamicTreeNode dynamicTreeNode, boolean selected, boolean expanded, boolean leaf, int row, boolean focused ) {}


    protected AttributedString getAttributedText() {
        return AttributedLabelUI.getAttributedText( this );
    }

    protected void setAttributedText( AttributedString attributedText ) {
        AttributedLabelUI.setAttributedText( this, attributedText );
    }


    protected boolean isResetAttributedTextForeground() {
        return AttributedLabelUI.isResetAttributedTextForeground( this );
    }

    protected void setResetAttributedTextForeground( boolean resetAttributedTextForeground ) {
        AttributedLabelUI.setResetAttributedTextForeground( this, resetAttributedTextForeground );
    }


    @Override
    public void updateUI() {
        setUI( new AttributedLabelUI() );
    }

}
