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

package org.jclouds.aws.functions;

import java.net.URI;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.aws.Region;
import org.jclouds.rest.annotations.Provider;

import com.google.common.base.Function;

/**
 * 
 * @author Adrian Cole
 */
@Singleton
public class RegionToEndpoint implements Function<Object, URI> {
   private final Map<String, URI> regionToEndpoint;
   private final URI defaultUri;

   @Inject
   public RegionToEndpoint(@Region Map<String, URI> regionToEndpoint, @Provider URI defaultUri) {
      this.regionToEndpoint = regionToEndpoint;
      this.defaultUri = defaultUri;
   }

   public URI apply(Object from) {
      return from == null ? defaultUri : regionToEndpoint.get(from);
   }

}