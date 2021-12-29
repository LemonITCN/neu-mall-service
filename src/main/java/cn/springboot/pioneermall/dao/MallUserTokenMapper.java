
package cn.springboot.pioneermall.dao;

import cn.springboot.pioneermall.entity.MallUserToken;

public interface MallUserTokenMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(MallUserToken record);

    int insertSelective(MallUserToken record);

    MallUserToken selectByPrimaryKey(Long userId);

    MallUserToken selectByToken(String token);

    int updateByPrimaryKeySelective(MallUserToken record);

    int updateByPrimaryKey(MallUserToken record);
}