
import java.util.InputMismatchException;
import java.util.Scanner;

public class wizardGameSrc {
    private String playerName;
    private int playerTotalHitPoints;
    private int playerMinHitStrength;
    private int playerMaxHitStrength;
    private boolean playerFireResistance;
    private boolean playerIceResistance;
    private boolean playerTurnSkipped = false;

    private String aiName;
    private int aiTotalHitPoints;
    private boolean aiFireResistance;
    private boolean aiIceResistance;
    private boolean aiTurnSkipped = false;

    public void play() {
        displayAsciiArt();
        getPlayerDetails();
        getAIDetails();
        fight();
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
                scan.next(); // Consume invalid input
            }
        }
    }

    private void getAIDetails() {
        Scanner scan = new Scanner(System.in);

        boolean inputGood = true;
        while (inputGood) {
            try {
                System.out.println("Please Enter AI's name: ");
                aiName = scan.next();

                System.out.println("Now please enter AI's total hit points: ");
                aiTotalHitPoints = scan.nextInt();
                System.out.println("Choose AI's resistance. 1. Fire. 2. Ice");
                int resistanceChoice = scan.nextInt();
                if (resistanceChoice == 1) {
                  aiFireResistance = true;
                  aiIceResistance = false;
                } 
                else {
                  aiFireResistance = false;
                  aiIceResistance = true;
                }
                inputGood = false;
            } catch (InputMismatchException ime) {
                System.out.println("You entered an invalid value. Try again.");
                scan.next(); // Consume invalid input
            }
        }
    }

    private void magicMissilePlayer() {
        aiTotalHitPoints -= 3;
        System.out.println("You have cast Magic Missile on the AI, dealing 3 damage.");
    }

    private void fireboltPlayer() {
        if (!aiFireResistance) {
            aiTotalHitPoints -= 10;
            System.out.println("You have cast Firebolt on the AI, dealing 10 damage.");
        } else {
            aiTotalHitPoints -= 1;
            System.out.println("You have cast Firebolt on the AI, dealing 1 damage.");
        }
    }

    private void iceSpikePlayer() {
        if (!aiIceResistance) {
            aiTotalHitPoints -= 10;
            System.out.println("You have cast Ice Spike on the AI, dealing 10 damage.");
        } else {
            aiTotalHitPoints -= 1;
            System.out.println("You have cast Ice Spike on the AI, dealing 1 damage.");
        }
    }

    private void cursePlayer() {
        aiTurnSkipped = true;
        System.out.println("You have cursed your opponent. The AI cannot cast this turn.");
    }

    private void healingPlayer() {
        playerTotalHitPoints += 10;
        System.out.println("You have healed yourself, gaining 10 hit points.");
    }

    private void magicMissileAI() {
        playerTotalHitPoints -= 3;
        System.out.println("AI has cast Magic Missile on you, dealing 3 damage.");
    }

    private void fireboltAI() {
        if (!playerFireResistance) {
            playerTotalHitPoints -= 10;
            System.out.println("AI has cast Firebolt on you, dealing 10 damage.");
        } else {
            playerTotalHitPoints -= 1;
            System.out.println("AI has cast Firebolt on you, dealing 1 damage.");
        }
    }

    private void iceSpikeAI() {
        if (!playerIceResistance) {
            playerTotalHitPoints -= 10;
            System.out.println("AI has cast Ice Spike on you, dealing 10 damage.");
        } else {
            playerTotalHitPoints -= 1;
            System.out.println("AI has cast Ice Spike on you, dealing 1 damage.");
        }
    }

    private void curseAI() {
        playerTurnSkipped = true;
        System.out.println("AI has cursed you. You cannot cast this turn.");
    }

    private void healingAI() {
        aiTotalHitPoints += 10;
        System.out.println("AI has healed itself, gaining 10 hit points.");
    }

    private void fight() {
        Scanner scan = new Scanner(System.in);
        boolean playerTurn = true; // Player starts first
        while (playerTotalHitPoints > 0 && aiTotalHitPoints > 0) {
            if (playerTurn) {
                // Player's turn
                if (!playerTurnSkipped) {
                    System.out.println("Player, it's your turn to cast a spell. Enter the spell number:");
                    System.out.println("1. Magic Missile 2. Firebolt 3. Ice Spike 4. Healing 5. Curse");
                    int spellNumber = scan.nextInt();
                    switch (spellNumber) {
                        case 1:
                            magicMissilePlayer();
                            break;
                        case 2:
                            fireboltPlayer();
                            break;
                        case 3:
                            iceSpikePlayer();
                            break;
                        case 4:
                            healingPlayer();
                            break;
                        case 5:
                            cursePlayer();
                            break;
                        default:
                            System.out.println("Invalid spell number. Please try again.");
                            break;
                    }
                } else {
                    System.out.println("Player's turn is skipped due to curse.");
                    playerTurnSkipped = false; // Reset turn skip
                }
            } else {
                // AI's turn
                if (!aiTurnSkipped) {
                    int aiSpellNumber = (int) (Math.random() * 5) + 1; // Randomly choose AI's spell
                    switch (aiSpellNumber) {
                        case 1:
                            magicMissileAI();
                            break;
                        case 2:
                            fireboltAI();
                            break;
                        case 3:
                            iceSpikeAI();
                            break;
                        case 4:
                            healingAI();
                            break;
                        case 5:
                            curseAI();
                            break;
                    }
                } else {
                    System.out.println("AI's turn is skipped due to curse.");
                    aiTurnSkipped = false; // Reset turn skip
                }
            }

            // Switch turn
            playerTurn = !playerTurn;

            // Check if any player's hit points reached zero
            if (playerTotalHitPoints <= 0) {
                System.out.println("AI Wins");
                break;
            } else if (aiTotalHitPoints <= 0) {
                System.out.println("Player Wins");
                break;
            }
        }
    }

    // Placeholder method for displaying ASCII art
    private void displayAsciiArt() {
        // Implement your ASCII art display here
        System.out.println("ASCII Art goes here");
    }
}


