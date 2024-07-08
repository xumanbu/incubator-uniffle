/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.uniffle.common.util;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class AutoCloseWrapper<T extends Closeable> implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(AutoCloseWrapper.class);
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private volatile T t;
    private Supplier<T> cf;
    private transient volatile long freshTime;
    private long delayCloseInterval;

    public AutoCloseWrapper(Supplier<T> cf) {
        this(cf, 3000);
    }

    public AutoCloseWrapper(Supplier<T> cf, long delayCloseInterval) {
        this.cf = cf;
        this.delayCloseInterval = delayCloseInterval;
    }

    public synchronized T get() {
        freshTime = System.currentTimeMillis();
        if (t == null) {
            t = cf.get();
        }
        executor.schedule(this::closeInternal, delayCloseInterval, TimeUnit.MILLISECONDS);
        return t;
    }

    @VisibleForTesting
    public synchronized void closeInternal() {
        try {
            t.close();
        } catch (Exception e) {
            LOG.warn("Failed to close " + t.getClass().getName() + " the resource", e);
        } finally {
            t = null;
        }
    }

    public synchronized void close() throws IOException {
        if (System.currentTimeMillis() - freshTime > delayCloseInterval) {
            this.closeInternal();
            freshTime = 0;
        }
    }

}
