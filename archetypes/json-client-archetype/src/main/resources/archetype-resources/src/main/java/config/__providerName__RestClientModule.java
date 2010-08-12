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

#set( $ucaseProviderName = ${providerName.toUpperCase()} )
package ${package}.config;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import javax.inject.Named;
import javax.inject.Singleton;

import org.jclouds.http.HttpErrorHandler;
import org.jclouds.http.RequiresHttp;
import org.jclouds.http.annotation.ClientError;
import org.jclouds.http.annotation.Redirection;
import org.jclouds.http.annotation.ServerError;
import org.jclouds.http.filters.BasicAuthentication;
import org.jclouds.rest.ConfiguresRestClient;
import org.jclouds.rest.config.RestClientModule;
import org.jclouds.crypto.Crypto;

import ${package}.${providerName};
import ${package}.${providerName}Client;
import ${package}.${providerName}AsyncClient;
import ${package}.reference.${providerName}Constants;
import ${package}.handlers.${providerName}ErrorHandler;

import com.google.inject.Provides;

/**
 * Configures the ${providerName} connection.
 * 
 * @author ${author}
 */
@RequiresHttp
@ConfiguresRestClient
public class ${providerName}RestClientModule  extends
         RestClientModule<${providerName}Client, ${providerName}AsyncClient> {

   public ${providerName}RestClientModule() {
      super(${providerName}Client.class, ${providerName}AsyncClient.class);
   }

   @Provides
   @Singleton
   public BasicAuthentication provideBasicAuthentication(
            @Named(${providerName}Constants.PROPERTY_${ucaseProviderName}_USER) String user,
            @Named(${providerName}Constants.PROPERTY_${ucaseProviderName}_PASSWORD) String password,
            Crypto crypto)
            throws UnsupportedEncodingException {
      return new BasicAuthentication(user, password, crypto);
   }

   @Provides
   @Singleton
   @${providerName}
   protected URI provideURI(@Named(${providerName}Constants.PROPERTY_${ucaseProviderName}_ENDPOINT) String endpoint) {
      return URI.create(endpoint);
   }

   @Override
   protected void bindErrorHandlers() {
      bind(HttpErrorHandler.class).annotatedWith(Redirection.class).to(
               ${providerName}ErrorHandler.class);
      bind(HttpErrorHandler.class).annotatedWith(ClientError.class).to(
               ${providerName}ErrorHandler.class);
      bind(HttpErrorHandler.class).annotatedWith(ServerError.class).to(
               ${providerName}ErrorHandler.class);
   }

   @Override
   protected void bindRetryHandlers() {
      // TODO
   }

}