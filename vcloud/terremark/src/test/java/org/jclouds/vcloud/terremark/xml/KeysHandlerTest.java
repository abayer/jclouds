/**
 *
 * Copyright (C) 2010 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.jclouds.vcloud.terremark.xml;

import static org.testng.Assert.assertEquals;

import java.io.InputStream;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Set;

import org.jclouds.http.functions.BaseHandlerTest;
import org.jclouds.vcloud.terremark.domain.KeyPair;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;

/**
 * Tests behavior of {@code KeysHandler}
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "vcloud.KeysHandlerTest")
public class KeysHandlerTest extends BaseHandlerTest {

   public void test1() throws UnknownHostException {
      InputStream is = getClass()
            .getResourceAsStream("/terremark/keysList.xml");

      Set<KeyPair> result = factory.create(injector.getInstance(KeyPairsHandler.class))
            .parse(is);
      assertEquals(
            result,
            ImmutableSet
                  .of(new KeyPair(
                        9,
                        URI
                              .create("https://services.vcloudexpress.terremark.com/api/v0.8a-ext1.6/extensions/key/9"),
                        "default", true, null,
                        "4e:af:8a:9f:e9:d2:72:d7:4b:a0:da:98:72:98:4d:7d")));

   }
}
