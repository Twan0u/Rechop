/**
 * generation of a serie of random numbers
 *
 * @author : Antoine Dumont, Antoine Herrent, Antoine Lambert
 * @version : %G%
 */

import java.util.ArrayList;
import java.util.Scanner;


public class RandomNumber {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";


    private static void printError(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    private static void printBlueText(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    /** Method used to launch the program
     */
    public static void menu() {

        int m = askUser("Sélectionnez la valeur de m");
        while (m <= 0) {
            printError("Erreur : m doit être 0 < m !");
            m = askUser("Sélectionnez la valeur de m");
        }

        int a = askUser("Sélectionnez la valeur de a");
        while (a >= m || a <= 0) {
            if (a >= m || a <= 0) {
                printError("Erreur : a doit être 0 < a < m !");
            }
            a = askUser("Sélectionnez la valeur de a");
        }

        int c = askUser("Sélectionnez la valeur de c");
        while (c >= m || c <= 0) {
            if (c >= m || c <= 0) {
                printError("Erreur : a doit être 0 < c < m !");
            }
            c = askUser("Sélectionnez la valeur de c");
        }

        int x = askUser("Sélectionnez la valeur de X0");
        while (x >= m || x <= 0) {
            printError("Erreur : a doit être 0 < X0 < m");
            x = askUser("Sélectionnez la valeur de X0");
        }

        calcTab(a, c, m, x);
    }

    /** make the calculation of the array and ask to print it in the console
     * @param a value of the parameter a
     * @param c value of the parameter c
     * @param m value of the parameter m
     * @param x value of the parameter X0
     */
    private static void calcTab(int a, int c, int m, int x) {

        //before grouping
        int[] tab = arrayListToArray(hubbDobell(a, c, m, x));
        int period = tab.length;
        double[] ri = realProbability(tab, period);
        double n = sum(ri);
        double[] pi = piCalculation(period, false,1);
        double[] npi = piCalculation(period, true,n);
        double[] khi = khiSquare(ri, npi);

        String[] xi = new String[ri.length];
        for (int i = 0; i < xi.length; i++) {
            xi[i] = "   " + Integer.toString(i + 1) + "   ";
        }

        printTab(xi, toString(ri), tabToPourcent(pi), toString(npi), toString(khi));
        System.out.println('\n');

        //after grouping
        double[] res = compress(pi, npi, ri);
        int index = (int) res[0];
        double[] newRi = transform(ri, index, res[3]);
        double[] newPi = transform(pi, index, res[1]);
        double[] newNpi = transform(npi, index, res[2]);
        double[] newKhi = khiSquare(newRi, newNpi);

        String[] newxi = new String[index + 1];
        int i = 0;
        while (i < newxi.length - 1) {
            newxi[i] = xi[i];
            i++;
        }
        newxi[newxi.length - 1] = "[" + (i + 1) + "->" + period + "]";


        printTab(newxi, toString(newRi), tabToPourcent(newPi), toString(newNpi), toString(newKhi));
        System.out.println('\n');

        final double[] tabKhi = {3.84, 5.99, 7.81, 9.49, 11.07, 12.59, 14.07, 15.51, 16.92, 18.31, 19.68, 21.03, 22.36, 23.68, 25.00, 26.30, 27.59, 28.87, 30.14, 31.41, 33.92, 36.42, 38.89, 41.34, 43.77};

        if (newNpi.length <2) {
            System.out.println("La période est beaucoup trop petite et la contrainte n'est jamais respectée, v = -1");
            System.out.println("La suite aléatoire est refusée!");
        } else {
            System.out.println("Calcul du degré de liberté: v = " + (index + 1) + "-1 = " + index);
            if (index > tab.length) {
                System.out.println("Erreur, tableau du khi carré limité à dl=30");
            } else {
                double sum = sum(newKhi);
                System.out.println("Somme des khi-carré: "+sum);
                System.out.println("Valeur dans le tableau pour 0.05: "+tabKhi[index-1]);
                if (sum < tabKhi[index - 1]) {
                    System.out.println("La suite aléatoire est acceptée!");
                } else {
                    System.out.println("La suite aléatoire est refusée!");
                }
            }
        }

    }

    private static double sum(double[] tab) {
        double sum = 0;
        for (int i = 0; i < tab.length; i++) {
            sum += tab[i];
        }
        return sum;
    }

    private static String[] toString(double[] tab) {
        String[] out = new String[tab.length];
        for (int i = 0; i < tab.length; i++) {
            out[i] = String.format("%.6f", tab[i]);
        }
        return out;
    }

    private static String[] tabToPourcent(double[] tab) {
        String[] out = new String[tab.length];
        for (int i = 0; i < tab.length; i++) {
            Double temp = tab[i] * 100;
            out[i] = String.format("%.6f", temp);
            out[i] = out[i] + "%";
        }
        return out;
    }

    /*
     * ask to the user and force them to enter a integer
     */
    private static int askUser(String message) {
        while (true) {
            Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println(message);
            System.out.print(">");
            try {
                return Integer.parseInt(scannerObj.nextLine());
            } catch (NumberFormatException e) {
                printError("Erreur - nombre invalide !");
            }
        }
    }


    /*
     * Display an array
     */
    private static void printTab(String[] xi, String[] ri, String[] pi, String[] npi, String[] khi) {

        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("%5s %15s %20s %15s       %20s", "Xi", "ri", "pi", "n.pi", "(ri-n.pi)^2/(n.pi)");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------");
        for (int i = 0; i < xi.length; i++) {
            System.out.printf("%5s %15s %20s %15s %20s", xi[i], ri[i], pi[i], npi[i], khi[i]);
            System.out.println();
        }
    }


    /*
     * Generate all the Xn
     * X0 won't be in the arrayList!
     */
    private static ArrayList<Integer> hubbDobell(int a, int c, int m, int x) {
        ArrayList<Integer> X = new ArrayList<Integer>();
        while (!X.contains((a * x + c) % m)) {
            x = (a * x + c) % m;
            X.add(x);
        }
        return X;
    }

    /*
     * Convert arrayList to array
     */
    private static int[] arrayListToArray(ArrayList<Integer> arrayList) {
        int size = arrayList.size();
        int[] X = new int[size];
        for (int i = 0; i < size; i++) {
            X[i] = arrayList.remove(0);
        }
        return X;
    }

    /*
     * Ri calculation
     * The size of  the series will be between 1 and period
     */
    private static double[] realProbability(int[] tab, int m) {
        double[] R = new double[m];
        int size = 1;
        for (int i = 0; i < tab.length - 1; i++) {
            if (tab[i] >= tab[i + 1]) {
                R[size - 1]++;
                size = 1;
                i++;
            } else {
                size++;
                if ((i + 1) > tab.length - 1) {
                    R[size - 1]++;
                }
            }
        }
        return R;
    }

    /*
     * calculation of pi
     */
    private static double expectedProbability(int i) {
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
    private static double[] piCalculation(int period, boolean needN,double n) {
        double[] tab = new double[period];
        for (int i = 0; i < period; i++) {
            tab[i] = expectedProbability(i + 1);
            if (needN) {
                tab[i] *= n;
            }
        }
        return tab;
    }

    /*
     * calculation of all khiCube
     */
    private static double[] khiSquare(double[] r, double[] nPi) {
        double[] khi = new double[r.length];
        for (int i = 0; i < r.length; i++) {
            if (r[i] < 1) {
                khi[i] = nPi[i];
            } else {
                khi[i] = Math.pow(r[i] - nPi[i], 2) / nPi[i];
            }
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
        int index = -1;
        double sumRi = 0;
        for (int i = 0; i < npi.length; i++) {
            if (npi[i] < 5.0) {
                if (index == -1) {
                    index = i;
                }
                sumNpi += npi[i];
                sumPi += pi[i];
                sumRi += ri[i];
            }
        }
        double[] res = new double[4];
        // add error margin od 0.000001
        if (sumNpi < 5.0 - (0.000001) && index > 0) {
            sumNpi += npi[index - 1];
            sumPi += pi[index - 1];
            sumRi += ri[index - 1];
            index--;
        }
        res[0] = index;
        res[1] = sumPi;
        res[2] = sumNpi;
        res[3] = sumRi;
        return res;
    }

    /** return a transformed array
     * @param tab the array to copy
     * @param index where to apply the change
     * @param sum the addition previously calc inside of the compress function
     * @return table whith a part of it compressed at the end
     */
    private static double[] transform(double[] tab, int index, double sum) {
        double[] res = new double[index + 1];
        if (index >= 0) System.arraycopy(tab, 0, res, 0, index);
        res[index] = sum;
        return res;
    }
}
