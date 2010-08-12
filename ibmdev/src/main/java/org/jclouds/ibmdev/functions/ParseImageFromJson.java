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

package org.jclouds.ibmdev.functions;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.http.HttpResponse;
import org.jclouds.http.functions.ParseJson;
import org.jclouds.ibmdev.domain.Image;

import com.google.common.base.Function;

/**
 * @author Adrian Cole
 */
@Singleton
public class ParseImageFromJson implements Function<HttpResponse, Image> {

   private final ParseJson<Image> json;

   @Inject
   ParseImageFromJson(ParseJson<Image> json) {
      this.json = json;
   }

   @Override
   public Image apply(HttpResponse arg0) {
      Image input = json.apply(arg0);
      ParseUtils.CLEAN_IMAGE.apply(input);
      return input;
   }
}