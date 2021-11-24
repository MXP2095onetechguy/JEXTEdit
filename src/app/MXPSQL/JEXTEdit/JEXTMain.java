package MXPSQL.JEXTEdit;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.nio.file.*;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;


// this class handles the window, asset unziping and argument parsing
public class JEXTMain{
	static EditorWindow editwin;
	static UnzipUtility uzip;
	
	// Path
	static Path cwdPath = Paths.get(System.getProperty("user.dir"));
	static String cwd = cwdPath.toAbsolutePath().toString();
	static String JarPath;
	static String superJarPath;
	
	// source
	static String viewPath = "";
	
	// args
	static Namespace ns;
	static ArgumentParser parser = ArgumentParsers.newFor("JEXTEdit").build()
            .defaultHelp(true)
            .description("Good Java Text Editor.");
	
	public static void run() {
		EventQueue.invokeLater(() -> {
			editwin = new EditorWindow(viewPath);
			editwin.setSize(680, 680);
			editwin.setLocationRelativeTo(null);
			editwin.setVisible(true);
		});
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
			
			
			if(!new File("META-INF").exists()) {
				new File("META-INF").mkdir();
			}
			
			if(!new File("org").exists()) {
				new File("org").mkdir();
			}
			
			File sawtdir = new File("Sawt");
			if(!sawtdir.exists()) {
				sawtdir.mkdir();
			}
			
			try {
				UnzipUtility unzipy = new UnzipUtility();
				unzipy.unzip(superJarPath, cwd);
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
        	viewPath = "MXPSQL/JEXTEdit/files/viewsfile.xml";
        }
        catch(java.nio.file.InvalidPathException ipe) {
        	JarPath = (Paths.get(System.getProperty("java.class.path").split(";")[0])).toString();
        	viewPath = "src/app/MXPSQL/JEXTEdit/files/viewsfile.xml";
        }
		
        if(!isDirectory(JarPath)) {
        	if(!new File( Paths.get(cwd).resolve("MXPSQL").resolve("JEXTEdit").resolve("resources").resolve("unused").resolve("toestrap.jpg").toString() ).exists()) {
        		JOptionPane.showMessageDialog(win, "Please wait while we extract our assets. It will begin once you close this dialog. Once we are done extracting, we will show the window.");
        		win.dispose();
            	if(!unzipJar()) {
            		System.exit(0);
            	}
        	}
        }
        
		run();
	}
}