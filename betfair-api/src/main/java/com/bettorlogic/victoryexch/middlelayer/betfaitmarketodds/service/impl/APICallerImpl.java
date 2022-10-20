package com.bettorlogic.victoryexch.middlelayer.betfaitmarketodds.service.impl;

import com.bettorlogic.victoryexch.middlelayer.betfaitmarketodds.dao.APICaller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class APICallerImpl implements APICaller {
    private static final Logger LOGGER = LogManager.getLogger(APICallerImpl.class);

    @Override
    public String processAPICallForFeed(String url, String requestAsJson, String applicationKey, String authenticationKey) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("X-Application", applicationKey);
            con.setRequestProperty("X-Authentication", authenticationKey);
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(requestAsJson);
            wr.flush();
            wr.close();
            // Read the response
            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                throw new Exception("Http response code: " + responseCode);
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            return response.toString();

        } catch (Exception e) {
            LOGGER.error("Error in processing countries");
            return null;
        }
    }
}
