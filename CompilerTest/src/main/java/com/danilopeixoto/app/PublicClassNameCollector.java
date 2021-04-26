package com.danilopeixoto.app;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class PublicClassNameCollector extends VoidVisitorAdapter<List<String>> {
  @Override
  public void visit(ClassOrInterfaceDeclaration declaration, List<String> collector) {
    super.visit(declaration, collector);

    if (declaration.isPublic()) {
      collector.add(declaration.getNameAsString());
    }
  }
}
