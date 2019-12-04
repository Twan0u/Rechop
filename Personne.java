public class Personne {
    private boolean estPrioritaire;
    private int dureeService;
    private int arrivee;

    public Personne(boolean estPrioritaire,int arrivee, int duree){
        this.estPrioritaire=estPrioritaire;
        this.arrivee=arrivee;
        this.dureeService=duree;
    }

    public boolean isPrioritaire() {
        return estPrioritaire;
    }

    public int getDureeService() {
        return dureeService;
    }

    public int getArrivee() {
        return arrivee;
    }

}
