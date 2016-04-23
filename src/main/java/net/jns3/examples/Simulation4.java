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

public class Simulation4 extends Simulation {

    public Simulation4()
    {
        
    }
    
    @Override
    public void run()
    {
        Host H1 = new Host("H1");
        Host H2 = new Host("H2");
        Host H3 = new Host("H3");
        H1.setPosition(100, 200);
        H2.setPosition(500, 200);
        H3.setPosition(300, 50);
        sandBox.add(H1);
        sandBox.add(H2);
        sandBox.add(H3);
        
        Switch SW1 = new Switch("SW1");
        SW1.setPosition(300, 200);
        sandBox.add(SW1);
        
        Link link1 = new Link(SW1, H1);
        Link link2 = new Link(SW1, H2);
        Link link3 = new Link(SW1, H3);
        
        sandBox.add(link1);
        sandBox.add(link2);
        sandBox.add(link3);
        link1.setSandBox(sandBox);
        link2.setSandBox(sandBox);
        link3.setSandBox(sandBox);
        
        H1.start();
        H2.start();
        H3.start();
    }
    
    public static void main(String[] args) 
    {
        Simulation simulation = new Simulation4();
        simulation.run();
    }
}
