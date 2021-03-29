
package trama;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import dominio.Personaje;

public class Tablero
{

	private Celda celda[][];
	private BufferedReader br = null;
	private String filePath;
	Celda c;

	private int anchoMaximo;
	private int altoMaximo;
	private int anchoMinimo;
	private int altoMinimo;
	int dimension[];
	int position[];

	public Tablero(String filePath)
	{
		this.filePath = filePath;
		this.setMaximumResolution(10, 10);
		this.setMinimumResolution(5, 5);

		try
		{
			this.br = new BufferedReader(new FileReader(filePath));
		} catch (IOException e)
		{
			System.out.println(e.getMessage());
		}

		celda = fromSourceToBoard(br);
	}

	/*--------------------------------------------------------------------------------*/
	// Asigna las dimensiones del tablero.
	public void setDimension(BufferedReader br)
	{
		dimension = new int[2];
		try
		{
			String transitory[] = br.readLine().split(",");
			dimension[0] = Integer.parseInt(transitory[0]);
			dimension[1] = Integer.parseInt(transitory[1]);
		} catch (FileNotFoundException fnfe)
		{
			fnfe.getMessage();
		} catch (IOException ioe)
		{
			ioe.getMessage();
		}
	}

	// Obtiene las dimensiones del tablero.
	public int[] getDimension()
	{
		return dimension;
	}
	/*--------------------------------------------------------------------------------*/

	/*--------------------------------------------------------------------------------*/
	public Celda[][] fromSourceToBoard(BufferedReader br)
	{
		try
		{
			/*--------------------------------------------------*/
			// Obtener dimensiones:
			setDimension(br);
			int rows = dimension[0];
			int columns = dimension[1];
			// System.out.println("Dimensiones: (" + rows + " x " + columns + ")");
			/*--------------------------------------------------*/

			// El primer 'int' indica las filas (vertical), el segundo las columnas (horizontal).
			celda = new Celda[rows][columns];
			int cuenta = 0;

			// Validamos las dimensiones:
			if (!(this.isInRank()))
			{
				throw new RangoMatrizInvalidoException("Está fuera del rango establecido para el tablero!");	
			}

			for (int h = 0; h < rows; h++)
			{
				String line[] = br.readLine().split(",");

				// Creamos los valores que vamos a utilizar incrementados después.
				int index_bando = 0;
				int index_personaje = 1;
				int index_arma = 2;
				int index_poder = 3;

				for (int w = 0; w < columns; w++)
				{
					int bando = Integer.parseInt(line[index_bando]);
					int personaje = Integer.parseInt(line[index_personaje]);
					int arma = Integer.parseInt(line[index_arma]);
					int poder = Integer.parseInt(line[index_poder]);

					Personaje p = new Personaje(bando, personaje, arma, poder);

					c = new Celda(p);

					celda[h][w] = c;

					index_bando = index_bando + 4;
					index_personaje = index_personaje + 4;
					index_arma = index_arma + 4;
					index_poder = index_poder + 4;
				}
			}
		} catch (IOException ioe)
		{
			System.out.println(ioe.getMessage());
		} catch (RangoMatrizInvalidoException e)
		{
			System.out.println(e.getMessage());
		}

		return celda;
	}

	public Celda getCelda(int x, int y)
	{
		return celda[x][y];
	}
	/*--------------------------------------------------------------------------------*/

	/*--------------------------------------------------------------------------------*/
	public void setPosition(int x, int y)
	{
		position = new int[2];
		position[0] = x;
		position[1] = y;
	}
	/*--------------------------------------------------------------------------------*/

	/*--------------------------------------------------------------------------------*/
	public String getStringCelda(int x, int y)
	{
		String p_1 = Integer.toString(position[0]);
		String p_2 = Integer.toString(position[1]);

		return p_1 + " - " + p_2;
	}
	/*--------------------------------------------------------------------------------*/

	/*--------------------------------------------------------------------------------*/
	private void setMaximumResolution(int anchoMaximo, int altoMaximo)
	{
		this.anchoMaximo = anchoMaximo;
		this.altoMaximo = altoMaximo;
	}

	private void setMinimumResolution(int anchoMinimo, int altoMinimo)
	{
		this.anchoMinimo = anchoMinimo;
		this.altoMinimo = altoMinimo;
	}

	private boolean isInRank()
	{
		int altoTablero = celda.length;
		int anchoTablero = celda[1].length;

		// Validar las filas, 'rows':
		if ((altoTablero > altoMaximo) || (altoTablero < altoMinimo))
		{
			return false;
		}

		// Validar las columnas, 'columns':
		if ((anchoTablero > anchoMaximo) || (anchoTablero < anchoMinimo))
		{
			return false;
		}
		return true;
	}
	/*--------------------------------------------------------------------------------*/

	/*--------------------------------------------------------------------------------*/
	public void writeFile()
	{
		String s = "";
		for(int i = 0; i < dimension[0]; i++)
		{
			for (int j = 0; j < dimension[1]; j++)
			{
				String bando = Integer.toString(celda[i][j].getContent()[0]);
				String personaje = Integer.toString(celda[i][j].getContent()[1]);
				String arma = Integer.toString(celda[i][j].getContent()[2]);
				String poder = Integer.toString(celda[i][j].getContent()[3]);

				if(j == (dimension[0] - 1))
				{
					s += bando + "," + personaje + "," + arma + "," + poder + "\n";
				}
				else
				{
					s += bando + "," + personaje + "," + arma + "," + poder + ",";
				}
			}
		}
		System.out.println(s);
	}
	/*--------------------------------------------------------------------------------*/
}
