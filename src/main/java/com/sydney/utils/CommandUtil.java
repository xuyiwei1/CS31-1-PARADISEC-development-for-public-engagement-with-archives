package com.sydney.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.ssh.JschUtil;
import com.jcraft.jsch.Session;

/**
 * @author Yiwei Xu
 * @version 1.0
 * @description TODO
 * @date 2023/10/18 9:37
 */
public class CommandUtil {
    public static final String getConnect(String sshHost, String sshUser, String sshPass, String cmd) {
        Session session = JschUtil.getSession(sshHost, 22, sshUser, sshPass);
        String exec = JschUtil.exec(session, cmd, CharsetUtil.CHARSET_UTF_8);
        JschUtil.close(session);
        return exec;
    }
}
