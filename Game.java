import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].isEmpty()) {
            System.out.println("Error: file name required");
            return;
        }

        Player p1 = null, p2 = null;
        try {
            File fichero = new File(args[0]);
            Scanner lector = new Scanner(fichero);

            // 1. Comprobar primera etiqueta <PLAYER>
            if (!lector.hasNextLine() || !lector.nextLine().equals("<PLAYER>")) {
                System.out.println("Error: bad format file");
                lector.close();
                return;
            }

            // 2. Leer nombre del primer jugador
            if (!lector.hasNextLine()) {
                System.out.println("Error: bad format file");
                lector.close();
                return;
            }
            String nombre1 = lector.nextLine();
            if (nombre1.startsWith("<")) {
                System.out.println("Error: bad format file");
                lector.close();
                return;
            }

            try {
                p1 = new Player(nombre1);
            } catch (Exception e) {
                System.out.println(e);
                lector.close();
                return;
            }

            // 3. Leer edificios del primer jugador hasta <PLAYER>
            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                if (linea.equals("<PLAYER>")) break;
                String[] elems = linea.split(" ");
                if (elems.length != 4) {
                    System.out.println("Error: bad format file");
                    lector.close();
                    return;
                }
                try {
                    int r1 = Integer.parseInt(elems[0]);
                    int c1 = Integer.parseInt(elems[1]);
                    int r2 = Integer.parseInt(elems[2]);
                    int c2 = Integer.parseInt(elems[3]);
                    Position start = new Position(r1, c1);
                    Position end = new Position(r2, c2);
                    p1.createBuilding(start, end);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            // 4. Comprobar si falta el segundo jugador
            if (!lector.hasNextLine()) {
                System.out.println("Error: bad format file");
                lector.close();
                return;
            }

            String nombre2 = lector.nextLine();
            if (nombre2.equals("<GAME>") || nombre2.startsWith("<")) {
                System.out.println("Error: bad format file");
                lector.close();
                return;
            }

            try {
                p2 = new Player(nombre2);
            } catch (Exception e) {
                System.out.println(e);
                lector.close();
                return;
            }

            // 5. Leer edificios del segundo jugador hasta <GAME>
            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                if (linea.equals("<GAME>")) break;
                String[] elems = linea.split(" ");
                if (elems.length != 4) {
                    System.out.println("Error: bad format file");
                    lector.close();
                    return;
                }
                try {
                    int r1 = Integer.parseInt(elems[0]);
                    int c1 = Integer.parseInt(elems[1]);
                    int r2 = Integer.parseInt(elems[2]);
                    int c2 = Integer.parseInt(elems[3]);
                    Position start = new Position(r1, c1);
                    Position end = new Position(r2, c2);
                    p2.createBuilding(start, end);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            // 6. Reproducir partida
            boolean turnoP1 = true; // empieza el primer jugador
            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                String[] elems = linea.split(" ");
                if (elems.length != 2) {
                    System.out.println("Error: bad format " + linea);
                    continue;
                }
                try {
                    int row = Integer.parseInt(elems[0]);
                    int col = Integer.parseInt(elems[1]);
                    Position pos = new Position(row, col);

                    Player actual = turnoP1 ? p1 : p2;
                    boolean resultado = actual.knockDown(pos);

                    System.out.println(actual.getName() + " " + row + " " + col + " " + resultado);

                    if (!resultado) {
                        turnoP1 = !turnoP1; // cambia de jugador si no ha dañado nada
                    }
                } catch (UnderConstruction | Spoiled e) {
                    System.out.println(e);
                    p1.show();
                    System.out.println();
                    p2.show();
                    lector.close();
                    return;
                } catch (Error e) {
                    System.out.println(e);
                    turnoP1 = !turnoP1;
                }
            }

            lector.close();

            // Si llegamos al final sin excepciones que acaben la partida
            p1.show();
            System.out.println();
            p2.show();

        } catch (FileNotFoundException e) {
            System.out.println("Error: file not found");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
