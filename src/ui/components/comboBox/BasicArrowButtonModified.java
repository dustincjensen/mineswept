/*
 * Copyright (c) 1997, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package ui.components.comboBox;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import javax.swing.*;
import javax.swing.plaf.UIResource;

/**
 * Stripped down version of BasicArrowButton
 */
@SuppressWarnings("serial")
public class BasicArrowButtonModified extends JButton
{
    private int direction;
    private Color shadow;
    private Color darkShadow;
    private Color highlight;

    /**
     * Creates a {@code BasicArrowButton} whose arrow
     * is drawn in the specified direction and with the specified
     * colors.
     *
     * @param direction the direction of the arrow; one of
     *        {@code SwingConstants.NORTH}, {@code SwingConstants.SOUTH},
     *        {@code SwingConstants.EAST} or {@code SwingConstants.WEST}
     * @param background the background color of the button
     * @param shadow the color of the shadow
     * @param darkShadow the color of the dark shadow
     * @param highlight the color of the highlight
     */
    public BasicArrowButtonModified(int direction, Color background, Color shadow,
                        Color darkShadow, Color highlight) {
        super();
        setRequestFocusEnabled(false);
        setBackground(background);
        this.direction = direction;
        this.shadow = shadow;
        this.darkShadow = darkShadow;
        this.highlight = highlight;
    }

    public void paint(Graphics g) {
        var w = getSize().width;
        var h = getSize().height;
        var origColor = g.getColor();
        var isEnabled = isEnabled();

        g.setColor(getBackground());
        g.fillRect(1, 1, w-2, h-2);

        // Draw the proper Border
        if (getBorder() != null && !(getBorder() instanceof UIResource)) {
            paintBorder(g);
        } 
        else {
            g.setColor(shadow);
            g.drawRect(0, 0, w-1, h-1);
        }

        // If there's no room to draw arrow, bail
        if(h < 5 || w < 5)      {
            g.setColor(origColor);
            return;
        }

        // Draw the arrow
        var size = Math.min((h - 4) / 3, (w - 4) / 3);
        size = Math.max(size, 2);
        paintTriangle(g, (w - size) / 2, (h - size) / 2,
                            size, direction, isEnabled);

        g.setColor(origColor);
    }

    public Dimension getPreferredSize() {
        return new Dimension(16, 16);
    }

    public Dimension getMinimumSize() {
        return new Dimension(5, 5);
    }

    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public boolean isFocusTraversable() {
        return false;
    }

    /**
     * Paints a triangle.
     *
     * @param g the {@code Graphics} to draw to
     * @param x the x coordinate
     * @param y the y coordinate
     * @param size the size of the triangle to draw
     * @param direction the direction in which to draw the arrow;
     *        one of {@code SwingConstants.NORTH},
     *        {@code SwingConstants.SOUTH}, {@code SwingConstants.EAST} or
     *        {@code SwingConstants.WEST}
     * @param isEnabled whether or not the arrow is drawn enabled
     */
    private void paintTriangle(Graphics g, double x, double y, double size,
                                     int direction, boolean isEnabled) {
        size = Math.max(size, 2);
        Path2D.Double path = new Path2D.Double();
        path.moveTo(-size, size / 2);
        path.lineTo(size, size / 2);
        path.lineTo(0, -size / 2);
        path.closePath();
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.PI * (direction - 1) / 4);
        path.transform(affineTransform);

        Graphics2D g2d = (Graphics2D) g;
        double tx = x + size / 2;
        double ty = y + size / 2;
        g2d.translate(tx, ty);
        Color oldColor = g.getColor();
        if (!isEnabled) {
            g2d.translate(1, 0);
            g2d.setColor(highlight);
            g2d.fill(path);
            g2d.translate(-1, 0);
        }
        g2d.setColor(isEnabled ? darkShadow : shadow);
        g2d.fill(path);
        g2d.translate(-tx, -ty);
        g2d.setColor(oldColor);
    }
}
