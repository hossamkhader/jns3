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

import java.awt.BorderLayout;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class SignalDisplay extends JFrame implements KeyEventPostProcessor
{   
    public SignalDisplay(double [] data1)
    {
        JScrollPane scroll1 = new JScrollPane(new Plot(data1));
        
        scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        getContentPane().add(scroll1,BorderLayout.CENTER);
        setSize(1000,400);
        setVisible(true);
        setResizable(false);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(this);
    }
    
    public SignalDisplay(double [] data1, double [] data2)
    {
        JScrollPane scroll1 = new JScrollPane(new Plot(data1));
        JScrollPane scroll2 = new JScrollPane(new Plot(data2));
        scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll2.getHorizontalScrollBar().setModel(scroll1.getHorizontalScrollBar().getModel());
        getContentPane().add(scroll1,BorderLayout.NORTH);
        getContentPane().add(scroll2,BorderLayout.CENTER);
        setSize(1000,700);
        setVisible(true);
        setResizable(false);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(this);
    }
    
    public SignalDisplay(double [] data1, double [] data2, double [] data3)
    {
        JScrollPane scroll1 = new JScrollPane(new Plot(data1));
        JScrollPane scroll2 = new JScrollPane(new Plot(data2));
        JScrollPane scroll3 = new JScrollPane(new Plot(data3));
        scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll2.getHorizontalScrollBar().setModel(scroll1.getHorizontalScrollBar().getModel());
        scroll3.getHorizontalScrollBar().setModel(scroll1.getHorizontalScrollBar().getModel());
        getContentPane().add(scroll1,BorderLayout.NORTH);
        getContentPane().add(scroll2,BorderLayout.CENTER);
        getContentPane().add(scroll3,BorderLayout.SOUTH);
        setSize(1000,1000);
        setVisible(true);
        setResizable(false);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(this);
    }

    @Override
    public boolean postProcessKeyEvent(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            this.dispose();
        }
        return false;
    }


}
