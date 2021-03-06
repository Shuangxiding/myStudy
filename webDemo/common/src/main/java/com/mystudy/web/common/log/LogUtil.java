package com.mystudy.web.common.log;

import org.slf4j.Logger;
import sun.rmi.runtime.Log;

/**
 * Created by 程祥 on 15/11/27.
 * Function：记录日志的公共方法 使用log4j2,需清楚知道原理和其优缺点~
 */
public class LogUtil {
    private static final Logger commonLogger = new LogWrapper("CommonLogger");
    private static final Logger testLogger = new LogWrapper("TestLogger");
    private static final Logger checkListLogger = new LogWrapper("CheckListLogger");

    public static Logger getCommonLogger(){
        return commonLogger;
    }

    public static Logger getCheckListLogger(){return checkListLogger;}

    public static Logger getTestLogger(){
        return testLogger;
    }


}
