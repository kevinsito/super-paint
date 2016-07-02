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
import java.awt.Color;

public class PrefFrame extends JFrame
{
  //constants used to determine shape type
  private final int LINE = 0;
  private final int RECTANGLE = 1;
  private final int OVAL = 2; 
  private final int RRECTANGLE = 3;
  
  private JPanel cJPanel; //center Panel
  private JPanel sJPanel;
  
  private JButton sButton;
  private JButton dButton;
  
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
  
  private PrintWriter writeText;
  
  
  public PrefFrame()
  {
    super( "SuperPaint Preferences" );
    
    cJPanel = new JPanel();
    sJPanel = new JPanel();
    
    sButton = new JButton( "Save" );
    dButton = new JButton( " Default Values" );
    
    
    //initialization of comboboxes and viewable row columns
    colorsJComboBox = new JComboBox( colorNames );
    colorsJComboBox.setMaximumRowCount( 5 );
    //resizing of object 
    colorsJComboBox.setPreferredSize( new Dimension ( 70, 25 ) );
    
    //initialization of second gradient colour combobox
    colors2JComboBox = new JComboBox( colorNames );
    colors2JComboBox.setMaximumRowCount( 5 );
    colors2JComboBox.setPreferredSize( new Dimension ( 70, 25 ) );
    
    //initialization of shapes selection
    shapesJComboBox = new JComboBox( shapeNames );
    shapesJComboBox.setMaximumRowCount( 3 );
    shapesJComboBox.setPreferredSize( new Dimension ( 80, 25 ) );
    
    //textfield items for stroke width and length
    sWidth = new JTextField( 3 );
    sLength = new JTextField( 3 );
    
    //initialization of the filled checkbox
    fCheckBox = new JCheckBox( "Filled", false );
    
    //initialization of gradient checkbox
    gCheckBox = new JCheckBox( "Gradient", false );
    
    //initialization of dashed checkbox
    dCheckBox = new JCheckBox( "Dashed", false );
    
    cJPanel.add( colorsJComboBox );
    cJPanel.add( colors2JComboBox );
    cJPanel.add( shapesJComboBox );
    cJPanel.add( new JLabel( "Stroke Width:" ) );
    cJPanel.add( sWidth );
    cJPanel.add( dCheckBox );
    cJPanel.add( new JLabel( "Stroke Length:" ) );
    cJPanel.add( sLength );
    cJPanel.add( fCheckBox );
    cJPanel.add( gCheckBox );
    
    sJPanel.add( dButton );
    sJPanel.add( sButton );
    
    add( cJPanel, BorderLayout.CENTER );
    add( sJPanel, BorderLayout.SOUTH );
    
    ButtonHandler handler = new ButtonHandler();
    sButton.addActionListener( handler );
    dButton.addActionListener( handler );
    
  }
  
  private class ButtonHandler implements ActionListener 
  {
    public void actionPerformed( ActionEvent event )
    {
      //control statements to differentiate the two buttons
      if( event.getSource() == sButton )
      {
        try
        {
          writeText = new PrintWriter( new FileWriter( "pref.txt" ) );
          
          writeText.println( colors[ colorsJComboBox.getSelectedIndex() ].getRGB() );//norm Colour
          writeText.println( shapesJComboBox.getSelectedIndex() );
          writeText.println( fCheckBox.isSelected() );
          writeText.println( gCheckBox.isSelected() );
          writeText.println( colors[ colorsJComboBox.getSelectedIndex() ].getRGB() );//G1
          writeText.println( colors[ colors2JComboBox.getSelectedIndex() ].getRGB() );
          writeText.println( dCheckBox.isSelected() );
          //writeText.println( currentSWidth );
          //writeText.println( currentSLength );
          
          writeText.close();
          }
        catch( IOException e )
        {
        }
        
        }
      if( event.getSource() == dButton )
      {
        try
        {
          writeText = new PrintWriter( new FileWriter( "pref.txt" ) );
          
          writeText.println( colors[ 0 ] );//norm Colour
          writeText.println( 0 );
          writeText.println( false );
          writeText.println( false );
          writeText.println( colors[ 0 ] );//G1
          writeText.println( colors[ 0 ] );
          writeText.println( false );
          //writeText.println( currentSWidth );
          //writeText.println( currentSLength );
          
          writeText.close();
        }
        catch( IOException e )
        {
        }
        
      }
      }
  }


}