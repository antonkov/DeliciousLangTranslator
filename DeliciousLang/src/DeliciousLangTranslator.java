import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by antonkov on 4/21/14.
 */
public class DeliciousLangTranslator extends DeliciousLangBaseVisitor<String> {
    PrintWriter out;
    String builtin, includes, functions = "", prog = "";
    List<String> exprStrings;
    Set<String> vars = new TreeSet<String>();
    Set<String> defFuncs = new TreeSet<String>();
    boolean addReturn = false;
    String funRetType;

    public DeliciousLangTranslator(String sourcename) throws IOException {
        File f = new File(sourcename);
        if (f.getParentFile() != null)
            f.getParentFile().mkdirs();
        f.createNewFile();
        out = new PrintWriter(f);
        String[] types = {"int", "double"};
        String[] specs = {"d", "lf"};
        includes = "#include \"stdlib.h\"\n" +
                "#include \"stdio.h\"\n" +
                "#include \"math.h\"\n" +
                "#include \"time.h\"\n" +
                "\n";
        builtin = "";
        builtin += "double _eps = 1e-7;\n";
        for (int i = 0; i < types.length; i++) {
            String type = types[i];
            String spec = specs[i];
            String readType =
            "double read" + type + "() {\n" +
            "   double res;\n" +
            "   " + type + " tmp;\n" +
            "   scanf(\"%" + spec + "\", &tmp);\n" +
            "   res = tmp;\n" +
            "   return res;\n" +
            "}\n";
            builtin += readType + "\n";
        }

        builtin +=
        "void print(double a) {\n" +
        "   if (fabs(a - (int)(a + _eps)) < _eps)\n" +
        "       printf(\"%d\", (int)a);\n" +
        "else\n" +
        "   printf(\"%lf\", a);\n" +
        "}\n\n";
        builtin +=
        "double acc_pow(double a, double n) {\n" +
        "   if (fabs(n - (int)(n + _eps)) < _eps && n <= 1000) {\n" +
        "       double res = 1;\n" +
        "       for (int i = 0; i < n; ++i)\n" +
        "           res *= a;\n" +
        "       return res;\n" +
        "   } else\n" +
        "       return pow(a, n);\n" +
        "}\n\n";
    }

    @Override
    public String visitProg(@NotNull DeliciousLangParser.ProgContext ctx) {
        prog = super.visitProg(ctx);
        String result = includes + builtin + functions + "int main() {\n" + prog + "}";
        out.println(result);
        out.close();
        return result;
    }

    @Override
    public String visitListStat(@NotNull DeliciousLangParser.ListStatContext ctx) {
        String result = "";
        for (DeliciousLangParser.StatContext stctx : ctx.stat()) {
            result += visitStat(stctx);
        }
        return result;
    }

    @Override
    public String visitStat(@NotNull DeliciousLangParser.StatContext ctx) {
        return super.visitStat(ctx) + ";\n";
    }

    @Override
    public String visitIfstat(@NotNull DeliciousLangParser.IfstatContext ctx) {
        String result = "if (" + visit(ctx.cond(0)) + ") {\n" + visit(ctx.listStat(0)) + "}";
        List<DeliciousLangParser.CondContext> conds = ctx.cond();
        List<DeliciousLangParser.ListStatContext> stats = ctx.listStat();
        for (int i = 1; i < conds.size(); ++i) {
            result += " else if (" + visit(conds.get(i)) + ") {\n" + visit(stats.get(i)) + "}";
        }
        if (stats.size() > conds.size())
            result += " else {\n" + visit(stats.get(stats.size() - 1)) + "}";
        return result;
    }

    @Override
    public String visitWhilestat(@NotNull DeliciousLangParser.WhilestatContext ctx) {
        String result = "while (" + visit(ctx.cond()) + ") {\n" + visit(ctx.listStat()) + "}";
        return result;
    }

    @Override
    public String visitFuncdef(@NotNull DeliciousLangParser.FuncdefContext ctx) {
        Set<String> oldVars = new TreeSet<String>(vars);
        vars = new TreeSet<String>();
        defFuncs.add(ctx.ID().getText());

        String result = "";
        boolean fun = ctx.rettype.getText().equals("fun");
        String args = "";
        for (TerminalNode id : ctx.listID().ID()) {
            if (!args.equals(""))
                args += ", ";
            args += "double " + id.getText();
            vars.add(id.getText());
        }
        funRetType = "double ";
        result += ctx.ID().getText() + "(" + args + ") {\n";
        if (ctx.listStat() != null) {
            int count = ctx.listStat().stat().size();
            for (int i = 0; i < count; i++) {
                if (i == count - 1 && fun)
                    addReturn = true;
                result += visit(ctx.listStat().stat(i)) + "\n";
                addReturn = false;
            }
        } else {
            if (fun)
                addReturn = true;
            result += visit(ctx.stat());
            addReturn = false;
        }
        result += "}\n\n";
        result = (fun ? funRetType : "void ") + result;
        functions += result;

        vars = new TreeSet<String>(oldVars);
        return "";
    }

    @Override
    public String visitAssign(@NotNull DeliciousLangParser.AssignContext ctx) {
        List<String> exprStrings = new ArrayList<String>();
        for (DeliciousLangParser.ExprContext expctx : ctx.listExpr().expr()) {
            exprStrings.add(visit(expctx));
        }
        int countIDs = ctx.listID().ID().size(), countExprs = ctx.listExpr().expr().size();
        if (countIDs != countExprs)
            throw new AssertionError("Count of IDs and Exprs not equal in multiple assignment");
        String result = "";
        List<TerminalNode> ids = ctx.listID().ID();
        for (int i = 0; i < ids.size(); ++i) {
            String name = ids.get(i).getText();
            if (!vars.contains(name)) {
                result += "double " + name + "= 0;\n";
                vars.add(name);
            }
        }
        result += "{\n";
        for (int i = 0; i < ids.size(); ++i) {
            String name = ids.get(i).getText();
            result += "\tdouble t_" + name + " = " + exprStrings.get(i) + ";\n";
        }
        for (int i = 0; i < ids.size(); ++i) {
            String name = ids.get(i).getText();
            result += "\t" + name + " = " + "t_" + name + ";\n";
        }
        result += "}";
        return result;
    }

    @Override
    public String visitListID(@NotNull DeliciousLangParser.ListIDContext ctx) {
        String res = "";
        for (TerminalNode tn : ctx.ID()) {
            if (res != "")
                res += ", ";
            res += tn.getText();
        }
        return res;
    }

    @Override
    public String visitListExpr(@NotNull DeliciousLangParser.ListExprContext ctx) {
        String res = "";
        for (DeliciousLangParser.ExprContext expctx : ctx.expr()) {
            if (res != "")
                res += ", ";
            res += visit(expctx);
        }
        return res;
    }

    @Override
    public String visitShortIf(@NotNull DeliciousLangParser.ShortIfContext ctx) {
        return "(" + visit(ctx.cond()) + " ? " + visit(ctx.exprNum(0)) + " : " + visit(ctx.exprNum(1)) + ")";
    }

    @Override
    public String visitExpr(@NotNull DeliciousLangParser.ExprContext ctx) {
        String prefix = addReturn ? "return " : "";
        boolean saverAddReturn = addReturn;
        if (ctx.exprNum() != null || ctx.exprStr() != null) {
            String res = "";
            if (addReturn)
            {
                if (ctx.exprNum() != null)
                    funRetType = "double ";
                else
                    funRetType = "char * ";
                addReturn = false;
            }
            res = super.visitExpr(ctx);
            addReturn = saverAddReturn;
            return prefix + res;
        }
        String args = "";
        String name = ctx.ID().getText();
        if (ctx.listExpr() != null) {
            addReturn = false;
            args += visit(ctx.listExpr());
            addReturn = saverAddReturn;
            return prefix + name + "(" + args + ")";
        }
        if (vars.contains(name))
            return prefix + name;
        return prefix + name + "()";
    }

    @Override
    public String visitExprStr(@NotNull DeliciousLangParser.ExprStrContext ctx) {
        if (ctx.STR() != null)
            return ctx.STR().getText();
        else
            return "(" + visit(ctx.cond()) + " ? " + visit(ctx.exprStr(0)) + " : " + visit(ctx.exprStr(1)) + ")";
    }

    @Override
    public String visitParen(@NotNull DeliciousLangParser.ParenContext ctx) {
        return "(" + visit(ctx.exprNum()) + ")";
    }

    @Override
    public String visitBinCond(@NotNull DeliciousLangParser.BinCondContext ctx) {
        String op = ctx.op.getText();
        if (op.equals("="))
            op = "==";
        return visit(ctx.exprNum(0)) + op + visit(ctx.exprNum(1));
    }

    @Override
    public String visitOrXorBool(@NotNull DeliciousLangParser.OrXorBoolContext ctx) {
        String op = ctx.op.getText();
        if (op.equals("or"))
            op = " || ";
        else
            op = " ^ ";
        return '(' + visit(ctx.cond(0)) + ')' + op + '(' + visit(ctx.cond(1)) + ')';
    }

    @Override
    public String visitAndBool(@NotNull DeliciousLangParser.AndBoolContext ctx) {
        return '(' + visit(ctx.cond(0)) + ')' + " && " + '(' + visit(ctx.cond(1)) + ')';
    }

    @Override
    public String visitParenCond(@NotNull DeliciousLangParser.ParenCondContext ctx) {
        return '(' + visit(ctx.cond()) + ')';
    }

    @Override
    public String visitUnarBool(@NotNull DeliciousLangParser.UnarBoolContext ctx) {
        return "!" + '(' + visit(ctx.cond()) + ')';
    }

    @Override
    public String visitAriphmPr0(@NotNull DeliciousLangParser.AriphmPr0Context ctx) {
        return "acc_pow(" + visit(ctx.exprNum(0)) + ", " + visit(ctx.exprNum(1)) + ")";
    }

    @Override
    public String visitAriphmPr1(@NotNull DeliciousLangParser.AriphmPr1Context ctx) {
        String op = ctx.op.getText();
        String ex0 = visit(ctx.exprNum(0)), ex1 = visit(ctx.exprNum(1));
        if (op.equals("mod") || op.equals("div")) {
            op = op.equals("mod") ? "%" : "/";
            String intRep = "(int)(" + ex0 + ") " + op + " (int)(" + ex1 + ")";
            return "(double)(" + intRep + ")";
        } else {
            return ex0 + op + ex1;
        }
    }

    @Override
    public String visitAriphmPr2(@NotNull DeliciousLangParser.AriphmPr2Context ctx) {
        return visit(ctx.exprNum(0)) + ctx.op.getText() + visit(ctx.exprNum(1));
    }

    @Override
    public String visitInt(@NotNull DeliciousLangParser.IntContext ctx) {
        return ctx.INT().getText();
    }

    @Override
    public String visitVar(@NotNull DeliciousLangParser.VarContext ctx) {
        return ctx.ID().getText();
    }

    @Override
    public String visitFunccall(@NotNull DeliciousLangParser.FunccallContext ctx) {
        String args = "";
        String name = ctx.ID().getText();
        if (ctx.listExpr() != null) {
            args += visit(ctx.listExpr());
        }
        return name + "(" + args + ")";
    }
}
