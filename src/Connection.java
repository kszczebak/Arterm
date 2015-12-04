
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.SwingUtilities;
import jssc.SerialPort;
import jssc.SerialPortList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Konrad
 */
public class Connection implements Runnable
{

    private SerialPort serialPort;
    private DataThread dataThread;
    private String portName;
    private String[] portList;
    private Gui gui;
    //private GregorianCalendar time;
    private boolean threadFlag = false;
    private boolean isPortOpen = false;

    public Connection( Gui gui )
    {
        this.gui = gui;
        //this.time = new GregorianCalendar();
    }

    public void startConnection( String portName, boolean logFlag )
    {
        serialPort = new SerialPort(portName);
        try
        {
            if ( serialPort.openPort() )
            {
                isPortOpen = true;
                if ( serialPort.setParams(9600, 8, 1, 0) )
                {
                    dataThread = new DataThread(serialPort, gui, logFlag);
                    dataThread.Initialize();
                }
            }
        } catch ( Exception e )
        {
            System.out.print("Błąd startConnection");
        }
    }

    public void stopConnection()
    {
        try
        {
            serialPort.closePort();
            isPortOpen = false;
            
        } catch ( Exception e )
        {
            System.out.print("Błąd stopConnection");
        }

    }

    public boolean isPortBusy()
    {
        return isPortOpen;
    }

    public String[] getPortList()
    {
        portList = new String[SerialPortList.getPortNames().length];

        portList = SerialPortList.getPortNames();

        return portList;
    }

    public ArrayList<String[]> getData()
    {
        return dataThread.getData();
    }

    public void setThreadFlag( boolean flag )
    {
        threadFlag = flag;
    }

//    public String getTime( )
//	{
//		String timeToString = "";
//		time = ( GregorianCalendar ) GregorianCalendar.getInstance();
//		timeToString = time.get( Calendar.HOUR ) + ":"
//				+ time.get( Calendar.MINUTE ) + ":"
//				+ time.get( Calendar.SECOND ) + "."
//				+ time.get( Calendar.MILLISECOND ) + " ";
//
//		return timeToString;
//	}
    @Override
    public void run()
    {
        String[] recivedData;
        while ( threadFlag )
        {
            recivedData = dataThread.getCurrentRecivedData();

            if ( recivedData != null )
            {
                gui.setTextField(reformatRecivedData(recivedData)); // sending recived data to the textfield
            }
            try
            {
                Thread.sleep(100);
            } catch ( Exception e )
            {
                System.out.print("Błąd run() Connection");
            }
        }
    }

    public String reformatRecivedData( String[] recivedData )
    {
        String temp = recivedData[0] + " " + recivedData[1] + "\n";
        return temp;
    }

}
