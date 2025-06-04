package observer;

//Interface pour notifier plusieurs observateurs
public interface Contexte {
	void addObserver(Observer observer);

	void removeObserver(Observer observer);

	void notifyObservers();
}
