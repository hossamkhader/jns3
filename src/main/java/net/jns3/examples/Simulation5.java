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

package net.jns3.examples;

import net.jns3.core.Host;
import net.jns3.core.Link;
import net.jns3.core.Switch;
import net.jns3.gui.Simulation;

public class Simulation5 extends Simulation {

    public Simulation5()
    {
        
    }
    
    @Override
    public void run()
    {
        Host H1 = new Host("H1");
        Host H2 = new Host("H2");
        H1.setPosition(200, 200);
        H2.setPosition(800, 200);
        sandBox.add(H1);
        sandBox.add(H2);
        
        Switch SW1 = new Switch("SW1");
        SW1.setPosition(400, 200);
        sandBox.add(SW1);
        
        Switch SW2 = new Switch("SW2");
        SW2.setPosition(600, 200);
        sandBox.add(SW2);
        
        Link link1 = new Link(SW1, H1);
        Link link2 = new Link(SW2, H2);
        Link linkSW1SW2 = new Link(SW1, SW2);
        
        sandBox.add(link1);
        sandBox.add(link2);
        sandBox.add(linkSW1SW2);
        link1.setSandBox(sandBox);
        link2.setSandBox(sandBox);
        linkSW1SW2.setSandBox(sandBox);
        
        H1.start();
        H2.start();
    } 
}
