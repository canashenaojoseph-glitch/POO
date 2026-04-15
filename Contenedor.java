package proyecto_taller;

public class Contenedor {
    private String id;
    private double peso;
    private int prioridad;

    public Contenedor(String id, double peso, int prioridad) {
        this.id = id;
        this.peso = peso;
        this.prioridad = prioridad;
    }

    public String getId() {
        return id;
    }

    public double getPeso() {
        return peso;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "Contenedor{id='" + id + "', peso=" + peso + ", prioridad=" + prioridad + "}";
    }
}