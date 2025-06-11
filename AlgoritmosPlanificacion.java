import java.util.*;

    public class AlgoritmosPlanificacion {

        // FCFS
        public static void FCFS(List<Proceso> procesos) {
            procesos.sort(Comparator.comparingInt(Proceso::getTiempoLlegada));
            imprimirResultados("FCFS - First-Come, First-Served", procesos);
        }

        // SJF
        public static void SJF(List<Proceso> procesos) {
            procesos.sort(Comparator.comparingInt(Proceso::getRafagaCPU));
            imprimirResultados("SJF - Shortest Job First", procesos);
        }

        // SRTF
        public static void SRTF(List<Proceso> procesos) {
            List<Proceso> copia = new ArrayList<>(procesos);
            copia.sort(Comparator.comparingInt(Proceso::getRafagaCPU));
            imprimirResultados("SRTF - Shortest Remaining Time First", copia);
        }

        // Prioridad
        public static void Prioridad(List<Proceso> procesos) {
            procesos.sort(Comparator.comparingInt(Proceso::getPrioridad));
            imprimirResultados("PS - Priority Scheduling", procesos);
        }

        // Round Robin
        public static void RoundRobin(List<Proceso> procesos, int quantum) {
            Queue<Proceso> cola = new LinkedList<>(procesos);
            int[] rafagasRestantes = procesos.stream().mapToInt(Proceso::getRafagaCPU).toArray();
            int[] tiemposEspera = new int[procesos.size()];
            int tiempoActual = 0;
            StringBuilder gantt = new StringBuilder();

            // Mostrar tabla de procesos 
            System.out.println("\n=== RR - Round Robin (Quantum: " + quantum + ") ===");
            System.out.println("| Proceso | Prioridad | Ráfaga CPU |");
            System.out.println("|---------|-----------|------------|");
            for (Proceso p : procesos) {
                System.out.printf("| %-7s | %-9d | %-10d |\n", 
                    p.getId(), p.getPrioridad(), p.getRafagaCPU());
            }

            // Simulación
            while (!cola.isEmpty()) {
                Proceso p = cola.poll();
                int idx = procesos.indexOf(p);
                int tiempoEjecucion = Math.min(quantum, rafagasRestantes[idx]);

                if (rafagasRestantes[idx] == p.getRafagaCPU()) {
                    tiemposEspera[idx] = Math.max(0, tiempoActual - p.getTiempoLlegada());
                }

                gantt.append(String.format("| %s (%d-%d) ", p.getId(), tiempoActual, tiempoActual + tiempoEjecucion));
                tiempoActual += tiempoEjecucion;
                rafagasRestantes[idx] -= tiempoEjecucion;

                if (rafagasRestantes[idx] > 0) {
                    cola.add(p);
                }
            }

            // Mostrar resultados
            double tiempoMedio = Arrays.stream(tiemposEspera).average().orElse(0);
            System.out.printf("\nTiempo medio de espera: %.1f ms\n", tiempoMedio);

            System.out.println("\nDiagrama de Gantt:");
            System.out.println(gantt.toString() + "|");
        }

        // Otro Metodo
private static void imprimirResultados(String algoritmo, List<Proceso> procesos) {
    System.out.println("\n=== " + algoritmo + " ===");

    // Imprimir orden de llegada
    System.out.print("✓ Orden de llegada (en instante 0) a cola de procesos listos: ");
    for (int i = 0; i < procesos.size(); i++) {
        System.out.print(procesos.get(i).getId());
        if (i < procesos.size() - 1) {
            System.out.print(", ");
        }
    }
    System.out.println();

    // Imprimir tabla
    System.out.println("| Proceso | Prioridad | Ráfaga CPU |");
    System.out.println("|---------|-----------|------------|");

    int tiempoActual = 0;
    int[] tiemposInicio = new int[procesos.size()];
    StringBuilder gantt = new StringBuilder();

    for (int i = 0; i < procesos.size(); i++) {
        Proceso p = procesos.get(i);

        if (i == 0) {
            tiempoActual = 0; // Forzar inicio en 0
        }

        tiemposInicio[i] = tiempoActual;

        System.out.printf("| %-7s | %-9d | %-10d |\n",
                p.getId(), p.getPrioridad(), p.getRafagaCPU());

        gantt.append(String.format("| %s (%d-%d) ", p.getId(), tiempoActual, tiempoActual + p.getRafagaCPU()));
        tiempoActual += p.getRafagaCPU();
    }

    double tiempoMedioEspera = Arrays.stream(tiemposInicio).average().orElse(0);
    System.out.printf("\n✓ Tiempo medio de espera: (");
    for (int i = 0; i < tiemposInicio.length; i++) {
        System.out.print(tiemposInicio[i]);
        if (i < tiemposInicio.length - 1) {
            System.out.print("+");
        }
    }
    System.out.printf(")/%d = %.1f ut\n", procesos.size(), tiempoMedioEspera);

    System.out.println("\n✓ Diagrama de Gantt para la planificación:");
    System.out.println(gantt.toString() + "|");
}
}


