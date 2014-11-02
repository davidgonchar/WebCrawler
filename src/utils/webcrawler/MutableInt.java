package utils.webcrawler;

/**
 * Created by david on 02/11/14.
 */

public class MutableInt {
    int value = 1; // note that we start at 1 since we're counting
    public void increment () { ++value;      }
    public int  get ()       { return value; }
}
