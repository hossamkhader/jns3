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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class QemuNode extends Node implements Runnable{
    
    private final String QEMU_COMMAND;
    
    private final List<QemuNetDevice> netDevices;
    private final int consolePort;
    private final int monitorPort;
    
    private static int consolePortAllocator = 2001;
    private static int monitorPortAllocator = 64179;
    
    
    private Process process;
    private Thread thread;
    private boolean configured;
    
    public QemuNode(String name)
    {
        super(name);
        this.QEMU_COMMAND = System.getProperty("user.dir") + "\\qemu-2.4.0\\qemu-system-x86_64w.exe";
        netDevices = new ArrayList();
        consolePort = consolePortAllocator;
        monitorPort = monitorPortAllocator;
        consolePortAllocator++;
        monitorPortAllocator++;
        configured = false;
    }
    
    public void start()
    {
        thread = new Thread(this);
        thread.start();
    }
    
    public String getQemuOptions()
    {
        int i= 0;
        String options = " -name " + getNodeName() + " -m 128M -smp cpus=1";
        options += " -hda " + System.getProperty("user.dir") + "\\tmp\\" + getNodeName() + ".img";
        options += " -serial telnet:127.0.0.1:" + getConsolePort() + ",server,nowait -monitor tcp:127.0.0.1:" + monitorPort + ",server,nowait";
        options += " -net none";
        Iterator<QemuNetDevice> iterator = netDevices.iterator();
        while(iterator.hasNext())
        {
            QemuNetDevice tmp = iterator.next();
            options += " -device e1000,mac=";
            options += tmp.getMacAddress();
            options += ",netdev=gns3-" + i;
            options += " -netdev socket,id=gns3-" + i;
            options += ",udp=127.0.0.1:" + tmp.getRemotePort() ;
            options += ",localaddr=127.0.0.1:" + tmp.getLocalPort();
            i++;
        }
        options += " -nographic";
        
        return options;
    }
    
    public QemuNetDevice addNetDevice()
    {
        QemuNetDevice tmp = new QemuNetDevice(this, "eth" + netDevices.size());
        netDevices.add(tmp);
        return tmp;
    }

    public int getConsolePort() {
        return consolePort;
    }
    
    public boolean isConfigured()
    {
        return configured;
    }
    
    public void setConfigured(boolean configured)
    {
        this.configured = configured;
    }
    
    public void setProcess(Process process)
    {
        this.process = process;
    }
    
    public Process getProcess()
    {
        return process;
    }
    
    public String getQemuCommand()
    {
        return this.QEMU_COMMAND;
    }
}
