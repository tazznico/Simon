package Simon.Observer;

import java.util.ArrayList;

/**
 * Class Interface Observable Permet de notifier que c'est un sujet
 * d'observation.
 *
 * @author tazznico
 */
public interface Observable {

    /**
     * Permet d'inscrire un observeur au sujet observable
     *
     * @param observer Observeur
     */
    public void subscribe(Observer observer);

    /**
     * Permet dé retirer un observer
     *
     * @param observer Observeur
     */
    public void unsubscribe(Observer observer);

    /**
     * Permet de notifier chaque observateur d'un changement du sujet observable
     *
     * @param listObserver
     */
    public void notify(ArrayList<Observer> listObserver);
}
