package org.scrum.psd.battleship.ascii;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static List<Ship> myFleet;
    private static List<Ship> enemyFleet;
    private static ColoredPrinter console;

    public static void main(String[] args) {
        console = new ColoredPrinter.Builder(1, false).build();

        console.setForegroundColor(Ansi.FColor.WHITE);
        console.println("                                     |__");
        console.println("                                     |\\/");
        console.println("                                     ---");
        console.println("                                     / | [");
        console.println("                              !      | |||");
        console.println("                            _/|     _/|-++'");
        console.println("                        +  +--|    |--|--|_ |-");
        console.println("                     { /|__|  |/\\__|  |--- |||__/");
        console.println("                    +---------------___[}-_===_.'____                 /\\");
        console.println("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _");
        console.println(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7");
        console.println("|                        Welcome to Battleship                         BB-61/");
        console.println(" \\_________________________________________________________________________|");
        console.println("");
        console.clear();

        InitializeGame();

        StartGame();
    }

    private static void StartGame() {
        Scanner scanner = new Scanner(System.in);
        console.setBackgroundColor(Ansi.BColor.NONE);
        // console.setForegroundColor(Ansi.FColor.MAGENTA);

        console.print("\033[2J\033[;H");
        console.println("                  __");
        console.println("                 /  \\");
        console.println("           .-.  |    |");
        console.println("   *    _.-'  \\  \\__/");
        console.println("    \\.-'       \\");
        console.println("   /          _/");
        console.println("  |      _  /\" \"");
        console.println("  |     /_\'");
        console.println("   \\    \\_/");
        console.println("    \" \"\" \"\" \"\" \"");

        do {
            //console.setBackgroundColor(Ansi.BColor.YELLOW);
            console.setForegroundColor(Ansi.FColor.MAGENTA);
            console.println("");
            console.println("Player, it's your turn");
            console.println("Enter coordinates for your shot :");
            console.setBackgroundColor(Ansi.BColor.BLACK);
            Position position = parsePosition(scanner.next());
            boolean isHit = GameController.checkIsHit(enemyFleet, position);
            if (isHit) {
                beep();
                console.setForegroundColor(Ansi.FColor.RED);
                console.println("                \\         .  ./");
                console.println("              \\      .:\" \";'.:..\" \"   /");
                console.println("                  (M^^.^~~:.'\" \").");
                console.println("            -   (/  .    . . \\ \\)  -");
                console.println("               ((| :. ~ ^  :. .|))");
                console.println("            -   (\\- |  \\ /  |  /)  -");
                console.println("                 -\\  \\     /  /-");
                console.println("                   \\  \\   /  /");
            } else {
                console.setForegroundColor(Ansi.FColor.BLUE);
            }
            if(isHit){
                console.setForegroundColor(Ansi.FColor.RED);
                console.println("Yeah ! Nice hit !");
            }
            else{
                console.setForegroundColor(Ansi.FColor.BLUE);
                console.println("Miss");
            }
            position = getRandomPosition();
            isHit = GameController.checkIsHit(myFleet, position);
            //console.setBackgroundColor(Ansi.BColor.YELLOW);
            console.println("");
            if(isHit){
                console.setForegroundColor(Ansi.FColor.RED);
                console.println(String.format("Computer shoot in %s%s and %s", position.getColumn(), position.getRow(), "hit your ship !"));

            }
            else{
                console.setForegroundColor(Ansi.FColor.BLUE);
                console.println(String.format("Computer shoot in %s%s and %s", position.getColumn(), position.getRow(), "miss"));

            }
            console.setBackgroundColor(Ansi.BColor.BLACK);
            if (isHit) {
                beep();
                console.setForegroundColor(Ansi.FColor.RED);
                console.println("                \\         .  ./");
                console.println("              \\      .:\" \";'.:..\" \"   /");
                console.println("                  (M^^.^~~:.'\" \").");
                console.println("            -   (/  .    . . \\ \\)  -");
                console.println("               ((| :. ~ ^  :. .|))");
                console.println("            -   (\\- |  \\ /  |  /)  -");
                console.println("                 -\\  \\     /  /-");
                console.println("                   \\  \\   /  /");

            }
            else {
                console.setForegroundColor(Ansi.FColor.BLUE);
            }
            console.setForegroundColor(Ansi.FColor.NONE);
        } while (true);
    }

    private static void beep() {
        console.print("\007");
    }

    protected static Position parsePosition(String input) {
        Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1));
        return new Position(letter, number);
    }

    private static Position getRandomPosition() {
        int rows = 8;
        int lines = 8;
        Random random = new Random();
        Letter letter = Letter.values()[random.nextInt(lines)];
        int number = random.nextInt(rows);
        Position position = new Position(letter, number);
        return position;
    }

    private static void InitializeGame() {
        InitializeMyFleet();

        InitializeEnemyFleet();
    }

    private static void InitializeMyFleet() {
        Scanner scanner = new Scanner(System.in);
        myFleet = GameController.initializeShips();
        console.setBackgroundColor(Ansi.BColor.CYAN);
        console.println("Please position your fleet (Game board has size from A to H and 1 to 8) :");
        console.setBackgroundColor(Ansi.BColor.BLACK);
        for (Ship ship : myFleet) {
            console.setForegroundColor(Ansi.FColor.WHITE);
            //console.setBackgroundColor(Ansi.BColor.CYAN);
            console.println("");
            console.println(String.format("Please enter the positions for the"));

            console.setForegroundColor(Ansi.FColor.valueOf(ship.getColor().toString()));
            console.println(String.format("%s (size: %s)", ship.getName(), ship.getSize()));
            console.setForegroundColor(Ansi.FColor.WHITE);
            console.setBackgroundColor(Ansi.BColor.BLACK);
            for (int i = 1; i <= ship.getSize(); i++) {
                console.println(String.format("Enter position %s of %s (i.e A3):", i, ship.getSize()));

                String positionInput = scanner.next();
                ship.addPosition(positionInput);
            }

        }
    }

    private static void InitializeEnemyFleet() {
        enemyFleet = GameController.initializeShips();

        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 4));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 5));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 6));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 7));
        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 8));

        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 6));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 7));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 8));
        enemyFleet.get(1).getPositions().add(new Position(Letter.E, 9));

        enemyFleet.get(2).getPositions().add(new Position(Letter.A, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.B, 3));
        enemyFleet.get(2).getPositions().add(new Position(Letter.C, 3));

        enemyFleet.get(3).getPositions().add(new Position(Letter.F, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.G, 8));
        enemyFleet.get(3).getPositions().add(new Position(Letter.H, 8));

        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 5));
        enemyFleet.get(4).getPositions().add(new Position(Letter.C, 6));
    }
}
