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

import java.util.Map;

import org.jclouds.vcloud.domain.internal.OrganizationImpl;
import org.jclouds.vcloud.endpoints.Catalog;
import org.jclouds.vcloud.endpoints.Org;
import org.jclouds.vcloud.endpoints.TasksList;
import org.jclouds.vcloud.endpoints.VDC;

import com.google.inject.ImplementedBy;

/**
 * @author Adrian Cole
 */
@Org
@ImplementedBy(OrganizationImpl.class)
public interface Organization extends NamedResource {

   @Catalog
   Map<String, NamedResource> getCatalogs();

   @VDC
   Map<String, NamedResource> getVDCs();

   @TasksList
   Map<String, NamedResource> getTasksLists();

}