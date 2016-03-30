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

import net.jns3.core.FmModem;
import net.jns3.core.Modem;
import net.jns3.core.PskModem;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ModemGUI extends JPanel {
    
    private JLabel iconLabel;
    private JLabel nameLabel;
    private String iconLocation;
    private final String name;
    private Modem modem;
    
    public ModemGUI(String name)
    {
        this.name = name;
        initComponents();
    }

    public ModemGUI()
    {
        name = "Modem";
        initComponents();
    }
    
    public final void initComponents()
    {
        iconLocation = "/icons/modem.png";
        iconLabel = new JLabel();
        nameLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(iconLocation));
        iconLabel.setIcon(imageIcon);
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        setLayout(new BorderLayout());
        nameLabel.setText(name);
        add(nameLabel, BorderLayout.NORTH);
        add(iconLabel, BorderLayout.CENTER);
        setBounds(0, 0, 0, 0);
        
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2)
                {
                    showSignal();
                }
            }
        });
    }
    
    public void showSignal()
    {
        if(modem instanceof PskModem)
        {
            double [] digitalSignal = ((PskModem)modem).getDigitalSignal();
            double [] analogSignal = ((PskModem)modem).getAnalogSignal();
            if(digitalSignal != null  && analogSignal != null)
            {
                SignalDisplay signalDisplay = new SignalDisplay(digitalSignal, analogSignal);
                signalDisplay.setTitle(PskModem.DigitalModulation.PSK.name());
            }
        }
        if(modem instanceof FmModem)
        {
            double [] inputSignal = ((FmModem)modem).getInputSignal();
            double [] outputSignal = ((FmModem)modem).getOutputSignal();
            if(inputSignal != null && outputSignal != null)
            {
                SignalDisplay signalDisplay = new SignalDisplay(inputSignal, outputSignal);
                signalDisplay.setTitle(FmModem.AnalogModulation.FM.name());
            }
        }
        
    }
    
    public void setPosition(Point position)
    {
        setBounds(position.x , position.y, 75, 75);
    }
    
    public void setModem(Modem modem)
    {
        this.modem = modem;
    }
}
