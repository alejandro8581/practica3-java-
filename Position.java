public class Position {
    private int row;
    private int column;
    private int floor;
    private State state;

    public Position(int fila, int columna) {

        this.row = fila;
        this.column = columna;
        this.floor = -1; // Valor por defecto
        this.state = State.EMPTY; // Estado inicial
    }

    public boolean changeState(int st) {
        State[] estados = State.values(); // Array con todos los valores de State
        if (st < 0 || st >= estados.length) { // Validar si el estado es válido
            return false; // No se lanza excepción, simplemente devuelve false
        }
        if (estados[st] != this.state) { // Cambiar solo si el estado es diferente
            this.state = estados[st];
            return true;
        }
        return false;
    }

    public boolean equals(Position poser) {
        if (poser == null) {
            return false; // No puede ser igual a null
        }
        return this.row == poser.row && this.column == poser.column;
    }

    @Override
    public String toString() {
        return "(" + this.row + "," + this.column + ")";
    }

    public State getState() {
        return this.state;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public void setFloor(int nFloor) {
        this.floor = nFloor;
    }

    public int getFloor() {
        return this.floor;
    }
}
