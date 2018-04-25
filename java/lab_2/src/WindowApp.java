import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/* Autor: Przemys�aw Wojcinowicz
 * Data: 10.10.2017
 * Plik: WindowsApp
 * 
 * Aplikacja okienkowa z GUI
 * 
 */

public class WindowApp extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private static final String GREETING_MESSAGE = 
			"Program Komis \n" +
			"Autor: Przemyslaw Wojcinowicz" +
			"Data: 10.10.2017";
	
	public static void main(String[] args)
	{
		
		new WindowApp();
		
	}
		
	
	private Data currentCar;
	
	Font font = new Font("MonoSpaced", Font.BOLD, 12);
	
	// Przyciski
	JButton newButton = new JButton("Nowy pojazd");
	JButton editButton = new JButton("Edytuj dane");
	JButton saveButton = new JButton("Zapisz dane");
	JButton loadButton = new JButton("Wczytaj");
	JButton deleteButton = new JButton("Usu� pojazd");
	JButton infoButton = new JButton("Informacje");
	JButton exitButton = new JButton("Wyj�cie");
	
	// Etykiety wy�wietlane na panelu w g��wnym oknie aplikacji
	JLabel brandLabel = new JLabel("      Marka: ");
	JLabel modelLabel = new JLabel("      Model: ");
	JLabel yearLabel  = new JLabel("        Rok: ");
	JLabel stateLabel = new JLabel("     Status: ");

	// Pola tekstowe wy�wietlane na panelu w g��wnym oknie aplikacji
	JTextField brandField = new JTextField(10);
	JTextField modelField    = new JTextField(10);
	JTextField yearField    = new JTextField(10);
	JTextField stateField     = new JTextField(10);
	
	public WindowApp()
	{
		// Konfiguracja parametr�w g��wnego okna aplikacji
		setTitle("Menu");  
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(270, 270);
		setResizable(false);
		setLocationRelativeTo(null);
		
		newButton.addActionListener(this);
		editButton.addActionListener(this);
		saveButton.addActionListener(this);
		loadButton.addActionListener(this);
		deleteButton.addActionListener(this);
		infoButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		// Zmiana domy�lnego fontu dla wszystkich etykiet
		// U�yto fontu o sta�ej szeroko�ci znak�w, by wyr�wna�
		// szeroko�� wszystkich etykiet.
		brandLabel.setFont(font);
		modelLabel.setFont(font);
		yearLabel.setFont(font);
		stateLabel.setFont(font);
		
		// Zablokowanie mo�liwo�ci edycji tekst�w we wszystkich 
		// polach tekstowych.  (pola nieedytowalne)
		brandField.setEditable(false);
		modelField.setEditable(false);
		yearField.setEditable(false);
		stateField.setEditable(false);
		
		
		// Utworzenie g��wnego panelu okna aplikacji.
		// Domy�lnym mened�erem rozd�adu dla panelu b�dzie
		// FlowLayout, kt�ry uk�ada wszystkie komponenty jeden za drugim.
		JPanel panel = new JPanel();
		
		// Dodanie i rozmieszczenie na panelu wszystkich
				// komponent�w GUI.
				panel.add(brandLabel);
				panel.add(brandField);
				
				panel.add(modelLabel);
				panel.add(modelField);
				
				panel.add(yearLabel);
				panel.add(yearField);
				
				panel.add(stateLabel);
				panel.add(stateField);

				panel.add(newButton);
				panel.add(deleteButton);
				panel.add(saveButton);
				panel.add(loadButton);
				panel.add(editButton);
				panel.add(infoButton);
				panel.add(exitButton);
				
				
				// Umieszczenie Panelu w g��wnym oknie aplikacji.
				setContentPane(panel);
				
				// Wype�nienie p�l tekstowych danymi aktualnej osoby.
				showCurrentCar();
				
				// Pokazanie na ekranie g��wnego okna aplikacji
				// UWAGA: T� instrukcj� nale�y wykona� jako ostatni�
				// po zainicjowaniu i rozmieszczeniu na panelu
				// wszystkich komponent�w GUI.
				// Od tego momentu aplikacja uruchamia g��wn� p�tl� zdarze�
				// kt�ra dzia�a w nowym w�tku niezale�nie od pozosta�ej cz�ci programu.
				setVisible(true);
		
	}
	
	
	/*
	 * Metoda wype�nia wszystkie pola tekstowe danymi
	 * aktualnej osoby.
	 */
	void showCurrentCar() {
		if (currentCar == null) {
			brandField.setText("");
			modelField.setText("");
			yearField.setText("");
			stateField.setText("");
		} else {
			brandField.setText(currentCar.getBrand());
			modelField.setText(currentCar.getModel());
			yearField.setText("" + currentCar.getYear());
			stateField.setText("" + currentCar.getState());
		}
	}
	
	/*
	 * Implementacja interfejsu ActionListener.
	 * 
	 * Metoda actionPerformrd bedzie automatycznie wywo�ywana
	 * do obs�ugi wszystkich zdarze� od obiekt�w, kt�rym jako s�uchacza zdarze�
	 * do��czono obiekt reprezentuj�cy bie��c� instancj� okna aplikacji (referencja this) 
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		// Odczytanie referencji do obiektu, kt�ry wygenerowa� zdarzenie.
		Object eventSource = event.getSource();

		try {
			if (eventSource == newButton) { 
				currentCar = WindowDialog.createNewCar(this);
			}
			if (eventSource == deleteButton) {
				currentCar = null;
			}
			if (eventSource == saveButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazw� pliku");
				if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku.
				Data.printToFile(fileName, currentCar);
			}
			if (eventSource == loadButton) {
				String fileName = JOptionPane.showInputDialog("Podaj nazw� pliku");
				if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku. 
				currentCar = Data.readFromFile(fileName);
			}
			if (eventSource == editButton) {
				if (currentCar == null) throw new ErrorMessages("Brak danych."); 
				WindowDialog.changeCarData(this, currentCar);
			}
			if (eventSource == infoButton) {
				JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
			}
			if (eventSource == exitButton) {
				System.exit(0);
			}
		} catch (ErrorMessages e) {
			// Tu s� wychwytywane wyj�tki zg�aszane przez metody klasy Data
			// gdy nie s� spe�nione ograniczenia na�o�one na dopuszczelne warto�ci 
			// poszczeg�lnych atrybut�w.
			// Wy�wietlanie modalnego okna dialogowego
			// z komunikatem o b��dzie zg�oszonym za pomoc� wyj�tku ErrorMessages.
			JOptionPane.showMessageDialog(this, e.getMessage(), "B��d", JOptionPane.ERROR_MESSAGE);
		}
		
		// Aktualizacja zawarto�ci wszystkich p�l tekstowych.
		showCurrentCar();

	
	}	
}
