import java.util.ArrayList;

public class Estado {
    private ArrayList<Maquina> maquinas;

    public Estado() {
        maquinas = new ArrayList<>();
    }

    public int getCantMaquinas() {
        return maquinas.size();
    }

    public ArrayList<Maquina> getMaquinas() {
        return this.maquinas;
    }


    public void agregarMaquina(Maquina maquina) {
        maquinas.add(maquina);
    }

    public void quitarMaquina() {
        maquinas.removeLast();
    }

    public int sumaPiezas() {
        int suma = 0;
        for(Maquina maquina: maquinas) {
            suma += maquina.getPiezasQueProduce();
        }
        return suma;
    }
}
