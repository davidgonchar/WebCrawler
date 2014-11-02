package utils.webcrawler;

/**
 * Created by david on 02/11/14.
 */

public class MutableInt {
    int value; // note that we start at 1 since we're counting

    public MutableInt(int initValue) {
        this.value = initValue;
    }

    public int incrementAndGet() {
        ++value;
        return get();
    }
    public int get() {
        return value;
    }
}
