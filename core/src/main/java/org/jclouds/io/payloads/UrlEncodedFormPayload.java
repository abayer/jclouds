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

package org.jclouds.io.payloads;

import static org.jclouds.http.HttpUtils.makeQueryLine;

import java.io.InputStream;
import java.util.Comparator;
import java.util.Map;

import javax.annotation.Nullable;
import javax.ws.rs.core.MediaType;

import org.jclouds.util.Utils;

import com.google.common.collect.Multimap;

/**
 * @author Adrian Cole
 */
public class UrlEncodedFormPayload extends BasePayload<String> {
   public UrlEncodedFormPayload(Multimap<String, String> formParams, char... skips) {
      this(formParams, null, skips);
   }

   public UrlEncodedFormPayload(Multimap<String, String> formParams,
            @Nullable Comparator<Map.Entry<String, String>> sorter, char... skips) {
      super(makeQueryLine(formParams, sorter, skips), MediaType.APPLICATION_FORM_URLENCODED, null,
               null);
      setContentLength(new Long(content.length()));
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public InputStream getInput() {
      return Utils.toInputStream(content);
   }


}