/**
 * Ta klasa implementuje przedmioty, kt�re s� potrzebne do rozwi�zania problemu plecakowego.
 * @autor Przemys�aw Wojcinowicz
 * 
 */

package zad1;

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
	

}
