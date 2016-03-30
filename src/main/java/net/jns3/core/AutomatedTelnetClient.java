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

import org.apache.commons.net.telnet.TelnetClient;
import java.io.InputStream;
import java.io.PrintStream;

public class AutomatedTelnetClient {
    private TelnetClient telnet = new TelnetClient();
    private InputStream in;
    private PrintStream out;
    private String prompt;
    
    public AutomatedTelnetClient(String server, int port)
    {
        try
        {
            telnet.connect(server, port);
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
    	}
        catch (Exception e)
        {
            System.err.println(e.getMessage());
    	}
    }
    
    public void connect(String user)
    {
        out.println();
        if(readUntil("login:").endsWith("login:"))
            write(user);
        readUntil(prompt);
    }

    public String readUntil(String pattern)
    {
    	try
        {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuilder sb = new StringBuilder();
            char ch = (char) in.read();
            while (true)
            {
    		sb.append(ch);
                if (ch == lastChar || ch == prompt.charAt(0))
                {
                    if (sb.toString().endsWith(pattern) || sb.toString().endsWith(prompt))
                    {
                        return sb.toString();
                    }
    		}
                ch = (char) in.read();
            }
    	}
        catch (Exception e)
        {
            System.err.println(e.getMessage());
    	}
    	return null;
    }

    public void write(String value)
    {
    	try
        {
            out.println(value);
            out.flush();
    	}
        catch (Exception e)
        {
            System.err.println(e.getMessage());
    	}
    }

    public String sendCommand(String command)
    {
    	try
        {
            out.println();
            write(command);
            return readUntil(prompt);
    	}
        catch (Exception e)
        {
            System.err.println(e.getMessage());
    	}
    	return null;
    }

    public void disconnect()
    {
    	try
        {
            telnet.disconnect();
    	}
        catch (Exception e)
        {
            System.err.println(e.getMessage());
    	}
    }
    
    public void setPrompt(String prompt)
    {
        this.prompt = prompt;
    }
}
