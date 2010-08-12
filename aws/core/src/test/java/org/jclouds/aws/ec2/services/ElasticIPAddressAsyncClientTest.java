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

package org.jclouds.aws.ec2.services;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

import org.jclouds.aws.ec2.xml.AllocateAddressResponseHandler;
import org.jclouds.aws.ec2.xml.DescribeAddressesResponseHandler;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.functions.ParseSax;
import org.jclouds.http.functions.ReleasePayloadAndReturn;
import org.jclouds.rest.functions.ReturnEmptySetOnNotFoundOr404;
import org.jclouds.rest.internal.RestAnnotationProcessor;
import org.testng.annotations.Test;

import com.google.inject.TypeLiteral;

/**
 * Tests behavior of {@code ElasticIPAddressAsyncClient}
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "ec2.ElasticIPAddressAsyncClientTest")
public class ElasticIPAddressAsyncClientTest extends BaseEC2AsyncClientTest<ElasticIPAddressAsyncClient> {

   public void testDisassociateAddress() throws SecurityException, NoSuchMethodException, IOException {
      Method method = ElasticIPAddressAsyncClient.class.getMethod("disassociateAddressInRegion", String.class,
            String.class);
      HttpRequest request = processor.createRequest(method, null, "127.0.0.1");

      assertRequestLineEquals(request, "POST https://ec2.us-east-1.amazonaws.com/ HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Host: ec2.us-east-1.amazonaws.com\n");
      assertPayloadEquals(request, "Version=2010-06-15&Action=DisassociateAddress&PublicIp=127.0.0.1",
            "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(request);
   }

   public void testAssociateAddress() throws SecurityException, NoSuchMethodException, IOException {
      Method method = ElasticIPAddressAsyncClient.class.getMethod("associateAddressInRegion", String.class,
            String.class, String.class);
      HttpRequest request = processor.createRequest(method, null, "127.0.0.1", "me");

      assertRequestLineEquals(request, "POST https://ec2.us-east-1.amazonaws.com/ HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Host: ec2.us-east-1.amazonaws.com\n");
      assertPayloadEquals(request, "Version=2010-06-15&Action=AssociateAddress&InstanceId=me&PublicIp=127.0.0.1",
            "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(request);
   }

   public void testReleaseAddress() throws SecurityException, NoSuchMethodException, IOException {
      Method method = ElasticIPAddressAsyncClient.class.getMethod("releaseAddressInRegion", String.class, String.class);
      HttpRequest request = processor.createRequest(method, null, "127.0.0.1");

      assertRequestLineEquals(request, "POST https://ec2.us-east-1.amazonaws.com/ HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Host: ec2.us-east-1.amazonaws.com\n");
      assertPayloadEquals(request, "Version=2010-06-15&Action=ReleaseAddress&PublicIp=127.0.0.1",
            "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, request, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, null);

      checkFilters(request);
   }

   public void testDescribeAddresses() throws SecurityException, NoSuchMethodException, IOException {
      Method method = ElasticIPAddressAsyncClient.class.getMethod("describeAddressesInRegion", String.class, Array
            .newInstance(String.class, 0).getClass());
      HttpRequest request = processor.createRequest(method, null, "127.0.0.1");

      assertRequestLineEquals(request, "POST https://ec2.us-east-1.amazonaws.com/ HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Host: ec2.us-east-1.amazonaws.com\n");
      assertPayloadEquals(request, "Version=2010-06-15&Action=DescribeAddresses&PublicIp.1=127.0.0.1",
            "application/x-www-form-urlencoded", false);

      assertResponseParserClassEquals(method, request, ParseSax.class);
      assertSaxResponseParserClassEquals(method, DescribeAddressesResponseHandler.class);
      assertExceptionParserClassEquals(method, ReturnEmptySetOnNotFoundOr404.class);

      checkFilters(request);
   }

   public void testAllocateAddress() throws SecurityException, NoSuchMethodException, IOException {
      Method method = ElasticIPAddressAsyncClient.class.getMethod("allocateAddressInRegion", String.class);
      HttpRequest request = processor.createRequest(method, (String) null);

      assertRequestLineEquals(request, "POST https://ec2.us-east-1.amazonaws.com/ HTTP/1.1");
      assertNonPayloadHeadersEqual(request, "Host: ec2.us-east-1.amazonaws.com\n");
      assertPayloadEquals(request, "Version=2010-06-15&Action=AllocateAddress", "application/x-www-form-urlencoded",
            false);

      assertResponseParserClassEquals(method, request, ParseSax.class);
      assertSaxResponseParserClassEquals(method, AllocateAddressResponseHandler.class);
      assertExceptionParserClassEquals(method, null);

      checkFilters(request);
   }

   @Override
   protected TypeLiteral<RestAnnotationProcessor<ElasticIPAddressAsyncClient>> createTypeLiteral() {
      return new TypeLiteral<RestAnnotationProcessor<ElasticIPAddressAsyncClient>>() {
      };
   }

}
