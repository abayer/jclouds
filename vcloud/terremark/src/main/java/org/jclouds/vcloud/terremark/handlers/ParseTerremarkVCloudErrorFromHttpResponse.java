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

package org.jclouds.vcloud.terremark.handlers;

import static org.jclouds.http.HttpUtils.releasePayload;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.inject.Singleton;

import org.jclouds.http.HttpCommand;
import org.jclouds.http.HttpErrorHandler;
import org.jclouds.http.HttpResponse;
import org.jclouds.http.HttpResponseException;
import org.jclouds.logging.Logger;
import org.jclouds.rest.AuthorizationException;
import org.jclouds.rest.ResourceNotFoundException;
import org.jclouds.util.Utils;

/**
 * This will parse and set an appropriate exception on the command object.
 * 
 * @author Adrian Cole
 * 
 */
@Singleton
public class ParseTerremarkVCloudErrorFromHttpResponse implements HttpErrorHandler {
   @Resource
   protected Logger logger = Logger.NULL;
   public static final Pattern RESOURCE_PATTERN = Pattern.compile(".*/v[^/]+/([^/]+)/([0-9]+)");

   public void handleError(HttpCommand command, HttpResponse response) {
      Exception exception = new HttpResponseException(command, response);

      try {
         String content = parseErrorFromContentOrNull(command, response);
         switch (response.getStatusCode()) {
         case 401:
            exception = new AuthorizationException(command.getRequest(), content);
            break;
         case 403: // TODO temporary as terremark mistakenly uses this for vApp
            // not found.
         case 404:
            if (!command.getRequest().getMethod().equals("DELETE")) {
               String path = command.getRequest().getEndpoint().getPath();
               Matcher matcher = RESOURCE_PATTERN.matcher(path);
               String message;
               if (matcher.find()) {
                  message = String.format("%s %s not found", matcher.group(1), matcher.group(2));
               } else {
                  message = path;
               }
               exception = new ResourceNotFoundException(message);
            }
            break;
         case 500:
            if ((response.getMessage().indexOf("because there is a pending task running") != -1)
                  || (response.getMessage().indexOf("because it is already powered off") != -1)
                  || (response.getMessage().indexOf("already exists") != -1)
                  || (response.getMessage().indexOf("same name exists") != -1))
               exception = new IllegalStateException(response.getMessage(), exception);
            break;
         default:
            exception = new HttpResponseException(command, response, content);
         }
      } finally {
         releasePayload(response);
         command.setException(exception);
      }
   }

   String parseErrorFromContentOrNull(HttpCommand command, HttpResponse response) {
      if (response.getPayload() != null) {
         try {
            return Utils.toStringAndClose(response.getPayload().getInput());
         } catch (IOException e) {
            logger.warn(e, "exception reading error from response", response);
         }
      }
      return null;
   }
}