import java.util.Scanner;

public class Console implements UserI {

  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_BLACK = "\u001B[30m";
  private static final String ANSI_RED = "\u001B[31m";
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_YELLOW = "\u001B[33m";
  private static final String ANSI_BLUE = "\u001B[34m";
  private static final String ANSI_PURPLE = "\u001B[35m";
  private static final String ANSI_CYAN = "\u001B[36m";
  private static final String ANSI_WHITE = "\u001B[37m";

  public void start(){
    printBlueText("      _____              _                   ");
    printBlueText("     |  __ \\            | |                  ");
    printBlueText("     | |__) | ___   ___ | |__    ___   _ __  ");
    printBlueText("     |  _  / / _ \\ / __|| '_ \\  / _ \\ | '_ \\ ");
    printBlueText("     | | \\ \\|  __/| (__ | | | || (_) || |_) |");
    printBlueText("     |_|  \\_\\\\___| \\___||_| |_| \\___/ | .__/ ");
    printBlueText("                                      | |    ");
    printBlueText("                                      |_|    ");

  }



  public int askUser(String message) {
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

  public void Console(){

  }
  /*
   * Display an array
   */
  public void printTab(String[] xi, String[] ri, String[] pi, String[] npi, String[] khi) {

      System.out.println("---------------------------------------------------------------------------------------");
      System.out.printf("%5s %15s %20s %15s       %20s", "Xi", "ri", "pi", "n.pi", "(ri-n.pi)^2/(n.pi)");
      System.out.println();
      System.out.println("---------------------------------------------------------------------------------------");
      for (int i = 0; i < xi.length; i++) {
          System.out.printf("%5s %15s %20s %15s %20s", xi[i], ri[i], pi[i], npi[i], khi[i]);
          System.out.println();
      }
  }


  public void printError(String message) {
      System.out.println(ANSI_RED + message + ANSI_RESET);
  }

  private static void printBlueText(String message) {
      System.out.println(ANSI_BLUE + message + ANSI_RESET);
  }

}
