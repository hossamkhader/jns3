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

import javax.xml.bind.DatatypeConverter;

public abstract class NetDevice {
    
    private static int linkPortAllocator = 10000;
    private static  byte macAddressAllocator;
    
    private final int localPort;
    private final int remotePort;
    private final byte [] macAddress;
    private final Node node;
    private final String ifname;
    
    
    public NetDevice(Node node, String ifname)
    {
        macAddress = new byte[6];
        localPort = linkPortAllocator;
        remotePort = linkPortAllocator+1;
        linkPortAllocator+=2;
        macAddressAllocator++;
        macAddress[5] = macAddressAllocator;
        this.node = node;
        this.ifname = ifname;
    }
    
    public String getMacAddress()
    {
        String tmp = DatatypeConverter.printHexBinary(macAddress);
        tmp = tmp.toLowerCase();
        String mac; 
        mac = tmp.substring(0, 2);
        mac += ":";
        mac += tmp.substring(2, 4);
        mac += ":";
        mac += tmp.substring(4, 6);
        mac += ":";
        mac += tmp.substring(6, 8);
        mac += ":";
        mac += tmp.substring(8, 10);
        mac += ":";
        mac += tmp.substring(10,12);
        
        return mac;
    }

    public int getLocalPort() {
        return localPort;
    }

    public int getRemotePort() {
        return remotePort;
    }
    
    public Node getNode()
    {
        return node;
    }
    
    public String getIfName()
    {
        return ifname;
    }
}
