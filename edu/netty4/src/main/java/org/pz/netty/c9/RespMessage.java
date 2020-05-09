package org.pz.netty.c9;



public class RespMessage {

    private int header = Constance.MAGIC_NUM;

    private byte flag = 0;//是否成功标记，成功0，失败1

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    @Override
    public String toString () {
        StringBuffer sb = new StringBuffer("RespMessage:{");
        sb.append("header=").append(this.header);
        sb.append(",flag=").append(this.flag);
        sb.append("}");
        return sb.toString();
    }
}
