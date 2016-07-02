/*
 * This class is directly inherited from the 'MyBoundedShape'
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
import java.awt.geom.Rectangle2D;

public class MyRectangle extends MyBoundedShape
{
  //no-parameter/defaul constructor
  public MyRectangle()
  {
  }
  
  //normal constructor which sets all parameter passed values
  //into those of the direct superclass
  public MyRectangle( int x1, int y1, int x2, int y2, Color color, Boolean g, Color g1, Color g2, float sW, Boolean d, float sL, Boolean fill )
  {
    //the 'super' keyword is used to instantiate variables
    //set by the parent class
    super( x1, x2, y1, y2, color, g, g1, g2, sW, d, sL, fill );
  }
  
  //the draw method which sets the colour and calls
  //the accessor methods from the superclass to set
  //according coordinates
  public void draw( Graphics g )
  {    
    //initialization of 2D Graphics object
    Graphics2D g2d = ( Graphics2D ) g;
    
    //setting of gradient colours and style
    g2d.setPaint( new GradientPaint( getUpperLeftX(), getUpperLeftY(), getMyGradientC1(), getWidth(), getHeight(), getMyGradientC2(), true ) ); 
    
    //setting of the current stroke width 
    g2d.setStroke( new BasicStroke( getStrokeWidth() ) );
    
    //control statement to determine filled vs. unfilled shapes
    if ( getFill() == true && getGradient() == false )
    {
      //sets the colour of the shape
      g.setColor( getMyColor() );
      
      //accessor methods of the 'MyBoundedShape' are used to set
      //according coordinates and dimensions
      g.fillRect ( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() );
    }
    if ( getFill() == true && getGradient() == true )
    {
      //method used to fill a 2D rectangle with gradient styles
      g2d.fill( new Rectangle2D.Double( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight()) );
    }
    
    //control statement to differentiate unfilled objects
    if ( getFill() == false )
    {
      if ( getGradient() == true  && getDashed() == false )
      {
        g2d.draw( new Rectangle2D.Double( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight()) );
      }
      else if ( getGradient() == false && getDashed() == true )
      {
        //sets the colour of the shape
        g.setColor( getMyColor() );
        
        //dashing criteria
        float dashes[] = { getStrokeLength() };
        //setting of end, bend, and width behaviors
        g2d.setStroke( new BasicStroke( getStrokeWidth(), BasicStroke.CAP_BUTT,
                                       BasicStroke.JOIN_ROUND, 10, dashes, 0 ) );
        
        g.drawRect ( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() );
      }
      else if ( getGradient() == true && getDashed() == true )
      {
        float dashes[] = { getStrokeLength() };
        g2d.setStroke( new BasicStroke( getStrokeWidth(), BasicStroke.CAP_BUTT,
                                       BasicStroke.JOIN_ROUND, 10, dashes, 0 ) );
        
        g2d.draw( new Rectangle2D.Double( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight()) );
      }
      else
      {
        g.setColor( getMyColor() );
        
        g.drawRect ( getUpperLeftX(), getUpperLeftY(), getWidth(), getHeight() );
      }
    }
  }
}