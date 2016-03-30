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


public class PskModem extends DigitalModem {
    
    public PskModem()
    {
        
    }

    @Override
    public double [] modulate(double [] digitalSignal)
    {
        double [] tempAnalog = new double [digitalSignal.length];
        for(int t = 0 ; t < digitalSignal.length ; t++)
        {
            tempAnalog[t] = sin(10 * Math.toRadians(t));
            if(digitalSignal[t] == 0)
                tempAnalog[t] *= -1;
        }
        this.setAnalogSignal(tempAnalog);
        return tempAnalog;
    }

    @Override
    public double [] demodulate(double[] analogSignal)
    {
        double [] tmpDigital = new double [analogSignal.length];
        for(int t = 0 ; t < analogSignal.length ; t++)
        {
            tmpDigital[t] = sin(10 * Math.toRadians(t));
            if(tmpDigital[t] == analogSignal[t])
                tmpDigital[t] = 1;
            else
                tmpDigital[t] = 0;
        }
        return tmpDigital;
    }

    @Override
    public double[] serialize(byte[] data)
    {
        double [] tempDigital = new double[data.length * 288];
        for (int i = 0 ; i < data.length ; i++)
        {
            for (int j = 0 ; j < 8 ; j++)
            {
                for (int k = 0 ; k < 36 ; k++)
                {
                    tempDigital[i*288+j*36+k]= (double)((data[i] >> j) & 1);
                }
            }
        }
        setDigitalSignal(tempDigital);
        return tempDigital;
    }

    @Override
    public byte[] deserialize(double[] signal)
    {
        byte [] data = new byte[signal.length/288];
        for (int i = 0 ; i < signal.length/288 ; i++)
        {
            for(int j = 0 ; j < 8 ; j ++)
            {
                if(signal[j*36 + i*288 + 17] == 1 && signal[j*36 + i*288 + 18] == 1 )
                {
                    data[i] |= 1 << j;
                    
                }
                if(signal[j*36 + i*288 + 17] == 0 && signal[j*36 + i*288 + 18] == 0)
                {
                    data[i] &= ~(1 << j);
                }
            }
        }
        return data;
    }
}
