public class BacteriaSimulation {
    private Bacteria[] population;
    private int generation = 0;

    public BacteriaSimulation(int populationSize) {
        population = new Bacteria[populationSize];
        for (int i = 0; i < population.length; i++) {
            population[i] = new Bacteria();
        }
    }

    public void run() {
        while (true) {
            System.out.println("Generation " + generation);
            for (int i = 0; i < population.length; i++) {
                System.out.println(population[i]);
            }
            System.out.println();
            generation++;
            population = nextGeneration();
            return;
        }
    }

    public static void main(String[] args) {
        BacteriaSimulation sim = new BacteriaSimulation(10);
        sim.run();
    }

    private Bacteria[] nextGeneration() {
        Bacteria[] next = new Bacteria[population.length];
        for (int i = 0; i < next.length; i++) {
            next[i] = new Bacteria();
        }
        return next;
    }

}
