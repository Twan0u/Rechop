import java.util.Scanner;

public class Main {

    public static void main(String[] args){
System.out.println("      _____              _                   ");
System.out.println("     |  __ \\            | |                  ");
System.out.println("     | |__) | ___   ___ | |__    ___   _ __  ");
System.out.println("     |  _  / / _ \\ / __|| '_ \\  / _ \\ | '_ \\ ");
System.out.println("     | | \\ \\|  __/| (__ | | | || (_) || |_) |");
System.out.println("     |_|  \\_\\\\___| \\___||_| |_| \\___/ | .__/ ");
System.out.println("                                      | |    ");
System.out.println("                                      |_|");


      Scanner myObj = new Scanner(System.in);  // Create a Scanner object

      System.out.println("Sélectionner la valeur de a");
      String a = myObj.nextLine();  // Read user input

      System.out.println("Sélectionner la valeur de c");
      String c = myObj.nextLine();  // Read user input

      System.out.println("Sélectionner la valeur de m");
      String m = myObj.nextLine();  // Read user input

      System.out.println("Sélectionner la valeur de X0");
      String x = myObj.nextLine();  // Read user input


    System.out.println("Les valeurs sélectionnées sont: " + a + ", "+ c + ", "+ m + ", "+ x);
        //affiche(occurency("1241242576"));
        //System.out.println(fact(4));
    }

    /*
    * simple HubbDobell formula
     */
    public static int hubbDobell(int a, int c, int m, int x){
        return((a*x+c)%m);
    }

    /*
    * calcule Pi
     */
    public static double Pi(int i){
        return (i/fact(i-1));
    }

    /*
    * calcul de r[]
     */
    public static int[] occurency(String x){
        int []r=new int[9];
        int size=1;
        boolean lastCheck=true;
        for(int i=1;i<x.length();i++){
            if(!isLess(x.charAt(i-1),x.charAt(i))){
                r[size-1]++;
                size=1;
                i++;
                if(i>x.length()){
                    lastCheck=false;
                }
            }
            else{
                size++;
            }
        }
        if(!lastCheck){
            r[size-1]++;
        }
        return r;
    }

    /*
    * inutile juste pour les tests
     */
    public static void affiche(int tab[]){
        for(int i=0;i<9;i++){
            System.out.println(Integer.toString(tab[i])+" ");
        }
    }

    /*
    * true if nb1 smaller
     */
    public static boolean isLess(char n1, char n2){
        String nb1=Character.toString(n1);
        String nb2=Character.toString(n2);
        if(nb1.compareTo(nb2)<0){
            return true;
        }
        return false;
    }

    /*
    * calcule factorielle
     */
    public static int fact(int i){
        int res=1;
        while(i>1){
            res*=i;
            i--;
        }
        return res;
    }
}
