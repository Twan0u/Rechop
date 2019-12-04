import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

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

    private static void menu() {
        System.out.println();
        printGreenText("MENU");
        printGreenText("Appuyez sur 1 pour générer une suite aléatoire et la tester.");
        printGreenText("Appuyez sur 2 pour effectuer une simulation de liste d'attente avancée.");
        printGreenText("Appuyez sur 3 pour effectuer une simulation de liste d'attente type. L'exemple du cours.");
        printGreenText("Appuyez sur 4 pour terminer.");
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
                ArrayList<Personne> simulation = Station.generateSimulation(600, 2, 10);
                Station.calculate(2, 10, 600, simulation, 1, 20, 5);
                menu();
                break;


            case 4:
                return;
            default:
                System.out.println("Veuillez choisir une commande disponible.");
                menu();
                break;
        }

    }

    public static void menuStation() {
        ArrayList<Personne> simulation = new ArrayList<Personne>();
        int tempsSimulation = 0;
        int Smin = 0;
        int Smax = 0;
        tempsSimulation = askUser("Quel est le temps de simulation désiré?");
        while (Smin < 2) {
            Smin = askUser("Quel est le nombre de station minimum?");
        }
        Smax = askUser("Quel est le nombre de station maximum?");

        simulation = Station.generateSimulation(tempsSimulation, Smin, Smax);
        Station.calculate(Smin, Smax, tempsSimulation, simulation, 0, 0, 0);
        sousMenu(simulation, Smin, Smax, tempsSimulation);

    }

    private static void sousMenu(ArrayList<Personne> simulation, int Smin, int Smax, int tempsSimulation) {
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
                Station.calculate(Smin, Smax, tempsSimulation, simulation, debutDetail, finDetail, SDetail);
                sousMenu(simulation, Smin, Smax, tempsSimulation);
                break;
            case 2:
                menu();
                break;
            default:
                System.out.println("Veuillez choisir une commande disponible.");
                sousMenu(simulation, Smin, Smax, tempsSimulation);
        }

    }

    private static void printError(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    private static void printBlueText(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    private static void printGreenText(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

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
}
