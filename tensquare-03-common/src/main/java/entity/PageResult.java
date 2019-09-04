package entity;

import java.util.List;

/**
 * 分页对象
 * @author Johnny.Chen
 *
 */
public class PageResult<T> {
	
	private Long total;   //总记录数
	private List<T> rows; //当前页数据
	
	
	//无参构造
	public PageResult() {
		super();
	}
	
	//有参构造
	public PageResult(Long total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}


	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
