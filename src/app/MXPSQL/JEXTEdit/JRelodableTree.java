package MXPSQL.JEXTEdit;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

class JRelodableTree extends JTree{
	public JRelodableTree() {
		super();
	}
	
	public JRelodableTree(Hashtable<?, ?> value) {
		super(value);
	}
	
	public JRelodableTree(Object[] value) {
		super(value);
	}
	
	public JRelodableTree(TreeModel value) {
		super(value);
	}
	
	public JRelodableTree(TreeNode root) {
		super(root);
	}
	
	public JRelodableTree(TreeNode root, boolean askAllowChildren) {
		super(root, askAllowChildren);
	}
	
	public JRelodableTree(Vector<?> value) {
		super(value);
	}
	
	public void reload() {
		DefaultTreeModel model = (DefaultTreeModel)getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
		model.reload(root);
	}
	
	public void load(DefaultMutableTreeNode root) {
		DefaultTreeModel model = (DefaultTreeModel)getModel();
		model.reload(root);
	}
}
