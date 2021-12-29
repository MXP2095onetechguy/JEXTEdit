package MXPSQL.JEXTEdit;

import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;

class FileTreeModel implements TreeModel {
	 
	private File root;
	
	/**
	 * Creating an object of this class and setting its root to the provided
	 * File object.
	 * 
	 * The root is the highest directory available in an object of this class.
	 * 
	 * @param file
	 *            - an object of type File, giving the root directory for an
	 *            object of type FileTreeModel.
	 */
	public FileTreeModel(File file) {
		this.root = file;
	}
	
	
	public Object getRoot() {
		return this.root;
	}
 
	@Override
	public Object getChild(Object parent, int index) {
		File f = (File) parent;
		return f.listFiles()[index];
	}
 
	@Override
	public int getChildCount(Object parent) {
		File f = (File) parent;
 
		try {
			if (!f.isDirectory() && f.list() != null) {
				return 0;
			} else {
				return f.list().length;
			}
		} catch (NullPointerException ex) {
			return 0;
		}
	}
 
	@Override
	public boolean isLeaf(Object node) {
		File f = (File) node;
		return !f.isDirectory();
	}
 
	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub
 
	}
 
	@Override
	public int getIndexOfChild(Object parent, Object child) {
		File par = (File) parent;
		File ch = (File) child;
		return Arrays.asList(par.listFiles()).indexOf(ch);
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub
		
	}
}


public class FBrowser extends JPanel {
	JRelodableTree ftree;
	
	  public void getFList(DefaultMutableTreeNode node, File f) {
		  // System.out.println(f);
		     if(!f.isDirectory()) {
		            DefaultMutableTreeNode child = new DefaultMutableTreeNode(f.toString());
		            node.add(child);
		         }
		     else {
		         DefaultMutableTreeNode child = new DefaultMutableTreeNode(f.toString());
		         node.add(child);
		         File fList[] = f.listFiles();
		         if(fList != null) {
			         for(int i = 0; i  < fList.length; i++)
			             getFList(child, fList[i]);
			         }
		         }
		    }
	
	public FBrowser(HashMap<String, File[]> fsmap) {
		
		DefaultMutableTreeNode compModel = new DefaultMutableTreeNode("Computer");
		TreeModel model = null;
		
		if(fsmap.get("roots") != null) {
			File[] rdirs = fsmap.get("roots");
			
			SwingWorker rdirsWorker = new SwingWorker() {
				@Override
				protected Object doInBackground() throws Exception {
					for(int i = 0; i < rdirs.length; i++) {
						
						if(isCancelled()){
							break;
						}
						
						getFList(compModel, rdirs[i]);
						ftree.load(compModel);
					}
					ftree = new JRelodableTree(compModel);
					
					return true;
				}
			};
			
			rdirsWorker.execute();
		}
		
		if(fsmap.get("cwd") != null) {
			File[] cdirs = fsmap.get("cwd");
			
			SwingWorker cdirsWorker = new SwingWorker() {
				@Override
				protected Object doInBackground() throws Exception {
					for(int i = 0; i < cdirs.length; i++) {
						
						if(isCancelled()) {
							break;
						}
						
						/* getFList(compModel, cdirs[i]);
						ftree.load(compModel); */
						
					}
					ftree = new JRelodableTree(compModel);
					return true;
				}
			};
			
			// cdirsWorker.execute();
			
			model = new FileTreeModel(cdirs[0]);
			
			
		}
		
		// ftree = new JRelodableTree(compModel);
		ftree = new JRelodableTree(model);
		// ftree.reload();
		// ftree.setRootVisible(false);
		JScrollPane scp = new JScrollPane(ftree);
		add(scp);
	}
}
