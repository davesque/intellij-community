import java.util.*;

class Vector {
    private final List<Number> values;

    public Vector(int a, int b, Number... args) {
        values = new ArrayList<>(args.length + 1);
        values.add(null);
        <caret>for (int i = a + 2; i < 1 + b; ++i) {
            values.add(args[i - 1]);
        }
    }
}
