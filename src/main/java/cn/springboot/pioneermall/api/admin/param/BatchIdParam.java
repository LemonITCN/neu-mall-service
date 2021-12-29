
package cn.springboot.pioneermall.api.admin.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class BatchIdParam implements Serializable {
    //id数组
    Long[] ids;
}
