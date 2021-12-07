package MXPSQL.JEXTEdit;

/*
MIT License

Copyright (c) 2021 MXPSQL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

///////////////////////////////////////////////////////////////////////////////

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
import java.awt.event.*;
import java.io.*;
public class RunAction extends JDialog implements ActionListener
{
    JButton run,cancel,browse;
    JPanel jp1,jp2;
    JTextField txt;

    public RunAction()
    {
        Container cp=getContentPane();

        jp1=new JPanel();
        jp2=new JPanel();

        JLabel lb=new JLabel("Select Program to Run");

        txt=new JTextField(30);

        browse=new JButton(". . .");
        browse.addActionListener(this);

        run=new JButton(" Run ");
        cancel=new JButton(" Cancel ");

        run.addActionListener(this);
        cancel.addActionListener(this);

        jp1.add(lb);
        jp1.add(txt,BorderLayout.CENTER);
        jp1.add(browse,BorderLayout.EAST);

        jp2.add(run);
        jp2.add(cancel);

        cp.add(jp1,BorderLayout.CENTER);
        cp.add(jp2,BorderLayout.SOUTH);

    }

    public void actionPerformed(ActionEvent evt)
    {
        Object src=evt.getSource();
        if(src==browse)
        {
            FileDialog fd=new FileDialog(new JFrame(),"Select File",FileDialog.LOAD);
            fd.show();
            if(fd.getFile()!=null)
            {
                String file=fd.getDirectory()+fd.getFile();
                txt.setText(file);
            }
        }
        else if(src==run)
        {
            if(txt.getText()!=null)
            {
                File file = new File(txt.getText());
                if(file.exists())
                {
                    if(file.toString().contains(".exe"))
                    {
                        try
                        {
                          Runtime.getRuntime().exec(file.toString());
                        }
                        catch(Exception e){ }
                    }
                    else
                    {
                        try
                        {
                            Desktop d = Desktop.getDesktop();
                            d.open(file);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
            dispose();
        }
        else if(src==cancel)
        {
            dispose();
        }
    }

}
