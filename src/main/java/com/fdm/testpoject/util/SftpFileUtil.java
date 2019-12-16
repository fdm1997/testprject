package com.fdm.testpoject.util;


import java.io.*;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.*;

/**
 * @author fdm
 * @date 2019/8/22 18:18
 * @Description:
 */
public class SftpFileUtil {

    private String host;
    private String username;
    private String password;
    private int port ;
    private ChannelSftp sftp = null;
    private Session sshSession = null;

    /**
     * 创建对象，初始化服务器参数
     * @param host  远程主机地址
     * @param port  远程端口
     * @param username  用户名
     * @param password  密码
     */
    public SftpFileUtil(String host, int port, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    /**
     * 连接SFTP服务器
     */
    public ChannelSftp connect() throws Exception {
        JSch jsch = new JSch();
        sshSession = jsch.getSession(username, host, port);
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();
        Channel channel = sshSession.openChannel("sftp");
        if (channel != null) {
            channel.connect();
        } else {
            throw new Exception("channel connecting failed.");
        }
        sftp = (ChannelSftp) channel;
        return sftp;
    }

    /**
     * 断开服务器
     */
    public void disconnect() {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
                this.sftp = null;
                System.out.println("sftp is closed already");
            }
        }
        if (this.sshSession != null) {
            if (this.sshSession.isConnected()) {
                this.sshSession.disconnect();
                this.sshSession = null;
                System.out.println("sshSession is closed already");
            }
        }
    }

    /**
     * 单个文件上传到远程服务器(路径上传)
     *
     * @param remotePath  远程上传目录
     * @param remoteFileName  远程上传文件名
     * @param localPath   本地上传文件目录
     * @param localFileName  本地上传文件名
     * @return
     */
    public boolean uploadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
        FileInputStream in = null;
        try {
            if (sftp == null)
                this.connect();

            createDir(remotePath);
            File file = new File(localPath + localFileName);
            in = new FileInputStream(file);
            sftp.put(in, remoteFileName);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.disconnect();
        }
        return false;
    }

    /**
     * 单个文件上传到远程服务器(流上传)
     * @param in   文件输入流
     * @param remotePath    远程上传目录
     * @param remoteFileName   远程上传文件名
     * @return
     */
    public boolean uploadFile(InputStream in,String remotePath, String remoteFileName){
        try {
            if (sftp == null)
                this.connect();
            createDir(remotePath);
            sftp.put(in, remoteFileName);
            return true;
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.disconnect();
        }
        return false;
    }

   /**
     * 批量上传本地文件到服务器
     *
     * @param remotePath  上传的远程目录
     * @param localPath  本地上传的目录
     * @param del 上传后是否删除本地文件
     * @return
     */
    public boolean bacthUploadFile(String remotePath, String localPath, boolean del) {
        try {
            File file = new File(localPath);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile() && files[i].getName().indexOf("bak") == -1) {
                    synchronized (remotePath) {
                        if (this.uploadFile(remotePath, files[i].getName(), localPath, files[i].getName()) && del) {
                            deleteFile(localPath + files[i].getName());
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 批量下载文件
     *
     * @param remotPath  远程下载目录(以路径符号结束)
     * @param localPath  本地保存目录(以路径符号结束)
     * @param fileFormat 下载文件格式(也就是文件名前缀,为空则不加)
     * @param del        下载后是否删除sftp文件
     * @return
     */
    @SuppressWarnings("rawtypes")
    public boolean batchDownLoadFile(String remotPath, String localPath,String fileFormat, boolean del)  {
        try {
            Vector v = listFiles(remotPath);
            if (v.size() > 0) {
                Iterator it = v.iterator();
                while (it.hasNext()) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        if (fileFormat != null && !"".equals(fileFormat.trim())) {
                            if (filename.startsWith(fileFormat)) {
                                if (this.downloadFile(remotPath, filename, localPath, filename) && del) {
                                    deleteSFTP(remotPath, filename);
                                }
                            }
                        } else {
                            if (this.downloadFile(remotPath, filename,localPath, filename) && del) {
                                deleteSFTP(remotPath, filename);
                            }
                        }
                    }
                }
            }
        } catch (SftpException e) {
           e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 单个文件下载
     *
     * @param remotePath
     * @param remoteFileName
     * @param localPath
     * @param localFileName
     * @return
     */
    public boolean downloadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
        try {
            if (sftp == null)
                this.connect();
            sftp.cd(remotePath);
            File file = new File(localPath + localFileName);
            mkdirs(localPath + localFileName);
            sftp.get(remoteFileName, new FileOutputStream(file));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }

        return false;
    }

    /**
     * 列出目录文件
     *
     * @param directory
     * @return
     * @throws SftpException
     */
    @SuppressWarnings("rawtypes")
    public Vector listFiles(String directory) throws SftpException {
        try {
            if (sftp == null)
                this.connect();
            return  sftp.ls(directory);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            this.disconnect();
        }
        return null;
    }

    /**
     * 创建文件夹
     * @param createpath
     * @return
     */
    public boolean createDir(String createpath) {
        try {
            if (sftp == null)
                this.connect();
            if (isDirExist(createpath)) {
                this.sftp.cd(createpath);
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                createpath = filePath.toString();
                if (isDirExist(createpath)) {
                    sftp.cd(createpath);
                } else {
                    sftp.mkdir(createpath);
                    sftp.cd(createpath);
                }
            }
            this.sftp.cd(createpath);
            return true;
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.disconnect();
        }
        return false;
    }

    /**
     * 判断目录是否存在
     *
     * @param directory
     * @return
     */
    @SuppressWarnings("DefaultLocale")
    public boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        try {
            if (sftp == null)
              this.connect();
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }finally {
            this.disconnect();
        }
        return isDirExistFlag;
    }

    /**
     * 删除指定路径下的指定文件
     * @param directory  路径
     * @param deleteFile  文件
     */
    public void deleteSFTP(String directory, String deleteFile) {
        try {
            if (sftp == null){this.connect();}
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.disconnect();
        }
    }

   /**
     * （本地操作）删除本地文件
     *
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        if (!file.isFile()) {
            return false;
        }
        return file.delete();
    }

   /**
     * (本地操作)创建本地目录
     *
     * @param path
     */
    public void mkdirs(String path) {
        File f = new File(path);
        String fs = f.getParent();
        f = new File(fs);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    public static void main(String[] args) {
        SftpFileUtil sftpFileUtil = new SftpFileUtil("192.168.20.101",22,"root","root");
        sftpFileUtil.uploadFile("/opt/test","test.txt","C:\\Users\\Lenovo\\Desktop","新建文本文档 (2).txt");

    }
}
