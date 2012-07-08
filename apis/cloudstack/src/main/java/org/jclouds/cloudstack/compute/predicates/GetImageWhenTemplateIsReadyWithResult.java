/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jclouds.cloudstack.compute.predicates;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

import org.jclouds.compute.domain.Image;
import org.jclouds.compute.reference.ComputeServiceConstants;
import org.jclouds.logging.Logger;
import org.jclouds.cloudstack.CloudStackClient;
import org.jclouds.cloudstack.domain.Template;
import org.jclouds.predicates.PredicateWithResult;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * @author David Alves
 */
public final class GetImageWhenTemplateIsReadyWithResult implements
                                                             PredicateWithResult<String, Image> {

   @Resource
   @Named(ComputeServiceConstants.COMPUTE_LOGGER)
   protected Logger logger = Logger.NULL;

   private Template result;
   private RuntimeException lastFailure;
   private Function<Template, Image> templateToImage;
   private CloudStackClient client;

   @Inject
   public GetImageWhenTemplateIsReadyWithResult(Function<Template, Image> templateToImage,
            CloudStackClient client) {
      this.templateToImage = templateToImage;
      this.client = client;
   }

   @Override
   public boolean apply(String input) {
      result = checkNotNull(findTemplate(input));

      if (result.isReady()) {
          logger.info("<< Template %s is available for use.", input);
          return true;
      } else {
          logger.info("<< Template %s is not available yet.", input);
          return false;
      }
   }

   @Override
   public Image getResult() {
       return templateToImage.apply(result);
   }

   @Override
   public Throwable getLastFailure() {
      return lastFailure;
   }

   private Template findTemplate(String id) {
       return Iterables.getOnlyElement(cloudStackClient.getVirtualMachineClient().getVirtualMachine(id));
   }

}