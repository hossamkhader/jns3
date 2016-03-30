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

import static java.lang.Math.sin;

public class FmModem extends AnalogModem{
    
    public FmModem()
    {
        
    }

    @Override
    public double[] modulate(double[] inputSignal)
    {
        double [] tempOutput = new double [inputSignal.length];
        for(int t = 0 ; t < inputSignal.length ; t++)
        {
            tempOutput[t] = sin((20+5*inputSignal[t]) * Math.toRadians(t));
        }
        this.setInputSignal(inputSignal);
        this.setOutputSignal(tempOutput);
        return tempOutput; 
    }

    @Override
    public double[] demodulate(double[] inputSignal)
    {
        double [] tmp = new double [inputSignal.length];
        for(int t = 0 ; t < inputSignal.length ; t++)
        {
            tmp[t] = sin(10 * Math.toRadians(t));
            if(inputSignal[t] != sin((20+5*tmp[t]) * Math.toRadians(t)))
            {
                tmp[t] *= -1;
            }
        }
        return tmp;   
    }
}
