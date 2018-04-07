package com.antlr.demo.expr;


import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author shixianwei
 * @date 2018/4/6 15:05
 */
public class ExprJoyRide {

    public static void main(String[] args) throws IOException {
        System.out.println();
        ANTLRInputStream antlrStringStream = new ANTLRInputStream(new FileInputStream(ExprJoyRide.class.getResource("input").getFile()));
        ExprLexer exprLexer = new ExprLexer(antlrStringStream);
        CommonTokenStream tokens = new CommonTokenStream(exprLexer);

        ExprParser parser = new ExprParser(tokens);
        ParseTree tree = parser.prog();

        EvalVisitor eval = new EvalVisitor();
        eval.visit(tree);

    }
}
