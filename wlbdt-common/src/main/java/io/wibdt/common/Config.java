package io.wibdt.common;

public interface Config {

    String COMMON_PROD_TEMP_PATH = System.getProperty("user.dir") + "/prodTemp/";

    String COMMON_RECV_TEMP_PATH = System.getProperty("user.dir") + "/recvTemp/";

    String COMMON_PROD_RESOURCE_PATH = System.getProperty("user.dir") + "/wlbdt-producer/src/main/resources/";

    int COMMON_PROD_QRCODE_SIZE = 700;

}
