
package cn.springboot.pioneermall.service;

import cn.springboot.pioneermall.api.mall.vo.MallIndexConfigGoodsVO;
import cn.springboot.pioneermall.entity.IndexConfig;
import cn.springboot.pioneermall.util.PageQueryUtil;
import cn.springboot.pioneermall.util.PageResult;

import java.util.List;

public interface MallIndexConfigService {

    /**
     * 返回固定数量的首页配置商品对象(首页调用)
     *
     * @param number
     * @return
     */
    List<MallIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number);

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getConfigsPage(PageQueryUtil pageUtil);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long id);

    Boolean deleteBatch(Long[] ids);
}
