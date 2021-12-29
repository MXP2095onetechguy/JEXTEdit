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

import javafx.util.*;
import javax.swing.JPanel;
import javafx.scene.web.*;
import javafx.scene.Scene;
import javafx.concurrent.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.concurrent.Worker.*;
import javafx.geometry.Rectangle2D;
import javafx.embed.swing.JFXPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableValue;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@SuppressWarnings({"unused", "static-access"})
public class JEmbeddedWeb extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFXWeb web;
	private WebEngine engine;
	private TextField text;
	
	

	public JEmbeddedWeb() {		
		JFXPanel jfxp = new JFXPanel();
		
		CountDownLatch l = new CountDownLatch(1);

		Platform.runLater(() -> {
		
			text = new TextField();
			HBox hbox = new HBox();
			
			hbox.setSpacing(10);
			
			Button relbtn = new Button("Reload");
			
			Button backbtn = new Button("<");
			Button forwardbtn = new Button(">");
			
			Button gobtn = new Button("Go");
			
			hbox.getChildren().addAll(relbtn, backbtn, forwardbtn, text, gobtn);
			
			Scene sc = new Scene(hbox);;
			sc.getStylesheets().add(this.getClass().getResource("css/webbrowser.css").toString());
			
			jfxp.setScene(sc);
			web = new JFXWeb(false, "https://google.com");
			
			engine = web.getEngine();
			assert engine != null;
			
			engine = web.getEngine();
			engine.reload();
			
			assert engine != null;
			
			
			engine.setOnAlert(new EventHandler<WebEvent<String>>(){
			
				@Override
				public void handle(WebEvent<String> event) {
					// TODO Auto-generated method stub
					// JOptionPane.showMessageDialog(jfxp, event.getData());
					Alert a = new Alert(Alert.AlertType.NONE);
					a.setContentText(event.getData());
					a.setAlertType(Alert.AlertType.INFORMATION);
					a.showAndWait();
				}
				
			});
			
			engine.setPromptHandler(new Callback<PromptData, String>(){

				@Override
				public String call(PromptData param) {
					// TODO Auto-generated method stub
					String results = "";
					
					TextInputDialog td = new TextInputDialog(param.getDefaultValue());
					td.setHeaderText(param.getMessage());
					
					Optional<String> res = td.showAndWait();
					
					if(res.isPresent()) {
						results = res.get();
					}
					else {
						if(param.getDefaultValue() != null) {
							results = param.getDefaultValue();
						}
						else {
							results = null;
						}
					}
					
					return results;
				}
				
			});
			
			engine.setConfirmHandler(new Callback<String, Boolean>() {

				@Override
				public Boolean call(String param) {
					// TODO Auto-generated method stub
					Alert a = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK, ButtonType.CANCEL);
					a.setContentText(param);
					a.showAndWait();
					
					Boolean result;
					
					if(a.getResult() == ButtonType.OK) {
						result = Boolean.TRUE;
					}
					else {
						result = Boolean.FALSE;
					}
					
					return result;
				}
				
			});
		
        	EventHandler<ActionEvent> relevent = new EventHandler<ActionEvent>() {
        		@Override
        	    public void handle(ActionEvent e)
        	    {
        	    	engine = web.getEngine();
        	    	assert engine == null;
        	        engine.reload();
        	    }
        	};
		
        	relbtn.setOnAction(relevent);
		
        	EventHandler<ActionEvent> backevent = new EventHandler<ActionEvent>() {
        		@Override
        	    public void handle(ActionEvent e)
        	    {
        	    	engine = web.getEngine();
        	    	assert engine == null;
        	    	engine.executeScript("history.back()");
        	    }
        	};
		
        	backbtn.setOnAction(backevent);
		
        	EventHandler<ActionEvent> forwardevent = new EventHandler<ActionEvent>() {
        		@Override
        	    public void handle(ActionEvent e)
        	    {
        	    	engine = web.getEngine();
        	    	assert engine == null;
        	    	engine.executeScript("history.forward()");
        	    }
        	};
		
        	forwardbtn.setOnAction(forwardevent);
			
        	EventHandler<ActionEvent> goevent = new EventHandler<ActionEvent>() {
        		@Override
        	    public void handle(ActionEvent e)
        	    {
        	    	engine = web.getEngine();
        	    	assert engine == null;
        	        engine.load(text.getText());
        	    }
        	};
		
        	gobtn.setOnAction(goevent);
        	text.setOnAction(goevent);

			l.countDown();
		});	
		try {
			l.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		add(jfxp);
		
		
		add(web);
	}
	
	public WebEngine getEngine() {
		return engine;
	}
}
