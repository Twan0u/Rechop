 
 public class Rechop{ 
 
	 public static void hullDobell(int a, int c, int m, int x){
	 	int y = (a*x+c)%m;
	 	System.out.println(y);
	 }
 
	 public static void main(String[] args){
	 	hullDobell(63, 57, 32767, 356);
	 }
  
 }
