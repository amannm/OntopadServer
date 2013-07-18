/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.oauth.facebook;

import com.ontopad.oauth.authcode.AuthorizationResponse;
import com.ontopad.oauth.authcode.TokenRequest;
import com.ontopad.oauth.authcode.TokenResponse;
import java.net.URLEncoder;
import /**
         *
         * @author admin
         */





import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.EnumMap;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

public class FacebookOAuthService {

    public static final String client_id = "asdf";
    public static final String client_secret = "asdfasdf";
    public static final String redirect_uri = "http%3A%2F%2Fwww.ontopad.com%2Fauthorization%2Fnew";
    public static final String encodedCredentials = "f03k94fk02394f20394fk";
   //if new unauthorized user...
    public static final String authRequestString =
            "HTTP/1.1 303 Redirect \r\n"
            + "Location: https://www.facebook.com/dialog/oauth?client_id=" + client_id 
            + "&redirect_uri=" + redirect_uri
            + "&state=SOME_ARBITRARY_BUT_UNIQUE_STRING\r\n"
            + "&scope="
            + "\r\n";
    //Process request at redirect endpoint
    //extract authorization code, post access token request with it
    public static final String tokenRequestString =
            "POST /oauth/access_token HTTP/1.1\r\n"
            + "Host: graph.facebook.com\r\n"
            + "Authorization: Basic " + encodedCredentials + "\r\n"
            + "Content-Type: application/x-www-form-urlencoded\r\n"
            + "\r\n"
            + "grant_type=authorization_code"
            + "&redirect_uri=" + redirect_uri
            + "&code=";
    
    //process response and extract access token
    // access_token=USER_ACCESS_TOKEN&expires=NUMBER_OF_SECONDS_UNTIL_TOKEN_EXPIRES
    //save to a database or something
    //
    public static final String accessRequestString =
            "POST /me HTTP/1.1\r\n"
            + "Host: graph.facebook.com\r\n"
            + "Authorization: Bearer " + encodedCredentials + "\r\n"
            + "Content-Type: application/x-www-form-urlencoded\r\n"
            + "\r\n"
            + "grant_type=authorization_code"
            + "&redirect_uri=" + redirect_uri
            + "&code=";

    
    /*
     https://graph.facebook.com/oauth/access_token?"
     . "client_id=" . $app_id . "&redirect_uri=" . urlencode($my_url)
     . "&client_secret=" . $app_secret . "&code=" . $code;
     */
    public TokenRequest handleAuthorizationResponse(AuthorizationResponse response) {
        String mess = message + response.getCode();
        TokenRequest request = new TokenRequest(, redirect_uri);
        //Dont enter client_id and secret, use authorization header
        //also get state but whatever

        return request;
    }

    public void handleTokenResponse(TokenResponse response) {
        response.getAccessToken();
    }

    public static void sendRequest(String messaa) {
        try (final AsynchronousSocketChannel asynchronousSocketChannel = AsynchronousSocketChannel.open()) {
            if (asynchronousSocketChannel.isOpen()) {
                Void connect = asynchronousSocketChannel.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT)).get(3, TimeUnit.SECONDS);
                if (connect == null) {
                    ByteBuffer receivingBuffer = ByteBuffer.allocateDirect(4096);
                    asynchronousSocketChannel.write(ByteBuffer.wrap(messaa.getBytes("UTF-8"))).get(3, TimeUnit.SECONDS);
                    asynchronousSocketChannel.read(receivingBuffer).get(3, TimeUnit.SECONDS);
                    receivingBuffer.flip();
                    String msgReceived = Charset.defaultCharset().decode(receivingBuffer).toString();
                    System.out.print(msgReceived);

                } else {
                    System.out.println("The connection cannot be established!");
                }
            } else {
                System.out.println("The asynchronous socket channel cannot be opened!");
            }
        } catch (IOException | InterruptedException | ExecutionException | TimeoutException ex) {
            Logger.getLogger(Util2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
