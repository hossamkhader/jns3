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
import net.jns3.core.PacketDropModel;
import net.jns3.gui.Simulation;

public class Simulation6 extends Simulation {

    public Simulation6()
    {
        
    }
    
    @Override
    public void run()
    {
        Host H1 = new Host("H1");
        Host H2 = new Host("H2");
        H1.setPosition(100, 200);
        H2.setPosition(500, 200);
        sandBox.add(H1);
        sandBox.add(H2);
        
        PacketDropModel drop = new PacketDropModel();
        drop.setDropRatio(0.0);
        
        Link link12 = new Link(H1, H2);
        sandBox.add(link12);
        link12.setSandBox(sandBox);
        link12.setNoiseModel(drop);
        
        H1.start();
        H2.start();
    }
    
    public static void main(String[] args) 
    {
        Simulation simulation = new Simulation6();
        simulation.run();
    }
}
