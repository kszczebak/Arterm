
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Konrad
 */
public class DataThread implements SerialPortEventListener
{
    private final SerialPort serialPort;
    private ArrayList<String[]> data;
    //private ArrayList<Object[]> data;
    private GregorianCalendar time;
    private boolean logFlag;
    private String[] currentRecivedData;

    public DataThread(SerialPort serialPort,Gui gui, boolean logFlag)
    {
        this.logFlag = logFlag;
        data = new ArrayList<String[]>( );
        //data = new ArrayList<Object[]>( );
        this.time = new GregorianCalendar();
        this.serialPort = serialPort;
  
    }

    public void Initialize()
    {
        try
        {
            serialPort.addEventListener(this);
        } catch ( SerialPortException e )
        {
            System.out.print("Błąd Initialize");
        }
    }
    
    public String getTime( )
	{
		String timeToString = "";
		time = ( GregorianCalendar ) GregorianCalendar.getInstance();
		timeToString = time.get( Calendar.HOUR ) + ":"
				+ time.get( Calendar.MINUTE ) + ":"
				+ time.get( Calendar.SECOND ) + "."
				+ time.get( Calendar.MILLISECOND ) + " ";

		return timeToString;
	}
    
    
    @Override
    public void serialEvent( SerialPortEvent spe )
    {
        
        if(spe.isRXCHAR() || spe.isRXFLAG())
        {
            if(spe.getEventValue()>0)
            {
                GetDataFromSerial();

            }
               
            
        }
    }
    
    public ArrayList<String[]> getData()
    {
        return data;
    }
    
//     public ArrayList<Object[]> getData()
//    {
//        return data;
//    }
    
    public String[] getCurrentRecivedData()
    {
        return currentRecivedData;
    }
    
    public synchronized void GetDataFromSerial()
    {
        
        try
                {
                    String buffer = serialPort.readString(12);
                    buffer = buffer.replace( '\n', ' ' );
                    String timeFromSystem = getTime();
                    String[] tempData = {timeFromSystem,buffer};
                   
                    if(logFlag)
                    {
                        data.add(tempData);
                    }
                    currentRecivedData = tempData;
                    
                    
                } catch ( Exception e )
                {
                    System.out.print("DataThread " + e );
                }
    }
}
