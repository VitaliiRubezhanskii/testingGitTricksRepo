package springajax.service.helper;



public class PageParameter {

    private int pageSize;


    public PageParameter(int pageSize) {
        this.pageSize = pageSize;

    }

    public PageParameter() {
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
