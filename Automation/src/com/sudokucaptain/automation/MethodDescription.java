package com.sudokucaptain.automation;

import java.util.ArrayList;

import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.Type;

public class MethodDescription {
	
	private String name;
	private Type type;
	private ArrayList<Argument> arguments;
	
	public MethodDescription(MethodDoc mDoc) {
		super();
		name = mDoc.name();
		type = mDoc.returnType();
		arguments = new ArrayList<Argument>();
		for(Parameter p : mDoc.parameters())
			arguments.add(new Argument(p.type(), p.name()));
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String methodName) {
		this.name = methodName;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public ArrayList<Argument> getArguments() {
		return arguments;
	}
	
	public void setArguments(ArrayList<Argument> arguments) {
		this.arguments = arguments;
	}
}
