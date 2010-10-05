package org.fun.doodle.api.parser;

import org.apache.commons.io.IOUtils;
import org.fun.doodle.api.model.Poll;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.InputStream;

/**
 * Made for pure fun.
 * Date: $Date$
 * Author: $Author$
 * Revision: $Revision$
 */
public class DoodleParserTest {

  @Test
  public void pollParseTest() throws Exception {
    String pollStr = getContent("/org/fun/doodle/api/parser/poll.xml");
    System.out.println("poll: \n" + pollStr);
    DoodleParser parser = new DoodleParser();
    Poll poll = parser.parsePoll(pollStr);
    assertNotNull(poll);
    
  }

  private String getContent(String pPath) throws Exception {
    InputStream is = getClass().getResourceAsStream(pPath);
    String content = new String(IOUtils.toByteArray(is), "iso-8859-1");
    IOUtils.closeQuietly(is);
    return content;
  }
}
