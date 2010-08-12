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

package org.jclouds.vcloud.terremark.compute;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.jclouds.Constants;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Size;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.compute.internal.BaseComputeService;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.compute.strategy.DestroyNodeStrategy;
import org.jclouds.compute.strategy.GetNodeMetadataStrategy;
import org.jclouds.compute.strategy.ListNodesStrategy;
import org.jclouds.compute.strategy.RebootNodeStrategy;
import org.jclouds.compute.strategy.RunNodesAndAddToSetStrategy;
import org.jclouds.compute.util.ComputeUtils;
import org.jclouds.domain.Location;
import org.jclouds.vcloud.terremark.compute.domain.OrgAndName;
import org.jclouds.vcloud.terremark.compute.functions.NodeMetadataToOrgAndName;
import org.jclouds.vcloud.terremark.compute.options.TerremarkVCloudTemplateOptions;
import org.jclouds.vcloud.terremark.compute.strategy.CleanupOrphanKeys;
import org.jclouds.vcloud.terremark.domain.KeyPair;

import com.google.common.base.Predicate;

/**
 * @author Adrian Cole
 */
@Singleton
public class TerremarkVCloudComputeService extends BaseComputeService {
   private final CleanupOrphanKeys cleanupOrphanKeys;

   @Inject
   protected TerremarkVCloudComputeService(ComputeServiceContext context,
         Provider<Set<? extends Image>> images,
         Provider<Set<? extends Size>> sizes,
         Provider<Set<? extends Location>> locations,
         ListNodesStrategy listNodesStrategy,
         GetNodeMetadataStrategy getNodeMetadataStrategy,
         RunNodesAndAddToSetStrategy runNodesAndAddToSetStrategy,
         RebootNodeStrategy rebootNodeStrategy,
         DestroyNodeStrategy destroyNodeStrategy,
         Provider<TemplateBuilder> templateBuilderProvider,
         Provider<TemplateOptions> templateOptionsProvider,
         @Named("NODE_RUNNING") Predicate<NodeMetadata> nodeRunning,
         @Named("NODE_TERMINATED") Predicate<NodeMetadata> nodeTerminated,
         ComputeUtils utils,
         @Named(Constants.PROPERTY_USER_THREADS) ExecutorService executor,
         CleanupOrphanKeys cleanupOrphanKeys,
         ConcurrentMap<OrgAndName, KeyPair> credentialsMap,
         NodeMetadataToOrgAndName nodeToOrgAndName) {
      super(context, images, sizes, locations, listNodesStrategy,
            getNodeMetadataStrategy, runNodesAndAddToSetStrategy,
            rebootNodeStrategy, destroyNodeStrategy, templateBuilderProvider,
            templateOptionsProvider, nodeRunning, nodeTerminated, utils,
            executor);
      this.cleanupOrphanKeys = cleanupOrphanKeys;
   }

   /**
    * like {@link BaseComputeService#destroyNodesMatching} except that this will
    * clean implicit keypairs.
    */
   @Override
   public Set<? extends NodeMetadata> destroyNodesMatching(
         Predicate<NodeMetadata> filter) {
      Set<? extends NodeMetadata> deadOnes = super.destroyNodesMatching(filter);
      cleanupOrphanKeys.execute(deadOnes);
      return deadOnes;
   }

   /**
    * returns template options, except of type
    * {@link TerremarkVCloudTemplateOptions}.
    */
   @Override
   public TerremarkVCloudTemplateOptions templateOptions() {
      return TerremarkVCloudTemplateOptions.class.cast(super.templateOptions());
   }

}