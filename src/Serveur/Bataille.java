package Serveur;

import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Scanner;

public class Bataille {
    int id_joueur1;
    int id_joueur2;

    ArrayList<String> pions_a_poser = new ArrayList<>();
    ArrayList<String> pions_poses = new ArrayList<>();
    //String[] pions_poses = {};
    boolean partie_commencee; //False = partie non commencée; True = partie commencée

    //Initialisation des deux plateaux des joueurs en tant que plateau en 8*8 vides
    int[][] plateau_j1 = new int[][]{   {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0}};

    int[][] plateau_j2 = new int[][]{   {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0}};

    public Bataille(int id_joueur1,int id_joueur2) {
        //Initialisation de l'id du premier joueur selon l'id du client
        id_joueur1 = this.id_joueur1;
        //Initialisation de l'id du second joueur selon l'id du client
        id_joueur2 = this.id_joueur2;

        //Initialisation des différentes pièces à placer
        pions_a_poser.add("Sous-Marin (2 cases)");
        pions_a_poser.add("Contre-Torpilleur (3 cases)");
        pions_a_poser.add("Porte-Avions (4 cases)");

        //On indique que la partie n'a pas encore commencée
        partie_commencee = false;

    }

    public void start()
    {
        //On indique que le partie commence puis on initialise le plateau
        partie_commencee = true;
        initialise_plateau(plateau_j1);
        //initialise_plateau(plateau_j2);
    }

    public void initialise_plateau(int[][] plateau_joueur)
    {
        System.out.println("Choisissez le numéro du bateau que vous voulez placer :");
        int n = 0;
        while (pions_a_poser.size() != 0 ) {
            afficher_plateau(plateau_joueur);
            for (int i = 0; i < pions_a_poser.size(); i++)
            {
                System.out.println(i+1 + ") " + pions_a_poser.get(i));
            }
            int choix = lecture_int(3-n);
            ajout_pion(pions_a_poser.get(choix - 1),1);
            pions_poses.add(pions_a_poser.get(choix - 1));
            pions_a_poser.remove(pions_a_poser.get(choix-1));
            n++;
        }
        afficher_plateau(plateau_joueur);
    }

    public void afficher_plateau(int[][] plateau)
    {
        String message = "";
        System.out.println("   a b c d e f g h");
        for(int i = 0; i < 8; i++ )
        {
            for(int j = 0; j < 8; j++)
            {
                message += " " + Integer.toString(plateau[i][j]);
            }
            System.out.println(i+1 + " " + message);
            message = "";
        }
    }

    public void ajout_pion(String pion, int nb_joueur)
    {
        // Si nb joueur = 1 --> écrire dans plateau_j1
        // Si nb joueur = 2 --> écrire dans plateau_j2
        int [] point_depart;
        Boolean libre;
        ArrayList<Integer> point_fin = new ArrayList<Integer>();
        String lettre_colonne_fin;
        if(nb_joueur == 1)
        {
            switch(pion)
            {
                case "Sous-Marin (2 cases)":
                    System.out.println("Ce bateau se dispose sur 2 cases à l'horizontale ou la verticale !\n" +
                            "Entrez la coordonnées de la case de départ du bateau");
                    point_depart = lecture_coordonees();
                    libre = verif_libre(point_depart, nb_joueur);
                    while(!libre) {
                        System.out.println("Cette case est déjà occupée !");
                        point_depart = lecture_coordonees();
                        libre = verif_libre(point_depart, nb_joueur);
                    }

                    point_fin = direction_libre(point_depart,2);

                    System.out.println("Voici les différentes positions possibles :");
                    for(int i = 0; i < point_fin.size(); i = i+2)
                    {
                        switch(point_fin.get(i))
                        {
                            case 0:
                                lettre_colonne_fin = "a";
                                break;
                            case 1:
                                lettre_colonne_fin = "b";
                                break;
                            case 2:
                                lettre_colonne_fin = "c";
                                break;
                            case 3:
                                lettre_colonne_fin = "d";
                                break;
                            case 4:
                                lettre_colonne_fin = "e";
                                break;
                            case 5:
                                lettre_colonne_fin = "f";
                                break;
                            case 6:
                                lettre_colonne_fin = "g";
                                break;
                            case 7:
                                lettre_colonne_fin = "h";
                                break;
                            default:
                                lettre_colonne_fin = "x";
                                break;
                        }
                        System.out.println(Integer.toString(i) + ") " + lettre_colonne_fin + (point_fin.get(i+1)+1));
                    }

                    plateau_j1[point_depart[0]][point_depart[1]] = 1;
                    break;

                case "Contre-Torpilleur (3 cases)":
                    System.out.println("Ce bateau se dispose sur 3 cases à l'horizontale ou la verticale !\n" +
                            "Entrez la coordonnées de la case de départ du bateau");
                    point_depart = lecture_coordonees();
                    libre = verif_libre(point_depart, nb_joueur);
                    while(!libre) {
                        System.out.println("Cette case est déjà occupée !");
                        point_depart = lecture_coordonees();
                        libre = verif_libre(point_depart, nb_joueur);
                    }

                    point_fin = direction_libre(point_depart,3);

                    System.out.println("Voici les différentes positions possibles :");
                    for(int i = 0; i < point_fin.size(); i = i+2)
                    {
                        switch(point_fin.get(i))
                        {
                            case 0:
                                lettre_colonne_fin = "a";
                                break;
                            case 1:
                                lettre_colonne_fin = "b";
                                break;
                            case 2:
                                lettre_colonne_fin = "c";
                                break;
                            case 3:
                                lettre_colonne_fin = "d";
                                break;
                            case 4:
                                lettre_colonne_fin = "e";
                                break;
                            case 5:
                                lettre_colonne_fin = "f";
                                break;
                            case 6:
                                lettre_colonne_fin = "g";
                                break;
                            case 7:
                                lettre_colonne_fin = "h";
                                break;
                            default:
                                lettre_colonne_fin = "x";
                                break;
                        }
                        System.out.println(Integer.toString(i) + ") " + lettre_colonne_fin + (point_fin.get(i+1)+1));
                    }

                    plateau_j1[point_depart[0]][point_depart[1]] = 1;
                    break;

                case "Porte-Avions (4 cases)" :
                    System.out.println("Ce bateau se dispose sur 4 cases à l'horizontale ou la verticale !\n" +
                            "Entrez la coordonnées de la case de départ du bateau");
                    point_depart = lecture_coordonees();
                    libre = verif_libre(point_depart, nb_joueur);
                    while(!libre) {
                        System.out.println("Cette case est déjà occupée !");
                        point_depart = lecture_coordonees();
                        libre = verif_libre(point_depart, nb_joueur);
                    }

                    point_fin = direction_libre(point_depart,4);

                    System.out.println("Voici les différentes positions possibles :");
                    for(int i = 0; i < point_fin.size(); i = i+2)
                    {
                        switch(point_fin.get(i))
                        {
                            case 0:
                                lettre_colonne_fin = "a";
                                break;
                            case 1:
                                lettre_colonne_fin = "b";
                                break;
                            case 2:
                                lettre_colonne_fin = "c";
                                break;
                            case 3:
                                lettre_colonne_fin = "d";
                                break;
                            case 4:
                                lettre_colonne_fin = "e";
                                break;
                            case 5:
                                lettre_colonne_fin = "f";
                                break;
                            case 6:
                                lettre_colonne_fin = "g";
                                break;
                            case 7:
                                lettre_colonne_fin = "h";
                                break;
                            default:
                                lettre_colonne_fin = "x";
                                break;
                        }
                        System.out.println(Integer.toString(i/2) + ") " + lettre_colonne_fin + (point_fin.get(i+1)+1));
                    }
                    System.out.println("Choisissez le numéro correspondant à la disposition que vous souhaitez :");
                    int choice = lecture_int((point_fin.size()/2)-1);
                    int[] tab_emplacement = new int[4];
                    tab_emplacement[0] = point_depart[1];
                    tab_emplacement[1] = point_depart[0];
                    tab_emplacement[2] = point_fin.get(choice);
                    tab_emplacement[3] = point_fin.get(choice+1);


                    //plateau_j1[point_depart[0]][point_depart[1]] = 1;

                    placer_bateau(tab_emplacement, 1);

                    break;

                default:
                    System.out.println("Ce pion n'existe pas !");
            }
        }
        else
        {

        }
    }

    public int lecture_int(int nombre)
    {
        Scanner myScan = new Scanner(System.in);
        System.out.print("-> ");
        int result = myScan.nextInt();
        while(result > nombre){
            System.out.print("-> ");
            result = myScan.nextInt();
        }
        return result;
    }

    public String lecture_str()
    {
        Scanner myScan = new Scanner(System.in);
        System.out.print("-> ");
        String result = myScan.nextLine();
        return result;
    }



    public int[] lecture_coordonees()
    {
        //Scanner coordonees = new Scanner(System.in);
        int colonne_nb;
        System.out.println("Veuillez entrer la colonne :");
        String colonne = lecture_str();
        switch(colonne){
            case "a":
                colonne_nb = 0;
                break;
            case "b":
                colonne_nb = 1;
                break;
            case "c":
                colonne_nb = 2;
                break;
            case "d":
                colonne_nb = 3;
                break;
            case "e":
                colonne_nb = 4;
                break;
            case "f":
                colonne_nb = 5;
                break;
            case "g":
                colonne_nb = 6;
                break;
            case "h":
                colonne_nb = 7;
                break;
            default:
                System.out.println("Error 404");
                colonne_nb = 0;
                break;
        }
        System.out.println("Veuillez entrer la ligne :");
        int ligne = lecture_int(8);
        int[] result = { ligne-1, colonne_nb};
        return result;
    }

    public Boolean verif_libre(int[] emplacement, int id_joueur)
    {
        if(id_joueur == 1)
        {
            if(plateau_j1[emplacement[0]][emplacement[1]] == 1)return false; else return true;
        }
        else
        {
            if(plateau_j2[emplacement[0]][emplacement[1]] == 1)return false; else return true;
        }

    }

    public ArrayList<Integer> direction_libre(int[] point_depart, int taille)
    {
        taille --;
        int ligne = point_depart[0];
        int colonne = point_depart[1];
        System.out.println("Ligne : " + ligne + " Colonne : " + colonne);

        ArrayList<Integer> point_fin = new ArrayList<Integer>();
        //int[] point_fin = new int[0];

        if(ligne-taille >= 0 && colonne >= 0 && colonne < 8)
        {
            point_fin.add(colonne);
            point_fin.add(ligne-taille);
        }

        if(ligne+taille < 8 && colonne >= 0 && colonne < 8)
        {
            point_fin.add(colonne);
            point_fin.add(ligne+taille);
        }

        if(ligne >= 0 && ligne < 8 && colonne-taille >= 0)
        {
            point_fin.add(colonne-taille);
            point_fin.add(ligne);
        }

        if(ligne >= 0 && ligne < 8 && colonne+taille < 8)
        {
            point_fin.add(colonne+taille);
            point_fin.add(ligne);
        }

        return point_fin;
    }

    public void placer_bateau(int[] tab_emplacement, int id_joueur)
    {
        System.out.println(tab_emplacement[0]);
        System.out.println(tab_emplacement[1]);
        System.out.println(tab_emplacement[2]);
        System.out.println(tab_emplacement[3]);
        if(id_joueur == 1){
            if(tab_emplacement[0] == tab_emplacement[2]){
                while(tab_emplacement[1] != tab_emplacement[3]){

                    //A completer à partir de ici

                }
            }
        }
    }

}

