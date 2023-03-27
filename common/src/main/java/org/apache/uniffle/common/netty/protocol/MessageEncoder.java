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

package org.apache.uniffle.common.netty.protocol;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class MessageEncoder extends MessageToMessageEncoder<Encodable> {

  private static final Logger LOG = LoggerFactory.getLogger(ChannelOutboundHandlerAdapter.class);

  public static final MessageEncoder INSTANCE = new MessageEncoder();

  private MessageEncoder() {
  }

  @Override
  protected void encode(ChannelHandlerContext ctx, Encodable msg, List<Object> out) throws Exception {
    ByteBuf buf = ctx.alloc().buffer(msg.encodedLength());
    msg.encode(buf);
    out.add(buf);
  }
}
