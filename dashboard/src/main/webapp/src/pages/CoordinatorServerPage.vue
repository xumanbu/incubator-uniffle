<!--
  ~ Licensed to the Apache Software Foundation (ASF) under one or more
  ~ contributor license agreements.  See the NOTICE file distributed with
  ~ this work for additional information regarding copyright ownership.
  ~ The ASF licenses this file to You under the Apache License, Version 2.0
  ~ (the "License"); you may not use this file except in compliance with
  ~ the License.  You may obtain a copy of the License at
  ~
  ~    http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  -->

<template>
  <div class="demo-collapse">
    <el-collapse v-model="pageData.activeNames" accordion:false>
      <el-collapse-item title="Coordinator Server" name="1">
        <div>
          <el-descriptions class="margin-top" :column="3" :size="size" border>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon :style="iconStyle">
                    <Platform />
                  </el-icon>
                  Coordinatro ID
                </div>
              </template>
              {{ pageData.serverInfo.coordinatorId }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon :style="iconStyle">
                    <Link />
                  </el-icon>
                  IP Address
                </div>
              </template>
              {{ pageData.serverInfo.serverIp }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon :style="iconStyle">
                    <Wallet />
                  </el-icon>
                  Coordinator Server Port
                </div>
              </template>
              {{ pageData.serverInfo.serverPort }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div class="cell-item">
                  <el-icon :style="iconStyle">
                    <Wallet />
                  </el-icon>
                  Coordinator Web Port
                </div>
              </template>
              {{ pageData.serverInfo.serverWebPort }}
            </el-descriptions-item>
          </el-descriptions>
        </div>
      </el-collapse-item>
      <el-collapse-item title="Coordinator Properties" name="2">
        <el-table :data="pageData.tableData" stripe style="width: 100%">
          <el-table-column prop="argumentKey" label="Name" min-width="380" />
          <el-table-column prop="argumentValue" label="Value" min-width="380" />
        </el-table>
      </el-collapse-item>
      <el-collapse-item title="Coordinator Metrics" name="3">
        <el-link @click="getCoordinatorMetrics" target="_blank">
          <el-icon :style="iconStyle">
            <Link />
          </el-icon>
          metrics
        </el-link>
      </el-collapse-item>
      <el-collapse-item title="Coordinator Prometheus Metrics" name="4">
        <el-link @click="getCoordinatorPrometheusMetrics" target="_blank">
          <el-icon :style="iconStyle">
            <Link />
          </el-icon>
          prometheus metrics
        </el-link>
      </el-collapse-item>
      <el-collapse-item title="Coordinator Stacks" name="5">
        <el-link @click="getCoordinatorStacks" target="_blank">
          <el-icon :style="iconStyle">
            <Link />
          </el-icon>
          stacks
        </el-link>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import { ref, reactive, computed, onMounted } from 'vue'
import {
  getCoordinatorConf,
  getCoordinatorMetrics,
  getCoordinatorPrometheusMetrics,
  getCoordinatorServerInfo,
  getCoordinatorStacks
} from '@/api/api'
import { useCurrentServerStore } from '@/store/useCurrentServerStore'

export default {
  methods: {getCoordinatorMetrics, getCoordinatorPrometheusMetrics, getCoordinatorStacks},
  setup() {
    const pageData = reactive({
      activeNames: ['1', '2', '3', '4', '5'],
      tableData: [],
      serverInfo: {}
    })
    const currentServerStore = useCurrentServerStore()

    async function getCoordinatorServerConfPage() {
      const res = await getCoordinatorConf()
      pageData.tableData = res.data.data
    }
    async function getCoorServerInfo() {
      const res = await getCoordinatorServerInfo()
      pageData.serverInfo = res.data.data
    }

    /**
     * The system obtains data from global variables and requests the interface to obtain new data after data changes.
     */
    currentServerStore.$subscribe((mutable, state) => {
      if (state.currentServer) {
        getCoordinatorServerConfPage()
        getCoorServerInfo()
      }
    })

    onMounted(() => {
      // If the coordinator address to request is not found in the global variable, the request is not initiated.
      if (currentServerStore.currentServer) {
        getCoordinatorServerConfPage()
        getCoorServerInfo()
      }
    })

    const size = ref('')
    const iconStyle = computed(() => {
      const marginMap = {
        large: '8px',
        default: '6px',
        small: '4px'
      }
      return {
        marginRight: marginMap[size.value] || marginMap.default
      }
    })
    const blockMargin = computed(() => {
      const marginMap = {
        large: '32px',
        default: '28px',
        small: '24px'
      }
      return {
        marginTop: marginMap[size.value] || marginMap.default
      }
    })

    return {
      pageData,
      iconStyle,
      blockMargin,
      size
    }
  }
}
</script>
<style>
.cell-item {
  display: flex;
  align-items: center;
}

.margin-top {
  margin-top: 20px;
}
</style>
