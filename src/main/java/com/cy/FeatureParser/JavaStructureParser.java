package com.cy.FeatureParser;

import com.cy.data.UtilList;
import com.github.javaparser.JavaParser;
import com.github.javaparser.Position;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * javaParser line 从 1 开始，保存到内存数据集变为从 0 开始，与文件读取到List从 0 开始保持一致
 */
public class JavaStructureParser {

    private CompilationUnit mCu;
    private List<Method> mMethods = new ArrayList<>();
    private List<Field> mFields = new ArrayList<>();
    private int mBodyBegin;
    /**
     * 字段区域代码的起止定义区间行数
     */
    private int[] mFieldsRegion = new int[2];

    public int[] getFieldsRegion() {
        return mFieldsRegion;
    }

    public int[] getMethodsRegion() {
        return mMethodsRegion;
    }

    /**
     * 函数区域代码的起止定义区间行数
     */
    private int[] mMethodsRegion = new int[2];

    public JavaStructureParser(File file) {
        mCu = newCu(file);
        parseNodes();
        analyseFieldsRegion();
        analyseMethodsRegion();
    }

    public Method findMethodByName(String methodName) {
        if (UtilList.isEmpty(mMethods)) return null;
        for (Method method : mMethods) {
            if (method.name.equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    private void analyseFieldsRegion() {
        int begin = 0, end = 0;
        for (Field field : mFields) {

            if (begin == 0) begin = field.lineBegin;
            begin = field.lineBegin < begin ? field.lineBegin : begin;

//            if (end == 0) end = field.lineEnd;
            end = field.lineEnd < end ? end : field.lineEnd;
        }
        mFieldsRegion[0] = begin;
        mFieldsRegion[1] = end;
    }

    private void analyseMethodsRegion() {
        int begin = 0, end = 0;
        for (Method method : mMethods) {

            if (begin == 0) begin = method.lineBegin;
            begin = method.lineBegin < begin ? method.lineBegin : begin;

//            if (end == 0) end = method.lineEnd;
            end = method.lineEnd < end ? end : method.lineEnd;
        }
        mMethodsRegion[0] = begin;
        mMethodsRegion[1] = end;
    }

    public void parseNodes() {
        TypeDeclaration typeDeclaration = mCu.getType(0);

        if (typeDeclaration.getMembers().size() > 0) {
            BodyDeclaration bodyDeclaration = typeDeclaration.getMember(0);
            Position position = (Position) bodyDeclaration.getBegin().get();
            mBodyBegin = position.line - 1;//javaParser line 从 1 开始，保存到内存数据集变为从 0 开始
        } else {
            //如果是一个空类，bodybegin看做是类的结束符}所在行。
            mBodyBegin = ((Position) typeDeclaration.getEnd().get()).line - 1;
        }
        mCu.accept(new NodeVisitor(), null);
    }

    public int getmBodyBegin() {
        return mBodyBegin;
    }


    private class NodeVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            super.visit(n, arg);
            Method method = new Method(n.getBegin().get().line - 1,
                    n.getEnd().get().line - 1,
                    n.getNameAsString());

            mMethods.add(method);
        }

        @Override
        public void visit(FieldDeclaration n, Void arg) {
            super.visit(n, arg);
            Field field = new Field(n.getBegin().get().line - 1,
                    n.getEnd().get().line - 1,
                    n.getVariables().get(0).getNameAsString());

            mFields.add(field);
        }

    }

    public static CompilationUnit newCu(File file) {
        CompilationUnit cu = null;
        try {
            cu = JavaParser.parse(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cu;
    }

    public static class Method {
        public int lineBegin;
        public int lineEnd;
        public String name;

        public Method(int lineBegin, int lineEnd, String name) {
            this.lineBegin = lineBegin;
            this.lineEnd = lineEnd;
            this.name = name;
        }
    }

    public static class Field {
        public int lineBegin;
        public int lineEnd;
        public String name;

        public Field(int lineBegin, int lineEnd, String name) {
            this.lineBegin = lineBegin;
            this.lineEnd = lineEnd;
            this.name = name;
        }
    }

    private static final String DUMMY_CLASS_DECLARE = "public class Dummy{";
    private static final String DUMMY_END = "}";

    public static String parseMethodName(String methodCode) {
        String dummyCode = DUMMY_CLASS_DECLARE + methodCode + DUMMY_END;
        CompilationUnit cu = JavaParser.parse(dummyCode);
        NodeList<BodyDeclaration<?>> members = cu.getType(0).getMembers();
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i) instanceof MethodDeclaration) {
                MethodDeclaration method = (MethodDeclaration) (members.get(i));
                return method.getNameAsString();
            }
        }
        return null;
    }
}
