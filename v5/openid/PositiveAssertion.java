/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ontopad.openid;

/**
 *
 * @author admin
 */
public class PositiveAssertion {

    String ns;
//As specified in Section 4.1.2.
    String mode;
//Value: "id_res"
    String op_endpoint;
//The OP Endpoint URL.
    String claimed_id;
//Value: (optional) The Claimed Identifier. "openid.claimed_id" and "openid.identity" SHALL be either both present or both absent.
//Note: The end user MAY choose to use an OP-Local Identifier as a Claimed Identifier.
//Note: If neither Identifier is present in the assertion, it is not about an identifier, and will contain other information in its payload, using extensions.
    String identity;
//Value: (optional) The OP-Local Identifier
//Note: OpenID Providers MAY assist the end user in selecting the Claimed and OP-Local Identifiers about which the assertion is made. The openid.identity field MAY be omitted if an extension is in use that makes the response meaningful without it (see openid.claimed_id above).
    String return_to;
//Value: Verbatim copy of the return_to URL parameter sent in the request.
    String response_nonce;
//Value: A string 255 characters or less in length, that MUST be unique to this particular successful authentication response. The nonce MUST start with the current time on the server, and MAY contain additional ASCII characters in the range 33-126 inclusive (printable non-whitespace characters), as necessary to make each response unique. The date and time MUST be formatted as specified in section 5.6 of [RFC3339], with the following restrictions:
//All times must be in the UTC timezone, indicated with a "Z".
//No fractional seconds are allowed
//For example: 2005-05-15T17:11:51ZUNIQUE
//String invalidate_handle
//Value: (optional) If the Relying Party sent an invalid association handle with the request, it SHOULD be included here.
    String assoc_handle;
//Value: The handle for the association that was used to sign this assertion.
    String signed;
//Value: Comma-separated list of signed fields.
//Note: This entry consists of the fields without the "openid." prefix that the signature covers. This list MUST contain at least "op_endpoint", "return_to" "response_nonce" and "assoc_handle", and if present in the response, "claimed_id" and "identity". Additional keys MAY be signed as part of the message. See Generating Signatures.
//For example, "op_endpoint,identity,claimed_id,return_to,assoc_handle,response_nonce".
    String sig;
//Value: Base 64 encoded signature calculated as specified in Section 6.
}
