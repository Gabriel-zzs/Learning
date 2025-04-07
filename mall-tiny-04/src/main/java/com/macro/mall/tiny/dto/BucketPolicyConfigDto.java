package com.macro.mall.tiny.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * minio Bucket 访问策略配置
 */

@Data
@EqualsAndHashCode
@Builder
public class BucketPolicyConfigDto {

    private String Version;
    private List<Statement> Statements;

    @Data
    @EqualsAndHashCode
    @Builder
    public static class Statement{
        private String Effect;
        private String Action;
        private String Principal;
        private String Resource;
    }

}
