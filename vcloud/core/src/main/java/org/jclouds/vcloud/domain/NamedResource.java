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

package org.jclouds.vcloud.domain;

import java.net.URI;

import org.jclouds.vcloud.domain.internal.NamedResourceImpl;

import com.google.inject.ImplementedBy;

/**
 * Location of a Rest resource
 * 
 * @author Adrian Cole
 * 
 */
@ImplementedBy(NamedResourceImpl.class)
public interface NamedResource extends Comparable<NamedResource> {

   /**
    * name is not a safe means to identify a resource. Please use name or
    * location
    */
   @Deprecated
   String getId();

   String getName();

   String getType();

   URI getLocation();
}