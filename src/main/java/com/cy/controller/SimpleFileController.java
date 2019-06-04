package com.cy.controller;

import com.cy.bean.AndroidView;
import com.cy.common.Constants;
import com.cy.ui.SimpleFormatSelectDialog;
import com.cy.util.AndroidUtils;
import com.cy.util.JavaCommonUtils;
import com.cy.util.UtilPlugin;
import com.cy.util.UtilTemplete;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.DocumentUtil;
import com.intellij.util.IncorrectOperationException;
import com.jfinal.kit.Kv;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by shenjianlin on 2018/9/4.
 * 控制生成activity中导入控件逻辑控制器
 */
public class SimpleFileController {

    /**
     //                List<AndroidView> androidViews=AndroidView.convert(mBeanWidgets);
     //                genSourceByPsi(androidViews);
     * @param androidViews
     */
    private void genSourceByPsi(List<AndroidView> androidViews) {
        WriteCommandAction.runWriteCommandAction(Constants.getAnActionEvent().getProject(), new Runnable() {
            @Override
            public void run() {
                SimpleFileController.loadFile(Constants.getAnActionEvent(), androidViews);
            }
        });
    }

    public static void loadFileByDialog(AnActionEvent anActionEvent) throws IncorrectOperationException {

        DocumentUtil.writeInRunUndoTransparentAction(new Runnable() {
            @Override
            public void run() {
                SimpleFormatSelectDialog.showDialog(anActionEvent);
            }
        });

    }

    public static void loadFile(AnActionEvent anActionEvent, List<AndroidView> androidViews) throws IncorrectOperationException {
        int offset = UtilPlugin.getEditor(anActionEvent).getCaretModel().getOffset();
        Project project = UtilPlugin.getProject(anActionEvent);
        //获取到正在编辑器中编辑的方法体。
        PsiElement currMethodPsiElement = UtilPlugin.getPsiFile(anActionEvent).findElementAt(offset);
        PsiStatement psiStatement = PsiTreeUtil.getParentOfType(currMethodPsiElement, PsiStatement.class);
        //获取当前操作的java类
        PsiClass psiClass = PsiTreeUtil.getParentOfType(currMethodPsiElement, PsiClass.class);
        //获取原来的成变量
        Set<String> fieldSet = new HashSet<String>();
        for (PsiField field : psiClass.getFields()) {
            fieldSet.add(field.getName());
        }

        Set<String> methodSet = new HashSet<>();
        for (PsiMethod method : psiClass.getMethods()) {
            methodSet.add(method.getName());
        }

        // collect this.foo = "" and (this.)foo = ""
        // collection already init variables
        final Set<String> thisSet = new HashSet<String>();
        processElement(currMethodPsiElement, thisSet);

        //获取当前工程的操作factory，要靠这个对象来进行添加对象的创建
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);
        String initViewMethodStr = "private void initView(){}";
        PsiMethod initViewMethod = elementFactory.createMethodFromText(initViewMethodStr, psiClass);
        PsiStatement initViewUseStatement = elementFactory.createStatementFromText("initView();", psiStatement);
        PsiMethod choiceMethod = PsiTreeUtil.getParentOfType(psiStatement, PsiMethod.class);
        if (choiceMethod != null) {
            choiceMethod.getBody().add(initViewUseStatement);
        }
//                psiStatement.add(initViewUseStatement);
        //导入android.view包
        JavaCommonUtils.importAndroidPackage(psiClass, project, "View");

        String clickStr = "private View.OnClickListener mOnClickListener = new View.OnClickListener() {" +
                "        @Override\n" +
                "        public void onClick(View v) {\n" +
                "            switch (v.getId()) {\n" +
                "                ";
        //遍历每个操作view，生成对应需要的代码。内部拼装方式，很好懂
        for (AndroidView v : androidViews) {
            if (!v.isChoice) {
                continue;
            }
            if (!fieldSet.contains(v.getFieldName())) {
//                String sb = "private " + v.getName() + " " + v.getFieldName() + ";";
                String declare= UtilTemplete.getByEnjoy("enjoy/declare",
                        Kv.by("id",v.getId())
                                .set("class",v.getName())
                                .set("name",v.getFieldName())
                        .set("useButterknife",true)
                );
                //插入字段
                PsiField psiField=elementFactory.createFieldFromText(declare, psiClass);
                psiClass.add(psiField);
                //给字段插入注解
                PsiAnnotation psiAnnotation = elementFactory.createAnnotationFromText(
                        UtilTemplete.getByEnjoy("enjoy/declare_butterknife_annotation",
                                Kv.by("id",v.getId()) .set("useButterknife",true)),psiClass);
                psiClass.addBefore(psiAnnotation,psiField);
                //插入import
                JavaCommonUtils.importAndroidPackage(psiClass, project, v.getName());

                fieldSet.add(v.getFieldName());
            }
            if (!thisSet.contains(v.getFieldName())) {
                String sb1;
                sb1 = String.format("%s = findViewById(%s);", v.getFieldName(), v.getName(), v.getId());
                PsiStatement statementFromText = elementFactory.createStatementFromText(sb1, null);
                initViewMethod.getBody().add(statementFromText);
            }

        }
        boolean hasClick = false;
        for (AndroidView tv : androidViews) {
            if (!thisSet.contains(tv.getFieldName()) && tv.isClick && fieldSet.contains(tv.getFieldName())) {
                clickStr = clickStr + "case " + tv.getId() + ":\n\nbreak;";
                hasClick = true;
                String sb2;
                sb2 = String.format("%s.setOnClickListener(%s);", tv.getFieldName(), "mOnClickListener");
                PsiStatement clickStrStatement = elementFactory.createStatementFromText(sb2, null);
                initViewMethod.getBody().add(clickStrStatement);

            }

        }
        clickStr = clickStr + "default:\n" +
                "                        break;\n" +
                "            }\n" +
                "        }\n" +
                "    };";
        System.out.print(clickStr);
        //把拼装好的点击事件的对象添加到类中。
        if (!fieldSet.contains("mOnClickListener") && hasClick) {
            PsiField clickText = elementFactory.createFieldFromText(clickStr, psiClass);
            psiClass.add(clickText);
        }

        if (!methodSet.contains("initView")) {
            psiClass.add(initViewMethod);
        }
        //CodeStyle
        CodeStyleManager.getInstance(project).reformat(psiClass.getParent());
    }

    /**
     * TODO Dialog 可配置ViewHolder方法。
     *
     * @throws IncorrectOperationException
     */
    public static void loadFileToAdapter(AnActionEvent anActionEvent) throws IncorrectOperationException {

        DocumentUtil.writeInRunUndoTransparentAction(new Runnable() {
            @Override
            public void run() {
                Project project= UtilPlugin.getProject(anActionEvent);
                String layoutName = UtilPlugin.getSelectedText(anActionEvent);
                PsiFile xmlFile = UtilPlugin.getFirstPsiFileByFileName(anActionEvent,layoutName+".xml");
                if (xmlFile == null) {
                    return;
                }
                List<AndroidView> androidViews = AndroidUtils.getAndroidViewsFromXML(xmlFile);
                int offset = UtilPlugin.getEditor(anActionEvent).getCaretModel().getOffset();
                PsiElement psiElement = UtilPlugin.getPsiFile(anActionEvent).findElementAt(offset);
                PsiStatement psiStatement = PsiTreeUtil.getParentOfType(psiElement, PsiStatement.class);
                // collection class field
                // check if we need to set them
                PsiClass psiClass = PsiTreeUtil.getParentOfType(psiElement, PsiClass.class);
                Set<String> fieldSet = new HashSet<String>();
                for (PsiField field : psiClass.getFields()) {
                    fieldSet.add(field.getName());
                }

                // collect this.foo = "" and (this.)foo = ""
                // collection already init variables
                final Set<String> thisSet = new HashSet<String>();
                processElement(psiElement, thisSet);
                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(project);
                JavaCommonUtils.importAndroidPackage(psiClass, project, "View");
                PsiClass innerClass = psiClass.findInnerClassByName("ViewHolder", true);
                JavaCommonUtils.importSelProjectPackage(psiClass, project, "InjectView");
                for (AndroidView v : androidViews) {
                    if (!fieldSet.contains(v.getFieldName())) {
                        String injectViewStr = String.format("@InjectView(%s)\n", v.getId());
                        String sb = "private " + v.getName() + " " + v.getFieldName() + ";";
                        String injectStr = injectViewStr + sb;
                        innerClass.add(elementFactory.createFieldFromText(injectStr, innerClass));
                        JavaCommonUtils.importAndroidPackage(psiClass, project, v.getName());
                    }
                }
                CodeStyleManager.getInstance(project).reformat(psiClass.getParent());
            }
        });

    }

    private static void processElement(PsiElement psiElement, final Set<String> thisSet) {
        PsiTreeUtil.processElements(psiElement, new PsiElementProcessor() {

            @Override
            public boolean execute(@NotNull PsiElement element) {

                if (element instanceof PsiThisExpression) {
                    attachFieldName(element.getParent());
                } else if (element instanceof PsiAssignmentExpression) {
                    attachFieldName(((PsiAssignmentExpression) element).getLExpression());
                }

                return true;
            }

            private void attachFieldName(PsiElement psiExpression) {

                if (!(psiExpression instanceof PsiReferenceExpression)) {
                    return;
                }

                PsiElement psiField = ((PsiReferenceExpression) psiExpression).resolve();
                if (psiField instanceof PsiField) {
                    thisSet.add(((PsiField) psiField).getName());
                }
            }
        });
    }
}
