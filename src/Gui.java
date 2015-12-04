
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 * Created by Konrad on 2014-07-03.
 */
public class Gui extends JFrame implements ActionListener
{

    private JTextArea textArea, timerArea;
    private JScrollPane scrollArea;
    private JButton startButton, stopButton, startTimer, refreshButton, testButton;
    private JCheckBox logCheckBox, excelCheckBox;
    private Connection connection;
    private JLabel timerLabel, secLabel, selectPortText;
    private String fileType = "";
    
    private boolean isStopButtonClicked = false;
    private JComboBox<String> portSelectionBox;
    private String selectedPort;
    private boolean logFlag = false;
    private DefaultComboBoxModel<String> dt;

    public Gui()
    {
        connection = new Connection(this);
        initialize();

    }

    public void initialize()
    {
        //frame
        setSize(1000, 700);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setLocation(100, 100);
        setLayout(null);

        //text area and scroll
        textArea = new JTextArea();
        // textArea.setFont(new Font("Consolas", Font.BOLD, 16));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        ((DefaultCaret) textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        textArea.setCaretPosition(textArea.getDocument().getLength());  // makes field scrollable
        scrollArea = new JScrollPane(textArea);
        scrollArea.setBounds(10, 10, 200, 640);
        add(scrollArea);

        //timer set area
        timerArea = new JTextArea();
        textArea.setEditable(true);
        timerArea.setBounds(400, 250, 30, 20);
        add(timerArea);

        //timer label
        timerLabel = new JLabel("Wyłącz po: ");
        timerLabel.setBounds(300, 250, 100, 20);
        add(timerLabel);

        //seconds label
        secLabel = new JLabel("sekundach");
        secLabel.setBounds(450, 250, 100, 20);
        add(secLabel);

        //select port text
        selectPortText = new JLabel("Select port:");
        selectPortText.setBounds(570, 12, 100, 20);
        add(selectPortText);

        //Port box 
        dt = new DefaultComboBoxModel<String>();
        portSelectionBox = new JComboBox<String>();
        portSelectionBox.setModel(dt);
        portSelectionBox.setBounds(650, 10, 150, 25);
        portSelectionBox.addActionListener(this);
        String[] tempPortList = new String[connection.getPortList().length];
        tempPortList = connection.getPortList();
        for ( String item : tempPortList )
        {
            dt.addElement(item);
        }
        add(portSelectionBox);

        //refresh button
        refreshButton = new JButton("Refresh port list");
        refreshButton.setBounds(820, 7, 170, 30);
        refreshButton.addActionListener(this);
        add(refreshButton);

        //start button
        startButton = new JButton("START!");
        startButton.setBounds(300, 10, 150, 50);
        startButton.setBackground(Color.GREEN);
        startButton.addActionListener(this);
        add(startButton);

        //stop button
        stopButton = new JButton("STOP!");
        stopButton.setBounds(300, 100, 150, 50);
        stopButton.setBackground(Color.RED);
        stopButton.addActionListener(this);
        add(stopButton);

        //start timer button
        startTimer = new JButton("START TIMER");
        startTimer.setBounds(550, 245, 120, 30);
        startTimer.addActionListener(this);
        add(startTimer);

        //testbutton
        testButton = new JButton("test");
        testButton.setBounds(550, 380, 120, 30);
        testButton.addActionListener(this);
        add(testButton);
        
        logCheckBox = new JCheckBox(".log file");
        logCheckBox.setBounds(800, 380, 100, 30);
        logCheckBox.addItemListener(new ItemListener()
        {

            @Override
            public void itemStateChanged( ItemEvent e )
            {
                if(e.getStateChange() == 1)
                {
                    fileType = ".log";
                }
                else
                {
                    fileType = "";
                }
            }
        });
        add(logCheckBox);
        
        excelCheckBox = new JCheckBox(".xls (Excel) file");
        excelCheckBox.setBounds(800, 410, 150, 30);
        excelCheckBox.addItemListener(new ItemListener()
        {

            @Override
            public void itemStateChanged( ItemEvent e )
            {
                if(e.getStateChange() == 1)
                {
                    fileType = ".xls";
                }
                else
                {
                    fileType = "";
                }
            }
        });
        add(excelCheckBox);
        
        
        
        

        setFocusable(true);
        setResizable(false);
        setVisible(true);
    }

    public void ItemListener( ItemEvent e )
    {
        
    }
    
    
    @Override
    public void actionPerformed( ActionEvent e )  // Actions in window
    {
        Object action = e.getSource();
        isStopButtonClicked = false;
        Thread GettingDataThread = new Thread(connection);
        if ( action == startButton )
        {
            if ( connection.isPortBusy() )
            {
                JOptionPane.showMessageDialog(null, "Port jest zajęty!");
            } else
            {
                startButton.setText("Processing...");
                startButton.setEnabled(false);
                connection.startConnection(selectedPort, true);
                connection.setThreadFlag(true);
                GettingDataThread.start();
            }
        }
        if ( action == stopButton )
        {
            if ( connection.isPortBusy() )
            {
                isStopButtonClicked = true;
                startButton.setText("START!");
                startButton.setEnabled(true);
                connection.setThreadFlag(false);
                connection.stopConnection();
                setTextField("\n");
//                if(fileType != "")
//                {
//                    SaveRevivedDataToFile(fileType);
//                }
            }

        }
        if ( action == startTimer )
        {

            if ( isIntegerInTimerArea() )	// check if typed string is integer
            {
                TimerClass timer = new TimerClass(this, connection, (Integer.parseInt(timerArea.getText())));
                Thread gettingDataThread = new Thread(connection);
                startButton.setText("Processing...");
                connection.setThreadFlag(true);
                connection.startConnection(selectedPort, logFlag);
                startButton.setEnabled(false);
                gettingDataThread.start();

            } else
            {
                JOptionPane.showMessageDialog(null, "Wpisz liczbę całkowitą większą od ZERA!");
            }
        }
        if ( action == portSelectionBox )
        {
            Object selectedValue = portSelectionBox.getSelectedItem();
            selectedPort = selectedValue.toString();

        }
        if(action == refreshButton)
        {
//        	dt.removeAllElements();
//        	repaint();
//        	String[] tempPortList = connection.getPortList();
//        	for(String item : tempPortList)
//            {
//        		DefaultComboBoxModel<String> dr = (DefaultComboBoxModel<String>) portSelectionBox.getModel();
//            	dr.addElement( item );
//            }
            JOptionPane.showMessageDialog(null, "Jeszcze nie działa. \t : ) ");
        	
            
        }
        if ( action == testButton )
        {
            //JOptionPane.showMessageDialog(null, connection.getData());
            JOptionPane.showMessageDialog(null, "Nic tu nie ma.");
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public String getSelectedPort()
    {
        return selectedPort;
    }

    public String getTextArea()
    {
        return textArea.getText();
    }

    public boolean getStopButtonClickedFlag()
    {
        return isStopButtonClicked;
    }

    public void setStartButtonText( String text )
    {
        startButton.setText(text);
    }

    public void setStartButtonEnable( boolean flag )
    {
        startButton.setEnabled(flag);
    }

    public void setTimerArea( int time )
    {
        timerArea.setText(Integer.toString(time));
    }

     public void setTextField( String text ) // Set text in text field
    {
        textArea.append(text);  //set text line by line
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public boolean isIntegerInTimerArea() 		// check if typed string in timer area is integer
    {
        try
        {
            if ( Integer.parseInt(timerArea.getText()) > 0 )
            {
                return true;
            }
            return false;
        } catch ( Exception e )
        {
            return false;
        }
    }

//    public void SaveRevivedDataToFile(String fileType)
//    {
//        SaveToFile saveToFile = new SaveToFile(1);
//        switch(fileType)
//        {
//            case ".log":
//            {
//                
//            }
//            break;
//                
//            case ".xls":
//            {
//                saveToFile.saveToXLS(connection.getData());
//            }
//            break;
//        }
//    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    public void Timer()
//    {
//        startButton.setText("Processing...");
//        Thread gettingDataThread = new Thread(connection);
//        //Timer timer = new Timer(1000, timerListener); // count per 1 second
//        //timer.start();
//        connection.setThreadFlag(true);
//        connection.startConnection(selectedPort, logFlag);
//
//        gettingDataThread.start();
//
//    }
//
//    ActionListener timerListener = new ActionListener()
//    {
//
//        @Override
//        public void actionPerformed( ActionEvent e )
//        {
//
//            if ( textArea.getText() != "" || textArea.getText() != null )
//            {
//                timerArea.setText(Integer.toString(time));
//
//                if ( time == 0 || isStopButtonClicked )	// if time is 0 or is stop was pressed, timer stops
//                {
//                    startButton.setText("START!");
//                    startButton.setEnabled(true);
//                    timerArea.setText(Integer.toString(time));
//                    setTextField("\n");
//                    connection.setThreadFlag(false);
//                    connection.stopConnection();
//                    ((Timer) e.getSource()).stop();
//                   // setTime(0);
//                }
//                
//                time -= 1;
//            }
//        }
//    };
}
