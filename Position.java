public class Position {
    private int row;
    private int column;
    private int floor;
    private State state;

    public Position(int fila,int colusna){
        state=EMPTY; //o maybe state=0;???
        floor=-1;
        row=fila;
        column=colusna;
    }
    public boolean changeState(int st){
        for (int i=0;i<3;i++){
            if (st==state(i) && st != this.state()){ // la idea es comparar si el st es 0,1 o 2 (estados válidos) y ademas es distinto del state actual(this)
                //este código no tiene mucho sentido y es bastante raruno
                this.state=st;
                return true;
            }
        }
        return false;
    }
    public boolean equals(Position poser){
        if (poser.getRow()==this.row && poser.getColumn()==this.column){
            return true;
        }
        else
            return false;
    }
    public String toString(){
       // String str="";
        return "()"+this.row+","+this.column+")";
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
