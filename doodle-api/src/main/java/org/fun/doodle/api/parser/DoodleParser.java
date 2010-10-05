package org.fun.doodle.api.parser;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.fun.doodle.api.model.Poll;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Made for pure fun.
 * Date: $Date$
 * Author: $Author$
 * Revision: $Revision$
 */
public class DoodleParser {

  protected Logger mLogger = Logger.getLogger(getClass());

  public Poll parsePoll(final String pContent) throws IOException {
    Document pollDoc = createDocument(pContent);
    return getPoll(pollDoc);
  }

  private Poll getPoll(final Document pDoc) {
    return new Poll();
  }

  private Document createDocument(final String pContent) throws IOException {
    Document doc = null;
    Builder builder = new Builder();
    final Reader contentReader = new StringReader(pContent);
    try {
      doc = builder.build(contentReader);
    } catch (ParsingException e) {
      mLogger.debug("can't parse content: " + pContent, e);
      throw new IOException("unable to parse content", e);
    } finally {
      IOUtils.closeQuietly(contentReader);
    }
    return doc;
  }
}
