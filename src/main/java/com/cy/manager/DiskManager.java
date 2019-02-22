package com.cy.manager;

import com.cy.common.FinalConstants;
import com.cy.util.PropertyHelper;

import static com.cy.common.FinalConstants.logger;

/**
 * Created by cy on 2017/3/7.
 */
public class DiskManager {

    public static String getPropertiesItem(){
        String mPropertiesItem= PropertyHelper.getValue(FinalConstants.PROPERTIES_XMLPATH_ITEMS);
        logger.info("mPropertiesItem:"+mPropertiesItem);
        return mPropertiesItem;
    }

    public static void savePropertiesItem(String currentItems,String newItem){
        if (currentItems ==null|| currentItems.length()==0){
            PropertyHelper.writeProperties(FinalConstants.PROPERTIES_XMLPATH_ITEMS,newItem);
        }else {
            PropertyHelper.writeProperties(FinalConstants.PROPERTIES_XMLPATH_ITEMS, currentItems +"|"+newItem);
        }
    }
}
