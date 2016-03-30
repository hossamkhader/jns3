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

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Switch extends Node {
    
    private final List<UdpTunnelNetDevice> ports; 
    private final Map<ByteBuffer, UdpTunnelNetDevice> switchingTable;
    
    public Switch(String name)
    {
        super(name);
        ports = new ArrayList();
        switchingTable = new HashMap();
    }
    
    @Override
    public UdpTunnelNetDevice addNetDevice()
    {
        UdpTunnelNetDevice tmp = new UdpTunnelNetDevice(this, "eth" + ports.size());
        ports.add(tmp);
        tmp.start();
        return tmp;
    }
    
    private void flood(byte [] frame, UdpTunnelNetDevice inPort)
    {
        Iterator<UdpTunnelNetDevice> i = this.ports.iterator();
        while(i.hasNext())
        {
            UdpTunnelNetDevice outPort = i.next();
            if(outPort != inPort)
            {
                outPort.send(frame);
            }
        }
    }
    
    @Override
    public void forward(byte [] frame, NetDevice inPort)
    {
        byte [] _src = new byte[6];
        byte [] _dst = new byte[6];
        System.arraycopy(frame, 0, _dst, 0, 6);
        System.arraycopy(frame, 6, _src, 0, 6);
        ByteBuffer dst = ByteBuffer.wrap(_dst);
        ByteBuffer src = ByteBuffer.wrap(_src);
        
        UdpTunnelNetDevice outPort = switchingTable.get(dst);
        switchingTable.put(src, (UdpTunnelNetDevice) inPort);
        
        if(outPort == null || (_dst[0] & 1) == 1)
        {
            flood(frame, (UdpTunnelNetDevice) inPort);
        }
        else
        {
            outPort.send(frame);
        }
        System.out.println();
    }
}
