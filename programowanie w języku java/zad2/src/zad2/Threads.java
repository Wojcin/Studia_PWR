package zad2;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Threads extends Thread {
	static List<Items> list = new LinkedList<Items>();
	List<Solution> solution;
	Map<Long, List<Solution>> map;
	private static long value, weight;
	private long random_bean;
	SoftReference<List<Solution>> reference;
	protected final Logger log = Logger.getLogger(getClass().getName());
	private int thread_index;
	int maxWeight = 30;
	static int numItems = 0;

	public static List<Items> generate_items(long bean)
	{
		numItems = (int) bean;
		for (int i = 0; i < bean; i++) {
			value = (i * bean) + 2;
			weight = ((bean/4) * i) + 1;
			list.add(new Items(i, value, weight));
		}
		return list;
	}


	public Threads(Map<Long, List<Solution>> map, long random_bean, SoftReference<List<Solution>> reference, int thread_index) {
		this.map = map;
		this.random_bean = random_bean;
		this.reference = reference;
		this.thread_index = thread_index;
	}

	public void run() {
		synchronized (map) {
			try {
				FileHandler fhandler = new FileHandler("log" + thread_index + "\\log" + thread_index + ".txt");
				SimpleFormatter sformatter = new SimpleFormatter();
				fhandler.setFormatter(sformatter);
				log.addHandler(fhandler);
			} catch (IOException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
			} catch (SecurityException ex) {
				log.log(Level.SEVERE, ex.getMessage(), ex);
			}

			log.log(Level.INFO,
					"Sprawdzenie czy w pamieci znajduje sie instancja o podanym ziarnie. Index watku " + thread_index);
			if (map.containsKey(random_bean)) {
				solution = map.get(random_bean);
				System.out.println("Wynik na liscie");
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} else {
				log.log(Level.INFO, "Generuje instancje na podstawie podanego ziarna Index watku " + thread_index);
				//reference = new SoftReference<List<Items>>(map.put(random_bean, generate_items(random_bean)));
				solution = map.get(random_bean);
				generate_items(random_bean);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				log.log(Level.INFO, "Obliczam wynik Index watku " + thread_index);
				{
					int rand=0;
					//Random r = new Random();
					//rand = r.nextInt(MainMenu.classes.size());
					
				
						try {
							Method m = (MainMenu.classes.get(rand).getMethod("startAlgorithm",
									new Class[] { int.class, int.class }));
							m.invoke(null, maxWeight, numItems);
							reference = new SoftReference<List<Solution>>(map.put(random_bean, Brute_force.result(numItems)));
							//System.out.println(map.get(random_bean));
							
						} catch (NoSuchMethodException | SecurityException | IllegalArgumentException
								| IllegalAccessException | InvocationTargetException e) {
							
							e.printStackTrace();
						}

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

}