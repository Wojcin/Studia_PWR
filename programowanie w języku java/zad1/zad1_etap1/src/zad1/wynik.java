/**
 * Ta klasa tworzy oraz pozwala wy�wietl� wyniki rozwi�za� problemu plecakowego
 * @autor Przemys�aw Wojcinowicz
 */

package zad1;

import java.util.ResourceBundle;

import javafx.scene.control.Alert;

public class Wynik {
	
	/**
	 * Metoda printResult przyjmuje warto�ci algorithmName, bestValue, bestWeight, numItems, solution, bundle. 
	 * @param algorithmName przechowuje informacje na temat jaki algorytm zosta� u�yty do rozwi�zania problemu
	 * @param bestValue przechowuje jak� najwi�ksz� warto�� plecaka znaleziono
	 * @param bestWeight przechowuje jak� wag� ma plecak przy najlpeszym rozwi�zaniu
	 * @param numItems przechowuje ilo�� wszystkich przedmiot�w 
	 * @param solution przechowuje kt�re przedmioty zosta�y spakowane do plecaka
	 * @param bundle przechowuje informacj� jaki j�zyk zosta� wybrany 
	 */
	
	public static void printResult(String algorithmName, float bestValue, int bestWeight, int numItems,
			boolean[] solution) 
	{
		

		for (int i = 0; i < numItems; i++) {
			if (solution[i]) {
				//ind += Instancja.itemList.get(i).index + " ";
			}
		}
		
		
		

		
	}
}
