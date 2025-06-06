public class Maquina {
    private String maquina;
    private int piezasQueProduce;

    public Maquina(String maquina, int piezasQueProduce) {
        this.maquina = maquina;
        this.piezasQueProduce = piezasQueProduce;
    }

    public String getMaquina() {
        return this.maquina;
    }

    public int getPiezasQueProduce() {
        return this.piezasQueProduce;
    }

    @Override
    public String toString() {
        return this.getMaquina();
    }
    
}
