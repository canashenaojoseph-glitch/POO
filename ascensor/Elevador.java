public class Elevador {
    public enum Direccion {SUBIENDO, BAJANDO, DETENIDO}

    private final int id;
    private int pisoActual;
    private Direccion direccion = Direccion.DETENIDO;
    private Direccion ultimaDireccion = Direccion.DETENIDO;  // Recordar última dirección de movimiento
    private final Puerta puerta = new Puerta();

    public Elevador(int id, int pisoInicial) {
        this.id = id;
        this.pisoActual = pisoInicial;
    }

    public int getId() { return id; }
    public int getPisoActual() { return pisoActual; }
    public Direccion getDireccion() { return direccion; }
    public Direccion getUltimaDireccion() { return ultimaDireccion; }

    public void abrirPuerta() { puerta.abrir(); }
    public void cerrarPuerta() { puerta.cerrar(); }

    // Mover hasta un piso destino mostrando paso a paso
    public void moverHasta(int destino) {
        if (destino == pisoActual) {
            System.out.println("Ascensor " + id + ": ya está en el piso " + pisoActual);
            abrirPuerta();
            cerrarPuerta();
            return;
        }

        if (destino > pisoActual) direccion = Direccion.SUBIENDO;
        else direccion = Direccion.BAJANDO;

        // Recordar la dirección de este movimiento
        ultimaDireccion = direccion;

        while (pisoActual != destino) {
            if (direccion == Direccion.SUBIENDO) {
                pisoActual++;
                System.out.println("Ascensor " + id + ": subiendo al piso " + pisoActual + "...");
            } else {
                pisoActual--;
                System.out.println("Ascensor " + id + ": bajando al piso " + pisoActual + "...");
            }
        }

        direccion = Direccion.DETENIDO;
        abrirPuerta();
        cerrarPuerta();
    }
}
