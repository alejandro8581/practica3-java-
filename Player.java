import java.util.ArrayList;
public class Player {
    private String name;
    private Position[][] board;
    private Building[] buildings;

    public Player(String nicker) throws Error{
        if (nicker==null||nicker.isEmpty())
            throw new Error("player without name");
        
        board = new Position[12][12];
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board[i].length;j++){
                board[i][j] = new Position(i+1,j+1); //como el constructor inicializa EMPTY, empty it is. Y para que no haya 0,0; más YUNOOOU
            }
        }
    }
    public boolean createBuilding(Position pInicial,Position pFinal) throws Error{
        boolean posiFound=false;
        boolean posfFound=false;

        boolean isHorizontal=false;

        int numPos; // numero de posiciones que ocupa el edificio a crear
        int numPlantas;

        // Validar que el array buildings esté inicializado
        if (buildings == null) {
            throw new Error("no space for buildings"); // No se pueden crear edificios
        }//COPILOT
        // Validar que las posiciones inicial y final estén dentro del rango del tablero
        if (pInicial.getRow() < 1 || pInicial.getRow() > 12 || pInicial.getColumn() < 1 || pInicial.getColumn() > 12) {
            throw new Error("invalid position", pInicial.getRow(), pInicial.getColumn());
        }//COPILOT
        if (pFinal.getRow() < 1 || pFinal.getRow() > 12 || pFinal.getColumn() < 1 || pFinal.getColumn() > 12) {
            throw new Error("invalid position", pFinal.getRow(), pFinal.getColumn());
        }//COPILOT
        // Validar que no se exceda el límite de edificios permitidos
        boolean hasSpace = false;
        for (Building b : buildings) {
            if (b == null) {
                hasSpace = true;
                break;
            }
        }
        if (!hasSpace) {
            throw new Error("building limit reached"); // No hay espacio para más edificios
        }
        if (pInicial==null || pFinal==null)
            return false;
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board[i].length;j++){
                if (pInicial.equals(board[i][j]))
                    posiFound=true;
                if (pFinal.equals(board[i][j]))
                    posfFound=true;
            }
        }
        if (!posiFound || !posfFound){
            if (!posiFound) //si esta es verdad y la otra es falsa, o si ambas son verdad, debe devolver lo mismo, porque es la primera posicion
                throw new Error("bad position",pInicial.getRow(),pInicial.getColumn());
            else // aqui por logica, solo el segundo es falso, ergo se debe mandar el error con las posiciones de la final que es la unica incorrecta
                throw new Error("bad position",pFinal.getRow(),pFinal.getColumn());
        }

        /*
         * ahora me esta exigiendo que las posiciones esten en horizontal o vertical y que la final sea mayor o igual que la primera, pero no especifica un error que lanzar
         * voy a presuponer, en tal caso, que es simplemente devolver false igual que si alguna de las dos es null
         */

        if (pFinal.getRow()<pInicial.getRow() || pFinal.getColumn()<pInicial.getColumn()) //desigualdad estricta en ambos parametros
            return false;
        /*
         * para que sea horizontal o vertical, tiene que darse que la fila sea la misma o que, de ser distinta, la columna sea la misma
         * o que la columna sea la misma, o de ser distinta, la fila sea la misma
         */
        if (pFinal.getRow()!=pInicial.getRow()){
            if (pFinal.getColumn()!=pInicial.getColumn()){
                //la posicion es diagonal
                return false;
            }
            //la posicion es vertical
            isHorizontal=false;
        }
        if (pFinal.getColumn()!=pInicial.getColumn()){
            if (pFinal.getRow()!=pInicial.getRow()){
                //la posicion es diagonal
                return false;
            }
            //la posicion es horizontal
            isHorizontal=true;
        }

        /*
         * ahora dice que solo puede ocupar de 1 a 4 posiciones, para ello debemos de restar el campo que varia
         */
        if (isHorizontal){
            numPos = pFinal.getColumn()-pInicial.getColumn()+1;  //no hace falta valor absoluto porque sabemos que Final es mayor o igual siempre
        }
        else
            numPos = pFinal.getRow()-pInicial.getRow()+1;
        
        if (numPos<1 || numPos>4)
            return false; //de nuevo el enunciado no especifica ninguna excepcion, debo presuponer un return false sin mas mariconadas
        
        /*
        * ahora pide que todas las posiciones sean EMPTY, luego supongo que lo que hay que hacer es recorrer el array de board desde pInicial hasta pFinal y comprobar eso
        */

        for (int i=pInicial.getRow();i<pFinal.getRow();i++){
            for (int j=pInicial.getColumn();j<pFinal.getColumn();j++){
                if (board[i][j].getState()!=State.EMPTY)
                    throw new Error("busy position",i,j);
            }
        }

        /*
         * ahora habla de limitaciones en tipos de edificios, ergo
         */
        numPlantas=pFinal.getColumn()-pInicial.getColumn()+1;
        int STBC=0;//Same Type Building Counter
        //recorremos los edificios que ya hay, y comprobamos cuantos son del mismo tipo del que queremos crear
        Type[] tipos=Type.values(); //array con todos los valores de 
        for (int i=0;i<buildings.length;i++){
            if (buildings[i].getType()==tipos[numPlantas]) //esto tengo que arreglarlo porque no coinciden tipos, aun los enums me dan un poco la risa
                STBC++;
        }
         switch (numPlantas){
            case 1:
                //es un SHACK, del que solo puede haber 4
                if (STBC>=4)
                    throw new Error("SHACK"); // lo paso como cadena, pero es un TYPE, lo que pasa es que eso, enums, rarete
            break;
            case 2:
                //es un TOWNHOUSE, del que solo puede haber 3
                if(STBC>=3)
                    throw new Error("TOWNHOUSE");
            break;
            case 3:
                //es una DETACHED HOUSE, de la que solo pued haber 2
                if (STBC>=2)
                    throw new Error("DETACHEDHOUSE");
            break;
            case 4:
                //es un APARTMENTBUILDING, del que solo puede haber 2
                if(STBC>=2)
                    throw new Error("APARTMENTBUILDING");
            break;
         }

         //ahora llenamos las posiciones creadas
         for (int i=pInicial.getRow();i<pFinal.getRow();i++){
            for (int j=pInicial.getColumn();j<pFinal.getColumn();j++){
                board[i][j].changeState(1); //1 es FULL
            }
        }

        for (int i=0;i<buildings.length;i++){
            if (buildings[i]==null){
                buildings[i]= new Building(numPlantas);
                return true; // ahora deberia de parar antes de crear el mismo edificio en todas las posiciones libres, pero puede dar problemas asi que revisalo anyways
            }
        }
        return false;

    }

    public boolean knockDown(Position pos) throws Error, UnderConstruction, Spoiled {
        if (pos == null) return false;
    
        // Verificar si la posición está en el tablero
        boolean isInside = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (pos.equals(board[i][j])) {  // Usar equals()
                    isInside = true;
                    break;
                }
            }
        }
        if (!isInside) throw new Error(pos.getRow(), pos.getColumn());
    
        // Verificar edificios completos
        if (buildings == null) throw new UnderConstruction(this.name, "incomplete");
        for (Building b : buildings) {
            if (b == null) throw new UnderConstruction(this.name, "incomplete");
        }
    
        // Intentar demoler
        for (Building building : buildings) {
            for (int j = 0; j < building.getFloorsLength(); j++) {
                Position floorPos = building.getFloor(j);
                if (floorPos != null && floorPos.equals(pos)) {
                    if (building.demolish(pos.getRow(), pos.getColumn())) {
                        return true;  // Éxito al demoler
                    }
                }
            }
        }
    
        // Verificar si todos los edificios están demolidos
        boolean allDemo = true;
        for (Building b : buildings) {
            if (!"demolished".equals(b.getState())) {
                allDemo = false;
                break;
            }
        }
        if (allDemo) throw new Spoiled(this.name);
    
        return false;
    }
    public void show(){
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board[i].length;j++){
                switch (board[i][j].getState()) {
                    case EMPTY:
                        System.out.print("e "); //como tiene que haber un espacio, lo pongo ahi. 
                        break;
                    case FULL:
                        System.out.print("f ");
                        break;
                    case DAMAGED:
                        System.out.print("d ");
                    default:
                        break;
                }

            }
            System.out.println(); //esto deberia de imprimir un cambio de linea cada vez cambie i
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
