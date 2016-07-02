/*
 * This class is directly inherited from the 'MyShape'
 * class and uses its methods to draw appropriate
 * lines in the 'DrawPanel' class.
 *
 * @author Kevin Sito
 * @version June 6, 2011.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.geom.Line2D;

public class MyLine extends MyShape
{
  //no-parameter/defaul constructor
  public MyLine()
  {
  }
  
  //normal constructor which sets all parameter passed values
  //into the 'MyShape' constructor
  public MyLine( int x1, int x2, int y1, int y2, Color color, Boolean g, Color g1, Color g2, float sW, Boolean d, float sL )
  {
    //the 'super' keyword to set values into the superclass
    super( x1, x2, y1, y2, color, g, g1, g2, sW, d, sL );
  }
  
  //the draw method which sets the colour and calls
  //the accessor methods from the superclass to set
  //according coordinates
  public void draw( Graphics g )
  { 
    //initialization of 2D shape Graphics object
    Graphics2D g2d = ( Graphics2D ) g;
    
    //setting of gradient paint and styles
    g2d.setPaint( new GradientPaint( getFirstX(), getFirstY(), getMyGradientC1(), getSecondX(), getSecondY(), getMyGradientC2(), true ) ); 
    
    //setting of the stroke width
    g2d.setStroke( new BasicStroke( getStrokeWidth() ) );
    
    //control statement to determine filled vs. unfilled shapes
    if ( getGradient() == true && getDashed() == false)
    {
      //accessor methods of the 'MyBoundedShape' are used to set
      //according coordinates and dimensions
      g2d.drawLine ( getFirstX(), getFirstY(), getSecondX(), getSecondY() );
    }
    else if( getGradient() == false && getDashed() == true )
    {
      g.setColor( getMyColor() );
      
      //methods used to create dash lengths and spacing styles
      float dashes[] = { getStrokeLength() };
      //sets the stroke style to different ends, bends, and widths
      g2d.setStroke( new BasicStroke( getStrokeWidth(), BasicStroke.CAP_BUTT,
                                     BasicStroke.JOIN_ROUND, 10, dashes, 0 ) ); 
      g2d.draw( new Line2D.Double( getFirstX(), getFirstY(), getSecondX(), getSecondY() ) ); 
    }
    else if ( getGradient() == true && getDashed() == true )
    {
      float dashes[] = { getStrokeLength() };
      g2d.setStroke( new BasicStroke( getStrokeWidth(), BasicStroke.CAP_BUTT,
                                     BasicStroke.JOIN_ROUND, 10, dashes, 0 ) ); 
      g2d.draw( new Line2D.Double( getFirstX(), getFirstY(), getSecondX(), getSecondY() ) );  
    }
    else
    {
      g.setColor( getMyColor() );
      
      g.drawLine( getFirstX(), getFirstY(), getSecondX(), getSecondY() );
    }
  }
}