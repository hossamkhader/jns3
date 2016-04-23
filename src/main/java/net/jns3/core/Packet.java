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
import javax.xml.bind.DatatypeConverter;

public class Packet {
    
    private final byte [] data;
    private final String link;
    
    public Packet(byte [] data, String link)
    {
        this.data = data;
        this.link = link;
    }
    
    @Override
    public String toString()
    {
        String tmp = link + ": ";
        tmp += "dst_mac=" + getDestinationMac();
        tmp += ", src_mac=" + getSourceMac();
        tmp += ", src_ip=" + getSourceIp();
        tmp += ", dst_ip=" + getDestinationIp();
        if(getIpProtocol() == 1)
            tmp += ", protocol=ICMP";
        if(getIpProtocol() == 6)
            tmp += ", protocol=TCP";
        if(getIpProtocol() == 17)
            tmp += ", protocol=UDP";
        if(getIpProtocol() == 6 || getIpProtocol() == 17)
        {
            tmp += ", src_port=" + getSourcePort();
            tmp += ", dst_port=" + getDestinationPort() ;
        }
        if(getIpProtocol() == 1)
        {
            tmp += ", icmp_type=" + getIcmpType();
        }
        if(getIpProtocol() == 6)
        {
            tmp += ", seq_num=" + getTcpSeqNumber();
            tmp += ", ack_num=" + getTcpAckNumber();
            tmp += ", tcp_flags=" + getTcpFlags();
            tmp += ", window_size=" + getTcpWindowSize();
        }
        return tmp;
    }
    
    public String getSourceMac()
    {
        byte [] tmp = new byte [6];
        System.arraycopy(data, 6, tmp, 0, 6);
        return DatatypeConverter.printHexBinary(tmp);
    }
    
    public String getDestinationMac()
    {
        byte [] tmp = new byte [6];
        System.arraycopy(data, 0, tmp, 0, 6);
        return DatatypeConverter.printHexBinary(tmp);
    }
    
    public String getSourceIp()
    {
        byte [] tmp = new byte [4];
        System.arraycopy(data, 26, tmp, 0, 4);
        return ((int) tmp[0] & 0xFF) + "." + ((int) tmp[1] & 0xFF) + "." +
               ((int) tmp[2] & 0xFF) + "." + ((int) tmp[3] & 0xFF); 
    }
    
    public String getDestinationIp()
    {
        byte [] tmp = new byte [4];
        System.arraycopy(data, 30, tmp, 0, 4);
        return ((int) tmp[0] & 0xFF) + "." + ((int) tmp[1] & 0xFF) + "." +
               ((int) tmp[2] & 0xFF) + "." + ((int) tmp[3] & 0xFF); 
    }
    
    public int getIpProtocol()
    {
        return ((int)(data[23]) & 0xFF);
    }
    
    public int getSourcePort()
    {
        if(getIpProtocol() == 6 || getIpProtocol() == 17)
        {
            byte [] tmp = new byte [2];
            System.arraycopy(data, 34, tmp, 0, 2);
            ByteBuffer buffer = ByteBuffer.wrap(tmp);
            return (buffer.getShort() & 0xFFFF);
        }
        return 0;
    }
    
    public int getDestinationPort()
    {
        if(getIpProtocol() == 6 || getIpProtocol() == 17)
        {
            byte [] tmp = new byte [2];
            System.arraycopy(data, 36, tmp, 0, 2);
            ByteBuffer buffer = ByteBuffer.wrap(tmp);
            return (buffer.getShort() & 0xFFFF);
        }
        return 0;
    }
    
    public int getIcmpType()
    {
        return ((int)data[34] & 0xFF);
    }
    
    public long getTcpSeqNumber()
    {
        byte [] tmp = new byte [4];
        System.arraycopy(data, 38, tmp, 0, 4);
        ByteBuffer buffer = ByteBuffer.wrap(tmp);
        return (buffer.getInt() & 0xFFFFFFFFl);
    }
    
    public long getTcpAckNumber()
    {
        byte [] tmp = new byte [4];
        System.arraycopy(data, 42, tmp, 0, 4);
        ByteBuffer buffer = ByteBuffer.wrap(tmp);
        return (buffer.getInt() & 0xFFFFFFFFl);
    }
    
    public int getTcpFIN()
    {
        byte tmp = data[47];
        return tmp & 0x01 ;
    }
    
    public int getTcpSYN()
    {
        byte tmp = data[47];
        return tmp & (0x01 << 1) ;
    }
    
    public int getTcpRST()
    {
        byte tmp = data[47];
        return tmp & (0x01 << 2) ;
    }
    
    public int getTcpPSH()
    {
        byte tmp = data[47];
        return tmp & (0x01 << 3) ;
    }
    
    public int getTcpACK()
    {
        byte tmp = data[47];
        return tmp & (0x01 << 4) ;
    }
    
    public int getTcpURG()
    {
        byte tmp = data[47];
        return tmp & (0x01 << 5) ;
    }
    
    public int getTcpECE()
    {
        byte tmp = data[47];
        return tmp & (0x01 << 6) ;
    }
    
    public int getTcpCWR()
    {
        byte tmp = data[47];
        return tmp & (0x01 << 7) ;
    }
    
    public int getTcpFlags()
    {
        return data[47];
    }
    
    public int getTcpWindowSize()
    {
        byte [] tmp = new byte [2];
        System.arraycopy(data, 48, tmp, 0, 2);
        ByteBuffer buffer = ByteBuffer.wrap(tmp);
        return (buffer.getShort() & 0xFFFF);
    }
}
