package com.cy.func_entry;

import com.cy.func_entry.intent.IntentCmdParser;

public class  CmdParser{

    private static IConcFuncParser selectConcFuncParser(String cmdLine){
        if (com.cy.data.UtilString.isEmpty(cmdLine)) return null;
        String[] args = cmdLine.split(" ");

        String[] params = new String[args.length - 1];
        System.arraycopy(args, 1, params, 0, args.length - 1);

        String funcName = args[0];

        switch (funcName){
            case "intent":
                return new IntentCmdParser(params);
            default:
                return null;
        }
    }

    public static void parse(String cmdLine){
        selectConcFuncParser(cmdLine);
//        IConcFuncParser parser=selectConcFuncParser(cmdLine);
//        if (parser instanceof IntentCmdParser){
//            IntentCmdParser intentCmdParser= (IntentCmdParser) parser;
//
//        }
    }
}
