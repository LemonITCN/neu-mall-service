
package cn.springboot.pioneermall.service;

import cn.springboot.pioneermall.api.mall.param.MallUserUpdateParam;
import cn.springboot.pioneermall.util.PageQueryUtil;
import cn.springboot.pioneermall.util.PageResult;

public interface MallUserService {

    /**
     * 用户注册
     *
     * @param loginName
     * @param password
     * @return
     */
    String register(String loginName, String password);


    /**
     * 登录
     *
     * @param loginName
     * @param passwordMD5
     * @return
     */
    String login(String loginName, String passwordMD5);

    /**
     * 用户信息修改
     *
     * @param mallUser
     * @return
     */
    Boolean updateUserInfo(MallUserUpdateParam mallUser, Long userId);

    /**
     * 登出接口
     * @param userId
     * @return
     */
    Boolean logout(Long userId);

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     *
     * @param ids
     * @param lockStatus
     * @return
     */
    Boolean lockUsers(Long[] ids, int lockStatus);

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getMallUsersPage(PageQueryUtil pageUtil);
}
