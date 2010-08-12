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

package org.jclouds.aws.ec2.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

/**
 * 
 * @see <a href="http://docs.amazonwebservices.com/AWSEC2/latest/APIReference/ApiReference-ItemType-SecurityGroupItemType.html"
 *      />
 * @author Adrian Cole
 */
public class SecurityGroup implements Comparable<SecurityGroup> {

   private final String region;
   private final String name;
   private final String ownerId;
   private final String description;
   private final Set<IpPermission> ipPermissions;

   public SecurityGroup(String region, String name, String ownerId, String description,
            Set<IpPermission> ipPermissions) {
      this.region = checkNotNull(region, "region");
      this.name = name;
      this.ownerId = ownerId;
      this.description = description;
      this.ipPermissions = ipPermissions;
   }

   /**
    * Security groups are not copied across Regions. Instances within the Region cannot communicate
    * with instances outside the Region using group-based firewall rules. Traffic from instances in
    * another Region is seen as WAN bandwidth.
    */
   public String getRegion() {
      return region;
   }

   /**
    * {@inheritDoc}
    */
   public int compareTo(SecurityGroup o) {
      return (this == o) ? 0 : getName().compareTo(o.getName());
   }

   /**
    * Name of the security group.
    */
   public String getName() {
      return name;
   }

   /**
    * AWS Access Key ID of the owner of the security group.
    */
   public String getOwnerId() {
      return ownerId;
   }

   /**
    * Description of the security group.
    */
   public String getDescription() {
      return description;
   }

   /**
    * Set of IP permissions associated with the security group.
    */
   public Set<IpPermission> getIpPermissions() {
      return ipPermissions;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((description == null) ? 0 : description.hashCode());
      result = prime * result + ((ipPermissions == null) ? 0 : ipPermissions.hashCode());
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((ownerId == null) ? 0 : ownerId.hashCode());
      result = prime * result + ((region == null) ? 0 : region.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      SecurityGroup other = (SecurityGroup) obj;
      if (description == null) {
         if (other.description != null)
            return false;
      } else if (!description.equals(other.description))
         return false;
      if (ipPermissions == null) {
         if (other.ipPermissions != null)
            return false;
      } else if (!ipPermissions.equals(other.ipPermissions))
         return false;
      if (name == null) {
         if (other.name != null)
            return false;
      } else if (!name.equals(other.name))
         return false;
      if (ownerId == null) {
         if (other.ownerId != null)
            return false;
      } else if (!ownerId.equals(other.ownerId))
         return false;
      if (region == null) {
         if (other.region != null)
            return false;
      } else if (!region.equals(other.region))
         return false;
      return true;
   }

   @Override
   public String toString() {
      return "SecurityGroup [description=" + description + ", ipPermissions=" + ipPermissions
               + ", name=" + name + ", ownerId=" + ownerId + ", region=" + region + "]";
   }
}