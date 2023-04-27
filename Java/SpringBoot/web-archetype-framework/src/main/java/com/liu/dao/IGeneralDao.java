package com.liu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author 刘老师
 * 
 * - 源码请所有同学合理使用，禁止非学习用途。
 * - 参照源码多想多练多Debug，杜绝无脑Copy！
 * - 有问题找学委统一汇总，课堂答疑，也可到办公室问我。
 *
 * 通用 Dao 封装公共属性常量
 */

public interface IGeneralDao<T> {
	
	public static final String PRIMARY_KEY 				= "id";
	public static final String INFO_STUDENT_STATE 		= "info_student_state";
	public static final String ACADEMY_RELEASE = "academy_release";
	public static final String LATE_RETURN = "late_return";
	public static final String STUDENT = "student";
	public static final String OUTSCHOOL = "outschool";


	public T mapResult(final ResultSet rs) throws SQLException;
	
}
