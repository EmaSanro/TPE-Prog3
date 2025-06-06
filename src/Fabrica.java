import java.util.ArrayList;

public class Fabrica {
    private ArrayList<Maquina> maquinas;
    private int piezasAFabricar;
    private ArrayList<Maquina> solucion;

    public Fabrica(ArrayList<Maquina> maquinas, int piezasAFabricar) {
        this.maquinas = maquinas;
        this.piezasAFabricar = piezasAFabricar;
        this.solucion = new ArrayList<>();
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
