/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.identity;

import com.ontopad.exception.AuthenticationServiceException;
import com.ontopad.exception.LoginException;
import com.ontopad.service.EmailService;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;
import ontopad.data.Group;
import ontopad.data.GroupData;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author admin
 */
@ApplicationScoped
public class AuthenticationService implements Serializable {

    private static final Identity anonymous = new Identity();
    private static Long currentGroupID = 1000L;
    @Inject
    private AuthenticationData authenticationData;
    @Inject
    private IdentityData identities;
    @Inject
    private GroupData groups;
    @Inject
    private ConfirmationData confirmations;
    @Inject
    private EmailService emailService;

    @PostConstruct
    public void init() {
    }

    
    
    public String login(String email, String password) throws LoginException {
        if (password != null || password.equals("")) {
            AuthenticationInfo info = authenticationData.get(email);
            if (authenticationData != null) {
                byte[] hashPass;
                try {
                    hashPass = hashData(password.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    throw new LoginException("Invalid password encoding: " + ex.getMessage());
                }
                String encoded = Base64.encodeBase64String(hashPass);
                if (encoded.equals(info.getPassword())) {
                    return info.getToken();
                } else {
                    throw new LoginException("Invalid password provided");
                }
            } else {
                throw new LoginException("Invalid email provided");
            }
        } else {
            throw new LoginException("Null password provided");
        }
    }

    public Identity authenticate(String token) {
        if (token != null) {
            Identity identity = identities.get(token);
            if (identity != null) {
                return identity;
            }
        }
        return anonymous;
    }

    public void register(String email, String password) throws AuthenticationServiceException {
        if (authenticationData.get(email) == null) {
            //probably should check password validity or something
            //email to token+password
            AuthenticationInfo authInfo = new AuthenticationInfo();
            String token = UUID.randomUUID().toString();
            authInfo.setToken(token);
            try {
                byte[] hashPass = hashData(password.getBytes("UTF-8"));
                String encoded = Base64.encodeBase64String(hashPass);
                authInfo.setPassword(encoded);
            } catch (UnsupportedEncodingException ex) {
                throw new AuthenticationServiceException("Invalid password encoding: " + ex.getMessage());
            }

            if (authenticationData.put(email, authInfo)) {
                Group group = new Group();
                group.setName(email);
                Long id = currentGroupID++;
                Set<Long> groupids = group.getSuperGroups();
                groupids.add(id); //Self
                groupids.add(0L); //Everyone
                groupids.add(1L); //Unactivated, registered members
                group.setSuperGroups(groupids);
                if (groups.put(id, group)) {
                    Identity identity = new Identity();
                    identity.setNominalGroupID(id);
                    identity.setGroupIDs(groupids);
                    if (identities.put(token, identity)) {
                        Confirmation act = new Confirmation();
                        act.setGroupID(id);
                        String code = UUID.randomUUID().toString().replaceAll("-", "");
                        identity.setConfirmationCode(code);
                        if (confirmations.put(code, act)) {
                            try {
                                emailService.send(email, "Ontopad Registration", "Hey click here <a href=\"http://ontopad.com/activation/" + code + "\"/>");
                            } catch (MessagingException ex) {
                                confirmations.delete(code);
                                identities.delete(token);
                                groups.delete(id);
                                authenticationData.delete(email);
                                throw new AuthenticationServiceException("Failed to send confirmation email: " + ex.getMessage());
                            }
                        } else {
                            identities.delete(token);
                            groups.delete(id);
                            authenticationData.delete(email);
                            throw new AuthenticationServiceException("Failed to PUT new confirmation");
                        }
                    } else {
                        groups.delete(id);
                        authenticationData.delete(email);
                        throw new AuthenticationServiceException("Failed to PUT new session");
                    }
                } else {
                    authenticationData.delete(email);
                    throw new AuthenticationServiceException("Failed to PUT new group");
                }
            } else {
                throw new AuthenticationServiceException("Failed to PUT new authentication");
            }
        } else {
            throw new AuthenticationServiceException("Email address is already registered");
        }
    }

    public void activate(String code) throws AuthenticationServiceException {
        Confirmation confirmation = confirmations.get(code);
        if (confirmation != null) {
            Long groupID = confirmation.getGroupID();
            Group group = groups.get(groupID);
            if (group != null) {
                Set<Long> groupids = group.getSuperGroups();
                groupids.remove(1L); //Unactivated, registered members
                groupids.add(2L); //Activated, registered members
                group.setSuperGroups(groupids);
                if (groups.put(groupID, group)) {
                    confirmations.delete(code);
                } else {
                    throw new AuthenticationServiceException("Failed to PUT group with updated membership");
                }
            } else {
                throw new AuthenticationServiceException("Confirmation references an invalid group id: " + groupID);
            }
        } else {
            throw new AuthenticationServiceException("Invalid confirmation code specified: " + code);
        }
    }

    private static byte[] hashData(byte[] data) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
        synchronized (md) {
            md.reset();
            data = md.digest(data);
        }
        return data;
    }
}
