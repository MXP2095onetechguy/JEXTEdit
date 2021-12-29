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
		if(runlater) {
			Platform.runLater(() -> {
				webkit = new WebView();
				setScene(new Scene(webkit));
				engine = webkit.getEngine();
				engine.load(weburl);
			});
		}
		else {
			webkit = new WebView();
			setScene(new Scene(webkit));
			engine = webkit.getEngine();
			engine.load(weburl);
		}
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
