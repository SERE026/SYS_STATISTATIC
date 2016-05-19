package com.ycm.web.pool;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 所有Queue的父类
 * @param <E>
 */
public abstract class AbstractQueue<E> extends LinkedBlockingQueue<E>
{

    private static final long serialVersionUID = -3645243155204152472L;
    
    private static Logger logger = LoggerFactory.getLogger(AbstractQueue.class);
    
    @Override
    public void put(E e) 
    {
        try
        {
            super.put(e);
        }
        catch(InterruptedException ie)
        {
            logger.error("InterruptedException while put new task in", ie);
        }
    }

    /**
     * 取队列头部的一个Task对象
     */
    @Override
    public E take()
    {
        try
        {
            return super.take();
        }
        catch(InterruptedException ie)
        {
            logger.error("InterruptedException while put new task in", ie);
            return null;
        }
    }

    
}
