
package cn.springboot.pioneermall.api.mall;

import cn.springboot.pioneermall.api.mall.vo.MallIndexCategoryVO;
import cn.springboot.pioneermall.common.MallException;
import cn.springboot.pioneermall.common.ServiceResultEnum;
import cn.springboot.pioneermall.service.MallCategoryService;
import cn.springboot.pioneermall.util.Result;
import cn.springboot.pioneermall.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "v1", tags = "3.商城分类页面接口")
@RequestMapping("/api/v1")
public class MallGoodsCategoryAPI {

    @Resource
    private MallCategoryService mallCategoryService;

    @GetMapping("/categories")
    @ApiOperation(value = "获取分类数据", notes = "分类页面使用")
    public Result<List<MallIndexCategoryVO>> getCategories() {
        List<MallIndexCategoryVO> categories = mallCategoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(categories);
    }
}
