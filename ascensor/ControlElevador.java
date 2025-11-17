import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ControlElevador {
    private final int totalPisos;
    private final List<Elevador> elevadores = new ArrayList<>();
    private final Map<Integer, BotonPiso> botonesPiso = new HashMap<>();

    public ControlElevador(int totalPisos, int cantidadElevadores) {
        this.totalPisos = totalPisos;
        for (int i = 1; i <= totalPisos; i++) botonesPiso.put(i, new BotonPiso(i));
        for (int i = 1; i <= cantidadElevadores; i++) elevadores.add(new Elevador(i, 1));
    }

    public int getTotalPisos() { return totalPisos; }

    // Flujo completo: asignar elevador, mover a usuario, preguntar destino y mover al destino
    public void procesarSolicitudPiso(int numeroPiso, Scanner scanner) {
        if (numeroPiso < 1 || numeroPiso > totalPisos) {
            System.out.println("Piso inválido.");
            return;
        }

        botonesPiso.get(numeroPiso).encender();
        System.out.println("¡Botón del piso " + numeroPiso + " iluminado!");

        Elevador elegido = encontrarMejorElevador(numeroPiso);
        if (elegido == null) {
            System.out.println("No se encontró elevador disponible.");
            return;
        }

        // Mover elevador hasta el piso del usuario
        elegido.moverHasta(numeroPiso);

        // Al llegar: apagar botón y mostrar mensaje
        apagarBotonPiso(numeroPiso);
        System.out.println("Botón del piso " + numeroPiso + " apagado");

        // Preguntar destino desde dentro del ascensor
        System.out.print("¿A qué piso desea ir? (1-" + totalPisos + "): ");
        String s = scanner.nextLine().trim();
        int destino;
        try {
            destino = Integer.parseInt(s);
            if (destino < 1 || destino > totalPisos) {
                System.out.println("Piso destino inválido. Operación cancelada.");
                return;
            }
        } catch (NumberFormatException ex) {
            System.out.println("Entrada inválida. Operación cancelada.");
            return;
        }

        // Mover al destino mostrando si sube o baja
        elegido.moverHasta(destino);
        System.out.println("El ascensor recuerda que quedó en el piso " + elegido.getPisoActual());
    }

    // Política inteligente mejorada: 1) mismo dirección/camino, 2) último movimiento compatible, 3) detenido cercano, 4) cualquiera
    public Elevador encontrarMejorElevador(int pisoSolicitud) {
        String dirSolicitud = calcularDireccionAutomatica(pisoSolicitud);
        
        // 1. Ascensores actualmente moviéndose en la misma dirección y en el camino correcto
        for (Elevador e : elevadores) {
            if (dirSolicitud.equals("SUBIR") && e.getDireccion() == Elevador.Direccion.SUBIENDO && e.getPisoActual() <= pisoSolicitud) {
                return e;
            }
            if (dirSolicitud.equals("BAJAR") && e.getDireccion() == Elevador.Direccion.BAJANDO && e.getPisoActual() >= pisoSolicitud) {
                return e;
            }
        }
        
        // 2. Ascensores detenidos cuyo último movimiento fue compatible (p. ej., si están arriba y bajaban)
        Elevador mejor = null;
        int mejorDist = Integer.MAX_VALUE;
        for (Elevador e : elevadores) {
            if (e.getDireccion() == Elevador.Direccion.DETENIDO) {
                // Preferir ascensores cuya última dirección sea compatible
                boolean compatibleUltima = false;
                if (dirSolicitud.equals("BAJAR") && e.getUltimaDireccion() == Elevador.Direccion.BAJANDO) compatibleUltima = true;
                if (dirSolicitud.equals("SUBIR") && e.getUltimaDireccion() == Elevador.Direccion.SUBIENDO) compatibleUltima = true;
                
                int d = Math.abs(e.getPisoActual() - pisoSolicitud);
                if (compatibleUltima && d < mejorDist) {
                    mejorDist = d;
                    mejor = e;
                }
            }
        }
        if (mejor != null) return mejor;
        
        // 3. Ascensores detenidos (sin compatibilidad de última dirección), elegir cercano
        mejor = null;
        mejorDist = Integer.MAX_VALUE;
        for (Elevador e : elevadores) {
            if (e.getDireccion() == Elevador.Direccion.DETENIDO) {
                int d = Math.abs(e.getPisoActual() - pisoSolicitud);
                if (d < mejorDist) {
                    mejorDist = d;
                    mejor = e;
                }
            }
        }
        if (mejor != null) return mejor;
        
        // 4. Cualquier ascensor (última opción): elegir el más cercano
        mejor = null;
        mejorDist = Integer.MAX_VALUE;
        for (Elevador e : elevadores) {
            int d = Math.abs(e.getPisoActual() - pisoSolicitud);
            if (d < mejorDist) {
                mejorDist = d;
                mejor = e;
            }
        }
        return mejor;
    }
    
    // Calcular dirección automática según la política original
    private String calcularDireccionAutomatica(int piso) {
        if (piso == 1) return "SUBIR";
        if (piso == totalPisos) return "BAJAR";
        int mitad = totalPisos / 2;
        if (piso <= mitad) return "SUBIR";
        return "BAJAR";
    }

    public void apagarBotonPiso(int piso) {
        BotonPiso b = botonesPiso.get(piso);
        if (b != null) b.apagar();
    }
}
