import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;


public class DiceGamev1 {

//    public static void main(String[] args) {
//
//        Scanner in = new Scanner(System.in);
//        Random random = new Random();
//
//        // Value for when machine will stop throwing
//        final int MACHINE_CASE = 16;
//
//
//        // declare and create flagArrayRound for player and machine
//        // used to hold results and info on a round from player or machine
//        // index and data:
//        // 0         1: player  0: machine
//        // 1         total times the dice were thrown
//        // 2         total sum or value of these throws
//        // 3         flag for if sum or value exceeds 21   0: false  1: true
//        // 4         flag for blackjack   0: false   1: true
//        // 5         flag only for machine to go for blackjack instead of MACHINE_CASE    0: false  1: true
//
//        int[] flagArrayRoundPlayer = {1, 0, 0, 0, 0};
//
//        int[] flagArrayRoundMachine = {0, 0, 0, 0, 0, 0};
//
//        // rules array
//        // index
//        // index and data
//        // 0         who wins a draw                                          0: machine  1: player  2: no one
//        // 1         can blackjack be challenged and overruled by blackjack   0: no       1: yes
//        int[] rules = {0, 0};
//
//        // flagArrayWinStat
//        // index            data
//        // 0                number of times machine won
//        // 1                number of times player won
//        // 2                number of draws
//        // 3                total number of games played
//
//        int[] flagArrayWinStat = {0, 0, 0, 0};
//
//        printWelcomeMessageRec1(4);
//
//        do {
//
//
//            // get through a round of the player throwing dice and put information into array for late evaluation
//            flagArrayRoundPlayer = getRound(in, random, flagArrayRoundPlayer, MACHINE_CASE, flagArrayWinStat);
//
//            // just for display test
//            //System.out.println(Arrays.toString(flagArrayRoundPlayer));
//
//
//            // if player hit blackjack set flag for machine to go for blackjack
//            if (flagArrayRoundPlayer[4] == 1) {
//                flagArrayRoundMachine[5] = 1;
//            }
//
//            // get through a round of the machine throwing dice
//            flagArrayRoundMachine = getRound(in, random, flagArrayRoundMachine, MACHINE_CASE, flagArrayWinStat);
//
//            // just for display test
//            //System.out.println(Arrays.toString(flagArrayRoundMachine));
//
//
//            // section for forcing specific results into presentWinnerOfRounds for testing
//            //int[] flagArrayRoundPlayerForce = {1, 2, 19, 0, 0};
//            //int[] flagArrayRoundMachineForce = {0, 3, 19, 0, 0, 0};
//            //flagArrayRoundPlayer = flagArrayRoundPlayerForce;
//            //flagArrayRoundMachine = flagArrayRoundMachineForce;
//
//
//            // present the winner and results of the completed round
//            flagArrayWinStat = presentWinnerOfRound(flagArrayRoundPlayer, flagArrayRoundMachine, flagArrayWinStat, rules);
//
//            // increment total amount of games played
//            flagArrayWinStat[3]++;
//
//            // just display test
//            //System.out.println(Arrays.toString(flagArrayWinStat));
//
//            // display the cummulative game stats to the player
//            printOverallWinStats(flagArrayWinStat);
//
//            // ask if player wants another cummulative round
//            System.out.println("Do you wanna try another round and keep results over time? y/n");
//
//        } while (getChoiceFromUser(in));
//
//
//    }


    public static int[] getDiceThrow(int[] dices, Random random) {
        dices[0] = random.nextInt(6) + 1;
        dices[1] = random.nextInt(6) + 1;
        return dices;
    }


    public static int[] presentWinnerOfRound(int[] flagArrayRoundPlayer, int[] flagArrayRoundMachine, int[] flagArrayWinStat, int[] rules) {

        // Information in the flagArrayRoundPlayer and flagArrayRoundMachine
        // index and data:
        // 0         1: player  0: machine
        // 1         total times the dice were thrown
        // 2         total sum or value of these throws
        // 3         flag for if sum or value exceeds 21   0: false  1: true
        // 4         flag for blackjack   0: false   1: true
        // 5         flag only for machine to go for blackjack instead of MACHINE_CASE    0: false  1: true

        // consider transfer information in array into variables to make the logic more clear

        // flagArrayWinStat
        // index      data
        // 0          number of wins machine
        // 1          number of wins player
        // 2          nimber of draws

        // did the player exceed?
        if (flagArrayRoundPlayer[3] == 1) {
            System.out.printf("You hit %d and exceeded\nMachine wins this round\n", flagArrayRoundPlayer[2]);
            flagArrayWinStat[0]++;
            // do we have a player blackjack?
        } else if (flagArrayRoundPlayer[4] == 1) {
            // we do!
            System.out.println("You hit blackjack!!");
            // test rules if possible crush blackjack with blackjack. Machine already threw for blackjack knowing that player hit it
            if (rules[1] == 1) {
                // yes, it can
                // did machine throw blackjack?
                if (flagArrayRoundMachine[4] == 1) {
                    // yes
                    System.out.println("But the machine got blackjack too!\nIntense!!\nMachine wins this round");
                    flagArrayWinStat[0]++;
                } else {
                    // no, so it must have exceeded
                    // since it was set to go for blackjack and didn't succeed
                    System.out.printf("The machine tried to match your blackjack but exceeded with %d!\n You win this round\n", flagArrayRoundMachine[2]);
                    flagArrayWinStat[1]++;
                }
            } else {
                // no, it can't
                // so machines throw is not considered
                System.out.println("You win this round!");
                flagArrayWinStat[1]++;
            }

            // do we have a draw below 21?
        } else if ((flagArrayRoundPlayer[2] == flagArrayRoundMachine[2]) && flagArrayRoundPlayer[2] < 21) {
            // we do
            System.out.printf("It's a draw! You and the machine both got %d\n", flagArrayRoundMachine[2]);
            // test the rules for who wins a draw
            if (rules[0] == 0) {
                // machine wins a draw
                System.out.println("Machine wins this round!");
                flagArrayWinStat[0]++;
            } else if (rules[0] == 1) {
                // player wins a draw
                System.out.println("Player wins this round!");
                flagArrayWinStat[1]++;
            } else if (rules[0] == 2) {
                System.out.println("This round doesn't have a winner!");
                flagArrayWinStat[2]++;
            }

            // is player below 21 without a draw?
        } else if (flagArrayRoundPlayer[2] < 21) {
            // yes

            // did the machine exceed?
            if (flagArrayRoundMachine[3] == 1) {
                // yes
                System.out.printf("Your pushed the machine over the line with your %d points\nYou win this round\n", flagArrayRoundPlayer[2]);
                flagArrayWinStat[1]++;
            } else {
                // no
                // let's show the number of points of player and machine

                // if the machine hit blackjack then change 21 to blackjack for the display
                // by making a String and assign it either blackjack or number of points as a String
                String tempString = (flagArrayRoundMachine[4] == 1) ? "blackjack" : Integer.toString(flagArrayRoundMachine[2]);
                // output
                System.out.printf("You managed to hit %d with %d rools. Machine hit %s with %d rolls\n", flagArrayRoundPlayer[2], flagArrayRoundPlayer[1], tempString, flagArrayRoundMachine[1]);

                // did player get the most points?
                if (flagArrayRoundPlayer[2] > flagArrayRoundMachine[2]) {
                    // yes
                    System.out.println("You win this round");
                    flagArrayWinStat[1]++;
                    // no
                } else {
                    System.out.println("Machine wins this round");
                    flagArrayWinStat[0]++;


                }
            }
        }

        return flagArrayWinStat;
    }


    public static boolean getChoiceFromUser(Scanner in) {
        boolean flagYes = true;
        boolean flagDone = false;
        String tempString = "";

        do {
            tempString = in.nextLine();

            if (tempString.equalsIgnoreCase("y")) {
                flagYes = true;
                flagDone = true;
            } else if (tempString.equalsIgnoreCase("n")) {
                flagYes = false;
                flagDone = true;
            } else {
                System.out.println("You need to enter either y (yes) or n (no)");
            }


        } while (!flagDone);

        return flagYes;
    }


    public static void printOverallWinStats(int[] flagArrayWinStat) {

        System.out.println(Arrays.toString(flagArrayWinStat));

    }


    public static int[] getRound(Scanner in, Random random, int[] flagArrayRoundTemp, int MACHINE_CASE, int[] flagArrayWinStat) {

        // array for value of two dices
        int[] dices = new int[2];
        // variable for total eyes of dice in round
        int throwValue = 0;
        // variable for amount of throws
        int throwCount = 0;
        // flag for hitting blackjack
        boolean flagBlackjack = false;
        // flag for exceeding 21
        boolean flagExceed = false;

        // test passed arrays first index to see if this round is players round and make a flag
        boolean flagPlayer = (flagArrayRoundTemp[0] == 1) ? true : false;

        // make a flag for holding players choice about rolling again
        boolean flagChoice = false;

        // if this is machine round and player already hit blackjack change machines goal to blackjack
        // and reset flag in array back to default
        if (flagArrayRoundTemp[0] == 0) {
            if (flagArrayRoundTemp[5] == 1) {
                MACHINE_CASE = 21;
                flagArrayRoundTemp[5] = 0;
            }


        }


        do {
            // make a throw of dices
            dices = getDiceThrow(dices, random);
            // adjust counter of throws
            throwCount++;
            // calculate sum of dices
            throwValue += dices[0] + dices[1];

            // set flag if hit blackjack / set flag if exceed 21
            if (throwValue == 21) {
                flagBlackjack = true;
                break;
            } else if (throwValue > 21) {
                flagExceed = true;
                break;
            }

            // is this the players round?
            // if it is we will need to communicate about decisions

            if (flagPlayer) {
                // yes it is the players round
                // inform the player of status
                System.out.println("*********************");
                //System.out.printf("**Game number:  % 2d **\n", flagArrayWinStat[3]+1);
                System.out.printf("**Throw number: % 2d **\n", throwCount);
                System.out.println("*********************\n");


                // just for display test
                //System.out.printf("Dice 1: %d   Dice 2: %d \n", dices[0], dices[1]);

                System.out.printf("You threw a %d and a %d\nSo you've got % d for now\n", dices[0], dices[1], throwValue);

                // tell player to make decision
                System.out.println("Do you want to roll the dice again? y/n");

                // get decision from player
                flagChoice = getChoiceFromUser(in);


            } else {
                // no it is not the players round, so it must be the machines round

                // test if machine has reached satisfying total
                // if so, imitate player choosing another throw to control the coming while loop
                flagChoice = (throwValue >= MACHINE_CASE) ? false : true;
            }


        } while (flagChoice);

        // input the information from this round into the array for returning

        flagArrayRoundTemp[0] = (flagPlayer) ? 1 : 0; // player is 1 machine is 0
        flagArrayRoundTemp[1] = throwCount;
        flagArrayRoundTemp[2] = throwValue;
        flagArrayRoundTemp[3] = (flagExceed) ? 1 : 0;
        flagArrayRoundTemp[4] = (flagBlackjack) ? 1 : 0;

        // just display test
        //System.out.printf("%d %d %b %b %d %d \n", dices[0], dices[1], flagBlackjack, flagExceed, throwCount, throwValue);


        return flagArrayRoundTemp;
    }

    public static void printWelcomeMessageRec1(int n) {

    }


}


