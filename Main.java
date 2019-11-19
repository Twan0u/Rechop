
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //int[] params = menu();
        //int a = params[0];
        //int c = params[1];
        //int m = params[2];
        //int x = params[3];
        //int n = params[4];

        //calcTab(a,c,m,x,n);
        //printTab(null);
        //affiche(process(hubbDobell(15,11,7,3)));
        //affiche(piCalculation(6,10,true));
        //System.out.println(areFirst(4,51));
        //System.out.println(checkA(68,34));
        //System.out.print(isPrime(37));

    }

    /*
     * encoding value menu
     */
    private static int[] menu() {
        System.out.println("      _____              _                   ");
        System.out.println("     |  __ \\            | |                  ");
        System.out.println("     | |__) | ___   ___ | |__    ___   _ __  ");
        System.out.println("     |  _  / / _ \\ / __|| '_ \\  / _ \\ | '_ \\ ");
        System.out.println("     | | \\ \\|  __/| (__ | | | || (_) || |_) |");
        System.out.println("     |_|  \\_\\\\___| \\___||_| |_| \\___/ | .__/ ");
        System.out.println("                                      | |    ");
        System.out.println("                                      |_|");

        int[] out = new int[6];

        out[0] = askUser("Sélectionner la valeur de a");
        out[1] = askUser("Sélectionner la valeur de c");
        out[2] = askUser("Sélectionner la valeur de m");
        out[3] = askUser("Sélectionner la valeur de X0");
        out[4] = askUser("Sélectionner la valeur de n (le nombre d'entier à générer)");
        out[5] = askUser("Sélectionner la valeur du degré d'erreurs");

        System.out.println("Les valeurs sélectionnées sont: " + out[0] + ", " + out[1] + ", " + out[2] + ", " + out[3] + ", " + out[4]+ ", " + out[5]);

        return out;
    }

    public static void calcTab(int a, int c, int m,int x, int n){

      int[] tab = hubbDobell(a,c,m,x,n);

      int[] ri = realProbability(tab,m);
      double[] pi = piCalculation(m,n,false);
      double[] npi = piCalculation(m,n,true);

      printTab(m,ri,pi,npi);
    }

    public static int askUser(String message) { //TODO VALIDATION

        // 0 < m
        //0< a < m
        //0 < c < m
        //0 < x0 <m
        // respect condition areFirst()->true
        // respect condition checkA()->true
      while(true){
        Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println(message);
        try{
          return Integer.parseInt(scannerObj.nextLine());
        }catch(NumberFormatException e){
          System.out.println("erreur - nombre invalide");
        }
      }
    }

    /* TODO
     * Display an array
     */
    public static void printTab(int m, int[] ri, double pi[], double npi[]) {

        System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%5s %15s %10s %10s       %20s", "Xi", "ri", "pi", "n.pi", "(ri-n.pi)^2/(n.pi)");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
        //TODO
        for (int i = 0; i < m; i++) {//TODO Foreach
            System.out.printf("%5d %15d %10f %10f %20f", i, ri[i], pi[i], npi[i], 0.0);
            System.out.println();
        }
    }

    /*
    * Euclids algorithm
    * true if the greatest common divisor of 2 natural numbers is 1, false otherwise
     */
    private static boolean areFirst(int nb1, int nb2){

        while(nb2!=0){
            int t = nb2;
            nb2 = nb1%nb2;
            nb1=t;
        }
        if(nb1==1){
            return true;
        }
        return false;
    }

    private static boolean isPrime(int nb){
        for(int i=2; i<=nb/2;i++){
            if(nb%i==0){
                return false;
            }
        }
        return true;
    }

    /*
     * true if a is good, false otherwise
     */
    private static boolean checkA(int m, int a){

        int max=m;
        for(int i=2; i<max;i++) {
            if (m % i == 0) {
                int temp = m / i;
                if ((a - 1) % i != 0 &&  (a - 1) % temp != 0 && isPrime(i) && isPrime(temp)) {
                    return false;
                }
            }
            max=m/i;
        }

        return true;
    }

    /*
     * Generate all the Xn
     * X0 won't be in the array!
     */
    private static int [] hubbDobell(int a, int c, int m, int x, int n) {
        int[]X=new int[n];
        for(int i=0;i<n;i++){
            x=((a * x + c) % m);
            X[i]=x;
        }
        return X;
    }

    /*
    * Ri calculation
    * The size of  the series will be between 1 et m
     */
    private static int[] realProbability(int tab[], int m){
        int []R=new int[m];
        int size=1;
        for(int i=0;i<m;i++){
            if(tab[i]>=tab[i+1]){
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
     * calculation of pi
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
     * Give the array of Pi if needN is false
     * Give the array of n*Pi if needN is true
     */
    private static double[] piCalculation(int m,int n,boolean needN){
        double tab[]= new double[m];
        for(int i=0; i<m;i++){
            tab[i]=espectedProbability(i+1);
            if(needN){
                tab[i]*=n;
            }
        }
        return tab;
    }

    /*
     * calculation of all khiCube
     */
    private static double[] khiSquare(int[] r, int []nPi) {

        double khi[] = new double[r.length];

        for (int i = 0; i < r.length; i++) {
            khi[i] = Math.pow(r[i] - nPi[i],2) / nPi[i];
        }

        return khi;
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
