public class BacteriaSimulation {
    Population population;

    public static void main(String[] args) {
        BacteriaSimulation bacteriaSimulation = new BacteriaSimulation();
        bacteriaSimulation.run();
    }

    public void run() {
        // int[] expectedChromosome = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        int expectedChromosomeLength = 20;
        int[] expectedChromosome = new int[expectedChromosomeLength];
        for (int gene = 0; gene < expectedChromosome.length; gene++) {
            if (0.5 < Math.random()) {
                expectedChromosome[gene] = 1;
            } else {
                expectedChromosome[gene] = 0;
            }
        }
        String expectedChromosomeString = "";
        for (int gene = 0; gene < expectedChromosome.length; gene++) {
            expectedChromosomeString += expectedChromosome[gene];
        }
        population = new Population(1000, expectedChromosomeLength, expectedChromosome);

        int generationCount = 0;
        int avgFitness = population.evaluate();
        int maxGenerations = 20000;
        while (avgFitness < (expectedChromosomeLength * 0.95)
                && generationCount < maxGenerations) {
            generationCount++;
            // System.out.println("Generation: " + generationCount + " Average Fitness: " +
            // avgFitness);
            population = population.selection();
            population.crossover();
            population.mutation();

            avgFitness = population.evaluate();
        }
        System.out.println("Generation " + generationCount);
        System.out.println("Expected: " + expectedChromosomeString);
        System.out.println("Avg Fitness: " + avgFitness);
        System.out.println("Fittest Individual: " + population.getFittest().toString());
        System.out.println("Fittest has fitness: " + population.getFittest().getFitness());
    }
}
