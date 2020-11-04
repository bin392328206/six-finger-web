package com.xiaoliuliu.rpc.core.compress;


import com.xiaoliuliu.rpc.common.extension.SPI;

/**
 * @author wangtao .
 * @createTime on 2020/10/3
 */

@SPI
public interface Compress {

    byte[] compress(byte[] bytes);


    byte[] decompress(byte[] bytes);
}
