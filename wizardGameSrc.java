import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

public class wizardGameSrc {
    private String playerName;
    private int playerTotalHitPoints;
    private int playerBaseHitPoints;
    private int playerMinHitStrength;
    private int playerMaxHitStrength;
    private boolean playerFireResistance;
    private boolean playerIceResistance;
    private boolean playerTurnSkipped = false;
    boolean playerTurn = true;

    private String[] aiNames = new String[3];
    private int[] aiTotalHitPoints = new int[3];
    private boolean[] aiFireResistance = new boolean[3];
    private boolean[] aiIceResistance = new boolean[3];
    private boolean[] aiTurnSkipped = new boolean[3];

    public void play() {
        getPlayerDetails();
        getAIDetails();
        for (int i = 0; i < 3; i++) {
            fightWithAI(i);
        }
    }

    private void getPlayerDetails() {
        Scanner scan = new Scanner(System.in);

        boolean inputGood = true;
        while (inputGood) {
            try {
                System.out.println("Please Enter only your first name cunning magus: ");
                playerName = scan.next();

                System.out.println("Now please enter your total hit points: ");
                playerTotalHitPoints = scan.nextInt();
                playerBaseHitPoints = playerTotalHitPoints;

                System.out.println("Choose a resistance. 1. Fire. 2. Ice");
                int resistanceChoice = scan.nextInt();
                if (resistanceChoice == 1) {
                    playerFireResistance = true;
                    playerIceResistance = false;
                } else {
                    playerFireResistance = false;
                    playerIceResistance = true;
                }
                inputGood = false;
            } catch (InputMismatchException ime) {
                System.out.println("You entered an invalid value. Try again.");
                scan.next();
            }
        }
    }

    private void getAIDetails() {
        Scanner scan = new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            boolean inputGood = true;
            while (inputGood) {
                try {
                    System.out.println("Please Enter AI " + (i + 1) + "'s name: ");
                    aiNames[i] = scan.next();

                    Random rand = new Random();
                    int minAIHitPoints = (int) (playerTotalHitPoints * 0.8); // 80% of player's hit points
                    int maxAIHitPoints = (int) (playerTotalHitPoints * 1.2); // 120% of player's hit points
                    int randomAIHitPoints = rand.nextInt(maxAIHitPoints - minAIHitPoints + 1) + minAIHitPoints;
                    aiTotalHitPoints[i] = randomAIHitPoints;
                    int resistanceChoice = 3;
                    if (resistanceChoice == 1) {
                        aiFireResistance[i] = true;
                        aiIceResistance[i] = false;
                    } else if (resistanceChoice == 2) {
                        aiFireResistance[i] = false;
                        aiIceResistance[i] = true;
                    } else if (resistanceChoice == 3) {
                        Random random = new Random();
                        resistanceChoice = random.nextInt(2) + 1;
                        if (resistanceChoice == 1) {
                            aiFireResistance[i] = true;
                            aiIceResistance[i] = false;
                        } else {
                            aiFireResistance[i] = false;
                            aiIceResistance[i] = true;
                        }
                    }
                    inputGood = false;
                } catch (InputMismatchException ime) {
                    System.out.println("You entered an invalid value. Try again.");
                    scan.next();    
                }
            }
        }
    }

    private void magicMissilePlayer(int aiIndex) {
        aiTotalHitPoints[aiIndex] -= 3;
        System.out.println("You have cast Magic Missile on " + aiNames[aiIndex] + ", dealing 3 damage.");
    }

    private void fireboltPlayer(int aiIndex) {
        if (!aiFireResistance[aiIndex]) {
            aiTotalHitPoints[aiIndex] -= 10;
            System.out.println("You have cast Firebolt on " + aiNames[aiIndex] + ", dealing 10 damage.");
        } else {
            aiTotalHitPoints[aiIndex] -= 1;
            System.out.println("You have cast Firebolt on " + aiNames[aiIndex] + ", dealing 1 damage.");
        }
    }

    private void iceSpikePlayer(int aiIndex) {
        if (!aiIceResistance[aiIndex]) {
            aiTotalHitPoints[aiIndex] -= 10;
            System.out.println("You have cast Ice Spike on " + aiNames[aiIndex] + ", dealing 10 damage.");
        } else {
            aiTotalHitPoints[aiIndex] -= 1;
            System.out.println("You have cast Ice Spike on " + aiNames[aiIndex] + ", dealing 1 damage.");
        }
    }

    private void cursePlayer(int aiIndex) {
        aiTurnSkipped[aiIndex] = true;
        System.out.println("You have cursed " + aiNames[aiIndex] + ". " + aiNames[aiIndex] + " cannot cast this turn.");
    }

    private void healingPlayer() {
        playerTotalHitPoints += 10;
        System.out.println("You have healed yourself, gaining 10 hit points.");
    }

    private void magicMissileAI(int aiIndex) {
        playerTotalHitPoints -= 3;
        System.out.println(aiNames[aiIndex] + " has cast Magic Missile on you, dealing 3 damage.");
    }

    private void fireboltAI(int aiIndex) {
        if (!playerFireResistance) {
            playerTotalHitPoints -= 10;
            System.out.println(aiNames[aiIndex] + " has cast Firebolt on you, dealing 10 damage.");
        } else {
            playerTotalHitPoints -= 1;
            System.out.println(aiNames[aiIndex] + " has cast Firebolt on you, dealing 1 damage.");
        }
    }

    private void iceSpikeAI(int aiIndex) {
        if (!playerIceResistance) {
            playerTotalHitPoints -= 10;
            System.out.println(aiNames[aiIndex] + " has cast Ice Spike on you, dealing 10 damage.");
        } else {
            playerTotalHitPoints -= 1;
            System.out.println(aiNames[aiIndex] + " has cast Ice Spike on you, dealing 1 damage.");
        }
    }

    private void curseAI(int aiIndex) {
        playerTurnSkipped = true;
        System.out.println(aiNames[aiIndex] + " has cursed you. You cannot cast this turn.");
    }

    private void healingAI() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(3);
        aiTotalHitPoints[randomIndex] += 10;
        System.out.println(aiNames[randomIndex] + " has healed itself, gaining 10 hit points.");
    }

    private void fightWithAI(int aiIndex) {
        Scanner scan = new Scanner(System.in);
        while (playerTotalHitPoints > 0 && aiTotalHitPoints[aiIndex] > 0) {

            if (playerTurn) {
                if (!playerTurnSkipped) {
                    System.out.println("Player, it's your turn to cast a spell. Enter the spell number:");
                    System.out.println("1. Magic Missile 2. Firebolt 3. Ice Spike 4. Healing 5. Curse");
                    int spellNumber = scan.nextInt();
                    switch (spellNumber) {
                        case 1:
                            magicMissilePlayer(aiIndex);
                            break;
                        case 2:
                            fireboltPlayer(aiIndex);
                            break;
                        case 3:
                            iceSpikePlayer(aiIndex);
                            break;
                        case 4:
                            healingPlayer();
                            break;
                        case 5:
                            cursePlayer(aiIndex);
                            break;
                        default:
                            System.out.println("Invalid spell number. Please try again.");
                            break;
                    }
                } else {
                    System.out.println("Player's turn is skipped due to curse.");
                    playerTurnSkipped = false;
                }
            } else {
                if (!aiTurnSkipped[aiIndex]) {
                    int aiSpellNumber = (int) (Math.random() * 5) + 1;
                    switch (aiSpellNumber) {
                        case 1:
                            magicMissileAI(aiIndex);
                            break;
                        case 2:
                            fireboltAI(aiIndex);
                            break;
                        case 3:
                            iceSpikeAI(aiIndex);
                            break;
                        case 4:
                            healingAI();
                            break;
                        case 5:
                            curseAI(aiIndex);
                            break;
                    }
                } else {
                    System.out.println(aiNames[aiIndex] + "'s turn is skipped due to curse.");
                    aiTurnSkipped[aiIndex] = false;
                }
            }

            playerTurn = !playerTurn;

            if (aiTotalHitPoints[aiIndex] <= 0) {
                System.out.println("AI " + aiNames[aiIndex] + " has been defeated!");
                if (aiIndex < 2) {
                    System.out.println("You have " + playerTotalHitPoints + " hit points remaining. Drink a healing potion to restore yourself to full health? 1. Yes 2. No");
                    int choice = scan.nextInt();
                    if (choice == 1) {
                        playerTotalHitPoints = playerBaseHitPoints;
                    }
                }
            }
            if (playerTotalHitPoints <= 0) {
                System.out.println("AI " + aiNames[aiIndex] + " Wins");
                break;
            } else if (aiTotalHitPoints[aiIndex] <= 0) {
                System.out.println("Player Wins against AI " + aiNames[aiIndex]);
                break;
            }
        }
    }

    public static void displayAsciiArt() {
        System.out.println(
                "      _,-'|                \n" +
                        "   ,-'._  |                \n" +
                        ".||,      |####\\ |         \n" +
                        "\\.`',/     \\####| |        \n" +
                        "= ,. =      |###| |         \n" +
                        "/ || \\    ,-'\\#/,'`.        \n" +
                        "  ||     ,'   `,,. `.       \n" +
                        "  ,|____,' , ,;' \\| |      \n" +
                        " (3|\\    _/|/'   _| |      \n" +
                        "  ||/,-''  | >-'' _,\\\\     \n" +
                        "  ||'      ==\\ ,-'  ,'     \n" +
                        "  ||       |  V \\ ,|       \n" +
                        "  ||       |    |` |        \n" +
                        "  ||       |    |   \\       \n" +
                        "  ||       |    \\    \\      \n" +
                        "  ||       |     |    \\     \n" +
                        "  ||       |      \\_,-'     \n" +
                        "  ||       |___,,--\")_\\     \n" +
                        "  ||         |_|   ccc/     \n" +
                        "  ||        ccc/             \n" +
                        "  ||                hjm     \n"
        );
    }
}

// a little buggy with the names still but liking the progress overall
