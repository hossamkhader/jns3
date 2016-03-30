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

package net.jns3.core;

import java.awt.Point;

public abstract class Node {
    
    private final String name;
    private final Point position;
    
    
    public Node(String name)
    {
        this.name = name;
        position = new Point();
    }
    
    public abstract NetDevice addNetDevice();
    
    public void forward(byte [] frame, NetDevice netDevice)
    {
        
    }
    
    public void setPosition(int x, int y)
    {
        position.x = x;
        position.y = y;
    }
    
    public Point getPosition()
    {
        return position;
    }
    
    public String getNodeName()
    {
        return name;
    }
}
