
package trama;

import dominio.Caballero;
import dominio.IPersonaje;
import dominio.Personaje;
import dominio.Rey;
import dominio.Soldado;

public class Celda
{

	/*private int bando;
	 private int personaje;
	 private int arma;
	 private int poder;*/
	// private Personaje p;
	private IPersonaje ip;

	public Celda()
	{
	}

	// public Celda(int bando, int personaje, int arma, int poder)
	public Celda(IPersonaje ip)
	{
		/*this.bando = bando;
		 this.personaje = personaje;
		 this.arma = arma;
		 this.poder = poder;*/
		// this.p = p;
		this.ip = ip;
		// ip = new Personaje();
	}

	public int getBando()
	{
		// return bando;
		// return p.getBando();
		return ip.getBando();
	}

	public void setBando(int bando)
	{
		// this.bando = bando;
		// p.setBando(bando);
		ip.setBando(bando);
	}

	public int getPersonaje()
	{
		// return personaje;
		// return p.getType();
		return ip.getType();
	}

	public void setPersonaje(int personaje)
	{
		// this.personaje = personaje;
		// p.setType(personaje);
		switch (personaje)
		{
			case 1:
				// p = new Soldado(p.getBando(), p.getType(), p.getArma(), p.getPoder());
				ip = new Soldado(ip.getBando(), ip.getType(), ip.getArma(), ip.getPoder());
				// p.setType(personaje);
				ip.setType(personaje);
				break;
			case 2:
				// p = new Caballero(p.getBando(), p.getType(), p.getArma(), p.getPoder());
				ip = new Caballero(ip.getBando(), ip.getType(), ip.getArma(), ip.getPoder());
				// p.setType(personaje);
				ip.setType(personaje);
				break;
			case 3:
				// p = new Rey(p.getBando(), p.getType(), p.getArma(), p.getPoder());
				ip = new Rey(ip.getBando(), ip.getType(), ip.getArma(), ip.getPoder());
				// p.setType(personaje);
				ip.setType(personaje);
				break;
			default:
				// p.setType(0);
				ip.setType(0);
				break;
		}
	}

	public int getArma()
	{
		// return arma;
		// return p.getArma();
		return ip.getArma();
	}

	public void setArma(int arma)
	{
		// this.arma = arma;
		// p.setArma(arma);
		ip.setArma(arma);
	}

	public int getPoder()
	{
		// return poder;
		// return p.getPoder();
		return ip.getPoder();
	}

	public void setPoder(int poder)
	{
		// this.poder = poder;
		// p.setPoder(poder);
		ip.setPoder(poder);
	}

	public String getInfo()
	{
		String info = "";

		String type = "";
		switch (getPersonaje())
		{
			case 0:
				type = "celda vacía";
				break;
			case 1:
				type = "\"Soldado\"";
				break;
			case 2:
				type = "\"Caballero\"";
				break;
			case 3:
				type = "\"Rey\"";
				break;
		}

		String b = "";
		switch (getBando())
		{
			case 0:
				b = "\"azul\"";
				break;
			case 1:
				b = "\"rojo\"";
				break;
		}

		String a = "";
		switch (getArma())
		{
			case 0:
				a = "\"sin arma\"";
				break;
			case 1:
				a = "\"cuchillo\"";
				break;
			case 2:
				a = "\"mangual\"";
				break;
			case 3:
				a = "\"espada láser\"";
				break;
		}

		String power = Integer.toString(getPoder());

		if (getPersonaje() == 0)
		{
			b = "celda vacía";
		}

		info = "Personaje: " + type + ". \n"
			+ "Bando: " + b + ". \n"
			+ "Arma: " + a + ". \n"
			+ "Poder: " + power + ".";

		return info;
	}

	// public String showBoard()
	public String showBoard()
	{
		String p = "";
		switch (getPersonaje())
		{
			case 0:
				p = "X";
				break;
			case 1:
				p = "SOLDADO";
				/*if(getBando() == 0)
				 {
				 p = "Soldado (f)";
				 }
				 else
				 {
				 p = "Soldado (v)";
				 }*/
				break;
			case 2:
				p = "CABALLERO";
				/*if(getBando() == 0)
				 {
				 p = "Caballero (f)";
				 }
				 else
				 {
				 p = "Caballero (v)";
				 }*/
				break;
			case 3:
				p = "REY";
				/*if(getBando() == 0)
				 {
				 p = "Rey (f)";
				 }
				 else
				 {
				 p = "Rey (v)";
				 }*/
				break;
		}

		return p;
	}

	public int[] getContent()
	{
		int c[] = new int[4];
		c[0] = getBando();
		c[1] = getPersonaje();
		c[2] = getArma();
		c[3] = getPoder();

		return c;
	}

	public String getBandoFinal(int bando)
	{
		String s = "";
		if (bando == 0)
		{
			s = "AZUL";
		} else
		{
			s = "ROJO";
		}
		return s;
	}
}
