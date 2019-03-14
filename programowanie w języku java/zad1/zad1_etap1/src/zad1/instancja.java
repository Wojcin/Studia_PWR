/**
 * Klasa ta implementuje metody pozwalaj�ce:
 * wczyta� list� przedmiot�w z pliku,
 * tworzy� przedmioty,
 * ustawi� maksymaln� wag� plecaka,
 * wybra�, kt�ry algorytm ma rozwi�za� problem plecakowy
 * @autor Przemys�aw Wojcinowicz
 */

package zad1;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Instancja {
	public static ArrayList<Przedmiot> itemList;
	private static int numItems = 0;
	private static int maxWeight;
	
/**
 * Metoda ta pozwala wczyta� dane z pliku
 * @param filename przechowuje nazw� pliku, z kt�rego chcemy wczyta� dane
 * @throws FileNotFoundException wy�wietli b��d, je�li metoda nie znajdzie danego pliku, lub dane w pliku nie b�d� pe�ne
 */
	public void readfile(String filename) throws FileNotFoundException {
		FileInputStream in = new FileInputStream(new File(filename));

		Scanner sc = new Scanner(in);
		itemList = new ArrayList<Przedmiot>();
		numItems = sc.nextInt();

		for (int i = 0; i < numItems; i++) {
			itemList.add(new Przedmiot(sc.nextInt(), sc.nextFloat(), sc.nextInt()));
		}
		maxWeight = sc.nextInt();
		sc.close();
	}
	/**
	 * Metoda pozwala stworzy� przedmioty je�li chcemy wprowadzi� je r�cznie z aplikacji
	 * @param value przechowuje warto�� przedmiotu
	 * @param weight przechowuje wag� przedmiotu
	 */

	public static void enterData(float value, int weight) {

		numItems += 1;
		itemList.add(new Przedmiot(numItems, value, weight));

	}
	
	/**
	 * Metoda pozwala ustawi� maksymaln� wag� plecaka przy wprowadzaniu danych r�cznie
	 * @param max przechowuje warto�� maksymalnej wagi plecaka
	 */
	public static void setMaxWeight(int max)
	{
		maxWeight = max;
	}
	/**
	 * Metoda inicjalizuje list� przedmiot�w
	 */
	public static void setItemList()
	{
		itemList = new ArrayList<Przedmiot>();
	}
	/**
	 * Metoda pozwala wybra� jaki algorytm ma rozwi�za� problem plecakowy
	 * @param option przechowuje, kt�ry algorytm wybrano
	 * @param bundle przechowuje, kt�ry j�zyk wybrano
	 */
	public void solveKnapsackProblem(String option, ResourceBundle bundle) {

		if (option.equals("Brute Force")) {
			Brute_force bf = new Brute_force(maxWeight, numItems);
			bf.startAlgorithm(bundle);

		} else if (option.equals("Greedy")) {
			Greedy gr = new Greedy(maxWeight, numItems);
			gr.startAlgorithm(bundle);
		}
	}

	

}
