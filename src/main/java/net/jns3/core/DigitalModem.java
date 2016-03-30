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

public abstract class DigitalModem extends Modem {
    
    private  double [] digitalSignal;
    private  double [] analogSignal;
    
    private DigitalModem peer;
    
    public enum DigitalModulation {ASK, FSK, PSK};
    
    public abstract double [] modulate(double [] digitalData);
    
    public abstract double [] demodulate(double [] analogData);
    
    public abstract double [] serialize(byte [] data);
    
    public abstract byte [] deserialize(double [] signal);

    public DigitalModem()
    {
        
    }
    
    public void setPeer(DigitalModem peer)
    {
        this.peer = peer;
    }
    
    public DigitalModem getPeer()
    {
        return peer;
    }
    
    public double[] getDigitalSignal()
    {
        return digitalSignal;
    }

    public double[] getAnalogSignal()
    {
        return analogSignal;
    }
    
    public void setDigitalSignal(double[] digitalSignal)
    {
        this.digitalSignal = digitalSignal;
    }

    public void setAnalogSignal(double[] analogSignal) {
        this.analogSignal = analogSignal;
    }
    
    
}
