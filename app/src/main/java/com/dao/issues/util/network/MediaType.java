package com.dao.issues.util.network;

/**
 * Created in 26/03/19 22:38.
 *
 * @author Diogo Oliveira.
 */
public class MediaType
{
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final String APPLICATION_JSON = "application/json";

    public static final okhttp3.MediaType MULTIPART_FORM_DATA_TYPE = parse(MULTIPART_FORM_DATA);

    public static okhttp3.MediaType parse(String type)
    {
        return okhttp3.MediaType.parse(type);
    }
}
