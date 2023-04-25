import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GA03 {
    public void einfacherGenetischerAlgorithmus(int maxGeneration) {
        Scanner scanner = new Scanner(System.in);
        int generation = 0;
        Population p = new Population();
        double avgFitness = p.evaluation();
        while (avgFitness < 200 &&
                generation < maxGeneration) {
            generation++;
            p = p.selection(); // age biased replacement
            p.crossover();
            p.mutation();
            avgFitness = p.evaluation();
            // scanner.nextLine();
        }
        p.print();
        scanner.close();
    }

    public static void main(String[] args) {
        new GA03().einfacherGenetischerAlgorithmus(100);
    }

}

class Population {

    static int anzahlIndividuen = 20;

    private List<Individual> population = new ArrayList<>();
    private int generation = 0;

    public Population() {
        for (int i = 0; i < anzahlIndividuen; i++) {
            population.add(new Individual());
        }
    }

    public double evaluation() {

        for (Individual i : population) {
            i.calcFitness();
        }

        double avgFitness = 0;
        for (Individual i : population) {
            avgFitness += i.fitness;
        }
        avgFitness /= population.size();
        System.out.println("Generation " + generation + " avgFitness " + avgFitness);
        generation++;
        return avgFitness;

    }

    public Population selection() {
        // Gesamtfitness berechnen
        double totalFitness = 0;
        for (Individual i : population) {
            i.calcFitness();
            totalFitness += i.fitness;
        }
        // Prozentsatz der Fitness berechnen
        for (Individual i : population) {
            i.fitnessProzent = i.fitness / totalFitness;
        }
        List<Individual> newPopulation = new ArrayList<>();
        // Individuen auswählen
        for (int i = 0; i < anzahlIndividuen; i++) {

            int random = (int) (Math.random() * 100);
            int sum = 0;
            for (Individual ind : population) {
                sum += ind.fitnessProzent * 100;
                if (sum >= random) {
                    newPopulation.add(ind);
                    break;
                }
            }

        }
        population = newPopulation;
        return this;
    }

    public void crossover() {
        // Crossover probability 25%
        for (int i = 0; i < population.size(); i++) {
            if (Math.random() < 0.25) {
                int j = (int) (Math.random() * population.size());
                int crossOverPoint = (int) (Math.random() * 18);
                byte[] temp = Arrays.copyOfRange(population.get(i).genes, 0, crossOverPoint);
                byte[] temp2 = Arrays.copyOfRange(population.get(j).genes, crossOverPoint, 18);
                byte[] temp3 = new byte[18];
                byte[] temp4 = new byte[18];
                System.arraycopy(temp, 0, temp3, 0, temp.length);
                System.arraycopy(temp2, 0, temp3, temp.length, temp2.length);
                System.arraycopy(temp2, 0, temp4, 0, temp2.length);
                System.arraycopy(temp, 0, temp4, temp2.length, temp.length);
                population.get(i).genes = temp3;
                population.get(j).genes = temp4;

            }
        }
    }

    public void mutation() {
        // Mutation probability 1%
        for (Individual i : population) {
            for (int j = 0; j < i.genes.length; j++) {
                if (Math.random() <= 0.01) {
                    i.genes[j] = (byte) (1 - i.genes[j]);
                }
            }
        }

    }

    public void print() {
        // Sort by fitness
        population.sort((i1, i2) -> Double.compare(i2.fitness, i1.fitness));
        System.out.println("Generation " + generation);
        for (Individual i : population) {
            System.out.println(i.toString() + "\n");

        }
        System.out.println();
        generation++;
    }
}

class Individual {
    public byte[] genes = new byte[18];
    double fitness = 0;
    double fitnessProzent = 0;

    public Individual() {
        for (int i = 0; i < genes.length; i++) {
            genes[i] = (byte) Math.round(Math.random());
        }
    }

    public void calcFitness() {
        double x = decode();
        // f(x) = 150 + 42 * x * sin(20 *x) * cos(2*x)
        fitness = 150 + 42 * x * Math.sin(20 * x) * Math.cos(2 * x);

    }

    public double decode() {
        // Xmin + Genotyp * ((Xmax - Xmin) / (2^18 - 1))
        double xmin = 2.0;
        double xmax = 4.0;
        // Bytes to decimal
        double d = 0;
        for (int i = 0; i < genes.length; i++) {
            d += genes[i] * Math.pow(2, i);
        }
        double fraction = (xmax - xmin) / (Math.pow(2, genes.length) - 1);
        return xmin + d * fraction;
    }

    @Override
    public String toString() {
        return "Individual [genes=" + Arrays.toString(genes) + ", fitness=" + fitness + ", fitnessProzent="
                + fitnessProzent + "]";
    }
}