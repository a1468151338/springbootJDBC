package zkl.common.entity;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 *分页
 */
@Repository
public class PageBean<T>{
    private int pageSize=10;   //每页大小
    private int begin; //开始
    private int totalPageSize;   //总页数
    private int total;  //总记录数
    private int nowPage=1; //当前页
    private int nextPage;    //下一页
    private int prevPage;   //上一页
    private int[] pageArray ; //页数数组
    private List<T> datas;

    public PageBean(){

    }

    public PageBean(int nowPage, int pageSize){
        this.nowPage = nowPage;
        this.pageSize = pageSize;
    }

    public PageBean(int nowPage, int pageSize, int total){
        this.nowPage = nowPage;
        this.pageSize = pageSize;
        this.total = total;

        if(total%pageSize==0 && total/pageSize!=0){
            this.totalPageSize=total/pageSize;
        }else{
            this.totalPageSize=(total/pageSize)+1;
        }
        if (nowPage<=1){
            this.prevPage=1;
            this.nextPage=nowPage+1;
            this.begin=0;
        }else if (nowPage>=(totalPageSize-1)){
            this.begin=(nowPage-1)*pageSize;
            this.nextPage=totalPageSize;
            this.prevPage=nowPage-1;
        }else{
            this.nextPage=nowPage+1;
            this.prevPage=nowPage-1;
            this.begin=(nowPage-1)*pageSize;
        }
        //页数数组
        if (totalPageSize<=10){
            pageArray = new int[totalPageSize];
            for (int i=0; i<totalPageSize; i++){
                pageArray[i] = i+1;
            }
        }else{
            pageArray = new int[10];
            if (nowPage<=6){
                pageArray[0] = 1;
            }else if (nowPage>=(totalPageSize-4)){
                pageArray[0] = totalPageSize-9;
            }else {
                pageArray[0] = nowPage-5;
            }
            for (int i=1; i<10; i++){
                pageArray[i] = pageArray[i-1]+1;
            }
        }

    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPageSize() {
        return totalPageSize;
    }

    public void setTotalPageSize(int totalPageSize) {
        this.totalPageSize = totalPageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int[] getPageArray() {
        return pageArray;
    }

    public void setPageArray(int[] pageArray) {
        this.pageArray = pageArray;
    }

}
