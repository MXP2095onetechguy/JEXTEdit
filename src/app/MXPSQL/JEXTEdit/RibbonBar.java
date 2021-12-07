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

import javax.swing.*;

import MXPSQL.JEXTEdit.EditorWindow.ToolBarButtonsAction;

import java.awt.*;
import java.util.*;

// public class RibbonBar extends JTabbedPane {
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
