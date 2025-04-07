package com.macro.mall.tiny.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.dto.BucketPolicyConfigDto;
import com.macro.mall.tiny.dto.MinioUploadDto;
import io.minio.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;

@RestController
@Api(tags = "MinioController")
@RequestMapping(value = "/minio")
public class MinioController {

    private static final Logger logger = LoggerFactory.getLogger(MinioController.class.getName());

    @Value("${minio.endpoint}")
    private String ENDPOINT;
    @Value("${minio.accessKey}")
    private String ACCESS_KEY;
    @Value("${minio.secretKey}")
    private String SECRET_KEY;
    @Value("${minio.bucketName}")
    private String BUCKET_NAME;

    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/upload")
    public CommonResult upload(@RequestParam("file") MultipartFile file) {
        try {
//            创建一个minio的java客户端
            MinioClient minioClient=MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY).build();
            boolean isExist=minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
            if(!isExist) {
                logger.info("存储桶已经存在");
            }else {
//                创建存储桶并设置只读权限
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(BUCKET_NAME).build());
                BucketPolicyConfigDto bucketPolicyConfigDto=createBucketPolicyConfigDto(BUCKET_NAME);
                SetBucketPolicyArgs setBucketPolicyArgs=SetBucketPolicyArgs.builder()
                        .bucket(BUCKET_NAME)
                        .config(JSONUtil.toJsonStr(bucketPolicyConfigDto))
                        .build();
                minioClient.setBucketPolicy(setBucketPolicyArgs);
            }
            String fileName=file.getOriginalFilename();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
//            设置存储对象名称
            String objectName=sdf.format(new Date())+"/"+fileName;
//            使用putObject上传一个文件到存储桶中
            PutObjectArgs putObjectArgs=PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(),file.getSize(),ObjectWriteArgs.MIN_MULTIPART_SIZE).build();
            minioClient.putObject(putObjectArgs);
            logger.info("文件上传成功");
            MinioUploadDto minioUploadDto=new MinioUploadDto();
            minioUploadDto.setName(fileName);
            minioUploadDto.setUrl(ENDPOINT+"/"+BUCKET_NAME+"/"+objectName);
            return CommonResult.success(minioUploadDto);
        }catch (Exception e) {
            e.printStackTrace();
            logger.info("上传发生错误：{}！"+ e.getMessage());
        }
        return CommonResult.failed();
    }

    private BucketPolicyConfigDto createBucketPolicyConfigDto(String bucketName) {
        BucketPolicyConfigDto.Statement statement=BucketPolicyConfigDto.Statement.builder()
                .Effect("Allow")
                .Principal("*")
                .Action("s3:GetObject")
                .Resource("arn:aws:s3:::"+bucketName+"/*.**").build();
        return BucketPolicyConfigDto.builder()
                .Version("2012-10-17")
                .Statements(CollUtil.toList(statement))
                .build();
    }

    @ApiOperation("文件删除")
    @PostMapping(value = "/delete")
    public CommonResult delete(@RequestParam("objectName") String objectName ) {
        try {
            MinioClient minioClient=MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY).build();
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(BUCKET_NAME).object(objectName).build());
            return CommonResult.success(null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CommonResult.failed();
    }
}
