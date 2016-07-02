/*
 * This class is used to invoke methods of the 'MyShape'
 * hiearchy to draw an assortment of 2D shapes.
 *
 * @author Kevin Sito
 * @version June 6, 2011.
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JOptionPane;
import java.util.Scanner;
import java.io.*;
import java.io.IOException;

public class DrawPanel extends JPanel
{
  //final variables used to remove magic numbers
  private final int LINE = 0;
  private final int RECTANGLE = 1;
  private final int CIRCLE = 2;
  private final int RRECTANGLE = 3;
  
  //creates a new 'MyShape' array
  private MyShape shapeObjects[];
  
  //variables used to determine which shape is to be drawn
  private int currentShapeType;
  
  //the total number of shapes in the array
  private int numOfShapes;
  
  //the total number of redo-able shapes
  private int rNumOfShapes;
  
  //the current object being drawn and orientated
  private MyShape currentShapeObject;
  
  //current colour of the shape being drawn
  private Color currentShapeColor;
  
  //current colour when gradient option is selected
  private Color currentGradientC1;
  private Color currentGradientC2;
  
  //variable used to decide wheter the shape will be filled or not
  private boolean currentShapeFilled;
  
  //boolean value used to determine wheter shape is gradient
  private boolean currentShapeGradient;
  
  //variable used to determine stroke width
  private float currentShapeStrokeWidth;
  
  //boolean value used to decipher whether shape is dashed(outline)
  private boolean currentShapeDashed;
  
  //variable used to determine stroke length
  private float currentShapeStrokeLength;
  
  //label set to show coordinates of the mouse
  private JLabel statusLabel;
  
  //colour array used to set current values
//  private final Color colors[] = { Color.BLACK, Color.BLUE, Color.CYAN, 
//    Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, 
//    Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, 
//    Color.YELLOW };
  
  //constructor of the DrawPanel which accepts a parameter for JLabel
  public DrawPanel( JLabel l )
  {
    statusLabel = l;
    
    //initializes the array to house 100 elements/shapes
    shapeObjects = new MyShape[ 100 ];
    //initial number of shapes to zero
    numOfShapes = 0;
    
    try 
    {
      Scanner input = new Scanner( new File( "pref.txt" ) );
      
      currentShapeColor = new Color( Integer.parseInt( input.nextLine() ) );
      
      //default shape will be a black line
      currentShapeType = Integer.parseInt( input.nextLine() );
      
      
      currentShapeFilled = Boolean.parseBoolean( input.nextLine() );
      
      //default gradient feature
      currentShapeGradient = Boolean.parseBoolean( input.nextLine() );
      
      //default gradient colours
      currentGradientC1 = new Color( Integer.parseInt( input.nextLine() ) );
      currentGradientC2 = new Color( Integer.parseInt( input.nextLine() ) );
      
      //default stroke width
      currentShapeStrokeWidth = 1;
      
      //dashed feature is off
      currentShapeDashed = Boolean.parseBoolean( input.nextLine() );
      
      //default stroke length
      currentShapeStrokeLength = 1;
      
      input.close();
    }
    catch ( IOException ioException ) 
    {
    }
    
    //background will be white
    setBackground( Color.WHITE );
    
    //handling object for mouse movement and actions
    MouseHandler handler = new MouseHandler();
    addMouseListener( handler );
    addMouseMotionListener( handler );
  }
  
  //drawing of the actual shapes
  public void paintComponent( Graphics g )
  {
    //inheritance from the paintComponent superclass
    super.paintComponent( g );
    
    //cycles through the 'shapes' array to draw each element
    for ( int i = 0; i < numOfShapes; i++ )
    {
      shapeObjects[i].draw( g );
    }
    
    //continual drawing for dragging of shapes
    if( currentShapeObject != null )
      currentShapeObject.draw( g );
  }
  
  //mutator method to set the current shape type (line, rectangle, circle)
  protected void setCurrentShapeType( int n )
  {
    currentShapeType = n;
  }
  //mutator method to set the current shape colour
  protected void setCurrentShapeColor( Color c )
  {
    currentShapeColor = c;
  }
  //mutator method to set gradient colours
  protected void setCurrentGradientC1( Color g1 )
  {
    currentGradientC1 = g1;
  }
  protected void setCurrentGradientC2( Color g2 )
  {
    currentGradientC2 = g2;
  }
  //mutator method to set if the shape is filled or not
  protected void setCurrentShapeFilled ( Boolean b )
  {
    currentShapeFilled  = b;
  }
  //mutator method to set gradient feature
  protected void setCurrentShapeGradient( Boolean g )
  {
    currentShapeGradient = g;
  }
  //mutator method to set stroke width
  protected void setCurrentShapeStrokeWidth( float w )
  {
    currentShapeStrokeWidth = w;
  }
  //mutator method to set dashed feature
  protected void setCurrentShapeDashed( Boolean d )
  {
    currentShapeDashed = d;
  }
  //mutator method to set stroke length
  protected void setCurrentShapeStrokeLength( float l )
  {
    currentShapeStrokeLength = l;
  }
  
  
  //method used to clear the most recent shape
  public void clearLastShape()
  {
    //control statment to end when number of shapes is zero
    if ( numOfShapes > 0 )
    {
      numOfShapes--;
      rNumOfShapes++; //number of redoes are incremented
    }
    
    //refresh the screen
    repaint();
  }
  
  //method used to clear the screen of all shapes
  public void clearDrawing()
  {
    numOfShapes = 0;
    rNumOfShapes = 0;
    
    //refreshes the screen
    repaint();
  }
  
  //method used to allow redo shape deductions
  public void redoLastShape()
  {
    if ( rNumOfShapes > 0 )
    {
      numOfShapes++; //number of shapes is incremented
      rNumOfShapes--;
    }
    
    //refresh the screen
    repaint();
  }
  
  
  //private inner class to handle all mouse movement and actions performed
  private class MouseHandler extends MouseAdapter implements MouseMotionListener
  {
    //event handling to initialize the beginning point of the shape
    public void mousePressed( MouseEvent event )
    {
      //control statement to determine which shape to be drawn
      if ( currentShapeType == LINE )
      {
        currentShapeObject = new MyLine();
        //setting of the colour
        currentShapeObject.setMyColor( currentShapeColor );
      }
      if ( currentShapeType == RECTANGLE )
      {
        currentShapeObject = new MyRectangle();
        //Casting of 'MyBoundedShape' to allow filling
        ((MyBoundedShape)currentShapeObject).setFill( currentShapeFilled );
        //setting of the colour
        currentShapeObject.setMyColor( currentShapeColor );
      }
      if ( currentShapeType == CIRCLE )
      {
        currentShapeObject = new MyOval();
        //Casting of 'MyBoundedShape' to allow filling
        ((MyBoundedShape)currentShapeObject).setFill( currentShapeFilled );
        //setting of the colour
        currentShapeObject.setMyColor( currentShapeColor );
      }
      if ( currentShapeType == RRECTANGLE )
      {
        currentShapeObject = new MyRoundRectangle();
        //Casting of 'MyBoundedShape' to allow filling
        ((MyBoundedShape)currentShapeObject).setFill( currentShapeFilled );
        //setting of the colour
        currentShapeObject.setMyColor( currentShapeColor );
      }
      //shared values of all shapes when created to have certain orientation
      currentShapeObject.setGradient( currentShapeGradient );
      currentShapeObject.setMyGradientC1( currentGradientC1 );
      currentShapeObject.setMyGradientC2( currentGradientC2 );
      currentShapeObject.setStrokeWidth( currentShapeStrokeWidth );
      currentShapeObject.setDashed( currentShapeDashed );
      currentShapeObject.setStrokeLength( currentShapeStrokeLength );
      
      //removal of right mouse drawing
      if ( !event.isMetaDown() && !event.isAltDown() )
      {
        currentShapeObject.setFirstX( event.getX() );
        currentShapeObject.setFirstY( event.getY() );
        currentShapeObject.setSecondX( event.getX() );
        currentShapeObject.setSecondY( event.getY() );
      }
      
      //control statement to limit the number of shapes to be drawn
      if( numOfShapes >= shapeObjects.length )
      {
        JOptionPane.showMessageDialog(null, "You have reached the end of the array. The screen will now be cleared.", "Maximum Shapes", JOptionPane.WARNING_MESSAGE);
        
        numOfShapes = 0;
        rNumOfShapes = 0;
        currentShapeObject = null;
        
        //refreshes the screen
        repaint();
      }
    }
    //event handling to finish the final shape coordinates
    public void mouseReleased( MouseEvent event )
    {
      if ( !event.isMetaDown() && !event.isAltDown() )
      {
        currentShapeObject.setSecondX( event.getX() );
        currentShapeObject.setSecondY( event.getY() );
      }
      
      //removes 'point' click shapes
      if ( currentShapeObject.getSecondX() != currentShapeObject.getFirstX() && currentShapeObject.getSecondY() != currentShapeObject.getFirstY() )
      {
        shapeObjects[numOfShapes] = currentShapeObject;
        currentShapeObject = null;
        numOfShapes++;
        repaint();
      }
    }
    
    //method used to show the coordinates of the mouse
    public void mouseMoved( MouseEvent event )
    {
      statusLabel.setText( String.format( "(%d,%d)", 
                                         event.getX(), event.getY() ) );
    }
    
    //method used to allow reshaping of the image when dragged
    public void mouseDragged( MouseEvent event )
    {
      if ( !event.isMetaDown() && !event.isAltDown() )
      {
        currentShapeObject.setSecondX( event.getX() );
        currentShapeObject.setSecondY( event.getY() );
        repaint();
      }
      
      statusLabel.setText( String.format( "(%d,%d)", 
                                         event.getX(), event.getY() ) );
    }
  }
}