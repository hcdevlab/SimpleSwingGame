
package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import trama.Tablero;
import trama.Window;

public class Main
{

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

			Tablero t = new Tablero("src/original_de_7x6.txt");
			// Tablero t = new Tablero("src/original_de_5x4.txt");

			// La partida se guarda en "src/datosActualizados.txt", para mantener un archivo original disponible.
			// Hay que cambiarlo si se desea usar una partida modificada.
			// Tablero mdt = new Tablero("src/datosActualizados.txt");
			Window w = new Window(t);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
		{
			System.out.println("Problema: " + ex.getMessage());
		}
		/*----------------------------------------------------------------------*/
	}
}
