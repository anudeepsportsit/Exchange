package com.bettorlogic.victoryexch.middlelayer.jbetfairng.dao;


import com.bettorlogic.victoryexch.middlelayer.jbetfairng.dto.*;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.enums.*;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.utils.AppKeyAndSession;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.utils.BetfairServerResponse;
import com.bettorlogic.victoryexch.middlelayer.jbetfairng.utils.Network;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static com.bettorlogic.victoryexch.middlelayer.jbetfairng.utils.BetFairConstants.*;

@Repository
@Component
public class BetfairClient {

    /**
     * Static defined identity endpoints
     */
    private static HashMap<Exchange, String> identityEndpoints = new HashMap<>();

    static {
        identityEndpoints.put(Exchange.UK, "https://identitysso.betfair.com/api/login");
    }

    private final Duration sessionExpireTime = Duration.ofHours(3);
    private Exchange exchange = Exchange.UK;
    private Network networkClient;
    private String appKey;
    private String sessionToken;
    private long lastConnectTime;
    private BufferedReader reader;
    private BufferedWriter writer;
    private java.net.Socket client;
    private AppKeyAndSession session;
    @Autowired
    private ObjectMapper mapper;

    public SessionDetailsTO connectSocket() {
        lastConnectTime = System.currentTimeMillis();

        try {
            SessionDetailsTO appKeyAndSession = getOrCreateNewSession();
            client = createSocket(hostName, port);
            client.setReceiveBufferSize(1024 * 1000 * 2); //shaves about 20s off firehose image.
            client.setSoTimeout((int) timeout);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            return appKeyAndSession;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private java.net.Socket createSocket(String hostName, int port) throws IOException {
        if (port == 443) {
            SocketFactory factory = SSLSocketFactory.getDefault();
            SSLSocket newSocket = (SSLSocket) factory.createSocket(hostName, port);
            newSocket.startHandshake();
            return newSocket;
        } else {
            return new java.net.Socket(hostName, port);
        }
    }


    public SessionDetailsTO getOrCreateNewSession() {
        if (session != null) {
            if ((session.getCreateTime().plus(sessionExpireTime)).isAfter(Instant.now(Clock.systemUTC()))) {
                System.out.println("SSO Login - session not expired - re-using");
            } else {
                System.out.println("SSO Login - session expired");
            }
        }
        SessionDetailsTO sessionDetails = null;
        try {
            String uri = String.format("https://%s/api/login?username=%s&password=%s",
                    hostName,
                    URLEncoder.encode(username, StandardCharsets.UTF_8.name()),
                    URLEncoder.encode(password, StandardCharsets.UTF_8.name()));

            Client client = Client.create();
            client.setConnectTimeout((int) (timeout * 1000));
            WebResource webResource = client.resource(uri);

            ClientResponse clientResponse = webResource
                    .accept("application/json")
                    .header("X-Application", appKey)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .post(ClientResponse.class);

            mapper = new ObjectMapper();
            sessionDetails = mapper.readValue(clientResponse.getEntityInputStream(), SessionDetailsTO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sessionDetails != null && "SUCCESS".equals(sessionDetails.getStatus())) {
            session = new AppKeyAndSession(appKey, sessionDetails.getToken());
        } else {
            System.out.println("SSO Authentication - response is fail: " + sessionDetails.getError());
        }
        return sessionDetails;
    }


    public void login(
            String appKey,
            String token) {
        FileInputStream keyStream = null;
        try {
            this.sessionToken = appKey;
            this.networkClient = new Network(appKey, token, false);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                keyStream.close();
            } catch (Exception ignore) {
            }
        }
    }


    public BetfairServerResponse<List<MarketBookTO>> listMarketBook(
            List<String> marketIds,
            PriceProjectionTO priceProjection,
            OrderProjection orderProjection,
            MatchProjection matchProjection) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(MARKET_IDS, marketIds);
        args.put(PRICE_PROJECTION, priceProjection);
        args.put(ORDER_PROJECTION, orderProjection);
        args.put(MATCH_PROJECTION, matchProjection);
        return networkClient.Invoke(
                new TypeToken<List<MarketBookTO>>() {
                },
                this.exchange,
                Endpoint.Betting,
                LIST_MARKET_BOOK_METHOD,
                args);
    }

    public BetfairServerResponse<List<MarketCatalogueTO>> listMarketCatalogue(
            MarketFilterTO marketFilter,
            Set<MarketProjection> marketProjections,
            MarketSort sort,
            int maxResult) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(FILTER, marketFilter);
        args.put(MARKET_PROJECTION, marketProjections);
        args.put(SORT, sort);
        args.put(MAX_RESULTS, maxResult);
        return networkClient.Invoke(
                new TypeToken<List<MarketCatalogueTO>>() {
                },
                this.exchange,
                Endpoint.Betting,
                LIST_MARKET_CATALOGUE_METHOD,
                args);
    }

    public BetfairServerResponse<CurrentOrderSummaryReportTO> listCurrentOrders(
            Set<String> betIds) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(BET_IDS, betIds);
        return networkClient.Invoke(
                new TypeToken<CurrentOrderSummaryReportTO>() {
                },
                this.exchange,
                Endpoint.Betting,
                LIST_CURRENT_ORDERS_METHOD,
                args);
    }

    public BetfairServerResponse<ClearedOrderSummaryReportTO> listClearedOrders(
            Set<String> betIds) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(BET_STATUS, "SETTLED");
        args.put(BET_IDS, betIds);
        return networkClient.Invoke(
                new TypeToken<ClearedOrderSummaryReportTO>() {
                },
                this.exchange,
                Endpoint.Betting,
                LIST_CLEARED_ORDERS_METHOD,
                args);
    }


    public BetfairServerResponse<PlaceExecutionReportTO> placeOrders(
            String marketId,
            List<PlaceInstructionTO> placeInstructions
            /*String customerRef, long marketVersion*/) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(MARKET_ID, marketId);
        args.put(INSTRUCTIONS, placeInstructions);
        /*args.put(CUSTOMER_REFERENCE, customerRef);
        args.put(MARKET_VERSION, new MarketVersionTO(marketVersion));*/

        return networkClient.Invoke(
                new TypeToken<PlaceExecutionReportTO>() {
                },
                this.exchange,
                Endpoint.Betting,
                PLACE_ORDERS_METHOD,
                args);
    }


    public BetfairServerResponse<CancelExecutionReportTO> cancelOrders(
            String marketId,
            List<CancelInstructionTO> instructions
            /*String customerRef*/) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(MARKET_ID, marketId);
        args.put(INSTRUCTIONS, instructions);
        //args.put(CUSTOMER_REFERENCE, customerRef);
        return networkClient.Invoke(
                new TypeToken<CancelExecutionReportTO>() {
                },
                this.exchange,
                Endpoint.Betting,
                CANCEL_ORDERS_METHOD,
                args);
    }

    public BetfairServerResponse<ReplaceExecutionReportTO> replaceOrders(
            String marketId,
            List<ReplaceInstructionTO> instructions
            /*String customerRef*/) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(MARKET_ID, marketId);
        args.put(INSTRUCTIONS, instructions);
        //args.put(CUSTOMER_REFERENCE, customerRef);

        return networkClient.Invoke(
                new TypeToken<ReplaceExecutionReportTO>() {
                },
                this.exchange,
                Endpoint.Betting,
                REPLACE_ORDERS_METHOD,
                args);
    }

    public BetfairServerResponse<UpdateExecutionReportTO> updateOrders(
            String marketId,
            List<UpdateInstructionTO> instructions,
            String customerRef) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(MARKET_ID, marketId);
        args.put(INSTRUCTIONS, instructions);
        args.put(CUSTOMER_REFERENCE, customerRef);

        return networkClient.Invoke(
                new TypeToken<UpdateExecutionReportTO>() {
                },
                this.exchange,
                Endpoint.Betting,
                UPDATE_ORDERS_METHOD,
                args);
    }

    // Account API's
    public BetfairServerResponse<AccountDetailsResponseTO> getAccountDetails() {
        HashMap<String, Object> args = new HashMap<>();
        return networkClient.Invoke(
                new TypeToken<AccountDetailsResponseTO>() {
                },
                this.exchange,
                Endpoint.Account,
                GET_ACCOUNT_DETAILS,
                args);
    }


    public BetfairServerResponse<List<CurrencyRateTO>> listCurrencyRates(String fromCurrency) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(FROM_CURRENCY, fromCurrency);
        return networkClient.Invoke(
                new TypeToken<List<CurrencyRateTO>>() {
                },
                this.exchange,
                Endpoint.Account,
                LIST_CURRENCY_RATES,
                args);
    }


}
