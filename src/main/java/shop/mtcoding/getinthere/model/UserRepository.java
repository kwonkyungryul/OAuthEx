package shop.mtcoding.getinthere.model;

import org.apache.ibatis.annotations.Mapper;
import shop.mtcoding.getinthere.dto.JoinDto;

@Mapper
public interface UserRepository {
    public User findByEmail(String email);

    public int insert(JoinDto joinDto);
}
