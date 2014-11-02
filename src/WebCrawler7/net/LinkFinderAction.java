package WebCrawler7.net;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import utils.webcrawler.LinkHandler;

/**
 * Created by david on 02/11/14.
 */
public class LinkFinderAction extends RecursiveAction {
    private String url;
    private LinkHandler cr;
    /**
     * Used for statistics
     */
    private static final long t0 = System.nanoTime();

    public LinkFinderAction(String url, LinkHandler cr) {
        this.url = url;
        this.cr = cr;
    }

    @Override
    public void compute() {
        if (!cr.visited(url)) {
            try {
                List<RecursiveAction> actions = new ArrayList<RecursiveAction>();
                URL uriLink = new URL(url);
                Parser parser = new Parser(uriLink.openConnection());
                NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));

                for (int i = 0; i < list.size(); i++) {
                    LinkTag extracted = (LinkTag) list.elementAt(i);

                    if (!extracted.extractLink().isEmpty()
                            && !cr.visited(extracted.extractLink())) {

                        actions.add(new LinkFinderAction(extracted.extractLink(), cr));
                    }
                }
                cr.addVisited(url);

                if (cr.size() % 1500 == 0) {
                    System.out.println("Time for visit 1500 distinct links= " +
                            TimeUnit.SECONDS.convert(System.nanoTime() - t0, TimeUnit.NANOSECONDS));
                }

                //invoke recursively
                invokeAll(actions);
            } catch (Exception e) {
                //ignore 404, unknown protocol or other server errors
            }
        }
    }
}
