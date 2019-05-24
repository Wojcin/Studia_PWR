/**
 * Ta klasa implementuje przedmioty, kt�re s� potrzebne do rozwi�zania problemu plecakowego.
 * @autor Przemys�aw Wojcinowicz
 * 
 */

package knapsack;

public class Przedmiot 
{
	
	public float value;
	public int weight;
	public int index;
	
	/**
	 * Ten konstruktor buduje przedmiot ze zmiennymi index, value, weight.
	 * @param index przechowuje index przedmiotu, pozwoli okresli� ilo�� przedmiot�w
	 * @param value przechowuje warto�� przedmiotu 
	 * @param weight przechowuje wag� przedmiotu
	 */
	
	public Przedmiot(int index, float value, int weight)
	{
		this.index=index;
		this.value=value;
		this.weight=weight;
	}
	/**
	 * @return Metoda ta zwraca index przedmiotu
	 */
	public int getIndex()
	{
		return index;
	}
	/**
	 * @return Metoda ta zwraca warto�� przedmiotu 
	 */
	public float getValue()
	{
		return value;
	}
	/**
	 * @return Metoda ta zwraca wag� przedmiotu
	 */
	public int getWeight()
	{
		return weight;
	}

}
