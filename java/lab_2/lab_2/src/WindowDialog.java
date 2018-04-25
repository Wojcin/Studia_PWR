import java.awt.Color;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Program: Aplikacja okienkowa z GUI, kt�ra umo�liwia testowanie 
 *          operacji wykonywanych na obiektach klasy data.
 *    Plik: dataWindowDialog.java
 *          
 *   Autor: Pawe� Rogalinski
 *    Data: pazdziernik 2017 r.
 *
 *
 * Klasa dataWindowDialog implementuje pomocnicze okna dialogowe
 * umo�liwiaj�ce utworzenie i wype�nienie danymi nowego obiektu klasy data
 * oraz modyfikacj� danych dla istniej�cego obiektu klasy data.
 */




public class WindowDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private Data data;
	
	// Utworzenie i inicjalizacja komponent�w do do budowy
	// okienkowego interfejsu u�ytkownika
	JLabel brandLabel =    new JLabel(" Marka: ");
	JLabel modelLabel =    new JLabel("  Model: ");
	JLabel yearLabel =     new JLabel("    Rok:  ");
	JLabel stateLabel =    new JLabel(" Stan: ");

	JTextField brandField = new JTextField(10);
	JTextField modelField = new JTextField(10);
	JTextField yearField = new JTextField(10);
	JComboBox<CarState> jobBox = new JComboBox<CarState>(CarState.values());

	JButton OKButton = new JButton("  OK  ");
	JButton CancelButton = new JButton("Anuluj");
	
	
	
	
	/*
	 * Konstruktor klasy dataWindowDialog.
	 *     parent - referencja do okna aplikacji, z kt�rego
     *              zosta�o wywo�ane to okno dialogowe.
     *     data - referencja do obiektu reprezentuj�cego osob�,
     *              kt�rej dane maj� by� modyfikowane. 
     *              Je�li data jest r�wne null to zostanie utworzony 
     *              nowy obiekt klasy data
	 */
	private WindowDialog(Window parent, Data data)
	{
		// Wywo�anie konstruktora klasy bazowej JDialog.
		// Ta instrukcja pododuje ustawienie jako rodzica nowego okna dialogowego
		// referencji do tego okna, z kt�rego wywo�ano to okno dialogowe.
		// Drugi parametr powoduje ustawienie trybu modalno�ci nowego okna diakogowego
		//       - DOCUMENT_MODAL oznacza, �e okno rodzica b�dzie blokowane.
		super(parent, Dialog.ModalityType.DOCUMENT_MODAL);
		
		// Konfiguracja parametr�w tworzonego okna dialogowego
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(220, 220);
		setLocationRelativeTo(parent);
		
		// zapami�tanie referencji do osoby, kt�rej dane b�d� modyfikowane.
		this.data = data;
		
		// Ustawienie tytu�u okna oraz wype�nienie zawarto�ci p�l tekstowych
		if (data==null){
			setTitle("Dodaj");
		} else{
			setTitle(data.toString());
			brandField.setText(data.getBrand());
			modelField.setText(data.getModel());
			yearField.setText(""+data.getYear());
			jobBox.setSelectedItem(data.getState());
		}
		
		// Dodanie s�uchaczy zdarze� do przycisk�w.
		// UWAGA: s�uchaczem zdarze� b�dzie metoda actionPerformed
		//        zaimplementowana w tej klasie i wywo�ana dla
		//        bie��cej instancji okna dialogowego - referencja this
		OKButton.addActionListener( this );
		CancelButton.addActionListener( this );
		
		// Utworzenie g��wnego panelu okna dialogowego.
		// Domy�lnym mened�erem rozd�adu dla panelu b�dzie
		// FlowLayout, kt�ry uk�ada wszystkie komponenty jeden za drugim.
		JPanel panel = new JPanel();
		
		// Zmiana koloru t�a g��wnego panelu okna dialogowego
		panel.setBackground(Color.green);

		// Dodanie i rozmieszczenie na panelu wszystkich komponent�w GUI.
		panel.add(brandLabel);
		panel.add(brandField);
		
		panel.add(modelLabel);
		panel.add(modelField);
		
		panel.add(yearLabel);
		panel.add(yearField);
		
		panel.add(stateLabel);
		panel.add(jobBox);
		
		panel.add(OKButton);
		panel.add(CancelButton);
		
		// Umieszczenie Panelu w oknie dialogowym.
		setContentPane(panel);
		
		
		// Pokazanie na ekranie okna dialogowego
		// UWAGA: T� instrukcj� nale�y wykona� jako ostatni�
		// po zainicjowaniu i rozmieszczeniu na panelu
		// wszystkich komponent�w GUI.
		// Od tego momentu aplikacja wy�wietla nowe okno dialogowe
		// i bokuje g��wne okno aplikacji, z kt�rego wywo�ano okno dialogowe
		setVisible(true);
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
		Object source = event.getSource();
		
		if (source == OKButton) {
			try {
				if (data == null) { // Utworzenie nowej osoby
					data = new Data(brandField.getText(), modelField.getText());
				} else { // Aktualizacji imienia i nazwiska istniej�cej osoby
					data.setBrand(brandField.getText());
					data.setModel(modelField.getText());
				}
				// Aktualizacja pozosta�ych danych osoby
				data.setYear(yearField.getText());
				data.setState((CarState) jobBox.getSelectedItem());
				
				// Zamkni�cie okna i zwolnienie wszystkich zasob�w.
				dispose();
			} catch (ErrorMessages e) {
				// Tu s� wychwytywane wyj�tki zg�aszane przez metody klasy data
				// gdy nie s� spe�nione ograniczenia na�o�one na dopuszczelne warto�ci 
				// poszczeg�lnych atrybut�w.
				// Wy�wietlanie modalnego okna dialogowego
				// z komunikatem o b��dzie zg�oszonym za pomoc� wyj�tku dataException.
				JOptionPane.showMessageDialog(this, e.getMessage(), "B��d", JOptionPane.ERROR_MESSAGE);
			}
		}

		if (source == CancelButton) {
			// Zamkni�cie okna i zwolnienie wszystkich zasob�w.
			dispose();
		}
	}
	

	/* 
	 * Meoda tworzy pomocnicze okno dialogowe, kt�re tworzy 
	 * nowy obiekt klasy data i umo�liwia wprowadzenie danych
	 * dla nowo utworzonej osoby.
	 * Jako pierwszy parametr nale�y przekaza� referencj� do g��wnego okna
	 * aplikacji, z kt�rego ta metoda jest wywo�ywana.
	 * G��wne okno aplikacji zostanie zablokowane do momentu zamkni�cia
	 * okna dialogowego.
	 * Po zatwierdzeniu zmian przyciskiem OK odbywa si�  walidacja poprawno�ci 
	 * danych w konstruktorze i setterach klasy data. 
	 * Je�li zostan� wykryte niepoprawne dane to zostanie przechwycony wyj�tek 
	 * dataException i zostanie wy�wietlony komunikat o b��dzie.
	 * 
	 *  Po poprawnym wype�nieniu danych metoda zamyka okno dialogowe
	 *  i zwraca referencj� do nowo utworzonego obiektu klasy data.
	 */
	public static Data createNewCar(Window parent) {
		WindowDialog dialog = new WindowDialog(parent, null);
		return dialog.data;
	}

	/* 
	 * Meoda tworzy pomocnicze okno dialogowe, kt�re umo�liwia 
	 * modyfikacj� danych osoby reprezentowanej przez obiekt klasy data,
	 * kt�ry zosta� przekazany jako drugi parametr.
	 * Jako pierwszy parametr nale�y przekaza� referencj� do g��wnego okna
	 * aplikacji, z kt�rego ta metoda jest wywo�ywana.
	 * G��wne okno aplikacji zostanie zablokowane do momentu zamkni�cia
	 * okna dialogowego.
	 * Po zatwierdzeniu zmian przyciskiem OK odbywa si�  walidacja poprawno�ci 
	 * danych w konstruktorze i setterach klasy data. 
	 * Je�li zostan� wykryte niepoprawne dane to zostanie przechwycony wyj�tek 
	 * dataException i zostanie wy�wietlony komunikat o b��dzie.
	 * 
	 *  Po poprawnym wype�nieniu danych metoda aktualizuje dane w obiekcie data
	 *  i zamyka okno dialogowe
	 */
	public static void changeCarData(Window parent, Data data) {
		new WindowDialog(parent, data);
	}

	
	

}
