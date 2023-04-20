import com.dsj.dao.StudentDao;
import com.dsj.pojo.Student;
import com.dsj.util.MybatisFactory;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class StudentTest01 {
    public static void main(String[] args) {
        SqlSession sqlSession = MybatisFactory.getSqlSession();
        StudentDao mapper  = sqlSession.getMapper(StudentDao.class);
        List<Student> list = mapper.queryAll();
        System.out.println(list);
    }
}