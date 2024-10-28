package Simon.Observer;

/**
 * Class Interface Observer Permet de notifier que c'est un observateur.
 *
 * @author tazznico
 */
public interface Observer {

    /**
     * Permet de mettre à jour tout les paramètres notofié par le sujet
     * d'observation
     */
    public void update();
}
