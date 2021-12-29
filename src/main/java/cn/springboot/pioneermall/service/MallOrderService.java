package cn.springboot.pioneermall.service;

import cn.springboot.pioneermall.api.mall.vo.MallOrderDetailVO;
import cn.springboot.pioneermall.api.mall.vo.MallOrderItemVO;
import cn.springboot.pioneermall.api.mall.vo.MallShoppingCartItemVO;
import cn.springboot.pioneermall.entity.MallUser;
import cn.springboot.pioneermall.entity.MallUserAddress;
import cn.springboot.pioneermall.entity.MallOrder;
import cn.springboot.pioneermall.util.PageQueryUtil;
import cn.springboot.pioneermall.util.PageResult;

import java.util.List;

public interface MallOrderService {
    /**
     * 获取订单详情
     *
     * @param orderId
     * @return
     */
    MallOrderDetailVO getOrderDetailByOrderId(Long orderId);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
    MallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);

    /**
     * 我的订单列表
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrders(PageQueryUtil pageUtil);

    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Long userId);

    /**
     * 确认收货
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String finishOrder(String orderNo, Long userId);

    String paySuccess(String orderNo, int payType);

    String saveOrder(MallUser loginMallUser, MallUserAddress address, List<MallShoppingCartItemVO> itemsForSave);

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getMallOrdersPage(PageQueryUtil pageUtil);

    /**
     * 订单信息修改
     *
     * @param mallOrder
     * @return
     */
    String updateOrderInfo(MallOrder mallOrder);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);

    /**
     * 出库
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);

    List<MallOrderItemVO> getOrderItems(Long orderId);
}
