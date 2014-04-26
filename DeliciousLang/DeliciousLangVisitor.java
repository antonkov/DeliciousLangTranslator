// Generated from DeliciousLang.g4 by ANTLR 4.2.2
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link DeliciousLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface DeliciousLangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#listExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListExpr(@NotNull DeliciousLangParser.ListExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(@NotNull DeliciousLangParser.AssignContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#BinCond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinCond(@NotNull DeliciousLangParser.BinCondContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#funcdef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncdef(@NotNull DeliciousLangParser.FuncdefContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#AndBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndBool(@NotNull DeliciousLangParser.AndBoolContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#ParenCond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenCond(@NotNull DeliciousLangParser.ParenCondContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#whilestat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhilestat(@NotNull DeliciousLangParser.WhilestatContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#listStat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListStat(@NotNull DeliciousLangParser.ListStatContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(@NotNull DeliciousLangParser.ExprContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#OrXorBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrXorBool(@NotNull DeliciousLangParser.OrXorBoolContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#Funccall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunccall(@NotNull DeliciousLangParser.FunccallContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#Paren}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParen(@NotNull DeliciousLangParser.ParenContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#exprStr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStr(@NotNull DeliciousLangParser.ExprStrContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#stat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStat(@NotNull DeliciousLangParser.StatContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(@NotNull DeliciousLangParser.ProgContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#ShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShortIf(@NotNull DeliciousLangParser.ShortIfContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#listID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListID(@NotNull DeliciousLangParser.ListIDContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#UnarBool}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnarBool(@NotNull DeliciousLangParser.UnarBoolContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#ifstat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfstat(@NotNull DeliciousLangParser.IfstatContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#AriphmPr1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAriphmPr1(@NotNull DeliciousLangParser.AriphmPr1Context ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#Var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(@NotNull DeliciousLangParser.VarContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#Int}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt(@NotNull DeliciousLangParser.IntContext ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#AriphmPr2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAriphmPr2(@NotNull DeliciousLangParser.AriphmPr2Context ctx);

	/**
	 * Visit a parse tree produced by {@link DeliciousLangParser#AriphmPr0}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAriphmPr0(@NotNull DeliciousLangParser.AriphmPr0Context ctx);
}