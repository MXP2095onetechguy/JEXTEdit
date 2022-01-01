package MXPSQL.JEXTEdit;

import java.io.*;
import java.util.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.WatchKey;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.StandardWatchEventKinds.*;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.TreeModelListener;

class WatchRegister{
	static boolean trace = false;
	
    static void register(Path dir, WatchService watcher, Map<WatchKey,Path> keys) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        keys.put(key, dir);
    }

    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    static void registerAll(final Path start, WatchService watcher, Map<WatchKey, Path> keys) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException
            {
                register(dir, watcher, keys);
                return FileVisitResult.CONTINUE;
            }
        });
    }

}

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
	WatchService watcher;
	HashMap<WatchKey, Path> keys;
	
	private boolean modelUpdated = false;
	
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
		
		keys = new HashMap<WatchKey, Path>();
		
		try {
			watcher = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error with fbrowser", JOptionPane.ERROR_MESSAGE);
		}
		
		try {
			WatchRegister.registerAll(fsmap.get("cwd")[0].toPath(), watcher, keys);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
		
		// swingworker for jtree
		if(fsmap.get("cwd") != null) {
			File[] cdirs = fsmap.get("cwd");
			
			
			SwingWorker JTreeWorker = new SwingWorker() {

				@Override
				protected Object doInBackground() throws Exception {
					// TODO Auto-generated method stub
					WatchKey key;
					
					while(!isCancelled()) {
						
						try {
							key = watcher.take();
						}
						catch(Exception e) {
							e.printStackTrace();
							cancel(false);
							break;
						}
						
						Path dir = keys.get(key);
						if(dir == null) {
							continue;
						}
						
						for(WatchEvent<?> event: key.pollEvents()) {
							WatchEvent.Kind<?> kind = event.kind();
							System.out.println(kind);
							
							if(kind == OVERFLOW) {
								continue;
							}
							
					        WatchEvent<Path> ev = (WatchEvent<Path>)event;
					        Path filename = ev.context();
					        
							if(kind == ENTRY_CREATE || kind == ENTRY_DELETE || kind == ENTRY_MODIFY) {
								modelUpdated = true;
							}
						}
						
						if(modelUpdated) {
							TreeModel model2 = new FileTreeModel(cdirs[0]);
							ftree.setModel(model2);
						}
						
						modelUpdated = false;
					}
					return null;
				}
				
			};
			
			// JTreeWorker.execute();
		}
		
		// ftree = new JRelodableTree(compModel);
		ftree = new JRelodableTree(model);
		modelUpdated = false;
		// ftree.reload();
		// ftree.setRootVisible(false);
		JScrollPane scp = new JScrollPane(ftree);
		add(scp);
	}
}
