/** part of the program responsable of the station traitement and simulation
* @author : Antoine Dumont, Antoine Herrent, Antoine Lambert
* @version : %G%
*/

import java.util.ArrayList;
import java.util.Random;

public class Station {

    public static ArrayList<Personne> generationSimulation(int tempsSimulation, int Smin, int Smax, double poisson, int[] tab) {
        ArrayList<Personne> simulation = new ArrayList<Personne>();
        int nbPrioritaire = 0;
        int nbNormal = 0;
        double moyenneDuree = 0;
        for (int i = 0; i < tempsSimulation; i++) {
            int nA = generationArrivee(poisson);
            while (nA > 0) {
                if (estPrioritaire()) {
                    int x = 0;
                    if (tab == null) {
                        x = generationDureeService();
                    } else {
                        x = generationDureeServiceAdvanced(tab);
                    }

                    moyenneDuree += x;
                    simulation.add(new Personne(true, i, x));
                    nbPrioritaire++;
                } else {
                    int y = 0;
                    if (tab == null) {
                        y = generationDureeService();
                    } else {
                        y = generationDureeServiceAdvanced(tab);
                    }
                    moyenneDuree += y;
                    simulation.add(new Personne(false, i, y));
                    nbNormal++;
                }
                nA--;
            }
        }
        System.out.println();
        System.out.println("Caractéristiques de la simulation:");
        System.out.println();
        System.out.println("Le temps de simulation est de " + tempsSimulation + " minutes");
        System.out.println("Le nombre de stations minimales est " + Smin);
        System.out.println("Le nombre de stations maximales est " + Smax);
        System.out.println("Le nombre de clients prioritaires est de " + nbPrioritaire + " clients");
        System.out.println("Le nombre de clients normaux est de " + nbNormal + " clients");
        System.out.println("Le nombre total de clients est de " + (nbNormal + nbPrioritaire) + " clients");
        System.out.println("La moyenne de la durée des services est de " + (String.format("%.2f", moyenneDuree / simulation.size())) + " minutes");
        System.out.println();
        return simulation;
    }


    /*
     * calculation of S optimum and cost
     */
    public static void calcule(int Smin, int Smax, int tempsSimulation, ArrayList<Personne> simulation, int debutDetail, int finDetail, int SDetail, double prixOccupationStation, double prixInoccupationStation, double prixClientDevenuOrdinaire, double prixClientPrioritaire, double prixClientOrdinaire) {
        int S = Smin;
        double[] tabCout = new double[Smax - Smin + 1];
        int cumulFileNormale = 0;
        int cumulFilePrioritaire = 0;
        int tempsOccupationPrioritaire = 0;
        int tempsOccupationNormale = 0;
        int tempsInoccupation = 0;
        int nbClientDevenuOrdinaire = 0;
        ArrayList<Personne> fileNormale = new ArrayList<Personne>();
        ArrayList<Personne> filePrioritaire = new ArrayList<Personne>();
        while (S <= Smax) {
            fileNormale.clear();
            filePrioritaire.clear();
            cumulFileNormale = 0;
            cumulFilePrioritaire = 0;
            tempsOccupationPrioritaire = 0;
            tempsOccupationNormale = 0;
            tempsInoccupation = 0;
            nbClientDevenuOrdinaire = 0;
            int[] tabDs = new int[S];
            int[] tabTempsTotal = new int[S];
            String[] tabClient = new String[S];
            int t = 1;
            while (t <= tempsSimulation) {
                for (int j = 0; j < S; j++) {
                    if (tabDs[j] == 0) {
                        tabClient[j] = "Libre";
                        tabTempsTotal[j] = 0;
                    }
                }
                for (int k = 0; k < simulation.size(); k++) {
                    if (simulation.get(k).getArrivee() == (t - 1)) {
                        if (simulation.get(k).isPrioritaire()) {
                            if (filePrioritaire.size() < 6) {
                                filePrioritaire.add(simulation.get(k));
                            } else {
                                fileNormale.add(simulation.get(k));
                                nbClientDevenuOrdinaire++;
                            }
                        } else {
                            fileNormale.add(simulation.get(k));
                        }
                    }
                }
                if (S == SDetail && t > (debutDetail - 1) && t < (finDetail + 1)) {

                    System.out.println("Détail en t=" + t + " minute(s) au début");
                    System.out.println();
                    System.out.println("Il y a exactement " + filePrioritaire.size() + " personne(s) arrivée dans la file prioritaire et " + fileNormale.size() + " personne(s) arrivée dans la file normale");
                    afficherDetail(tabDs, tabClient, tabTempsTotal);
                    System.out.println();
                }
                int i = 0;
                while (i < S) {
                    if (tabDs[i] == 0) {
                        if (i == 0) {
                            if (filePrioritaire.size() != 0) {
                                tabDs[i] = filePrioritaire.get(0).getDureeService();
                                tabClient[i] = "Prioritaire";
                                tabTempsTotal[i] = tabDs[i];
                                filePrioritaire.remove(0);
                                tabDs[i]--;
                                tempsOccupationPrioritaire++;
                            } else {
                                tempsInoccupation++;
                            }
                        } else {
                            if (fileNormale.size() != 0) {
                                tabDs[i] = fileNormale.get(0).getDureeService();
                                tabClient[i] = "Normal";
                                tabTempsTotal[i] = tabDs[i];
                                fileNormale.remove(0);
                                tabDs[i]--;
                                tempsOccupationNormale++;
                            } else {
                                if (filePrioritaire.size() != 0) {
                                    tabDs[i] = filePrioritaire.get(0).getDureeService();
                                    tabClient[i] = "Prioritaire";
                                    tabTempsTotal[i] = tabDs[i];
                                    filePrioritaire.remove(0);
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
                cumulFileNormale += fileNormale.size();
                cumulFilePrioritaire += filePrioritaire.size();
                if (S == SDetail && t > (debutDetail - 1) && t < (finDetail + 1)) {
                    System.out.println("Détail en t=" + t + " minute(s) à la fin");
                    afficherDetail(tabDs, tabClient, tabTempsTotal);
                    System.out.println("Il y a exactement " + filePrioritaire.size() + " personne(s) attendant dans la file prioritaire et " + fileNormale.size() + " personne(s) attendant dans la file normale");
                    System.out.println('\n');
                }
                t++;
            }
            tabCout[S - Smin] = (nbClientDevenuOrdinaire * prixClientDevenuOrdinaire) + ((1 / 60.0) * (prixClientOrdinaire * (cumulFileNormale + tempsOccupationNormale) + prixClientPrioritaire * (cumulFilePrioritaire + tempsOccupationPrioritaire) + prixOccupationStation * (tempsOccupationNormale + tempsOccupationPrioritaire) + prixInoccupationStation * tempsInoccupation));
            S++;
        }
        afficherCouts(tabCout, Smin);
    }

    private static void afficherCouts(double[] tabCout, int Smin) {

        String leftAlignFormat = "| %-18s | %-25s |%n";
        System.out.format("+--------------------+---------------------------+%n");
        System.out.format("| Nombre de stations |           Coût            |%n");
        System.out.format("+--------------------+---------------------------+%n");
        for (int i = 0; i < tabCout.length; i++) {
            System.out.format(leftAlignFormat, Integer.toString((int) (i + Smin)), String.format("%.2f", tabCout[i]) + " euros.");
        }
        System.out.format("+--------------------+---------------------------+%n");

        double[] res = min(tabCout);
        System.out.println();
        System.out.println("Le nombre de station optimal est " + ((int) res[0] + Smin) + " avec un coût minimum de " + String.format("%.2f", res[1]) + " euros");
        System.out.println();
    }

    private static void afficherDetail(int[] tabDs, String[] tabClient, int[] tabTempsTotal) {

        String leftAlignFormat = "| %-12s | %-16s | %-13s | %-12s|%n";
        System.out.format("+--------------+------------------+---------------+-------------+%n");
        System.out.format("|   Guichets   | Statut du client | Temps restant | Temps total |%n");
        System.out.format("+--------------+------------------+---------------+-------------+%n");
        for (int i = 0; i < tabDs.length; i++) {
            System.out.format(leftAlignFormat, "guichet " + (i + 1), tabClient[i], tabDs[i] + " minute(s)", tabTempsTotal[i] + " minute(s)");
        }
        System.out.format("+--------------+------------------+---------------+-------------+%n");
    }

    /*
     * generate time of services
     */
    private static int generationDureeService() {
        int random = generationEntierAleatoire(1, 58);
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

    private static int generationDureeServiceAdvanced(int[] tab) {
        int random = generationEntierAleatoire(1, 10000);
        if (random < tab[0] + 1) {
            return 1;
        } else if (random > tab[0] && random < (sumTab(tab, 1) + 1)) {
            return 2;
        } else if (random > sumTab(tab, 1) && random < (sumTab(tab, 2) + 1)) {
            return 3;
        } else if (random > sumTab(tab, 2) && random < (sumTab(tab, 3) + 1)) {
            return 4;
        } else if (random > sumTab(tab, 3) && random < (sumTab(tab, 4) + 1)) {
            return 5;
        } else {
            return 6;
        }
    }

    private static int sumTab(int[] tab, int index) {
        int sum = 0;
        for (int i = 0; i < index + 1; i++) {
            sum += tab[i];
        }
        return sum;
    }

    /*
     * generate number of arrivals 0-5
     */
    private static int generationArrivee(double parameter) {
        int[] fish = poisson(parameter);
        int random = generationEntierAleatoire(1, 10000);
        if (random < fish[0] + 1) {
            return 0;
        } else if (random > fish[0] && random < (sumTab(fish, 1) + 1)) {
            return 1;
        } else if (random > sumTab(fish, 1) && random < (sumTab(fish, 2) + 1)) {
            return 2;
        } else if (random > sumTab(fish, 2) && random < (sumTab(fish, 3) + 1)) {
            return 3;
        } else if (random > sumTab(fish, 3) && random < (sumTab(fish, 4) + 1)) {
            return 4;
        } else {
            return 5;
        }
    }

    public static int[] poisson(double parametre) {
        int[] res = new int[6];
        double term = Math.pow(Math.E, -1.0 * parametre);
        for (int i = 0; i < 6; i++) {
            res[i] = (int) (((Math.pow(parametre, i) * term) / fact(i)) * 10000);
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
    private static int generationEntierAleatoire(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private static boolean estPrioritaire() {
        int random = generationEntierAleatoire(1, 10);
        if (random < 4) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Renvoie l'index et la valeur de la plus petite valeur du tableau
     */
    private static double[] min(double[] tab) {
        double min = Double.POSITIVE_INFINITY;
        double index = -1.0;
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] < min) {
                min = tab[i];
                index = i;
            }
        }
        double[] res = new double[2];
        res[0] = index;
        res[1] = min;
        return res;
    }
}
