package MXPSQL.JEXTEdit;

import javax.swing.*;

import MXPSQL.JEXTEdit.EditorWindow.ToolBarButtonsAction;

import java.awt.*;
import java.util.*;

public class RibbonBar extends JTabbedPane {
	public Map<String, JToolBar> tape = new HashMap<String, JToolBar>();
	
	public RibbonBar() {
		initUI();
	}
	
	public void initUI() {
        //************************************************************
        //creating & adding  to north direction
        //************************************************************
		
		tape.put("Main", new JToolBar());
		
		JToolBar _toolbar = tape.get("Main");
        
        
        add("Main", _toolbar);
	}
	
	public void updateRibbon() {
        for(Map.Entry<String, JToolBar> entry : tape.entrySet()) {
        	tape.get(entry.getKey()).setFloatable(false);;
        }
	}
}
