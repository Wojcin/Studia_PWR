package zad2;

import java.util.ArrayList;

public class Greedy {
	static ArrayList<Items> orderedList = new ArrayList<Items>();
	static Items curItem;
	private int maxWeight;
	private static int curWeight;
	private int numItems;
	private static long curRatio;
	private static long orderedCurRatio;
	private static long curValue;
	private static boolean solution[];
	// private static boolean added;

	public Greedy(int maxWeight, int numItems) {
		// this.maxWeight = maxWeight;
		// this.numItems = numItems;
	}

	public static void startAlgorithm(int maxWeight, int numItems) {
		solution = new boolean[numItems];
		curRatio = 0;
		orderedCurRatio = 0;
		curValue = 0;
		curWeight = 0;
		algorithm(numItems, maxWeight, numItems);
		//result(numItems);

	}

	public static void algorithm(int numSize, int maxWeight, int numItems) {
		
		boolean added;
		curWeight = 0;
		// dodanie przedmiotu 0
		orderedList.add(Threads.list.get(0));
		for (int i = 1; i < numSize; i++) {
			added = false;
			// pobranie przedmiotu i
			curItem = Threads.list.get(i);
			// obliczenie zaleznosci wartosci od wagi przedmoitu i
			curRatio = curItem.value / curItem.weight;

			for (int j = 0; j < orderedList.size(); j++) {
				// pobranie przedmiotu j
				Items orderedItem = orderedList.get(j);

				// obliczenie zaleznosci wartosci od wagi przedmiotu j
				orderedCurRatio = orderedItem.value / orderedItem.weight;

				// jesli zaleznosc przedmiotu i jest mniejsza od zaleznosci przedmiotu j
				// dodaj przedmiot j
				if (curRatio < orderedCurRatio) {
					orderedList.add(j, curItem);
					added = true;
					break;
				}
			}
			// w przeciwnym wypadku dodaj przedmiot i
			if (!added) {

				orderedList.add(curItem);
			}

		}
		// dopoki obecna waga plecak nie przekroczy maksymalnej wagi plecaka i
		// orderedList nie jest pusta
		while (curWeight < maxWeight && !orderedList.isEmpty()) {

			int highestValueIndex = orderedList.size() - 1;
			
			// jesli waga przedmiotu i obecna waga plecaka nie przekracza maksymalnej wagi
			// plecaka
		
			
			if (curItem.weight + curWeight <= maxWeight) {

				solution[curItem.index] = true;
				// przypisanie nowych wartoci
				curValue += curItem.value;
				curWeight += curItem.weight;
			}
			
			curItem = orderedList.remove(highestValueIndex);
		}
		
	}

	public static String result(int numItems)
	{
		
		String items = " ";
		String name = " Greedy ";
		
		for (int i = 0; i < numItems; i++) {
			if (solution[i]) {
				items += Threads.list.get(i).index + " ";
			}
				
			}
		String result = ("Rozwiazano uzywajac " + name + " Wynik: " + curValue + " Waga: " + curWeight + " Przedmioty: " + items);
		//System.out.println(result);
		return result;
	}


}