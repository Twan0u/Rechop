public class Main {

    public static void main(String[] args) {

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
