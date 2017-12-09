package com.besmart.utils.initialize;

import com.besmart.utils.Constants;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


public class ClientManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientManager.class);

    private static ClientManager instance;
    HttpClient client;


    /**
     * Singleton constructor
     */
    private ClientManager() {

        try {
            this.client = HttpClientBuilder.create().build();


        } catch (Exception e) {
            LOGGER.error(new StringBuilder().append(new Date()).append(Constants.SOMETHING_IS_WRONG_PLEASE_TRY_LATER).append("\n").append(e).toString());
        }

    }

    public static synchronized ClientManager getInstance() {
        if (instance == null) {
            synchronized (ClientManager.class) {
                if (instance == null)
                    instance = new ClientManager();
                LOGGER.debug(new StringBuilder().append(new Date()).append(Constants.CLIENT_INSTANCE_WAS_INITIATED).toString());
            }
        }
        return instance;
    }


    public HttpClient getClient() {
        return client;
    }

}

