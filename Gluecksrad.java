
import java.util.ArrayList;
import java.util.List;

public class Gluecksrad {
    // Glücksrad mit unterschiedlichen Wahrscheinlichkeiten
    List<Integer> wahrscheinlicheiten = new ArrayList<>();

    public Gluecksrad() {
        for (int i = 0; i < 10; i++) {
            wahrscheinlicheiten.add(0);
        }
        for (int i = 0; i < 30; i++) {
            wahrscheinlicheiten.add(1);
        }
        for (int i = 0; i < 30; i++) {
            wahrscheinlicheiten.add(2);
        }
        for (int i = 0; i < 40; i++) {
            wahrscheinlicheiten.add(3);
        }
    }

    public int get() {
        return wahrscheinlicheiten.get((int) (Math.random() * wahrscheinlicheiten.size()));
    }

    public static void main(String[] args) {
        Gluecksrad g = new Gluecksrad();
        for (int i = 0; i < 100; i++) {
            System.out.println(g.get());
        }
    }

}
