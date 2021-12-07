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

public class ForeBackColorAction
{

   static class ForeColor_Action extends JDialog implements ActionListener
    {
        JColorChooser chooser = new JColorChooser(Color.WHITE);
        JPanel jp;
        JButton ok, cancel;
        // JTextPane textPane;
        JTextArea textPane;

        /* public ForeColor_Action(JTextPane tx)
        {
            textPane = tx;

            Container cp = getContentPane();

            jp = new JPanel();

            ok = new JButton("  OK  ");
            cancel = new JButton(" Cancel ");

            ok.addActionListener(this);
            cancel.addActionListener(this);

            jp.add(ok);
            jp.add(cancel);

            cp.add(chooser, BorderLayout.CENTER);
            cp.add(jp, BorderLayout.SOUTH);
        } */
        
        public ForeColor_Action(JTextArea tx)
        {
            textPane = tx;

            Container cp = getContentPane();

            jp = new JPanel();

            ok = new JButton("  OK  ");
            cancel = new JButton(" Cancel ");

            ok.addActionListener(this);
            cancel.addActionListener(this);

            jp.add(ok);
            jp.add(cancel);

            cp.add(chooser, BorderLayout.CENTER);
            cp.add(jp, BorderLayout.SOUTH);
        }

        public void actionPerformed(ActionEvent evt)
        {
            Object src = evt.getSource();
            if (src == ok)
            {
                Color color = chooser.getColor();
                if (color != null)
                {
                    textPane.setForeground(color);
                }
                this.dispose();
            }
            else if (src == cancel)
            {
                this.dispose();
            }
        }
    }


    static class BackColor_Action extends JDialog implements ActionListener
    {
        JColorChooser chooser = new JColorChooser(Color.WHITE);
        JPanel jp;
        JButton ok, cancel;
        // JTextPane textPane;
        JTextArea textArea;

        /* public BackColor_Action(JTextPane textPane2)
        {
            textPane = textPane2;

            Container cp = getContentPane();

            jp = new JPanel();

            ok = new JButton("  OK  ");
            cancel = new JButton(" Cancel ");

            ok.addActionListener(this);
            cancel.addActionListener(this);

            jp.add(ok);
            jp.add(cancel);

            cp.add(chooser, BorderLayout.CENTER);
            cp.add(jp, BorderLayout.SOUTH);
        } */
        
        public BackColor_Action(JTextArea textPane2)
        {
            textArea = textPane2;

            Container cp = getContentPane();

            jp = new JPanel();

            ok = new JButton("  OK  ");
            cancel = new JButton(" Cancel ");

            ok.addActionListener(this);
            cancel.addActionListener(this);

            jp.add(ok);
            jp.add(cancel);

            cp.add(chooser, BorderLayout.CENTER);
            cp.add(jp, BorderLayout.SOUTH);
        }

        public void actionPerformed(ActionEvent evt)
        {
            Object src = evt.getSource();
            if (src == ok)
            {
                Color color = chooser.getColor();
                if (color != null)
                {
                    textArea.setBackground(color);
                }
                this.dispose();
            }
            else if (src == cancel)
            {
                this.dispose();
            }
        }
    }

}
