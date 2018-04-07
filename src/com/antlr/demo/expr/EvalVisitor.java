package com.antlr.demo.expr;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shixianwei
 * @date 2018/4/6 15:56
 */
public class EvalVisitor extends ExprBaseVisitor<Integer>{

    //存放变量名和对应关系
    Map<String,Integer> memory = new HashMap<>();
    @Override
    public Integer visitProg(ExprParser.ProgContext ctx) {
        return super.visitProg(ctx);
    }

    //  expr NEWLINE            #   printExpr
    @Override
    public Integer visitPrintExpr(ExprParser.PrintExprContext ctx) {
        Integer value = visit(ctx.expr()); //计算 expr 子节点的值
        System.out.println(value);      //直接打印结果
        return 0; //随便返回
    }

    //    |   ID '=' expr NEWLINE     #   assign
    @Override
    public Integer visitAssign(ExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        Integer value = visit(ctx.expr()); //计算右侧表达式的值
        memory.put(id,value);//将变量映射关系存储到内存中
        return value;   //返回结果
    }

    @Override
    public Integer visitBlank(ExprParser.BlankContext ctx) {
        return super.visitBlank(ctx);
    }

    //expr ('*'|'/') expr
    @Override
    public Integer visitMulDiv(ExprParser.MulDivContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == ExprParser.MUL) return left * right;
        return left / right;
    }

    // expr op=('+'|'-') expr
    @Override
    public Integer visitAddSub(ExprParser.AddSubContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == ExprParser.ADD) return left + right;
        return left - right;
    }

    // ID
    @Override
    public Integer visitId(ExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) return memory.get(id);
        return 0;
    }

    // INT
    @Override
    public Integer visitInt(ExprParser.IntContext ctx) {
        return Integer.valueOf(ctx.INT().getText());
    }

    @Override
    public Integer visitParents(ExprParser.ParentsContext ctx) {
        return visit(ctx.expr()); //返回子表达式的
    }
}
