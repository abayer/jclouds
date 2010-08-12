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

package org.jclouds.aws.sqs.xml;

import java.util.Set;

import javax.inject.Inject;

import org.jclouds.aws.sqs.domain.Queue;
import org.jclouds.http.functions.ParseSax;

import com.google.common.collect.Sets;

/**
 * 
 * @see <a href="http://docs.amazonwebservices.com/AWSSimpleQueueService/latest/APIReference/Query_QueryListQueues.html"
 *      />
 * @author Adrian Cole
 */
public class ListQueuesResponseHandler extends ParseSax.HandlerWithResult<Set<Queue>> {

   Set<Queue> queues = Sets.newLinkedHashSet();

   private final QueueHandler qHandler;

   @Inject
   ListQueuesResponseHandler(QueueHandler qHandler) {
      this.qHandler = qHandler;
   }

   public Set<Queue> getResult() {
      return queues;
   }

   public void endElement(String uri, String name, String qName) {
      qHandler.endElement(uri, name, qName);
      if (qName.equals("QueueUrl")) {
         queues.add(qHandler.getResult());
      }
   }

   public void characters(char ch[], int start, int length) {
      qHandler.characters(ch, start, length);
   }
}
