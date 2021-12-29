
package cn.springboot.pioneermall.service;

import cn.springboot.pioneermall.api.mall.vo.MallIndexCarouselVO;
import cn.springboot.pioneermall.entity.Carousel;
import cn.springboot.pioneermall.util.PageQueryUtil;
import cn.springboot.pioneermall.util.PageResult;

import java.util.List;

public interface MallCarouselService {

    /**
     * 返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return
     */
    List<MallIndexCarouselVO> getCarouselsForIndex(int number);

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCarouselPage(PageQueryUtil pageUtil);

    String saveCarousel(Carousel carousel);

    String updateCarousel(Carousel carousel);

    Carousel getCarouselById(Integer id);

    Boolean deleteBatch(Long[] ids);
}
