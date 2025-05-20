public class Position {
    private int row;
    private int column;
    private int floor;
    private State state;

    public Position(int fila,int colusna){
        state=State.EMPTY; 
        floor=-1;
        row=fila;
        column=colusna;
    }
    public boolean changeState(int st) throws Error{
        State[] estados = State.values(); //este array contiene todos los valores de State
        if (st < 0 || st >= estados.length) { // Validar si el estado es válido
            throw new Error("invalid state: " + st);
        }//COPILOT
            if (st >= 0 && st < estados.length){ // st es un estado valido?
                if (estados[st] != this.state){ //st es distinto del estado actual?
                    this.state=estados[st]; //cambia el estadoa actual por st
                    return true;
                }
                
            }
        
        return false;
    }
    public boolean equals(Position poser) throws Error{
        if (poser == null) { // Validar que poser no sea null
            throw new Error("null position provided for comparison");
        }//COPILOT
        if (poser.getRow()==this.row && poser.getColumn()==this.column){
            return true;
        }
        else
            return false;

    }
   

    public String toString() {
        // Validar que las coordenadas sean válidas
        if (this.row < 1 || this.column < 1) {
            return "(invalid position)";
        }
        return "(" + this.row + "," + this.column + ")";
    }
    public void setFloor(int nFloor){
        this.floor=nFloor;
    }
    public State getState(){
        return this.state;
    }
    public  int getFloor(){
        return this.floor;
    }
    public int getRow(){
        return this.row;
    }
    public int getColumn(){
        return this.column;
    }

}
