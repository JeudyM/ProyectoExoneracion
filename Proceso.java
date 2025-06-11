public class Proceso {
    private String id;
    private int prioridad;
    private int rafagaCPU;
    private int tiempoLlegada;

    public Proceso(String id, int prioridad, int rafagaCPU, int tiempoLlegada) {
        this.id = id;
        this.prioridad = prioridad;
        this.rafagaCPU = rafagaCPU;
        this.tiempoLlegada = tiempoLlegada;
    }

    // Getters y Setters
    public String getId() { return id; }
    public int getPrioridad() { return prioridad; }
    public int getRafagaCPU() { return rafagaCPU; }
    public int getTiempoLlegada() { return tiempoLlegada; }
    public void setRafagaCPU(int rafagaCPU) { this.rafagaCPU = rafagaCPU; }
}