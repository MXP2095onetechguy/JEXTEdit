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
import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import net.sourceforge.argparse4j.inf.Namespace;

public class LookAndFeelAction
{
    JFrame jf;
    public LookAndFeelAction(JFrame j)
    {
        jf=j;
    }

    public static void setBasicLookAndFeel()
    {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            JFrame.setDefaultLookAndFeelDecorated(false);
            EditorWindow tb = new EditorWindow("", new Namespace(null));
            tb.setExtendedState(JFrame.MAXIMIZED_BOTH);
            BufferedImage image = null;
            try {
                image = ImageIO.read(tb.getClass().getResource("resources/myicon.png"));
            } catch (IOException e) {
            }
            tb.setIconImage(image);
            tb.setSize(800, 600);
            tb.setLocation(100, 50);
            tb.setVisible(true);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
    }

    public static void setMotifLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            EditorWindow tb = new EditorWindow("", new Namespace(null));
            tb.setExtendedState(JFrame.MAXIMIZED_BOTH);
            BufferedImage image = null;
            try {
                image = ImageIO.read(tb.getClass().getResource("resources/myicon.png"));
            } catch (IOException e) {
            }
            tb.setIconImage(image);
            tb.setSize(800, 600);
            tb.setLocation(100, 50);
            tb.setVisible(true);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
    }

    public static void setNimbusLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            EditorWindow tb=new EditorWindow("", new Namespace(null));
            tb.setExtendedState(JFrame.MAXIMIZED_BOTH);
            BufferedImage image = null;
            try {
                image = ImageIO.read(tb.getClass().getResource("resources/myicon.png"));
            } catch (IOException e) {
            }
            tb.setIconImage(image);
            tb.setSize(800, 600);
            tb.setLocation(100, 50);
            tb.setVisible(true);
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){ }
    }

    public static void setWindowsLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            EditorWindow tb = new EditorWindow("", new Namespace(null));
            tb.setExtendedState(JFrame.MAXIMIZED_BOTH);
            BufferedImage image = null;
            try {
                image = ImageIO.read(tb.getClass().getResource("resources/myicon.png"));
            } catch (IOException e) {
            }
            tb.setIconImage(image);
            tb.setSize(800, 600);
            tb.setLocation(100, 50);
            tb.setVisible(true);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
    }

    public static void setWindowsClassicLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
            EditorWindow tb = new EditorWindow("", new Namespace(null));
            tb.setExtendedState(JFrame.MAXIMIZED_BOTH);
            BufferedImage image = null;
            try {
                image = ImageIO.read(tb.getClass().getResource("resources/myicon.png"));
            } catch (IOException e) {
            }
            tb.setIconImage(image);
            tb.setSize(800, 600);
            tb.setLocation(100, 50);
            tb.setVisible(true);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
    }

    public static void setGlobalDarkLookAndFeel()
    {
        MetalLookAndFeel.setCurrentTheme(new JavaGlobalDarkTheme().darkTheme);
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (Exception ev) {
        }

        JFrame.setDefaultLookAndFeelDecorated(true);
        EditorWindow tb = new EditorWindow("", new Namespace(null));
        tb.setExtendedState(JFrame.MAXIMIZED_BOTH);
        BufferedImage image = null;
        try {
            image = ImageIO.read(tb.getClass().getResource("resources/myicon.png"));
        } catch (IOException e) {
        }
        tb.setIconImage(image);
        tb.setSize(800, 600);
        tb.setLocation(100, 50);
        tb.setVisible(true);

    }

}

class JavaGlobalDarkTheme {

    DefaultMetalTheme darkTheme = new DefaultMetalTheme() {

        @Override
        public ColorUIResource getPrimary1() {
            return new ColorUIResource(new Color(30, 30, 30));
        }


        @Override
        public ColorUIResource getPrimary2() {
            return new ColorUIResource(new Color(20, 20, 20));
        }

        @Override
        public ColorUIResource getPrimary3() {
            return new ColorUIResource(new Color(30, 30, 30));
        }

        @Override
        public ColorUIResource getBlack(){
                    return new ColorUIResource(new Color(30, 30, 30));
                }

        @Override
        public ColorUIResource getWhite() {
            return new ColorUIResource(new Color(240, 240, 240));
        }


        @Override
        public ColorUIResource getMenuForeground() {
            return new ColorUIResource(new Color(200, 200, 200));
        }

        @Override
        public ColorUIResource getMenuBackground() {
            return new ColorUIResource(new Color(25, 25, 25));
        }

         @Override
        public ColorUIResource getMenuSelectedBackground(){
            return new ColorUIResource(new Color(50, 50, 50));
        }

        @Override
        public ColorUIResource getMenuSelectedForeground() {
            return new ColorUIResource(new Color(255, 255, 255));
        }


        @Override
        public ColorUIResource getSeparatorBackground() {
            return new ColorUIResource(new Color(15, 15, 15));
        }


        @Override
        public ColorUIResource getUserTextColor() {
            return new ColorUIResource(new Color(240, 240, 240));
        }

        @Override
        public ColorUIResource getTextHighlightColor() {
            return new ColorUIResource(new Color(80, 40, 80));
        }


        @Override
        public ColorUIResource getAcceleratorForeground(){
            return new ColorUIResource(new Color(30, 30,30));
        }


        @Override
        public ColorUIResource getWindowTitleInactiveBackground() {
            return new ColorUIResource(new Color(30, 30, 30));
        }


        @Override
        public ColorUIResource getWindowTitleBackground() {
            return new ColorUIResource(new Color(30, 30, 30));
        }


        @Override
        public ColorUIResource getWindowTitleForeground() {
            return new ColorUIResource(new Color(230, 230, 230));
        }

        @Override
        public ColorUIResource getPrimaryControlHighlight() {
            return new ColorUIResource(new Color(40, 40, 40));
        }

        @Override
        public ColorUIResource getPrimaryControlDarkShadow() {
            return new ColorUIResource(new Color(40, 40, 40));
        }

        @Override
        public ColorUIResource getPrimaryControl() {
            //color for minimize,maxi,and close
            return new ColorUIResource(new Color(60, 60, 60));
        }

        @Override
        public ColorUIResource getControlHighlight() {
            return new ColorUIResource(new Color(20, 20, 20));
        }

        @Override
        public ColorUIResource getControlDarkShadow() {
            return new ColorUIResource(new Color(50, 50, 50));
        }

        @Override
        public ColorUIResource getControl() {
            return new ColorUIResource(new Color(25, 25, 25));
        }

        @Override
        public ColorUIResource getControlTextColor() {
            return new ColorUIResource(new Color(230, 230, 230));
        }

        @Override
        public ColorUIResource getFocusColor() {
            return new ColorUIResource(new Color(0, 100, 0));
        }

        @Override
        public ColorUIResource getHighlightedTextColor() {
            return new ColorUIResource(new Color(250, 250, 250));
        }

    };
}
