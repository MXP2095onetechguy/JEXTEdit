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

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.nio.file.*;

import org.apache.commons.lang3.*;

import net.lingala.zip4j.ZipFile;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;


/* this class handles the window, asset unziping and argument parsing and the entry point
   basically, a dedicated main method */
 
public class JEXTMain{
	// app
	static EditorWindow editwin;
	static UnzipUtility unzipy;
	
	
	// Path
	static Path cwdPath = Paths.get(System.getProperty("user.dir"));
	static String cwd = cwdPath.toAbsolutePath().toString();
	static Path confPath = Paths.get(cwd);
	static String JarPath;
	static String superJarPath;
	static String viewPath = "";
	
	// args
	static Namespace ns;
	static ArgumentParser parser = ArgumentParsers.newFor("JEXTEdit").build()
            .defaultHelp(true)
            .description("Good Java Text Editor.");
	
	public static void run() {
		EventQueue.invokeLater(() -> {
			editwin = new EditorWindow(viewPath, ns);
			editwin.setSize(680, 680);
			editwin.setLocationRelativeTo(null);
			editwin.setVisible(true);
		});
	}
	
	public static void xmlSanitize(Path path) {
		File file = new File(path.toString());
		try {
			Scanner Reader = new Scanner(file);
			String edit = "";
			while(Reader.hasNextLine()) {
				edit += Reader.nextLine();
				edit += System.getProperty("line.separator");
			}
			
			
			Reader.close();
			
			while(true) {
				char first = edit.charAt(0);
				char second = edit.charAt(1);
				
				if(first != '<' && second != 'x') {
					edit = edit.substring(1, edit.length());
					System.out.println("1");
				}
				else {
					break;
				}
			}
			
			new FileWriter(file, false).close();
			
			FileWriter writer = new FileWriter(file);
			PrintWriter printer = new PrintWriter(writer);
			printer.flush();
			printer.print(edit);
			printer.close();
			writer.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// check path type
	public static boolean isDirectory(String path) {
	    return path !=null && new File(path).isDirectory();
	}
	
	public static boolean unzipJar() {
		try {
			try {
				superJarPath = new File(JEXTMain.class
				          .getProtectionDomain()
				          .getCodeSource()
				          .getLocation()
				          .toURI()
				          .getPath()).toString();
				
			}
			catch(NullPointerException noe) {
				superJarPath = JarPath;
			}
			
			
			/* if(!new File("META-INF").exists()) {
				new File("META-INF").mkdir();
			}
			
			if(!new File("org").exists()) {
				new File("org").mkdir();
			} */
			
			File sawtdir = new File("MXPSQL");
			if(!sawtdir.exists()) {
				sawtdir.mkdir();
			}
			
			try {
				ZipFile jarred = new ZipFile(superJarPath);
				// unzipy.unzip(superJarPath, cwd);
				jarred.extractFile("MXPSQL/", Paths.get(cwd).toString());
				jarred.close();
			}
			catch(java.nio.file.InvalidPathException jniof) {
				
			}
			
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
		
        JFrame win = new JFrame();
		
        try {
        	JarPath = (Paths.get(cwd).resolve(System.getProperty("java.class.path"))).toString();
        	confPath = confPath.resolve("MXPSQL").resolve("JEXTEdit").resolve("files").resolve("conf.xml");
        	viewPath = "MXPSQL/JEXTEdit/files/viewsfile.xml";
        }
        catch(java.nio.file.InvalidPathException ipe) {
        	JarPath = (Paths.get(System.getProperty("java.class.path").split(";")[0])).toString();
        	confPath = confPath.resolve("src").resolve("app").resolve("MXPSQL").resolve("JEXTEdit").resolve("files").resolve("conf.xml");
        	viewPath = "src/app/MXPSQL/JEXTEdit/files/viewsfile.xml";
        }
		
        if(!isDirectory(JarPath)) {
        	if(!new File( Paths.get(cwd).resolve("MXPSQL").resolve("JEXTEdit").resolve("resources").resolve("unused").resolve("toestrap.jpg").toString() ).exists()) {
        		JOptionPane.showMessageDialog(win, "Please wait while we extract our resources. It will begin once you close this dialog. Once we are done extracting, we will show the window.");
        		win.dispose();
            	if(!unzipJar()) {
            		System.exit(0);
            	}
        	}
        }
        
        xmlSanitize(Paths.get(viewPath));
        
        try {
			BOMUtils.removeBom(Paths.get(viewPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
        
        // sun.awt.xembedserver workaround, run if linux
        if(SystemUtils.IS_OS_LINUX) {
        	System.setProperty("sun.awt.xembedserver", "true");
        }
        
		run();
	}
}