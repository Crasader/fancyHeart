package com.doteyplay.game.config.template;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.doteyplay.game.domain.tollgate.NodeArray;
import com.doteyplay.game.util.excel.ExcelRowBinding;
import com.doteyplay.game.util.excel.TemplateService;

/**
 * @className:TollgateDataObject.java
 * @classDescription: ������װ��Ĺؿ���Ϣ��
 * @author:Tom.Zheng
 * @createTime:2014��6��25�� ����12:01:33
 */
@ExcelRowBinding
public class TollgateDataObject extends TollgateDataTemplate {

	/**
	 * @author:Tom.Zheng
	 * @createTime:2014��6��25�� ����12:10:14
	 */
	private static final long serialVersionUID = -7250763316096756322L;
	
	private NodeArray<Integer, TollgateNodeDataTemplate> nodeArray=null;
	
	@Override
	public void patchUp() throws Exception {
		// TODO Auto-generated method stub
		super.patchUp();
		//��ʼ��
		nodeArray = new NodeArray<Integer, TollgateNodeDataTemplate>();
		
		Map<Integer, TollgateNodeDataTemplate> all = TemplateService
				.getInstance().getAll(TollgateNodeDataTemplate.class);

		for (TollgateNodeDataTemplate iterable_element : all.values()) {
			if (iterable_element.getTollgateGateId() == this.id) {
				nodeArray.addValue(iterable_element.getId(), iterable_element);
			}
		}
		
		if(nodeArray.size()==0){
			System.err.println("�ؿ��������û�нڵ������!!�༭���������ȡ����!Id="+this.id);
		}
	}

	public List<TollgateNodeDataTemplate> getNodeList() {
		return nodeArray.getNodeList();
	}
	
	public TollgateNodeDataTemplate getNode(int nodeId){
		return nodeArray.get(nodeId);
	}
	
	public TollgateNodeDataTemplate getFirstNode(){
		
		List<TollgateNodeDataTemplate> nodeList = getNodeList();
		if(nodeList.size()==0){
			return null;
		}
		return nodeList.get(0);
	}
	
	/**
	 * Ϊ˳�򼤻�ÿһ���ڵ�,�����Ĳ���.
	 * @param nodeId
	 * @return
	 */
	public TollgateNodeDataTemplate getNextNode(int nodeId){
		List<TollgateNodeDataTemplate> nodeList = getNodeList();
		
		Collections.sort(nodeList,  new Comparator<TollgateNodeDataTemplate>(){

			@Override
			public int compare(TollgateNodeDataTemplate o1,
					TollgateNodeDataTemplate o2) {
				// TODO Auto-generated method stub
				return o1.getId()-o2.getId();
			}
			
		});
		
		for (int i = 0; i < nodeList.size(); i++) {
			TollgateNodeDataTemplate temp = nodeList.get(i);
			if(temp.getId()==nodeId){
				int nextIndex = i+1;
				if(nextIndex>=nodeList.size()){
					return null;
				}else{
					return nodeList.get(nextIndex);
				}
			
			}
		}
		return null;
	}
	
	
	

}
