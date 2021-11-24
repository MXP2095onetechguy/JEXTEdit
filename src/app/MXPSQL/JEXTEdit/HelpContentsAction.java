package MXPSQL.JEXTEdit;
import java.awt.*;

import javax.swing.*;
public class HelpContentsAction extends JFrame
{
    Image image;
  public HelpContentsAction()
  {
      Container cp=getContentPane();
      JLabel lb=new JLabel("JEXTEdit");
      lb.setFont(new Font("SansSerif",Font.PLAIN,22));

      JPanel jp=new JPanel();
      jp.add(lb, BorderLayout.PAGE_START);
      
      JLabel lb2 = new JLabel("Very Nice MIT Licensed editor.");
      jp.add(lb2, BorderLayout.CENTER);

      cp.add(jp,BorderLayout.NORTH);

  }
}
