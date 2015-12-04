
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Konrad
 */
public class TimerClass implements ActionListener
{

    private Timer timer;
    private Gui gui;
    private Connection connection;
    private int time;

    public TimerClass( Gui gui, Connection connection, int time )
    {
        timer = new Timer(1000, this);
        this.gui = gui;
        this.connection = connection;
        this.time = time;
        timer.start();
    }

    @Override
    public void actionPerformed( ActionEvent e )
    {

        if ( gui.getTextArea() != "" || gui.getTextArea() != null )
        {
            //timerArea.setText(Integer.toString(time));
            gui.setTimerArea(time);

            if ( time == 0 || gui.getStopButtonClickedFlag() )	// if time is 0 or is stop was pressed, timer stops
            {
                gui.setStartButtonText("START!");
                gui.setStartButtonEnable(true);
                gui.setTimerArea(time);
                gui.setTextField("\n");
                connection.setThreadFlag(false);
                connection.stopConnection();
                ((Timer) e.getSource()).stop();
                time = 0;
            }

            time -= 1;
        }

    }

    public int getTime()
    {
        return time;

    }

    public void setTime( int time )
    {
        this.time = time;
    }

}
