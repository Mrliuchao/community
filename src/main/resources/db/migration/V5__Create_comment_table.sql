CREATE TABLE `comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父类ID',
  `type` int(255) NULL DEFAULT NULL COMMENT '父类类型',
   `commentatsor` int(11) NULL DEFAULT NULL COMMENT '评论人ID',
  `gmt_create` bigint(255) NULL DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` bigint(255) NULL DEFAULT NULL COMMENT '更新时间',
  `like_count` bigint(255) NULL DEFAULT NULL COMMENT '点赞次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;