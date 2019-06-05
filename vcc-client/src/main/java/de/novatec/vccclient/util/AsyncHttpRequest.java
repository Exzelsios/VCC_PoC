package de.novatec.vccclient.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.http.client.fluent.Async;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AsyncHttpRequest {

    private final static Logger LOGGER = LoggerFactory.getLogger(AsyncHttpRequest.class);

    public void execute(String host, String path) throws Exception {

        URIBuilder urlBuilder = new URIBuilder()
                .setScheme("http")
                .setHost(host)
                .setPort(8080)
                .setPath(path);

        final int nThreads = 3; // no. of threads in the pool
        final int timeout = 0; // connection time out in milliseconds

        URI uri = null;
        try {
            uri = urlBuilder.build();
        } catch (URISyntaxException use) {
            use.printStackTrace();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        Async async = Async.newInstance().use(executorService);
        final Request request = Request.Get(uri).connectTimeout(timeout);

        Future<Content> future = async.execute(request, new FutureCallback<Content>() {
            public void failed(final Exception e) {
                LOGGER.error("Failed executing HttpRequest.", e);
            }

            public void completed(final Content content) {
            }

            public void cancelled() {
                LOGGER.warn("Cancelled HttpRequest.");
            }
        });
    }

}