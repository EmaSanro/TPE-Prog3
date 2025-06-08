import java.util.ArrayList;

public class Fabrica {
    private ArrayList<Maquina> maquinas;
    private int piezasAFabricar;
    private ArrayList<Maquina> solucion;

    public Fabrica(ArrayList<Maquina> maquinas, int piezasAFabricar) {
        this.maquinas = maquinas;
        this.piezasAFabricar = piezasAFabricar;
        this.solucion = new ArrayList<>();
        ordenar();
    }

    public ArrayList<Maquina> minimizarCosto() {
        solucion.clear();
        Estado estado = new Estado();

        minimizarCostoBacktracking(estado);
        
        return solucion;
    }

    private void minimizarCostoBacktracking(Estado estado) {
        if(estado.sumaPiezas() == piezasAFabricar) {
            if(estado.getCantMaquinas() < solucion.size() || solucion.size() == 0) {
                solucion.clear();
                solucion.addAll(new ArrayList<Maquina>(estado.getMaquinas()));
            }
        } else {
            for(Maquina maquina: maquinas) {
                estado.agregarMaquina(maquina);
                if(estado.sumaPiezas() <= piezasAFabricar)
                    minimizarCostoBacktracking(estado);
                estado.quitarMaquina();
            }
        }
    }


// solucion = [m4];
// maquinas = [m4,m1,m2,m5,m3];
// maq = m4;

    public ArrayList<Maquina> greedy() { // Inicialmente el conjunto C contiene todos los candidatos
        while (!solucion()) {//
            Maquina maq = seleccionarCandidato(); // determina el mejor candidatos del conjunto a seleccionar
            if (esFactible(maq)){ //factible(S,x)
                solucion.add(maq);
            } else {
                maquinas.remove(maq);
            }
        }
        if (solucion()) {
            return solucion;
        }
        else{
            return null;
        }
    }
    
    public boolean solucion(){
        if(solucion.size() == 0) {
            return false;
        }
        return getSumaTotal() == piezasAFabricar;
    }

    public int getSumaTotal(){
        int sum = 0;
        for(Maquina mq: solucion){
            sum += mq.getPiezasQueProduce();
        }
        return sum;
    }

    public boolean esFactible(Maquina maquina) {
        return (this.getSumaTotal() + maquina.getPiezasQueProduce()) <= piezasAFabricar;
    }

    public Maquina seleccionarCandidato(){
        if(maquinas.size() > 0){
            return maquinas.get(0);
        }
        else{
            return null;    
        }
    }

    public void ordenar(){
        boolean swapped;
        for (int i = 0; i < maquinas.size() - 1; i++) {
            swapped = false;
            for (int j = 0; j < maquinas.size() - i - 1; j++) {
                if (maquinas.get(j).getPiezasQueProduce() < maquinas.get(j+1).getPiezasQueProduce()) {
                    Maquina temp = maquinas.get(j);
                    maquinas.set(j, maquinas.get(j+1));
                    maquinas.set(j+1, temp);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    
    public void imprimirMaquinas() {
        System.out.println("Piezas a producir: " + piezasAFabricar);
        for(Maquina maq: maquinas) {
            System.out.println("Maquina: " + maq.getMaquina() + ", piezas que produce: " + maq.getPiezasQueProduce());
        }
    }

    public String toString() {
        String pal = "[";
        for(Maquina maq: solucion) {
            pal += maq.getMaquina() + ", ";
        }
        pal += "]";
        return pal;
    }

}
