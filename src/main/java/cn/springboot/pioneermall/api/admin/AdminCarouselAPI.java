
package cn.springboot.pioneermall.api.admin;

import cn.springboot.pioneermall.api.admin.param.BatchIdParam;
import cn.springboot.pioneermall.api.admin.param.CarouselAddParam;
import cn.springboot.pioneermall.api.admin.param.CarouselEditParam;
import cn.springboot.pioneermall.common.ServiceResultEnum;
import cn.springboot.pioneermall.config.annotation.TokenToAdminUser;
import cn.springboot.pioneermall.entity.AdminUserToken;
import cn.springboot.pioneermall.entity.Carousel;
import cn.springboot.pioneermall.service.MallCarouselService;
import cn.springboot.pioneermall.util.BeanUtil;
import cn.springboot.pioneermall.util.PageQueryUtil;
import cn.springboot.pioneermall.util.Result;
import cn.springboot.pioneermall.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@Api(value = "v1", tags = "8-1.后台管理系统轮播图模块接口")
@RequestMapping("/manage-api/v1")
public class AdminCarouselAPI {

    private static final Logger logger = LoggerFactory.getLogger(AdminCarouselAPI.class);

    @Resource
    MallCarouselService mallCarouselService;

    /**
     * 列表
     */
    @RequestMapping(value = "/carousels", method = RequestMethod.GET)
    @ApiOperation(value = "轮播图列表", notes = "轮播图列表")
    public Result list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10) {
            return ResultGenerator.genFailResult("分页参数异常！");
        }
        Map params = new HashMap(4);
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(mallCarouselService.getCarouselPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/carousels", method = RequestMethod.POST)
    @ApiOperation(value = "新增轮播图", notes = "新增轮播图")
    public Result save(@RequestBody @Valid CarouselAddParam carouselAddParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        Carousel carousel = new Carousel();
        BeanUtil.copyProperties(carouselAddParam, carousel);
        String result = mallCarouselService.saveCarousel(carousel);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/carousels", method = RequestMethod.PUT)
    @ApiOperation(value = "修改轮播图信息", notes = "修改轮播图信息")
    public Result update(@RequestBody CarouselEditParam carouselEditParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        Carousel carousel = new Carousel();
        BeanUtil.copyProperties(carouselEditParam, carousel);
        String result = mallCarouselService.updateCarousel(carousel);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/carousels/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取单条轮播图信息", notes = "根据id查询")
    public Result info(@PathVariable("id") Integer id, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        Carousel carousel = mallCarouselService.getCarouselById(id);
        if (carousel == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(carousel);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/carousels", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除轮播图信息", notes = "批量删除轮播图信息")
    public Result delete(@RequestBody BatchIdParam batchIdParam, @TokenToAdminUser AdminUserToken adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (batchIdParam == null || batchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (mallCarouselService.deleteBatch(batchIdParam.getIds())) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

}