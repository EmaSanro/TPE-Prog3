import java.util.ArrayList;

public class Fabrica {
    private ArrayList<Maquina> maquinas;
    private int piezasAFabricar;
    private ArrayList<Maquina> solucion;
    private int cantEstados;
    private int cantConsiderados;

    public Fabrica(ArrayList<Maquina> maquinas, int piezasAFabricar) {
        this.cantEstados = 0;
        this.cantConsiderados = 0;
        this.maquinas = maquinas;
        this.piezasAFabricar = piezasAFabricar;
        this.solucion = new ArrayList<>();
    }

    public int getPiezasAFabricar() {
        return piezasAFabricar;
    }

    public int getCantPuestasEnMarcha() {
        return solucion.size();
    }
    
    public int cantEstados() {
        return cantEstados;
    }

    public int cantConsiderados(){
        return cantConsiderados;
    }

    /*
    * <<Estrategia de resolución Backtracking>>
    * - Cómo se genera el árbol de exploración:
        El arbol de exploracion se genera iterando todas las maquinas que tenemos
        disponibles, luego cada una la vamos agregando a un estado de posible solucion
        y verificamos que se cumpla con la condicion de que la suma de piezas de la 
        maquinas del estado no sea mayor a las piezas a fabricar. Luego, se llama 
        recursivamente con el estado actual y finalmente en caso de que no se cumpla
        la condicion o se haya explorado por completo se quita la maquina para iniciar
        una nueva posible solucion. 

    * - Cuáles son los estados finales y estados solución:
        Estados finales: [M2, M2, M2, M2, M6], [M2, M2, M2, M5, M3, M1] y
                         [M5, M5, M5, M5, M5, M5, M5]
        Estados solucion: [M4, M4, M5] y [M4, M1, M2]
    * - Posibles podas: Se implemento una poda la cual compara los tamaños del posible 
                        estado solucion y la mejor solucion encontrada hasta el momento 
                        (en caso de existir), si el tamaño del estado es menor a la 
                        solucion encontrada, se llama recursivamente, si no quita la 
                        maquina y continua con la siguiente.    

    * - Como extra creamos una clase Estado donde se van agregando y quitando las maquinas puestas 
        en marcha para luego poder obtener:
         - la cantidad de piezas que se van generando hasta el momento.
         - la cantidad de maquinas puestas en marcha.
    */
    public ArrayList<Maquina> minimizarCosto() {
        solucion.clear();
        Estado estado = new Estado();
        minimizarCostoBacktracking(estado, 0);
        return solucion;
    }

    private void minimizarCostoBacktracking(Estado estado, int contador) {
        if(estado.sumaPiezas() == piezasAFabricar) {
            if(estado.getCantMaquinas() < solucion.size() || solucion.size() == 0) {
                solucion.clear();
                solucion.addAll(new ArrayList<>(estado.getMaquinas()));
                cantEstados = contador;
            }
        } else {
            for(Maquina maquina: maquinas) {
                estado.agregarMaquina(maquina);
                contador++;
                if(estado.sumaPiezas() <= piezasAFabricar) {
                    if (!(solucion.size() > 0 && estado.getCantMaquinas() >= solucion.size())) {
                        minimizarCostoBacktracking(estado, contador);
                    }
                }
                estado.quitarMaquina();
            }
        }
    }

    /*
    * <<Estrategia de resolución Greedy>>
    * - Cuáles son los candidatos:
    *   Son todas las maquinas disponibles a utilizar. 
    * - Estrategia de selección de candidatos:
    *   Se ordenan los candidatos en base a la cantidad de piezas que produce 
    *   (de manera descendiente) y se selecciona el primero.
    * - Consideraciones respecto a encontrar o no solución: 
    *   La consideracion que se tuvo es que se va a encontrar una solucion siempre y cuando
    *   la suma de las piezas de las maquinas agregadas sea igual a la cantidad de piezas a 
    *   fabricar.
    * - La funcion 'esFactible()' realiza: Por cada maquina se chequea que la suma de las piezas 
    *   de ella y de las ya agregadas sea menor o igual a las piezas a fabricar, en caso de ser 
    *   asi se agrega a la solucion, sino se remueve la maquina y se continua con la siguiente. 
    */

    public ArrayList<Maquina> greedy() {
        solucion.clear();
        ordenar();
        while (!solucion()) {
            Maquina maq = seleccionarCandidato();
            cantConsiderados++;
            if (esFactible(maq)){ 
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

}
