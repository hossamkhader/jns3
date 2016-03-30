/*
 * This file is part of "Java Network Simulator 3".
 *
 * (c) Hossam Khader. All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation;
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 * Author: Hossam Khader <hossamkhader@gmail.com>
 */

package net.jns3.gui;

import java.awt.Color;
import java.awt.geom.Line2D;

public class Bubble {
    
    private Line2D.Double line;
    public double x;
    public double y;
    private final Color color;
    
    public Bubble(Line2D.Double line, Color color)
    {
        this.line = line;
        x = line.x1;
        y = line.y1;
        this.color = color;
    }
    
    public Line2D.Double getLine()
    {
        return line;
    }
    
    public void setLine(Line2D.Double line)
    {
        this.line = line;
    }
    
    public Color getColor()
    {
        return color;
    }
}
