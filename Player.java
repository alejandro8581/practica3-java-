import java.util.ArrayList;

public class Player {
    private String name;
    private Position[][] board;
    private Building[] buildings;
    private int[] buildingCount = new int[4];

    public Player(String name) throws Error {
        if (name == null || name.equals("")) throw new Error("player without name");
        this.name = name;
        this.board = new Position[12][12];
        for (int i = 0; i < 12; i++)
            for (int j = 0; j < 12; j++)
                board[i][j] = new Position(i + 1, j + 1);
        this.buildings = new Building[11];
    }

    public boolean createBuilding(Position p1, Position p2) throws Error {
        if (p1 == null || p2 == null) return false;
        int r1 = p1.getRow() - 1, c1 = p1.getColumn() - 1;
        int r2 = p2.getRow() - 1, c2 = p2.getColumn() - 1;
        if (r1 < 0 || r1 >= 12 || c1 < 0 || c1 >= 12) throw new Error("bad position", p1.getRow(), p1.getColumn());
        if (r2 < 0 || r2 >= 12 || c2 < 0 || c2 >= 12) throw new Error("bad position", p2.getRow(), p2.getColumn());
        int dr = r2 - r1, dc = c2 - c1;
        if ((dr != 0 && dc != 0) || (dr < 0 || dc < 0)) return false;
        int len = Math.max(Math.abs(dr), Math.abs(dc)) + 1;
        if (len < 1 || len > 4) return false;

        Position[] pos = new Position[len];
        for (int i = 0; i < len; i++) {
            int ri = r1 + (dr != 0 ? i : 0);
            int ci = c1 + (dc != 0 ? i : 0);
            if (board[ri][ci].getState() != State.EMPTY)
                throw new Error("busy position", board[ri][ci].getRow(), board[ri][ci].getColumn());
            pos[i] = board[ri][ci];
        }

        Building b = new Building(len);
        int typeIndex = b.getType().ordinal();
        int[] limits = {4, 3, 2, 2};
        if (buildingCount[typeIndex] >= limits[typeIndex]) throw new Error(b.getType());
        for (int i = 0; i < len; i++) b.setFloor(pos[i], i);
        for (int i = 0; i < buildings.length; i++) if (buildings[i] == null) {
            buildings[i] = b;
            buildingCount[typeIndex]++;
            break;
        }
        return true;
    }

    public boolean knockDown(Position p) throws Error, UnderConstruction, Spoiled {
        if (p == null) return false;
        int r = p.getRow(), c = p.getColumn();
        if (r < 1 || r > 12 || c < 1 || c > 12) throw new Error(r, c);
        for (Building b : buildings) if (b == null) throw new UnderConstruction(name, "incomplete");
        boolean changed = false;
        for (Building b : buildings)
            if (b.demolish(r, c)) changed = true;
        for (Building b : buildings)
            if (!b.getState().equals("demolished")) return changed;
        throw new Spoiled(name);
    }

    public void show() {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                switch (board[i][j].getState()) {
                    case EMPTY -> System.out.print("e ");
                    case FULL -> System.out.print("f ");
                    case DAMAGED -> System.out.print("d ");
                }
            }
            System.out.println();
        }
    }

    public String getName() { return name; }
    public ArrayList<Building> getBuildings() {
        ArrayList<Building> list = new ArrayList<>();
        for (Building b : buildings) if (b != null) list.add(b);
        return list;
    }
}
