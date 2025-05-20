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
    public boolean setFloor(Position poser,int posDfloor) throws Error{
        if (posDfloor >= 0 && posDfloor < floors.length && floors[posDfloor]==null){
            floors[posDfloor]=poser;
            poser.changeState(1); //cambia a FULL
            poser.setFloor(posDfloor);
            return true;
        }
        else
            return false;
    }
    public boolean demolish(int i, int j) throws Error {
        boolean todasColocadas = true; // asumo que todas están colocadas hasta que se demuestre lo contrario
        boolean existePos = false; // para verificar si existe la posición con los valores pasados
        int posExistente = -1; // inicializo a un valor inválido para evitar problemas
        boolean estadoCambiado = false; // para saber si se cambió el estado de alguna posición
         // Validar si el array floors es nulo
        if (floors == null) {
            throw new Error("building not initialized"); // El edificio no está inicializado
        }//COPILOT
        // Validar si el array floors está vacío
        if (floors.length == 0) {
            throw new Error("building has no floors"); // El edificio no tiene plantas
        }//COPILOT
        // Validar si los índices están fuera de rango
        if (i < 1 || i > 12 || j < 1 || j > 12) { // Suponiendo que las filas y columnas están en el rango 1-12
            throw new Error("invalid position", i, j); // Posición inválida
        }
        // Verifico si todas las posiciones están colocadas y busco la posición que coincide
        for (int k = 0; k < floors.length; k++) {
            if (floors[k] == null) { // si cualquier posición es nula, no están todas colocadas
                todasColocadas = false;
            }
            if (floors[k] != null) {
                if (floors[k].getRow() == i && floors[k].getColumn() == j) {
                    // si encuentro la posición con los valores pasados
                    existePos = true;
                    posExistente = k; // guardo el índice de la posición encontrada
                }
            }
        } // cierre del for
    
        // Si no están todas colocadas, lanzo la excepción
        if (!todasColocadas) {
            throw new Error("building under construction"); // se lanza el error con el mensaje requerido
        }
    
        // Si la posición existe, verifico las condiciones para demoler
        if (existePos) {
            // Verifico si la posición no está dañada y las plantas inferiores están dañadas
            if (floors[posExistente].getState() != State.DAMAGED) { 
                boolean lowerFloorsDamaged = true; // asumo que todas las plantas inferiores están dañadas
                for (int k = 0; k < posExistente; k++) { // recorro las plantas inferiores
                    if (floors[k].getState() != State.DAMAGED) { 
                        lowerFloorsDamaged = false; // si alguna no está dañada, cambio el estado
                        break;
                    }
                }
    
                if (lowerFloorsDamaged) { 
                    // Si todas las plantas inferiores están dañadas, cambio el estado de la posición actual
                    floors[posExistente].changeState(2); // cambio el estado a DAMAGED
                    estadoCambiado = true; // marco que se cambió el estado
                }
            }
    
            // Verifico si todas las plantas están dañadas
            boolean allDamaged = true; // asumo que todas están dañadas
            for (Position floor : floors) { 
                if (floor.getState() != State.DAMAGED) { 
                    allDamaged = false; // si alguna no está dañada, cambio el estado
                    break;
                }
            }
    
            // Si todas las plantas están dañadas, cambio el estado del edificio a "demolished"
            if (allDamaged) { 
                this.state = "demolished"; // cambio el estado del edificio
            }
        }
    
        return estadoCambiado; // devuelvo si se cambió el estado de alguna posición
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
