package com.cy.util;

import com.cy.data.UArray;
import com.cy.data.UList;
import org.apache.commons.cli.*;

import java.util.LinkedHashMap;
import java.util.List;

public class UtilCommonCLI {

    /**
     * 备注另外一种获取option值的方法
     * CommandLine parsedCmd = null;
     * parsedCmd = parser.parse(options, args, true);
     * parsedCmd.getOptionValues("layout")
     * @param options
     * eg:
    简版
    Options options = new Options()
    .addOption(OptionBuilder.withLongOpt("sources").hasArgs().create())
    .addOption(OptionBuilder.withLongOpt("layout").hasArgs().create())
    .addOption(OptionBuilder.withLongOpt("click").create());

    复杂版
    Options options = new Options();

    options.addOption(
    OptionBuilder
    .withValueSeparator(',')  //weak usage,use valueSeprator replace this if have more than one seprators.
    .withLongOpt("sources")
    .withDescription("path to sources")
    .hasArgs()
    .withArgName("PATHS")
    .create());

    options.addOption(
    OptionBuilder
    .withValueSeparator(',')
    .withLongOpt("layout")
    .withDescription("path to sources")
    .hasArgs()
    .withArgName("PATHS")
    .create());
     * @param cmd
     * @return  args and values
     */
    public static LinkedHashMap<String,String[]> parseCmd(Options options, String cmd,String... valueSeprator){
        String[] args=formatCommand(cmd,valueSeprator);
        CommandLineParser parser = new GnuParser();
        CommandLine parsedCmd = null;
        try {
            parsedCmd = parser.parse(options, args, true);
        } catch (ParseException exp) {
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
        LinkedHashMap<String,String[]> parsedCmds=new LinkedHashMap<>();
        Option[] parsedOptions=parsedCmd.getOptions();
        if (!UArray.isEmpty(parsedOptions)){
            for (int i = 0; i < parsedOptions.length; i++) {
                parsedCmds.put(parsedOptions[i].getLongOpt(),parsedOptions[i].getValues());
            }
        }
        return parsedCmds;
    }

    private static String[] formatCommand(String command,String... valueSeprator){
        String[] args;
        if (UArray.isEmpty(valueSeprator)){
            args = command.split(" |,");
        }else {
            args = valueSeprator;
        }
        if (UArray.isEmpty(args)) return null;

        List<String> list= UList.arrayToList(args);
        for (int i = 0; i < list.size(); i++) {
            if ("".equals(list.get(i))){
                list.remove(i);
                i--;
            }
        }
        String[] argsResult= UList.listToArray(list,new String[]{});
        return argsResult;
    }
}
