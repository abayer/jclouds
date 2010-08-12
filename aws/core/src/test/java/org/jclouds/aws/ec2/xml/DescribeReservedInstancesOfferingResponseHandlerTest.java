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

package org.jclouds.aws.ec2.xml;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.testng.Assert.assertEquals;

import java.io.InputStream;

import org.jclouds.aws.ec2.domain.ReservedInstancesOffering;
import org.jclouds.http.functions.ParseSax;
import org.jclouds.rest.internal.GeneratedHttpRequest;
import org.testng.annotations.Test;

import com.google.common.collect.Iterables;

/**
 * Tests behavior of {@code DescribeReservedInstancesOfferingResponseHandler}
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "ec2.DescribeReservedInstancesOfferingResponseHandlerTest")
public class DescribeReservedInstancesOfferingResponseHandlerTest extends BaseEC2HandlerTest {
   public void testApplyInputStream() {
      InputStream is = getClass().getResourceAsStream("/ec2/describe_reserved_instances_offerings.xml");

      ReservedInstancesOffering expected = new ReservedInstancesOffering("us-east-1", "us-east-1a", 12, 0.0f, "m1.small",
            "m1.small offering in us-east-1a", "4b2293b4-5813-4cc8-9ce3-1957fc1dcfc8", 0.0f);

      DescribeReservedInstancesOfferingResponseHandler handler = injector
            .getInstance(DescribeReservedInstancesOfferingResponseHandler.class);
      addDefaultRegionToHandler(handler);
      ReservedInstancesOffering result = Iterables.getOnlyElement(factory.create(handler).parse(is));

      assertEquals(result, expected);
   }

   private void addDefaultRegionToHandler(ParseSax.HandlerWithResult<?> handler) {
      GeneratedHttpRequest<?> request = createMock(GeneratedHttpRequest.class);
      expect(request.getArgs()).andReturn(new Object[] { null });
      replay(request);
      handler.setContext(request);
   }
}