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

import net.jns3.core.AnalogModem.AnalogModulation;
import net.jns3.core.DigitalModem.DigitalModulation;
import net.jns3.gui.SandBox;
import java.awt.Color;
import java.awt.geom.Line2D;

public class Link {
    
    private final LinkUdpServer server1;
    private final LinkUdpServer server2;
    private final NetDevice netDevice1;
    private final NetDevice netDevice2;
    private DigitalModem digitalModem1;
    private DigitalModem digitalModem2;
    private AnalogModem analogModem1;
    private AnalogModem analogModem2;
    private final Line2D.Double line;
    private SandBox sandBox;
    private String label;
    
    public Link(Node node1, Node node2)
    {
        netDevice1 = node1.addNetDevice();
        netDevice2 = node2.addNetDevice();
        line = new Line2D.Double(node1.getPosition(),node2.getPosition());
        server1 = new LinkUdpServer(netDevice1);
        server2 = new LinkUdpServer(netDevice2);
        server1.setPeer(server2);
        server2.setPeer(server1);
        server1.setLink(this);
        server2.setLink(this);
        server1.start();
        server2.start();
    }
    
    public void add(Packet packet)
    {
        sandBox.add(packet);
    }
    
    public Line2D.Double getLine()
    {
        return line;
    }
    
    public void drawBubble(LinkUdpServer caller, Color color)
    {
        if(caller == server1)
        {
            sandBox.drawBubble(new Line2D.Double(line.getP1(), line.getP2()), color);
        }
        if(caller == server2)
        {
            sandBox.drawBubble(new Line2D.Double(line.getP2(), line.getP1()), color);
        }
    }
    
    public void setSandBox(SandBox sandBox)
    {
        this.sandBox = sandBox;
    }
    
    public void setDigitalModulation(DigitalModulation modulation)
    {
        if(modulation == DigitalModulation.ASK)
        {
            //digitalModem1 = new AskModem();
            //digitalModem2 = new AskModem();
        }
        if(modulation == DigitalModulation.FSK)
        {
            //digitalModem1 = new FskModem();
            //digitalModem2 = new FskModem();
        }
        if(modulation == DigitalModulation.PSK)
        {
            digitalModem1 = new PskModem();
            digitalModem2 = new PskModem();
        }
        digitalModem1.setPeer(digitalModem2);
        digitalModem2.setPeer(digitalModem1);
        server1.setDigitalModem(digitalModem1);
        server2.setDigitalModem(digitalModem2);
    }
    
    public void setAnalogModulation(AnalogModulation modulation)
    {
        if(modulation == AnalogModulation.AM)
        {
            //analogModem1 = new AmModem();
            //analogModem1 = new AmModem();
        }
        if(modulation == AnalogModulation.FM)
        {
            analogModem1 = new FmModem();
            analogModem2 = new FmModem();
        }
        if(modulation == AnalogModulation.PM)
        {
            //analogModem1 = new PmModem();
            //analogModem1 = new PmModem();
        }
        analogModem1.setPeer(analogModem2);
        analogModem2.setPeer(analogModem1);
        server1.setAnalogModem(analogModem1);
        server2.setAnalogModem(analogModem2);
    }
    
    public DigitalModulation getDigitalModulation()
    {
        if(digitalModem1 == null)
            return null;
        //if(digitalModem1 instanceof AskModem)
        //    return Modulation.ASK;
        //if(digitalModem1 instanceof FskModem)
        //    return Modulation.FSK;
        if(digitalModem1 instanceof PskModem)
            return DigitalModulation.PSK;
        return null;
    }
    
    public DigitalModem getDigitalModem1()
    {
        return digitalModem1;
    }
    
    public DigitalModem getDigitalModem2()
    {
        return digitalModem2;
    }
    
    
    public AnalogModulation getAnalogModulation()
    {
        if(analogModem1 == null)
            return null;
        //if(digitalModem1 instanceof AmModem)
        //    return Modulation.AM;
        //if(digitalModem1 instanceof PmModem)
        //    return Modulation.PM;
        if(analogModem1 instanceof FmModem)
            return AnalogModulation.FM;
        return null;
    }
    
    public AnalogModem getAnalogModem1()
    {
        return analogModem1;
    }
    
    public AnalogModem getAnalogModem2()
    {
        return analogModem2;
    }
    
    public String getLabel()
    {
        return label;
    }
    
    public void setLabel(String label)
    {
        this.label = label;
    }
    
    public NetDevice getNetDevice1()
    {
        return netDevice1;
    }
    
    public NetDevice getNetDevice2()
    {
        return netDevice2;
    }
}
