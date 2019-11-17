import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //menu();
        //printTab(null);
        affiche(process(hubbDobell(15,11,7,3)));
        //System.out.print(espectedProbability(10));
    }

    /*
     * encoding value menu
     */
    private static String[] menu() {
        System.out.println("      _____              _                   ");
        System.out.println("     |  __ \\            | |                  ");
        System.out.println("     | |__) | ___   ___ | |__    ___   _ __  ");
        System.out.println("     |  _  / / _ \\ / __|| '_ \\  / _ \\ | '_ \\ ");
        System.out.println("     | | \\ \\|  __/| (__ | | | || (_) || |_) |");
        System.out.println("     |_|  \\_\\\\___| \\___||_| |_| \\___/ | .__/ ");
        System.out.println("                                      | |    ");
        System.out.println("                                      |_|");

        String[] out = new String[5];

        out[0] = askUser("Sélectionner la valeur de a");
        out[1] = askUser("Sélectionner la valeur de c");
        out[2] = askUser("Sélectionner la valeur de m");
        out[3] = askUser("Sélectionner la valeur de X0");
        out[4] = askUser("Sélectionner la valeur du degré d'erreurs");

        System.out.println("Les valeurs sélectionnées sont: " + out[0] + ", " + out[1] + ", " + out[2] + ", " + out[3] + ", " + out[4]);

        return out;
    }

    public static String askUser(String message) {
        Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println(message);
        return scannerObj.nextLine();
        //TODO Validation ?
    }

    /* TODO
     * Display an array
     */
    public static void printTab(String[] tab) {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%5s %15s %10s %10s       %20s", "Xi", "ri", "pi", "n.pi", "(ri-n.p)^2/(n.pi)");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        //TODO
        for (int i = 0; i < 10; i++) {//TODO Foreach
            System.out.printf("%5d %15f %10f %10f %20f", i, 0.0, 0.0, 0.0, 0.0);
            System.out.println();
        }
    }

    /*
     * Generate all the Xn
     */
    private static int [] hubbDobell(int a, int c, int m, int x) {
        int[]X=new int[m];
        for(int i=0;i<m;i++){
            // don't need x0
            x=((a * x + c) % m);
            X[i]=x;
        }
        return X;
    }

    private static int[] process(int tab[]){
        int []R=new int[tab.length];
        int size=1;
        for(int i=0;i<tab.length;i++){
            if(tab[i]>=tab[i+1]){
                //on ne prend pas 0 car impossible
                R[size-1]++;
                size=1;
                i++;
            }
            else{
                size++;
                if(i+1==tab.length-1){
                    R[size-1]++;
                }
            }

        }
        return R;
    }

    /*
     * calculation of p
     */
    private static double espectedProbability(int i) {
        double res = 1;
        int j = i+1;

        while (j > 1) {
            res *= j;
            j--;
        }

        return i/res ;
    }

    /*
     * calculation of all khiCube
     */
    private static double[] khiCube(int[] r, int p, int n) {

        double khi[] = new double[9];

        for (int i = 0; i < r.length; i++) {
            khi[i] = Math.pow(r[i] - n * p, 2) / n * p;
        }

        return khi;
    }

    /*
     * inutile juste pour les tests
     */
    private static void affiche(int tab[]) {
        for (int i = 0; i < tab.length; i++) {
            System.out.println(tab[i]);
        }
    }
}
