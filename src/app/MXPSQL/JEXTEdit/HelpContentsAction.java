package MXPSQL.JEXTEdit;

/*
JEXTEdit, text editor with web browser
Copyright (C) 2021 MXPSQL

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/


import java.awt.*;


import javax.swing.*;
public class HelpContentsAction extends JFrame
{
    Image image;
  public HelpContentsAction()
  {
      Container cp=getContentPane();
      JLabel lb=new JLabel("JEXTEdit");
      lb.setFont(new Font("SansSerif",Font.PLAIN,22));

      JPanel jp=new JPanel();
      jp.add(lb, BorderLayout.PAGE_START);
      
      JLabel lb2 = new JLabel("Very Nice MIT and 'LGPL 2.1 or any later version' Licensed editor.");
      jp.add(lb2, BorderLayout.CENTER);

      cp.add(jp,BorderLayout.NORTH);

  }
}
