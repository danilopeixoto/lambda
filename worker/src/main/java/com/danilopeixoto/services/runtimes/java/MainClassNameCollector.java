package com.danilopeixoto.services.runtimes.java;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.logging.log4j.util.Strings;

public class MainClassNameCollector extends VoidVisitorAdapter<String> {
  @Override
  public void visit(ClassOrInterfaceDeclaration declaration, String className) {
    super.visit(declaration, className);

    if (className.equals(Strings.EMPTY) && declaration.isPublic()) {
      className = declaration.getNameAsString();
    }
  }
}
