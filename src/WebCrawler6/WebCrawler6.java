package WebCrawler6;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import utils.webcrawler.LinkHandler;
import utils.webcrawler.MutableInt;
import WebCrawler6.net.LinkFinder;

/**
 * Created by david on 02/11/14.
 */
public class WebCrawler6 implements LinkHandler {
    private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<String>());
//    private final ConcurrentMap<String, MutableInt> visitedLinks = new ConcurrentHashMap<String, MutableInt>();

    private String url;
    private ExecutorService execService;

    public WebCrawler6(String startingURL, int maxThreads) {
        this.url = startingURL;
        execService = Executors.newFixedThreadPool(maxThreads);
    }

    @Override
    public void queueLink(String link) throws Exception {

        startNewThread(link);
    }

    @Override
    public int size() {
        return visitedLinks.size();
    }

    @Override
    public void addVisited(String s) {
        visitedLinks.add(s);
//        visitedLinks.putIfAbsent(s, new MutableInt(0));
//        visitedLinks.get(s).incrementAndGet();
    }

    @Override
    public boolean visited(String s) {
        return false;
//        return visitedLinks.containsKey(s);
    }

    private void startNewThread(String link) throws Exception {
        execService.execute(new LinkFinder(link, this));
    }

    private void startCrawling() throws Exception {
        startNewThread(this.url);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        new WebCrawler6("http://www.javaworld.com", 64).startCrawling();
    }
}

