package org.pz.netty.c9;

import org.apache.commons.codec.binary.Hex;

public class NettyMessage {

    private int header = Constance.MAGIC_NUM;

    private byte version;//版本

    private byte protocol;//协议类型

    private int deviceNo;//设备号

    private int length;//内容长度

    private byte[] content;//内容

    public int getHeader() {
        return header;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getProtocol() {
        return protocol;
    }

    public void setProtocol(byte protocol) {
        this.protocol = protocol;
    }

    public int getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(int deviceNo) {
        this.deviceNo = deviceNo;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString () {
        StringBuffer sb = new StringBuffer("NettyMessage:{");
        sb.append("header=").append(this.header);
        sb.append(",version=").append(this.version);
        sb.append(",protocol=").append(this.protocol);
        sb.append(",deviceNo=").append(this.deviceNo);
        sb.append(",length=").append(this.length);
        sb.append(",content=").append(Hex.encodeHex(this.content));
        sb.append("}");
        return sb.toString();
    }
}
