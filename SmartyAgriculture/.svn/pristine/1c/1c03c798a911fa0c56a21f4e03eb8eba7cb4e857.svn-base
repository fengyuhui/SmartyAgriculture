package cn.bupt.smartyagl.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {

    /** 保存从数据库中读取出的全部数�?*/
    private List list;
    /** 保存每一页的信息 */
    private List pageList = new ArrayList();
    /** 保存当前�?*/
    private List pageNow;
    /** 当前的页�?*/
    private int pageNo = 1;
    /** �?��有多少页 */
    private int pageMax;
    /** 每一页显示多少条记录 */
    private int pageRowNum;

    public PageUtil(List list, int pageRowNum) {
        this.list = list;// 获取到数据库的全部信�?
        this.pageRowNum = pageRowNum;// 获取每一页要显示多少条记�?
        if (!list.isEmpty()) {
            splitpage();// 计算分页算法
            getNowList();
        }// 取出每一页的数据
    }

    /**
     * 分页算法，得出最大的页数以及每一页存放的数据
     */
    public void splitpage() {
        // 得出�?��有多少条记录
        int size = list.size();
        // 这个if语句主要是判断取整的问题，如果�?得条数除以每�?��的显示的条数�?的话
        // 那么�?��可以分为pageMax页，如果不为0，那么要记得加上1�?
        // 不然的话就显示不了最后一页的信息�?
        if (size % pageRowNum == 0) {
            pageMax = size / pageRowNum;
        } else {
            pageMax = size / pageRowNum + 1;
        }
        int index = 0;
        // 这里是先取出�?��中的要显示的条数放到�?��List集合�?
        // 然后再使用List保存每一�?
        for (int i = 0; i < pageMax; i++) {
            ArrayList tlist = new ArrayList();
            // 这个循环是保存每�?��的信�?
            for (int j = 0; j < pageRowNum; j++) {
                if (index < size) {
                    tlist.add(list.get(index++));
                } else {
                    break;
                }
            }
            // 将每�?��的信息保存到List集合�?
            pageList.add(tlist);
        }
    }

    /**
     * 取出每一页的数据
     */
    public void getNowList() {
        int n = pageNo - 1;
        this.pageNow = (ArrayList) pageList.get(n);
    }

    /**
     * 第一�?
     */
    public void setFirstPage() {
        this.pageNo = 1;
        getNowList();
    }

    /**
     * 前一�?
     */
    public void setPrivousPage() {
        if (this.pageNo > 1) {
            this.pageNo--;
        }
        getNowList();
    }

    /**
     * 下一�?
     */
    public void setNextPage() {
        if (this.pageNo < this.pageMax) {
            this.pageNo++;
        }
        getNowList();
    }

    /**
     * �?���?��
     */
    public void setLastPage() {
        this.pageNo = this.pageMax;
        getNowList();
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public List getPageList() {
        return pageList;
    }

    public void setPageList(List pageList) {
        this.pageList = pageList;
    }

    public List getPageNow() {
        return pageNow;
    }

    public void setPageNow(List pageNow) {
        this.pageNow = pageNow;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        getNowList();
    }

    public int getPageMax() {
        return pageMax;
    }

    public void setPageMax(int pageMax) {
        this.pageMax = pageMax;
    }

    public int getPageRowNum() {
        return pageRowNum;
    }

    public void setPageRowNum(int pageRowNum) {
        this.pageRowNum = pageRowNum;
    }
}