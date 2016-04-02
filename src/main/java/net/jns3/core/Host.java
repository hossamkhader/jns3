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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Host extends QemuNode {
    
    public Host(String name)
    {
        super(name);
    }
    
    @Override
    public void run()
    {        
        try
        {
            new File(System.getProperty("user.dir") + "\\tmp\\").mkdir();
            Path path = new File(System.getProperty("user.dir") + "\\tmp\\" + getNodeName() +".img").toPath();
            if(Files.exists(path))
                Files.delete(path);
            Files.copy(new File(System.getProperty("user.dir") + "\\images\\host.img").toPath(), 
                       new File(System.getProperty("user.dir") + "\\tmp\\" + getNodeName() +".img").toPath());
            
            setProcess(Runtime.getRuntime().exec(getQemuCommand() + getQemuOptions()));
            applyConfig();
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {

                getProcess().destroy();
            }
        });
    }
    
    private void applyConfig()
    {
        String config = System.getProperty("user.dir") + "\\config\\" + getNodeName() + ".cfg";
        File file = new File(config);
        AutomatedTelnetClient telnet = new AutomatedTelnetClient("127.0.0.1", getConsolePort());
        telnet.setPrompt("$");
        telnet.authenticate("tc");
        telnet.sendCommand("sudo hostname " + getNodeName());
        if(file.exists())
        {
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null)
                {
                    line = "sudo " + line;
                    telnet.sendCommand(line);
                }
            }
            catch(Exception e)
            {
                System.err.println(e.getMessage());
            }
        }
        telnet.disconnect();
        setConfigured(true);
    }
}
