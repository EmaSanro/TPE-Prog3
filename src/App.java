import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        String archivo = "C:/Users/emanu/OneDrive/Escritorio/TUDAI 2025/Programacion3/TPE-Prog3/src/Configuracion.txt";
        try (BufferedReader buffer = new BufferedReader(new FileReader(archivo))){
            String linea;
            ArrayList<Maquina> maquinas = new ArrayList<>();
            int piezasTotales = Integer.parseInt(buffer.readLine());
            while((linea = buffer.readLine()) != null) {
                if(linea.trim().isEmpty()){
                    continue;
                }else {
                    String[] confMaq = linea.split(", ");
                    int piezasQueProduce = Integer.parseInt(confMaq[1]);
                    Maquina maq = new Maquina(confMaq[0], piezasQueProduce);
                    maquinas.add(maq);
                }
            }
            Fabrica fabrica = new Fabrica(maquinas, piezasTotales);
            // System.out.println("Mejor solucion backtracking: " +fabrica.minimizarCosto().toString());
            System.out.println(fabrica.greedy());
            buffer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
