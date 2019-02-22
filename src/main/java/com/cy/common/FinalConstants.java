package com.cy.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by cy on 2016/1/30.
 */
public class FinalConstants {
    public static final String currVersion ="1.1.8";
    public static final String DIFF_VERIFI="flashmonkey_gen";
    public static final String PATH_BEFORE_GEN ="/before_gen/";


    public static final String HELP_CONTENT="1、提供xml布局文件路径时，xml文件中必须有tools:context" +
            "属性指定对应activity或Fragment类名；\n提供activity或fragment文件路径时，Java代码中必须已设置R.layout.xxx布局" +
            "\n2、xml中需要生成代码的组件id名必须以m开头" +
            "\n3、xml中需要生成点击事件的组件请加上android:tag属性，属性值含\"-click\"，" +
            "如android:tag=\"...others -click others...\"" +
            "\n4、列表型控件自动生成adapter，在布局文件中对应控件增加属性：" +
            "android:tag=\"-layout xml1 xml2 -bean yyy\"" ;
    public static final String MENU_AUTHOR_CONTENT="Made By 承影" +
            "\nhttp:github.com/djun100" +
            "\nQQ 969460400" ;
    public static final String MENU_CHANGELOG=
            "v"+currVersion+"\n"+
                    "生成代码可用模板定制\n"+
            "v1.1.7\n"+
                    "支持提供activity或fragment路径来关联布局文件生成代码\n"+
            "v1.1.6\n"+
                    "不再需要Java文件中写genXXX_start/end等信息，变量自动生成到类的前部，view初始化函数自动生成到类的末尾\n"+
            "v1.1.5\n"+
                    "增加自动生成adapter功能；\n"+
            "v1.1.4\n"+
                    "需要生成点击事件时，布局文件对应组件加android:tag=\"-click\";\n"+
            "v1.1.3\n"+
                    "点击事件由switch id改为if(v==写法;\n"+
            "v1.1.2\n"+
                    "修复include标签支持;\n"+
            "v1.1.1\n"+
                    "修复包名带下划线的情况_错误;\n"+
            "v1.1.0\n"+
                    "增加include标签支持，标签内的节点只要符合约定的id命名规则同样将被解析;\n"+
            "v1.0.2\n" +
            "1、增加写回原代码中单击事件的功能，使原代码不会丢失；\n" +
                    "2、修复使原activity中文乱码的问题\n" +
            "v1.0.1\n" +
            "1、增加保存历史记录功能，可以下拉选择历史处理过的文件路径";

    public static final String PROPERTIES_XMLPATH_ITEMS="PROPERTIES_XMLPATH_ITEMS";

    public static final String TITLE="闪猿 "+ currVersion+"    一个会写代码的机器人";

    public static final Logger logger = LogManager.getLogger("LayoutToActivity");


}
