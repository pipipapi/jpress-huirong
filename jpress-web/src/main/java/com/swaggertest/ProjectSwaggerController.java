package com.swaggertest;

import io.swagger.annotations.*;

/**
 * @ClassName ProjectSwaggerController
 * @Description TODO
 * @Author daosheng.huang
 * @Date 2018/12/4 22:15
 * @Version 1.0
 */
@Api(tags="项目相关接口")
public class ProjectSwaggerController {
    @ApiOperation(value = "/getProjectById", httpMethod = "GET",
            consumes="application/json;charset=UFT-8",produces="application/json;charset=UFT-8",
            notes = "根据项目id获取项目,更详细的说明")
    @ApiImplicitParams(@ApiImplicitParam(required = true, name = "pid", value = "项目的id",dataType="Long"))
    @ApiResponses({ @ApiResponse(code = 200, message = "Success"),@ApiResponse(code = 400, message = "Invalid Order") })
    public void getProjectById(){

    }

    @ApiOperation(value = "/getProjectByName", httpMethod = "GET",
            consumes="application/json;charset=UFT-8",produces="application/json;charset=UFT-8",
            notes = "根据项目名称获取项目,更详细的说明")
    @ApiImplicitParams(@ApiImplicitParam(required = true, name = "pname", value = "项目的名称",dataType="String"))
    @ApiResponses({ @ApiResponse(code = 200, message = "Success"),@ApiResponse(code = 400, message = "Invalid Order") })
    public void getProjectByName(){

    }
}
