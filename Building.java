public class Building {
    private Position[] floors;
    private String state; //puede ser intact o demolished
    private Type type;
    public Building(int nPlantas) {
        if (nPlantas < 1 || nPlantas > 4) {
            type = Type.SHACK;
        } else {
            type = Type.values()[nPlantas - 1];
        }
        state = "intact";
        floors = new Position[nPlantas];  // Inicializar array
    }
    public boolean setFloor(Position poser, int posDfloor) {
        if (poser == null) {
            return false; // No se puede asignar una posición nula
        }

        if (posDfloor >= 0 && posDfloor < floors.length && floors[posDfloor] == null) {
            floors[posDfloor] = poser;
            poser.changeState(1); // Cambia a FULL
            poser.setFloor(posDfloor);
            return true;
        }

        return false;
    }
    public boolean demolish(int r, int c) throws Error {
        // Verificar si el edificio está en construcción
        for (Position p : floors) {
            if (p == null) {
                throw new Error("building under construction");
            }
        }

        // Buscar la posición a demoler
        for (int i = 0; i < floors.length; i++) {
            Position p = floors[i];
            if (p.getRow() == r && p.getColumn() == c && p.getState() != State.DAMAGED) {
                // Verificar si todas las plantas inferiores están dañadas
                for (int j = 0; j < i; j++) {
                    if (floors[j].getState() != State.DAMAGED) {
                        return false;
                    }
                }
                // Cambiar el estado de la posición actual a DAMAGED
                p.changeState(2);

                // Verificar si todas las posiciones están dañadas
                for (Position q : floors) {
                    if (q.getState() != State.DAMAGED) {
                        return true;
                    }
                }
                // Cambiar el estado del edificio a "demolished"
                this.state = "demolished";
                return true;
            }
        }
        return false;
    }
    
    public String getState(){
        return this.state;
    }
    public Type getType(){
        return this.type;
    }
    public int getFloorsLength() { //creo estos 2 getters porque creo que me hace falta en player.knockdown
        return floors.length;
    }
    public Position getFloor(int p){
        return floors[p];
    }
}
