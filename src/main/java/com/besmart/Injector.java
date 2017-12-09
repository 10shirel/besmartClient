package com.besmart;

import com.besmart.model.enums.State;
import com.besmart.model.pojo.Triangle;
import com.besmart.utils.Configurations;
import com.besmart.utils.Constants;
import com.besmart.utils.initialize.ClientManager;
import com.google.gson.Gson;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by shirela on 12/6/2017.
 */
@Service
@EnableScheduling
public class Injector {
    private static final Logger LOGGER = LoggerFactory.getLogger(Injector.class);

    Gson gson = new Gson();


    Random random;

    @Autowired
    Configurations Configurations;

    /**
     * Send 2 random edges of triangle via rest
     */
    @Scheduled(fixedDelayString = "${fixed.delay.in.milliseconds}")
    public List<String> sendRandEdges() {
        List<String> idEdgeList = new ArrayList<>();
        idEdgeList.addAll(getRandEge());
        idEdgeList.add(getRandId());


        // Build post request
        Triangle triangle = new Triangle(idEdgeList.get(2), idEdgeList.get(0), idEdgeList.get(1), null, State.PRECALC);
        String postJsonData = gson.toJson(triangle);

        //post
        HttpPost post = new HttpPost(Constants.ADD_TRIANGLE);
        post.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        StringEntity entity = new StringEntity(postJsonData, StandardCharsets.UTF_8);
        post.setEntity(entity);

        //get Instance of Client for performance
        HttpClient client = ClientManager.getInstance().getClient();

        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                LOGGER.error(new StringBuilder().append(new Date()).
                        append(Constants.INVALID_RESPONSE_THE_RESPONSE_IS).
                        append(httpResponse.getStatusLine().getStatusCode()).
                        toString());
            } else {
                String response = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(httpResponse.getStatusLine().getStatusCode() + ": " + response);

                LOGGER.debug(new StringBuilder().append(new Date()).
                        append(Constants.THE_RESPONSE_IS).
                        append(response).
                        toString());
            }

        }catch (HttpHostConnectException e){
            LOGGER.error(new StringBuilder().append(new Date()).
                    append(Constants.SOMETHING_IS_WRONG_CHECK_THAT_THE_SERVER_IS_UP_AND_TRY_LATER).
                    append("\n").
                    append(e).
                    toString());
        }
        catch (IOException e) {
            LOGGER.error(new StringBuilder().append(new Date()).
                    append(Constants.SOMETHING_IS_WRONG_PLEASE_TRY_LATER).
                    append("\n").
                    append(e).
                    toString());
        } finally {
            EntityUtils.consumeQuietly(httpResponse.getEntity());
        }


        return idEdgeList;

    }

    /**
     * generate random edge based on config file
     *
     * @return
     */
    private List<String> getRandEge() {
        random = new Random();
        int minEdge = Configurations.getRangeOfTriangleEdgMin();
        int maxEdge = Configurations.getRangeOfTriangleEdgMax();
        return Arrays.asList(String.valueOf(random.nextInt(maxEdge + 1 - minEdge) + minEdge),
                String.valueOf(random.nextInt(maxEdge + 1 - minEdge) + minEdge));
    }

    private String getRandId() {
        random = new Random();
        return String.valueOf(random.nextInt(99999 + 1 - 10000) + 10000);
    }
}