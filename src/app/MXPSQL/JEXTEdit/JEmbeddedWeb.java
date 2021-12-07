package MXPSQL.JEXTEdit;



import javax.swing.JPanel;
import javafx.scene.web.*;
import javafx.scene.Scene;
import javafx.concurrent.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.concurrent.Worker.*;
import javafx.embed.swing.JFXPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableValue;

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
		text = new TextField();
		HBox hbox = new HBox();
		
		Button relbtn = new Button("Reload");
		
		Button backbtn = new Button("<");
		Button forwardbtn = new Button(">");
		
		Button gobtn = new Button("Go");
		
		hbox.getChildren().addAll(relbtn, backbtn, forwardbtn, text, gobtn);
		
		jfxp.setScene(new Scene(hbox));
		
		web = new JFXWeb(false, "https://google.com");
		engine = web.getEngine();
		
		assert engine != null;

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
		
		add(jfxp);
		
		
		add(web);
	}
	
	public WebEngine getEngine() {
		return engine;
	}
}
