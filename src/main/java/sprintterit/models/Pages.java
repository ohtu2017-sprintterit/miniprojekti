package sprintterit.models;

public class Pages {

    private final int begin;
    private final int end;

    public Pages(int begin, int end) {
        if (begin < 1 || begin > end) {
            throw new IllegalArgumentException("Illegal page interval " + begin + "--" + end);
        }

        this.begin = begin;
        this.end = end;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return begin + "--" + end;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pages)) {
            return false;
        }

        Pages p = (Pages) obj;
        return begin == p.begin && end == p.end;
    }

}
