package zad2;

import java.util.LinkedList;
import java.util.List;

/**
 * Klasa ta pozwala rozwi�za� problem plecakowy metod� si�ow�
 * @author Przemys�aw Wojcinowicz
 */


import java.util.ResourceBundle;


public class Brute_force  {

	private int maxWeight;
	private boolean[] solution;
	private boolean[] current;
	private float curBestValue;
	private int curBestWeight;
	private int curWeight;
	private float curValue;
	private int numItems;
	private String algorithmName = "Brute Force";
	
	/**
	 * Kontruktor inicjalizuje nast�puj�ce zmienne 
	 * @param maxWeight przechowuje maksymalna wage plecaka
	 * @param numItems przechowuje ilo�� wszystkich przedmiot�w
	 */

	public Brute_force(int maxWeight, int numItems) {
		this.maxWeight = maxWeight;
		this.numItems = numItems;
	}

	/**
	 * Metoda:
	 * inicjalizuje tablice dla znalezionego obecnego rozwi�zania,
	 * inicjalizuje tablice dla znalezionego najlepszego rozwi�zania, 
	 * wywo�uje metod� rozwi�zuj�c� problem,
	 * wywo�uje metod�, kt�ra wy�wietla wynik rozwi�zania,
	 * @param bundle przechowuje informacj� jaki j�zyk zosta� wybrany 
	 */
	public void startAlgorithm() {
		curBestValue=0;
		curBestWeight=0;
		current = new boolean[numItems];
		solution = new boolean[numItems];
		algorithm(numItems - 1);
		result();

	}
	/**
	 * Metoda rozwi�zuje problem plecakowy metod� zach�ann� 
	 * @param numSize przechowuje ilo�� wszystkich przedmiot�w 
	 */	 
	public void algorithm(int numSize) {
		if (numSize < 0) {
			curWeight = 0;
			curValue = 0;
			// liczenie waga oraz wartosci
			for (int i = 0; i < numItems; i++) {
				if (current[i]) {
					curWeight += Threads.list.get(i).weight;
					curValue += Threads.list.get(i).value;

				}
			}
			// jesli waga jest mniejsza od maksymalnej oraz nowa wartosc jest wieksza od aktualnej 
			// ustawia nowa wage i wartosc jako dotychczasowe najlepsze 
			if (curWeight <= maxWeight && curValue > curBestValue) {
				curBestValue = curValue;
				curBestWeight = curWeight;
				for (int j = 0; j < solution.length; j++) {
					solution[j] = current[j];
				}
			}
		} else {
			// zaznaczanie ze przedmiot zostal spakowany 
			current[numSize] = true;
			algorithm(numSize - 1);
			// zaznaczanie ze przedmiot nie zostal spakowany
			current[numSize] = false;
			algorithm(numSize - 1);

		}

	}
	
public String result() {
	
		String items = " ";
		String name = " Brute force ";
		System.out.println(curBestValue);
		for (int i = 0; i < numItems-1; i++) {
			if (solution[i]) {
				items += Threads.list.get(i + 1).index + " ";
				
			}
			
			
		}
		
		String result2 = ("Rozwiazano uzywajac " + name + " Wynik: " + curBestValue + " Waga: " + curBestWeight
				+ " Przedmioty: " + items);
		Threads.result = new LinkedList<Solution>();
		if(Threads.result.size() < Threads.size)
		{
		Threads.result.add(new Solution(items, curBestWeight));
		}else
		{
			Threads.result.remove(0);
			Threads.result.add(new Solution(items, curBestWeight));
		}

		System.out.println(result2);
		return result2;
	}

}
