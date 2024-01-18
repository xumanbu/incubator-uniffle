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

package org.apache.uniffle.client.response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import org.apache.uniffle.client.impl.FailedBlockSendTracker;
import org.apache.uniffle.common.ShuffleServerInfo;

public class SendShuffleDataResult {

  private Set<Long> successBlockIds;
  private FailedBlockSendTracker failedBlockSendTracker;

  public SendShuffleDataResult(
      Set<Long> successBlockIds, FailedBlockSendTracker failedBlockSendTracker) {
    this.successBlockIds = successBlockIds;
    this.failedBlockSendTracker = failedBlockSendTracker;
  }

  public Set<Long> getSuccessBlockIds() {
    return successBlockIds;
  }

  public Set<Long> getFailedBlockIds() {
    return failedBlockSendTracker.getFailedBlockIds();
  }

  public Map<Long, BlockingQueue<ShuffleServerInfo>> getSendFailedBlockIds() {
    return null;
  }

  public FailedBlockSendTracker getFailedBlockSendTracker() {
    return failedBlockSendTracker;
  }
}
