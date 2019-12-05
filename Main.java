import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";


    public static void main(String[] args) {
        afficherTextBleu("      _____              _                   ");
        afficherTextBleu("     |  __ \\            | |                  ");
        afficherTextBleu("     | |__) | ___   ___ | |__    ___   _ __  ");
        afficherTextBleu("     |  _  / / _ \\ / __|| '_ \\  / _ \\ | '_ \\ ");
        afficherTextBleu("     | | \\ \\|  __/| (__ | | | || (_) || |_) |");
        afficherTextBleu("     |_|  \\_\\\\___| \\___||_| |_| \\___/ | .__/ ");
        afficherTextBleu("                                      | |    ");
        afficherTextBleu("                                      |_|    ");
        System.out.println();
        menu();
    }

    private static void menu() {
        System.out.println();
        afficherTextVert("MENU");
        afficherTextVert("Appuyez sur 1 pour générer une suite aléatoire et la tester.");
        afficherTextVert("Appuyez sur 2 pour effectuer une simulation de liste d'attente avancée.");
        afficherTextVert("Appuyez sur 3 pour effectuer une simulation de liste d'attente type. L'exemple du cours.");
        afficherTextVert("Appuyez sur 4 pour effectuer une simulation de liste d'attente avancée.");
        afficherTextVert("Appuyez sur 5 pour terminer.");

        int res = demandeUtilisateur(null);
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
                menuStationAvance();
                break;
            case 5:
                return;
            default:
                System.out.println("Veuillez choisir une commande disponible.");
                menu();
                break;
        }

    }

    private static void menuStation() {
        int tempsSimulation = 0;
        int Smin = 0;
        int Smax = 0;
        tempsSimulation = demandeUtilisateur("Quel est le temps de simulation désiré?");
        while (Smin < 2) {
            Smin = demandeUtilisateur("Quel est le nombre de station minimum?");
        }
        Smax = demandeUtilisateur("Quel est le nombre de station maximum?");

        ArrayList<Personne> simulation = Station.generationSimulation(tempsSimulation, Smin, Smax, 1.8, null);
        Station.calcule(Smin, Smax, tempsSimulation, simulation, 0, 0, 0, 35.0, 20.0, 50.0, 40.0, 25.0);
        subMenuStation(simulation, Smin, Smax, tempsSimulation, 35.0, 20.0, 50.0, 40.0, 25.0);
    }

    private static void menuStationAvance() {
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

        tempsSimulation = demandeUtilisateur("Quel est le temps de simulation désiré?");
        while (Smin < 2) {
            Smin = demandeUtilisateur("Quel est le nombre de station minimum?");
        }
        Smax = demandeUtilisateur("Quel est le nombre de station maximum?");

        poisson = demandeUtilisateur2("Quel est le paramètre de la loi de Poisson pour les arrivées?");
        prixClientPrioritaire = demandeUtilisateur2("Quel est le tarif pour une heure de présence d'un client prioritaire?");
        prixClientOrdinaire = demandeUtilisateur2("Quel est le tarif pour une heure de présence d'un client ordinaire?");
        prixOccupationStation = demandeUtilisateur2("Quel est le tarif pour une heure d'occupation de station?");
        prixInoccupationStation = demandeUtilisateur2("Quel est le tarif pour une heure d'inoccupation de station?");
        prixClientDevenuOrdinaire = demandeUtilisateur2("Quels sont les frais pour un client prioritaire devenu ordinaire?");
        int sum=20000;
        while(sum>10000) {
            sum=0;
            for (int i = 0; i < probaServices.length; i++) {
                probaServices[i] = (int) Math.round(demandeUtilisateur2("Quel est la probabilité que le service dure " + (i + 1) + " minute(s)? Proba entre 0 et 1, 4 chiffres après la virgule") * 10000.0);
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

    private static void subMenuStation(ArrayList<Personne> simulation, int Smin, int Smax, int tempsSimulation, double prixOccupationStation, double prixInoccupationStation, double prixClientDevenuOrdinaire, double prixClientPrioritaire, double prixClientOrdinaire) {
        afficherTextVert("Appuyez sur 1 si vous désirez voir le détail entre 2 instants.");
        afficherTextVert("Appuyez sur 2 pour revenir au menu principal.");
        int res = demandeUtilisateur(null);
        int debutDetail = 0;
        int finDetail = 0;
        int SDetail = 0;
        switch (res) {
            case 1:
                SDetail = demandeUtilisateur("Choisissez la station à voir en détail.");
                debutDetail = demandeUtilisateur("Choisissez le début à voir en détail. Commence en t=1.");
                finDetail = demandeUtilisateur("Choisissez le fin à voir en détail.");
                Station.calcule(Smin, Smax, tempsSimulation, simulation, debutDetail, finDetail, SDetail, prixOccupationStation, prixInoccupationStation, prixClientDevenuOrdinaire, prixClientPrioritaire, prixClientOrdinaire);
                subMenuStation(simulation, Smin, Smax, tempsSimulation, prixOccupationStation, prixInoccupationStation, prixClientDevenuOrdinaire, prixClientPrioritaire, prixClientOrdinaire);
                break;
            case 2:
                menu();
                break;
            default:
                System.out.println("Veuillez choisir une commande disponible.");
                subMenuStation(simulation, Smin, Smax, tempsSimulation, prixOccupationStation, prixInoccupationStation, prixClientDevenuOrdinaire, prixClientPrioritaire, prixClientOrdinaire);
        }
    }

    private static void afficherErreur(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    private static void afficherTextBleu(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    private static void afficherTextVert(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    private static int demandeUtilisateur(String message) {
        while (true) {
            Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
            if (message != null) {
                System.out.println(message);
            }
            System.out.print(">");
            try {
                return Integer.parseInt(scannerObj.nextLine());
            } catch (NumberFormatException e) {
                afficherErreur("Erreur - nombre invalide !");
            }
        }
    }

    private static double demandeUtilisateur2(String message) {
        while (true) {
            Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
            if (message != null) {
                System.out.println(message);
            }
            System.out.print(">");
            try {
                return Double.parseDouble(scannerObj.nextLine());
            } catch (NumberFormatException e) {
                afficherErreur("Erreur - nombre invalide !");
            }
        }
    }
}
