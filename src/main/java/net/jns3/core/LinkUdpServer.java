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

import java.awt.Color;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class LinkUdpServer extends Thread
{
    private final int localPort;
    private final int remotePort;
    private LinkUdpServer peer;
    private DatagramSocket socket;
    private Link link;
    private DigitalModem digitalModem;
    private AnalogModem analogModem;
    private final NetDevice netDevice;
    
    public LinkUdpServer(NetDevice netDevice)
    {
        this.netDevice = netDevice;
        this.localPort = netDevice.getRemotePort();
        this.remotePort = netDevice.getLocalPort();
    }
    
    public void setPeer(LinkUdpServer peer)
    {
        this.peer = peer;
    }
    
    private void send (byte [] data)
    {
        if(data != null)
        {
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, InetAddress.getLoopbackAddress(), remotePort);
            try
            {   
                socket.send(sendPacket);
            }
            catch (Exception e)
            {
                System.err.println(e.getMessage());
            }
        }
    }
    
    private void receive()
    {
        byte [] receiveData = new byte[9216];
        try
        {
            socket = new DatagramSocket(localPort, InetAddress.getLoopbackAddress());
            while (true)
            {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                byte [] tmp = new byte[receivePacket.getLength()];
                System.arraycopy(receivePacket.getData(), 0, tmp, 0, receivePacket.getLength());
                if(peer != null && digitalModem == null)
                {
                    if(link.getNoiseModel() != null)
                    {
                        tmp = link.getNoiseModel().apply(tmp);
                    }
                    peer.send(tmp);
                }
                if(digitalModem != null)
                {
                    double [] tmpSignal = digitalModem.modulate(digitalModem.serialize(tmp));
                    if(analogModem != null)
                    {
                        tmpSignal = analogModem.modulate(tmpSignal);
                        tmpSignal = analogModem.getPeer().demodulate(tmpSignal);
                    }
                    tmp = digitalModem.getPeer().deserialize(digitalModem.demodulate(tmpSignal));
                    if(link.getNoiseModel() != null)
                    {
                        tmp = link.getNoiseModel().apply(tmp);
                    }
                    peer.send(tmp);
                }
                if(link != null && tmp != null)
                {
                    Packet packet = new Packet(tmp, netDevice.getNode().getNodeName() + "->" + 
                                                    peer.netDevice.getNode().getNodeName());
                    link.add(packet);
                    if(("10.0.1.100".equals(packet.getSourceIp()) && 
                       "10.0.2.100".equals(packet.getDestinationIp())) ||
                       ("10.0.2.100".equals(packet.getSourceIp()) &&
                       "10.0.1.100".equals(packet.getDestinationIp())))
                    {
                        link.drawBubble(this, Color.red);
                    }
                    
                    else
                    if(("10.0.1.100".equals(packet.getSourceIp()) && 
                       "10.0.3.100".equals(packet.getDestinationIp())) ||
                       ("10.0.3.100".equals(packet.getSourceIp()) &&
                       "10.0.1.100".equals(packet.getDestinationIp())))
                    {
                        link.drawBubble(this, Color.blue);
                    }
                    else
                    {
                        link.drawBubble(this, Color.BLACK);
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }    
    }
    
    @Override
    public void run()
    {
        receive();
    }
    
    public void setLink(Link link)
    {
        this.link = link;
    }
    
    public void setDigitalModem(DigitalModem digitalModem)
    {
        this.digitalModem = digitalModem;
    }
    
    public void setAnalogModem(AnalogModem analogModem)
    {
        this.analogModem = analogModem;
    }
}
