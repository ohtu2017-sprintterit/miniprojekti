package sprintterit.models;

public class Pages {

    private final int begin;
    private final int end;

    public Pages(int begin, int end) {
        if (begin > end) {
            throw new IllegalArgumentException("Illegal page interval " + begin + "--" + end);
        }
        if (begin < 1) {
            this.begin = 0;
            this.end = 0;
        } else {
            this.begin = begin;
            this.end = end;
        }
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
