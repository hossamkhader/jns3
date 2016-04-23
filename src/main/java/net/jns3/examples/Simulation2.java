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

import net.jns3.core.AnalogModem.AnalogModulation;
import net.jns3.core.DigitalModem.DigitalModulation;
import net.jns3.core.Host;
import net.jns3.core.Link;
import net.jns3.core.Router;
import net.jns3.gui.Simulation;

public class Simulation2 extends Simulation {
    
    public Simulation2 ()
    {
        
    }
    
    @Override
    public void run()
    {
        Host H1 = new Host("H1");
        Host H2 = new Host("H2");
        Host H3 = new Host("H3");
        H1.setPosition(100, 200);
        H2.setPosition(1100, 100);
        H3.setPosition(1100, 300);
        sandBox.add(H1);
        sandBox.add(H2);
        sandBox.add(H3);
  
        Router R1 = new Router("R1");
        Router R2 = new Router("R2");
        Router R3 = new Router("R3");
        R1.setPosition(300, 200);
        R2.setPosition(900, 100);
        R3.setPosition(900, 300);
        sandBox.add(R1);
        sandBox.add(R2);
        sandBox.add(R3);
        
        Link linkH1R1 = new Link(H1, R1);
        linkH1R1.setSandBox(sandBox);
        sandBox.add(linkH1R1);
        
        
        Link linkH2R2 = new Link(H2, R2);
        linkH2R2.setSandBox(sandBox);
        sandBox.add(linkH2R2);
        
        Link linkH3R3 = new Link(H3, R3);
        linkH3R3.setSandBox(sandBox);
        sandBox.add(linkH3R3);
        
        Link linkR1R2 = new Link(R1, R2);
        linkR1R2.setSandBox(sandBox);
        linkR1R2.setDigitalModulation(DigitalModulation.PSK);
        linkR1R2.setAnalogModulation(AnalogModulation.FM);
        sandBox.add(linkR1R2);
        
        Link linkR1R3 = new Link(R1, R3);
        linkR1R3.setSandBox(sandBox);
        sandBox.add(linkR1R3);
        
        H1.start();
        H2.start();
        H3.start();
        R1.start();
        R2.start();
        R3.start();
    }
    
    public static void main(String[] args) 
    {
        Simulation simulation = new Simulation2();
        simulation.run();
    }
}
