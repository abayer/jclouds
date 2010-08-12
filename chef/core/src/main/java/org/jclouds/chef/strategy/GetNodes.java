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

package org.jclouds.chef.strategy;

import org.jclouds.chef.domain.Node;
import org.jclouds.chef.strategy.internal.GetNodesImpl;

import com.google.common.base.Predicate;
import com.google.inject.ImplementedBy;

/**
 * 
 * 
 * @author Adrian Cole
 */
@ImplementedBy(GetNodesImpl.class)
public interface GetNodes {

   Iterable<? extends Node> execute();

   Iterable<? extends Node> execute(Predicate<String> nodeNameSelector);

   Iterable<? extends Node> execute(Iterable<String> toGet);
}