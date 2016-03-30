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

import java.awt.BorderLayout;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class NodeGui extends JPanel {
    
    private JLabel iconLabel;
    private JLabel nameLabel;
    private String iconLocation;
    private final String name;
    
    
    public NodeGui(String name)
    {
        this.name = name;
    }
    
    public void initComponents() {
        
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
    }
    
    public final void setIconLocation(String iconLocation)
    {
        this.iconLocation = iconLocation;
    }
    
    public String getNodeName()
    {
        return name;
    }
    
    public void setPosition(Point position)
    {
        setBounds(position.x , position.y, 75, 75);
    }
}
