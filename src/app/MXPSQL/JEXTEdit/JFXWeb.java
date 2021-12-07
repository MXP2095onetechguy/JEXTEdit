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

import javafx.scene.Scene;
import javafx.scene.web.*;
import javafx.embed.swing.*;
import javafx.application.Platform;

// a class that can embed a jfx webview directly without the need to make a new jfxpanel
public class JFXWeb extends JFXPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7188918465904747631L;
	private WebView webkit;
	private WebEngine engine;
	
	public JFXWeb() {
		Platform.runLater(() -> {
			webkit = new WebView();
			setScene(new Scene(webkit));
			engine = webkit.getEngine();
			engine.load("https://google.com");
		});
	}
	
	public JFXWeb(boolean runlater) {
		if(runlater) {
			Platform.runLater(() -> {
				webkit = new WebView();
				setScene(new Scene(webkit));
				engine = webkit.getEngine();
				engine.load("https://google.com");
			});
		}
		else {
			webkit = new WebView();
			setScene(new Scene(webkit));
			engine = webkit.getEngine();
			engine.load("https://google.com");
		}
	}
	
	public JFXWeb(boolean runlater, String weburl) {
		Platform.runLater(() -> {
			webkit = new WebView();
			setScene(new Scene(webkit));
			engine = webkit.getEngine();
			engine.load(weburl);
		});
	}
	
	public WebView getWebkit() {
		return webkit;
	}
	
	public WebView getWebView() {
		return getWebkit();
	}
	
	public WebEngine getEngine() {
		return engine;
	}
	
	public WebEngine getWebEngine() {
		return getEngine();
	}
}
