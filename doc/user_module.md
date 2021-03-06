### 用户模块设计

#### 业务场景

##### 游客
 - 查看首页
 - 试用个税计算器
 - 查看服务商列表
 - 登录/注册为会员
 
##### 企业
 - 企业入驻
 - 员工入驻、离职

##### 会员
 - 完善自然人纳税基本信息 t_natural_person
  - 关联会员
  - 国籍 （中国大陆，港澳台，外籍）
  - 姓名
  - 证件类型（枚举：名称，是否正反）
  - 证件号码
  - 证件照片正
  - 证件照片反
  - 残疾
    - 图片
    - 证件号
  - 烈属
    - 图片
    - 证件号
  - 孤老
    - 图片
    - 证件号
  - 专家或学者
     - 证书号
     - 图片
  - 两院院士
    - 证书号
    - 图片
  - 社保属性（0公司代缴，1个人自行）
  - 更新时间
  - 创建时间
  
 - 收入类型表
   - 类别ID
   - 分类名称：综合所得 
   - 排序
   - 是否前台输入
   - 父分类ID
   - 分类描述
   - 级别
 - 专项扣除分类表
   - 类别ID
   - 分类名称：养老
   - 排序
 - 专项附加扣除分类表
   - 类别ID
   - 分类名称：子女教育
   - 排序
   - 描述
 - 其它扣除减免分类表
    - 类别ID
    - 分类名称：子女教育
    - 排序
    - 描述
 - 报税数据（主）
   - ID
   - 关联会员
   - 申报类型：0 月度申报 1 年度汇缴申报
   - 申报时段：2018-09 / 2018-01:2018-12
   - 创建时间
   - 修改时间
 - 报税数据收入表（副）
    - 报税数据ID
    - 收入分类ID
    - 收入金额
    - createTime
    - updateTime
    - updater
 - 报税数据专项扣除表 （副）
    - 报税数据ID
    - 专项扣除分类ID  
    - 个人缴纳金额
    - 企业缴纳金额
    - 个人缴纳比例
    - 单位缴纳比例
    - createTime
    - updateTime
    - updater
 - 报税数据专项附加扣除表 （副）
    - 报税数据ID
    - 专项附加扣除分类ID
    - 个人缴纳金额
    - 缴纳证明附件
    - createTime
    - updateTime
    - updater
 - 报税数据其它扣除减免表 （副）
    - 报税数据ID
    - 其它扣除减免分类ID
    - 个人缴纳金额
    - 缴纳证明附件
    - createTime
    - updateTime
    - updater
 - 使用个税评估
