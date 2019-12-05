/**
* object class used to create clients inside of the system
* @author : Antoine Dumont, Antoine Herrent, Antoine Lambert
* @version : %G%
*/

public class Personne {
    /** Is this person a priority ?*/
    private boolean prior;
    /** time  needed to  execute the service*/
    private int durationService;
    /** moment when it arrives inside of the system*/
    private int arival;

    /** constructor of the personne object
    * @param prior is this person in the priority group
    * @param arival when it's supposed to arrive
    * @param durationService the duration of the traitement
    */
    public Personne(boolean prior,int arival, int durationService){
        this.prior=prior;
        this.arival=arival;
        this.durationService=durationService;
    }

    /** getter for the estPrioritaire variable
    * @return return if this person need to be treated in priority
    */
    public boolean isPrioritaire() {
        return prior;
    }

    /** getter for the duration of service for this client
    * @return time of service duration
    */
    public int getDureeService() {
        return durationService;
    }

    /** getter for the arival time of this person
    * @return the arrival time inside of the system
    */
    public int getArrivee() {
        return arival;
    }

}
