import java.util.Random;

public class Population {
    private Individual[] individuals;
    private int[] expectedChromosome;
    private Individual fittest;

    public Population(int populationSize, int chromosomeLength, int[] expectedChromosome) {
        this.individuals = new Individual[populationSize];
        for (int individualCount = 0; individualCount < populationSize; individualCount++) {
            Individual individual = new Individual(chromosomeLength);
            this.saveIndividual(individualCount, individual);
        }
        this.expectedChromosome = expectedChromosome;
    }

    public Individual[] getIndividuals() {
        return this.individuals;
    }

    public Individual getIndividual(int index) {
        return this.individuals[index];
    }

    public void saveIndividual(int index, Individual individual) {
        this.individuals[index] = individual;
    }

    public int size() {
        return this.individuals.length;
    }

    public int evaluate() {
        int totalFitness = 0;
        fittest = this.getIndividual(0);
        for (int individualCount = 0; individualCount < this.size(); individualCount++) {
            Individual individual = this.getIndividual(individualCount);
            int individualFitness = 0;
            for (int geneCount = 0; geneCount < individual.getChromosomeLength(); geneCount++) {
                if (individual.getGene(geneCount) == this.expectedChromosome[geneCount]) {
                    individualFitness++;
                }
            }
            individual.setFitness(individualFitness);
            totalFitness += individualFitness;
            if (individualFitness > fittest.getFitness()) {
                fittest = individual;
            }
        }
        return totalFitness / this.size();
    }

    public Individual getFittest() {
        return this.fittest;
    }

    public Population selection() {
        RandomSelector<Individual> selector = new RandomSelector<Individual>();
        for (int individualCount = 0; individualCount < this.size(); individualCount++) {
            selector.add(this.getIndividual(individualCount).getFitness(), this.getIndividual(individualCount));
        }
        // Population newPopulation = new Population(this.size(),
        // this.getIndividual(0).getChromosomeLength(),
        // this.expectedChromosome);
        // for (int individualCount = 0; individualCount < this.size();
        // individualCount++) {
        // newPopulation.saveIndividual(individualCount, selector.next());
        // }
        // return newPopulation;
        Individual[] newIndividuals = new Individual[this.size()];
        for (int individualCount = 0; individualCount < this.size(); individualCount++) {
            newIndividuals[individualCount] = selector.next();
        }
        this.individuals = newIndividuals;
        return this;
    }

    public void mutation() {
        double anzahlMutationen = this.individuals.length * this.individuals[0].getChromosomeLength() * 0.01;
        // System.out.println("Anzahl Mutationen: " + anzahlMutationen);
        for (int i = 0; i < anzahlMutationen; i++) {
            int individuum = (int) (Math.random() * this.individuals.length);
            int gen = (int) (Math.random() * this.individuals[0].getChromosomeLength());
            if (this.individuals[individuum].getGene(gen) == 0) {
                this.individuals[individuum].setGene(gen, 1);
            } else {
                this.individuals[individuum].setGene(gen, 0);
            }
        }
    }

    public void crossover() {
        double anzahlCrossover = this.individuals.length * 0.25;
        // Paarweise auswählen
        for (int i = 0; i < anzahlCrossover; i = i + 2) {
            if (i == anzahlCrossover - 1) {
                // 50% Chance, dass das letzte HPModell auch noch ein Crossover bekommt
                if (Math.random() < 0.5)
                    break;
            }
            int individuum1 = (int) (Math.random() * this.individuals.length);
            int individuum2 = (int) (Math.random() * this.individuals.length);
            int gen = (int) (Math.random() * this.individuals[0].getChromosomeLength());
            int[] chromosome1 = this.individuals[individuum1].getChromosome();
            int[] chromosome2 = this.individuals[individuum2].getChromosome();
            int[] newChromosome1 = new int[chromosome1.length];
            int[] newChromosome2 = new int[chromosome2.length];
            for (int j = 0; j < chromosome1.length; j++) {
                if (j < gen) {
                    newChromosome1[j] = chromosome1[j];
                    newChromosome2[j] = chromosome2[j];
                } else {
                    newChromosome1[j] = chromosome2[j];
                    newChromosome2[j] = chromosome1[j];
                }
            }
            this.individuals[individuum1] = new Individual(newChromosome1);
            this.individuals[individuum2] = new Individual(newChromosome2);
        }

    }

}
