/*
 * The MIT License
 *
 * Copyright 2020 vinayak.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.vinayaksproject.simpleelasticproject.services.events;

import com.vinayaksproject.simpleelasticproject.entity.EntityAudit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Implementation of EventPublisherImpl
 *
 * @author vinayak
 */
@Service
public class EntityChangeEventPublisherImpl implements EntityChangeEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    private static final Logger LOG = Logger.getLogger(EntityChangeEventPublisherImpl.class.getName());

    private void publishEvent(Event event) {
        if (LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "Publishing event {0}", event);
        }
        applicationEventPublisher.publishEvent(event);

    }

    @Override
    public void publishEntityChangeEvent(Object source, String fromServer, EntityAudit entity, EntityChangeEvent.EntityChangeEventType type) {
        EntityChangeEvent event = new EntityChangeEvent(source, fromServer, entity, type);
        publishEvent(event);
    }

}
