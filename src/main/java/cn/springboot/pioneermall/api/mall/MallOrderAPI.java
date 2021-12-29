
package cn.springboot.pioneermall.api.mall;

import cn.springboot.pioneermall.api.mall.param.SaveOrderParam;
import cn.springboot.pioneermall.api.mall.vo.MallOrderDetailVO;
import cn.springboot.pioneermall.api.mall.vo.MallOrderListVO;
import cn.springboot.pioneermall.api.mall.vo.MallShoppingCartItemVO;
import cn.springboot.pioneermall.common.Constants;
import cn.springboot.pioneermall.common.MallException;
import cn.springboot.pioneermall.common.ServiceResultEnum;
import cn.springboot.pioneermall.config.annotation.TokenToMallUser;
import cn.springboot.pioneermall.entity.MallUser;
import cn.springboot.pioneermall.entity.MallUserAddress;
import cn.springboot.pioneermall.service.MallOrderService;
import cn.springboot.pioneermall.service.MallShoppingCartService;
import cn.springboot.pioneermall.service.MallUserAddressService;
import cn.springboot.pioneermall.util.PageQueryUtil;
import cn.springboot.pioneermall.util.PageResult;
import cn.springboot.pioneermall.util.Result;
import cn.springboot.pioneermall.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "v1", tags = "7.商城订单操作相关接口")
@RequestMapping("/api/v1")
public class MallOrderAPI {

    @Resource
    private MallShoppingCartService mallShoppingCartService;
    @Resource
    private MallOrderService mallOrderService;
    @Resource
    private MallUserAddressService mallUserAddressService;

    @PostMapping("/saveOrder")
    @ApiOperation(value = "生成订单接口", notes = "传参为地址id和待结算的购物项id数组")
    public Result<String> saveOrder(@ApiParam(value = "订单参数") @RequestBody SaveOrderParam saveOrderParam, @TokenToMallUser MallUser loginMallUser) {
        int priceTotal = 0;
        if (saveOrderParam == null || saveOrderParam.getCartItemIds() == null || saveOrderParam.getAddressId() == null) {
            MallException.fail(ServiceResultEnum.PARAM_ERROR.getResult());
        }
        if (saveOrderParam.getCartItemIds().length < 1) {
            MallException.fail(ServiceResultEnum.PARAM_ERROR.getResult());
        }
        List<MallShoppingCartItemVO> itemsForSave = mallShoppingCartService.getCartItemsForSettle(Arrays.asList(saveOrderParam.getCartItemIds()), loginMallUser.getUserId());
        if (CollectionUtils.isEmpty(itemsForSave)) {
            //无数据
            MallException.fail("参数异常");
        } else {
            //总价
            for (MallShoppingCartItemVO mallShoppingCartItemVO : itemsForSave) {
                priceTotal += mallShoppingCartItemVO.getGoodsCount() * mallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                MallException.fail("价格异常");
            }
            MallUserAddress address = mallUserAddressService.getMallUserAddressById(saveOrderParam.getAddressId());
            if (!loginMallUser.getUserId().equals(address.getUserId())) {
                return ResultGenerator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
            }
            //保存订单并返回订单号
            String saveOrderResult = mallOrderService.saveOrder(loginMallUser, address, itemsForSave);
            Result result = ResultGenerator.genSuccessResult();
            result.setData(saveOrderResult);
            return result;
        }
        return ResultGenerator.genFailResult("生成订单失败");
    }

    @GetMapping("/order/{orderNo}")
    @ApiOperation(value = "订单详情接口", notes = "传参为订单号")
    public Result<MallOrderDetailVO> orderDetailPage(@ApiParam(value = "订单号") @PathVariable("orderNo") String orderNo, @TokenToMallUser MallUser loginMallUser) {
        return ResultGenerator.genSuccessResult(mallOrderService.getOrderDetailByOrderNo(orderNo, loginMallUser.getUserId()));
    }

    @GetMapping("/order")
    @ApiOperation(value = "订单列表接口", notes = "传参为页码")
    public Result<PageResult<List<MallOrderListVO>>> orderList(@ApiParam(value = "页码") @RequestParam(required = false) Integer pageNumber,
                                                               @ApiParam(value = "订单状态:0.待支付 1.待确认 2.待发货 3:已发货 4.交易成功") @RequestParam(required = false) Integer status,
                                                               @TokenToMallUser MallUser loginMallUser) {
        Map params = new HashMap(8);
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        params.put("userId", loginMallUser.getUserId());
        params.put("orderStatus", status);
        params.put("page", pageNumber);
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装分页请求参数
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(mallOrderService.getMyOrders(pageUtil));
    }

    @PutMapping("/order/{orderNo}/cancel")
    @ApiOperation(value = "订单取消接口", notes = "传参为订单号")
    public Result cancelOrder(@ApiParam(value = "订单号") @PathVariable("orderNo") String orderNo, @TokenToMallUser MallUser loginMallUser) {
        String cancelOrderResult = mallOrderService.cancelOrder(orderNo, loginMallUser.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    @PutMapping("/order/{orderNo}/finish")
    @ApiOperation(value = "确认收货接口", notes = "传参为订单号")
    public Result finishOrder(@ApiParam(value = "订单号") @PathVariable("orderNo") String orderNo, @TokenToMallUser MallUser loginMallUser) {
        String finishOrderResult = mallOrderService.finishOrder(orderNo, loginMallUser.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(finishOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(finishOrderResult);
        }
    }

    @GetMapping("/paySuccess")
    @ApiOperation(value = "模拟支付成功回调的接口", notes = "传参为订单号和支付方式")
    public Result paySuccess(@ApiParam(value = "订单号") @RequestParam("orderNo") String orderNo, @ApiParam(value = "支付方式") @RequestParam("payType") int payType) {
        String payResult = mallOrderService.paySuccess(orderNo, payType);
        if (ServiceResultEnum.SUCCESS.getResult().equals(payResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(payResult);
        }
    }

}
