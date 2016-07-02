/*
 * This class is used to creat a pleasant GUI for the user.
 * The class will call methods of the 'DrawPanel' hierarchy
 * to create objects according to orientation and configuration.
 *
 * @author Kevin Sito
 * @version June 6, 2011.
 */

import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import java.awt.Event;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class DrawFrame extends JFrame
{
  //constants used to determine shape type
  private final int LINE = 0;
  private final int RECTANGLE = 1;
  private final int OVAL = 2; 
  private final int RRECTANGLE = 3;
  
  //variable used to set up menu bar
  private JMenuBar menuBar;
  
  //menus available in the menu bar
  private JMenu fileMenu;
  private JMenu editMenu;
  private JMenu helpMenu;
  
  //selectable menu items for the File menu
  private JMenuItem fileMenuNewFrame;
  private JMenuItem fileMenuAbout;
  private JMenuItem fileMenuPrefs;
  private JMenuItem fileMenuExit;
  
  //selectable menu items for Edit menu
  private JMenuItem editMenuUndo;
  private JMenuItem editMenuRedo;
  private JMenuItem editMenuClear;
  
  //selectable menu items for Help menu
  private JMenuItem helpMenuResources;
  
  private JPanel nJPanel; //north Panel
  
  private JButton uButton; //undo button
  private JButton cButton; //clear button
  private JButton rButton; //redo button
  
  private JCheckBox fCheckBox; //filled check box
  private JCheckBox gCheckBox; //gradient check box
  private JCheckBox dCheckBox; //dashed check box
  
  private JComboBox colorsJComboBox; //colours drop-down list
  private JComboBox shapesJComboBox; //shapes drop-down list
  
  private JComboBox colors2JComboBox; //second gradient colour list
  
  private JTextField sWidth; //stroke width text field
  private JTextField sLength; //stroke length text field
  
  //arrays used for pinpointing certain colours for the combobox
  private final String colorNames[] = { "Black", "Blue", "Cyan", 
    "Dark Gray", "Gray", "Green", "Light Gray", "Magenta",
    "Orange", "Pink", "Red", "White", "Yellow" };
  private final Color colors[] = { Color.BLACK, Color.BLUE, Color.CYAN, 
    Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, 
    Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, 
    Color.YELLOW };
  
  //array used to list the shapes of the shapesComboBox
  private final String shapeNames[] = { "Line", "Rectangle", "Circle", "Rounded Rectangle" };
  
  //DrawPanel object
  private DrawPanel currentMPanel;
  
  //array used to determine how many panels can be set in the frames
  private DrawPanel[] mPanel = new DrawPanel[6];
  
  //label for coordinates
  private JLabel statusBar;
  
  //desktop object used to place frames/windows
  private JDesktopPane desktop;
  
  //class object used to define each individual frame
  private MyInternalFrame frame;
  
  //variable used to store how many frames are open
  private int openFrameCount;
  
  //variable used to store preference frame count
  private int pCount;
  
  //current frame index used to work event handling
  private int currentFrame;
  
  //current frame's stroke width
  private float currentSWidth;
  
  //current frame's stroke length
  private float currentSLength = 1;
  
  public DrawFrame()
  {
    super( "SuperPaint Application!" );
    
    //initialization of a new desktop
    desktop = new JDesktopPane();
    
    //initialization of menuBar object
    menuBar = new JMenuBar();
    
    //visable menu items under the main MenuBar
    fileMenu = new JMenu( "File" );
    fileMenu.setMnemonic(KeyEvent.VK_F); 
    editMenu = new JMenu( "Edit" );
    editMenu.setMnemonic(KeyEvent.VK_E); 
    helpMenu = new JMenu( "Help" );
    helpMenu.setMnemonic(KeyEvent.VK_H); 
    
    //visable menu items under 'File'
    fileMenuNewFrame = new JMenuItem( "  New Frame" );
    fileMenuAbout = new JMenuItem( "  About" );
    fileMenuPrefs = new JMenuItem( "  Preferences" );
    fileMenuExit = new JMenuItem( "  Exit" );
    
    //visable menu items under 'Edit'
    editMenuUndo = new JMenuItem( "  Undo" );
    editMenuUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));
    editMenuRedo = new JMenuItem( "  Redo" );
    editMenuRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
    editMenuClear = new JMenuItem( "  Clear" );
    editMenuClear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
    
    //visable menu items under 'Help'
    helpMenuResources = new JMenuItem( " Resources" );
    
    //addition of the items into the MenuBar
    fileMenu.add( fileMenuNewFrame );
    fileMenu.add( fileMenuAbout );
    fileMenu.add( fileMenuPrefs );
    fileMenu.add( fileMenuExit );
    
    editMenu.add( editMenuUndo );
    editMenu.add( editMenuRedo );
    editMenu.add( editMenuClear );
    
    helpMenu.add( helpMenuResources );
    
    //adding the visable categories into the Menu
    menuBar.add( fileMenu );
    menuBar.add( editMenu );
    menuBar.add( helpMenu );
    
    //places the MenuBar in required area
    setJMenuBar(menuBar);
    
    //creates a new JPanel
    nJPanel = new JPanel();
    
    //default setting for the JLabel(south)
    statusBar = new JLabel("(0,0)");
    //mPanel object allows a JLabel parameter to set drawing box and coordinates
    mPanel[0] = new DrawPanel( statusBar );
    //sets the currentPanel as the first object of the array
    currentMPanel = mPanel[0];
    
    //initialization of buttons
    uButton = new JButton( "Undo" );
    cButton = new JButton( "Clear" );
    rButton = new JButton( "Redo" );
    
    //initialization of comboboxes and viewable row columns
    colorsJComboBox = new JComboBox( colorNames );
    colorsJComboBox.setMaximumRowCount( 5 );
    //resizing of object 
    colorsJComboBox.setPreferredSize( new Dimension ( 70, 25 ) );
    
    //initialization of second gradient colour combobox
    colors2JComboBox = new JComboBox( colorNames );
    colors2JComboBox.setMaximumRowCount( 5 );
    colors2JComboBox.setPreferredSize( new Dimension ( 70, 25 ) );
    //uneditable when default values/shapes are started
    colors2JComboBox.setEnabled( false );
    
    //initialization of shapes selection
    shapesJComboBox = new JComboBox( shapeNames );
    shapesJComboBox.setMaximumRowCount( 3 );
    shapesJComboBox.setPreferredSize( new Dimension ( 80, 25 ) );
    
    //textfield items for stroke width and length
    sWidth = new JTextField( 3 );
    sLength = new JTextField( 3 );
    //length is uneditable when dashed is unclicked
    sLength.setEnabled( false );
    
    //initialization of the filled checkbox
    fCheckBox = new JCheckBox( "Filled", false );
    fCheckBox.setEnabled( false );
    
    //initialization of gradient checkbox
    gCheckBox = new JCheckBox( "Gradient", false );
    
    //initialization of dashed checkbox
    dCheckBox = new JCheckBox( "Dashed", false );
    
    //adds all components to the north Panel
    nJPanel.add( uButton );
    nJPanel.add( rButton );
    nJPanel.add( cButton );
    nJPanel.add( colorsJComboBox );
    nJPanel.add( colors2JComboBox );
    nJPanel.add( shapesJComboBox );
    nJPanel.add( new JLabel( "Stroke Width:" ) );
    nJPanel.add( sWidth );
    nJPanel.add( dCheckBox );
    nJPanel.add( new JLabel( "Stroke Length:" ) );
    nJPanel.add( sLength );
    nJPanel.add( fCheckBox );
    nJPanel.add( gCheckBox );
    
    //method used to create new frames
    createFrame();
    
    //adds the desktop object onto the main drawframe
    add( desktop );
    
    //adds all panels, labels and DrawPanel to the created window
    add( nJPanel, BorderLayout.NORTH );
    //add( mPanel, BorderLayout.CENTER );
    add( statusBar, BorderLayout.SOUTH );
    
    //Button handler for undo and clear buttons
    ButtonHandler handler = new ButtonHandler();
    uButton.addActionListener( handler );
    cButton.addActionListener( handler );
    rButton.addActionListener( handler );
    
    //checkbox handler for filled option
    CheckBoxHandler handler2 = new CheckBoxHandler();
    fCheckBox.addItemListener( handler2 );
    gCheckBox.addItemListener( handler2 );
    dCheckBox.addItemListener( handler2 );
    
    //textfield handler for stroke width and length
    TextFieldHandler handler3 = new TextFieldHandler();
    sWidth.addActionListener( handler3 );
    sLength.addActionListener( handler3 );
    
    //colorsComboBox handler to pinpoint wanted colours
    colorsJComboBox.addItemListener(
                                    new ItemListener() 
                                      {
      public void itemStateChanged( ItemEvent event )
      {
        //if statement for certain colour selection
        if ( event.getStateChange() == ItemEvent.SELECTED )
        {
          
          //control statement which allows static shape orientation
          for( int i = 0; i <= openFrameCount; i++)
          {
            //the current shape colour will be decided according to set arrays and index of combobox
            mPanel[i].setCurrentShapeColor( colors[ colorsJComboBox.getSelectedIndex() ] );
            mPanel[i].setCurrentGradientC1( colors[ colorsJComboBox.getSelectedIndex() ] );
          }
        }
      } 
    }
    );
    
    //colors2JComboBox handler to pinpoint wanted gradient colour
    colors2JComboBox.addItemListener(
                                     new ItemListener() 
                                       {
      public void itemStateChanged( ItemEvent event )
      {
        //if statement for certain colour selection
        if ( event.getStateChange() == ItemEvent.SELECTED )
        {         
          for( int i = 0; i <= openFrameCount; i++)
          {
            //the current shape colour will be decided according to set arrays and index of combobox
            mPanel[i].setCurrentGradientC2( colors[ colors2JComboBox.getSelectedIndex() ] );
          }
        }
      }
    }
    );
    
    //shapesComboBox handler to decipher according shapes
    shapesJComboBox.addItemListener(
                                    new ItemListener() 
                                      {
      public void itemStateChanged( ItemEvent event )
      {
        //if statement for shape selection
        if ( event.getStateChange() == ItemEvent.SELECTED )
        {
          for( int i = 0; i <= openFrameCount; i++)
          {
            //setting of current shape to the index of the combobox selection
            mPanel[i].setCurrentShapeType( shapesJComboBox.getSelectedIndex() );
            
            //allows visability to filled checkbox when 'Line' is not selected
            if( shapesJComboBox.getSelectedIndex() == RECTANGLE || shapesJComboBox.getSelectedIndex() == OVAL || shapesJComboBox.getSelectedIndex() == RRECTANGLE )
            {
              //allows editing
              fCheckBox.setEnabled( true );
            }
            else
            {
              //uneditable options
              fCheckBox.setEnabled( false );
            }
          }
        }
      } 
    }
    );
    
    //MenuItem handler to access certain methods on the MenuBar
    MenuItemHandler menuItemHandler = new MenuItemHandler();
    fileMenuNewFrame.addActionListener( menuItemHandler );
    fileMenuAbout.addActionListener( menuItemHandler );
    fileMenuPrefs.addActionListener( menuItemHandler );
    fileMenuExit.addActionListener( menuItemHandler );
    editMenuUndo.addActionListener( menuItemHandler );
    editMenuRedo.addActionListener( menuItemHandler );
    editMenuClear.addActionListener( menuItemHandler );
    helpMenuResources.addActionListener( menuItemHandler );
    
  }
  
  public void createFrame()
  {
    //increase number of frames
    openFrameCount++;
    
    //calls the internalframe class to create new frames
    MyInternalFrame frame = new MyInternalFrame ("" + openFrameCount);
    //initializes each frame into a new drawable area
    mPanel[openFrameCount] = new DrawPanel( statusBar ); 
    //sets the current panel as one dedicated to each frame
    currentMPanel = mPanel[openFrameCount];
    
    //preset orientation when frame is created
    currentMPanel.setCurrentShapeColor( colors[ colorsJComboBox.getSelectedIndex() ] );
    currentMPanel.setCurrentShapeType( shapesJComboBox.getSelectedIndex() );
    currentMPanel.setCurrentShapeFilled( fCheckBox.isSelected() );
    currentMPanel.setCurrentShapeGradient( gCheckBox.isSelected() );
    currentMPanel.setCurrentGradientC1( colors[ colorsJComboBox.getSelectedIndex() ] );
    currentMPanel.setCurrentGradientC2( colors[ colors2JComboBox.getSelectedIndex() ] );
    currentMPanel.setCurrentShapeDashed( dCheckBox.isSelected() );
    currentMPanel.setCurrentShapeStrokeWidth( currentSWidth );
    currentMPanel.setCurrentShapeStrokeLength( currentSLength );
    
    //adds drawable area onto the frame
    frame.add( currentMPanel );
    
    desktop.add(frame); //adds frame to desktop
    
    //exception handling to allow newest frame on top
    try 
    {
      frame.setSelected(true);
    }
    catch (java.beans.PropertyVetoException e) 
    {
    }
    
    //adds handler to frames
    InternalFrameHandler handler = new InternalFrameHandler();
    frame.addInternalFrameListener (handler);
  }
  
  private class InternalFrameHandler extends InternalFrameAdapter
  {
    //when frame is closed
    public void internalFrameClosed(InternalFrameEvent e) 
    {
      openFrameCount--; //lowers frame count by one
    }
  }
  
  //private inner class for button handling
  private class ButtonHandler implements ActionListener 
  {
    public void actionPerformed( ActionEvent event )
    {
      //control statements to differentiate the two buttons
      if( event.getSource() == uButton )
      {
        
        //deciphers the currentframe as the index used for mPanel
        currentFrame = Integer.parseInt( desktop.getSelectedFrame().getTitle() );
        
        //invokes the DrawPanel method to clear the most recent shape according to frame
        mPanel[currentFrame].clearLastShape();
      }
      if( event.getSource() == cButton )
      {
        currentFrame = Integer.parseInt( desktop.getSelectedFrame().getTitle() );
        //invokes the DrawPanel method to clear the screen
        mPanel[currentFrame].clearDrawing();
      }
      if( event.getSource() == rButton )
      {
        currentFrame = Integer.parseInt( desktop.getSelectedFrame().getTitle() );
        //invokes the DrawPanel method to redo recent shapes
        mPanel[currentFrame].redoLastShape(); 
      }
    }
  }
  
  //private inner class for checkBox handling
  private class CheckBoxHandler implements ItemListener 
  {
    public void itemStateChanged( ItemEvent event )
    {
      //if the selection is 'checked'
      if( fCheckBox.isSelected() == true )
      {
        for( int i = 0; i <= openFrameCount; i++)
        {
          //the shape will be filled (rectangle or circle)
          mPanel[i].setCurrentShapeFilled( true );
        }
      }
      if( gCheckBox.isSelected() == true )
      {
        for( int i = 0; i <= openFrameCount; i++)
        {
          //the shape is not filled otherwise
          mPanel[i].setCurrentShapeGradient( true );
          colors2JComboBox.setEnabled( true );
        }
      }
      if( dCheckBox.isSelected() == true )
      {
        for( int i = 0; i <= openFrameCount; i++)
        {
          //the shape is not filled otherwise
          mPanel[i].setCurrentShapeDashed( true );
          sLength.setEnabled( true );
        }
      }
      if( gCheckBox.isSelected() == false )
      {
        for( int i = 0; i <= openFrameCount; i++)
        {
          //the shape is not filled otherwise
          mPanel[i].setCurrentShapeGradient( false );
          colors2JComboBox.setEnabled( false );
        }
      }
      if( fCheckBox.isSelected() == false )
      {
        for( int i = 0; i <= openFrameCount; i++)
        {
          //the shape is not filled otherwise
          mPanel[i].setCurrentShapeFilled( false );
        }
      }
      if( dCheckBox.isSelected() == false )
      {
        for( int i = 0; i <= openFrameCount; i++)
        {
          //the shape is not filled otherwise
          mPanel[i].setCurrentShapeDashed( false );
          sLength.setEnabled( false );
        }
      }
    }
  }
  
  //private inner class for textfield handling
  private class TextFieldHandler implements ActionListener 
  {
    public void actionPerformed( ActionEvent event )
    {
      //determines wheter text inputted is for stroke width or length
      if ( event.getSource() == sWidth )
      {
        //exception handling for non-integer value input
        try
        {
          for( int i = 0; i <= openFrameCount; i++)
          {
            //sets the current stroke width to a constant variable for internal frames
            currentSWidth = Float.parseFloat( event.getActionCommand() );
            
            mPanel[i].setCurrentShapeStrokeWidth( currentSWidth );
          }
        }
        catch ( NumberFormatException numberFormatException )
        {
          JOptionPane.showMessageDialog( null, "You must enter numerical values. Pleast try again.", 
                                        "Stroke Width", JOptionPane.WARNING_MESSAGE );
        }
      }
      
      //strokeLength text field handler
      if ( event.getSource() == sLength )
      {
        //exception for similar format errors
        try
        {
          for( int i = 0; i <= openFrameCount; i++)
          {
            //sets the current stroke length as a constant for all internal frames
            currentSLength = Float.parseFloat( event.getActionCommand() ); 
            
            mPanel[i].setCurrentShapeStrokeLength( currentSLength );
          }
        }
        catch ( NumberFormatException numberFormatException )
        {
          JOptionPane.showMessageDialog( null, "You must enter numerical values. Pleast try again.", 
                                        "Stroke Length", JOptionPane.WARNING_MESSAGE );
        }
      }
    }
  }
  
  //private inner class for Menu handling
  private class MenuItemHandler implements ActionListener 
  { 
    public void actionPerformed (ActionEvent event) 
    {
      //individual handling options when MenuItems are selected
      if ( event.getSource() == fileMenuNewFrame )
      {
        //control statement to determine max number of open frames
        if( openFrameCount < mPanel.length - 1)
        {
          createFrame();
        }
        else
        {
          JOptionPane.showMessageDialog( null, "Sorry you have reached the maximum number of windows!", "Maximum Windows Reached", JOptionPane.ERROR_MESSAGE );
        }
        
      }
      if ( event.getSource() == fileMenuAbout ) 
      {   
        //about prompt
        String text = String.format("%s\n%s\n%s\n%s", "SuperPaint Summative Project", "Final Version", "Created By: Kevin Sito", "Thanks for using the program!");
        JOptionPane.showMessageDialog( null, text, "About SuperPaint", JOptionPane.INFORMATION_MESSAGE );
      }
      if ( event.getSource() == fileMenuPrefs )
      {
        //preferences prompt
        PrefFrame prefPane = new PrefFrame();
        
        prefPane.setSize (300, 250);
        prefPane.setLocation (200 , 200);
        prefPane.setVisible( true );
        
      }
      if ( event.getSource() == fileMenuExit )
      {
        //exit prompt which provides three options before closing
        int close = JOptionPane.showConfirmDialog( null, "Do you wish to close the program?", "SuperPaint Application", JOptionPane.YES_NO_CANCEL_OPTION );
        
        //closes the program if 'YES' is selected
        if( close == JOptionPane.YES_OPTION )
        {
          System.exit(0);
        }
      }
      
      //editMenu item handling
      if( event.getSource() == editMenuUndo )
      {
        //currentFrame index used to allow stand-alone shape reductions/redos
        currentFrame = Integer.parseInt( desktop.getSelectedFrame().getTitle() );
        
        mPanel[currentFrame].clearLastShape();
      }
      if( event.getSource() == editMenuRedo )
      {
        currentFrame = Integer.parseInt( desktop.getSelectedFrame().getTitle() );
        
        mPanel[currentFrame].redoLastShape();
      }
      if ( event.getSource() == editMenuClear )
      {
        currentFrame = Integer.parseInt( desktop.getSelectedFrame().getTitle() );
        
        mPanel[currentFrame].clearDrawing();
      }
      if (event.getSource() == helpMenuResources )
      {
        //Resources prompt
        String text = String.format("%s\n%s\n%s\n%s\n%s", "Mr. Rao's Website:", "http://rao.bethuneci.com/", "Java API:", "http://download.oracle.com/javase/1,5.0/docs/api/", "Thanks to all student resources and Mr. Rao!!");
        JOptionPane.showMessageDialog( null, text, "Resources", JOptionPane.INFORMATION_MESSAGE );
      }
    }
  }
  
}