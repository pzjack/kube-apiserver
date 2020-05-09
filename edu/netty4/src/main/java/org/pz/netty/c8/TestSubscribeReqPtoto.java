package org.pz.netty.c8;

import com.google.protobuf.InvalidProtocolBufferException;

public class TestSubscribeReqPtoto {

    private static byte[] encode (SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode (byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq create() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(1);
        builder.setUserName("jack");
        builder.setProductName("Netty in Action");
        builder.setAddress("长安十二时辰");
        return builder.build();
    }

    public static void main(String[] args) throws Exception {
        SubscribeReqProto.SubscribeReq req = create();
        System.out.println("Before encode:" + req.toString());
        SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
        System.out.println("After decode:" + req.toString());
        System.out.println("Asset eq:" + req.equals(req2));
    }
}
