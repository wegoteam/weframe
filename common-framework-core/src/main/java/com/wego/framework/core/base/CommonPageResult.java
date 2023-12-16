package com.wego.framework.core.base;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: XUCHANG
 * @time: 2019/12/4 10:51
 */
public class CommonPageResult<T> implements java.io.Serializable {
    private static final long serialVersionUID = -3628865867907230918L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 总页数
     */
    private long pageCount;
    /**
     * 当前页号
     */
    private long pageNum;
    /**
     * 页面大小
     */
    private long pageSize;

    /**
     * 数据列表
     */
    private List<T> datas;

    private CommonPageResult(){}

    public CommonPageResult(List<T> list, CommonPageInfo pageQueryRequest, long total){
        CommonPageResult result = build(list, pageQueryRequest, total);
        this.total = result.getTotal();
        this.pageCount = result.getPageCount();
        this.pageNum = result.getPageNum();
        this.pageSize = result.getPageSize();
        this.datas = list;
    }

    /**
     * 构建分页返回的结果集
     * @param list 分页的数据
     * @param pageQueryRequest 分页参数
     * @param total 总数
     * @param <T>
     * @return
     */
    public static <T> CommonPageResult<T> build(List<T> list, CommonPageInfo pageQueryRequest, long total) {

        int pageSize = pageQueryRequest.getPageSize();
        int pageNum = pageQueryRequest.getPageNum();
        if (total < 0 || pageSize <= 0 || pageNum < 0) {
            throw new IllegalArgumentException("total must more than 0");
        }

        CommonPageResult<T> result = new CommonPageResult<>();
        //判断结果集是否分页的数据，是的话，重新构建分页
        if(list.size() == total || list.size() > pageSize){
            List<T> temp = list.stream().skip((pageNum-1) * pageSize).limit(pageSize).collect(Collectors.toList());
            result.setDatas(temp);
        }else {
            result.setDatas(list);
        }
        result.setTotal(total);

        result.setPageSize(pageSize);
        result.setPageNum(pageNum);
        //判断分页的页数
        if (total == 0) {
            result.setPageCount(0);
        } else {
            if (total % pageSize > 0) {
                result.setPageCount(total / pageSize + 1);
            } else {
                result.setPageCount(total / pageSize);
            }
        }

        return result;
    }


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
}
