package com.sudokucaptain.automation;

import com.sun.javadoc.Type;

public class Argument {
	private Type type;
	private String name;
	
	public Argument(Type type, String name) {
		super();
		this.type = type;
		this.name = name;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
