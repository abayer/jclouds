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

package org.jclouds.aws.ec2.binders;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.jclouds.http.HttpUtils.addFormParamTo;

import javax.inject.Singleton;

import org.jclouds.aws.ec2.domain.UserIdGroupPair;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.Binder;

/**
 * Binds the String [] to query parameters named with GroupName.index
 * 
 * @author Adrian Cole
 */
@Singleton
public class BindUserIdGroupPairToSourceSecurityGroupFormParams implements Binder {

   public void bindToRequest(HttpRequest request, Object input) {
      checkArgument(checkNotNull(input, "input") instanceof UserIdGroupPair,
               "this binder is only valid for UserIdGroupPair!");
      UserIdGroupPair pair = (UserIdGroupPair) input;
      addFormParamTo(request, "SourceSecurityGroupOwnerId", pair.getUserId());
      addFormParamTo(request, "SourceSecurityGroupName", pair.getGroupName());
   }
}