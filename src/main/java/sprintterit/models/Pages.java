package sprintterit.models;

public class Pages {

    private final int begin;
    private final int end;

    public static Pages construct(Integer begin, Integer end) {
        if (begin == null || end == null) {
            return null;
        }

        return new Pages(begin, end);
    }

    public Pages(int begin, int end) {
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
