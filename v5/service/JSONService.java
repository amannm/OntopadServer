/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.service;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.json.stream.JsonParsingException;

/**
 *
 * @author admin
 */
@RequestScoped
public class JSONService implements Serializable {

    private static final Logger log = Logger.getLogger(JSONService.class.getName());

    private String getNextValue(String key, Iterator<Event> it, JsonParser parser) {
        if (it.next() == Event.START_OBJECT) {
            if (it.next() == Event.KEY_NAME) {
                if (parser.getString().equals(key)) {
                    if (it.next() == Event.VALUE_STRING) {
                        return parser.getString();
                    }
                }
            }
        }
        return null;
    }

    public String[] parseRegistration(InputStream is) {
        String[] pair = new String[2];
        try (JsonParser parser = Json.createParser(is)) {
            Iterator<Event> it = parser.iterator();
            if (it.next() == Event.START_OBJECT) {
                pair[0] = getNextValue("emailAddress", it, parser);
                pair[1] = getNextValue("password", it, parser);
                if (it.next() != Event.END_OBJECT) {
                    throw new JsonParsingException();
                }
            }
        } catch (JsonException jpe) {
            log.log(Level.SEVERE, "Exception while parsing JSON registration object: {0}", jpe.getMessage());
        }
        return pair;
    }
}
