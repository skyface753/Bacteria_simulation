/**
 * Bacteria
 */
public class Bacteria {
    private int energy = 100;
    private boolean[] code = new boolean[10];
    private int generation = 0;
    
    public Bacteria() {
        for (int i = 0; i < code.length; i++) {
            code[i] = Math.random() < 0.5;
        }
    }

    public Bacteria(boolean[] code, int generation) {
        this.code = code;
        this.generation = generation;   
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < code.length; i++) {
            s += code[i] ? "1" : "0";
        }
        return s + ": " + energy + ": " + generation;
    }
}