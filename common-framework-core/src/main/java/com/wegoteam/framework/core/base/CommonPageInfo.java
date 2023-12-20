package com.wegoteam.framework.core.base;

/**
 * @description: 分页参数
 * @author: XUCHANG
 * @time: 2019/12/4 10:51
 */
public class CommonPageInfo {

    public CommonPageInfo(int currentPage, int pageSize) {
        if (currentPage < 1 || pageSize < 1) {
            throw new IllegalArgumentException("currentPage and pageSize must more than 0.");
        }

        this.pageNum = currentPage;
        this.pageSize = pageSize;
    }

    /**
     * 当前页号
     */
    private int pageNum;
    /**
     * 页面大小
     */
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 计算查询的开始行数
     * @return
     */
    public int getStartRow(){
        return (pageNum - 1) * pageSize;
    }
}
