
import java.awt.EventQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Konrad
 */
public class Main
{
    
    public static void main(String [ ] args)
    {
        EventQueue.invokeLater( new Runnable()
		{
			@Override public void run( )
			{
				new Gui().setVisible( true );
			}
		} );
    }
    
}
