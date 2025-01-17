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

package org.apache.dolphinscheduler.api.controller;

import static org.apache.dolphinscheduler.api.enums.Status.LIST_MASTERS_ERROR;
import static org.apache.dolphinscheduler.api.enums.Status.LIST_WORKERS_ERROR;
import static org.apache.dolphinscheduler.api.enums.Status.QUERY_DATABASE_STATE_ERROR;

import org.apache.dolphinscheduler.api.aspect.AccessLogAnnotation;
import org.apache.dolphinscheduler.api.exceptions.ApiException;
import org.apache.dolphinscheduler.api.service.MonitorService;
import org.apache.dolphinscheduler.api.utils.Result;
import org.apache.dolphinscheduler.common.Constants;
import org.apache.dolphinscheduler.dao.entity.User;

import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * monitor controller
 */
@Api(tags = "MONITOR_TAG")
@RestController
@RequestMapping("/monitor")
public class MonitorController extends BaseController {

    @Autowired
    private MonitorService monitorService;

    /**
     * master list
     *
     * @param loginUser login user
     * @return master list
     */
    @ApiOperation(value = "listMaster", notes = "MASTER_LIST_NOTES")
    @GetMapping(value = "/masters")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(LIST_MASTERS_ERROR)
    @AccessLogAnnotation(ignoreRequestArgs = "loginUser")
    public Result listMaster(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser) {
        Map<String, Object> result = monitorService.queryMaster(loginUser);
        return returnDataList(result);
    }

    /**
     * worker list
     *
     * @param loginUser login user
     * @return worker information list
     */
    @ApiOperation(value = "listWorker", notes = "WORKER_LIST_NOTES")
    @GetMapping(value = "/workers")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(LIST_WORKERS_ERROR)
    @AccessLogAnnotation(ignoreRequestArgs = "loginUser")
    public Result listWorker(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser) {
        Map<String, Object> result = monitorService.queryWorker(loginUser);
        return returnDataList(result);
    }

    /**
     * query database state
     *
     * @param loginUser login user
     * @return data base state
     */
    @ApiOperation(value = "queryDatabaseState", notes = "QUERY_DATABASE_STATE_NOTES")
    @GetMapping(value = "/databases")
    @ResponseStatus(HttpStatus.OK)
    @ApiException(QUERY_DATABASE_STATE_ERROR)
    @AccessLogAnnotation(ignoreRequestArgs = "loginUser")
    public Result queryDatabaseState(@ApiIgnore @RequestAttribute(value = Constants.SESSION_USER) User loginUser) {
        Map<String, Object> result = monitorService.queryDatabaseState(loginUser);
        return returnDataList(result);
    }

}
