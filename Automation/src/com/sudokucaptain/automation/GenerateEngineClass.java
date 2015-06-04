package com.sudokucaptain.automation;

import java.io.StringWriter;
import java.util.ArrayList;

import com.sun.javadoc.*;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class GenerateEngineClass extends Doclet {

	public static boolean start(RootDoc rootDoc) {
		
    	Properties sudokucaptainProjectProperties = new Properties();
		
    	try { sudokucaptainProjectProperties.load(new FileInputStream("sudokucaptain.properties")); } 
		catch (FileNotFoundException e1) { e1.printStackTrace(); } 
		catch (IOException e1) { e1.printStackTrace(); }

		ClassDoc[] classesFromRoot = rootDoc.classes();
		ArrayList<ClassDescriptionForSudokuCaptain> automatableClasses = new ArrayList<ClassDescriptionForSudokuCaptain>();
		Template template = null;

		VelocityContext vContext = new VelocityContext();
		VelocityEngine vEngine = new VelocityEngine();
		vEngine.init();

		for (ClassDoc classFromRoot : classesFromRoot)
			if (canThisClassBeAutomated(classFromRoot))
				automatableClasses.add(new ClassDescriptionForSudokuCaptain(classFromRoot));

		for (ClassDescriptionForSudokuCaptain classDescription : automatableClasses) {
			vContext.put("class", classDescription);
			try {
				template = vEngine.getTemplate("GenerateEngineClass.vsl");
			} catch (ResourceNotFoundException rnfe) {
				// couldn't find the template
			} catch (ParseErrorException pee) {
				// syntax error : problem parsing the template
			} catch (MethodInvocationException mie) {
				// something invoked in the template
				// threw an exception
			} catch (Exception e) {
			}
			
			/*
			 * For a given automatable class we have extracted it's name, and info about it's automatable methods. 
			 * Now we create an Engine class which is derived from the given automatable class. This derived
			 * class is in package ${generatedCodePackageName} and it's Java file will be created inside that directory. 
			 */

			StringWriter sw = new StringWriter();
			template.merge(vContext, sw);
			System.out.println(sw.toString());
			
			try {
				FileWriter fw = new FileWriter(
						sudokucaptainProjectProperties.getProperty("Package_Name_of_Generated_Class")
								+ sudokucaptainProjectProperties.getProperty("Name_Prefix_of_Generated_Class")
								+ classDescription.getName()
								+ ".java");
				fw.write(sw.toString());
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}

	private static boolean canThisClassBeAutomated(ClassDoc aClassDoc) {
		return (aClassDoc.tags("automatable_class").length != 0);
	}

}
