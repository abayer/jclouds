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

package org.jclouds.vcloud.xml;

import static org.jclouds.vcloud.util.Utils.newNamedResource;
import static org.jclouds.vcloud.util.Utils.putNamedResource;

import java.util.Map;

import org.jclouds.http.functions.ParseSax;
import org.jclouds.vcloud.domain.Capacity;
import org.jclouds.vcloud.domain.NamedResource;
import org.jclouds.vcloud.domain.Quota;
import org.jclouds.vcloud.domain.VDC;
import org.jclouds.vcloud.domain.internal.VDCImpl;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.google.common.collect.Maps;

/**
 * @author Adrian Cole
 */
public class VDCHandler extends ParseSax.HandlerWithResult<VDC> {
   private StringBuilder currentText = new StringBuilder();

   private NamedResource vDC;
   private Map<String, NamedResource> resourceEntities = Maps.newHashMap();
   private Map<String, NamedResource> availableNetworks = Maps.newHashMap();

   private String description;

   private Quota instantiatedVmsQuota;

   private Capacity memoryCapacity;

   private Capacity cpuCapacity;

   private Capacity storageCapacity;

   private Quota deployedVmsQuota;

   private String units;

   private int allocated;

   private int used;

   private int limit;

   public VDC getResult() {
      return new VDCImpl(vDC.getId(), vDC.getName(), vDC.getLocation(), description,
               storageCapacity, cpuCapacity, memoryCapacity, instantiatedVmsQuota,
               deployedVmsQuota, resourceEntities, availableNetworks);
   }

   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
      if (qName.equals("Vdc")) {
         vDC = newNamedResource(attributes);
      } else if (qName.equals("Network")) {
         putNamedResource(availableNetworks, attributes);
      } else if (qName.equals("ResourceEntity")) {
         putNamedResource(resourceEntities, attributes);
      }
   }

   public void endElement(String uri, String name, String qName) {
      if (qName.equals("Description")) {
         description = currentOrNull();
      } else if (qName.equals("Units")) {
         units = currentOrNull();
      } else if (qName.equals("Allocated")) {
         allocated = Integer.parseInt(currentOrNull());
      } else if (qName.equals("Used")) {
         used = Integer.parseInt(currentOrNull());
      } else if (qName.equals("Limit")) {
         limit = Integer.parseInt(currentOrNull());
      } else if (qName.equals("StorageCapacity")) {
         storageCapacity = new Capacity(units, allocated, used);
      } else if (qName.equals("Cpu")) {
         cpuCapacity = new Capacity(units, allocated, used);
      } else if (qName.equals("Memory")) {
         memoryCapacity = new Capacity(units, allocated, used);
      } else if (qName.equals("InstantiatedVmsQuota")) {
         instantiatedVmsQuota = new Quota(limit, used);
      } else if (qName.equals("DeployedVmsQuota")) {
         deployedVmsQuota = new Quota(limit, used);
      }
      currentText = new StringBuilder();
   }

   public void characters(char ch[], int start, int length) {
      currentText.append(ch, start, length);
   }

   protected String currentOrNull() {
      String returnVal = currentText.toString().trim();
      return returnVal.equals("") ? null : returnVal;
   }
}
