package MXPSQL.JEXTEdit;


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
