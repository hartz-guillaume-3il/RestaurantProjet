package observer;

public interface Contexte {
	void addObserver(Observer observer);

	void removeObserver(Observer observer);

	void notifyObservers();
}
