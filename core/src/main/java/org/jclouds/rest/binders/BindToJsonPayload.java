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

package org.jclouds.rest.binders;

import static com.google.common.base.Preconditions.checkState;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.jclouds.http.HttpRequest;
import org.jclouds.json.Json;
import org.jclouds.rest.MapBinder;

/**
 * Binds the object to the request as a json object.
 * 
 * @author Adrian Cole
 * @since 4.0
 */
public class BindToJsonPayload implements MapBinder {

   @Inject
   protected Json jsonBinder;

   public void bindToRequest(HttpRequest request, Map<String, String> postParams) {
      bindToRequest(request, (Object) postParams);
   }

   public void bindToRequest(HttpRequest request, Object toBind) {
      checkState(jsonBinder != null, "Program error: json should have been injected at this point");
      String json = jsonBinder.toJson(toBind);
      request.setPayload(json);
      request.getPayload().setContentType(MediaType.APPLICATION_JSON);
   }

}