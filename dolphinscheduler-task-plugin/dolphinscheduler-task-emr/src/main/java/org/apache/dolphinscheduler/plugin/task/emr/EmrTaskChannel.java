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

package org.apache.dolphinscheduler.plugin.task.emr;

import org.apache.dolphinscheduler.plugin.task.api.AbstractTask;
import org.apache.dolphinscheduler.plugin.task.api.TaskChannel;
import org.apache.dolphinscheduler.plugin.task.api.TaskExecutionContext;
import org.apache.dolphinscheduler.plugin.task.api.parameters.AbstractParameters;
import org.apache.dolphinscheduler.plugin.task.api.parameters.ParametersNode;
import org.apache.dolphinscheduler.plugin.task.api.parameters.resource.ResourceParametersHelper;
import org.apache.dolphinscheduler.spi.utils.JSONUtils;

public class EmrTaskChannel implements TaskChannel {

    @Override
    public void cancelApplication(boolean status) {
        // no need
    }

    @Override
    public AbstractTask createTask(TaskExecutionContext taskRequest) {
        EmrParameters emrParameters = JSONUtils.parseObject(taskRequest.getTaskParams(), EmrParameters.class);
        assert emrParameters != null;
        if (ProgramType.RUN_JOB_FLOW.equals(emrParameters.getProgramType())) {
            return new EmrJobFlowTask(taskRequest);
        } else if (ProgramType.ADD_JOB_FLOW_STEPS.equals(emrParameters.getProgramType())) {
            return new EmrAddStepsTask(taskRequest);
        } else {
            throw new IllegalArgumentException("Unsupported program type: " + emrParameters.getProgramType());
        }
    }

    @Override
    public AbstractParameters parseParameters(ParametersNode parametersNode) {
        return JSONUtils.parseObject(parametersNode.getTaskParams(), EmrParameters.class);
    }

    @Override
    public ResourceParametersHelper getResources(String parameters) {
        return null;
    }
}
