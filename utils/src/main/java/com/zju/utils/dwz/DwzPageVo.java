package com.zju.utils.dwz;

import com.zju.utils.Lang;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.LinkedHashMap;
import java.util.Map;

public class DwzPageVo {
    /**
     * 每页显示的数
     */
    private Integer numPerPage = 20;

    private Map<String, String> numPerPages = new LinkedHashMap<String, String>() {{
        put("20", "20");
        put("50", "50");
        put("100", "100");
        put("200", "200");
    }};
    /**
     * 当前页数
     */
    private Integer pageNum;
    /**
     * 排序属性
     */
    private String[] orderField;
    /**
     * 排列方式 desc ace
     */
    private String orderBy;

    /**
     * 总数目
     */
    private long totalCount;

    public Integer getNumPerPage() {
        if (numPerPage == null || numPerPage == 0) {
            numPerPage = 20;
        }
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public Integer getPageNum() {
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        return pageNum - 1;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String[] getOrderField() {
        return orderField;
    }

    public void setOrderField(String[] orderField) {
        this.orderField = orderField;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }


    public Pageable getPageable() {

        Direction dir = Direction.DESC;
        if ("asc".equals(getOrderBy())) {
            dir = Direction.ASC;
        }
        Pageable page = null;
        if (Lang.isEmpty(orderField)) {
            page = new PageRequest(getPageNum(), getNumPerPage(), new Sort(dir, "lastUpdated"));
        } else {
            page = new PageRequest(getPageNum(), getNumPerPage(), new Sort(dir, orderField));
        }
        return page;
    }

    public Map<String, String> getNumPerPages() {
        return numPerPages;
    }


}
