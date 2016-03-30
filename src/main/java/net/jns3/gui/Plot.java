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
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;


public class Plot extends JPanel{
    
    private final double [] data;
    
    public Plot(double [] data)
    {
        super();
        this.data = data;
        this.setPreferredSize(new Dimension(data.length, 300));
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.RED);
        int y1,y2;
        g.setColor( Color.BLACK );
        g.drawLine(0, 150, data.length, 150);
        g.setColor(Color.RED);
        for( int t=0 ; t < data.length -1 ; t++)
        {
            y1 = (int) (-100 * data[t]);
            y2 = (int) (-100 * data[t+1]);
            g.drawLine(t, y1+150, t+1, y2+150);
        }
    }
}
