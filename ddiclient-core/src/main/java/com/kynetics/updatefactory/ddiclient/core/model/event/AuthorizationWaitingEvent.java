/*
 * Copyright © 2017-2018 Kynetics LLC
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 */

package com.kynetics.updatefactory.ddiclient.core.model.event;

import static com.kynetics.updatefactory.ddiclient.core.model.event.AbstractEvent.EventName.AUTHORIZATION_WAITING;

/**
 * @author Daniele Sergio
 */
public class AuthorizationWaitingEvent extends AbstractEvent {
    private static final long serialVersionUID = -2086057101722612301L;

    public AuthorizationWaitingEvent() {
        super(AUTHORIZATION_WAITING);
    }


}
