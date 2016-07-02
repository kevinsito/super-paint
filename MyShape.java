/*
 * This class is the superclass which all classes
 * will be inherited from. All variables are created here.
 * This class is an 'abtract' class in which the methods
 * denoted as 'abstract' must exist in all subclasses of
 * the hierarchy.
 *
 * @author Kevin Sito
 * @version June 6, 2011.
 */

import java.awt.Color;
import java.awt.Graphics;

//an abstract class
abstract class MyShape
{
  //declaration of all variables used in the draw methods
  private int x1;
  private int x2;
  private int y1;
  private int y2;
  private Color myColor;
  private Boolean isGradient;
  private Color myGradientC1;
  private Color myGradientC2;
  private float strokeWidth;
  private Boolean isDashed;
  private float strokeLength;
  
  //no-parameter/default constructor
  //sets all coordinates to zero and with black colour
  public MyShape()
  {
    this.x1 = 0;
    this.x2 = 0;
    this.y1 = 0;
    this.y2 = 0;
    myColor = Color.BLACK;
    isGradient = false;
    myGradientC1 = Color.BLACK;
    myGradientC2 = Color.BLACK;
    strokeWidth = 1;
    isDashed = false;
    strokeLength = 1;
  }
  
  //a normal constructor
  //initiates the values determined by each parameter given
  public MyShape( int x1, int y1, int x2, int y2, Color color , Boolean g, Color g1, Color g2, float sW, Boolean d, float sL )
  {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    myColor = color;
    this.isGradient = g;
    this.strokeWidth = sW;
    this.isDashed = d;
    this.strokeLength = sL;
  }
  
  //mutator methods for all variables
  protected void setFirstX( int x )
  {
    x1 = x;
  }
  protected void setSecondX( int x )
  {
    x2 = x;
  }
  protected void setFirstY( int y )
  {
    y1 = y;
  }
  protected void setSecondY(int y )
  {
    y2 = y;
  }
  protected void setMyColor( Color c )
  {
    myColor = c;
  }
  protected void setGradient( Boolean g ) 
  {
    isGradient = g;
  }
  protected void setMyGradientC1( Color c1 ) 
  {
    myGradientC1 = c1;
  }
  protected void setMyGradientC2( Color c2 ) 
  {
    myGradientC2 = c2;
  }
  protected void setStrokeWidth( float w ) 
  {
    strokeWidth = w;
  }
  protected void setDashed( Boolean d ) 
  {
    isDashed = d;
  }
  protected void setStrokeLength( float l )
  {
    strokeLength = l;
  }
  
  //accessor methods for all variables
  protected int getFirstX()
  {
    return x1;
  }
  protected int getSecondX()
  {
    return x2;
  }
  protected int getFirstY()
  {
    return y1;
  }
  protected int getSecondY()
  {
    return y2;
  }
  protected Color getMyColor()
  {
    return myColor;
  }
  protected Boolean getGradient() 
  {
    return isGradient;
  }
  protected Color getMyGradientC1() 
  {
    return myGradientC1;
  }
  protected Color getMyGradientC2() 
  {
    return myGradientC2;
  }
  protected float getStrokeWidth() 
  {
    return strokeWidth;
  }
  protected Boolean getDashed() 
  {
    return isDashed;
  }
  protected float getStrokeLength()
  {
    return strokeLength;
  }
  
  //abstract method which all subclasses must contain
  //and provide set bodies for drawing shapes
  public abstract void draw( Graphics g );
}