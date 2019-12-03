import java.util.Random;

public class Station {

    /*
     * calculation of S optimum and cost
     */
    public static void calculate(int Smin, int Smax, int tempsSimulation) {
        int S = Smin;
        double []tabCout=new double[Smax-Smin+1];
        int fileNormale = 0;
        int filePrioritaire = 0;
        int cumulFileNormale = 0;
        int cumulFilePrioritaire = 0;
        int tempsOccupationPrioritaire = 0;
        int tempsOccupationNormale = 0;
        int tempsInoccupation = 0;
        int nbClientDevenuOrdinaire = 0;
        int[] arriveeNormale=new int[tempsSimulation];
        int[] arriveePrioritaire=new int[tempsSimulation];
        for(int i=0;i<tempsSimulation;i++){
            int nA = generateArrival();
            while (nA > 0) {
                if (isPrioritaire()) {
                    arriveePrioritaire[i]++;

                } else {
                    arriveeNormale[i]++;
                }
                nA--;
            }
        }
        while (S <= Smax) {
            fileNormale = 0;
            filePrioritaire = 0;
            cumulFileNormale = 0;
            cumulFilePrioritaire = 0;
            tempsOccupationPrioritaire = 0;
            tempsOccupationNormale = 0;
            tempsInoccupation = 0;
            nbClientDevenuOrdinaire = 0;
            int[] tabDs = new int[S];
            int t = 1;
            while (t <= tempsSimulation) {
                /*int nA = generateArrival();
                while (nA > 0) {
                    if (isPrioritaire()) {
                        if (filePrioritaire < 6) {
                            filePrioritaire++;
                        } else {
                            nbClientDevenuOrdinaire++;
                            fileNormale++;
                        }
                    } else {
                        fileNormale++;
                    }
                    nA--;
                }*/
                fileNormale+=arriveeNormale[t-1];
                int nAPrioritaire=arriveePrioritaire[t-1];
                while(nAPrioritaire>0){
                    if(filePrioritaire<6){
                        filePrioritaire++;
                    }
                    else{
                        nbClientDevenuOrdinaire++;
                        fileNormale++;
                    }
                    nAPrioritaire--;
                }
                int i = 0;
                while (i < S) {
                    if (tabDs[i] == 0) {
                        if (i == 0) {
                            if (filePrioritaire != 0) {
                                filePrioritaire--;
                                tabDs[i] = generateDuration();
                                tabDs[i]--;
                                tempsOccupationPrioritaire++;
                            } else {
                                tempsInoccupation++;
                            }
                        } else {
                            if (fileNormale != 0) {
                                fileNormale--;
                                tabDs[i] = generateDuration();
                                tabDs[i]--;
                                tempsOccupationNormale++;
                            } else {
                                if (filePrioritaire != 0) {
                                    filePrioritaire--;
                                    tabDs[i] = generateDuration();
                                    tabDs[i]--;
                                    tempsOccupationPrioritaire++;
                                } else {
                                    tempsInoccupation++;
                                }
                            }
                        }
                    } else {
                        tabDs[i]--;
                        if (i == 0) {
                            tempsOccupationPrioritaire++;
                        } else {
                            tempsOccupationNormale++;
                        }
                    }
                    i++;
                }
                cumulFileNormale += fileNormale;
                cumulFilePrioritaire += filePrioritaire;
                t++;
            }
            tabCout[S-Smin]=(nbClientDevenuOrdinaire*50.0)+((1/60.0)*(25.0*(cumulFileNormale+tempsOccupationNormale)+40.0*(cumulFilePrioritaire+tempsOccupationPrioritaire)+35.0*(tempsOccupationNormale+tempsOccupationPrioritaire)+20.0*tempsInoccupation));
            S++;
        }
        String leftAlignFormat = "| %-18s | %-25s |%n";

        System.out.format("+--------------------+---------------------------+%n");
        System.out.format("| Nombre de stations |           Coût            |%n");
        System.out.format("+--------------------+---------------------------+%n");
        for (int i = 0; i < tabCout.length; i++) {
            System.out.format(leftAlignFormat, Integer.toString((int)(i+Smin)), String.format("%.2f",tabCout[i])+" euros.");
        }
        System.out.format("+--------------------+---------------------------+%n");

        double[] res=findMin(tabCout);
        System.out.println('\n');
        System.out.println("Le nombre de station optimal est "+ ((int)res[0]+Smin)+" avec un coût minimum de "+String.format("%.2f",res[1])+" euros");
    }

    /*
     * generate time of services
     */
    private static int generateDuration() {
        int random = generateRandomIntIntRange(1, 58);
        if (random < 19) {
            return 1;
        } else if (random > 18 && random < 40) {
            return 2;
        } else if (random > 39 && random < 55) {
            return 3;
        } else if (random > 54 && random < 58) {
            return 4;
        } else if (random > 57 && random < 59) {
            return 5;
        } else {
            return 6;
        }
    }

    /*
     * generate number of arrivals 0-5
     */
    private static int generateArrival() {
        int[] fish = fisher(1.8);
        int random = generateRandomIntIntRange(1, 10000);
        if (random < 1653) {
            return 0;
        } else if (random > 1652 && random < 4628) {
            return 1;
        } else if (random > 4627 && random < 7305) {
            return 2;
        } else if (random > 7304 && random < 8911) {
            return 3;
        } else if (random > 8910 && random < 9634) {
            return 4;
        } else {
            return 5;
        }
    }

    public static int[] fisher(double parameter) {
        int[] res = new int[6];
        double term = Math.pow(Math.E, -1.0 * parameter);
        for (int i = 0; i < 6; i++) {
            res[i] = (int) (((Math.pow(parameter, i) * term) / fact(i)) * 10000);
        }
        return res;
    }

    private static int fact(int i) {
        int res = 1;
        while (i > 1) {
            res *= i;
            i--;
        }
        return res;
    }

    /*
     * generate a random number between min et max
     */
    private static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private static boolean isPrioritaire() {
        int random = generateRandomIntIntRange(1, 10);
        if (random < 4) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Renvoie l'index et la valeur de la plus petite valeur du tableau
     */
    private static double[] findMin(double[] tab) {
        double min = Double.POSITIVE_INFINITY;
        double index = -1.0;
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] < min) {
                min = tab[i];
                index=i;
            }
        }
        double[] res = new double[2];
        res[0] = index;
        res[1] = min;
        return res;
    }
}
