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

import net.jns3.core.Host;
import net.jns3.core.Link;
import net.jns3.core.Node;
import net.jns3.core.Packet;
import net.jns3.core.Router;
import net.jns3.core.Switch;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Line2D;
import javax.swing.DefaultListModel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class SandBox extends JLayeredPane {
    
    private LinksLayer links;
    private DefaultListModel packets;
    private JList<String> packetsList;
    private JScrollPane scroll;
    
    public SandBox()
    {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(null);
        links = new LinksLayer();
        this.add(links, new Integer(5));
        packets = new DefaultListModel();
        packetsList = new JList<>(packets);
        scroll = new JScrollPane(packetsList);
        scroll.setBounds(0, 400, 1195, 360);
        add(scroll, new Integer(6));
    }
    
    public void add(Packet packet)
    {
        SwingUtilities.invokeLater(() -> {
                if(packets.size() > 100)
                    packets.removeRange(50, packets.size() -1);
                packets.add(0, packet);
            });
    }
    
    public void add(Node node)
    {
        NodeGui tmp = null;
        if(node instanceof Host)
        {
            tmp = new HostGui(node.getNodeName());
            ((QemuNodeGui)tmp).setConsolePort(((Host)node).getConsolePort());
        }
        if(node instanceof Router)
        {
            tmp = new RouterGui(node.getNodeName());
            ((QemuNodeGui)tmp).setConsolePort(((Router)node).getConsolePort());
        }
        if(node instanceof Switch)
        {
            tmp = new SwitchGui(node.getNodeName());
        }
        tmp.setPosition(node.getPosition());
        add(tmp, new Integer(10));
    }
    
    public void add(Link link)
    {
        double x = (link.getLine().x1 + link.getLine().x2)/2;
        double y = (link.getLine().y1 + link.getLine().y2)/2;
        if(link.getDigitalModulation() != null)
        {
            double _x;
            double _y;
            ModemGUI modem1 = new ModemGUI(link.getDigitalModulation().name());
            ModemGUI modem2 = new ModemGUI(link.getDigitalModulation().name());
            modem1.setModem(link.getDigitalModem1());
            modem2.setModem(link.getDigitalModem2());
            _x = (x + link.getLine().x1)/2;
            _y = (y + link.getLine().y1)/2;
            modem1.setPosition(new Point((int)(_x), (int)(_y)));
            _x = (x + link.getLine().x2)/2;
            _y = (y + link.getLine().y2)/2;
            modem2.setPosition(new Point((int)(_x), (int)(_y)));
            add(modem1, new Integer(10));
            add(modem2, new Integer(10));
            if(link.getAnalogModulation() != null)
            {
                ModemGUI modem3 = new ModemGUI(link.getAnalogModulation().name());
                ModemGUI modem4 = new ModemGUI(link.getAnalogModulation().name());
                modem3.setModem(link.getAnalogModem1());
                modem4.setModem(link.getAnalogModem2());
                _x = (modem1.getX() + x )/2;
                _y = (modem1.getY() + y )/2;
                modem3.setPosition(new Point((int)(_x), (int)(_y)));
                _x = (modem2.getX() + x )/2;
                _y = (modem2.getY() + y )/2;
                modem4.setPosition(new Point((int)(_x), (int)(_y)));
                add(modem3, new Integer(10));
                add(modem4, new Integer(10));
            }
        }
        links.addLink(link);
    }
    
    public void drawBubble(Line2D.Double line, Color color)
    {
        links.drawBubble(line, color);
    }
}
