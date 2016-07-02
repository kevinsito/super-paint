/*
 * This class is used to create seperate windows/frames
 * to be attached to the 'desktop'. Allows several drawpanels
 * to be accessed and used at the same time.
 *
 * @author Kevin Sito
 * @version June 6, 2011.
 */

import javax.swing.JInternalFrame;

public class MyInternalFrame extends JInternalFrame
{
  //constructor that accepts int parameter for document number
  public MyInternalFrame (String frameCount)
  {
    //instantiates internal frame
    super ( frameCount, true, true, true, true);
    
    setSize (400, 400);
    //cascading effect
    setLocation (50 * Integer.parseInt(frameCount), 20 * Integer.parseInt(frameCount));
    setVisible(true);
  }
}