import java.util.ArrayList;
public class Player {
    private String name;
    private Position[][] board;
    private Building[] buildings;

    public Player(String nicker) throws Error {
        if (nicker == null || nicker.isEmpty()) {
            throw new Error("player without name");
        }
        this.name = nicker;
        this.board = new Position[12][12];
        this.buildings = new Building[10]; // Inicializar con un límite de 10 edificios
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Position(i + 1, j + 1);
            }
        }
    }

    public boolean createBuilding(Position pInicial, Position pFinal) throws Error {
        if (pInicial == null || pFinal == null) {
            throw new Error("bad position");
        }
        if (pInicial.getRow() < 1 || pInicial.getRow() > 12 || pInicial.getColumn() < 1 || pInicial.getColumn() > 12) {
            throw new Error("bad position", pInicial.getRow(), pInicial.getColumn());
        }
        if (pFinal.getRow() < 1 || pFinal.getRow() > 12 || pFinal.getColumn() < 1 || pFinal.getColumn() > 12) {
            throw new Error("bad position", pFinal.getRow(), pFinal.getColumn());
        }
        if (pFinal.getRow() != pInicial.getRow() && pFinal.getColumn() != pInicial.getColumn()) {
            return false; // No está en línea recta
        }

int numPos;
if (pFinal.getRow() == pInicial.getRow()) {
    // Si las filas son iguales, el edificio es horizontal
    numPos = Math.abs(pFinal.getColumn() - pInicial.getColumn()) + 1;
} else {
    // Si las filas son diferentes, el edificio es vertical
    numPos = Math.abs(pFinal.getRow() - pInicial.getRow()) + 1;
}

        if (numPos < 1 || numPos > 4) {
            return false; // Número de posiciones inválido
        }

        for (int i = pInicial.getRow(); i <= pFinal.getRow(); i++) {
            for (int j = pInicial.getColumn(); j <= pFinal.getColumn(); j++) {
                if (board[i - 1][j - 1].getState() != State.EMPTY) {
                    throw new Error("busy position", i, j);
                }
            }
        }

        for (int i = 0; i < buildings.length; i++) {
            if (buildings[i] == null) {
                buildings[i] = new Building(numPos);
                for (int r = pInicial.getRow(); r <= pFinal.getRow(); r++) {
                    for (int c = pInicial.getColumn(); c <= pFinal.getColumn(); c++) {
                        board[r - 1][c - 1].changeState(1); // Cambiar a FULL
                    }
                }
                return true;
            }
        }

        throw new Error("no space for buildings");
    }

    public boolean knockDown(Position pos) throws Error, UnderConstruction, Spoiled {
        if (pos == null) {
            throw new Error("bad position");
        }
        if (pos.getRow() < 1 || pos.getRow() > 12 || pos.getColumn() < 1 || pos.getColumn() > 12) {
            throw new Error("bad position", pos.getRow(), pos.getColumn());
        }

        boolean allDemolished = true;
        for (Building building : buildings) {
            if (building != null && !"demolished".equals(building.getState())) {
                allDemolished = false;
                break;
            }
        }
        if (allDemolished) {
            throw new Spoiled(this.name);
        }

        for (Building building : buildings) {
            if (building != null) {
                for (int i = 0; i < building.getFloorsLength(); i++) {
                    Position floor = building.getFloor(i);
                    if (floor != null && floor.equals(pos)) {
                        return building.demolish(pos.getRow(), pos.getColumn());
                    }
                }
            }
        }

        return false;
    }

    public void show() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // Convertir el nombre del estado a minúsculas antes de imprimir
                System.out.print(board[i][j].getState().name().toLowerCase().charAt(0) + " ");
            }
            System.out.println();
        }
    }

    public String getName(){
        return this.name;
    }
    public ArrayList<Building> getBuildings(){
        ArrayList<Building> buildingsList = new ArrayList<>();
        for (int i=0;i<buildings.length;i++){
            
                buildingsList.add(buildings[i]);
        }
        return buildingsList;

    }
}
