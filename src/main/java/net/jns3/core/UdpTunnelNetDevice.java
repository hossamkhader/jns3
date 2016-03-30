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

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpTunnelNetDevice extends NetDevice implements Runnable
{
    private DatagramSocket socket;
    private final Thread thread;
    
    public UdpTunnelNetDevice(Node node, String ifname)
    {
        super(node, ifname);
        thread = new Thread(this);
    }
    
    public void send (byte [] data)
    {
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, InetAddress.getLoopbackAddress(), getRemotePort());
        try
        {   
            socket.send(sendPacket);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    private void receive()
    {
        byte [] receiveData = new byte[9216];
        try
        {
            socket = new DatagramSocket(getLocalPort(), InetAddress.getLoopbackAddress());
            while (true)
            {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                byte [] tmp = new byte[receivePacket.getLength()];
                System.arraycopy(receivePacket.getData(), 0, tmp, 0, receivePacket.getLength());
                getNode().forward(tmp, this);
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
    
    public void start()
    {
        thread.start();
    }
}
