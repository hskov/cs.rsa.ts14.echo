package cs.rsa.ts14.foxtrot;

// inspired by http://stackoverflow.com/a/3646398/198348

public class Pair<TL, TR> {

    private TL left;
    private TR right;

    public Pair(TL left, TR right) {
        this.left = left;
        this.right = right;
    }

    public static <TL, TR> Pair<TL, TR> of(TL left,TR right) {
        return new Pair<>(left, right);
    }

    public TL getLeft() {
        return left;
    }

    public TR getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (!left.equals(pair.left)) return false;
        if (!right.equals(pair.right)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = left.hashCode();
        result = 31 * result + right.hashCode();
        return result;
    }
}
