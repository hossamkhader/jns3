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

import net.jns3.core.Link;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import static java.util.Collections.synchronizedList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class LinksLayer extends JPanel implements ActionListener {
    
    private final List<Link> links;
    private final Timer timer;
    private final List<Bubble> bubbles;
    
    public LinksLayer()
    {
        links = new ArrayList<>();
        bubbles = synchronizedList(new ArrayList<>());
        setBounds(0 , 0, 1200, 400);
        timer = new Timer(10, this);
        timer.start();
    }
    
    public void addLink(Link link)
    {
        if(! links.contains(link))
        {
            link.getLine().x1 += 37;
            link.getLine().y1 += 37; 
            link.getLine().x2 += 37;
            link.getLine().y2 += 37;
            links.add(link);   
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        links.stream().forEach((l) -> {
            g.drawLine((int)l.getLine().x1, (int)l.getLine().y1, 
                       (int)l.getLine().x2, (int)l.getLine().y2);
            if(l.getLabel() != null)
            {
                int x = (int)((l.getLine().x1 + l.getLine().x2)/2);
                int y = (int)((l.getLine().y1 + l.getLine().y2)/2);
                g.setFont(new Font("default", Font.BOLD, 20));
                g.drawString(l.getLabel(), x, y);
            }
        });
        
        synchronized(bubbles)
        {
            Iterator<Bubble> i = bubbles.iterator();
            while (i.hasNext())
            {
                Bubble bubble = i.next();
                Line2D.Double bubbleLine = bubble.getLine();
                if(bubbleLine != null)
                {
                    g.setColor(bubble.getColor());
                    g.fillOval((int)bubble.x - 10, (int)bubble.y - 10, 20, 20);
                    double m = 1;
                    if(bubbleLine.x2 != bubbleLine.x1)
                        m = (bubbleLine.y2 - bubbleLine.y1) / (bubbleLine.x2 - bubbleLine.x1);
                    if(bubbleLine.x2 < bubbleLine.x1)
                        m = m * -1;
                    if(bubbleLine.x2 == bubbleLine.x1 && bubbleLine.y2 > bubbleLine.y1)
                        m = 1;
                    if(bubbleLine.x2 == bubbleLine.x1 && bubbleLine.y2 < bubbleLine.y1)
                        m = -1;
                
                    if(bubbleLine.x2 > bubbleLine.x1)
                        bubble.x++;
                    if(bubbleLine.x2 < bubbleLine.x1)
                        bubble.x--;
                    
                    bubble.y = bubble.y + m ;
                    
                    if(bubble.x == bubbleLine.x2 && bubbleLine.x2 != bubbleLine.x1)
                        bubble.setLine(null);
                    if(bubble.y == bubbleLine.y2 && bubbleLine.y2 != bubbleLine.y1)
                        bubble.setLine(null);
                }
            }
        }
    }
    
    public void drawBubble (Line2D.Double line, Color color)
    {
        bubbles.add(new Bubble(line, color));
    }
}
