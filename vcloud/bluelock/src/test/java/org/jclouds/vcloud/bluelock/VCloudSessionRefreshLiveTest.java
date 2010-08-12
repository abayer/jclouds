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

package org.jclouds.vcloud.bluelock;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.Constants.PROPERTY_SESSION_INTERVAL;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.logging.log4j.config.Log4JLoggingModule;
import org.jclouds.vcloud.VCloudClient;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

/**
 * Tests session refresh works
 * 
 * @author Adrian Cole
 */
@Test(groups = "live", sequential = true, testName = "vcloud.VCloudSessionRefreshLiveTest")
public class VCloudSessionRefreshLiveTest {

   private final static int timeOut = 40;
   protected VCloudClient connection;
   protected String identity;
   protected ComputeServiceContext context;

   @Test
   public void testSessionRefresh() throws Exception {
      connection.getDefaultOrganization();
      Thread.sleep(timeOut * 1000);
      connection.getDefaultOrganization();
   }

   @BeforeGroups(groups = { "live" })
   public void setupClient() throws IOException {
      identity = checkNotNull(System.getProperty("jclouds.test.identity"), "jclouds.test.identity");
      String credential = checkNotNull(System.getProperty("jclouds.test.credential"), "jclouds.test.credential");

      Properties props = new Properties();
      props.setProperty(PROPERTY_SESSION_INTERVAL, 40 + "");

      context = new ComputeServiceContextFactory().createContext("bluelock", identity, credential,
               ImmutableSet.<Module> of(new Log4JLoggingModule()), props);

      connection = VCloudClient.class.cast(context.getProviderSpecificContext().getApi());
   }

   @AfterTest
   protected void cleanup() throws InterruptedException, ExecutionException, TimeoutException {
      context.close();
   }

}
