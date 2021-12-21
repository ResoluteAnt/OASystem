package com.gec.domain;

public class TestBean {
	public TestBean(){
		print("+----------------------------------------------+");
		print("");
		print("容器创建了 TestBean [12:55]");
		print("");
		print("+----------------------------------------------+");
	}

	private void print(Object obj) {
		System.out.println( obj );
	}
}
