public class BacteriaSimulation {
    Population population;

    public static void main(String[] args) {
        BacteriaSimulation bacteriaSimulation = new BacteriaSimulation();
        bacteriaSimulation.run();
    }

    public void run() {
        int[] expectedChromosome = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        String expectedChromosomeString = "";
        for (int gene = 0; gene < expectedChromosome.length; gene++) {
            expectedChromosomeString += expectedChromosome[gene];
        }
        population = new Population(100, 10, expectedChromosome);

        int generationCount = 0;
        int avgFitness = population.evaluate();
        int maxGenerations = 1000;
        while (avgFitness < 9 && generationCount < maxGenerations) {
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
