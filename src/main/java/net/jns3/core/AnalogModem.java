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

public abstract class AnalogModem extends Modem {
    
    private  double [] inputSignal;
    private  double [] outputSignal;
    
    private AnalogModem peer;
    
    public enum AnalogModulation {AM, FM, PM};
    
    public abstract double [] modulate(double [] inputSignal);
    
    public abstract double [] demodulate(double [] inputSignal);
    
    public AnalogModem()
    {
        
    }
    
    public void setPeer(AnalogModem peer)
    {
        this.peer = peer;
    }
    
    public AnalogModem getPeer()
    {
        return peer;
    }
    
    public double[] getInputSignal()
    {
        return inputSignal;
    }

    public double[] getOutputSignal()
    {
        return outputSignal;
    }
    
    public void setInputSignal(double[] inputSignal)
    {
        this.inputSignal = inputSignal;
    }

    public void setOutputSignal(double[] outputSignal) {
        this.outputSignal = outputSignal;
    }
    
    
}
