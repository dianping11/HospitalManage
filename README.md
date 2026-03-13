一：需求描述
2. 预约成功后立即发送通知
- 医生审核预约通过时（ shBatch 方法），自动创建就诊通知
- 通知状态初始化为"待发送"
 2. 通知发送和重试机制
- NoticeService.processPendingNotices() ：每分钟执行，发送待发送的通知
- NoticeService.retryFailedNotices() ：每小时执行，重试发送失败的通知
3. 发送记录功能
- 每次发送尝试都记录到 tongzhi_record 表
- 记录发送时间、状态、失败原因、重试次数
- 管理员可在后台查看完整的发送记录
4. 管理员查看与处理
- 就诊通知列表页显示通知状态
- 就诊通知详情页可修改通知状态
- 专门的通知发送记录页面，显示所有发送历史

二、表修改
一、数据库表结构修改
 1. jiuzhentongzhi 表（就诊通知表）
- 新增字段： tongzhizhuangtai VARCHAR(20) - 通知状态（待发送/已发送/发送失败）
 2. tongzhi_record 表（通知发送记录表）【新建】
- 记录每次通知发送的详细信息
- 包含字段：通知ID、通知编号、账号、手机、通知内容、发送时间、发送状态、失败原因、重试次数

三、后端代码修改
修改java的文件  	
JiuzhentongzhiEntity.java	添加 tongzhizhuangtai 字段及getter/setter
YishengyuyueController.java	 shBatch方法中添加审核通过时自动创建就诊通知的逻辑
NoticeService.java	添加发送记录功能，每次发送都记录到tongzhi_record表
新建的文件  	
TongzhiRecordEntity.java	通知发送记录实体类
TongzhiRecordView.java	通知发送记录视图类
TongzhiRecordDao.java	通知发送记录DAO
TongzhiRecordService.java	通知发送记录Service接口
TongzhiRecordServiceImpl.java	通知发送记录Service实现
TongzhiRecordController.java	通知发送记录Controller
四、前端vue代码修改
修改的文件
jiuzhentongzhi/list.vue	列表页添加"通知状态"列显示
jiuzhentongzhi/formModel.vue	表单页添加"通知状态"字段，支持编辑
新建的文件
tongzhi_record/list.vue	通知发送记录列表页，管理员可查看所有发送记录
