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

import net.jns3.core.AutomatedTelnetClient;
import net.jns3.core.Host;
import net.jns3.core.Link;
import net.jns3.core.QemuNode;
import net.jns3.core.Router;
import net.jns3.gui.Simulation;
import java.util.Random;

public class Simulation3 extends Simulation {

    public Simulation3 ()
    {
        
    }
    
    @Override
    public void run()
    {   
        Host H1 = new Host("H1");
        Host H2 = new Host("H2");
        H1.setPosition(100, 170);
        H2.setPosition(1100, 170);
        sandBox.add(H1);
        sandBox.add(H2);
        
        Router R1 = new Router("R1");
        Router R2 = new Router("R2");
        Router R3 = new Router("R3");
        Router R4 = new Router("R4");
        Router R5 = new Router("R5");
        Router R6 = new Router("R6");
        Router R7 = new Router("R7");
        Router R8 = new Router("R8");
        
        R1.setPosition(300, 170);
        R2.setPosition(400, 25);
        R3.setPosition(800, 25);
        R4.setPosition(900, 170);
        R5.setPosition(500, 170);
        R6.setPosition(700, 170);
        R7.setPosition(400, 325);
        R8.setPosition(800, 325);
        
        sandBox.add(R1);
        sandBox.add(R2);
        sandBox.add(R3);
        sandBox.add(R4);
        sandBox.add(R5);
        sandBox.add(R6);
        sandBox.add(R7);
        sandBox.add(R8);
        
        Link H1R1 = new Link(H1, R1);
        Link H2R4 = new Link(H2, R4);
        
        Link R1R2 = new Link(R1, R2);
        Link R1R7 = new Link(R1, R7);
        Link R2R3 = new Link(R2, R3);
        Link R2R5 = new Link(R2, R5);
        Link R3R4 = new Link(R3, R4);
        Link R3R6 = new Link(R3, R6);
        Link R4R8 = new Link(R4, R8);
        Link R5R6 = new Link(R5, R6);
        Link R5R7 = new Link(R5, R7);
        Link R6R8 = new Link(R6, R8);
        Link R7R8 = new Link(R7, R8);
        
        sandBox.add(H1R1);
        sandBox.add(H2R4);
        sandBox.add(R1R2);
        sandBox.add(R1R7);
        sandBox.add(R2R3);
        sandBox.add(R2R5);
        sandBox.add(R3R4);
        sandBox.add(R3R6);
        sandBox.add(R4R8);
        sandBox.add(R5R6);
        sandBox.add(R5R7);
        sandBox.add(R6R8);
        sandBox.add(R7R8);
        
        H1R1.setSandBox(sandBox);
        H2R4.setSandBox(sandBox);
        R1R2.setSandBox(sandBox);
        R1R7.setSandBox(sandBox);
        R2R3.setSandBox(sandBox);
        R2R5.setSandBox(sandBox);
        R3R4.setSandBox(sandBox);
        R3R6.setSandBox(sandBox);
        R4R8.setSandBox(sandBox);
        R5R6.setSandBox(sandBox);
        R5R7.setSandBox(sandBox);
        R6R8.setSandBox(sandBox);
        R7R8.setSandBox(sandBox);
        
        H1.start();
        H2.start();
        R1.start();
        R2.start();
        R3.start();
        R4.start();
        R5.start();
        R6.start();
        R7.start();
        R8.start();

        do
        {
            randomCost(R1R2);
            randomCost(R2R3);
            randomCost(R3R4);
            randomCost(R2R5);
            randomCost(R3R6);
            randomCost(R5R6);
            randomCost(R1R7);
            randomCost(R5R7);
            randomCost(R4R8);
            randomCost(R6R8);
            randomCost(R7R8);
            try
            {
                Thread.sleep(15000);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        while(true);
    }

    private void randomCost(Link link)
    {
        try
        {
            while(! ((QemuNode)link.getNetDevice1().getNode()).isConfigured() ||
                  ! ((QemuNode)link.getNetDevice2().getNode()).isConfigured())
            {
                Thread.sleep(1000);
            }
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        
        Random random = new Random();
        int r = random.nextInt(9) + 1;
        AutomatedTelnetClient telnet1 = new AutomatedTelnetClient("127.0.0.1", ((QemuNode)link.getNetDevice1().getNode()).getConsolePort());
        AutomatedTelnetClient telnet2 = new AutomatedTelnetClient("127.0.0.1", ((QemuNode)link.getNetDevice2().getNode()).getConsolePort());
        telnet1.setPrompt("#");
        telnet2.setPrompt("#");
        telnet1.sendCommand("configure terminal");
        telnet2.sendCommand("configure terminal");
        telnet1.sendCommand("interface " + link.getNetDevice1().getIfName());
        telnet2.sendCommand("interface " + link.getNetDevice2().getIfName());
        telnet1.sendCommand("ip ospf cost " + r);
        telnet2.sendCommand("ip ospf cost " + r);
        telnet1.sendCommand("end");
        telnet2.sendCommand("end");
        telnet1.disconnect();
        telnet2.disconnect();
        link.setLabel(String.valueOf(r));
    }
}
