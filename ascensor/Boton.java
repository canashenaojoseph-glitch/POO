public abstract class Boton {
    protected int piso;
    protected boolean iluminado = false;

    public Boton(int piso) {
        this.piso = piso;
    }

    public int getPiso() { return piso; }

    public boolean isIluminado() { return iluminado; }

    public void encender() { iluminado = true; }

    public void apagar() { iluminado = false; }
}
