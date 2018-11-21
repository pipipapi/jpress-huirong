/**
 * Copyright (c) 2016-2019, Michael Yang 杨福海 (fuhai999@gmail.com).
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jpress.web.commons.controller;

import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import io.jboot.utils.FileUtils;
import io.jboot.utils.StrUtils;
import io.jboot.web.controller.annotation.RequestMapping;
import io.jpress.JPressConsts;
import io.jpress.commons.utils.AliyunOssUtils;
import io.jpress.commons.utils.AttachmentUtils;
import io.jpress.model.Attachment;
import io.jpress.service.AttachmentService;
import io.jpress.web.base.UserControllerBase;

import javax.inject.Inject;
import java.net.URL;

/**
 * @author Michael Yang 杨福海 （fuhai999@gmail.com）
 * @version V1.0
 * @Title: 首页
 * @Package io.jpress.web.admin
 */
@RequestMapping("/commons/attachment")
public class AttachmentController extends UserControllerBase {


    @Inject
    private AttachmentService as;


    public void upload() {
        if (!isMultipartRequest()) {
            renderError(404);
            return;
        }

        UploadFile uploadFile = getFile();
        if (uploadFile == null) {
            renderJson(Ret.fail().set("success", false));
            return;
        }

        String path = AttachmentUtils.moveFile(uploadFile).replace("\\", "/");

        boolean enable = Boolean.parseBoolean(JPressConsts.OSS_KEY_ENABLE);
        if (enable == false || StrUtils.isBlank(path)) {
            //不同步到oss,保存到本地
            Attachment attachment = new Attachment();
            attachment.setUserId(getLoginedUser().getId());
            attachment.setTitle(uploadFile.getOriginalFileName());
            attachment.setPath(path);
            attachment.setSuffix(FileUtils.getSuffix(uploadFile.getFileName()));
            attachment.setMimeType(uploadFile.getContentType());
            as.save(attachment);
            renderJson(Ret.ok().set("success", true).set("src", attachment.getPath()));
        }else {
            boolean success = AliyunOssUtils.upload(path, AttachmentUtils.file(path));
            if(!success){
                renderJson(Ret.ok().set("success", false));
            }
            String ossurl = JPressConsts.OSS_KEY_ENDPOINT;
            if(ossurl.contains("http://")){
                ossurl = ossurl.replace("http://","http://"+JPressConsts.OSS_KEY_BUCKETNAME+".");
            }
            if(ossurl.contains("https://")){
                ossurl = ossurl.replace("https://","https://"+JPressConsts.OSS_KEY_BUCKETNAME+".");
            }
            ossurl = ossurl+path;
            renderJson(Ret.ok().set("success", true).set("src", ossurl));
        }


    }


}
