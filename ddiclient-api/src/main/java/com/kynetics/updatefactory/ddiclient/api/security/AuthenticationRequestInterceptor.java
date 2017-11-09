/*
 * Copyright © 2017 Kynetics LLC
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 */

package com.kynetics.updatefactory.ddiclient.api.security;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.kynetics.updatefactory.ddiclient.api.security.Authentication.AuthenticationType.ANONYMOUS_AUTHENTICATION;

/**
 * @author Daniele Sergio
 */
public class AuthenticationRequestInterceptor implements Interceptor {

    public AuthenticationRequestInterceptor(List<Authentication> authentications) {
        Objects.requireNonNull(authentications);
        this.authentications = authentications;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request originalRequest = chain.request();

        final Request.Builder builder = originalRequest.newBuilder();

        authentications.stream()
                .filter(a -> a.getType() != ANONYMOUS_AUTHENTICATION)
                .forEach( a -> builder.addHeader(a.getType().getHeader(), a.getToken()) );

        return chain.proceed(builder.build());
    }

    private final List<Authentication> authentications;
}
