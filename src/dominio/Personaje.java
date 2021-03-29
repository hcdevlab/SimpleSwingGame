
package dominio;

public class Personaje implements IPersonaje
{
	int bando;
	int type;
	int arma;
	int poder;

	public Personaje(int bando, int type, int arma, int poder)
	{
		this.bando = bando;
		this.type = type;
		this.arma = arma;
		this.poder = poder;
	}

	@Override
	public void setBando(int bando)
	{
		this.bando = bando;
	}

	@Override
	public int getBando()
	{
		return bando;
	}

	@Override
	public void setType(int definition)
	{
		this.type = definition;
	}

	@Override
	public void setArma(int arma)
	{
		this.arma = arma;
	}

	@Override
	public int getType()
	{
		return type;
	}

	@Override
	public int getArma()
	{
		return arma;
	}

	@Override
	public void setPoder(int poder)
	{
		this.poder = poder;
	}

	@Override
	public int getPoder()
	{
		return poder;
	}
}
