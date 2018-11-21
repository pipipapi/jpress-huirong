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
package io.jpress.commons.utils;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.jfinal.kit.LogKit;
import com.jfinal.log.Log;
import io.jboot.utils.StrUtils;
import io.jpress.JPressConsts;
import io.jpress.JPressOptions;

import java.io.File;

import static io.jpress.JPressConsts.*;


public class AliyunOssUtils {

    static Log log = Log.getLog(AliyunOssUtils.class);


    /**
     * 同步本地文件到阿里云OSS
     *
     * @param path
     * @param file
     * @return
     */
    public static boolean upload(String path, File file) {


        path = removeFileSeparator(path);


        String ossBucketName = JPressConsts.OSS_KEY_BUCKETNAME;
        OSSClient ossClient = newOSSClient();

        try {
            ossClient.putObject(ossBucketName, path, file);
            boolean success = ossClient.doesObjectExist(ossBucketName, path);
            if (!success) {
                LogKit.error("aliyun oss upload error! path:" + path + "\nfile:" + file);
            }
            return success;

        } catch (Throwable e) {
            log.error("aliyun oss upload error!!!", e);
            return false;
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 如果文件以 / 或者 \ 开头，去除 / 或 \ 符号
     */
    private static String removeFileSeparator(String path) {
        while (path.startsWith("/") || path.startsWith("\\")) {
            path = path.substring(1, path.length());
        }
        return path;
    }

    /**
     * 同步 阿里云OSS 到本地
     *
     * @param path
     * @param toFile
     * @return
     */
    public static boolean download(String path, File toFile) {
        boolean enable = Boolean.parseBoolean(JPressConsts.OSS_KEY_ENABLE);

        if (enable == false || StrUtils.isBlank(path)) {
            return false;
        }

        path = removeFileSeparator(path);
        String ossBucketName = JPressConsts.OSS_KEY_BUCKETNAME;
        OSSClient ossClient = newOSSClient();
        try {

            if (!toFile.getParentFile().exists()) {
                toFile.getParentFile().mkdirs();
            }

            if (!toFile.exists()) {
                toFile.createNewFile();
            }
            ossClient.getObject(new GetObjectRequest(ossBucketName, path), toFile);
            return true;
        } catch (Throwable e) {
            log.error("aliyun oss download error!!!  path:" + path + "   toFile:" + toFile, e);
            if (toFile.exists()) {
                toFile.delete();
            }
            return false;
        } finally {
            ossClient.shutdown();
        }
    }

    private static OSSClient newOSSClient() {
        String endpoint = JPressConsts.OSS_KEY_ENDPOINT;
        String accessId = JPressConsts.OSS_KEY_ACCESSKEYID;
        String accessKey = JPressConsts.OSS_KEY_ACCESSKEYSECRET;
        return new OSSClient(endpoint, new DefaultCredentialProvider(accessId, accessKey), null);
    }


}
