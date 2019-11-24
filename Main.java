
import java.util.Scanner;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void printError(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    public static void printBlueText(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    public static void main(String[] args) {
        /*int[] params = menu();
        int a = params[0];
        int c = params[1];
        int m = params[2];
        int x = params[3];
        int n = params[4];
        int e = params[5];*/

        //calcTab(a,c,m,x,n);

        calcTab(8,11,13,3,10);
        //printTab(null);
        //affiche(process(hubbDobell(15,11,7,3)));
        //affiche(piCalculation(6,10,true));
        //affiche(realProbability(hubbDobell(8,11,13,3,10),13));
        //affiche(hubbDobell(8,11,13,3,10));
        //System.out.println(areFirst(4,51));
        //System.out.println(checkA(104,31));
        //System.out.print(isPrime(37));

    }

    /*
     * encoding value menu
     * TODO : - respect condition areFirst()->true
     */
    private static int[] menu() {

        printBlueText("      _____              _                   ");
        printBlueText("     |  __ \\            | |                  ");
        printBlueText("     | |__) | ___   ___ | |__    ___   _ __  ");
        printBlueText("     |  _  / / _ \\ / __|| '_ \\  / _ \\ | '_ \\ ");
        printBlueText("     | | \\ \\|  __/| (__ | | | || (_) || |_) |");
        printBlueText("     |_|  \\_\\\\___| \\___||_| |_| \\___/ | .__/ ");
        printBlueText("                                      | |    ");
        printBlueText("                                      |_|    ");

        int[] out = new int[6];

        int m = askUser("Sélectionner la valeur de m");
        while (m <= 0) {
            printError("Erreur m doit être 0 < m");
            m = askUser("Sélectionner la valeur de m");
        }

        int a = askUser("Sélectionner la valeur de a");
        while (a >= m || a <= 0) {
            printError("Erreur a doit être 0 < a < m");
            a = askUser("Sélectionner la valeur de a");
        }

        while (!checkA(m,a)){
          printError("Erreur a invalide");
          a = askUser("Sélectionner la valeur de a");
        }

        int c = askUser("Sélectionner la valeur de c");
        while (c >= m || c <= 0) {
            printError("Erreur a doit être 0 < c < m");
            c = askUser("Sélectionner la valeur de c");
        }

        while (!areFirst(m,c)){
          printError("Erreur a doit être premier avec m");
          c = askUser("Sélectionner la valeur de c");
        }


        int x = askUser("Sélectionner la valeur de X0");
        while (x >= m || x <= 0) {
            printError("Erreur a doit être 0 < X0 < m");
            x = askUser("Sélectionner la valeur de X0");
        }

        int n = askUser("Sélectionner la valeur de n (le nombre d'entier à générer)");
        int e = askUser("Sélectionner la valeur du degré d'erreurs");

        out[0] = a;
        out[1] = c;
        out[2] = m;
        out[3] = x;
        out[4] = n;
        out[5] = e;

        return out;
    }

    public static void calcTab(int a, int c, int m, int x, int n) {

        //before grouping
        int[] tab = hubbDobell(a, c, m, x, n);
        double[] ri = realProbability(tab, m);
        double[] pi = piCalculation(m, n, false);
        double[] npi = piCalculation(m, n, true);

        String[] xi = new String[ri.length];
        for(int i=0;i<xi.length;i++){
          xi[i] = Integer.toString(i+1);
        }

        printTab(xi, toString(ri), tabToPourcent(pi), toString(npi));

        //after grouping
        double res[]=compress(pi,npi,ri);
        int index=(int)res[0];
        double [] newRi = transform(ri,index,res[3]);
        double[] newPi = transform(pi,index,res[1]);
        double[] newnPi = transform(npi,index,res[2]);
        // taille tableau index+1
        // colonne Xi -> [i -> m]

        String[] newxi = new String[index+1];
        int i=0;
        while(i<newxi.length-1){
          newxi[i] = xi[i];
          i++;
        }
          newxi[newxi.length-1] = "["+i+"->"+m+"]";

      printTab(newxi, toString(newRi), tabToPourcent(newPi), toString(newnPi));
    }

    public static String[] toString(int[] tab){
      String[] out = new String[tab.length];
      for(int i=0;i<tab.length;i++){
        out[i]=Integer.toString(tab[i]);
      }
      return out;
    }

    public static String[] toString(double[] tab){
      String[] out = new String[tab.length];
      for(int i=0;i<tab.length;i++){
        out[i]= String.format("%.6f", tab[i]);;
      }
      return out;
    }

    public static String[] tabToPourcent(double[] tab){
      String[] out = new String[tab.length];
      for(int i=0;i<tab.length;i++){
        Double temp = tab[i]*100;
        out[i]= String.format("%.6f", temp);
        out[i] = out[i] + "%" ;
      }
      return out;
    }

    public static int askUser(String message) {
        while (true) {

            Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println(message);
            System.out.print(">");
            try {
                return Integer.parseInt(scannerObj.nextLine());
            } catch (NumberFormatException e) {
                printError("Erreur - nombre invalide");
            }
        }
    }


    /*
     * Display an array
     */
    public static void printTab(String[]xi, String[] ri, String[] pi, String[] npi) {

        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("%5s %15s %20s %15s       %20s", "Xi", "ri", "pi", "n.pi", "(ri-n.pi)^2/(n.pi)");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------");
        for (int i=0; i < xi.length ; i++ ) {
            System.out.printf("%5s %15s %20s %15s %20s", xi[i] , ri[i], pi[i], npi[i], 0.0);
            System.out.println();
        }
    }

    /*
     * Euclids algorithm
     * true if the greatest common divisor of 2 natural numbers is 1, false otherwise
     */
    private static boolean areFirst(int nb1, int nb2) {

        while (nb2 != 0) {
            int t = nb2;
            nb2 = nb1 % nb2;
            nb1 = t;
        }
        if (nb1 == 1) {
            return true;
        }
        return false;
    }

    private static boolean isPrime(int nb) {
        for (int i = 2; i <= nb / 2; i++) {
            if (nb % i == 0) {
                return false;
            }
        }
        return true;
    }

    /*
     * true if a is good, false otherwise
     */
    private static boolean checkA(int m, int a) {

        if(m%4==0 && (a-1)%4!=0){
            return false;
        }
        int max = m;
        for (int i = 2; i < max; i++) {
            if (m % i == 0) {
                int temp = m / i;
                if ((a - 1) % i != 0 && isPrime(i) || (a - 1) % temp != 0 && isPrime(temp)) {
                    return false;
                }
            }
            max = m / i;
        }

        return true;
    }

    /*
     * Generate all the Xn
     * X0 won't be in the array!
     */
    private static int[] hubbDobell(int a, int c, int m, int x, int n) {
        int[] X = new int[n];
        for (int i = 0; i < n; i++) {
            x = ((a * x + c) % m);
            X[i] = x;
        }
        return X;
    }

    /*
     * Ri calculation
     * The size of  the series will be between 1 et m
     */
    private static double[] realProbability(int tab[], int m) {
        double[] R = new double[m];
        int size = 1;
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] >= tab[i + 1]) {
                R[size - 1]++;
                size = 1;
                i++;
            } else {
                size++;
                if (i + 1 == tab.length - 1) {
                    R[size - 1]++;
                }
            }

        }
        return R;
    }

    /*
     * calculation of pi
     */
    private static double espectedProbability(int i) {
        double res = 1;
        int j = i + 1;

        while (j > 1) {
            res *= j;
            j--;
        }

        return i / res;
    }

    /*
     * Give the array of Pi if needN is false
     * Give the array of n*Pi if needN is true
     */
    private static double[] piCalculation(int m, int n, boolean needN) {
        double tab[] = new double[m];
        for (int i = 0; i < m; i++) {
            tab[i] = espectedProbability(i + 1);
            if (needN) {
                tab[i] *= n;
            }
        }
        return tab;
    }

    /*
     * calculation of all khiCube
     */
    private static double[] khiSquare(int[] r, int[] nPi) {

        double khi[] = new double[r.length];

        for (int i = 0; i < r.length; i++) {
            khi[i] = Math.pow(r[i] - nPi[i], 2) / nPi[i];
        }

        return khi;
    }

    /*
     * Make the grouping
     * renvoie l'index où la contrainte n'est pas respectée
     * la somme des pi à cet endroit
     * la somme des npi à cet endroit
     */
    private static double[] compress(double[] pi, double[] npi, double[] ri) {
        double sumNpi = 0;
        double sumPi = 0;
        double index = -1;
        double sumRi = 0;
        for (int i = 0; i < npi.length; i++) {
            if (npi[i] < 5.0) {
                index = i;
            }
            if (index != -1) {
                sumNpi += npi[i];
                sumPi += pi[i];
                sumRi += ri[i];
            }
        }
        double[] res = new double[4];
        res[0] = index;
        res[1] = sumPi;
        res[2] = sumNpi;
        res[3] = sumRi;
        return res;
    }

    /*
     * renvoie un tableau transformé
     * tab à copier
     * index où il y a un changement
     * sum calculée dans compress
     */
    private static double[] transform(double[] tab, int index, double sum) {
        double[] res = new double[index + 1];
        for (int i = 0; i < index; i++) {
            res[i] = tab[i];
        }
        res[index] = sum;
        return res;
    }

    /*
     * inutile juste pour les tests
     */
    private static void affiche(double tab[]) {
        for (int i = 0; i < tab.length; i++) {
            System.out.println(tab[i]);
        }
    }
}
