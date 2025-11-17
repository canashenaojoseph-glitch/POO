public class Puerta {
    private boolean abierta = false;

    public void abrir() {
        abierta = true;
        System.out.println("  Puertas abriendo...");
    }

    public void cerrar() {
        abierta = false;
        System.out.println("  Puertas cerrando...");
    }

    public boolean isAbierta() { return abierta; }
}
