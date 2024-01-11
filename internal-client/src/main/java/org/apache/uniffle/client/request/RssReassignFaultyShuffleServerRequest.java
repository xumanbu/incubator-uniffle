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

package org.apache.uniffle.client.request;

import java.util.Set;

import org.apache.uniffle.proto.RssProtos;

public class RssReassignFaultyShuffleServerRequest {

  private int shuffleId;
  private Set<String> partitionIds;
  private String faultyShuffleServerId;

  public RssReassignFaultyShuffleServerRequest(
      int shuffleId, Set<String> partitionIds, String faultyShuffleServerId) {
    this.shuffleId = shuffleId;
    this.partitionIds = partitionIds;
    this.faultyShuffleServerId = faultyShuffleServerId;
  }

  public int getShuffleId() {
    return shuffleId;
  }

  public Set<String> getPartitionIds() {
    return partitionIds;
  }

  public String getFaultyShuffleServerId() {
    return faultyShuffleServerId;
  }

  public RssProtos.RssReassignFaultyShuffleServerRequest toProto() {
    RssProtos.RssReassignFaultyShuffleServerRequest.Builder builder =
        RssProtos.RssReassignFaultyShuffleServerRequest.newBuilder()
            .setShuffleId(this.shuffleId)
            .setFaultyShuffleServerId(this.faultyShuffleServerId)
            .addAllPartitionIds(this.partitionIds);
    return builder.build();
  }
}
