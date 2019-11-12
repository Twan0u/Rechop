import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        //menu();
        //affiche(subList("145589321895367"));
        //System.out.println(fact(4));
    }

    /*
    * encoding value menu
     */
    private static void menu(){
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

        if(Character.compare(nb1,nb2)<=0){
            return true;
        }
        return false;
    }

}
