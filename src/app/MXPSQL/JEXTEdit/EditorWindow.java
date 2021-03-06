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
import javax.swing.border.*;
import java.awt.dnd.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.undo.*;

import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import org.eclipse.swt.awt.SWT_AWT;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import jakarta.xml.bind.*;
import javax.xml.parsers.*;
import javax.swing.text.*;
import javax.swing.tree.TreePath;

import java.awt.datatransfer.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.sourceforge.argparse4j.inf.Namespace;
import MXPSQL.JEXTEdit.EditorWindow.ToolBarButtonsAction;

import MXPSQL.JEXTEdit.*;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;


///////////////////////////////////////////////////////////////////////////////
//                 Main window class, dedicated window class
//////////////////////////////////////////////////////////////////////////////
public final class EditorWindow extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JFrame parent;
	Shell shell;
	Display display;
	
	ImageIcon liico;
	Image lico;

    JMenuBar mb;

    JSplitPane jsplit;

    JTabbedPane _tabbedPane;

    JList _list;

    DefaultListModel listModel;
    
    JPanel spanel;
    ProgressBar progressBar;
    JToolBar statusBar;

    JLabel readylabel;

    JLabel filenameLabel=new JLabel("");
    JLabel rowLabel=new JLabel("Row : ");
    JLabel colLabel=new JLabel("Col : ");

    // RibbonBar ribbon;
    RibbonBar ribbon;

    JMenu windowMenu=new JMenu("Window");

    int count=1;
    int webcount=1;

    DefaultListModel filesHoldListModel=new DefaultListModel();

    JList filesHoldList=new JList(filesHoldListModel);


    UndoManager _undoManager = new UndoManager();
    Action undoAction = new PerformUndoAction(_undoManager);
    Action redoAction = new PerformRedoAction(_undoManager);


    Clipboard clip = getToolkit().getSystemClipboard();


    ButtonGroup buttonGroup;


    Toolkit toolkit=Toolkit.getDefaultToolkit();


    JPopupMenu _popupMenu;
    
    Namespace ns;
    
    
    public String viewPath = "src/app/MXPSQL/JEXTEdit/files/viewsfile.xml";



    // getCenterPoints() function that returns the
    // center point of the screen
    public Point getCenterPoints()
    {
        Point pt=new Point(0,0);
        Dimension d=toolkit.getScreenSize();
        pt.x=d.width/3;
        pt.y=d.height/4;

        return pt;
    }



    // create constructor of TabbedNotepad class
    public EditorWindow(JFrame prt, Display disp, String viewp, Namespace nss)
    {
    	
    	parent = prt;
    	display = disp;
    	
    	liico = new ImageIcon(this.getClass().getResource("resources/logo.png"));
    	lico = liico.getImage();
    	
    	parent.setIconImage(lico);
    	
    	ns = nss;
    	
    	viewPath = viewp;
        parent.setTitle("JEXTEdit");
        
        // setUndecorated(true);


        mb=new JMenuBar();


        //**********************************************
        // File menu
        //**********************************************
        JMenu file=new JMenu("  File  ");

        // creating file menu itemas
        JMenuItem file_new=new JMenuItem("  New                                      ");
        file_new.setIcon(new ImageIcon(this.getClass().getResource("resources/new.png")));

        file_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
        
        JMenuItem file_newweb=new JMenuItem("  New Web browser                                      ");
        file_newweb.setIcon(new ImageIcon(this.getClass().getResource("resources/web.png")));
        file_newweb.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));

        JMenuItem file_open=new JMenuItem("  Open ");
        file_open.setIcon(new ImageIcon(this.getClass().getResource("resources/open.png")));
        file_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        
        JMenuItem file_openwebrq = new JMenuItem("  Open From Web");
        file_openwebrq.setIcon(new ImageIcon(this.getClass().getResource("resources/webrq.png")));

        JMenuItem file_save=new JMenuItem("  Save ");
        file_save.setIcon(new ImageIcon(this.getClass().getResource("resources/save.png")));
        file_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));

        JMenuItem file_saveas=new JMenuItem("  Save As");
        file_saveas.setIcon(new ImageIcon(this.getClass().getResource("resources/saveas.png")));
        file_saveas.setAccelerator(KeyStroke.getKeyStroke("F2"));

        JMenuItem file_saveall=new JMenuItem("  Save All");
        file_saveall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));

        JMenuItem file_close=new JMenuItem("  Close");
        file_close.setIcon(new ImageIcon(this.getClass().getResource("resources/close.png")));
        file_close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.CTRL_MASK));
        
        JMenuItem file_closeall=new JMenuItem("  Close All");
        file_closeall.setIcon(new ImageIcon(this.getClass().getResource("resources/closeall.png")));
        file_closeall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));
        
        JMenuItem file_openinsystemeditor=new JMenuItem("  Open In System Editor");
        file_openinsystemeditor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));

        JMenuItem file_restart=new JMenuItem("  Restart");
        file_restart.setIcon(new ImageIcon(this.getClass().getResource("resources/restart.png")));
        
        JMenuItem file_newwin=new JMenuItem("  New Window");
        file_newwin.setIcon(new ImageIcon(this.getClass().getResource("resources/newwin.png")));
        
        JMenuItem file_exit=new JMenuItem("  Exit");
        file_exit.setIcon(new ImageIcon(this.getClass().getResource("resources/exit.png")));

        // adding actions to file menu items
        File_MenuItemsAction file_action=new File_MenuItemsAction();

        file_new.addActionListener(file_action);
        file_newweb.addActionListener((e) -> {
        	File_NewWeb_Action();
        });
        file_open.addActionListener(file_action);
        file_openwebrq.addActionListener((e) -> {
        	Platform.runLater(new Runnable() {
        		@Override
        		public void run() {
        			File_Open_WebRq_Action();
        		}
        	});
        });
        file_save.addActionListener(file_action);
        file_saveas.addActionListener(file_action);
        file_saveall.addActionListener(file_action);
        file_close.addActionListener(file_action);
        file_closeall.addActionListener(file_action);
        file_openinsystemeditor.addActionListener(file_action);
        file_restart.addActionListener((e) -> {try {JEXTMain.runApplication(true);} catch (URISyntaxException | IOException e1) {e1.printStackTrace();}});
        file_newwin.addActionListener((e) -> {try {JEXTMain.runApplication(false);} catch (URISyntaxException | IOException e1) {e1.printStackTrace();}});
        file_exit.addActionListener(file_action);

        //add MenuListener to menu items
        JMenuItem[] filemenuitems={ file_save,file_saveas,file_saveall,file_close,file_closeall };

        Menus_MenuListener fml=new Menus_MenuListener(filemenuitems);
        file.addMenuListener(fml);




        //***********************************************
        // Edit menu
        //***********************************************
        JMenu edit=new JMenu("  Edit  ");

        //creating edit menu items
        JMenuItem edit_cut=new JMenuItem("  Cut                                      ");
        edit_cut.setIcon(new ImageIcon(this.getClass().getResource("resources/cut.png")));
        edit_cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));

        JMenuItem edit_copy = new JMenuItem("  Copy");
        edit_copy.setIcon(new ImageIcon(this.getClass().getResource("resources/copy.png")));
        edit_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));

        JMenuItem edit_paste = new JMenuItem("  Paste");
        edit_paste.setIcon(new ImageIcon(this.getClass().getResource("resources/paste.png")));
        edit_paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));

        JMenuItem edit_undo = new JMenuItem("  Undo");
        edit_undo.setIcon(new ImageIcon(this.getClass().getResource("resources/undo.png")));
        edit_undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));

        JMenuItem edit_redo = new JMenuItem("  Redo");
        edit_redo.setIcon(new ImageIcon(this.getClass().getResource("resources/redo.png")));
        edit_redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,ActionEvent.CTRL_MASK));


        JMenuItem edit_find = new JMenuItem("  Find");
        edit_find.setIcon(new ImageIcon(this.getClass().getResource("resources/find.png")));
        edit_find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));

         JMenuItem edit_replace = new JMenuItem("  Replace");

         JMenuItem edit_goto = new JMenuItem("  GoTo");
         edit_goto.setIcon(new ImageIcon(this.getClass().getResource("resources/goto.png")));
         edit_goto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,ActionEvent.CTRL_MASK));

         JMenuItem edit_selectall = new JMenuItem("  Select All");

         JMenu edit_changecase=new JMenu("  Change Case");
         JMenuItem edit_changecase_upper=new JMenuItem("  Upper Case   ");
         JMenuItem edit_changecase_lower = new JMenuItem("  Lower Case   ");
         JMenuItem edit_changecase_sentence = new JMenuItem("  Sentence Case   ");
         
         JMenuItem edit_user = new JMenuItem("  Insert username");
         edit_user.setIcon(new ImageIcon(this.getClass().getResource("resources/user.png")));
         
         JMenuItem edit_time = new JMenuItem("  Insert time");
         edit_time.setIcon(new ImageIcon(this.getClass().getResource("resources/time.png")));

         JMenuItem edit_nextdocument=new JMenuItem("  Next Document   ");
         JMenuItem edit_previousdocument = new JMenuItem("  Previous Document   ");

         // adding actions to edit menu items
        Edit_MenuItemsAction edit_action=new Edit_MenuItemsAction();

        edit_cut.addActionListener(edit_action);
        edit_copy.addActionListener(edit_action);
        edit_paste.addActionListener(edit_action);
        edit_goto.addActionListener(edit_action);
        edit_find.addActionListener(edit_action);
        edit_replace.addActionListener(edit_action);
        edit_selectall.addActionListener(edit_action);
        edit_changecase_upper.addActionListener(edit_action);
        edit_changecase_lower.addActionListener(edit_action);
        edit_changecase_sentence.addActionListener(edit_action);
        edit_user.addActionListener((e) -> {
        	int sel = _tabbedPane.getSelectedIndex();
        	
        	if(sel != -1) {
        		JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
        		textPane.insert(System.getProperty("user.name"), textPane.getCaretPosition());
        	}
        });        
        
        edit_time.addActionListener((e) -> {
        	int sel = _tabbedPane.getSelectedIndex();
        	
        	if(sel != -1) {
        		JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
        		textPane.insert(new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), textPane.getCaretPosition());
        	}
        });
        edit_nextdocument.addActionListener(edit_action);
        edit_previousdocument.addActionListener(edit_action);   

        edit_undo.addActionListener(undoAction);
        edit_redo.addActionListener(redoAction);

        // add MenuListener to menu items
        JMenuItem[] editmenuitems={ edit_cut,edit_copy,edit_paste,edit_undo,edit_redo,edit_find,edit_replace,edit_goto,edit_selectall,
                                                      edit_changecase_upper,edit_changecase_lower,edit_changecase_sentence,edit_user, edit_time,edit_nextdocument,edit_previousdocument};

        Menus_MenuListener eml=new Menus_MenuListener(editmenuitems);
        edit.addMenuListener(eml);




        //************************************************
        // View menu
        //************************************************
        JMenu view=new JMenu("  View  ");

        //creating view menu items
        JMenuItem view_font=new JMenuItem("  Font                               ");
        view_font.setIcon(new ImageIcon(this.getClass().getResource("resources/font.png")));
        view_font.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));

        JMenuItem view_forecolor=new JMenuItem("  Fore Color");
        JMenuItem view_backcolor=new JMenuItem("  Back Color");

        JMenu view_tabsalign=new JMenu("  Tabs Alignment");
        JRadioButtonMenuItem view_tabsalign_top=new JRadioButtonMenuItem("  Top  ");
        view_tabsalign_top.setSelected(true);
        JRadioButtonMenuItem view_tabsalign_bottom = new JRadioButtonMenuItem("  Bottom  ");
        JRadioButtonMenuItem view_tabsalign_left = new JRadioButtonMenuItem("  Left  ");
        JRadioButtonMenuItem view_tabsalign_right = new JRadioButtonMenuItem("  Right  ");

        buttonGroup=new ButtonGroup();
        buttonGroup.add(view_tabsalign_top);
        buttonGroup.add(view_tabsalign_bottom);
        buttonGroup.add(view_tabsalign_left);
        buttonGroup.add(view_tabsalign_right);


        JCheckBoxMenuItem view_toolbar=new JCheckBoxMenuItem("  Ribbon");

        JCheckBoxMenuItem view_statusstrip=new JCheckBoxMenuItem("  Status Strip");
        view_statusstrip.setSelected(true);

        JCheckBoxMenuItem view_documentpane = new JCheckBoxMenuItem("  Document Pane");
        view_documentpane.setSelected(true);

        //adding actions to view menu items
        View_MenuItemsAction view_action=new View_MenuItemsAction();

        view_font.addActionListener(view_action);
        view_forecolor.addActionListener(view_action);
        view_backcolor.addActionListener(view_action);
        view_tabsalign_top.addActionListener(view_action);
        view_tabsalign_bottom.addActionListener(view_action);
        view_tabsalign_left.addActionListener(view_action);
        view_tabsalign_right.addActionListener(view_action);

        view_documentpane.addActionListener(new View_DocumentPane_Action(view_documentpane));

        view_toolbar.addActionListener(new View_ToolBar_Action(view_toolbar));
        
        // word_wrap.addActionListener(new Word_Wrap_Action(word_wrap, _tabbedPane));

        view_statusstrip.addActionListener(new View_StatusStrip_Action(view_statusstrip));

        JMenuItem[] viewmenuitems = {view_font,view_forecolor,view_backcolor};

        Menus_MenuListener vml = new Menus_MenuListener(viewmenuitems);
        view.addMenuListener(vml);




        //************************************************
        // Run menu
        //************************************************
        JMenu run=new JMenu("  Run  ");

        //creating run menu items
        JMenuItem run_run=new JMenuItem("  Run                                            ");
        run_run.setAccelerator(KeyStroke.getKeyStroke("F5"));

        JMenuItem run_runinbrowser=new JMenuItem("  Run in Browser ");

        JMenuItem run_googlesearch=new JMenuItem("  Google Search");

        // adding actions to run menu items
        Run_MenuItemsAction run_action=new Run_MenuItemsAction();

        run_run.addActionListener(run_action);
        run_runinbrowser.addActionListener(run_action);
        run_googlesearch.addActionListener(run_action);




        //************************************************
        // Window menu
        //************************************************
        windowMenu.addMenuListener(new WindowMenuAction());




        //*********************************************
        // help menu
        //********************************************
        JMenu help = new JMenu("  Help  ");

        JMenuItem help_helpcontents = new JMenuItem("  Help Contents            ");
        JMenuItem help_onlinehelp = new JMenuItem("  Online Help");
        JMenuItem help_about = new JMenuItem("  About....");

        // adding actions to help menu items
        help_about.addActionListener(file_action);
        help_onlinehelp.addActionListener((e) -> {
        	if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        	    try {
					Desktop.getDesktop().browse(new URI("https://github.com/MXP2095onetechguy/JEXTEdit"));
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e1.printStackTrace(pw);
					
					JOptionPane.showMessageDialog(this, "An error occured the error is " + sw.toString() + ", try to launch browser or what ever it is and go to https://github.com/MXP2095onetechguy/JEXTEdit to get help", "Help",JOptionPane.ERROR_MESSAGE);
					
				}
        	}
        	else {
        		JOptionPane.showMessageDialog(this, "Cannot launch browser, try to launch browser or what ever it is and go to https://github.com/MXP2095onetechguy/JEXTEdit to get help", "Help",JOptionPane.ERROR_MESSAGE);
        	}
        });
        help_helpcontents.addActionListener(file_action);




        //***********************************************************
        // adding file menuitems to file menu

        file.add(file_new);
        file.add(file_newweb);
        file.addSeparator();
        file.add(file_open);
        file.add(file_openwebrq);
        file.addSeparator();
        file.add(file_save);
        file.add(file_saveas);
        file.add(file_saveall);
        file.addSeparator();
        file.add(file_close);
        file.add(file_closeall);
        file.addSeparator();
        file.add(file_openinsystemeditor);
        file.addSeparator();
        file.add(file_restart);
        file.add(file_newwin);
        file.addSeparator();
        file.add(file_exit);

        // add file menu to menu bar mb
        mb.add(file);


        // adding edit menuitems to edit menu
        edit.add(edit_cut);
        edit.add(edit_copy);
        edit.add(edit_paste);
        edit.addSeparator();
        edit.add(edit_undo);
        edit.add(edit_redo);
        edit.addSeparator();
        edit.add(edit_find);
        edit.add(edit_replace);
        edit.add(edit_goto);
        edit.addSeparator();
        edit.add(edit_selectall);
        edit.addSeparator();
        edit_changecase.add(edit_changecase_upper);
        edit_changecase.add(edit_changecase_lower);
        edit_changecase.add(edit_changecase_sentence);
        edit.add(edit_changecase);
        edit.addSeparator();
        edit.add(edit_user);
        edit.add(edit_time);
        edit.addSeparator();
        edit.add(edit_nextdocument);
        edit.add(edit_previousdocument);

        //add edit menu to mb
        mb.add(edit);


        //adding view menuitems to view menu
        view.add(view_font);
        view.addSeparator();
        view.add(view_forecolor);
        view.add(view_backcolor);
        view.addSeparator();
        view_tabsalign.add(view_tabsalign_top);
        view_tabsalign.add(view_tabsalign_bottom);
        view_tabsalign.add(view_tabsalign_left);
        view_tabsalign.add(view_tabsalign_right);
        view.add(view_tabsalign);
        view.addSeparator();
        view.add(view_toolbar);
        view.add(view_statusstrip);
        view.addSeparator();
        view.add(view_documentpane);
        view.addSeparator();

        //add view menu to mb
        mb.add(view);


        // adding run menu items to run menu
        run.add(run_run);
        run.addSeparator();
        run.add(run_runinbrowser);
        run.addSeparator();
        run.add(run_googlesearch);

        //add run menu to mb
        mb.add(run);


        mb.add(windowMenu);


        //adding help menu items to help menu
        help.add(help_helpcontents);
        help.add(help_onlinehelp);
        help.add(help_about);

        // add help menu to mb
        mb.add(help);


        ////////////////////////////////////////////////////////////////////////
        //set visibility of ,Status Strip,ToolStrip
        // and Tabs Alignment when application starts by reading values from
        // a file files/viewsfile.xml
        String isDocumentSelect = getNodeTextContent("documentPane");
        String isStatusStrip = getNodeTextContent("statusStrip");
        String isToolStrip = getNodeTextContent("toolStrip");
        String tabsAlign = getNodeTextContent("tabsAlignment");





        //***********************************************************************
        // create _tabbedPane object & adding ChangeListener interface to it
        //***********************************************************************
         
         _tabbedPane=new JTabbedPane();
         _tabbedPane.setFont(new Font("Calibri",Font.PLAIN,14));
         _tabbedPane.addChangeListener(new TabChanged());


         //setting tab placement
         switch(tabsAlign){
             case "Top":
                 _tabbedPane.setTabPlacement(JTabbedPane.TOP);
                 view_tabsalign_top.setSelected(true);
                 break;

            case "Bottom":
                 _tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
                 view_tabsalign_bottom.setSelected(true);
                 break;

            case "Left":
                 _tabbedPane.setTabPlacement(JTabbedPane.LEFT);
                 view_tabsalign_left.setSelected(true);
                 break;

            case "Right":
                 _tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
                 view_tabsalign_right.setSelected(true);
                 break;
         }




         //***********************************************************************
         // create listModel object & _list object and adding
         // ListSelectionListener interface to _list
         //************************************************************************
         listModel=new DefaultListModel();
         _list=new JList(listModel);
         _list.setFont(new Font("Calibri",Font.PLAIN,14));

         _list.setMinimumSize(new Dimension(400,600));
         JScrollPane listpane=new JScrollPane(_list);
         _list.addListSelectionListener(new SelectTabFromListItem());


         // creating document tab pane & adding listpane object to it
         JTabbedPane documentPane=new JTabbedPane();
         documentPane.addTab(" Document Selector", listpane);
         
         
         
         //***************************************************************************
         // Add a file browser
         // put it on the documentpane
         //*******************************************************************************
         HashMap<String, File[]> fsmap = new HashMap<String, File[]>();
         // fsmap.put("roots", File.listRoots());
         fsmap.put("roots", null);
         
         fsmap.put("cwd", new File[] {new File(".")});
         
         
         FBrowser docExView = new FBrowser(fsmap);
         
         JTree ftree = docExView.ftree;
         
 		 MouseListener ml = new MouseAdapter() {
		    public void mousePressed(MouseEvent e) {
		        int selRow = ftree.getRowForLocation(e.getX(), e.getY());
		        TreePath selPath = ftree.getPathForLocation(e.getX(), e.getY());
		        if(selRow != -1) {
		            if(e.getClickCount() == 2) {
		            	Object node = selPath.getLastPathComponent();
		            	File fnode;
		            	try {
		            		fnode = (File) node;
		            	}
		            	catch(Exception e1) {
		            		e1.printStackTrace();
		            		return;
		            	}
		            	
		            	try {
		            		if(!fnode.isDirectory()) {
		                		String filename=fnode.toString();
		                    	String file=filename;
		                    	if(filename.contains("\\")){
		                        	file = filename.substring(filename.lastIndexOf("\\") + 1);
		                    	}
		                    	else if(filename.contains("/")){
		                    		file = filename.substring(filename.lastIndexOf("/") + 1);
		                    		}
		                    
		                    	StringBuilder sb = new StringBuilder();
		                    	BufferedReader d;
		                    
		                    	d = new BufferedReader(new FileReader(filename));
		                    	String line;
		                    	while((line=d.readLine())!=null)
		                             sb.append(line + "\n");
		                    	d.close();
		                    
		                    	open_from_string(sb.toString(), file);
		            		}
		            	}
		            	catch(Exception exx) {
		            		exx.printStackTrace();
		            	}
		            }
		        }
		    }
		};
		
		ftree.addMouseListener(ml);
		
         documentPane.addTab(" File Browser", docExView);



        //***********************************************************
        // create and add documentPane & _tabbedPane to jsplit
         //***********************************************************
         jsplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, documentPane,_tabbedPane);
        jsplit.setContinuousLayout(true);
        jsplit.setOneTouchExpandable(true);
        jsplit.setDividerLocation(210);



        //set visibility to 
        if(isDocumentSelect.equals("true")){
            jsplit.getLeftComponent().setVisible(true);
            jsplit.setDividerLocation(210);
            view_documentpane.setSelected(true);
        }else{
            jsplit.getLeftComponent().setVisible(false);
            view_documentpane.setSelected(false);
        }





        ///////////////////////////////////////
        //set JMenubar
        parent.setJMenuBar(mb);
        
        ///////////////////////////////////////
        //make the status panel for progressbar
        spanel = new JPanel();




        //********************************************************
        // creating & adding statusbar to south direction
        //*********************************************************
        statusBar=new JToolBar();
        statusBar.setFloatable(false);

        readylabel=new JLabel("JEXTEdit");
        readylabel.setFont(new Font("Calibri",Font.PLAIN,15));
        filenameLabel.setFont(new Font("Calibri",Font.PLAIN,15));
        statusBar.add(readylabel);
        statusBar.add(new JLabel("                          "));
        statusBar.add(filenameLabel);
        statusBar.add(new JLabel("                                                            "));
        statusBar.add(rowLabel);
        statusBar.add(new JLabel("     "));
        statusBar.add(colLabel);

        ///////////////////////////////////////////////////
        //set visibility to status strip
        if (isStatusStrip.equals("true")) {
            spanel.setVisible(true);
            view_statusstrip.setSelected(true);
        } else {
            spanel.setVisible(false);
            view_statusstrip.setSelected(false);
        }
        
        



        //************************************************************
        // creating popup menu
        //************************************************************
        _popupMenu=new JPopupMenu();

        JMenuItem popup_edit_cut = new JMenuItem("  Cut                                     ");
        popup_edit_cut.setIcon(new ImageIcon(this.getClass().getResource("resources/cut.png")));
        popup_edit_cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

        JMenuItem popup_edit_copy = new JMenuItem("  Copy");
        popup_edit_copy.setIcon(new ImageIcon(this.getClass().getResource("resources/copy.png")));
        popup_edit_copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));

        JMenuItem popup_edit_paste = new JMenuItem("  Paste");
        popup_edit_paste.setIcon(new ImageIcon(this.getClass().getResource("resources/paste.png")));
        popup_edit_paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));

        JMenuItem popup_edit_selectall = new JMenuItem("  Select All");
        
        JMenuItem popup_edit_user = new JMenuItem("  Insert Username");
        popup_edit_user.setIcon(new ImageIcon(this.getClass().getResource("resources/user.png")));
        
        JMenuItem popup_edit_time = new JMenuItem("  Insert Time");
        popup_edit_time.setIcon(new ImageIcon(this.getClass().getResource("resources/time.png")));

        JMenu popup_edit_changecase = new JMenu("  Change Case");
        JMenuItem popup_edit_changecase_upper = new JMenuItem("  Upper Case   ");
        JMenuItem popup_edit_changecase_lower = new JMenuItem("  Lower Case   ");
        JMenuItem popup_edit_changecase_sentence = new JMenuItem("  Sentence Case   ");

        popup_edit_changecase.add(popup_edit_changecase_upper);
        popup_edit_changecase.add(popup_edit_changecase_lower);
        popup_edit_changecase.add(popup_edit_changecase_sentence);

        JMenuItem popup_view_font = new JMenuItem("  Font ");
        popup_view_font.setIcon(new ImageIcon(this.getClass().getResource("resources/font.png")));
        popup_view_font.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));

        // add actions to popup menu items
        popup_edit_cut.addActionListener(edit_action);
        popup_edit_copy.addActionListener(edit_action);
        popup_edit_paste.addActionListener(edit_action);
        popup_edit_selectall.addActionListener(edit_action);
        popup_edit_user.addActionListener((e) -> {
        	int sel = _tabbedPane.getSelectedIndex();
        	
        	if(sel != -1) {
        		JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
        		textPane.insert(System.getProperty("user.name"), textPane.getCaretPosition());
        	}
        });        
        
        popup_edit_time.addActionListener((e) -> {
        	int sel = _tabbedPane.getSelectedIndex();
        	
        	if(sel != -1) {
        		JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
        		textPane.insert(new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), textPane.getCaretPosition());
        	}
        });
        popup_edit_changecase_upper.addActionListener(edit_action);
        popup_edit_changecase_lower.addActionListener(edit_action);
        popup_edit_changecase_sentence.addActionListener(edit_action);
        popup_view_font.addActionListener(view_action);

        //adding popup menu items to _popupMenu
        _popupMenu.add(popup_edit_cut);
        _popupMenu.add(popup_edit_copy);
        _popupMenu.add(popup_edit_paste);
        _popupMenu.addSeparator();        
        _popupMenu.add(popup_edit_selectall);
        _popupMenu.addSeparator();
        _popupMenu.add(popup_edit_user);
        _popupMenu.add(popup_edit_time);
        _popupMenu.addSeparator();
        _popupMenu.add(popup_edit_changecase);
        _popupMenu.addSeparator();
        _popupMenu.add(popup_view_font);



        //add window listener to TabbedNotepad frame
        parent.addWindowListener(new Load_Close_Frame_Action());
        
        // toolbar in ribbon
        // make it
        ribbon = new RibbonBar();
        
        // creating toolbar buttons
        JToolBar _toolbar = ribbon.tape.get("Main");
        
        JButton toolbar_new=new JButton(new ImageIcon(this.getClass().getResource("resources/new.png"), "New"));
        toolbar_new.setToolTipText("New (CTRL+N)");
        toolbar_new.addActionListener(new ToolBarButtonsAction("new"));
        
        JButton toolbar_newweb=new JButton(new ImageIcon(this.getClass().getResource("resources/web.png"), "New Web browser"));
        toolbar_newweb.setToolTipText("New Webkit web browser (CTRL+N+SHIFT)");
        toolbar_newweb.addActionListener((e) -> File_NewWeb_Action());

        JButton toolbar_open = new JButton(new ImageIcon(this.getClass().getResource("resources/open.png"), "Open"));
        toolbar_open.setToolTipText("Open (CTRL+O)");
        toolbar_open.addActionListener(new ToolBarButtonsAction("open"));
        
        JButton toolbar_open_webrq = new JButton(new ImageIcon(this.getClass().getResource("resources/webrq.png"), "Open From Web"));
        toolbar_open_webrq.setToolTipText("Open from websites with WebRequest");
        toolbar_open_webrq.addActionListener((e) -> {
        	Platform.runLater(new Runnable() {
        		@Override
        		public void run() {
        			File_Open_WebRq_Action();
        		}
        	});
        });

        JButton toolbar_save = new JButton(new ImageIcon(this.getClass().getResource("resources/save.png"), "Save"));
        toolbar_save.setToolTipText("Save (CTRL+S)");
        toolbar_save.addActionListener(new ToolBarButtonsAction("save"));

        JButton toolbar_saveas = new JButton(new ImageIcon(this.getClass().getResource("resources/saveas.png"), "Save As"));
        toolbar_saveas.setToolTipText("Save As (F2)");
        toolbar_saveas.addActionListener(new ToolBarButtonsAction("saveas"));

        JButton toolbar_font = new JButton(new ImageIcon(this.getClass().getResource("resources/font.png"), "Font"));
        toolbar_font.setToolTipText("Set Font (ALT+F)");
        toolbar_font.addActionListener(new ToolBarButtonsAction("font"));

        JButton toolbar_help = new JButton(new ImageIcon(this.getClass().getResource("resources/help.png"), "Help"));
        toolbar_help.setToolTipText("Help");

        //adding toolbar buttons to _toolbar object
        _toolbar.add(toolbar_new);
        _toolbar.add(toolbar_newweb);
        _toolbar.addSeparator(new Dimension(4,4));
        _toolbar.add(toolbar_open);
        _toolbar.add(toolbar_open_webrq);
        _toolbar.addSeparator(new Dimension(4,4));
        _toolbar.add(toolbar_save);
        _toolbar.add(toolbar_saveas);
        _toolbar.addSeparator(new Dimension(6, 6));
        _toolbar.add(toolbar_font);
        _toolbar.add(toolbar_help);
        
        ribbon.tape.put("Edit", new JToolBar());
        ribbon.add("Edit", ribbon.tape.get("Edit"));
        JToolBar _toolbar2 = ribbon.tape.get("Edit");
        
        JButton toolbar2_cut = new JButton(new ImageIcon(this.getClass().getResource("resources/cut.png"), "Cut"));
        toolbar2_cut.setToolTipText("Cut (CTRL+X)");
        toolbar2_cut.addActionListener(new ToolBarButtonsAction("cut"));

        JButton toolbar2_copy = new JButton(new ImageIcon(this.getClass().getResource("resources/copy.png"), "Copy"));
        toolbar2_copy.setToolTipText("Copy (CTRL+C)");
        toolbar2_copy.addActionListener(new ToolBarButtonsAction("copy"));

        JButton toolbar2_paste = new JButton(new ImageIcon(this.getClass().getResource("resources/paste.png"), "Paste"));
        toolbar2_paste.setToolTipText("Paste (CTRL+V)");
        toolbar2_paste.addActionListener(new ToolBarButtonsAction("paste"));

        JButton toolbar2_goto = new JButton(new ImageIcon(this.getClass().getResource("resources/goto.png"), "Goto"));
        toolbar2_goto.setToolTipText("GoTo (CTRL+G)");
        toolbar2_goto.addActionListener(new ToolBarButtonsAction("goto"));
        
        JButton toolbar2_undo = new JButton(new ImageIcon(this.getClass().getResource("resources/undo.png")));
        toolbar2_undo.setToolTipText("Undo (CTRL+Z)");
        toolbar2_undo.addActionListener(undoAction);
        
        JButton toolbar2_redo = new JButton(new ImageIcon(this.getClass().getResource("resources/redo.png")));
        toolbar2_redo.setToolTipText("Redo (CTRL+Z)");
        toolbar2_redo.addActionListener(redoAction);
        
        JButton toolbar2_user=new JButton(new ImageIcon(this.getClass().getResource("resources/user.png"), "User"));
        toolbar2_user.setToolTipText("Insert username");
        toolbar2_user.addActionListener((e) -> {
        	int sel = _tabbedPane.getSelectedIndex();
        	
        	if(sel != -1) {
        		JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
        		textPane.insert(System.getProperty("user.name"), textPane.getCaretPosition());
        	}
        });        
        
        JButton toolbar2_time=new JButton(new ImageIcon(this.getClass().getResource("resources/time.png"), "Time"));
        toolbar2_time.setToolTipText("Insert current timestamp");
        toolbar2_time.addActionListener((e) -> {
        	int sel = _tabbedPane.getSelectedIndex();
        	
        	if(sel != -1) {
        		JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
        		textPane.insert(new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss").format(Calendar.getInstance().getTime()), textPane.getCaretPosition());
        	}
        });    
        
        _toolbar2.add(toolbar2_cut);
        _toolbar2.add(toolbar2_copy);
        _toolbar2.add(toolbar2_paste);
        _toolbar2.add(toolbar2_goto);
        _toolbar2.addSeparator(new Dimension(6,6));
        _toolbar2.add(toolbar2_undo);
        _toolbar2.add(toolbar2_redo);
        _toolbar2.addSeparator(new Dimension(6,6));
        _toolbar2.add(toolbar2_user);
        _toolbar2.add(toolbar2_time);
        
        ribbon.updateRibbon();
        
        
        
        
        // add item to this
        ////////////////////////////////////////////////////////
        //set visibility to tool strip
        if (isToolStrip.equals("true")) {
            ribbon.setVisible(true);
            view_toolbar.setSelected(true);
        } else {
            ribbon.setVisible(false);
            view_toolbar.setSelected(false);
        }



        //**************************************************************
        //get content pane & adding ribbon,spanel & jsplit to it
        //***************************************************************
        Container cp = parent.getContentPane();
        
        // add the progressbar and status bar before adding the spanel
        GridBagLayout gbl = new GridBagLayout();
        spanel.setLayout(gbl);
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.weightx = gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        
        gbc.gridy = 0;
        
    	// old stream
    	PrintStream oldPrintErr = System.err;
    	
    	// remove errors
    	System.setErr(new PrintStream(OutputStream.nullOutputStream()));
        
        JFXPanel pane = new JFXPanel();
        
        // change the streams back
        System.setErr(oldPrintErr);
        assert System.err == oldPrintErr;
        
        spanel.add(pane, gbc);
        
        
        
        gbc.gridy = 1;
        
        spanel.add(statusBar, gbc);
        
        // add the progressbar to javafx
        progressBar = new ProgressBar(0);
        
        Scene scene = new Scene(progressBar);
        scene.getStylesheets().add(this.getClass().getResource("css/statusstrip.css").toString());
        
        pane.setScene(scene);
        
        cp.add(ribbon,BorderLayout.PAGE_START);
        cp.add(spanel,BorderLayout.SOUTH);
        cp.add(jsplit, BorderLayout.CENTER);
        
        // cp.add(this);
        
        new DropTarget(this, new FileDND());
        new DropTarget(parent, new FileDND());
        new DropTarget(jsplit, new FileDND());
        new DropTarget(ribbon, new FileDND());
        new DropTarget(spanel, new FileDND());
        

    }
    
    class FileDND implements DropTargetListener{

		@Override
		public void dragEnter(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void dragExit(DropTargetEvent dte) {
			// TODO Auto-generated method stub
			
		}

		@Override
        public synchronized void drop(DropTargetDropEvent dtde) {
			dtde.acceptDrop(DnDConstants.ACTION_COPY);
			
            try {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                java.util.List<File> droppedFiles = (java.util.List<File>)
                    dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                for (File filefs : droppedFiles) {
                    // process files
                	String filename=filefs.toString();
                    String file=filename;
                    if(filename.contains("\\")){
                        file = filename.substring(filename.lastIndexOf("\\") + 1);
                    }
                    else if(filename.contains("/")){
                        file = filename.substring(filename.lastIndexOf("/") + 1);
                    }
                    
                    StringBuilder sb = new StringBuilder();
                    BufferedReader d;
                    
                    d = new BufferedReader(new FileReader(filename));
                    String line;
                    while((line=d.readLine())!=null)
                             sb.append(line + "\n");
                    d.close();
                    
                    open_from_string(sb.toString(), file);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    	
    }
    
    
    /**
     * 
     */
    public void open_from_string(String str, String title) {
    	
        if (true)
        {
            //crerate textpane object
            JTextArea _textPane=new JTextArea(); new DropTarget(_textPane, new FileDND());

            _textPane.setFont(new Font("Calibri",Font.PLAIN,14));

            JScrollPane jsp=new JScrollPane(_textPane);
            // add key listener & Undoable edit listener to text pane
            _textPane.addKeyListener(new KeyTypedAction());
            _textPane.getDocument().addUndoableEditListener(_undoManager);
            //add tab to _tabbedPane with control textpane
            _tabbedPane.addTab(title + " ",jsp);
            //add caret listener & mouse listener to text pane
            _textPane.addCaretListener(new CaretAction());
            _textPane.addMouseListener(new TextPane_MouseAction());
            int index=_tabbedPane.getTabCount()-1;

            _tabbedPane.setSelectedIndex(index);

            // set save icon to added tab
            _tabbedPane.setIconAt(index, new ImageIcon(this.getClass().getResource("resources/save.png")));
            listModel.addElement(title);

           _list.setSelectedIndex(index);

           //change the title
            parent.setTitle("JEXTEdit - [ " + title + " ]");
            filenameLabel.setText(title);
            
            _textPane.setText(str);

            count++; 
        
          Platform.runLater(() -> progressBar.setProgress(0)); 


        }	
    }





//**************************************************************************
// class for action of Window menu
//**************************************************************************
class WindowMenuAction implements MenuListener
{
    @Override
    public void menuSelected(MenuEvent me) {
        if (_tabbedPane.getTabCount() > 0) {

            windowMenu.removeAll();

            windowMenu.addSeparator();

            int tabcount = _tabbedPane.getTabCount();
            String tabtext=_tabbedPane.getTitleAt(_tabbedPane.getSelectedIndex());
            for (int i = 0; i < tabcount; i++) {
                String title = _tabbedPane.getTitleAt(i);
                JCheckBoxMenuItem witem=new JCheckBoxMenuItem(title);
                witem.addActionListener(new Window_MenuItemsAction());
                if(tabtext.equals(title)){
                    witem.setSelected(true);
                }
                windowMenu.add(witem);
            }
        }
  }

    @Override
    public void menuDeselected(MenuEvent me) {
      }

    @Override
    public void menuCanceled(MenuEvent me) {
    }
}



//******************************************************
// Window menu item action
//******************************************************
class Window_MenuItemsAction implements ActionListener
{
        @Override
        public void actionPerformed(ActionEvent ae) {

            String menutext=ae.getActionCommand().trim();

            if (_tabbedPane.getTabCount() > 0) {
                int tabcount = _tabbedPane.getTabCount();
                for (int i = 0; i < tabcount; i++) {
                    String title = _tabbedPane.getTitleAt(i).trim();
                    if (title.contains("*")) {
                        title = title.replace("*", "").trim();
                    }

                    if (title.equals(menutext)) {
                        _tabbedPane.setSelectedIndex(i);
                        parent.setTitle("JEXTEdit - [ " + _tabbedPane.getTitleAt(_tabbedPane.getSelectedIndex()) + " ]");
                    }
                }
            }
        }

}




    //********************************************************
    // class for defining actions of file menu items
    //********************************************************
    class File_MenuItemsAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if(evt.getSource() instanceof JMenuItem)
            {
                String item=evt.getActionCommand().trim();

                switch(item)
                {
                    case "New" : File_New_Action(); break;
                    case "Open" : File_Open_Action();break;
                    case "Save" : File_Save_Action();break;
                    case "Save As" : File_SaveAs_Action();break;
                    case "Save All" : File_SaveAll_Action();break;
                    case "Close" : File_Close_Action();break;
                    case "Close All" : File_CloseAll_Action();break;
                    case"Open In System Editor" : File_OpenInSystemEditor_Action();break;
                    case "Exit" : File_Exit_Action();break;

                    case "Help Contents" :
                       HelpContentsAction hca=new HelpContentsAction();
                       hca.setTitle("Help Contents Action");
                       hca.setSize(660,450);
                       hca.setVisible(true);
                        break;

                    // adding Help -> About action here
                    case "About...." :
                        JOptionPane.showMessageDialog(null,"JEXTedit (Java-EXT Edit), a very advanced Java Text Editor. Cool?","About....",JOptionPane.INFORMATION_MESSAGE);
                        break;
                }
            }
        }
    }




    //********************************************************
    // class for defining actions of edit menu items
    //********************************************************
    class Edit_MenuItemsAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if (evt.getSource() instanceof JMenuItem)
            {
                String item = evt.getActionCommand().trim();
                switch (item)
                {
                    case "Cut" : Edit_Cut_Action();break;
                    case "Copy" : Edit_Copy_Action();break;
                    case "Paste" : Edit_Paste_Action();break;
                    case "GoTo" : Edit_GoTo_Action(); break;
                    case "Find": Edit_Find_Action();break;
                    case "Replace": Edit_Replace_Action();break;
                    case "Select All" : Edit_SelectAll_Action();break;
                    case "Upper Case" : Edit_ChangeCase_UpperCase_Action();break;
                    case "Lower Case" : Edit_ChangeCase_LowerCase_Action();break;
                    case "Sentence Case" : Edit_ChangeCase_SentenceCase_Action();break;
                    case "Next Document" : Edit_NextDocument_Action();break;
                    case "Previous Document" : Edit_PreviousDocument_Action();break;
                }
            }
        }
    }





    //********************************************************
    // class for defining actions of view menu items
    //********************************************************
    class View_MenuItemsAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if (evt.getSource() instanceof JMenuItem)
            {
                String item = evt.getActionCommand().trim();
                switch (item)
                {
                    case "Font" : View_Font_Action(); break;
                    case "Fore Color" : View_ForeColor_Action();break;
                    case "Back Color" : View_BackColor_Action();break;
                    case "Top" : View_TabsAlignment_Action("top");break;
                    case "Bottom" : View_TabsAlignment_Action("bottom");break;
                    case "Left" : View_TabsAlignment_Action("left");break;
                    case "Right" : View_TabsAlignment_Action("right");break;
                }
            }
        }
    }




    //********************************************************
    // class for defining actions of Run menu items
    //********************************************************
    class Run_MenuItemsAction implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if (evt.getSource() instanceof JMenuItem)
            {
                String item = evt.getActionCommand().trim();
                switch (item)
                {
                    case "Run" :
                        JDialog ra=new RunAction();
                        ra.setTitle("Run");
                        ra.setModal(true);
                        ra.setSize(450,120);
                        ra.setResizable(false);
                        ra.setLocation(getCenterPoints().x+100,getCenterPoints().y+80);
                        ra.setVisible(true);
                        break;

                    case "Run in Browser" :
                        if(_tabbedPane.getTabCount()>0)
                        {
                            if(filenameLabel.getText().contains("\\") || filenameLabel.getText().contains("/"))
                            {
                                String file=filenameLabel.getText();
                                file=file.replace("\\", "/");
                                file="file:///"+file;
                                try
                                {
                                  Desktop dt=Desktop.getDesktop();
                                  dt.browse(new URI(file));
                                }
                                catch(URISyntaxException | IOException e) {}
                            }
                        }
                        break;

                    case "Google Search" :
                        try {
                            Desktop d = Desktop.getDesktop();
                            d.browse(new URI("https://www.google.com"));
                        } catch (URISyntaxException | IOException Ex) {
                        }
                        break;
                }
            }
        }
    }




    //********************************************************
    // tool abr button action class
    //********************************************************
    class ToolBarButtonsAction implements ActionListener
    {
        String type="";
        public ToolBarButtonsAction(String s)
        {
            type=s;
        }

        @Override
        public void actionPerformed(ActionEvent evt)
        {
            switch(type)
            {
                case "new" : File_New_Action();break;
                case "open" : File_Open_Action();break;
                case "save" : File_Save_Action();break;
                case "saveas" : File_SaveAs_Action();break;
                case "cut" : Edit_Cut_Action();break;
                case "copy" : Edit_Copy_Action();break;
                case "paste" : Edit_Paste_Action();break;
                case "goto" : Edit_GoTo_Action();break;
                case "font" : View_Font_Action();break;
            }
        }
    }




    //************************************************************
    // set items enable to false if tabcount=0 to menu items
    //************************************************************
    public class Menus_MenuListener implements MenuListener
    {
        JMenuItem[] list;

        Menus_MenuListener(JMenuItem[] lst)
        {
            list=lst;
        }

        @Override
        public void menuCanceled(MenuEvent ev){}
        @Override
        public void menuDeselected(MenuEvent ev){}
        @Override
        public void menuSelected(MenuEvent evt)
        {
            if(_tabbedPane.getTabCount()>0)
            {
                for(JMenuItem item : list)
                {
                    item.setEnabled(true);
                }
            }
            else
            {
                for (JMenuItem item : list)
                {
                    item.setEnabled(false);
                }
            }
        }
    }




    //********************************************************
    // showing popupMenu on textpane
    //********************************************************
    class TextPane_MouseAction extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent evt)
        {
            if(evt.isPopupTrigger())
            {
                _popupMenu.show(evt.getComponent(),evt.getX(),evt.getY());
            }
        }
    }




    //********************************************************
    // display row & col
    //********************************************************
    class CaretAction implements CaretListener
    {
        public int getRow(int pos,JTextArea textpane)
        {
            int rn=(pos==0) ? 1:0;
            try
            {
                int offs=pos;
                while(offs>0)
                {
                    offs=Utilities.getRowStart(textpane, offs)-1;
                    rn++;
                }
            }
            catch(BadLocationException e){ e.printStackTrace();}

            return rn;
        }

        public int getColumn(int pos,JTextArea textpane)
        {
            try
            {
                return pos-Utilities.getRowStart(textpane, pos)+1;
            }
            catch (BadLocationException e) {e.printStackTrace();  }

            return -1;
        }

        @Override
        public void caretUpdate(CaretEvent evt)
        {
            JTextArea textpane=(JTextArea)evt.getSource();
            int row = getRow(evt.getDot(), textpane);
            int col = getColumn(evt.getDot(), textpane);
            rowLabel.setText("Row : "+row);
            colLabel.setText("Col : "+col);
        }
    }




    /////////////////////////////////////////////////////////////////////////////
    //**************************************************************************
    // functions
    //***************************************************************************
    /////////////////////////////////////////////////////////////////////////////


    //********************************************************
    // File -> New action
    //********************************************************
    public void File_New_Action()
    {
        //crerate textpane object
         JTextArea _textPane=new JTextArea(); new DropTarget(_textPane, new FileDND());

         _textPane.setFont(new Font("Calibri",Font.PLAIN,14));

         JScrollPane jsp=new JScrollPane(_textPane);
         // add key listener & Undoable edit listener to text pane
         _textPane.addKeyListener(new KeyTypedAction());
         _textPane.getDocument().addUndoableEditListener(_undoManager);
         //add tab to _tabbedPane with control textpane
         _tabbedPane.addTab("Document "+count+" ",jsp);
         //add caret listener & mouse listener to text pane
         _textPane.addCaretListener(new CaretAction());
         _textPane.addMouseListener(new TextPane_MouseAction());
         int index=_tabbedPane.getTabCount()-1;

         _tabbedPane.setSelectedIndex(index);

         // set save icon to added tab
         _tabbedPane.setIconAt(index, new ImageIcon(this.getClass().getResource("resources/save.png")));
         listModel.addElement("Document "+count+" ");

        _list.setSelectedIndex(index);

        //change the title
         parent.setTitle("JEXTEdit - [ Document "+count+" ]");
         filenameLabel.setText("Document "+count);

         count++;

    }
    //********************************************************
    // File -> New Web Browser action
    //********************************************************
    public void File_NewWeb_Action()
    {
    	// old stream
    	PrintStream oldPrintErr = System.err;
    	Platform.runLater(() -> progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS));    	
    	// remove errors
    	// System.setErr(new PrintStream(OutputStream.nullOutputStream())); 
        //create JFXWeb object
    	
    	CountDownLatch l = new CountDownLatch(1);
    	JEmbeddedWeb[] web = {null};
    	try {
        	web[0] = new JEmbeddedWeb();
        	throw new IllegalStateException("ded");
        }
        catch(IllegalStateException ise) {
        	
        }
        
    	// JFXWeb web = new JFXWeb();
         
         // change the streams back
         System.setErr(oldPrintErr);
         assert System.err == oldPrintErr;
         
         //add tab to _tabbedPane with control textpane
         _tabbedPane.addTab("WebDocument "+webcount+" ",web[0]);
         int index=_tabbedPane.getTabCount()-1;

         _tabbedPane.setSelectedIndex(index);
         _tabbedPane.setIconAt(index, new ImageIcon(this.getClass().getResource("resources/web.png")));
         listModel.addElement("WebDocument "+webcount+" ");

        _list.setSelectedIndex(index);

        //change the title
         parent.setTitle("JEXTEdit - [ WebDocument "+webcount+" ]");

         webcount++;
         
         Platform.runLater(() -> progressBar.setProgress(0)); 
    }


    //********************************************************
    // File -> Open action
    //********************************************************
    public void File_Open_Action()
    {
    	
         FileDialog fd = new FileDialog(new JFrame(), "Select File",FileDialog.LOAD);
         fd.setMultipleMode(true);
         fd.show();
         if (fd.getFiles()!=null)
         {
            File[] files=fd.getFiles();
            for(File item : files)
            {
               String  filename = item.toString();
               String file=filename;
               if(filename.contains("\\")){
                   file = filename.substring(filename.lastIndexOf("\\") + 1);
               }
               else if(filename.contains("/")){
                   file = filename.substring(filename.lastIndexOf("/") + 1);
               }

               int count=_tabbedPane.getTabCount();

               JTextArea _textPane=new JTextArea(); new DropTarget(_textPane, new FileDND());
               _textPane.setFont(new Font("Calibri",Font.PLAIN,14));

               JScrollPane jsp=new JScrollPane(_textPane);
               _textPane.addKeyListener(new KeyTypedAction());
                _textPane.getDocument().addUndoableEditListener(_undoManager);
                _textPane.addCaretListener(new CaretAction());
                _textPane.addMouseListener(new TextPane_MouseAction());
               _tabbedPane.addTab(file,jsp);
               _tabbedPane.setSelectedIndex(count);
               _tabbedPane.setIconAt(count, new ImageIcon(this.getClass().getResource("resources/save.png")));
               listModel.addElement(file);
               _list.setSelectedIndex(count);

               parent.setTitle("JEXTEdit - [ "+file+" ]");
               filenameLabel.setText(filename);
               filesHoldListModel.addElement(filename);
               
               Platform.runLater(() -> progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS)); 
               BufferedReader d;
               StringBuffer sb = new StringBuffer();
               try
                {
                  d = new BufferedReader(new FileReader(filename));
                  String line;
                  while((line=d.readLine())!=null)
                           sb.append(line + "\n");
                           _textPane.setText(sb.toString());
                  d.close();
                }
               catch(FileNotFoundException fe)
                {
                   System.out.println("File not Found");
                }
                 catch(IOException ioe){}

                  _textPane.requestFocus();

               }
           }
         
         Platform.runLater(() -> progressBar.setProgress(0)); 

    }


    //********************************************************
    // File -> Open From Web action
    //********************************************************
    public void File_Open_WebRq_Action()
    {
    	String url = null;
    	
    	TextInputDialog dialog = new TextInputDialog("https://google.com");
    	
    	dialog.setTitle("WebRequest download");
    	dialog.setHeaderText("WebRq");
    	
    	Optional<String> result = dialog.showAndWait(); 
    	
    	if(result.isPresent()) {
    		url = result.get();
    	}
    	
    	// String url = JOptionPane.showInputDialog(this, "What URL do you want to open?", "https://google.com");
    	
         if (url!=null)
         {
               String  filename = url;
               String file=filename;
               if(filename.contains("\\")){
                   file = filename.substring(filename.lastIndexOf("\\") + 1);
               }
               else if(filename.contains("/")){
                   file = filename.substring(filename.lastIndexOf("/") + 1);
               }

               int count=_tabbedPane.getTabCount();

               JTextArea _textPane=new JTextArea(); new DropTarget(_textPane, new FileDND());
               _textPane.setFont(new Font("Calibri",Font.PLAIN,14));

               JScrollPane jsp=new JScrollPane(_textPane);
               _textPane.addKeyListener(new KeyTypedAction());
                _textPane.getDocument().addUndoableEditListener(_undoManager);
                _textPane.addCaretListener(new CaretAction());
                _textPane.addMouseListener(new TextPane_MouseAction());
               _tabbedPane.addTab(file,jsp);
               _tabbedPane.setSelectedIndex(count);
               _tabbedPane.setIconAt(count, new ImageIcon(this.getClass().getResource("resources/save.png")));
               listModel.addElement(file);
               _list.setSelectedIndex(count);

               parent.setTitle("JEXTEdit - [ "+file+" ]");
               filenameLabel.setText(filename);
               filesHoldListModel.addElement(filename);
               
               Platform.runLater(() -> progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS)); 
               
               java.net.http.HttpResponse<String> c = null;
               
               	try {
               		c = WebHelper.getHTTPResponse(url);
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               
               if(c == null) {
            	   _textPane.setText("");
               }
               else {
            	   _textPane.setText(c.body());
               }
         
         Platform.runLater(() -> progressBar.setProgress(0)); 

         }
    }



    //********************************************************
    // File -> Save action
    //********************************************************
    public void File_Save_Action()
    {
    	
         if(_tabbedPane.getTabCount()>0)
         {
            String filename=filenameLabel.getText();
            int sel=_tabbedPane.getSelectedIndex();
            
            try{
            	(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            }
            catch(java.lang.ClassCastException cce) {
            	return;
            }
            
            JTextArea textPane=(JTextArea)(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            if(filename.contains("\\")||filename.contains("/"))
            {
              File f=new File(filename);
              if(f.exists())
              {
            	  Platform.runLater(() -> progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS)); 
                  try
                  {
                       DataOutputStream d = new DataOutputStream(new FileOutputStream(filename));
                       String line = textPane.getText();
                       d.writeBytes(line);
                       d.close();

                       String tabtext=_tabbedPane.getTitleAt(sel);
                       if(tabtext.contains("*"))
                       {
                           tabtext=tabtext.replace("*", "");
                           _tabbedPane.setTitleAt(sel, tabtext);
                           parent.setTitle("JEXTEdit - [ "+tabtext+" ]");
                           _tabbedPane.setIconAt(sel,new ImageIcon(this.getClass().getResource("resources/save.png")));
                       }

                  }
                 catch(Exception ex)
                  {
                           System.out.println("File not found");
                  }
                  textPane.requestFocus();
                }
           }

            else if(filename.contains("Document "))
            {
                    File_SaveAs_Action();
            }

         }Platform.runLater(() -> progressBar.setProgress(0)); 
         
    }




    //********************************************************
    // File -> Save As action
    //********************************************************
    public void File_SaveAs_Action()
    {
    	
        if (_tabbedPane.getTabCount() > 0)
        {
        	
        	int sel = _tabbedPane.getSelectedIndex();
        	
            try{
            	(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            }
            catch(java.lang.ClassCastException cce) {
            	return;
            }
            
            FileDialog fd = new FileDialog(new JFrame(), "Save File", FileDialog.SAVE);
            fd.show();
            if (fd.getFile() != null)
            {
            	Platform.runLater(() -> progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS)); 
                String filename = fd.getDirectory() + fd.getFile();
                JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
                try
                {
                    DataOutputStream d = new DataOutputStream(new FileOutputStream(filename));
                    String line = textPane.getText();
                    d.writeBytes(line);
                    d.close();

                    filesHoldListModel.addElement(filename);
                    filenameLabel.setText(filename);

                    String file = filename.substring(filename.lastIndexOf("\\") + 1);
                    _tabbedPane.setTitleAt(sel, file);

                    _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/save.png")));

                    parent.setTitle("JEXTEdit - [ " + file + " ]");

                }
                catch (Exception ex)
                {
                    System.out.println("File not found");
                }
                textPane.requestFocus();

            }
        }
        Platform.runLater(() -> progressBar.setProgress(0)); 
    }




    //********************************************************
    // File -> Save All action
    //********************************************************
    public void File_SaveAll_Action()
    {
    	
        if (_tabbedPane.getTabCount() > 0)
        {
            int maxindex = _tabbedPane.getTabCount() - 1;
            for (int i = 0; i <= maxindex; i++)
            {
                _tabbedPane.setSelectedIndex(i);
                String filename = filenameLabel.getText();
                int sel = _tabbedPane.getSelectedIndex();
                JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
                if (filename.contains("\\")||filename.contains("/"))
                {
                    File f = new File(filename);
                    if (f.exists())
                    {
                        try
                        {
                        	Platform.runLater(() -> progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS)); 
                            DataOutputStream d = new DataOutputStream(new FileOutputStream(filename));
                            String line = textPane.getText();
                            d.writeBytes(line);
                            d.close();

                            String tabtext = _tabbedPane.getTitleAt(sel);
                            if (tabtext.contains("*")) {
                                tabtext = tabtext.replace("*", "");
                                _tabbedPane.setTitleAt(sel, tabtext);
                                parent.setTitle("JEXTEdit - [ " + tabtext + " ]");
                                _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/save.png")));
                            }

                        }
                        catch (Exception ex)
                        {
                            System.out.println("File not found");
                        }
                        textPane.requestFocus();
                    }
                }

            }
        }
        
        Platform.runLater(() -> progressBar.setProgress(0)); 
    }





    //********************************************************
    // File -> Close action
    //********************************************************
    public void File_Close_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            String tabtext = _tabbedPane.getTitleAt(sel);

            if (tabtext.contains("*"))
            {
                int n = JOptionPane.showConfirmDialog(null, "Do you want to save " + tabtext + " before close ?",
                        "Save or Not", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                tabtext.replace("*", "");

                if (n == 0)
                {
                    String filename = filenameLabel.getText();
                    JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

                    if (filename.contains("\\")||filename.contains("/"))
                    {
                        File_Save_Action();

                        _tabbedPane.remove(sel);
                        listModel.removeAllElements();

                        //adding all elements to list after removing the tab
                        for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                        {
                            String item = _tabbedPane.getTitleAt(i);
                            if (item.contains("*"))
                            {
                                item = item.replace("*", "").trim();
                            }

                            listModel.addElement(item);
                        }

                        _list.setSelectedIndex(_tabbedPane.getTabCount()-1);

                        rowLabel.setText("Row :");
                        colLabel.setText("Col :");

                        if(_tabbedPane.getTabCount()==0)
                        {
                            parent.setTitle("JEXTEdit");
                            filenameLabel.setText("");
                            rowLabel.setText("Row :");
                            colLabel.setText("Col :");
                        }

                    }

                    else if (filename.contains("Document "))
                    {
                        File_SaveAs_Action();

                        _tabbedPane.remove(sel);
                        listModel.removeAllElements();

                        //adding all elements to list after removing the tab
                        for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                        {
                            String item = _tabbedPane.getTitleAt(i);
                            if (item.contains("*"))
                            {
                                item = item.replace("*", "").trim();
                            }

                            listModel.addElement(item);
                        }

                        _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                        rowLabel.setText("Row :");
                        colLabel.setText("Col :");

                        if (_tabbedPane.getTabCount() == 0)
                        {
                            parent.setTitle("JEXTEdit");
                            filenameLabel.setText("");
                            rowLabel.setText("Row :");
                            colLabel.setText("Col :");
                        }
                    }

                }

                if (n == 1)
                {
                    _tabbedPane.remove(sel);
                    listModel.removeAllElements();

                    //adding all elements to list after removing the tab
                    for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                    {
                        String item = _tabbedPane.getTitleAt(i);
                        if (item.contains("*"))
                        {
                            item = item.replace("*", "").trim();
                        }

                        listModel.addElement(item);
                    }

                    _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                    rowLabel.setText("Row :");
                    colLabel.setText("Col :");

                    if (_tabbedPane.getTabCount() == 0)
                    {
                        parent.setTitle("JEXTEdit");
                        filenameLabel.setText("");
                        rowLabel.setText("Row :");
                        colLabel.setText("Col :");
                    }
                }
            }

            else
            {
                _tabbedPane.remove(sel);
                listModel.removeAllElements();

                //adding all elements to list after removing the tab
                for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                {
                    String item = _tabbedPane.getTitleAt(i);
                    if (item.contains("*"))
                    {
                        item = item.replace("*", "").trim();
                    }

                    listModel.addElement(item);
                }

                _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                rowLabel.setText("Row :");
                colLabel.setText("Col :");

                if (_tabbedPane.getTabCount() == 0)
                {
                    parent.setTitle("JEXTEdit");
                    filenameLabel.setText("");
                    rowLabel.setText("Row :");
                    colLabel.setText("Col :");
                }

            }
        }

        else
        {
            parent.setTitle("JEXTEdit");
            filenameLabel.setText("");
            rowLabel.setText("Row :");
            colLabel.setText("Col :");

        }
    }






    //********************************************************
    // File -> Close All action
    //********************************************************
    public void File_CloseAll_Action() throws IndexOutOfBoundsException
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            for(int j=0;j<_tabbedPane.getTabCount();j++)
            {
            _tabbedPane.setSelectedIndex(j);
            int sel=_tabbedPane.getSelectedIndex();
            String tabtext = _tabbedPane.getTitleAt(sel);

            if (tabtext.contains("*"))
            {
                int n = JOptionPane.showConfirmDialog(null, "Do you want to save " + tabtext + " before close ?",
                        "Save or Not", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                tabtext.replace("*", "");

                if (n == 0)
                {
                    String filename = filenameLabel.getText();
                    JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

                    if (filename.contains("\\")||filename.contains("/"))
                    {
                        File_Save_Action();

                        _tabbedPane.remove(sel);
                        listModel.removeAllElements();

                        //adding all elements to list after removing the tab
                        for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                        {
                            String item = _tabbedPane.getTitleAt(i);
                            if (item.contains("*"))
                            {
                                item = item.replace("*", "").trim();
                            }

                            listModel.addElement(item);
                        }

                        _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                        File_CloseAll_Action();

                        rowLabel.setText("Row :");
                        colLabel.setText("Col :");
                    }
                    else if (filename.contains("Document "))
                    {
                        File_SaveAs_Action();

                        _tabbedPane.remove(sel);
                        listModel.removeAllElements();

                        //adding all elements to list after removing the tab
                        for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                        {
                            String item = _tabbedPane.getTitleAt(i);
                            if (item.contains("*"))
                            {
                                item = item.replace("*", "").trim();
                            }

                            listModel.addElement(item);
                        }

                        _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                        File_CloseAll_Action();

                        rowLabel.setText("Row :");
                        colLabel.setText("Col :");
                    }

                }

                if (n == 1)
                {
                    _tabbedPane.remove(sel);
                    listModel.removeAllElements();

                    //adding all elements to list after removing the tab
                    for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                    {
                        String item = _tabbedPane.getTitleAt(i);
                        if (item.contains("*"))
                        {
                            item = item.replace("*", "").trim();
                        }

                        listModel.addElement(item);
                    }

                    _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                    File_CloseAll_Action();

                    rowLabel.setText("Row :");
                    colLabel.setText("Col :");
                }
            }

            else
            {
                _tabbedPane.remove(sel);
                listModel.removeAllElements();

                //adding all elements to list after removing the tab
                for (int i = 0; i < _tabbedPane.getTabCount(); i++)
                {
                    String item = _tabbedPane.getTitleAt(i);
                    if (item.contains("*"))
                    {
                        item = item.replace("*", "").trim();
                    }

                    listModel.addElement(item);
                }

               // _list.setSelectedIndex(_tabbedPane.getTabCount() - 1);

                File_CloseAll_Action();

                rowLabel.setText("Row :");
                colLabel.setText("Col :");
            }
           }
        }

        else
        {
            parent.setTitle("JEXTEdit");
            filenameLabel.setText("");

            rowLabel.setText("Row :");
            colLabel.setText("Col :");
        }
    }




    //********************************************************
    // File -> Open in System Editor
    //********************************************************
    public void File_OpenInSystemEditor_Action()
    {
        if(_tabbedPane.getTabCount()>0)
        {
            String filename=filenameLabel.getText();
            if(filename.contains("\\")||filename.contains("/"))
            {
                try
                {
                 Desktop d=Desktop.getDesktop();
                 d.open(new File(filename));
                }
                catch(Exception e){e.printStackTrace();}
            }
        }
    }




    //********************************************************
    // File -> Exit
    //********************************************************
    public void File_Exit_Action()
    {
        File_CloseAll_Action();
        if (_tabbedPane.getTabCount() == 0)
        {
            parent.dispose();
        }
    }




    //********************************************************
    // Edit -> Cut
    //********************************************************
    public void Edit_Cut_Action()
    {
        if(_tabbedPane.getTabCount()>0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            try{
            	(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            }
            catch(java.lang.ClassCastException cce) {
            	return;
            }
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            String selected_text = textPane.getSelectedText();
            StringSelection ss = new StringSelection(selected_text);
            clip.setContents(ss, ss);
            textPane.replaceSelection("");

            String tabtext = _tabbedPane.getTitleAt(sel);
            if (tabtext.contains("*"))
            {  }
            else
            {
                _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel) + "*");
                _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
            }
        }
    }



    //********************************************************
    // Edit -> Copy
    //********************************************************
    public void Edit_Copy_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            try{
            	(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            }
            catch(java.lang.ClassCastException cce) {
            	return;
            }
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            String selected_text = textPane.getSelectedText();
            StringSelection ss = new StringSelection(selected_text);
            clip.setContents(ss, ss);

        }
    }



    //********************************************************
    // Edit -> Paste
    //********************************************************
    public void Edit_Paste_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            try{
            	(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            }
            catch(java.lang.ClassCastException cce) {
            	return;
            }
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            Transferable cliptran = clip.getContents(EditorWindow.this);
            try
            {
                String selected_text = (String) cliptran.getTransferData(DataFlavor.stringFlavor);
                textPane.replaceSelection(selected_text);

                // here you can direct use textPane.paste();

                String tabtext = _tabbedPane.getTitleAt(sel);
                if (tabtext.contains("*")) { }
                else
                {
                    _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel) + "*");
                    _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
                }
            }
            catch (Exception exc)
            {
                System.out.println("error to paste");
            }
        }
    }




    //********************************************************
    // Edit -> GoTo
    //********************************************************
    public void Edit_GoTo_Action()
    {
        if(_tabbedPane.getTabCount()>0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            try{
            	(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            }
            catch(java.lang.ClassCastException cce) {
            	return;
            }
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

            do
            {
                try
                {
                    String str = (String) JOptionPane.showInputDialog(null,"Enter Line number :  "+"(1 - "+getLineCount(textPane)+" )", "GoTo Line",JOptionPane.PLAIN_MESSAGE, null, null, null);
                    if (str == null)
                    {
                        break;
                    }

                    int lineNumber = Integer.parseInt(str);
                    _lineCount = getLineCount(textPane);
                    if (lineNumber > _lineCount)
                    {
                        JOptionPane.showMessageDialog(null,"Line number out of range", "Error....",JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    textPane.setCaretPosition(0);
                    textPane.setCaretPosition(SetCursor(lineNumber,textPane));
                    return;
                }
                catch (Exception e) { }
            }
            while (true);
        }
    }

    int _lineCount;
    public int getLineCount(JTextArea textPane)
    {
        _lineCount = 0;
        Scanner scanner = new Scanner(textPane.getText());
        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            _lineCount++;
        }
        return _lineCount;
    }

    public int SetCursor(int newlineno,JTextArea textPane)
    {
        int pos = 0;
        int i = 0;
        String line = "";
        Scanner scanner = new Scanner(textPane.getText());
        while (scanner.hasNextLine())
        {
            line = scanner.nextLine();
            i++;
            if (newlineno > i)
            {
                pos = pos + line.length() + 1;
            }
        }
        return pos;
    }




    //********************************************************
    // Edit -> Find
    //********************************************************
    public void Edit_Find_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            try{
            	(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            }
            catch(java.lang.ClassCastException cce) {
            	return;
            }
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            String input=(String) JOptionPane.showInputDialog(null,"Enter Text to Find :  ", "Find",JOptionPane.PLAIN_MESSAGE, null, null, null);
            if(input!=null)
            {
              int start = textPane.getText().indexOf(input);
              int end = input.length();
              if (start >= 0 && end > 0)
              {
                  textPane.select(start, start + end);
              }
            }
        }
    }




    //********************************************************
    // Edit -> Replace
    //********************************************************
    JTextField findText;
    JTextField replaceText;
    JButton replaceButton;
    JButton cancelButton;
    JDialog jd;
    public void Edit_Replace_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
        	int sel = _tabbedPane.getSelectedIndex();
            try{
            	(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            }
            catch(java.lang.ClassCastException cce) {
            	return;
            }
            jd=new JDialog(new JDialog(),true);
            jd.setSize(360,120);
            jd.setLocation(this.getCenterPoints().x+150,this.getCenterPoints().y+130);
            jd.setResizable(false);
            jd.setTitle("Replace");

            JPanel jp1=new JPanel();
            JPanel jp2=new JPanel();
            JLabel findwhat=new JLabel("Find What    :    ");
            JLabel replacewith=new JLabel("Replace With : ");
            findText=new JTextField(20);
            replaceText=new JTextField(20);

            replaceButton=new JButton("Replace All");
            cancelButton=new JButton("Cancel");

            replaceButton.addActionListener(new ReplaceText_Action());
            cancelButton.addActionListener(new ReplaceText_Action());

            jp1.add(findwhat);
            jp1.add(findText);
            jp1.add(replacewith);
            jp1.add(replaceText);
            jp2.add(replaceButton);
            jp2.add(cancelButton);

            jd.add(jp1,BorderLayout.CENTER);
            jd.add(jp2,BorderLayout.SOUTH);

            jd.show();
        }
    }

    class ReplaceText_Action implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            Object source=evt.getSource();
            if(source==replaceButton)
            {
                int sel = _tabbedPane.getSelectedIndex();
                JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

                String find=findText.getText();
                String replace=replaceText.getText();

                textPane.setText(textPane.getText().replaceAll(find, replace));

                String tabtext = _tabbedPane.getTitleAt(sel);
                if (tabtext.contains("*"))
                { }
                else
                {
                    _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel) + "*");
                    _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
                }
            }
            else if(source==cancelButton)
            {
                jd.dispose();
            }
        }
    }




    //********************************************************
    // Edit -> Select All
    //********************************************************
    public void Edit_SelectAll_Action()
    {
        if(_tabbedPane.getTabCount()>0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            try{
            	(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            }
            catch(java.lang.ClassCastException cce) {
            	return;
            }
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

            textPane.selectAll();
        }
    }




    //********************************************************
    // Edit -> Change Case -> Upper Case
    //********************************************************
    public void Edit_ChangeCase_UpperCase_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            try{
            	(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            }
            catch(java.lang.ClassCastException cce) {
            	return;
            }
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

            if(textPane.getSelectedText()!=null)
            {
               textPane.replaceSelection(textPane.getSelectedText().toUpperCase());

                String tabtext = _tabbedPane.getTitleAt(sel);
                if (tabtext.contains("*"))
                {  }
                else
                {
                    _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel) + "*");
                    _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
                }
            }
        }
    }



    //********************************************************
    // Edit -> Change Case -> Lower Case
    //********************************************************
    public void Edit_ChangeCase_LowerCase_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            try{
            	(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            }
            catch(java.lang.ClassCastException cce) {
            	return;
            }
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

            if (textPane.getSelectedText() != null)
            {
                textPane.replaceSelection(textPane.getSelectedText().toLowerCase());

                String tabtext = _tabbedPane.getTitleAt(sel);
                if (tabtext.contains("*"))
                { }
                else
                {
                    _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel) + "*");
                    _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
                }
            }
        }
    }



    //********************************************************
    // Edit -> Change Case -> Sentence Case
    //********************************************************
    public void Edit_ChangeCase_SentenceCase_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            try{
            	(((JScrollPane)_tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);
            }
            catch(java.lang.ClassCastException cce) {
            	return;
            }
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

            if (textPane.getSelectedText() != null)
            {
                String s=textPane.getSelectedText();
                char ch=s.charAt(0);
                String ss=String.valueOf(ch).toUpperCase();
                String str=s.substring(1);
                str=ss+str;
                textPane.replaceSelection(str);

                String tabtext = _tabbedPane.getTitleAt(sel);
                if (tabtext.contains("*")) { }
                else
                {
                    _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel) + "*");
                    _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
                }
            }
        }
    }



    //********************************************************
    // Edit -> Next Document
    //********************************************************
    public void Edit_NextDocument_Action() throws IndexOutOfBoundsException
    {
        if(_tabbedPane.getTabCount()>0)
        {
            int tabindex=_tabbedPane.getTabCount()-1;
            if(_tabbedPane.getSelectedIndex()==tabindex)
            { }
           else if(_tabbedPane.getSelectedIndex()<tabindex)
            {
                _tabbedPane.setSelectedIndex(_tabbedPane.getSelectedIndex()+1);
                _list.setSelectedIndex(tabindex);
            }
        }
    }




    //********************************************************
    // Edit -> Previous Document
    //********************************************************
    public void Edit_PreviousDocument_Action() throws IndexOutOfBoundsException
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int tabcount = _tabbedPane.getTabCount();
            if(_tabbedPane.getSelectedIndex()==0){ }
            else
            {
                _tabbedPane.setSelectedIndex(_tabbedPane.getSelectedIndex()-1);
                _list.setSelectedIndex(tabcount);
            }
        }
    }





    //********************************************************
    // View -> Font
    //********************************************************
    public void View_Font_Action()
    {
        if(_tabbedPane.getTabCount()>0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

            JDialog fa = new FontAction(textPane);
            fa.setTitle("Set Font");
            fa.setSize(540, 300);
            fa.setModal(true);
            fa.setLocation(this.getCenterPoints());
            fa.setResizable(false);
            fa.setAlwaysOnTop(true);
            fa.setVisible(true);
        }
    }



    //********************************************************
    // View -> Fore Color
    //********************************************************
    public void View_ForeColor_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

            ForeBackColorAction.ForeColor_Action fra=new ForeBackColorAction.ForeColor_Action(textPane);
            fra.setTitle("Set Fore Color");
            fra.setModal(true);
            fra.setSize(540, 300);
            fra.setLocation(this.getCenterPoints());
            fra.setResizable(false);
            fra.setAlwaysOnTop(true);
            fra.setVisible(true);
        }
    }




    //********************************************************
    // View -> Back Color
    //********************************************************
    public void View_BackColor_Action()
    {
        if (_tabbedPane.getTabCount() > 0)
        {
            int sel = _tabbedPane.getSelectedIndex();
            JTextArea textPane = (JTextArea) (((JScrollPane) _tabbedPane.getComponentAt(sel)).getViewport()).getComponent(0);

            ForeBackColorAction.BackColor_Action bra = new ForeBackColorAction.BackColor_Action(textPane);
            bra.setTitle("Set Back Color");
            bra.setModal(true);
            bra.setSize(540, 300);
            bra.setLocation(this.getCenterPoints());
            bra.setResizable(false);
            bra.setAlwaysOnTop(true);
            bra.setVisible(true);
        }
    }



    //********************************************************
    // Tabs alignment action
    //********************************************************
    public void View_TabsAlignment_Action(String type)
    {
        switch (type) {
            case "top":
                _tabbedPane.setTabPlacement(JTabbedPane.TOP);
                ReplaceViewsFileNodeText("tabsAlignment", getNodeTextContent("tabsAlignment"), "Top");
                break;
            case "bottom":
                _tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
                ReplaceViewsFileNodeText("tabsAlignment", getNodeTextContent("tabsAlignment"), "Bottom");
                break;
            case "left":
                _tabbedPane.setTabPlacement(JTabbedPane.LEFT);
                ReplaceViewsFileNodeText("tabsAlignment", getNodeTextContent("tabsAlignment"), "Left");
                break;
            case "right":
                _tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
                ReplaceViewsFileNodeText("tabsAlignment", getNodeTextContent("tabsAlignment"), "Right");
                break;
        }
    }




    //********************************************************
    //  action
    //********************************************************
    public class View_DocumentPane_Action implements ActionListener
    {
        JCheckBoxMenuItem jcbmi;
        public View_DocumentPane_Action(JCheckBoxMenuItem jcm)
        {
            jcbmi=jcm;
        }

        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if(jcbmi.isSelected())
            {
                JTabbedPane jtb = (JTabbedPane) jsplit.getLeftComponent();
                jtb.setVisible(true);
                jsplit.setDividerLocation(210);

                ReplaceViewsFileNodeText("documentpane","false","true");
            }
            else
            {
                JTabbedPane jtb = (JTabbedPane) jsplit.getLeftComponent();
                jtb.setVisible(false);
                ReplaceViewsFileNodeText("documentpane","true","false");
            }
        }
    }




    //********************************************************
    // Tool bar action
    //********************************************************
    public class View_ToolBar_Action implements ActionListener
    {
        JCheckBoxMenuItem jcbmi;
        

        public View_ToolBar_Action(JCheckBoxMenuItem jcm)
        {
            jcbmi = jcm;
        }

        @Override
        public void actionPerformed(ActionEvent evt)
        {
        	
        		
        	if (jcbmi.isSelected())
            {
        		ribbon.setVisible(true);
        		ReplaceViewsFileNodeText("toolStrip", "false", "true");
            }
            else
            {
                ribbon.setVisible(false);
                ReplaceViewsFileNodeText("toolStrip", "true", "false");
            }
        }
    }




    //********************************************************
    // Status strip action
    //********************************************************
    public class View_StatusStrip_Action implements ActionListener
    {
        JCheckBoxMenuItem jcbmi;

        public View_StatusStrip_Action(JCheckBoxMenuItem jcm)
        {
            jcbmi = jcm;
        }

        @Override
        public void actionPerformed(ActionEvent evt)
        {
            if (jcbmi.isSelected())
            {
               spanel.setVisible(true);
                ReplaceViewsFileNodeText("statusStrip", "false", "true");
            }
            else
            {
                spanel.setVisible(false);
                ReplaceViewsFileNodeText("statusStrip", "true", "false");
            }
        }
    }



    //********************************************************
    // The Perform Undo Action class
    //********************************************************
    public class PerformUndoAction extends AbstractAction
    {
       UndoManager _manager;

        public PerformUndoAction(UndoManager manager)
        {
            this._manager = manager;
        }

        public void actionPerformed(ActionEvent evt)
        {
            try
            {
                _manager.undo();
            }
            catch (CannotUndoException e)
            {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }



    //********************************************************
    // The Perform Redo Action class
    //********************************************************
    public class PerformRedoAction extends AbstractAction
    {
       UndoManager _manager;

        public PerformRedoAction(UndoManager manager)
        {
            this._manager = manager;
        }

        public void actionPerformed(ActionEvent evt)
        {
            try
            {
                _manager.redo();
            }
            catch (CannotRedoException e)
            {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }




    //********************************************************
    // KeyTypedAction
    // if key is typed then add * to tab
    //********************************************************
          class KeyTypedAction implements KeyListener
          {
              @Override
              public void keyPressed(KeyEvent evt)
              {
                  int keycode=evt.getKeyCode();
                  boolean is_ControlDown=evt.isControlDown();

                  if(keycode==KeyEvent.VK_X && is_ControlDown)
                  {
                      Edit_Cut_Action();
                  }
                  else if(keycode==KeyEvent.VK_C && is_ControlDown)
                  {
                      Edit_Copy_Action();
                  }
                  else if(keycode==KeyEvent.VK_V && is_ControlDown)
                  {
                      int sel=_tabbedPane.getSelectedIndex();
                      String tabtext = _tabbedPane.getTitleAt(sel);
                      if (tabtext.contains("*"))
                      { }
                      else
                      {
                          _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel) + "*");
                          _tabbedPane.setIconAt(sel, new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
                      }
                  }
                  else if(keycode==KeyEvent.VK_S && is_ControlDown)
                  {
                      File_Save_Action();
                  }
              }

              @Override
              public void keyReleased(KeyEvent evt){}

              @Override
              public void keyTyped(KeyEvent evt)
              {
                  if(!evt.isControlDown())
                  {
                     int sel=_tabbedPane.getSelectedIndex();
                     String tabtext=_tabbedPane.getTitleAt(sel);
                     if(tabtext.contains("*"))
                      { }
                     else
                     {
                       _tabbedPane.setTitleAt(sel, _tabbedPane.getTitleAt(sel)+"*");
                       _tabbedPane.setIconAt(sel,new ImageIcon(this.getClass().getResource("resources/unsaved.png")));
                     }
                  }
              }
          }





          //********************************************************
          // actions when frame is loading & closing
          //********************************************************
          class Load_Close_Frame_Action extends WindowAdapter
          {
              @Override
              public void windowOpened(WindowEvent evt)
              {
                  File_New_Action();
              }

              @Override
              public void windowClosing(WindowEvent evt)
              {
                  File_CloseAll_Action();
                  if (_tabbedPane.getTabCount() == 0)
                  {
                      parent.dispose();
                  }
              }
          }




          //********************************************************
          // select tab from list after clicking on item in the list
          //********************************************************
          class SelectTabFromListItem implements ListSelectionListener
          {
              @Override
              public void valueChanged(ListSelectionEvent evt)
              {
                  if(_list.getSelectedValue()!=null)
                  {
                      String list_item=_list.getSelectedValue().toString().trim();

                      if(_tabbedPane.getTabCount() >0)
                      {
                          int tabcount=_tabbedPane.getTabCount();
                         for(int i=0;i<tabcount;i++)
                          {
                              String title=_tabbedPane.getTitleAt(i).trim();
                              if (title.contains("*"))
                              {
                                  title = title.replace("*", "").trim();
                              }

                             if(title.equals(list_item))
                              {
                                  _tabbedPane.setSelectedIndex(i);
                                  parent.setTitle("JEXTEdit - [ "+_tabbedPane.getTitleAt(_tabbedPane.getSelectedIndex())+" ]");
                              }
                          }
                      }

                  }
              }
          }





          //********************************************************
          // selected tab changed
          //********************************************************
          class TabChanged implements ChangeListener
          {
              @Override
              public void stateChanged(ChangeEvent evt)
              {
                  if(_tabbedPane.getTabCount()>0)
                  {
                      Object[] files=filesHoldListModel.toArray();
                      String tabtext=_tabbedPane.getTitleAt(_tabbedPane.getSelectedIndex()).trim();
                      if(tabtext.contains("*"))
                       {
                           tabtext=tabtext.replace("*", "");
                       }

                      for(Object filename : files)
                      {
                          String file=filename.toString().substring(filename.toString().lastIndexOf("\\")+1);

                          if(file.equals(tabtext))
                          {
                              filenameLabel.setText(filename.toString());
                              parent.setTitle("JEXTEdit - [ "+_tabbedPane.getTitleAt(_tabbedPane.getSelectedIndex())+" ]");
                          }
                      }

                      if(tabtext.contains("Document "))
                      {
                          filenameLabel.setText(tabtext);
                          parent.setTitle("JEXTEdit - [ "+_tabbedPane.getTitleAt(_tabbedPane.getSelectedIndex())+" ]");
                      }

                  }
              }
          }




          //********************************************************
          // writing contents to files/viewsfile.xml file
          //********************************************************
          void WriteXMLFile_ViewContents(String documentpane,String statusStrip,
                                          String tabsAlign,String toolStrip)
          {
              Views views = new Views();
              views.setdocumentPane(documentpane);
              views.setStatusStrip(statusStrip);
              views.setTabsAlignment(tabsAlign);
              views.setToolStrip(toolStrip);

              try {

                  File file = new File(viewPath);
                  JAXBContext jaxbContext = JAXBContext.newInstance(Views.class);
                  Marshaller marshaller = jaxbContext.createMarshaller();
                  marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                  //writing to a file
                  marshaller.marshal(views, file);

              } catch (JAXBException e) {
                  e.printStackTrace();
              }
          }



           //************************************************************************
           // read files/viewsfile.xml file & replace its contents with newValue
           //************************************************************************
          void ReplaceViewsFileNodeText(String nodetag,String oldValue,String newValue)
          {
              try {

                  File fXmlFile = new File(viewPath);
                  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                  Document doc = dBuilder.parse(fXmlFile);

                  doc.getDocumentElement().normalize();

                  NodeList nList = doc.getElementsByTagName("views");

                  for (int temp = 0; temp < nList.getLength(); temp++) {

                      Node nNode = nList.item(temp);

                      Element eElement = (Element) nNode;

                      if (eElement.getElementsByTagName(nodetag).item(0).getTextContent().equals(oldValue)) {
                          eElement.getElementsByTagName(nodetag).item(0).setTextContent(newValue);
                      }

                      //writing all contents to a file
                      WriteXMLFile_ViewContents(eElement.getElementsByTagName("documentpane").item(0).getTextContent(),
                                                eElement.getElementsByTagName("statusStrip").item(0).getTextContent(),
                                                eElement.getElementsByTagName("tabsAlignment").item(0).getTextContent(),
                                                eElement.getElementsByTagName("toolStrip").item(0).getTextContent());

                  }
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }



      //**************************************************
        // returns content of node
        //**************************************************
        public String getNodeTextContent(String nodetag)
        {
          String content="";
          try {
              File fXmlFile = new File(viewPath);
              DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
              DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
              Document doc = dBuilder.parse(fXmlFile);
              doc.getDocumentElement().normalize();
              NodeList nList = doc.getElementsByTagName("views");
              for (int temp = 0; temp < nList.getLength(); temp++) {
                  Node nNode = nList.item(temp);
                  Element eElement = (Element) nNode;
                   content = eElement.getElementsByTagName(nodetag).item(0).getTextContent();
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
          return content;
        }



      //////////////////////////////////////////////////////////////////////////
      //**********************************************************************
      //run method
      //*********************************************************************
      public void run(String args[])
      {
      	
      
      }



}
