# 技术方案设计

## User Requirements

### 用户

* 学生
* 开发人员
* 老师 (待考虑)

### 功能

* 登入 (通过学校email)
* 提问, 发帖, 回复可添加图片
* 可查询问题, 关注, 按赞, 即收藏
* 可将问题过滤查询 (依据标签)
* 声望值 (通过发文数量, 收集点赞数量, 以及收藏量, 来增加)
* 上传历届考卷即解答, 评论, 看同意次数确定可信程度 (暂定使用爬虫完成)
* 线上聊天室

## 开发流程 (Agile Method)

* 需求分析, 技术选型, 可行性分析
* 设计需求, 原型设计
* 写代码/重复
* 测试
* 部署
* 反馈

## 技术栈

* Vue 前端框架

* SpringBoot 后端框架
* HuTool 工具类
* Sa-Token 用户登入Token, 权限管控
* SpringDoc 文档撰写
* RocketMQ 做消息推送, 削峰
* lombok 精简代码
* Redis 缓存
* MySQL 持久层
* Quartz 定时任务(待定)
* FastDFS 存储用户图片, 以及获取
* Elastic Search 做全局查询
* Nginx 反向代理, 服务器负载均衡

* Docker 容器
