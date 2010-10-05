package org.fun.doodle.api;

import org.fun.doodle.api.model.Poll;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Made for pure fun.
 * Date: $Date$
 * Author: $Author$
 * Revision: $Revision$
 */
public class DoodleManagerTest {

  public DoodleManager createDoodleManager() {
    return new DoodleManager("9ss9pxuq2nd35rxf9us1dqotsiaqtysj",
                             "cb2ho6udxhr8jd2vqmut81k211xlzszt",
                             "pahzyxigxuezmqh4");
  }

  @Test
  public void getSimplePollTest() throws Exception {
    DoodleManager doodleManager = createDoodleManager();
    Poll poll = doodleManager.getPoll();
    assertNotNull(poll);
  }
}
