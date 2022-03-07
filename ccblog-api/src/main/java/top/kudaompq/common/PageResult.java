package top.kudaompq.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 封装一个分页数据传输类
 * @author: kudaompq
 * @date: 2022/1/5 17:06
 * @version: v1.0
 */

@Data
public class PageResult<T> implements Serializable {


    /**
     * 全部页数
     */
    private Long totalPage;

    /**
     * 当前页
     */
    private Long currentPage;

    /**
     * 数据总数
     */
    private Long total;

    /**
     * 当前页数据
     */
    private List<T> data;



    public PageResult(IPage page){
        this.currentPage = page.getCurrent();
        this.totalPage = page.getPages();
        this.data = page.getRecords();
        this.total = page.getTotal();
    }

    public static PageResult create(IPage page){
        return new PageResult(page);
    }

}
