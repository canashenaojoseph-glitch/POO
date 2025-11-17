import java.util.Scanner;

public class SistemaElevador {
    public static void main(String[] args) {
        final int PISOS = 10;
        final int ELEVADORES = 2;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=== SISTEMA DE ASCENSORES ===");

            ControlElevador control = new ControlElevador(PISOS, ELEVADORES);

            while (true) {
                System.out.println();
                System.out.println("1. Presionar botón de piso");
                System.out.println("2. Salir");
                System.out.print("Seleccione una opción: ");
                String opt = scanner.nextLine().trim();
                switch (opt) {
                    case "1" -> {
                        System.out.print("¿En qué piso se encuentra? (1-" + PISOS + "): ");
                        String s = scanner.nextLine().trim();
                        try {
                            int piso = Integer.parseInt(s);
                            control.procesarSolicitudPiso(piso, scanner);
                        } catch (NumberFormatException ex) {
                            System.out.println("Entrada inválida.");
                        }
                    }
                    case "2" -> {
                        System.out.println("Saliendo...");
                        return;
                    }
                    default -> System.out.println("Opción inválida.");
                }
            }
        }
    }
}
