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

import javax.inject.Inject;

import org.jclouds.aws.Region;
import org.jclouds.aws.ec2.domain.ReservedInstancesOffering;
import org.jclouds.aws.ec2.util.EC2Utils;
import org.jclouds.http.functions.ParseSax;

/**
 * 
 * @see <a href="http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/index.html?ApiReference-query-DescribeReservedInstancesOfferingsResponseSetItemType.html"
 *      />
 * @author Adrian Cole
 */
public class ReservedInstancesOfferingHandler extends
      ParseSax.HandlerForGeneratedRequestWithResult<ReservedInstancesOffering> {
   @Inject
   @Region
   String defaultRegion;

   private StringBuilder currentText = new StringBuilder();

   private String availabilityZone;
   private long duration = 0;
   private float fixedPrice = 0;
   private String instanceType;
   private String productDescription;
   private String id;
   private float usagePrice = 0;

   public ReservedInstancesOffering getResult() {
      String region = EC2Utils.findRegionInArgsOrNull(getRequest());
      if (region == null)
         region = defaultRegion;

      ReservedInstancesOffering returnVal = new ReservedInstancesOffering(region, availabilityZone, duration,
            fixedPrice, instanceType, productDescription, id, usagePrice);
      this.availabilityZone = null;
      this.duration = 0;
      this.fixedPrice = 0;
      this.instanceType = null;
      this.productDescription = null;
      this.id = null;
      this.usagePrice = 0;
      return returnVal;
   }

   public void endElement(String uri, String name, String qName) {

      if (qName.equalsIgnoreCase("reservedInstancesOfferingId")) {
         this.id = currentText.toString().trim();
      } else if (qName.equalsIgnoreCase("availabilityZone")) {
         this.availabilityZone = currentText.toString().trim();
      } else if (qName.equalsIgnoreCase("duration")) {
         this.duration = Long.parseLong(currentText.toString().trim());
      } else if (qName.equalsIgnoreCase("fixedPrice")) {
         this.fixedPrice = Float.parseFloat(currentText.toString().trim());
      } else if (qName.equalsIgnoreCase("instanceType")) {
         this.instanceType = currentText.toString().trim();
      } else if (qName.equalsIgnoreCase("productDescription")) {
         this.productDescription = currentText.toString().trim();
      } else if (qName.equalsIgnoreCase("usagePrice")) {
         this.usagePrice = Float.parseFloat(currentText.toString().trim());
      }
      currentText = new StringBuilder();
   }

   public void characters(char ch[], int start, int length) {
      currentText.append(ch, start, length);
   }
}
