/*
 * This class is used to test all methods of the DrawFrame
 *
 * @author Kevin Sito
 * @version June 6, 2011.
 */

import javax.swing.JFrame;

public class SuperTest extends JFrame 
{
  public static void main( String args[] )
  { 
    DrawFrame drawFrame = new DrawFrame(); 
    drawFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    drawFrame.setSize( 925, 650 );
    drawFrame.setVisible( true );
  }
}