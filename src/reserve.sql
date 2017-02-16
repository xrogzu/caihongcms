SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS c_schedule;
DROP TABLE IF EXISTS c_user_schedule;
DROP TABLE IF EXISTS jc_reserve_attachment;
DROP TABLE IF EXISTS jc_reserve;
DROP TABLE IF EXISTS jc_patient;




/* Create Tables */

-- 排班表
CREATE TABLE c_schedule
(
	id int NOT NULL AUTO_INCREMENT COMMENT 'id',
	doctor_id int NOT NULL COMMENT '医生id',
	schedule_time date COMMENT '资源日期',
	price double NOT NULL COMMENT '资源价格',
	counts int NOT NULL COMMENT '可用数量',
	-- 1，可用
	-- 0，停诊
	status tinyint DEFAULT 1 NOT NULL COMMENT '状态 : 1，可用
0，停诊',
	PRIMARY KEY (id),
	UNIQUE (id)
) COMMENT = '排班表';


-- 医生排班
CREATE TABLE c_user_schedule
(
	user_id int NOT NULL COMMENT 'user_id',
	-- 周一到周日坐诊次数
	-- 1,0,1,0，1,5,6,7
	zuozhen_times varchar(200) DEFAULT '0' NOT NULL COMMENT '看诊状态 : 周一到周日坐诊次数
1,0,1,0，1,5,6,7',
	counts int COMMENT '可看诊次数',
	-- 开始时间
	start_date date NOT NULL COMMENT '开始日期 : 开始时间',
	end_date date COMMENT '结束时间',
	-- 坐诊价格
	price double DEFAULT 0 NOT NULL COMMENT '坐诊价格 : 坐诊价格',
	create_time datetime COMMENT '创建时间',
	PRIMARY KEY (user_id),
	UNIQUE (user_id)
) COMMENT = '医生排班';


-- 病人
CREATE TABLE jc_patient
(
	-- id
	id int NOT NULL COMMENT '病人id : id',
	name varchar(50) NOT NULL COMMENT '姓名',
	-- 1,男 2女
	gender tinyint COMMENT '性别 : 1,男 2女',
	telphone varchar(50) COMMENT '手机号',
	birthday varchar(20) COMMENT '出生日期',
	id_no varchar(50) COMMENT '身份证号',
	-- 1，普通员工
	-- 2，农民
	-- 3，工人
	-- 4，其他
	job int COMMENT '职业 : 1，普通员工
2，农民
3，工人
4，其他',
	-- 可以为空
	user_id int COMMENT '所属用户 : 可以为空',
	time datetime COMMENT 'time',
	work_address varchar(200) COMMENT '工作地址',
	home_address varchar(200) COMMENT '家庭地址',
	PRIMARY KEY (id),
	UNIQUE (id)
) COMMENT = '病人';


-- 预约表
CREATE TABLE jc_reserve
(
	-- id
	id int NOT NULL AUTO_INCREMENT COMMENT '病人id : id',
	price double COMMENT '预约价格',
	doctor_id int COMMENT '医生id',
	expect_time datetime COMMENT '期望诊断时间',
	-- 1，申请中
	-- 2，已安排
	-- 3，已诊断
	-- 4，已取消
	status int DEFAULT 1 NOT NULL COMMENT '状态 : 1，申请中
2，已安排
3，已诊断
4，已取消',
	reserve_user_id int NOT NULL COMMENT '预约用户',
	time datetime COMMENT '预约时间',
	-- 1，已支付
	-- 2，未支付
	pay_status tinyint DEFAULT 2 COMMENT '支付状态 : 1，已支付
2，未支付',
	order_num varchar(100) COMMENT '订单号',
	-- 医生诊断
	diagnosis text COMMENT '医生诊断 : 医生诊断',
	clinical_diagnosis text COMMENT '临床诊断',
	-- id
	patient_id int NOT NULL COMMENT '病人id : id',
	PRIMARY KEY (id),
	UNIQUE (id)
) COMMENT = '预约表';


-- 预约附件
CREATE TABLE jc_reserve_attachment
(
	-- id
	reserve_id int NOT NULL COMMENT '预约id : id',
	priority int COMMENT '顺序',
	path varchar(255) NOT NULL COMMENT '文件路径',
	name varchar(255) NOT NULL COMMENT '附件名称',
	filename varchar(255) COMMENT '文件名'
) COMMENT = '预约附件';



/* Create Foreign Keys */

ALTER TABLE jc_reserve
	ADD FOREIGN KEY (patient_id)
	REFERENCES jc_patient (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE jc_reserve_attachment
	ADD FOREIGN KEY (reserve_id)
	REFERENCES jc_reserve (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



