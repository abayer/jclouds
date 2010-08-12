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

package org.jclouds.ssh;

/**
 * @author Adrian Cole
 */
public class SshException extends RuntimeException {

   /** The serialVersionUID */
   private static final long serialVersionUID = 7271048517353750433L;

   public SshException() {
      super();
   }

   public SshException(String arg0, Throwable arg1) {
      super(arg0, arg1);
   }

   public SshException(String arg0) {
      super(arg0);
   }

   public SshException(Throwable arg0) {
      super(arg0);
   }

}