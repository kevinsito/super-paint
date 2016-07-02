/*
 * This class is the second abstract class which 
 * inherits directly from the 'MyShape' class
 * and branches to the 'MyRectangle' and the
 * 'MyCircle' classes in a second level
 * hierarchy. It provides additional constructors
 * which a 'fill' parameter can be set to draw
 * transparent or opaque shapes(rectangles or circles).
 *
 * @author Kevin Sito
 * @version June 6, 2011.
 */
import java.awt.Color;
import java.awt.Graphics;
//direct inheritance of 'MyShape' class
abstract class MyBoundedShape extends MyShape
{
  //variable to store boolean value for a filled/unfilled shape
  private Boolean fill;
  
  //non-parameter/default constructor
  //adds a new variable for fill and sets it to false(unfilled)
  public MyBoundedShape()
  {
    this.fill = false;
  }
  
  //normal constructor
  public MyBoundedShape( int x1, int y1, int x2, int y2, Color color, Boolean g, Color g1, Color g2, float sW, Boolean d, float sL, Boolean fill)
  {
    //the 'super' keyword recalls and initializes variables set by parentclass
    super( x1, x2, y1, y2, color, g, g1, g2, sW, d, sL );
    //integrates a new fill variable
    this.fill = fill;
  }
  
  //mutator methods to determine proper coordinate orientation
  //method to obtain the upper left x-coordinate
  protected int getUpperLeftX()
  {
    return Math.min( getFirstX(),getSecondX() ); 
  }
  //method to obtain the upper left y-coordinate
  protected int getUpperLeftY()
  {
    return Math.min( getFirstY(),getSecondY() ); 
  }
  //method to obtain width of the shape
  protected int getWidth()
  {
    return Math.abs( ( getFirstX() - getSecondX() ) );
  }
  //method to obtain the height of the shape
  protected int getHeight()
  {
    return Math.abs( ( getFirstY() - getSecondY() ) );
  }
  
  //mutator of the 'fill' variable
  protected void setFill( Boolean f )
  {
    fill = f;
  }
  //accessor to the 'fill' variable
  protected Boolean getFill()
  {
    return fill;
  }
  
  public abstract void draw( Graphics g );
}