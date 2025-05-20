public class Error extends Exception{

    public Error (String msg){
        super(msg); //es o esto o return super.getMessage
    }
    public Error (String msg,int i,int j){
        super(msg+" ("+i+","+j+")");
    }
    public Error (int i,int j){
        super("("+i+","+j+")");
    }
    public Error (Type tipo){
        super(tipo.toString()); //es un enum, no se si esto serviría
    }
}
