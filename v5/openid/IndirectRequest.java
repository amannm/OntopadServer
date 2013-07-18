/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid;

/**
 *
 * @author admin
 */
public class IndirectRequest {

    String ns;
//Node: As specified in Section 4.1.2.
    String mode;
//Value: "checkid_immediate" or "checkid_setup"
//Note: If the Relying Party wishes the end user to be able to interact with the OP, "checkid_setup" should be used. An example of a situation where interaction between the end user and the OP is not desired is when the authentication request is happening asynchronously in JavaScript.
    String claimed_id;
//Value: (optional) The Claimed Identifier.
//Note: "openid.claimed_id" and "openid.identity" SHALL be either both present or both absent. If neither value is present, the assertion is not about an identifier, and will contain other information in its payload, using extensions.
//Note: It is RECOMMENDED that OPs accept XRI identifiers with or without the "xri://" prefix, as specified in the Normalization section.
    String identity;
//Value: (optional) The OP-Local Identifier.
//Node: If a different OP-Local Identifier is not specified, the claimed identifier MUST be used as the value for openid.identity.
//Note: If this is set to the special value "http://specs.openid.net/auth/2.0/identifier_select" then the OP SHOULD choose an Identifier that belongs to the end user. This parameter MAY be omitted if the request is not about an identifier (for instance if an extension is in use that makes the request meaningful without it; see openid.claimed_id above).
    String assoc_handle;
//Value: (optional) A handle for an association between the Relying Party and the OP that SHOULD be used to sign the response.
//Note: If no association handle is sent, the transaction will take place in Stateless Mode.
    String return_to;
//Value: (optional) URL to which the OP SHOULD return the User-Agent with the response indicating the status of the request.
//Note: If this value is not sent in the request it signifies that the Relying Party does not wish for the end user to be returned.
//Note: The return_to URL MAY be used as a mechanism for the Relying Party to attach context about the authentication request to the authentication response. This document does not define a mechanism by which the RP can ensure that query parameters are not modified by outside parties; such a mechanism can be defined by the RP itself.
    String realm;
//Value: (optional) URL pattern the OP SHOULD ask the end user to trust. See Section 9.2. This value MUST be sent if openid.return_to is omitted.
//Default: return_to URL
}
