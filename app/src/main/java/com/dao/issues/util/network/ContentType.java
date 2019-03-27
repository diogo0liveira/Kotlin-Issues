package com.dao.issues.util.network;

/**
 * Created in 26/03/19 22:37.
 *
 * @author Diogo Oliveira.
 */
public interface ContentType
{
    String MULTIPART_FORM_DATA = "Content-Type: " + MediaType.MULTIPART_FORM_DATA;
    String APPLICATION_JSON = "Content-Type: " + MediaType.APPLICATION_JSON + ";charset=UTF-8";
}
