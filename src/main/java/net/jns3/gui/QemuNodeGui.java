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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class QemuNodeGui extends NodeGui {
    
    private int consolePort;
    private static final String PUTTY_COMMAND = System.getProperty("user.dir") + "\\bin\\putty.exe";
    private Process process;
    
    public QemuNodeGui(String name)
    {
        super(name);
    }
    
    @Override
    public void initComponents() 
    {
        super.initComponents();
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2)
                {
                    startConsole(" telnet:127.0.0.1 " + consolePort + " -wt " + "\"" + getNodeName() + "\"" );
                }
            }
        });
    }
    
    public void startConsole(String options)
    {
        try
        {
            process = Runtime.getRuntime().exec(PUTTY_COMMAND + options);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {

                process.destroy();
            }
        });
    }

    public int getConsolePort()
    {
        return consolePort;
    }

    public void setConsolePort(int consolePort)
    {
        this.consolePort = consolePort;
    }   
}
