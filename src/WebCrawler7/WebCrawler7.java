package WebCrawler7;

import WebCrawler7.net.LinkFinderAction;
import utils.webcrawler.LinkHandler;
import utils.webcrawler.MutableInt;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by david on 02/11/14.
 */

public class WebCrawler7 implements LinkHandler {

//    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
//    private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<String>());
    private final ConcurrentMap<String, MutableInt> visitedLinks = new ConcurrentHashMap<String, MutableInt>();
//    private final ConcurrentMap<String, AtomicInteger> visitedLinks = new ConcurrentHashMap<String, AtomicInteger>();

    private String url;
    private ForkJoinPool mainPool;

    public WebCrawler7(String startingURL, int maxThreads) {
        this.url = startingURL;
        mainPool = new ForkJoinPool(maxThreads);
    }

    private void startCrawling() {
        mainPool.invoke(new LinkFinderAction(this.url, this));
    }

    @Override
    public void queueLink(String link) throws Exception {

    }

    @Override
    public int size() {
        return visitedLinks.size();
    }

    @Override
    public void addVisited(String s) {
        MutableInt count = visitedLinks.get(s);
        if (count == null) {
            visitedLinks.put(s, new MutableInt());
        } else {
            count.increment();
//            if (count.get() > 9) {
//                System.out.println("" + count.get() + " add visited: " + s);
//            }
        }
//        visitedLinks.putIfAbsent(s, new AtomicInteger(0));
//        visitedLinks.get(s).incrementAndGet();
    }

    @Override
    public boolean visited(String s) {
//        return visitedLinks.containsKey(s);
        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        new WebCrawler7("http://www.javaworld.com", 64).startCrawling();
    }
}
