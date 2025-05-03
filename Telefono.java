public class Telefono{
    private double Tarifa,saldo;
    private int uso;

    public Telefono(double t){
        if (t>0)
            Tarifa=t;
        else
            Tarifa=0.09;
        uso=0;
        saldo=18.95;
    }
    public int llama(int cant){
        int c=0;
        if (cant>0){
            double necesita=cant*Tarifa;
            if(necesita<=saldo){
                uso+=cant;
                saldo-=necesita;
                c=cant;
            }
            else{
                c=(int)saldo/Tarifa;
                uso+=c;
                saldo=(c*Tarifa);
            }
            usoTotal+=c;

        }
        return c;
    }

    public int dispone(){
        int disp=(int)saldo/Tarifa;
        return disp;
    }
    public boolean recarga(double cant){
        boolean done=false;
        if (cant>0){
            saldo+=cant;
            done=true;
        }
        return done;
    }
    public double getSaldo(){
        return saldo;
    }
    public double getTarifa(){
        return Tarifa;
    }
    public int getUso(){
        return uso;
    }
    public static int getUsoTotal(){
        return usoTotal;
    }
    public int llama (int cant){
        int c=0;
        if (cant>0){
            double necesita=cant*Tarifa;
            if(necesita<=saldo){
                uso+=cant;
                saldo-=necesita;
                c=cant;
            }
        }
    }
}