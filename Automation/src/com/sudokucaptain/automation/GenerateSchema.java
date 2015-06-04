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

public class GenerateSchema extends Doclet {

	public static boolean start(RootDoc rootDoc) {

		ClassDoc[] classesFromRoot = rootDoc.classes();
		ArrayList<ClassDescriptionForSudokuCaptain> automatableClasses = new ArrayList<ClassDescriptionForSudokuCaptain>();
		Template template = null;

		VelocityContext vContext = new VelocityContext();
		VelocityEngine vEngine = new VelocityEngine();
		vEngine.init();

		for (ClassDoc classFromRoot : classesFromRoot)
			if (isThisClassAnEngine(classFromRoot))
				automatableClasses.add(new ClassDescriptionForSudokuCaptain(classFromRoot));

		for (ClassDescriptionForSudokuCaptain classDescription : automatableClasses) {
			vContext.put("class", classDescription);
			try {
				template = vEngine.getTemplate("GenerateSchema.vsl");
			} catch (ResourceNotFoundException rnfe) {
				// couldn't find the template
			} catch (ParseErrorException pee) {
				// syntax error : problem parsing the template
			} catch (MethodInvocationException mie) {
				// something invoked in the template
				// threw an exception
			} catch (Exception e) {
			}

			StringWriter sw = new StringWriter();

			template.merge(vContext, sw);

			System.out.println(sw.toString());
		}

		return true;
	}

	private static boolean isThisClassAnEngine(ClassDoc aClassDoc) {
		return (aClassDoc.tags("@automatable_engine").length != 0);
	}

}