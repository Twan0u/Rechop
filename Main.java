import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        menu();
        printTab(null);
        //affiche(subList("145589321895367"));
        //System.out.println(fact(4));
    }

    /*
    * encoding value menu
     */
    private static String[] menu(){
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

        System.out.println("Les valeurs sélectionnées sont: " + out[0] + ", "+ out[1] + ", "+ out[2] + ", "+ out[3]+ ", " + out[4]);

        return out;
    }

    public static String askUser(String message){
      Scanner scannerObj = new Scanner(System.in);  // Create a Scanner object
      System.out.println(message);
      return scannerObj.nextLine();
        //TODO Validation ?
    }

    /* TODO
    * Display an array
    */
    public static void printTab(String[] tab){
      System.out.println("-----------------------------------------------------------------------------");
      System.out.printf("%5s %15s %10s %10s       %20s", "Xi", "ri", "pi", "n.pi", "(ri-n.p)^2/(n.pi)");
      System.out.println();
      System.out.println("-----------------------------------------------------------------------------");
      //TODO
      for(int i=0;i<10;i++){//TODO Foreach
        System.out.printf("%5d %15f %10f %10f %20f", i, 0.0, 0.0, 0.0, 0.0);
        System.out.println();
      }
    }

    /*
    * simple HubbDobell formula
     */
    private static int hubbDobell(int a, int c, int m, int x){
        return((a*x+c)%m);
    }

    /*
    * calculation of frequency of sublist length
     */
    private static int[] subList(String x){
        int []r=new int[9];
        int size=1;
        for(int i=0;i<x.length()-1;i++){
            if(!isLess(x.charAt(i),x.charAt(i+1))){
                r[size-1]++;
                size=1;
                i++;
            }
            else{
                size++;
                if(i+1==x.length()-1){
                    r[size-1]++;
                }
            }
        }
        return r;
    }

    /*
     * calculation of p
     */
    private static int espectedProbability(int i){
        int res=1;
        int j=i;

        while(j>1){
            res*=j;
            j--;
        }

        return i/res*(i+1);
    }

    /*
     * calculation of all khiCube
     */
    private static double[]khiCube(int[]r, int p, int n){

        double khi[]=new double[9];

        for(int i=0;i<r.length;i++){
            khi[i]=Math.pow(r[i]-n*p,2)/n*p;
        }

        return khi;
    }

    /*
    * inutile juste pour les tests
     */
    private static void affiche(int tab[]){
        for(int i=0;i<9;i++){
            System.out.println(Integer.toString(tab[i])+" ");
        }
    }

    /*
    * true if nb1 smaller
     */
    private static boolean isLess(char nb1, char nb2){

        if(Character.compare(nb1,nb2)<0){
            return true;
        }
        return false;
    }

}
