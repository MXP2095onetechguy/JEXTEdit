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

import java.io.*;
import java.nio.file.*;
import java.text.ParseException;
import java.net.URISyntaxException;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;

import java.util.*;
import java.util.concurrent.*;

import org.eclipse.swt.*;
import org.eclipse.swt.awt.*;
import org.eclipse.swt.widgets.*;

import org.pushingpixels.radiance.theming.api.*;
import org.pushingpixels.radiance.theming.api.skin.*;

import org.apache.commons.lang3.*;
import org.apache.commons.configuration2.*;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.builder.fluent.Configurations;

import net.lingala.zip4j.ZipFile;
import net.sourceforge.argparse4j.inf.Namespace;
import net.sourceforge.argparse4j.ArgumentParsers;
// import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;


/* this class handles the window_ifm, asset unziping and argument parsing and the entry point
   basically, a dedicated main method, if this does not exists, the other method is more golly messy code like your pasta le sphagetti */
 
public class JEXTMain{
	// app
	static EditorWindow editfm;
	static UnzipUtility unzipy;
	static JFrame window;
	static Display disp;
	static final float version = 1.2f;
	static float cVer = 0f;
	static final int[] rectSize = {0, 0, 680, 680};
	
	
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
            .description("Good Java Text Editor.").epilog("Oook, noice.");
	
	// config
	static Configurations configEngine = new Configurations();
	static Configuration propertiesConfig;
	static String theme = "BusinessBlue";
	static String synthlafxml = "";
	
	public static void run() {
		
		disp = new Display();
		/* shell = new Shell(disp, SWT.TITLE | SWT.CLOSE | SWT.MIN);
		shell.setSize(rectSize[2], rectSize[3]);
		
		Monitor primary = disp.getPrimaryMonitor();
		
		org.eclipse.swt.graphics.Rectangle bounds = primary.getBounds();
		
		org.eclipse.swt.graphics.Rectangle rect = shell.getBounds();
		
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		
		shell.setLocation(x, y);
		
		org.eclipse.swt.widgets.Composite comp = new org.eclipse.swt.widgets.Composite(shell, SWT.EMBEDDED);
		
		comp.setBounds(rectSize[0], rectSize[1], rectSize[2], rectSize[3]);
		
		Frame fm = SWT_AWT.new_Frame(comp); */
		
		
		EventQueue.invokeLater(() -> {
			/* if(theme == "BusinessBlue") {
				RadianceThemingCortex.GlobalScope.setSkin(new BusinessBlueSteelSkin());
			}
			else if(theme == "Mariner") {
				RadianceThemingCortex.GlobalScope.setSkin(new MarinerSkin());
			}
			else {
				RadianceThemingCortex.GlobalScope.setSkin(new BusinessBlueSteelSkin());
			} */
			
			switch(theme) {
				case "BusinessBlue":
					RadianceThemingCortex.GlobalScope.setSkin(new BusinessBlueSteelSkin());
					break;
				case "Mariner":
					RadianceThemingCortex.GlobalScope.setSkin(new MarinerSkin());
					break;
				case "Custom":
					// You provide your own custom LAF here
					break;
				case "Platform":
					try {
				        UIManager.setLookAndFeel(
				                UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						System.err.println("An error occured while using System dependent look");
						e1.printStackTrace();
						throw new IllegalStateException("Error while using System dependent look");
					}
					break;
				case "Motif":
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						System.err.println("An error occured while using System dependent look");
						e1.printStackTrace();
						throw new IllegalStateException("Error while using System dependent look");
					}
					break;
				case "Metal":
					try {
						UIManager.setLookAndFeel(
								UIManager.getCrossPlatformLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						// TODO Auto-generated catch block
						System.err.println("An error occured while using METAL LAF");
						e.printStackTrace();
						throw new IllegalStateException("Error while using Metal look and feel");
					}
					break;
				case "Synth":
					 SynthLookAndFeel laf = new SynthLookAndFeel();
					 try {
						laf.load(JEXTMain.class.getResource(synthlafxml));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						throw new IllegalStateException("Error while using synth laf!");
					}
					 
					try {
						UIManager.setLookAndFeel(laf);
					} catch (UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						throw new IllegalStateException("Error while trying to set the synth look and feel");
					}
					break;
				default:
					RadianceThemingCortex.GlobalScope.setSkin(new BusinessBlueSteelSkin());
					throw new IllegalArgumentException("Invalid theme!");
			}
			
			JFrame.setDefaultLookAndFeelDecorated(true); 
			
			window = new JFrame();
			
			
			// Frame awtfm = new Frame();
			
			editfm = new EditorWindow(window, disp, viewPath, ns);
			
			// window.add(editfm);
			
			window.setSize(680, 680);
			window.setLocationRelativeTo(null);
			window.setVisible(true);
			window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
			window.addWindowListener(new WindowListener() {
				@Override
		         public void windowClosing(WindowEvent windowEvent){
					System.exit(0);
		          }

				@Override
				public void windowOpened(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowClosed(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowIconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowDeiconified(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void windowDeactivated(WindowEvent e) {
					// TODO Auto-generated method stub
					
				}     
			});
		});
		
		// shell.open();
		
		while(!disp.isDisposed()) {
			if(!disp.isDisposed()) {
				if(!disp.readAndDispatch()){
					disp.sleep();
				}
			}
		}
		
		/* while(!shell.isDisposed()) {
			/* if(!disp.readAndDispatch()){
				disp.sleep();
			}
			disp.sleep();
		} */
		
		disp.dispose();
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
	
	public static void runApplication(boolean restart) throws URISyntaxException, IOException
	{
	  final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
	  final File currentJar = new File((new JEXTMain()).getClass().getProtectionDomain().getCodeSource().getLocation().toURI());

	  /* is it a jar file? */
	  if(!currentJar.getName().endsWith(".jar"))
	    return;

	  /* Build command: java -jar application.jar */
	  final ArrayList<String> command = new ArrayList<String>();
	  command.add(javaBin);
	  command.add("-jar");
	  command.add(currentJar.getPath());

	  final ProcessBuilder builder = new ProcessBuilder(command);
	  builder.start();
	  if(restart) {
		  System.exit(0);
	  }
	}
	
	public static void main(String[] args) {
		/* parser.addArgument("-f", "--file")
			.nargs("+").metavar("Path")
			.type(String.class).dest("files")
			.action(Arguments.storeConst())
			.help("Files to open");
		
		// LOL
		parser.addArgument("-a", "--giselle").dest("giselle").help("useless");

        parser.addArgument("-t", "--type")
        .choices("SHA-256", "SHA-512", "SHA1").setDefault("SHA-256")
        .help("Specify hash function to use"); */
		
		parser.addArgument("-f", "--file")
        .dest("file");
		
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        
        try {
        	propertiesConfig = configEngine.properties((new JEXTMain()).getClass().getResource("files/config.properties"));
        	theme = propertiesConfig.getString("theming", "BusinessBlue");
        	cVer = propertiesConfig.getFloat("version", -1f);
        	
        	if(theme == "Synth") {
        		synthlafxml = propertiesConfig.getString("SynthThemeFile");
        	}
        }
        catch(ConfigurationException cex) {
        	cex.printStackTrace();
        }
        
        if(cVer == -1f) {
        	System.err.println("Config is not at the right version!");
        	System.exit(0);
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
        	if(!new File( Paths.get(cwd).resolve("MXPSQL").resolve("JEXTEdit").resolve("resources").resolve("unused").resolve("toestrap.jpg").toString() ).exists() || cVer < version) {
        		JOptionPane.showMessageDialog(win, "Please wait while we extract our resources. It will begin once you close this dialog. Once we are done extracting, we will show the window.");
        		win.dispose();
            	if(!unzipJar()) {
            		System.err.println("Error occured while unzipping assets");
            		System.exit(0);
            	}
        	}
        }
        
        try {
        	xmlSanitize(Paths.get(viewPath));
        }
        catch(Exception e) {
        	System.err.println("An error occured while sanitizing the xml");
        	e.printStackTrace();
        	System.exit(0);
        }
        
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
        	assert System.getProperty("sun.awt.xembedserver") == "true";
        }
        
		try {
			run();
		}
		catch(Exception e) {
			System.err.println("Time to crash, an error had occured");
			e.printStackTrace(System.err);
			
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error with application", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
}