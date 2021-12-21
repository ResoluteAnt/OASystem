package com.gec.domain;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

public class PageBean <T> {
	//{1} 总记录数
	private int count = 0;
	//{2} 当前页的页码
	private int page = 1;
	//{3} 页大小
	private int limit = 10;
	//{4} List<T> 集合
	private List<T> list;
	//{5} JSon 数组
	private JSONArray jsArray;

	public void setList( List<T> _list ){
		this.list = _list;
		//{ps} 将 List 中的 T 数据转入 JsArray 中
		this.jsArray = new JSONArray();
//		print( "+------------------------------------+" );
//		for( T t : _list ){
//			jsArray.add( t );
//			print( t );
//		}
//		print( "+------------------------------------+" );
	}

	private void print(Object o) {
		System.out.println( "{PageBean} "+ o );
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public JSONArray getJsArray() {
		return jsArray;
	}

	public void setJsArray(JSONArray jsArray) {
		this.jsArray = jsArray;
	}

	public List<T> getList() {
		return list;
	}

	@Override
	public String toString() {
		return "PageBean [count=" + count + ", page=" + page +
				", limit=" + limit + ", list="
				+ list + ", jsArray="
				+ jsArray + "]";
	}

}
