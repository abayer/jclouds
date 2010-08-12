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

import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.jclouds.aws.Region;
import org.jclouds.aws.ec2.domain.PublicIpInstanceIdPair;
import org.jclouds.aws.ec2.util.EC2Utils;
import org.jclouds.http.functions.ParseSax.HandlerForGeneratedRequestWithResult;
import org.jclouds.logging.Logger;

import com.google.common.collect.Sets;

/**
 * 
 * @author Adrian Cole
 */
public class DescribeAddressesResponseHandler extends
         HandlerForGeneratedRequestWithResult<Set<PublicIpInstanceIdPair>> {

   @Resource
   protected Logger logger = Logger.NULL;
   private Set<PublicIpInstanceIdPair> pairs = Sets.newLinkedHashSet();
   private String ipAddress;
   private StringBuilder currentText = new StringBuilder();
   @Inject
   @Region
   String defaultRegion;
   private String instanceId;

   protected String currentOrNull() {
      String returnVal = currentText.toString().trim();
      return returnVal.equals("") ? null : returnVal;
   }

   public void endElement(String uri, String name, String qName) {
      if (qName.equals("publicIp")) {
         ipAddress = currentOrNull();
      } else if (qName.equals("instanceId")) {
         instanceId = currentOrNull();
      } else if (qName.equals("item")) {
         String region = EC2Utils.findRegionInArgsOrNull(getRequest());
         if (region == null)
            region = defaultRegion;
         pairs.add(new PublicIpInstanceIdPair(region, ipAddress, instanceId));
         ipAddress = null;
         instanceId = null;
      }
      currentText = new StringBuilder();
   }

   public void characters(char ch[], int start, int length) {
      currentText.append(ch, start, length);
   }

   @Override
   public Set<PublicIpInstanceIdPair> getResult() {
      return pairs;
   }

}