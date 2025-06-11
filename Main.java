import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Proceso> procesosBase = new ArrayList<>();

        System.out.println("=== SIMULADOR DE ALGORITMOS DE PLANIFICACIÓN ===");
        System.out.print("¿Cuántos procesos desea ingresar?: ");
        int n = leerEntero(scanner);

        // Ingreso automático de procesos (P1, P2...)
        for (int i = 0; i < n; i++) {
            System.out.println("\nProceso P" + (i + 1) + ":");
            System.out.print("Prioridad (número entero): ");
            int prioridad = leerEntero(scanner);
            System.out.print("Ráfaga CPU (ms): ");
            int rafaga = leerEntero(scanner);
            System.out.print("Tiempo de llegada (ms): ");
            int llegada = leerEntero(scanner);

            procesosBase.add(new Proceso("P" + (i + 1), prioridad, rafaga, llegada));
        }

        while (true) {
            // Clonar procesos para cada ejecución
            List<Proceso> procesos = new ArrayList<>();
            for (Proceso p : procesosBase) {
                procesos.add(new Proceso(p.getId(), p.getPrioridad(), p.getRafagaCPU(), p.getTiempoLlegada()));
            }

            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. FCFS - First-Come, First-Served");
            System.out.println("2. SJF - Shortest Job First");
            System.out.println("3. SRTF - Shortest Remaining Time First");
            System.out.println("4. PS - Priority Scheduling");
            System.out.println("5. RR - Round Robin");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción (1-6): ");

            int opcion = leerEntero(scanner);
            switch (opcion) {
                case 1 -> AlgoritmosPlanificacion.FCFS(procesos);
                case 2 -> AlgoritmosPlanificacion.SJF(procesos);
                case 3 -> AlgoritmosPlanificacion.SRTF(procesos);
                case 4 -> AlgoritmosPlanificacion.Prioridad(procesos);
                case 5 -> {
                    System.out.print("Ingrese quantum para Round Robin (ms): ");
                    int quantum = leerEntero(scanner);
                    AlgoritmosPlanificacion.RoundRobin(procesos, quantum);
                }
                case 6 -> {
                    System.out.println("¡Hasta luego!");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Opción no válida.");
            }

            // Pausa
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
            scanner.nextLine();
        }
    }

    private static int leerEntero(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("¡Error! Ingrese un número válido: ");
                scanner.next();
            }
        }
    }
}