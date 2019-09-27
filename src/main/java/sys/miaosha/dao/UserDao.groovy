package sys.miaosha.dao

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository
import sys.miaosha.domain.User

@Mapper
@Repository
public interface UserDao {

        @Select("select * from user where id=#{id}")
        public User getUser(@Param("id")int id);

        @Insert("insert into user(id , name)value(#{id},#{name})")
        public int insert(User user);
}