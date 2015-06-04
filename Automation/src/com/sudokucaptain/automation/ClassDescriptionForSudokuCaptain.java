package com.sudokucaptain.automation;

import java.util.*;

import com.sun.javadoc.*;

public class ClassDescriptionForSudokuCaptain {

	private String name;
	private ArrayList<MethodDescription> methods;

	public ClassDescriptionForSudokuCaptain(ClassDoc cDoc) {
		super();
		name = cDoc.name();
		methods = new ArrayList<MethodDescription>();
		for(MethodDoc cDocMethod : cDoc.methods()){
			if(canThisMethodBeAutomated(cDocMethod))
				methods.add(new MethodDescription(cDocMethod));
		}
	}

	public void addMethod(MethodDescription aMethod) {
		methods.add(aMethod);
	}

	public String getName() {
		return name;
	}

	public ArrayList<MethodDescription> getMethods() {
		return methods;
	}

	private static boolean canThisMethodBeAutomated(MethodDoc aMethodDoc) {
		return (aMethodDoc.tags("automatable_method").length != 0);
	}

}
