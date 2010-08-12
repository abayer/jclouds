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

package org.jclouds.rest.binders;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import java.io.File;
import java.net.URI;

import javax.inject.Provider;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.UriBuilder;

import org.jboss.resteasy.specimpl.UriBuilderImpl;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.internal.GeneratedHttpRequest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

/**
 * Tests behavior of {@code BindMapToMatrixParams}
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "rest.BindMapToMatrixParamsTest")
public class BindMapToMatrixParamsTest {

   @Test
   public void testCorrect() throws SecurityException, NoSuchMethodException {

      HttpRequest request = createMock(HttpRequest.class);
      expect(request.getEndpoint()).andReturn(URI.create("http://momma/"));
      request.setEndpoint(URI.create("http://momma/;imageName=foo"));
      expect(request.getEndpoint()).andReturn(URI.create("http://momma/;imageName=foo"));
      request.setEndpoint(URI.create("http://momma/;imageName=foo;serverId=2"));

      replay(request);
      BindMapToMatrixParams binder = new BindMapToMatrixParams(new Provider<UriBuilder>() {

         @Override
         public UriBuilder get() {
            return new UriBuilderImpl();
         }

      });

      binder.bindToRequest(request, ImmutableMap.of("imageName", "foo", "serverId", "2"));

      verify(request);

   }

   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testMustBeMap() {
      BindMapToMatrixParams binder = new BindMapToMatrixParams(null);
      HttpRequest request = new HttpRequest(HttpMethod.POST, URI.create("http://localhost"));
      binder.bindToRequest(request, new File("foo"));
   }

   @Test(expectedExceptions = { NullPointerException.class, IllegalStateException.class })
   public void testNullIsBad() {
      BindMapToMatrixParams binder = new BindMapToMatrixParams(null);
      GeneratedHttpRequest<?> request = createMock(GeneratedHttpRequest.class);
      binder.bindToRequest(request, null);
   }
}
