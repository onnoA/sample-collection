package com.onnoa.shop.demo.video.component;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description 用于执行外部命令（操作系统中）的通用工具类
 *  该工具类可以根据外部程序的返回值（0， 1 等）来判定是否执行成功，并把外部程序打印的日志保存到ExecResult的printOutLines字段，可以输出到自己的应用日志中。
 * @author onnoA
 * @date 2021/7/6 23:22
 */
@Slf4j
@Component
public class ExtCommandExecutor {

    /** 命令执行后被强制kill的超时时间(单位是millisecond, 默认是20分钟)*/
    @Value("${videostream.common.exec.watchdogtimeout:1200000}")
    private long watchDogTimeout;

    public static class ExecResult extends LogOutputStream {
        private int exitCode;
        /**
         * @return the exitCode
         */
        public int getExitCode() {
            return exitCode;
        }

        /**
         * @param exitCode the exitCode to set
         */
        public void setExitCode(int exitCode) {
            this.exitCode = exitCode;
        }

        /**
         * 存储外部程序打印输出的日志
         */
        private final List<String> printOutLines = new LinkedList<String>();

        @Override
        protected void processLine(String line, int level) {
            printOutLines.add(line);
        }

        public List<String> getPrintOutLines() {
            return printOutLines;
        }
    }


    /**
     * execute the given command
     * @param cmd - the command
     * @return the output as lines and exit Code
     *
     * @throws Exception
     */
    public ExecResult execCmd(String cmd) throws Exception {
        return execCmd(cmd, 0); // 默认返回0是正常结束标识
    }

    /**
     * execute the given command
     * @param cmd - the command
     * @param successExitValue - the expected success exit Value， 确认该外部程序正常结束后返回的值，一般为0，非正常结束为非0
     * @return the output as lines and exit Code
     * @throws Exception
     */
    public ExecResult execCmd(String cmd, int successExitValue) throws Exception {
        if (log.isDebugEnabled())
            log.debug("Preparing to run command: {}", cmd);
        CommandLine commandLine = CommandLine.parse(cmd);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(successExitValue);

        ExecResult result =new ExecResult();
        executor.setStreamHandler(new PumpStreamHandler(result));
        executor.setWatchdog(new ExecuteWatchdog(this.watchDogTimeout));
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        executor.execute(commandLine, resultHandler);

        // some time later the result handler callback was invoked so we
        // can safely request the exit value
        resultHandler.waitFor(); // blocking
        result.setExitCode(resultHandler.getExitValue());

        return result;
    }
}
