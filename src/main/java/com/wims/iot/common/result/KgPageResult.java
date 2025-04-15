package com.wims.iot.common.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class KgPageResult<T> implements Serializable {

    private String code;

    private Data<T> data;

    private String message;

    public static <T> KgPageResult<T> success(IPage<T> page) {
        KgPageResult<T> result = new KgPageResult<>();
        result.setCode(KgpResultCode.SYSTEM_EXECUTION_SUCCESS.getCode());
        result.setMessage(KgpResultCode.SYSTEM_EXECUTION_SUCCESS.getMsg());
        Data data = new Data<T>();
        data.setList(page.getRecords());
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(page.getCurrent());
        pagination.setPageSize(page.getSize());
        pagination.setTotalItems(page.getTotal());
        pagination.setTotalPages(page.getPages());
        data.setPagination(pagination);
        result.setData(data);
        return result;
    }

    @lombok.Data
    public static class Data<T> {

        private List<T> list;

        private Pagination pagination;

    }

    @lombok.Data
    public static class Pagination {

        private long currentPage;

        private long pageSize;

        private long totalItems;

        private long totalPages;

    }


}
