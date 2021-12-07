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
