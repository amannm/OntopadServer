/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.authorization;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import com.ontopad.identity.Identity;

/**
 *
 * @author admin
 */
@ApplicationScoped
public class AuthorizationService implements Serializable {

    @Inject
    private ServletPolicyData servletPolicyData;

    @PostConstruct
    public void init() {
        //Group "Everyone" (0L) is allowed to GET the site root "/"
        ServletPolicy ap = new ServletPolicy();
        ap.allowedGET.add(0L);
        servletPolicyData.put("/", ap);
        ap.allowedPOST.add(0L);
        servletPolicyData.put("/login", ap);
        servletPolicyData.put("/identity/registration", ap);
        servletPolicyData.put("/identity/activation", ap);
        
        ap = new ServletPolicy();
        ap.allowedGET.add(1L);
        ap.allowedPOST.add(1L);
        ap.allowedGET.add(2L);
        ap.allowedPOST.add(2L);
        servletPolicyData.put("/logout", ap);
        
    }

    public boolean authorize(Identity identity, String method, String location) {
        if (identity != null) {
            Set<Long> groups = identity.getGroupIDs();
            if (method != null) {
                HttpRequestMethod m = HttpRequestMethod.valueOf(method);
                if (location != null) {
                    ServletPolicy policy = servletPolicyData.get(location);
                    if (policy != null) {
                        switch (m) {
                            case GET:
                                return !Collections.disjoint(groups, policy.allowedGET);
                            case POST:
                                return !Collections.disjoint(groups, policy.allowedPOST);
                            case PUT:
                                return !Collections.disjoint(groups, policy.allowedPUT);
                            case DELETE:
                                return !Collections.disjoint(groups, policy.allowedDELETE);
                            case HEAD:
                                return !Collections.disjoint(groups, policy.allowedHEAD);
                            case OPTIONS:
                                return !Collections.disjoint(groups, policy.allowedOPTIONS);
                            case TRACE:
                                return !Collections.disjoint(groups, policy.allowedTRACE);
                            default:
                        }
                    }
                }
            }
        }
        return false;
    }
}
