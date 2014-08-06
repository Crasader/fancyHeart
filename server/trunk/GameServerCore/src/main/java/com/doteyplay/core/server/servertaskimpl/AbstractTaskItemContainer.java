package com.doteyplay.core.server.servertaskimpl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

/**
 * ��Ҫִ�е�������Ŀ������
 */
public abstract class AbstractTaskItemContainer {
	private static final Logger logger=Logger.getLogger(AbstractTaskItemContainer.class);
	/**
	 * �ճ�������Ŀ������
	 */
	protected List<AbstractTaskItem> taskItemList=new CopyOnWriteArrayList<AbstractTaskItem>();
	
	public AbstractTaskItemContainer(){
		init();
	}
	/**
	 * ��ʼ���ķ���
	 */
	public abstract void init();
	/**
	 * ִ������
	 * @return
	 */
	public boolean executeTask(){
        logger.error("ִ�ж�ʱ�������Ŀ");
		if(taskItemList!=null&&taskItemList.size()>0){
			for(AbstractTaskItem item:taskItemList){
                logger.error(item.getName()+"������������ʼִ��"+(new Date()));
                try {
                	if(item.isOpen())
                		item.invoke();
                	else
                		logger.error(item.getName()+"û�п���");                	
                } catch (Exception e){
					logger.error("ִ�ж�ʱ������Ŀ�쳣:" + (new Date()), e);
				}
			}
			logger.error("ִ�����"+(new Date()));
			return true;
		}
		else{
			return false;
		}
	}
	
	public void addItem(AbstractTaskItem item) {
		taskItemList.add(item);
	}
	
	public boolean setOpen(String itemClassName, boolean open){
		for(AbstractTaskItem item:taskItemList){
			if(item.getClass().getSimpleName().equals(itemClassName)){
				item.setOpen(open);
				return true;
			}
		}
		return false;
	}
}
