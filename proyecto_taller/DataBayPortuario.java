package proyecto_taller;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DataBayPortuario {
    private final Contenedor[] manifiesto;
    private final Contenedor[][] patio;
    private final Queue<Contenedor> inspeccion;
    private final Stack<Contenedor> buque;
    private int contadorManifiesto;

    public DataBayPortuario(int tamanoManifiesto, int filasPatio, int columnasPatio) {
        this.manifiesto = new Contenedor[tamanoManifiesto];
        this.patio = new Contenedor[filasPatio][columnasPatio];
        this.inspeccion = new LinkedList<>();
        this.buque = new Stack<>();
        this.contadorManifiesto = 0;
    }

    // Modulo 1: Registro de Manifiesto
    public boolean registrarContenedor(String id, double peso, int prioridad) {
        if (contadorManifiesto < manifiesto.length) {
            manifiesto[contadorManifiesto] = new Contenedor(id, peso, prioridad);
            contadorManifiesto++;
            System.out.println("Contenedor " + id + " registrado.");
            return true;
        }
        System.out.println("Manifiesto lleno.");
        return false;
    }

    public void mostrarManifiesto() {
        System.out.println("Manifiesto:");
        for (int i = 0; i < contadorManifiesto; i++) {
            System.out.println(manifiesto[i]);
        }
    }

    // Modulo 2: Patio de Almacenamiento (Matriz)
    public boolean ubicarEnPatio(Contenedor contenedor) {
        for (int i = 0; i < patio.length; i++) {
            for (int j = 0; j < patio[i].length; j++) {
                if (patio[i][j] == null) {
                    patio[i][j] = contenedor;
                    System.out.println("Contenedor " + contenedor.getId() + " en patio [" + i + "][" + j + "]");
                    return true;
                }
            }
        }
        System.out.println("Patio lleno.");
        return false;
    }

    public void mostrarPatio() {
        System.out.println("Patio:");
        for (int i = 0; i < patio.length; i++) {
            for (int j = 0; j < patio[i].length; j++) {
                if (patio[i][j] == null) {
                    System.out.print("[VACIO] ");
                } else {
                    System.out.print("[" + patio[i][j].getId() + "] ");
                }
            }
            System.out.println();
        }
    }

    // Modulo 3: Bahia de Inspeccion (Cola FIFO)
    public void agregarAInspeccion(Contenedor contenedor) {
        if (contenedor.getPrioridad() == 1) {
            inspeccion.add(contenedor);
            System.out.println("Contenedor " + contenedor.getId() + " a inspeccion.");
        } else {
            System.out.println("Contenedor " + contenedor.getId() + " no requiere inspeccion.");
        }
    }

    public Contenedor inspeccionarSiguiente() {
        Contenedor c = inspeccion.poll();
        if (c != null) {
            System.out.println("Inspeccionado: " + c.getId());
        }
        return c;
    }

    public void mostrarInspeccion() {
        System.out.println("Cola de inspeccion: " + inspeccion);
    }

    // Modulo 4: Estiba en el Buque (Pila LIFO)
    public void cargarEnBuque(Contenedor contenedor) {
        buque.push(contenedor);
        System.out.println("Cargado en buque: " + contenedor.getId());
    }

    public Contenedor descargarDeBuque() {
        if (!buque.isEmpty()) {
            Contenedor c = buque.pop();
            System.out.println("Descargado: " + c.getId());
            return c;
        }
        System.out.println("Buque vacio.");
        return null;
    }

    public void mostrarBuque() {
        System.out.println("Buque: " + buque);
    }

    // Flujo integrado
    public void ejecutarFlujo() {
        System.out.println("Iniciando flujo...");
        mostrarManifiesto();

        for (int i = 0; i < contadorManifiesto; i++) {
            ubicarEnPatio(manifiesto[i]);
            agregarAInspeccion(manifiesto[i]);
        }

        mostrarPatio();
        mostrarInspeccion();

        while (!inspeccion.isEmpty()) {
            inspeccionarSiguiente();
        }

        for (int i = 0; i < contadorManifiesto; i++) {
            cargarEnBuque(manifiesto[i]);
        }

        mostrarBuque();
    }

    public static void main(String[] args) {
        DataBayPortuario puerto = new DataBayPortuario(5, 3, 3);

        puerto.registrarContenedor("C1", 10.0, 1);
        puerto.registrarContenedor("C2", 8.0, 2);
        puerto.registrarContenedor("C3", 12.0, 1);
        puerto.registrarContenedor("C4", 5.0, 3);
        puerto.registrarContenedor("C5", 15.0, 2);

        puerto.ejecutarFlujo();
    }
}