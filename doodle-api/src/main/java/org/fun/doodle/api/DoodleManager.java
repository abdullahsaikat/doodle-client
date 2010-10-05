package org.fun.doodle.api;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.fun.doodle.api.model.Poll;
import org.fun.doodle.api.parser.DoodleParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Made for pure fun.
 * Date: $Date$
 * Author: $Author$
 * Revision: $Revision$
 */
public class DoodleManager {

  public static final int CONTENT_SIZE_LIMIT = 32*1024*1024;
  
  private final String mAppKey;
  private final String mSecret;
  private final String mPollId;
  private URI mBaseURL;
  private HttpClient mHttpClient = new DefaultHttpClient();

  private DoodleParser mParser = new DoodleParser();

  public DoodleManager(final String pAppKey, final String pSecret, final String pPollId) {
    mAppKey = pAppKey;
    mSecret = pSecret;
    mPollId = pPollId;
    mBaseURL = URI.create("http://doodle.com/api1/");
  }

  public String getAppKey() {
    return mAppKey;
  }

  public String getSecret() {
    return mSecret;
  }

  public String getPollId() {
    return mPollId;
  }

  public URI getPollURL() {
    return URIUtils.resolve(mBaseURL, "polls/" + getPollId());
  }

  public Poll getPoll() throws IOException {
    return mParser.parsePoll(getContent(getPollURL()));
  }

  private String getContent(final URI pURI) throws IOException {
    HttpGet getOp = new HttpGet(pURI);
    HttpResponse response = mHttpClient.execute(getOp);
    if (response.getEntity() != null) {
      InputStream in = response.getEntity().getContent();
      String content =  readContent(in, response.getEntity().getContentLength(),
                                    response.getEntity().getContentEncoding().getValue());
      IOUtils.closeQuietly(in);
      return content;
    }
    return null;
  }

  private String readContent(final InputStream pIn, final long pLength,
                            final String pEncoding) throws IndexOutOfBoundsException, IOException {
    byte [] buffer;
    if (pLength > CONTENT_SIZE_LIMIT) {
      throw new IndexOutOfBoundsException("unable to allocate " + CONTENT_SIZE_LIMIT +
                                          " bytes of memory to retrieve content");
    }
    buffer = new byte[CONTENT_SIZE_LIMIT];
    int length = pIn.read(buffer);
    if (pLength != length) {
      throw new IOException("unable to retrieve full content");
    }
    String encoding = "iso-8859-1";
    if (pEncoding != null) {
      encoding = pEncoding;
    }
    return new String(buffer, encoding);
  }
}
