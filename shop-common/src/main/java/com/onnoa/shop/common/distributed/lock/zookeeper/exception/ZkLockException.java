package com.onnoa.shop.common.distributed.lock.zookeeper.exception;

/**
 * @Description:  ZK锁异常
 * @Author: onnoA
 * @Date: 2020/6/12 15:47
 */
public class ZkLockException extends DistributedLockException {
	private static final long serialVersionUID = 4596228837703995210L;

	public ZkLockException(){
		super();
	}

	public ZkLockException(String message){
		super(message);
	}

    public ZkLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
