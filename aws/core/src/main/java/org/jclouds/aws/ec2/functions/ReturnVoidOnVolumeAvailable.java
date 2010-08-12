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

package org.jclouds.aws.ec2.functions;

import static org.jclouds.util.Utils.propagateOrNull;

import javax.inject.Singleton;

import org.jclouds.aws.AWSResponseException;

import com.google.common.base.Function;

@Singleton
public class ReturnVoidOnVolumeAvailable implements Function<Exception, Void> {

   public Void apply(Exception from) {
      if (from instanceof AWSResponseException) {
         AWSResponseException e = (AWSResponseException) from;
         if (e.getError().getCode().equals("IncorrectState")
                  && e.getError().getCode().contains("available"))
            return null;
      }
      return Void.class.cast(propagateOrNull(from));
   }

}