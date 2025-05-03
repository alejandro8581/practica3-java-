public class Building {
    private Position[] floors;
    private String state; //puede ser intact o demolished
    private Type type;
    
    public Building(int nPlantas){
        if (nPlantas<1 || nPlantas>4){
            //se estandariza a 1 planta, ergo SHACK
            type=1;//????????
        }
        else
            type=nPlantas; //??

        state="intact";
    }
    public boolean setFloor(Position poser,int posDfloor){
        if (posDfloor<=floors.length+1 && posDfloor>=0 && floors[posDfloor]==null){
            floors[posDfloor]=poser;
            poser.changeState(1); //cambia a FULL
            poser.setFloor(posDfloor);
            return true;
        }
        else
            return false;
    }
    public boolean demolish(int i, int j){
        boolean todasColocadas=true;
        boolean existePos=false;
        int posExistente;
        for (int k=0;k<floors.length;k++){ //si cualquier posicion es nula, no están todas colocadas
            if (floors[k]==null)
                todasColocadas=false;
            if (floors[k].getRow==i && floors[k].getColumn)
                existePos=true;
                posExistente=k;
        }
        if (!todasColocadas){
            throw new Error("building under construction");
        }
        else if (existePos){
            if (floors[posExistente].getState!=DAMAGED && floors[posExistente-1].getState==DAMAGED){ //presupongo que si el estado de el de abajo justo, eso implica que todos estan dañados
                floors[posExistente].changeState("demolished"); //me hallo anonadado ante el estado al que me piden que le cambie, pues se lo han sacado del anillo
                return true;
            }
            else
                return false;
        }
        //niggas be making the wildest methods
    }
    public State getState(){
        return this.state;
    }
    public Type getType(){
        return this.type;
    }
}
