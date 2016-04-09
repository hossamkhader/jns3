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

public class PacketDropModel extends NoiseModel {
    
    private double dropRatio;
    
    public PacketDropModel()
    {
        
    }

    public double getDropRatio()
    {
        return dropRatio;
    }


    public void setDropRatio(double dropRatio)
    {
        this.dropRatio = dropRatio;
    }
    

    @Override
    double [] apply(double[] data)
    {
        double p = Math.random();
        if(p < dropRatio)
        {
            return null;
        }
        return data;
    }

    @Override
    byte [] apply(byte[] data)
    {
        double p = Math.random();
        if(p < dropRatio)
        {
            return null;
        }
        return data;
    }
    
    
    
}
