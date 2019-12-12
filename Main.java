/** class used to launch the program and display some choices
* <b> works only in console </b>
* <i> colors of the terminal may cause some readability problems </i>
* @author : Antoine Dumont, Antoine Herrent, Antoine Lambert
* @version : %G%
*/


import java.util.ArrayList;
import java.util.Scanner;

public class Main {

/** Colors that can be used inside of an System.out.print
*/

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";


    /** Main method used to launch the program
    *@param args NOT USED
    */
    public static void main(String[] args) {
        printBlueText("      _____              _                   ");
        printBlueText("     |  __ \\            | |                  ");
        printBlueText("     | |__) | ___   ___ | |__    ___   _ __  ");
        printBlueText("     |  _  / / _ \\ / __|| '_ \\  / _ \\ | '_ \\ ");
        printBlueText("     | | \\ \\|  __/| (__ | | | || (_) || |_) |");
        printBlueText("     |_|  \\_\\\\___| \\___||_| |_| \\___/ | .__/ ");
        printBlueText("                                      | |    ");
        printBlueText("                                      |_|    ");
        System.out.println();
        menu();
    }

    /** Menu of the program used to select wich mode run the simulations
    *
    */
    private static void menu() {
        System.out.println();
        printGreenText("MENU");
        printGreenText("Appuyez sur 1 pour générer une suite aléatoire et la tester.");
        printGreenText("Appuyez sur 2 pour effectuer une simulation de liste d'attente.");
        printGreenText("Appuyez sur 3 pour effectuer une simulation de liste d'attente type. L'exemple du cours.");
        printGreenText("Appuyez sur 4 pour effectuer une simulation de liste d'attente avancée.");
        printGreenText("Appuyez sur 5 pour terminer.");

        int res = askUser(null);
        switch (res) {
            case 1:
                RandomNumber.menu();
                menu();
                break;
            case 2:
                menuStation();
                break;
            case 3:
                ArrayList<Personne> simulation = Station.generationSimulation(600, 2, 10, 1.8, null);
                Station.calcule(2, 10, 600, simulation, 1, 20, 5, 35.0, 20.0, 50.0, 40.0, 25.0);
                menu();
                break;
            case 4:
                menuStationAdvanced();
                break;
            case 5:
                return;
            default:
                printError("Veuillez choisir une commande disponible.");
                menu();
                break;
        }

    }


    /** Menu used to select the stations simulation related options
    *
    */
    public static void menuStation() {
        int tempsSimulation = 0;
        int Smin = 0;
        int Smax = 0;
        tempsSimulation = askUser("Quel est le temps de simulation désiré?");
        while (Smin < 2) {
            Smin = askUser("Quel est le nombre de station minimum?");
        }
        Smax = askUser("Quel est le nombre de station maximum?");

        ArrayList<Personne> simulation = Station.generationSimulation(tempsSimulation, Smin, Smax, 1.8, null);
        Station.calcule(Smin, Smax, tempsSimulation, simulation, 0, 0, 0, 35.0, 20.0, 50.0, 40.0, 25.0);
        subMenuStation(simulation, Smin, Smax, tempsSimulation, 35.0, 20.0, 50.0, 40.0, 25.0);
    }

    private static void menuStationAdvanced() {
        int tempsSimulation = 0;
        int Smin = 0;
        int Smax = 0;
        double poisson = 0;
        double prixOccupationStation = 0;
        double prixInoccupationStation = 0;
        double prixClientDevenuOrdinaire = 0;
        double prixClientPrioritaire = 0;
        double prixClientOrdinaire = 0;
        int[] probaServices = new int[6];

        tempsSimulation = askUser("Quel est le temps de simulation désiré?");
        while (Smin < 2) {
            Smin = askUser("Quel est le nombre de station minimum?");
        }
        Smax = askUser("Quel est le nombre de station maximum?");

        poisson = askUser2("Quel est le paramètre de la loi de Poisson pour les arrivées?");
        prixClientPrioritaire = askUser2("Quel est le tarif pour une heure de présence d'un client prioritaire?");
        prixClientOrdinaire = askUser2("Quel est le tarif pour une heure de présence d'un client ordinaire?");
        prixOccupationStation = askUser2("Quel est le tarif pour une heure d'occupation de station?");
        prixInoccupationStation = askUser2("Quel est le tarif pour une heure d'inoccupation de station?");
        prixClientDevenuOrdinaire = askUser2("Quels sont les frais pour un client prioritaire devenu ordinaire?");
        int sum=20000;
        while(sum>10000) {
            sum=0;
            for (int i = 0; i < probaServices.length; i++) {
                probaServices[i] = (int) Math.round(askUser2("Quel est la probabilité que le service dure " + (i + 1) + " minute(s)? Proba entre 0 et 1, 4 chiffres après la virgule") * 10000.0);
                sum+=probaServices[i];
            }
            if(sum>10000){
                System.out.println("Probabilités incorrects, veuillez réencoder.");
            }
        }

        ArrayList<Personne> simulation = Station.generationSimulation(tempsSimulation, Smin, Smax, poisson, probaServices);
        Station.calcule(Smin, Smax, tempsSimulation, simulation, 0, 0, 0, prixOccupationStation, prixInoccupationStation, prixClientDevenuOrdinaire, prixClientPrioritaire, prixClientOrdinaire);
        subMenuStation(simulation, Smin, Smax, tempsSimulation, prixOccupationStation, prixInoccupationStation, prixClientDevenuOrdinaire, prixClientPrioritaire, prixClientOrdinaire);
    }

        /** submenu used in the station part of the program
         *
         */
    private static void subMenuStation(ArrayList<Personne> simulation, int Smin, int Smax, int tempsSimulation, double prixOccupationStation, double prixInoccupationStation, double prixClientDevenuOrdinaire, double prixClientPrioritaire, double prixClientOrdinaire) {
        printGreenText("Appuyez sur 1 si vous désirez voir le détail entre 2 instants.");
        printGreenText("Appuyez sur 2 pour revenir au menu principal.");
        int res = askUser(null);
        int debutDetail = 0;
        int finDetail = 0;
        int SDetail = 0;
        switch (res) {
            case 1:
                SDetail = askUser("Choisissez la station à voir en détail.");
                debutDetail = askUser("Choisissez le début à voir en détail. Commence en t=1.");
                finDetail = askUser("Choisissez le fin à voir en détail.");
                Station.calcule(Smin, Smax, tempsSimulation, simulation, debutDetail, finDetail, SDetail, prixOccupationStation, prixInoccupationStation, prixClientDevenuOrdinaire, prixClientPrioritaire, prixClientOrdinaire);
                subMenuStation(simulation, Smin, Smax, tempsSimulation, prixOccupationStation, prixInoccupationStation, prixClientDevenuOrdinaire, prixClientPrioritaire, prixClientOrdinaire);
                break;
            case 2:
                menu();
                break;
            default:
                printError("Veuillez choisir une commande disponible.");
                subMenuStation(simulation, Smin, Smax, tempsSimulation,prixOccupationStation, prixInoccupationStation, prixClientDevenuOrdinaire, prixClientPrioritaire, prixClientOrdinaire);
        }
    }

    /** print in console a red text
    * @param txt text to display in red
    */
    private static void printError(String txt) {
        System.out.println(ANSI_RED + txt + ANSI_RESET);
    }

    /** print in console a red text
    * @param txt text to display in red
    */
    private static void printBlueText(String txt) {
        System.out.println(ANSI_BLUE + txt + ANSI_RESET);
    }

    /** print in console a red text
    * @param txt text to display in red
    */
    private static void printGreenText(String txt) {
        System.out.println(ANSI_GREEN + txt + ANSI_RESET);
    }

    /** print a message and ask the user for a response until he respond with a valid answer
    * @param message message to display asking for a response
    * @return response of the user
    */
    private static int askUser(String message) {
        while (true) {
            Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
            if (message != null) {
                System.out.println(message);
            }
            System.out.print(">");
            try {
                return Integer.parseInt(scannerObj.nextLine());
            } catch (NumberFormatException e) {
                printError("Erreur - nombre invalide !");
            }
        }
    }
    /** print a message and ask the user for a response until he respond with a valid answer
     * @param message message to display asking for a response
     * @return response of the user
     */
    private static double askUser2(String message) {
        while (true) {
            Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
            if (message != null) {
                System.out.println(message);
            }
            System.out.print(">");
            try {
                return Double.parseDouble(scannerObj.nextLine());
            } catch (NumberFormatException e) {
                printError("Erreur - nombre invalide !");
            }
        }
    }
}
